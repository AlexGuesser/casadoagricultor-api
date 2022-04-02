package online.guessersoftware.casadoagricultorapi.webservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.model.ProcessingErrorsWarnings;
import online.guessersoftware.casadoagricultorapi.webservice.model.ProcessingErrorsWarningsEnum;
import online.guessersoftware.casadoagricultorapi.webservice.repository.ProcessingErrorsWarningsRepository;

@Service
public class ProcessingErrorsWarningsService {

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private ProcessingErrorsWarningsRepository processingErrorsWarningsRepository;

	public Set<ProcessingErrorsWarnings> transformEnumListToModelList(List<ProcessingErrorsWarningsEnum> enums) {
		Set<ProcessingErrorsWarnings> modelSet = new HashSet<ProcessingErrorsWarnings>();
		for (ProcessingErrorsWarningsEnum errorEnum : enums) {
			ProcessingErrorsWarnings model = findByCode(errorEnum.name());
			if (model != null) {
				modelSet.add(model);
			} else {
				log.error("Processing error or warning not found on database for code: " + errorEnum.name());
			}
		}
		return modelSet;
	}

	public ProcessingErrorsWarnings findByCode(String code) {
		return processingErrorsWarningsRepository.findByCode(code);
	}

}
