package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.model.ProductAndVariety;
import online.guessersoftware.casadoagricultorapi.webservice.repository.ProductAndVarietyRepository;

@Service
public class ProductAndVarietyService {

	@Autowired
	private ProductAndVarietyRepository productAndVarietyRepository;

	public ProductAndVariety getProductAndVarietyByName(String name) {
		return productAndVarietyRepository.findByName(name);
	}

	public ProductAndVariety createProductAndVariety(ProductAndVariety newProductAndVariety) {
		return productAndVarietyRepository.save(newProductAndVariety);
	}

}
