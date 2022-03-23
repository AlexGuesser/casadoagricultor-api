package online.guessersoftware.casadoagricultorapi.webservice.transformer;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.model.Price;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.PriceValueObject;

public class PriceTransformer {

	public static Price transformVOToModel(PriceValueObject priceVO) {
		Price price = new Price();
		price.setMinimum(new BigDecimal(StringUtils.replace(priceVO.getMinimunPrice(), ",", ".")));
		price.setCommon(new BigDecimal(StringUtils.replace(priceVO.getCommonPrice(), ",", ".")));
		price.setMaximum(new BigDecimal(StringUtils.replace(priceVO.getMaximunPrice(), ",", ".")));
		price.setKgCommon(new BigDecimal(StringUtils.replace(priceVO.getCommonPricePerKg(), ",", ".")));
		return price;
	}

}
