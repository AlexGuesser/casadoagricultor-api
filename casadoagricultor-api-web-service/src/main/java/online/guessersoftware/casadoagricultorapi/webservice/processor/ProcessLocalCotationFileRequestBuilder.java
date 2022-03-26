package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.time.LocalDate;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;

public class ProcessLocalCotationFileRequestBuilder {

	private ProcessLocalCotationFileRequest request = new ProcessLocalCotationFileRequest();

	public static ProcessLocalCotationFileRequestBuilder usingThis() {
		return new ProcessLocalCotationFileRequestBuilder();
	}

	public ProcessLocalCotationFileRequestBuilder fileFullPath(String fileFullPath) {
		this.request.setFileFullPath(fileFullPath);
		return this;
	}

	public ProcessLocalCotationFileRequestBuilder date(LocalDate date) {
		this.request.setDate(date);
		return this;
	}

	public ProcessLocalCotationFileRequestBuilder ceasa(CeasasEnum ceasa) {
		this.request.setCeasa(ceasa);
		return this;
	}

	public ProcessLocalCotationFileRequest build() {
		return this.request;
	}

}
