package online.guessersoftware.casadoagricultorapi.webservice.utils;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.constants.MonthsPortugueseEnum;

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

	public CotationsDownloadRequestBuilder destinyFolder(String destinyFolder, boolean contatenateWithYear) {
		if (contatenateWithYear) {
			this.request.setDestinyFolder(destinyFolder + request.getYear() + "/");
			return this;
		}
		this.request.setDestinyFolder(destinyFolder);
		return this;
	}

	public CotationsDownloadRequestBuilder baseUrl(String baseUrl) {
		this.request.setBaseUrl(baseUrl);
		return this;
	}

	public CotationsDownloadRequestBuilder usingDayComplete(String dayComplete, boolean adjustSomeField, String fieldToAdjust, String valueToAdjust,
			String newFieldValue) {
		LocalDate date = LocalDate.parse(dayComplete);
		String day = date.getDayOfMonth() < 10 ? ("0" + String.valueOf(date.getDayOfMonth())) : String.valueOf(date.getDayOfMonth());
		String month = date.getMonthValue() < 10 ? ("0" + String.valueOf(date.getMonthValue())) : String.valueOf(date.getMonthValue());
		String year = String.valueOf(date.getYear());
		if (adjustSomeField) {
			switch (fieldToAdjust) {

			case "day":
				if (StringUtils.equals(day, valueToAdjust)) {
					day = newFieldValue;
				}
				break;

			case "month":
				if (StringUtils.equals(month, valueToAdjust)) {
					month = newFieldValue;
				}
				break;

			case "year":
				if (StringUtils.equals(year, valueToAdjust)) {
					year = newFieldValue;
				}
				break;
			}
		}
		String monthString = MonthsPortugueseEnum.fromMonthNumber(month).getMonthAsString();
		day(day);
		monthNumber(month);
		monthString(monthString);
		year(year);
		return this;
	}

	public static void main(String[] args) {
		String date = "2022-01-31";
		LocalDate localDate = LocalDate.parse(date);
		LocalDate plusDays = localDate.plusDays(1);
		System.out.println(plusDays.getDayOfMonth());
		System.out.println(plusDays.getMonthValue());
		System.out.println(plusDays.getYear());
	}

}
