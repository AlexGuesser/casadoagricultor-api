package online.guessersoftware.casadoagricultorapi.webservice.model;

public enum ProcessingErrorsWarningsEnum {

	FILE_NOT_FOUND_ERROR("e", "The cotation file searched was not found!",
			"Analyze if the specific file does exists at Ceasa site, if don't it's OK, if exists analyze!"), //
	PARSING_DATE_COTATION_ERROR("e", "There was an exception while parsing the date of cotation.", "Analyze that day's file cotation and adjust the code."), //
	TYPE_NOT_IN_ENUM_ERROR("e", "Some type was not found inside TypeListEnum.", "Analyze PDF and insert this new type."), //
	PACKING_NOT_IN_ENUM_ERROR("e", "Some packing was not found inside PackagingListEnum.", "Analyze PDF and insert this new packing."), //
	UNKNOWN_ERROR("e", "Some unexpected exception occured while processing.", "Analyze logs, file and code!"); //

	private String errorOrWarning;
	private String problem;
	private String solution;

	ProcessingErrorsWarningsEnum(String errorOrWarning, String problem, String solution) {
		this.errorOrWarning = errorOrWarning;
		this.problem = problem;
		this.solution = solution;
	}

	public String getErrorOrWarning() {
		return errorOrWarning;
	}

	public String getProblem() {
		return problem;
	}

	public String getSolution() {
		return solution;
	}

}
