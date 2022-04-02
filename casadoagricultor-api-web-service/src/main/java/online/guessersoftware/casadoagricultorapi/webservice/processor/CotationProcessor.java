package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.Mail;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.MailService;
import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PDFMessagesToNotParse;
import online.guessersoftware.casadoagricultorapi.webservice.constants.PackagingListEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.TypeListEnum;
import online.guessersoftware.casadoagricultorapi.webservice.model.CotationFile;
import online.guessersoftware.casadoagricultorapi.webservice.model.ProcessingErrorsWarningsEnum;
import online.guessersoftware.casadoagricultorapi.webservice.service.CotationFileService;
import online.guessersoftware.casadoagricultorapi.webservice.service.CotationService;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

@Service
public class CotationProcessor {

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private CotationService cotationService;

	@Autowired
	private MailService mailService;

	@Autowired
	private CotationFileService cotationFileService;

//	public void processByUrl(String urlString, CeasasEnum ceasa) {
//		ProcessResult processResult = new ProcessResult();
//		try {
//			PDDocument document = createPDDocumentByUrl(urlString);
//			processResult = processPDDocument(document, ceasa, processResult);
//			// cotationService.saveCotationsValueObject(cotationsValueObject);
//		} catch (Exception e) {
//			log.error(e);
//		}
//	}

	public void processLocalFile(ProcessLocalCotationFileRequest request) {
		ProcessResult processResult = new ProcessResult();
		try {
			PDDocument document = createPDDocumentByLocalFile(request.getFileFullPath());
			processResult = processPDDocument(document, request.getCeasa(), processResult);
			if (processResult.hadSomeError()) {
				cotationFileService.saveFileProcessedWithError(request, processResult.getErrorsAndWarnings());
			} else {
				CotationFile cotationFile = cotationFileService.saveFileProcessedWithSuccess(request, processResult.getErrorsAndWarnings());
				cotationService.saveCotationsValueObject(processResult.getCotationsVO(), cotationFile);
			}
		} catch (Exception e) {
			if (e instanceof IOException) {
				logAndSaveError(request, processResult, "File not found? Exception: " + e.getMessage());
			}
			logAndSaveError(request, processResult, "Some exception while processing request: " + request + ". Exception: " + e.getMessage());
		}
		mailService.sendEmailToProcessingAdmin(createProcessingMail(request, processResult));
	}

	private void logAndSaveError(ProcessLocalCotationFileRequest request, ProcessResult processResult, String errorLog) {
		log.error(errorLog);
		processResult.addErrorOrWarning(ProcessingErrorsWarningsEnum.FILE_NOT_FOUND_ERROR);
		processResult.addLogError(errorLog);
		cotationFileService.saveFileProcessedWithError(request, processResult.getErrorsAndWarnings());
	}

	private Mail createProcessingMail(ProcessLocalCotationFileRequest request, ProcessResult processResult) {
		Mail mail = Mail.build() //
				.sender(Constants.MAIL_DEFAULT_SENDER) //
				.recipients(Constants.MAIL_PROCESSING_ADMIN_RECEIVER) //
				.subject(buildSubjectForProcessingMail(request)) //
				.content(buildContentForProcessingMail(request, processResult), true); //
		return mail;
	}

	private String buildContentForProcessingMail(ProcessLocalCotationFileRequest request, ProcessResult processResult) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<hr>");
		stringBuilder.append("<h2> File:" + request.getFileFullPath() + "</h2>");
		stringBuilder.append("<h2> Date:" + request.getDate() + "</h2>");

		if (processResult.hadSomeError()) {
			stringBuilder.append("<h3> The file processing had errors. No cotation were inserted into the database.</h3>");
			stringBuilder.append("<h3> Errors and Warnings encountered: </h3>");
			for (String errorOrWarning : processResult.getLogErrors()) {
				stringBuilder.append("<h5>" + errorOrWarning + "</h5>");
			}
		} else {
			stringBuilder.append("<h3> The file processing were successfully." + processResult.getCotationsVO().size()
					+ "  cotations were inserted into the database.</h3>");
		}
		return stringBuilder.toString();
	}

	private String buildSubjectForProcessingMail(ProcessLocalCotationFileRequest request) {
		return Constants.MAIL_PROCESSING_SUBJECT_BASE + request.getCeasa().getName() + " - " + request.getDate();
	}

	private PDDocument createPDDocumentByLocalFile(String fileFullPath) throws IOException {
		return PDDocument.load(new File(fileFullPath));
	}

	public ProcessResult processPDDocument(PDDocument document, CeasasEnum ceasa, ProcessResult result) throws IOException {

		String[] lines = getLinesOfPDDocument(document);
		List<String> linesWithCotations = excludeNotCotationsLines(lines);
		String dateOfCotations = getDateOfCotations(linesWithCotations);
		linesWithCotations = makeAdjustsAndfiltersOnCotationLines(linesWithCotations);

		// ########## COTATIONS #######
		List<String> linesWithCotationsAdjusted = new ArrayList<String>();

		adjustAndInsertCotationsLines(linesWithCotations, linesWithCotationsAdjusted);
		linesWithCotationsAdjusted.forEach(cotation -> {
			CotationValueObject cotationValueObject = parseAndCreateCotation(ceasa, cotation, dateOfCotations, result);
			if (cotationValueObject != null) {
				result.addCotationVO(cotationValueObject);
			}
		});
		document.close();
		return result;
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
		List<String> linesFiltered = linesWithCotations.stream().filter(cotation -> (cotation.length() <= 17 && !StringUtils.equals(cotation, "Escovada")))
				.collect(Collectors.toList());
		log.info("Filtering the following lines: ");
		linesFiltered.forEach(lf -> log.info(lf));

		linesWithCotations = linesWithCotations.stream().filter(cotation -> (cotation.length() > 17 || StringUtils.equals(cotation, "Escovada")))
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

	@SuppressWarnings("unused")
	private PDDocument createPDDocumentByUrl(String urlString) throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		BufferedInputStream fileParse = new BufferedInputStream(is);
		PDDocument document = PDDocument.load(fileParse);
		return document;
	}

	private CotationValueObject parseAndCreateCotation(CeasasEnum ceasa, String cotationString, String dateOfCotation, ProcessResult result) {
		CotationValueObject cotation = new CotationValueObject();
		cotation.setCeasaName(ceasa);
		try {
			cotation.setFromDay(LocalDate.parse(dateOfCotation, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		} catch (DateTimeParseException e) {
			String errorLog = "Error while parsing date. Date: " + dateOfCotation + ". Exception: " + e.getMessage();
			log.error(errorLog);
			result.addErrorOrWarning(ProcessingErrorsWarningsEnum.PARSING_DATE_COTATION_ERROR);
			result.addLogError(errorLog);
			return null;
		}
		// ### PRODUCT AND VARIETY
		int charPositionOfMinimunPrice = 0;
		for (int i = 0; i < cotationString.length(); i++) {
			if (Constants.NUMBERS.contains(String.valueOf(cotationString.charAt(i)))) {
				charPositionOfMinimunPrice = i;
				cotation.getProductAndVarietyValueObject().setName(StringUtils.trim(cotationString.substring(0, charPositionOfMinimunPrice)));
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
				cotation.getPriceValueObject().setCommonPricePerKg(StringUtils.trim(cotationString.substring(firstSpacePosition, cotationString.length())));
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
		for (String type : TypeListEnum.getValues()) {
			if (cotationString.contains(type)) {
				cotationString = cotationString.replace(type, "");
				cotation.setType(type);
			}
		}
		if (cotation.getType() == null) {
			String errorLog = "Type not include in TypeList: " + cotationString;
			log.error(errorLog);
			result.addErrorOrWarning(ProcessingErrorsWarningsEnum.TYPE_NOT_IN_ENUM_ERROR);
			result.addLogError(errorLog);
			return null;
		}
		// ### PACKING
		for (String packing : PackagingListEnum.getValues()) {
			if (cotationString.contains(packing)) {
				cotationString = cotationString.replace(packing, "");
				cotation.setPackaging(packing);
			}
		}
		if (cotation.getPackaging() == null) {
			String errorLog = "Packaging not include in TypeList: " + cotationString;
			log.error(errorLog);
			result.addErrorOrWarning(ProcessingErrorsWarningsEnum.PACKING_NOT_IN_ENUM_ERROR);
			result.addLogError(errorLog);
			return null;
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
