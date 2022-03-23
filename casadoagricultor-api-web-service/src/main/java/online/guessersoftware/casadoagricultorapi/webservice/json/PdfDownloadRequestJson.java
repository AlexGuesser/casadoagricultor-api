package online.guessersoftware.casadoagricultorapi.webservice.json;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PdfDownloadRequestJson {

	@NotNull
	private String fromDay;
	@NotNull
	private String toDay;
	@NotNull
	private String destinyFolder;
	@NotNull
	private String baseUrl;

	public PdfDownloadRequestJson() {
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

	public String getDestinyFolder() {
		return destinyFolder;
	}

	public void setDestinyFolder(String destinyFolder) {
		this.destinyFolder = destinyFolder;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
		result = prime * result + ((destinyFolder == null) ? 0 : destinyFolder.hashCode());
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
		result = prime * result + ((toDay == null) ? 0 : toDay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PdfDownloadRequestJson other = (PdfDownloadRequestJson) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null)
				return false;
		} else if (!baseUrl.equals(other.baseUrl))
			return false;
		if (destinyFolder == null) {
			if (other.destinyFolder != null)
				return false;
		} else if (!destinyFolder.equals(other.destinyFolder))
			return false;
		if (fromDay == null) {
			if (other.fromDay != null)
				return false;
		} else if (!fromDay.equals(other.fromDay))
			return false;
		if (toDay == null) {
			if (other.toDay != null)
				return false;
		} else if (!toDay.equals(other.toDay))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PdfDownloadRequestJson [fromDay=" + fromDay + ", toDay=" + toDay + ", destinyFolder=" + destinyFolder + ", baseUrl=" + baseUrl
				+ "]";
	}

}
