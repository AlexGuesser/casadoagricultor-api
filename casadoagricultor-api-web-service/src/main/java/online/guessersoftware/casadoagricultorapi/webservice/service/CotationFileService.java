package online.guessersoftware.casadoagricultorapi.webservice.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.model.CotationFile;
import online.guessersoftware.casadoagricultorapi.webservice.model.ProcessingErrorsWarningsEnum;
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessCotationFileRequest;
import online.guessersoftware.casadoagricultorapi.webservice.repository.CotationFileRepository;

@Service
public class CotationFileService {

	private final Logger log = LogManager.getLogger(getClass());

	@Autowired
	private CotationFileRepository cotationFileRepository;

	@Autowired
	private CeasaService ceasaService;

	@Autowired
	private BaseModelService baseModelService;

	@Autowired
	private ProcessingErrorsWarningsService errorsWarningsService;

	public void saveFileProcessedWithError(ProcessCotationFileRequest request, List<ProcessingErrorsWarningsEnum> errorsList) {
		saveFileProcessed(request, false, errorsList);
	}

	public CotationFile saveFileProcessedWithSuccess(ProcessCotationFileRequest request, List<ProcessingErrorsWarningsEnum> errorsList) {
		return saveFileProcessed(request, true, errorsList);
	}

	private CotationFile saveFileProcessed(ProcessCotationFileRequest request, boolean success, List<ProcessingErrorsWarningsEnum> errorsList) {
		CotationFile newCotationFile = new CotationFile();
		newCotationFile.setSuccessfullyProcessed(success);
		newCotationFile.setFilename(request.getFileName());
		newCotationFile.setFormat(Constants.PDF);
		newCotationFile.setUrl(request.getUrl());
		newCotationFile.setStorageReference(request.getFileFullPath());
		newCotationFile.setSavedLocallyOrCloud(request.isLocal() ? Constants.LOCAL : Constants.CLOUD);
		newCotationFile.setCeasa(ceasaService.getCeasaByName(request.getCeasa().getName()));
		newCotationFile = baseModelService.setLastUserAsTechJobProcessorUser(newCotationFile);
		newCotationFile.setErrorsAndWarnings(errorsWarningsService.transformEnumListToModelList(errorsList));
		log.info("Saving new cotationFile: " + newCotationFile);
		return cotationFileRepository.save(newCotationFile);
	}

	public boolean cotationFileAlreadyProcessedSuccessfullyBy(ProcessCotationFileRequest request) {
		List<CotationFile> cotationFilesAlreadyProcessedSuccessfully = cotationFileRepository.cotationFilesAlreadyProcessedSuccessfully( //
				request.getFileName(), //
				ceasaService.getCeasaByName(request.getCeasa().getName()).getId()); //
		return CollectionUtils.isNotEmpty(cotationFilesAlreadyProcessedSuccessfully);
	}

}
