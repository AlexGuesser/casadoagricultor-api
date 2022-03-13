package online.guessersoftware.casadoagricultorapi.webservice.transformer;

import org.springframework.util.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.model.Cotation;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

public class CotationTransformer {

	public static Cotation transformVOToModel(CotationValueObject cVO) {
		Cotation cotation = new Cotation();
		cotation.setFromDay(cVO.getFromDay());
		cotation.setClassification(cVO.getClassification());
		cotation.setType(cVO.getType());
		cotation.setOrigin(cVO.getOrigin());
		cotation.setPackaging(cVO.getPackaging());
		cotation.setCommunWeight(Float.valueOf(StringUtils.replace(cVO.getCommonWeight(), ",", ".")));
		cotation.setPrice(PriceTransformer.transformVOToModel(cVO.getPriceValueObject()));
		return cotation;
	}

}
