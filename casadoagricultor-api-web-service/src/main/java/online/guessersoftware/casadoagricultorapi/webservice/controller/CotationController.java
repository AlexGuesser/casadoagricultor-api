package online.guessersoftware.casadoagricultorapi.webservice.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.Mail;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.MailService;
import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.json.PdfDownloadRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.json.ProcessLocalCotationFileRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.processor.CotationProcessor;
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessCotationFileRequest;
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessCotationFileRequestBuilder;
import online.guessersoftware.casadoagricultorapi.webservice.service.CotationFileService;
import online.guessersoftware.casadoagricultorapi.webservice.service.CotationService;
import online.guessersoftware.casadoagricultorapi.webservice.utils.CotationsDownloadRequest;
import online.guessersoftware.casadoagricultorapi.webservice.utils.CotationsDownloadRequestBuilder;
import online.guessersoftware.casadoagricultorapi.webservice.utils.DownloadUtils;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

@Controller
@RequestMapping(path = "/cotation")
public class CotationController {

	private static final String CEASA_SC_COTATIONS_BASE_URL = "https://www.ceasa.sc.gov.br/index.php/cotacao-de-precos/";

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private CotationProcessor cotationProcessor;

	@Autowired
	private CotationFileService cotationFileService;

	@Autowired
	private CotationService cotationService;

	@Autowired
	private DownloadUtils downloader;

	@Autowired
	private MailService mailService;

	@RequestMapping(method = RequestMethod.GET, path = "")
	@ResponseBody
	public ResponseEntity<List<CotationValueObject>> getCotations(@RequestParam(required = true) String day, @RequestParam(required = false) String ceasaName,
			@RequestParam(required = true) int page, @RequestParam(required = true) int size) {
		LocalDate dayLD = LocalDate.parse(day);
		String ceasa = StringUtils.isBlank(ceasaName) ? CeasasEnum.SAO_JOSE_SC.getName() : ceasaName;
		List<CotationValueObject> cotations = cotationService.getContationsBy(dayLD, ceasa, page, size);
		return new ResponseEntity<List<CotationValueObject>>(cotations, HttpStatus.OK);
	}

//	@RequestMapping(method = RequestMethod.GET, path = "/generate-cotations-for-ceasa-sc-by-url")
//	@ResponseBody
//	public void createCotationsOfCeasaSCBy(@RequestParam(required = true) String url) {
//		cotationProcessor.processByUrl(url, CeasasEnum.SAO_JOSE_SC);
//	}

	@RequestMapping(method = RequestMethod.POST, path = "/download-pdf-cotations-from-ceasa-sc-to-local-machine")
	@ResponseBody
	public void downloadPdfCotationsFromCeasaScToLocalMachine(@Valid @RequestBody(required = true) PdfDownloadRequestJson request) {
		log.info("Trying to download all ceasa sc pdfs using as request: " + request.toString());
		LocalDate currentDate = LocalDate.parse(request.getFromDay());
		LocalDate limitDate = LocalDate.parse(request.getToDay());
		while (currentDate.isBefore(limitDate) || currentDate.isEqual(limitDate)) {
			CotationsDownloadRequest downloadRequest = //
					CotationsDownloadRequestBuilder //
							.usingThis() //
							// For some reason, ceasa uses 2018-1 instead of 2018
							.usingDayComplete(currentDate.toString(), true, "year", "2018", "2018-1") //
							.baseUrl(request.getBaseUrl()) //
							.destinyFolder(request.getDestinyFolder(), true) //
							.build(); //
			downloader.downloadCotationsAndSavesOnLocalMachine(downloadRequest);
			currentDate = currentDate.plusDays(1);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/process-local-cotations-file")
	@ResponseBody
	public ResponseEntity<String> processLocalCotationFileBy(@Valid @RequestBody(required = true) ProcessLocalCotationFileRequestJson request) {
		log.info("Trying to proccess all ceasa sc pdfs using as request: " + request.toString());
		LocalDate currentDate = LocalDate.parse(request.getFromDay());
		LocalDate limitDate = LocalDate.parse(request.getToDay());
		String basePath = StringUtils.isBlank(request.getBaseFolderPath()) ? Constants.DEFAULT_DESTINY_FOLDER : request.getBaseFolderPath();
		while (currentDate.isBefore(limitDate) || currentDate.isEqual(limitDate)) {
			String fullpath = concatenateFullPath(currentDate, basePath); //
			ProcessCotationFileRequest cotationFileRequest = //
					ProcessCotationFileRequestBuilder //
							.usingThis() //
							.fileName(concatenateFileName(currentDate)) //
							.fileFullPath(fullpath) //
							.url(fullpath) //
							.date(currentDate) //
							.ceasa(CeasasEnum.SAO_JOSE_SC) //
							.local(true) //
							.build(); //
			if (cotationFileService.cotationFileAlreadyProcessedSuccessfullyBy(cotationFileRequest)) {
				log.info("Cotation already successfully processed by request: " + request.toString());
				currentDate = currentDate.plusDays(1);
				continue;
			}
			cotationProcessor.processLocalFile(cotationFileRequest);
			currentDate = currentDate.plusDays(1);
		}
		return new ResponseEntity<String>("All good!", HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get-today-cotations")
	@ResponseBody
	public ResponseEntity<String> uploadTodayCotationFileToGoogleAndProcess() throws IOException {
		LocalDate today = LocalDate.now();
		log.info("Trying to download ceasa sc pdf: " + today.toString());
		CotationsDownloadRequest downloadRequest = //
				CotationsDownloadRequestBuilder //
						.usingThis() //
						// For some reason, ceasa uses 2018-1 instead of 2018
						.usingDayComplete(today.toString(), true, "year", "2018", "2018-1") //
						.baseUrl(CEASA_SC_COTATIONS_BASE_URL) //
						.destinyFolder("", true) //
						.build(); //
		PDDocument pdf = downloader.downloadCotationsUploadToGoogle(downloadRequest);
		// NO COTATIONS FOUND FOR THIS SPECIFIC DAY( WEEKEND? HOLIDAYS? )
		if (pdf == null) {
			mailService.sendEmailToProcessingAdmin(createNoCotationFoundMail(today.toString()));
		}
		String fullpath = concatenateFullPath(today, Constants.DEFAULT_BUCKET_FOLDER); //
		ProcessCotationFileRequest cotationFileRequest = //
				ProcessCotationFileRequestBuilder //
						.usingThis() //
						.fileName(concatenateFileName(today)) //
						.fileFullPath(fullpath) //
						.url(fullpath) //
						.date(today) //
						.ceasa(CeasasEnum.SAO_JOSE_SC) //
						.local(false) //
						.build(); //
		if (cotationFileService.cotationFileAlreadyProcessedSuccessfullyBy(cotationFileRequest)) {
			log.info("Cotation already successfully processed by request: " + cotationFileRequest.toString());
		} else {
			cotationProcessor.processFile(pdf, cotationFileRequest);
		}
		return new ResponseEntity<String>("All good!", HttpStatus.OK);
	}

	private String concatenateFullPath(LocalDate currentDate, String basePath) {
		return basePath //
				+ (currentDate.getYear() == 2018 ? "2018-1" : String.valueOf(currentDate.getYear())) //
				+ "/" //
				+ currentDate.toString() //
				+ Constants.DOT + Constants.PDF;
	}

	private String concatenateFileName(LocalDate currentDate) {
		return currentDate.toString() //
				+ Constants.DOT + Constants.PDF;
	}

	private Mail createNoCotationFoundMail(String day) {
		Mail mail = Mail.build() //
				.sender(Constants.MAIL_DEFAULT_SENDER) //
				.recipients(Constants.MAIL_PROCESSING_ADMIN_RECEIVER) //
				.subject(buildSubjectForNoCotationFoundMail()) //
				.content(buildContentForNoCotationFoundMail(day), true); //
		return mail;
	}

	private String buildContentForNoCotationFoundMail(String day) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<hr>");
		stringBuilder.append("<h2> No cotation was found for day: " + day + "</h2>");

		return stringBuilder.toString();
	}

	private String buildSubjectForNoCotationFoundMail() {
		return Constants.MAIL_PROCESSING_SUBJECT_BASE_NO_COTATION;
	}

	public static void main(String[] args) {
		System.out.println(LocalDate.now());
	}
}
