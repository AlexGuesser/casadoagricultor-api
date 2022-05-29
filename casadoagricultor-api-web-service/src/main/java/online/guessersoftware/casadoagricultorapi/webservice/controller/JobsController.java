package online.guessersoftware.casadoagricultorapi.webservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/jobs")
public class JobsController {

	private final Logger log = LogManager.getLogger(getClass());

	@RequestMapping(method = RequestMethod.GET, path = "/test")
	@ResponseBody
	public ResponseEntity<String> runTestJob() {
		try {
			log.info("Running test job");
			return new ResponseEntity<String>("All good!", HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception while running test job. Exception: " + e.getMessage());
			return new ResponseEntity<String>("Error while running test job!", HttpStatus.BAD_REQUEST);
		}
	}

}
