package online.guessersoftware.casadoagricultorapi.webservice.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price extends BaseModel {

	@Column(name = "minimum")
	private BigDecimal minimum;

	@Column(name = "common")
	private BigDecimal common;

	@Column(name = "maximum")
	private BigDecimal maximum;

	@Column(name = "kg_common")
	private BigDecimal kgCommon;

	public Price() {
	}

	public Price(BigDecimal minimum, BigDecimal common, BigDecimal maximum, BigDecimal kgCommon) {
		this.minimum = minimum;
		this.common = common;
		this.maximum = maximum;
		this.kgCommon = kgCommon;
	}

	public BigDecimal getMinimum() {
		return minimum;
	}

	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}

	public BigDecimal getCommon() {
		return common;
	}

	public void setCommon(BigDecimal common) {
		this.common = common;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}

	public void setMaximum(BigDecimal maximum) {
		this.maximum = maximum;
	}

	public BigDecimal getKgCommon() {
		return kgCommon;
	}

	public void setKgCommon(BigDecimal kgCommon) {
		this.kgCommon = kgCommon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((common == null) ? 0 : common.hashCode());
		result = prime * result + ((kgCommon == null) ? 0 : kgCommon.hashCode());
		result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
		result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
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
		Price other = (Price) obj;
		if (common == null) {
			if (other.common != null)
				return false;
		} else if (!common.equals(other.common))
			return false;
		if (kgCommon == null) {
			if (other.kgCommon != null)
				return false;
		} else if (!kgCommon.equals(other.kgCommon))
			return false;
		if (maximum == null) {
			if (other.maximum != null)
				return false;
		} else if (!maximum.equals(other.maximum))
			return false;
		if (minimum == null) {
			if (other.minimum != null)
				return false;
		} else if (!minimum.equals(other.minimum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Price [minimum=" + minimum + ", common=" + common + ", maximum=" + maximum
				+ ", kgCommon=" + kgCommon + "]";
	}

}
