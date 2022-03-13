package online.guessersoftware.casadoagricultorapi.webservice.valueobject;

public class ProductAndVarietyValueObject {

	private String name;

	public ProductAndVarietyValueObject() {

	}

	public ProductAndVarietyValueObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductAndVarietyValueObject [name=" + name + "]";
	}

}
