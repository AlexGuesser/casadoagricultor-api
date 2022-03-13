package online.guessersoftware.casadoagricultorapi.webservice.valueobject;

public class PriceValueObject {

	private String minimunPrice;
	private String commonPrice;
	private String maximunPrice;
	private String commonPricePerKg;

	public PriceValueObject() {
	}

	public PriceValueObject(String minimunPrice, String commonPrice, String maximunPrice, String commonPricePerKg) {
		this.minimunPrice = minimunPrice;
		this.commonPrice = commonPrice;
		this.maximunPrice = maximunPrice;
		this.commonPricePerKg = commonPricePerKg;
	}

	public String getMinimunPrice() {
		return minimunPrice;
	}

	public void setMinimunPrice(String minimunPrice) {
		this.minimunPrice = minimunPrice;
	}

	public String getCommonPrice() {
		return commonPrice;
	}

	public void setCommonPrice(String commonPrice) {
		this.commonPrice = commonPrice;
	}

	public String getMaximunPrice() {
		return maximunPrice;
	}

	public void setMaximunPrice(String maximunPrice) {
		this.maximunPrice = maximunPrice;
	}

	public String getCommonPricePerKg() {
		return commonPricePerKg;
	}

	public void setCommonPricePerKg(String commonPricePerKg) {
		this.commonPricePerKg = commonPricePerKg;
	}

	@Override
	public String toString() {
		return "PriceValueObject [minimunPrice=" + minimunPrice + ", commonPrice=" + commonPrice + ", maximunPrice="
				+ maximunPrice + ", commonPricePerKg=" + commonPricePerKg + "]";
	}

}
