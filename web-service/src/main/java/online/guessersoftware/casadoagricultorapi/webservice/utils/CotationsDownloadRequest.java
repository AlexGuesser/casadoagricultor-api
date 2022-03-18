package online.guessersoftware.casadoagricultorapi.webservice.utils;

public class CotationsDownloadRequest {

	private String day;
	private String monthNumber;
	private String monthString;
	private String year;
	private String destinyFolder;
	private String baseUrl;

	public CotationsDownloadRequest() {
	}

	public CotationsDownloadRequest(String day, String monthNumber, String monthString, String year, String destinyFolder,
			String baseUrl) {
		this.day = day;
		this.monthNumber = monthNumber;
		this.monthString = monthString;
		this.year = year;
		this.destinyFolder = destinyFolder;
		this.baseUrl = baseUrl;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(String monthNumber) {
		this.monthNumber = monthNumber;
	}

	public String getMonthString() {
		return monthString;
	}

	public void setMonthString(String monthString) {
		this.monthString = monthString;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((destinyFolder == null) ? 0 : destinyFolder.hashCode());
		result = prime * result + ((monthNumber == null) ? 0 : monthNumber.hashCode());
		result = prime * result + ((monthString == null) ? 0 : monthString.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		CotationsDownloadRequest other = (CotationsDownloadRequest) obj;
		if (baseUrl == null) {
			if (other.baseUrl != null)
				return false;
		} else if (!baseUrl.equals(other.baseUrl))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (destinyFolder == null) {
			if (other.destinyFolder != null)
				return false;
		} else if (!destinyFolder.equals(other.destinyFolder))
			return false;
		if (monthNumber == null) {
			if (other.monthNumber != null)
				return false;
		} else if (!monthNumber.equals(other.monthNumber))
			return false;
		if (monthString == null) {
			if (other.monthString != null)
				return false;
		} else if (!monthString.equals(other.monthString))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CotationsDownloadRequest [day=" + day + ", monthNumber=" + monthNumber + ", monthString=" + monthString
				+ ", year=" + year + ", destinyFolder=" + destinyFolder + ", baseUrl=" + baseUrl + "]";
	}

}
