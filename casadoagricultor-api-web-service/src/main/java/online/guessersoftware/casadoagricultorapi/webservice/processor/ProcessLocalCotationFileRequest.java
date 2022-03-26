package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.time.LocalDate;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;

public class ProcessLocalCotationFileRequest {

	private String fileFullPath;
	private LocalDate date;
	private CeasasEnum ceasa;

	public ProcessLocalCotationFileRequest() {
	}

	public ProcessLocalCotationFileRequest(String fileFullPath, LocalDate date, CeasasEnum ceasa) {
		this.fileFullPath = fileFullPath;
		this.date = date;
		this.ceasa = ceasa;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public CeasasEnum getCeasa() {
		return ceasa;
	}

	public void setCeasa(CeasasEnum ceasa) {
		this.ceasa = ceasa;
	}

	@Override
	public String toString() {
		return "ProcessLocalCotationFileRequest [fileFullPath=" + fileFullPath + ", date=" + date + ", ceasa=" + ceasa + "]";
	}

}
