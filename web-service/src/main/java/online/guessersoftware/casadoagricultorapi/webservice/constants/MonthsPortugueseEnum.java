package online.guessersoftware.casadoagricultorapi.webservice.constants;

public enum MonthsPortugueseEnum {

	JANEIRO("janeiro", "01"), //
	FEVEREIRO("fevereiro", "02"), //
	MARCO("marco", "03"), //
	ABRIL("abril", "04"), //
	MAIO("maio", "05"), //
	JUNHO("junho", "06"), //
	JULHO("julho", "07"), //
	AGOSTO("agosto", "08"), //
	SETEMBRO("setembro", "09"), //
	OUTUBRO("outubro", "10"), //
	NOVEMBRO("novembro", "11"), //
	DEZEMBRO("dezembro", "12"); //

	private String monthAsString;
	private String monthAsNumber;

	MonthsPortugueseEnum(String monthAsString, String monthAsNumber) {
		this.monthAsString = monthAsString;
		this.monthAsNumber = monthAsNumber;
	}

	public String getMonthAsString() {
		return monthAsString;
	}

	public String getMonthAsNumber() {
		return monthAsNumber;
	}

}
