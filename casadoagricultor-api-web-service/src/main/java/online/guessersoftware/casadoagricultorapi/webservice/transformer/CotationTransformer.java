package online.guessersoftware.casadoagricultorapi.webservice.transformer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.model.Cotation;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.ProductAndVarietyValueObject;

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

	public static CotationValueObject transformModelToVO(Cotation c) {
		CotationValueObject cVO = new CotationValueObject();
		cVO.setFromDay(c.getFromDay());
		cVO.setClassification(c.getClassification());
		cVO.setType(adjustType(c.getType()));
		cVO.setOrigin(c.getOrigin());
		cVO.setPackaging(c.getPackaging());
		cVO.setCommonWeight(adjustCommonWeight(c.getCommunWeight()));
		cVO.setPriceValueObject(PriceTransformer.transformModelToVO(c.getPrice()));
		cVO.setProductAndVarietyValueObject(new ProductAndVarietyValueObject(c.getProductAndVariety().getName()));
		cVO.setCeasaName(c.getCotationFile().getCeasa().getName());
		return cVO;
	}

	private static String adjustCommonWeight(float commonWeight) {
		try {
			DecimalFormat formatter = new DecimalFormat("#,00");
			return formatter.format(commonWeight);
			// return StringUtils.replace(formatter.format(commonWeight), ".", ",");
		} catch (Exception e) {
			System.out.println("Exception when adjusting common weight: " + e.getMessage());
			return "0,00";
		}
	}

	private static String adjustType(String type) {
		if (StringUtils.isBlank(type)) {
			return "";
		}
		switch (type) {

		case "Convenci":
			return "Convencional";

		case "Organico":
			return "Orgânico";

		default:
			return type;
		}
	}

	public static List<CotationValueObject> transformModelToVO(List<Cotation> cotations) {
		List<CotationValueObject> cVOList = new ArrayList<CotationValueObject>();
		cotations.forEach(c -> cVOList.add(transformModelToVO(c)));
		return cVOList;
	}

}
