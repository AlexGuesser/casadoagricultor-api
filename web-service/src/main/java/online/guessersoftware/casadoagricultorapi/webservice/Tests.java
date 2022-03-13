package online.guessersoftware.casadoagricultorapi.webservice;

import java.math.BigDecimal;
import java.text.ParseException;

public class Tests {

	public static void main(String[] args) throws ParseException {

		String minimunPrice = "2.50";
		String commonPricePerKg = "3.00";
		BigDecimal minimunPriceBD = new BigDecimal(minimunPrice);
		BigDecimal commonPricePerKgBD = new BigDecimal(commonPricePerKg);

		System.out.println(minimunPriceBD);
		System.out.println(commonPricePerKgBD);

//		System.out.println(new SimpleDateFormat("dd/MM/yyyy").parse("16/07/2021"));
//		CotationValueObject cotation = new CotationValueObject();
//		String text = "7,701 7,70Especial KiloOrganico";
//		// ### TYPE
//		for (String type : TypeList.getValues()) {
//			if (text.contains(type)) {
//				text = text.replace(type, "");
//				cotation.setType(type);
//				System.out.println(text);
//			}
//		}
//		if (cotation.getType() == null) {
//			System.out.println("Type not include in TypeList: " + text);
//		}

	}

}
