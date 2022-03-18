package online.guessersoftware.casadoagricultorapi.webservice.constants;

public enum DaysEnum {

	ONE("01"), //
	TWO("02"), //
	THREE("03"), //
	FOUR("04"), //
	FIVE("05"), //
	SIX("06"), //
	SEVEN("07"), //
	EIGHT("08"), //
	NINE("09"), //
	TEN("10"), //
	ELEVEN("11"), //
	TWELVE("12"), //
	THIRTEEN("13"), //
	FOURTEEN("14"), //
	FIFTEEN("15"), //
	SIXTEEN("16"), //
	SEVENTEEN("17"), //
	EIGHTEEN("18"), //
	NINETEEN("19"), //
	TWENTY("20"), //
	TWENTY_ONE("21"), //
	TWENTY_TWO("22"), //
	TWENTY_THREE("23"), //
	TWENTY_FOUR("24"), //
	TWENTY_FIVE("25"), //
	TWENTY_SIX("26"), //
	TWENTY_SEVEN("27"), //
	TWENTY_EIGTH("28"), //
	TWENTY_NINE("29"), //
	THIRTY("30"), //
	THIRTY_ONE("31");

	private String day;

	DaysEnum(String day) {
		this.day = day;
	}

	public String getDay() {
		return day;
	}

}
