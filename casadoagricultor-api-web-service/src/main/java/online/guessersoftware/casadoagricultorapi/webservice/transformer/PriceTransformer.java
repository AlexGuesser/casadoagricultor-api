package online.guessersoftware.casadoagricultorapi.webservice.transformer;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.model.Price;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.PriceValueObject;

public class PriceTransformer {

	public static Price transformVOToModel(PriceValueObject priceVO) {
		Price price = new Price();
		// BIG DECIMAL NEEDS . AS SEPARATOR
		price.setMinimum(new BigDecimal(adjustPriceToUseDot(priceVO.getMinimunPrice())));
		price.setCommon(new BigDecimal(adjustPriceToUseDot(priceVO.getCommonPrice())));
		price.setMaximum(new BigDecimal(adjustPriceToUseDot(priceVO.getMaximunPrice())));
		price.setKgCommon(new BigDecimal(adjustPriceToUseDot(priceVO.getCommonPricePerKg())));
		return price;
	}

	public static PriceValueObject transformModelToVO(Price price) {
		PriceValueObject pVO = new PriceValueObject();
		pVO.setMinimunPrice(adjustPriceToUseComma(price.getMinimum()));
		pVO.setCommonPrice(adjustPriceToUseComma(price.getCommon()));
		pVO.setMaximunPrice(adjustPriceToUseComma(price.getMaximum()));
		pVO.setCommonPricePerKg(adjustPriceToUseComma(price.getKgCommon()));
		return pVO;
	}

	private static String adjustPriceToUseComma(BigDecimal price) {
		return StringUtils.replace(String.valueOf(price), ".", ",");
	}

	private static String adjustPriceToUseDot(String price) {
		return StringUtils.replace(String.valueOf(price), ",", ".");
	}

}
