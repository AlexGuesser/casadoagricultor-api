package online.guessersoftware.casadoagricultorapi.webservice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "deleted")
	private boolean deleted;
	@Column(name = "last_user")
	private Long lastUser;
	@Column(name = "last_operation")
	private LocalDateTime lastOperation;

	public BaseModel() {
	}

	public BaseModel(Long id, boolean deleted, Long lastUser, LocalDateTime lastOperation) {
		this.id = id;
		this.deleted = deleted;
		this.lastUser = lastUser;
		this.lastOperation = lastOperation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getLastUser() {
		return lastUser;
	}

	public void setLastUser(Long lastUser) {
		this.lastUser = lastUser;
	}

	public LocalDateTime getLastOperation() {
		return lastOperation;
	}

	public void setLastOperation(LocalDateTime lastOperation) {
		this.lastOperation = lastOperation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastOperation == null) ? 0 : lastOperation.hashCode());
		result = prime * result + ((lastUser == null) ? 0 : lastUser.hashCode());
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
		BaseModel other = (BaseModel) obj;
		if (deleted != other.deleted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastOperation == null) {
			if (other.lastOperation != null)
				return false;
		} else if (!lastOperation.equals(other.lastOperation))
			return false;
		if (lastUser == null) {
			if (other.lastUser != null)
				return false;
		} else if (!lastUser.equals(other.lastUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseModel [id=" + id + ", deleted=" + deleted + ", lastUser=" + lastUser
				+ ", lastOperation=" + lastOperation + "]";
	}

}
