package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PDFMessagesToNotParse;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PackagingList;
import online.guessersoftware.casadoagricultorapi.webservice.constants.TypeList;
import online.guessersoftware.casadoagricultorapi.webservice.service.CotationService;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

@Service
public class CotationProcessor {

	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private CotationService cotationService;

	public void processByUrl(String urlString, CeasasEnum ceasa) {
		try {

			PDDocument document = createPDDocumentByUrl(urlString);
			String[] lines = getLinesOfPDDocument(document);
			List<String> linesWithCotations = excludeNotCotationsLines(lines);
			String dateOfCotations = getDateOfCotations(linesWithCotations);
			linesWithCotations = makeAdjustsAndfiltersOnCotationLines(linesWithCotations);

			// ########## COTATIONS #######
			List<CotationValueObject> cotationsValueObject = new ArrayList<CotationValueObject>();
			List<String> linesWithCotationsAdjusted = new ArrayList<String>();

			adjustAndInsertCotationsLines(linesWithCotations, linesWithCotationsAdjusted);
			linesWithCotationsAdjusted.forEach(cotation -> {
				CotationValueObject cotationValueObject = parseAndCreateCotation(ceasa, cotation, dateOfCotations);
				if (cotationValueObject != null) {
					cotationsValueObject.add(cotationValueObject);
				}
			});
			document.close();
			cotationService.saveCotationsValueObject(cotationsValueObject);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void adjustAndInsertCotationsLines(List<String> linesWithCotations, List<String> linesWithCotationsAdjusted) {
		int flag = -1;
		for (int i = 0; i < linesWithCotations.size(); i++) {
			String cotation = linesWithCotations.get(i);
			if (StringUtils.equals(cotation, "Escovada") || flag == 1) {
				String oldValue = linesWithCotationsAdjusted.get(linesWithCotationsAdjusted.size() - 1);
				linesWithCotationsAdjusted.set(linesWithCotationsAdjusted.size() - 1, oldValue + " " + cotation);
				flag = flag * -1;
				continue;
			}
			linesWithCotationsAdjusted.add(cotation);
		}
	}

	private List<String> makeAdjustsAndfiltersOnCotationLines(List<String> linesWithCotations) {
		List<String> linesFiltered = linesWithCotations.stream()
				.filter(cotation -> (cotation.length() <= 17 && !StringUtils.equals(cotation, "Escovada")))
				.collect(Collectors.toList());
		logger.info("Filtering the following lines: ");
		linesFiltered.forEach(lf -> logger.info(lf));
		
		linesWithCotations = linesWithCotations.stream()
				.filter(cotation -> (cotation.length() > 17 || StringUtils.equals(cotation, "Escovada")))
				.collect(Collectors.toList());

		return linesWithCotations;
	}

	private String getDateOfCotations(List<String> linesWithCotations) {
		return linesWithCotations.stream().filter(cotation -> cotation.matches(Constants.DATE_REGEX)).findFirst().get();
	}

	private List<String> excludeNotCotationsLines(String[] lines) {
		List<String> linesWithCotations = new ArrayList<String>();
		for (String line : lines) {
			if (PDFMessagesToNotParse.contains(line)) {
				continue;
			}
			linesWithCotations.add(line);
		}
		return linesWithCotations;
	}

	private String[] getLinesOfPDDocument(PDDocument document) throws IOException {
		PDFTextStripper tStripper = new PDFTextStripper();
		String pdfFileInText = tStripper.getText(document);
		// split by line
		String lines[] = pdfFileInText.split("\\n");
		return lines;
	}

	private PDDocument createPDDocumentByUrl(String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		BufferedInputStream fileParse = new BufferedInputStream(is);
		PDDocument document = PDDocument.load(fileParse);
		return document;
	}

	private CotationValueObject parseAndCreateCotation(CeasasEnum ceasa, String cotationString, String dateOfCotation) {
		CotationValueObject cotation = new CotationValueObject();
		cotation.setCeasaValueObject(ceasa);
		try {
			cotation.setFromDay(new SimpleDateFormat("dd/MM/yyyy").parse(dateOfCotation));
		} catch (ParseException e) {
			logger.error("Error while parsing date. Date: " + dateOfCotation + ". Exception: " + e.getMessage());
			return null;
		}
		// ### PRODUCT AND VARIETY
		int charPositionOfMinimunPrice = 0;
		for (int i = 0; i < cotationString.length(); i++) {
			if (Constants.NUMBERS.contains(String.valueOf(cotationString.charAt(i)))) {
				charPositionOfMinimunPrice = i;
				cotation.getProductAndVarietyValueObject()
						.setName(StringUtils.trim(cotationString.substring(0, charPositionOfMinimunPrice)));
				cotationString = StringUtils.trim(cotationString.substring(charPositionOfMinimunPrice, cotationString.length()));
				break;
			}
		}
		// ### MINIMUN PRICE
		int firstSpacePosition = 0;
		for (int i = 0; i < cotationString.length(); i++) {
			if (StringUtils.equals(" ", String.valueOf(cotationString.charAt(i)))) {
				firstSpacePosition = i;
				cotation.getPriceValueObject().setMinimunPrice(StringUtils.trim(cotationString.substring(0, firstSpacePosition)));
				cotationString = StringUtils.trim(cotationString.substring(firstSpacePosition, cotationString.length()));
				break;
			}
		}
		// ### COMMON PRICE PER KG
		firstSpacePosition = 0;
		for (int i = cotationString.length() - 1; i >= 0; i--) {
			if (StringUtils.equals(" ", String.valueOf(cotationString.charAt(i)))) {
				firstSpacePosition = i;
				cotation.getPriceValueObject().setCommonPricePerKg(
						StringUtils.trim(cotationString.substring(firstSpacePosition, cotationString.length())));
				cotationString = StringUtils.trim(cotationString.substring(0, firstSpacePosition));
				break;
			}
		}
		// ### ORIGIN
		firstSpacePosition = 0;
		for (int i = cotationString.length() - 1; i >= 0; i--) {
			if (StringUtils.equals(" ", String.valueOf(cotationString.charAt(i)))) {
				firstSpacePosition = i;
				cotation.setOrigin(StringUtils.trim(cotationString.substring(firstSpacePosition, cotationString.length())));
				cotationString = StringUtils.trim(cotationString.substring(0, firstSpacePosition));
				break;
			}
		}
		// ### TYPE
		for (String type : TypeList.getValues()) {
			if (cotationString.contains(type)) {
				cotationString = cotationString.replace(type, "");
				cotation.setType(type);
			}
		}
		if (cotation.getType() == null) {
			logger.error("Type not include in TypeList: " + cotationString);
		}
		// ### PACKING
		for (String packing : PackagingList.getValues()) {
			if (cotationString.contains(packing)) {
				cotationString = cotationString.replace(packing, "");
				cotation.setPackaging(packing);
			}
		}
		if (cotation.getPackaging() == null) {
			logger.error("Packaging not include in TypeList: " + cotationString);
		}

		// ### MAXÍMUM PRICE
		for (int i = 0; i < cotationString.length(); i++) {
			if (StringUtils.equals(",", String.valueOf(cotationString.charAt(i)))) {
				cotation.getPriceValueObject().setMaximunPrice(cotationString.substring(0, i + 3));
				cotationString = cotationString.substring(i + 3);
				break;
			}
		}
		// ### COMMON WEIGHT
		for (int i = 0; i < cotationString.length(); i++) {
			if (StringUtils.equals(" ", String.valueOf(cotationString.charAt(i)))) {
				cotation.setCommonWeight(cotationString.substring(0, i));
				cotationString = StringUtils.trim(cotationString.substring(i));
				break;
			}
		}
		// ### COMMON PRICE
		for (int i = 0; i < cotationString.length(); i++) {
			if (StringUtils.equals(",", String.valueOf(cotationString.charAt(i)))) {
				cotation.getPriceValueObject().setCommonPrice(cotationString.substring(0, i + 3));
				cotationString = cotationString.substring(i + 3);
				break;
			}
		}
		// ### CLASSIFICATION
		if (StringUtils.isNotBlank(cotationString)) {
			cotation.setClassification(cotationString);
		}
		return cotation;
	}

}
