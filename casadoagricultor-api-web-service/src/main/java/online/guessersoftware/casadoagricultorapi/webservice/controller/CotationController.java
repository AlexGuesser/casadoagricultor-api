package online.guessersoftware.casadoagricultorapi.webservice.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.json.PdfDownloadRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.json.ProcessLocalCotationFileRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.processor.CotationProcessor;
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessLocalCotationFileRequest;
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessLocalCotationFileRequestBuilder;
import online.guessersoftware.casadoagricultorapi.webservice.utils.CotationsDownloadRequest;
import online.guessersoftware.casadoagricultorapi.webservice.utils.CotationsDownloadRequestBuilder;
import online.guessersoftware.casadoagricultorapi.webservice.utils.DownloadUtils;

@Controller
@RequestMapping(path = "/cotation")
public class CotationController {

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private CotationProcessor cotationProcessor;

	@RequestMapping(method = RequestMethod.GET, path = "/generate-cotations-for-ceasa-sc-by-url")
	@ResponseBody
	public void createCotationsOfCeasaSCBy(@RequestParam(required = true) String url) {
		cotationProcessor.processByUrl(url, CeasasEnum.SAO_JOSE_SC);
	}

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
			DownloadUtils downloader = new DownloadUtils();
			downloader.downloadCotationsAndSavesOnLocalMachine(downloadRequest);
			currentDate = currentDate.plusDays(1);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/process--local-cotations-file")
	@ResponseBody
	public ResponseEntity<String> processLocalCotationFileBy(@Valid @RequestBody(required = true) ProcessLocalCotationFileRequestJson request) {
		log.info("Trying to proccess all ceasa sc pdfs using as request: " + request.toString());
		LocalDate currentDate = LocalDate.parse(request.getFromDay());
		LocalDate limitDate = LocalDate.parse(request.getToDay());
		String basePath = StringUtils.isBlank(request.getBaseFolderPath()) ? Constants.DEFAULT_DESTINY_FOLDER : request.getBaseFolderPath();
		while (currentDate.isBefore(limitDate) || currentDate.isEqual(limitDate)) {
			String fullpath = basePath //
					+ (currentDate.getYear() == 2018 ? "2018-1" : String.valueOf(currentDate.getYear())) //
					+ currentDate.toString() //
					+ Constants.DOT + Constants.PDF; //
			ProcessLocalCotationFileRequest cotationFileRequest = //
					ProcessLocalCotationFileRequestBuilder //
							.usingThis() //
							.fileFullPath(fullpath) //
							.date(currentDate) //
							.ceasa(CeasasEnum.SAO_JOSE_SC) //
							.build(); //
			cotationProcessor.processLocalFile(cotationFileRequest);
			currentDate = currentDate.plusDays(1);
		}
		return new ResponseEntity<String>("All good!", HttpStatus.OK);
	}

}
