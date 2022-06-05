package online.guessersoftware.casadoagricultorapi.webservice.valueobject;

import java.time.LocalDate;
import java.util.List;

public class CotationResultValueObject {

	private LocalDate desiredDate;
	private LocalDate fromDay;
	private boolean desiredDayExist;
	private List<CotationValueObject> cotations;

	public CotationResultValueObject() {
	}

	public CotationResultValueObject(LocalDate desiredDate, LocalDate fromDay, boolean desiredDayExist, List<CotationValueObject> cotations) {
		super();
		this.desiredDate = desiredDate;
		this.fromDay = fromDay;
		this.desiredDayExist = desiredDayExist;
		this.cotations = cotations;
	}

	public LocalDate getDesiredDate() {
		return desiredDate;
	}

	public void setDesiredDate(LocalDate desiredDate) {
		this.desiredDate = desiredDate;
	}

	public LocalDate getFromDay() {
		return fromDay;
	}

	public void setFromDay(LocalDate fromDay) {
		this.fromDay = fromDay;
	}

	public boolean isDesiredDayExist() {
		return desiredDayExist;
	}

	public void setDesiredDayExist(boolean desiredDayExist) {
		this.desiredDayExist = desiredDayExist;
	}

	public List<CotationValueObject> getCotations() {
		return cotations;
	}

	public void setCotations(List<CotationValueObject> cotations) {
		this.cotations = cotations;
	}

	@Override
	public String toString() {
		return "CotationResultValueObject [desiredDate=" + desiredDate + ", fromDay=" + fromDay + ", desiredDayExist=" + desiredDayExist + ", cotations="
				+ cotations + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cotations == null) ? 0 : cotations.hashCode());
		result = prime * result + ((desiredDate == null) ? 0 : desiredDate.hashCode());
		result = prime * result + (desiredDayExist ? 1231 : 1237);
		result = prime * result + ((fromDay == null) ? 0 : fromDay.hashCode());
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
		CotationResultValueObject other = (CotationResultValueObject) obj;
		if (cotations == null) {
			if (other.cotations != null)
				return false;
		} else if (!cotations.equals(other.cotations))
			return false;
		if (desiredDate == null) {
			if (other.desiredDate != null)
				return false;
		} else if (!desiredDate.equals(other.desiredDate))
			return false;
		if (desiredDayExist != other.desiredDayExist)
			return false;
		if (fromDay == null) {
			if (other.fromDay != null)
				return false;
		} else if (!fromDay.equals(other.fromDay))
			return false;
		return true;
	}

}
