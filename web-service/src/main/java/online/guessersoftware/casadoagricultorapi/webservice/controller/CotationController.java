package online.guessersoftware.casadoagricultorapi.webservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;
import online.guessersoftware.casadoagricultorapi.webservice.processor.CotationProcessor;

@Controller
@RequestMapping(path = "/cotation")
public class CotationController {

	private final Logger logger = LogManager.getLogger(getClass());

	@Autowired
	private CotationProcessor cotationProcessor;

	@RequestMapping(method = RequestMethod.GET, path = "/generate-cotations-for-ceasa-sc-by-url")
	@ResponseBody
	public void createCotationsOfCeasaSCBy(@RequestParam(required = true) String url) {
		cotationProcessor.processByUrl(url, CeasasEnum.SAO_JOSE_SC);
	}

}
