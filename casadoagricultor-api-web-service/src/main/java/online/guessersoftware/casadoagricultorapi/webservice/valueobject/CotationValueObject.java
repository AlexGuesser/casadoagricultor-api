package online.guessersoftware.casadoagricultorapi.webservice.valueobject;

import java.time.LocalDate;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;

public class CotationValueObject {

	private LocalDate fromDay;
	private String classification;
	private String type;
	private String origin;
	private String packaging;
	private String commonWeight;
	private ProductAndVarietyValueObject productAndVarietyValueObject = new ProductAndVarietyValueObject();
	private PriceValueObject priceValueObject = new PriceValueObject();
	private String ceasaName;

	public CotationValueObject() {
	}

	public CotationValueObject(LocalDate fromDay, String classification, String type, String origin, String packaging, String commonWeight,
			ProductAndVarietyValueObject productAndVarietyValueObject, PriceValueObject priceValueObject, String ceasaName) {
		this.fromDay = fromDay;
		this.classification = classification;
		this.type = type;
		this.origin = origin;
		this.packaging = packaging;
		this.commonWeight = commonWeight;
		this.productAndVarietyValueObject = productAndVarietyValueObject;
		this.priceValueObject = priceValueObject;
		this.ceasaName = ceasaName;
	}

	public LocalDate getFromDay() {
		return fromDay;
	}

	public void setFromDay(LocalDate fromDay) {
		this.fromDay = fromDay;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getCommonWeight() {
		return commonWeight;
	}

	public void setCommonWeight(String commonWeight) {
		this.commonWeight = commonWeight;
	}

	public ProductAndVarietyValueObject getProductAndVarietyValueObject() {
		return productAndVarietyValueObject;
	}

	public void setProductAndVarietyValueObject(ProductAndVarietyValueObject productAndVarietyValueObject) {
		this.productAndVarietyValueObject = productAndVarietyValueObject;
	}

	public PriceValueObject getPriceValueObject() {
		return priceValueObject;
	}

	public void setPriceValueObject(PriceValueObject priceValueObject) {
		this.priceValueObject = priceValueObject;
	}

	public String getCeasaName() {
		return this.ceasaName;
	}

	public void setCeasaName(String ceasaName) {
		this.ceasaName = ceasaName;
	}

	public void setCeasaName(CeasasEnum ceasa) {
		this.ceasaName = ceasa.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ceasaName == null) ? 0 : ceasaName.hashCode());
		result = prime * result + ((classification == null) ? 0 : classification.hashCode());
		result = prime * result + ((commonWeight == null) ? 0 : commonWeight.hashCode());
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((packaging == null) ? 0 : packaging.hashCode());
		result = prime * result + ((priceValueObject == null) ? 0 : priceValueObject.hashCode());
		result = prime * result + ((productAndVarietyValueObject == null) ? 0 : productAndVarietyValueObject.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CotationValueObject other = (CotationValueObject) obj;
		if (ceasaName == null) {
			if (other.ceasaName != null)
				return false;
		} else if (!ceasaName.equals(other.ceasaName))
			return false;
		if (classification == null) {
			if (other.classification != null)
				return false;
		} else if (!classification.equals(other.classification))
			return false;
		if (commonWeight == null) {
			if (other.commonWeight != null)
				return false;
		} else if (!commonWeight.equals(other.commonWeight))
			return false;
		if (fromDay == null) {
			if (other.fromDay != null)
				return false;
		} else if (!fromDay.equals(other.fromDay))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (packaging == null) {
			if (other.packaging != null)
				return false;
		} else if (!packaging.equals(other.packaging))
			return false;
		if (priceValueObject == null) {
			if (other.priceValueObject != null)
				return false;
		} else if (!priceValueObject.equals(other.priceValueObject))
			return false;
		if (productAndVarietyValueObject == null) {
			if (other.productAndVarietyValueObject != null)
				return false;
		} else if (!productAndVarietyValueObject.equals(other.productAndVarietyValueObject))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CotationValueObject [fromDay=" + fromDay + ", classification=" + classification + ", type=" + type + ", origin=" + origin + ", packaging="
				+ packaging + ", commonWeight=" + commonWeight + ", productAndVarietyValueObject=" + productAndVarietyValueObject + ", priceValueObject="
				+ priceValueObject + ", ceasaName=" + ceasaName + "]";
	}

}
