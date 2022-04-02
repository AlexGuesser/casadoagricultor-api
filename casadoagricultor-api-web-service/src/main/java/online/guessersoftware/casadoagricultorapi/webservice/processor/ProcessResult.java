package online.guessersoftware.casadoagricultorapi.webservice.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import online.guessersoftware.casadoagricultorapi.webservice.model.ProcessingErrorsWarningsEnum;
import online.guessersoftware.casadoagricultorapi.webservice.valueobject.CotationValueObject;

public class ProcessResult {

	private List<ProcessingErrorsWarningsEnum> errorsAndWarnings = new ArrayList<ProcessingErrorsWarningsEnum>();
	private List<CotationValueObject> cotationsVO = new ArrayList<CotationValueObject>();
	private List<String> logErrors = new ArrayList<String>();

	public ProcessResult() {
	}

	public void addErrorOrWarning(ProcessingErrorsWarningsEnum ewEnum) {
		errorsAndWarnings.add(ewEnum);
	}

	public void addCotationVO(CotationValueObject cVO) {
		cotationsVO.add(cVO);
	}

	public void addLogError(String error) {
		logErrors.add(error);
	}

	public List<ProcessingErrorsWarningsEnum> getErrorsAndWarnings() {
		return errorsAndWarnings;
	}

	public List<CotationValueObject> getCotationsVO() {
		return cotationsVO;
	}

	public List<String> getLogErrors() {
		return logErrors;
	}

	@Override
	public String toString() {
		return "ProcessResult [errorsAndWarnings=" + errorsAndWarnings + ", cotationsVO=" + cotationsVO + ", logErrors=" + logErrors + "]";
	}

	public boolean hadSomeError() {
		for (ProcessingErrorsWarningsEnum ew : errorsAndWarnings) {
			if (StringUtils.equals(ew.getErrorOrWarning(), "e")) {
				return true;
			}
		}
		return false;
	}

}