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
import online.guessersoftware.casadoagricultorapi.webservice.processor.ProcessLocalCotationFileRequest;
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

	public void saveFileProcessedWithError(ProcessLocalCotationFileRequest request, List<ProcessingErrorsWarningsEnum> errorsList) {
		saveFileProcessed(request, false, errorsList);
	}

	public CotationFile saveFileProcessedWithSuccess(ProcessLocalCotationFileRequest request, List<ProcessingErrorsWarningsEnum> errorsList) {
		return saveFileProcessed(request, true, errorsList);
	}

	private CotationFile saveFileProcessed(ProcessLocalCotationFileRequest request, boolean success, List<ProcessingErrorsWarningsEnum> errorsList) {
		CotationFile newCotationFile = new CotationFile();
		newCotationFile.setSuccessfullyProcessed(success);
		newCotationFile.setFilename(request.getFileFullPath());
		newCotationFile.setFormat(Constants.PDF);
		newCotationFile.setUrl(request.getFileFullPath());
		newCotationFile.setStorageReference(request.getFileFullPath());
		newCotationFile.setSavedLocallyOrCloud(Constants.LOCAL);
		newCotationFile.setCeasa(ceasaService.getCeasaByName(request.getCeasa().getName()));
		newCotationFile = baseModelService.setLastUserAsTechJobProcessorUser(newCotationFile);
		newCotationFile.setErrorsAndWarnings(errorsWarningsService.transformEnumListToModelList(errorsList));
		log.info("Saving new cotationFile: " + newCotationFile);
		return cotationFileRepository.save(newCotationFile);
	}

	public boolean cotationFileAlreadyProcessedSuccessfullyBy(ProcessLocalCotationFileRequest request) {
		List<CotationFile> cotationFilesAlreadyProcessedSuccessfully = cotationFileRepository.cotationFilesAlreadyProcessedSuccessfully( //
				request.getFileFullPath(), //
				ceasaService.getCeasaByName(request.getCeasa().getName()).getId()); //
		return CollectionUtils.isNotEmpty(cotationFilesAlreadyProcessedSuccessfully);
	}

}
