package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.model.BaseModel;

@Service
public class BaseModelService {

	@Autowired
	private UserService userService;

	public <T extends BaseModel> T setLastUserAsTechJobProcessorUser(T baseModel) {
		baseModel.setLastUser(userService.getUserByName(Constants.TECH_JOB_PROCESSOR_NAME).getId());
		return baseModel;
	}

}
