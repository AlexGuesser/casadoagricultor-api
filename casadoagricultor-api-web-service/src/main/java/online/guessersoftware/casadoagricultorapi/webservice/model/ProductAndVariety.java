package online.guessersoftware.casadoagricultorapi.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "product_and_variety")
public class ProductAndVariety extends BaseModel {

	@Column(name = "name")
	private String name;

	public ProductAndVariety() {
	}

	public ProductAndVariety(String name) {
		this.name = StringUtils.normalizeSpace(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.normalizeSpace(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ProductAndVariety other = (ProductAndVariety) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductAndVariety [name=" + name + "]";
	}

}
