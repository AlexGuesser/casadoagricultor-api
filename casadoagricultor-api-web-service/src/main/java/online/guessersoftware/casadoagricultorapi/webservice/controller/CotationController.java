package online.guessersoftware.casadoagricultorapi.webservice.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.json.PdfDownloadRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.processor.CotationProcessor;
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

	@RequestMapping(method = RequestMethod.GET, path = "/download-pdf-cotations-from-ceasa-sc-to-local-machine")
	@ResponseBody
	public void downLoadPdfCotationsFromCeasaScToLocalMachine(@Valid @RequestBody(required = true) PdfDownloadRequestJson request) {
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

}
