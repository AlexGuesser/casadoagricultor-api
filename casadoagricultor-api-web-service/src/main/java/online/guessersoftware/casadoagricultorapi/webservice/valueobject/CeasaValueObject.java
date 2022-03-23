package online.guessersoftware.casadoagricultorapi.webservice.valueobject;

public class CeasaValueObject {

	private String name;
	private String city;
	private String state;
	private String country;

	public CeasaValueObject() {
	}

	public CeasaValueObject(String name, String city, String state, String country) {
		this.name = name;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CeasaValueObject [name=" + name + ", city=" + city + ", state=" + state + ", country=" + country + "]";
	}

}
