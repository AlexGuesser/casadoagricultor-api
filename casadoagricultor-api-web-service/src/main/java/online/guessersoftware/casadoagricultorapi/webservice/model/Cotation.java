package online.guessersoftware.casadoagricultorapi.webservice.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cotation")
public class Cotation extends BaseModel {

	@Column(name = "from_day")
	private LocalDate fromDay;

	@Column(name = "classification")
	private String classification;

	@Column(name = "type")
	private String type;

	@Column(name = "origin")
	private String origin;

	@Column(name = "packaging")
	private String packaging;

	@Column(name = "commun_weight")
	private float communWeight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_and_variety_FK")
	private ProductAndVariety productAndVariety;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "price_FK")
	private Price price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cotation_file_FK")
	private CotationFile cotationFile;

	public Cotation() {
	}

	public Cotation(LocalDate fromDay, String classification, String type, String origin, String packaging, float communWeight, ProductAndVariety productAndVariety,
			Price price, CotationFile cotationFile) {
		this.fromDay = fromDay;
		this.classification = classification;
		this.type = type;
		this.origin = origin;
		this.packaging = packaging;
		this.communWeight = communWeight;
		this.productAndVariety = productAndVariety;
		this.price = price;
		this.cotationFile = cotationFile;
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

	public float getCommunWeight() {
		return communWeight;
	}

	public void setCommunWeight(float communWeight) {
		this.communWeight = communWeight;
	}

	public ProductAndVariety getProductAndVariety() {
		return productAndVariety;
	}

	public void setProductAndVariety(ProductAndVariety productAndVariety) {
		this.productAndVariety = productAndVariety;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public CotationFile getCotationFile() {
		return cotationFile;
	}

	public void setCotationFile(CotationFile cotationFile) {
		this.cotationFile = cotationFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((classification == null) ? 0 : classification.hashCode());
		result = prime * result + Float.floatToIntBits(communWeight);
		result = prime * result + ((cotationFile == null) ? 0 : cotationFile.hashCode());
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((packaging == null) ? 0 : packaging.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productAndVariety == null) ? 0 : productAndVariety.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cotation other = (Cotation) obj;
		if (classification == null) {
			if (other.classification != null)
				return false;
		} else if (!classification.equals(other.classification))
			return false;
		if (Float.floatToIntBits(communWeight) != Float.floatToIntBits(other.communWeight))
			return false;
		if (cotationFile == null) {
			if (other.cotationFile != null)
				return false;
		} else if (!cotationFile.equals(other.cotationFile))
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
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productAndVariety == null) {
			if (other.productAndVariety != null)
				return false;
		} else if (!productAndVariety.equals(other.productAndVariety))
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
		return "Cotation [fromDay=" + fromDay + ", classification=" + classification + ", type=" + type + ", origin=" + origin + ", packaging=" + packaging
				+ ", communWeight=" + communWeight + ", productAndVariety=" + productAndVariety + ", price=" + price + ", cotationFile=" + cotationFile + "]";
	}

}
