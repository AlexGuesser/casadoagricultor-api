package online.guessersoftware.casadoagricultorapi.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import online.guessersoftware.casadoagricultorapi.webservice.service.StorageService;

@Controller
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	@ResponseBody
	public ResponseEntity<String> testStorageService() {
		storageService.testStorage();
		return new ResponseEntity<String>("OK", HttpStatus.OK);

	}

}
