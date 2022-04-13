package online.guessersoftware.casadoagricultorapi.webservice.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.model.Ceasa;
import online.guessersoftware.casadoagricultorapi.webservice.model.Cotation;
import online.guessersoftware.casadoagricultorapi.webservice.model.CotationFile;
import online.guessersoftware.casadoagricultorapi.webservice.model.ProductAndVariety;
import online.guessersoftware.casadoagricultorapi.webservice.repository.CotationRepository;
import online.guessersoftware.casadoagricultorapi.webservice.transformer.CotationTransformer;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

@Service
public class CotationService {

	@Autowired
	private BaseModelService baseModelService;

	@Autowired
	private ProductAndVarietyService productService;

	@Autowired
	private CeasaService ceasaService;

	@Autowired
	private CotationRepository cotationRepository;

	private final Logger logger = LogManager.getLogger(getClass());

	public void saveCotationsValueObject(List<CotationValueObject> cotationsValueObject, CotationFile cotationFile) {
		cotationsValueObject.stream().forEach(cVO -> saveCotationValueObject(cVO, cotationFile));
	}

	private void saveCotationValueObject(CotationValueObject cVO, CotationFile cotationFile) {
		Cotation newCotation = CotationTransformer.transformVOToModel(cVO);
		newCotation.setCotationFile(cotationFile);
		newCotation.setProductAndVariety(retrieveOrCreateProductAndVariety(cVO.getProductAndVarietyValueObject().getName()));
		newCotation.setPrice(baseModelService.setLastUserAsTechJobProcessorUser(newCotation.getPrice()));
		newCotation = baseModelService.setLastUserAsTechJobProcessorUser(newCotation);
		logger.info("Saving new cotation: " + newCotation);
		cotationRepository.save(newCotation);
	}

	private ProductAndVariety retrieveOrCreateProductAndVariety(String name) {
		name = StringUtils.normalizeSpace(name);
		ProductAndVariety product = productService.getProductAndVarietyByNameOrName2OrName3(name);
		if (product != null) {
			return product;
		}
		logger.info("Saving new product and variety: " + name);
		ProductAndVariety newProductAndVariety = new ProductAndVariety(name);
		newProductAndVariety = baseModelService.setLastUserAsTechJobProcessorUser(newProductAndVariety);
		return productService.createProductAndVariety(newProductAndVariety);
	}

	public List<CotationValueObject> getContationsBy(LocalDate day, String ceasaName, int page, int size) {
		Ceasa ceasa = ceasaService.getCeasaByName(ceasaName);
		return CotationTransformer.transformModelToVO( //
				cotationRepository.getCotationsBy(day, ceasa.getId(), PageRequest.of(page, size))); //
	}

}
