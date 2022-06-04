package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.time.LocalDate;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;

public class ProcessCotationFileRequestBuilder {

	private ProcessCotationFileRequest request = new ProcessCotationFileRequest();

	public static ProcessCotationFileRequestBuilder usingThis() {
		return new ProcessCotationFileRequestBuilder();
	}

	public ProcessCotationFileRequestBuilder fileFullPath(String fileFullPath) {
		this.request.setFileFullPath(fileFullPath);
		return this;
	}

	public ProcessCotationFileRequestBuilder date(LocalDate date) {
		this.request.setDate(date);
		return this;
	}

	public ProcessCotationFileRequestBuilder ceasa(CeasasEnum ceasa) {
		this.request.setCeasa(ceasa);
		return this;
	}

	public ProcessCotationFileRequestBuilder local(boolean local) {
		this.request.setLocal(local);
		return this;
	}

	public ProcessCotationFileRequestBuilder fileName(String fileName) {
		this.request.setFileName(fileName);
		return this;
	}

	public ProcessCotationFileRequestBuilder url(String url) {
		this.request.setUrl(url);
		return this;
	}

	public ProcessCotationFileRequest build() {
		return this.request;
	}

}
