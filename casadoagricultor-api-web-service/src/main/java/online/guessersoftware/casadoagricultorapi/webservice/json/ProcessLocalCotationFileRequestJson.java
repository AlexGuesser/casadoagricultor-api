package online.guessersoftware.casadoagricultorapi.webservice.json;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessLocalCotationFileRequestJson {

	private String baseFolderPath;
	@NotNull
	private String fromDay;
	@NotNull
	private String toDay;

	public ProcessLocalCotationFileRequestJson() {
	}

	public ProcessLocalCotationFileRequestJson(String baseFolderPath, @NotNull String fromDay, @NotNull String toDay) {
		this.baseFolderPath = baseFolderPath;
		this.fromDay = fromDay;
		this.toDay = toDay;
	}

	public String getBaseFolderPath() {
		return baseFolderPath;
	}

	public void setBaseFolderPath(String baseFolderPath) {
		this.baseFolderPath = baseFolderPath;
	}

	public String getFromDay() {
		return fromDay;
	}

	public void setFromDay(String fromDay) {
		this.fromDay = fromDay;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}

	@Override
	public String toString() {
		return "ProcessLocalCotationFileRequest [baseFolderPath=" + baseFolderPath + ", fromDay=" + fromDay + ", toDay=" + toDay + "]";
	}

}
