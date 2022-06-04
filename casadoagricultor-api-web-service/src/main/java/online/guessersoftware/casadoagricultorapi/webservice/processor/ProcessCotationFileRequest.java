package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.time.LocalDate;

import online.guessersoftware.casadoagricultorapi.webservice.constants.CeasasEnum;

public class ProcessCotationFileRequest {

	private LocalDate date;
	private CeasasEnum ceasa;
	private String fileName;
	private String fileFullPath;
	private String url;
	private boolean local; // IF TRUE, THIS FILE WAS LOADED FROM LOCAL MACHINE, OTHERWISE, DOWNLOAD FROM
							// GOOGLE STORAGE

	public ProcessCotationFileRequest() {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}
	
	



}
