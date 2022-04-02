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

	public static PriceValueObject transformModelToVO(Price price) {
		PriceValueObject pVO = new PriceValueObject();
		pVO.setMinimunPrice(StringUtils.replace(String.valueOf(price.getMinimum()), ".", ","));
		pVO.setCommonPrice(StringUtils.replace(String.valueOf(price.getCommon()), ".", ","));
		pVO.setMaximunPrice(StringUtils.replace(String.valueOf(price.getMaximum()), ".", ","));
		pVO.setCommonPricePerKg(StringUtils.replace(String.valueOf(price.getKgCommon()), ".", ","));
		return pVO;
	}

}
