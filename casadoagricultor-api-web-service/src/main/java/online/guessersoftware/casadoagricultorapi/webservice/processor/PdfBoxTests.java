package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PDFMessagesToNotParseEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PackagingListEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.TypeListEnum;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

public class PdfBoxTests {

	public static void main(String[] args) {
		try {
			URL url = new URL("https://www.ceasa.sc.gov.br/index.php/cotacao-de-precos/2021/07-julho-7/1417-16-07-2021/file");
			InputStream is = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is);
			PDDocument document = PDDocument.load(fileParse);

			PDFTextStripper tStripper = new PDFTextStripper();
			String pdfFileInText = tStripper.getText(document);
			// split by line
			String lines[] = pdfFileInText.split("\\n");
			List<String> linesWithCotations = new ArrayList<String>();
			for (String line : lines) {
				if (PDFMessagesToNotParseEnum.contains(line)) {
					continue;
				}
				linesWithCotations.add(line);
			}
			String date = linesWithCotations.stream().filter(cotation -> cotation.matches(Constants.DATE_REGEX)).findFirst()
					.get();
			System.out.println("DATE: " + date);
			linesWithCotations = linesWithCotations.stream()
					.filter(cotation -> (cotation.length() > 17 || StringUtils.equals(cotation, "Escovada")))
					.collect(Collectors.toList());
			System.out.println("########## COTATIONS #######");
			List<CotationValueObject> cotations = new ArrayList<CotationValueObject>();
			List<String> linesWithCotationsAdjusted = new ArrayList<String>();
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
			linesWithCotationsAdjusted.forEach(cotation -> cotations.add(parseAndCreateCotation(cotation)));
			cotations.forEach(cotation -> System.out.println(cotation));
			document.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static CotationValueObject parseAndCreateCotation(String text) {
		CotationValueObject cotation = new CotationValueObject();
		// ### PRODUCT AND VARIETY
		int charPositionOfMinimunPrice = 0;
		for (int i = 0; i < text.length(); i++) {
			if (Constants.NUMBERS.contains(String.valueOf(text.charAt(i)))) {
				charPositionOfMinimunPrice = i;
				cotation.getProductAndVarietyValueObject()
						.setName(StringUtils.trim(text.substring(0, charPositionOfMinimunPrice)));
				text = StringUtils.trim(text.substring(charPositionOfMinimunPrice, text.length()));
				break;
			}
		}
		// ### MINIMUN PRICE
		int firstSpacePosition = 0;
		for (int i = 0; i < text.length(); i++) {
			if (StringUtils.equals(" ", String.valueOf(text.charAt(i)))) {
				firstSpacePosition = i;
				cotation.getPriceValueObject().setMinimunPrice(StringUtils.trim(text.substring(0, firstSpacePosition)));
				text = StringUtils.trim(text.substring(firstSpacePosition, text.length()));
				break;
			}
		}
		// ### COMMON PRICE PER KG
		firstSpacePosition = 0;
		for (int i = text.length() - 1; i >= 0; i--) {
			if (StringUtils.equals(" ", String.valueOf(text.charAt(i)))) {
				firstSpacePosition = i;
				cotation.getPriceValueObject()
						.setCommonPricePerKg(StringUtils.trim(text.substring(firstSpacePosition, text.length())));
				text = StringUtils.trim(text.substring(0, firstSpacePosition));
				break;
			}
		}
		// ### ORIGIN
		firstSpacePosition = 0;
		for (int i = text.length() - 1; i >= 0; i--) {
			if (StringUtils.equals(" ", String.valueOf(text.charAt(i)))) {
				firstSpacePosition = i;
				cotation.setOrigin(StringUtils.trim(text.substring(firstSpacePosition, text.length())));
				text = StringUtils.trim(text.substring(0, firstSpacePosition));
				break;
			}
		}
		// ### TYPE
		for (String type : TypeListEnum.getValues()) {
			if (text.contains(type)) {
				text = text.replace(type, "");
				cotation.setType(type);
			}
		}
		if (cotation.getType() == null) {
			System.out.println("Type not include in TypeList: " + text);
		}
		// ### PACKING
		for (String packing : PackagingListEnum.getValues()) {
			if (text.contains(packing)) {
				text = text.replace(packing, "");
				cotation.setPackaging(packing);
			}
		}
		if (cotation.getPackaging() == null) {
			System.out.println("Packaging not include in TypeList: " + text);
		}

		// ### MAXÍMUM PRICE
		for (int i = 0; i < text.length(); i++) {
			if (StringUtils.equals(",", String.valueOf(text.charAt(i)))) {
				cotation.getPriceValueObject().setMaximunPrice(text.substring(0, i + 3));
				text = text.substring(i + 3);
				break;
			}
		}
		// ### COMMON WEIGHT
		for (int i = 0; i < text.length(); i++) {
			if (StringUtils.equals(" ", String.valueOf(text.charAt(i)))) {
				cotation.setCommonWeight(text.substring(0, i));
				text = StringUtils.trim(text.substring(i));
				break;
			}
		}
		// ### COMMON PRICE
		for (int i = 0; i < text.length(); i++) {
			if (StringUtils.equals(",", String.valueOf(text.charAt(i)))) {
				cotation.getPriceValueObject().setCommonPrice(text.substring(0, i + 3));
				text = text.substring(i + 3);
				break;
			}
		}
		// ### CLASSIFICATION
		if (StringUtils.isNotBlank(text)) {
			cotation.setClassification(text);
		}
		return cotation;
	}

}
