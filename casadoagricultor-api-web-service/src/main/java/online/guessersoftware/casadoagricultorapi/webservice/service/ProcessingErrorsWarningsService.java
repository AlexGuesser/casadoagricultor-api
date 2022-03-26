package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.repository.ProcessingErrorsWarningsRepository;

@Service
public class ProcessingErrorsWarningsService {

	@Autowired
	private ProcessingErrorsWarningsRepository processingErrorsWarningsRepository;

}
