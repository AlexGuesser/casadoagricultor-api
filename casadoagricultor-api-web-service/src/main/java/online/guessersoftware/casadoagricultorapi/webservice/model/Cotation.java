package online.guessersoftware.casadoagricultorapi.webservice.model;

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
	private Date fromDay;

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
	@JoinColumn(name = "ceasa_FK")
	private Ceasa ceasa;

	public Cotation() {
	}

	public Cotation(Date fromDay, String classification, String type, String origin,
			String packaging, float communWeight, ProductAndVariety productAndVariety, Price price,
			Ceasa ceasa) {
		this.fromDay = fromDay;
		this.classification = classification;
		this.type = type;
		this.origin = origin;
		this.packaging = packaging;
		this.communWeight = communWeight;
		this.productAndVariety = productAndVariety;
		this.price = price;
		this.ceasa = ceasa;
	}

	public Date getFromDay() {
		return fromDay;
	}

	public void setFromDay(Date fromDay) {
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

	public Ceasa getCeasa() {
		return ceasa;
	}

	public void setCeasa(Ceasa ceasa) {
		this.ceasa = ceasa;
	}

	@Override
	public String toString() {
		return "Cotation [fromDay=" + fromDay + ", classification=" + classification + ", type=" + type + ", origin=" + origin
				+ ", packaging=" + packaging + ", communWeight=" + communWeight + ", productAndVariety=" + productAndVariety
				+ ", price=" + price + ", ceasa=" + ceasa + "]";
	}
	
	

}
