package online.guessersoftware.casadoagricultorapi.webservice.constants;

public enum CeasasEnum {

	SAO_JOSE_SC("Ceasa de São José, SC", "São José", "SC", "BR");

	private String name;
	private String city;
	private String state;
	private String country;

	CeasasEnum(String name, String city, String state, String country) {
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

}
