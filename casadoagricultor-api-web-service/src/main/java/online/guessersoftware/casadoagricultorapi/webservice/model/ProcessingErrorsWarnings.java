package online.guessersoftware.casadoagricultorapi.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "processing_errors_warnings")
public class ProcessingErrorsWarnings extends BaseModel {

	@Column(name = "error_or_warning")
	private String errorOrWarning;

	@Column(name = "code")
	private String code;

	@Column(name = "problem")
	private String problem;

	@Column(name = "solution")
	private String solution;

	public ProcessingErrorsWarnings() {
	}

	public ProcessingErrorsWarnings(String errorOrWarning, String code, String problem, String solution) {
		this.errorOrWarning = errorOrWarning;
		this.code = code;
		this.problem = problem;
		this.solution = solution;
	}

	public String getErrorOrWarning() {
		return errorOrWarning;
	}

	public void setErrorOrWarning(String errorOrWarning) {
		this.errorOrWarning = errorOrWarning;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((errorOrWarning == null) ? 0 : errorOrWarning.hashCode());
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result + ((solution == null) ? 0 : solution.hashCode());
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
		ProcessingErrorsWarnings other = (ProcessingErrorsWarnings) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (errorOrWarning == null) {
			if (other.errorOrWarning != null)
				return false;
		} else if (!errorOrWarning.equals(other.errorOrWarning))
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessingErrorsWarnings [errorOrWarning=" + errorOrWarning + ", code=" + code + ", problem=" + problem + ", solution=" + solution + "]";
	}

}
