package online.guessersoftware.casadoagricultorapi.webservice.json;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailRequestJson {

	@NotEmpty
	private String name;

	@NotEmpty
	private String lastName;

	private String fromEmail;

	private String replyToEmail;

	@NotEmpty
	private String toEmail;

	@NotEmpty
	private String subject;

	@NotEmpty
	private String message;

	public MailRequestJson() {
	}

	public MailRequestJson(@NotEmpty String name, @NotEmpty String lastName, String fromEmail, String replyToEmail, @NotEmpty String toEmail,
			@NotEmpty String subject, @NotEmpty String message) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.fromEmail = fromEmail;
		this.replyToEmail = replyToEmail;
		this.toEmail = toEmail;
		this.subject = subject;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getReplyToEmail() {
		return replyToEmail;
	}

	public void setReplyToEmail(String replyToEmail) {
		this.replyToEmail = replyToEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "MailRequestJson [name=" + name + ", lastName=" + lastName + ", fromEmail=" + fromEmail + ", replyToEmail=" + replyToEmail + ", toEmail="
				+ toEmail + ", subject=" + subject + ", message=" + message + "]";
	}

}
