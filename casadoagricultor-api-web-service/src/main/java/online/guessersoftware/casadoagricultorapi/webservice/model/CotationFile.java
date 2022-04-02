package online.guessersoftware.casadoagricultorapi.webservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cotation_file")
public class CotationFile extends BaseModel {

	@Column(name = "successfully_processed")
	private boolean successfullyProcessed;

	@Column(name = "filename")
	private String filename;

	@Column(name = "format")
	private String format;

	@Column(name = "url")
	private String url;

	@Column(name = "storage_reference")
	private String storageReference;

	@Column(name = "saved_locally_or_cloud")
	private String savedLocallyOrCloud;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ceasa_FK")
	private Ceasa ceasa;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable( //
			name = "cotation_file_processing_errors_warnings", //
			joinColumns = @JoinColumn(name = "cotation_file_fk"), //
			inverseJoinColumns = @JoinColumn(name = "processing_errors_warnings_fk")) //
	private Set<ProcessingErrorsWarnings> errorsAndWarnings = new HashSet<ProcessingErrorsWarnings>();

	public CotationFile() {
	}

	public CotationFile(boolean successfullyProcessed, String filename, String format, String url, String storageReference, String savedLocallyOrCloud,
			Ceasa ceasa) {
		this.successfullyProcessed = successfullyProcessed;
		this.filename = filename;
		this.format = format;
		this.url = url;
		this.storageReference = storageReference;
		this.savedLocallyOrCloud = savedLocallyOrCloud;
		this.ceasa = ceasa;
	}

	public boolean isSuccessfullyProcessed() {
		return successfullyProcessed;
	}

	public void setSuccessfullyProcessed(boolean successfullyProcessed) {
		this.successfullyProcessed = successfullyProcessed;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStorageReference() {
		return storageReference;
	}

	public void setStorageReference(String storageReference) {
		this.storageReference = storageReference;
	}

	public String getSavedLocallyOrCloud() {
		return savedLocallyOrCloud;
	}

	public void setSavedLocallyOrCloud(String savedLocallyOrCloud) {
		this.savedLocallyOrCloud = savedLocallyOrCloud;
	}

	public Ceasa getCeasa() {
		return ceasa;
	}

	public void setCeasa(Ceasa ceasa) {
		this.ceasa = ceasa;
	}

	public Set<ProcessingErrorsWarnings> getErrorsAndWarnings() {
		return errorsAndWarnings;
	}

	public void setErrorsAndWarnings(Set<ProcessingErrorsWarnings> errorsAndWarnings) {
		this.errorsAndWarnings = errorsAndWarnings;
	}

	public void addErrorOrWarning(ProcessingErrorsWarnings errorOrWarning) {
		this.errorsAndWarnings.add(errorOrWarning);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ceasa == null) ? 0 : ceasa.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((savedLocallyOrCloud == null) ? 0 : savedLocallyOrCloud.hashCode());
		result = prime * result + ((storageReference == null) ? 0 : storageReference.hashCode());
		result = prime * result + (successfullyProcessed ? 1231 : 1237);
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CotationFile other = (CotationFile) obj;
		if (ceasa == null) {
			if (other.ceasa != null)
				return false;
		} else if (!ceasa.equals(other.ceasa))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (savedLocallyOrCloud == null) {
			if (other.savedLocallyOrCloud != null)
				return false;
		} else if (!savedLocallyOrCloud.equals(other.savedLocallyOrCloud))
			return false;
		if (storageReference == null) {
			if (other.storageReference != null)
				return false;
		} else if (!storageReference.equals(other.storageReference))
			return false;
		if (successfullyProcessed != other.successfullyProcessed)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CotationFile [successfullyProcessed=" + successfullyProcessed + ", filename=" + filename + ", format=" + format + ", url=" + url
				+ ", storageReference=" + storageReference + ", savedLocallyOrCloud=" + savedLocallyOrCloud + ", ceasa=" + ceasa + "]";
	}

}
