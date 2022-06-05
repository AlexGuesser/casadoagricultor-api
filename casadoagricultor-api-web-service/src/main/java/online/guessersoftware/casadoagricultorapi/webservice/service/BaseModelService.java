package online.guessersoftware.casadoagricultorapi.webservice.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.model.BaseModel;

@Service
public class BaseModelService {

	@Autowired
	private UserService userService;

	public <T extends BaseModel> T setMetaInfo(T baseModel) {
		baseModel.setLastUser(userService.getUserByName(Constants.TECH_JOB_PROCESSOR_NAME).getId());
		baseModel.setLastOperation(LocalDateTime.now());
		return baseModel;
	}

}
