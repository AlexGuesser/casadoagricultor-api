package online.guessersoftware.casadoagricultorapi.webservice.utils;

public class CotationsDownloadRequestBuilder {

	private CotationsDownloadRequest request = new CotationsDownloadRequest();

	public CotationsDownloadRequest build() {
		return this.request;
	}

	public static CotationsDownloadRequestBuilder usingThis() {
		return new CotationsDownloadRequestBuilder();
	}

	public CotationsDownloadRequestBuilder day(String day) {
		this.request.setDay(day);
		return this;
	}

	public CotationsDownloadRequestBuilder monthNumber(String monthNumber) {
		this.request.setMonthNumber(monthNumber);
		return this;
	}

	public CotationsDownloadRequestBuilder monthString(String monthString) {
		this.request.setMonthString(monthString);
		return this;
	}

	public CotationsDownloadRequestBuilder year(String year) {
		this.request.setYear(year);
		return this;
	}

	public CotationsDownloadRequestBuilder destinyFolder(String destinyFolder) {
		this.request.setDestinyFolder(destinyFolder);
		return this;
	}

	public CotationsDownloadRequestBuilder baseUrl(String baseUrl) {
		this.request.setBaseUrl(baseUrl);
		return this;
	}

}
