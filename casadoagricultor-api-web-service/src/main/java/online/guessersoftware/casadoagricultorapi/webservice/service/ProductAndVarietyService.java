package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.Mail;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.MailService;
import online.guessersoftware.casadoagricultorapi.webservice.model.ProductAndVariety;
import online.guessersoftware.casadoagricultorapi.webservice.repository.ProductAndVarietyRepository;

@Service
public class ProductAndVarietyService {

	@Autowired
	private MailService mailService;

	@Autowired
	private ProductAndVarietyRepository productAndVarietyRepository;

	public ProductAndVariety getProductAndVarietyByName(String name) {
		name = StringUtils.normalizeSpace(name);
		return productAndVarietyRepository.findByName(name);
	}

	public ProductAndVariety getProductAndVarietyByNameOrName2OrName3(String name) {
		name = StringUtils.normalizeSpace(name);
		return productAndVarietyRepository.findBySomeName(name);
	}

	public ProductAndVariety createProductAndVariety(ProductAndVariety newProductAndVariety) {
		mailService.sendEmailToProcessingAdmin(Mail.build() //
				.sender(Constants.MAIL_DEFAULT_SENDER) //
				.recipients(Constants.MAIL_PROCESSING_ADMIN_RECEIVER) //
				.subject("CASA DO AGRICULTOR - NEW PRODUCT AND VARIETY") //
				.content("NEW PRODUCT INSERTED INTO DATABASE: " + newProductAndVariety, false));//
		return productAndVarietyRepository.save(newProductAndVariety);
	}

}
