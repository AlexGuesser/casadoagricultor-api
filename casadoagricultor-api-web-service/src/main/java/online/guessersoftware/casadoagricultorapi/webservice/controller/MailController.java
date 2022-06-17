package online.guessersoftware.casadoagricultorapi.webservice.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.MailService;
import online.guessersoftware.casadoagricultorapi.webservice.json.MailRequestJson;
import online.guessersoftware.casadoagricultorapi.webservice.transformer.MailTransformer;

@Controller
@RequestMapping(path = "/mail")
public class MailController {

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private MailService mailService;

	@RequestMapping(method = RequestMethod.GET, path = "/send-test-mail")
	@ResponseBody
	public ResponseEntity<String> sendTestMail() {
		try {
			if (mailService.sendTestMail()) {
				return new ResponseEntity<String>("Mail test sent successfully", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Mail test sent unsuccessfully", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("Exception occurred while sending mail test!. Exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/send-mail-by-request")
	@ResponseBody
	public ResponseEntity<String> sendMailByRequest(@Valid @NotNull @RequestBody(required = true) MailRequestJson mailRequestJson) {
		try {
			if (mailService.sendEmailByRequest(MailTransformer.transformToMail(mailRequestJson))) {
				return new ResponseEntity<String>("Mail sent successfully", HttpStatus.OK);
			}
			return new ResponseEntity<String>("Mail sent unsuccessfully", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("Exception occurred while sending mail!. Exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
