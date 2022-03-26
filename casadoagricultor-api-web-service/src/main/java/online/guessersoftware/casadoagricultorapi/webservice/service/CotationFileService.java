package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.repository.CotationFileRepository;

@Service
public class CotationFileService {

	@Autowired
	private CotationFileRepository cotationFileRepository;

}
