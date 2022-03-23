package online.guessersoftware.casadoagricultorapi.microserviceemailsender.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.common.constants.MimeTypes;

public class Mail {

	private String sender;
	private List<String> recipients = new ArrayList<>();
	private List<String> ccRecipients = new ArrayList<>();
	private List<String> bccRecipients = new ArrayList<>();
	private List<Attachment> attachments = new ArrayList<>();
	private String subject;
	private String content;
	private Map<String, String> headers = new HashMap<>();
	private boolean html = false;
	private static Pattern p = Pattern.compile(Constants.MAIL_REGEX_VALIDATOR);

	private Mail() {

	}

	public static Mail build() {
		return new Mail();
	}

	public static Mail build(Collection<String> recipients) {
		Mail mail = new Mail();
		mail.recipients(recipients);
		return mail;
	}

	public static Mail build(String... recipients) {
		Mail mail = new Mail();
		mail.recipients(recipients);
		return mail;
	}

	public Mail sender(String sender) {
		if (!EmailValidator.getInstance().isValid(sender)) {
			throw new IllegalArgumentException("Sender must be a valid e-mail, got=" + sender);
		}
		this.sender = sender;
		return this;
	}

	public Mail recipient(String recipient) {
		recipient = StringUtils.trim(recipient);
		if (!(checkIsValid(recipient) || checkIsValidInternal(recipient))) {
			throw new IllegalArgumentException("Recipient must be a valid e-mail, got=" + recipient);
		}
		this.recipients.add(recipient);
		return this;
	}

	public boolean checkIsValid(String recipient) {
		Matcher m = p.matcher(recipient);
		boolean matchFound = m.matches();
		return matchFound;
	}

	private boolean checkIsValidInternal(String recipient) {
		String formatRecipient = recipient.replaceAll(".*\\<|\\>.*", "");
		return checkIsValid(formatRecipient);
	}

	public Mail recipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			recipient(recipient);
		}
		return this;
	}

	public Mail recipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			recipient(recipient);
		}
		return this;
	}

	public Mail replyTo(String replyTo) {
		this.headers.put("Reply-To", replyTo);
		return this;
	}

	public Mail clearRecipients() {
		recipients.clear();
		return this;
	}

	public Mail ccRecipient(String recipient) {
		if (!EmailValidator.getInstance().isValid(recipient)) {
			throw new IllegalArgumentException("Recipient must be a valid e-mail, got=" + recipient);
		}
		this.ccRecipients.add(recipient);
		return this;
	}

	public Mail ccRecipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			ccRecipient(recipient);
		}
		return this;
	}

	public Mail ccRecipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			ccRecipient(recipient);
		}
		return this;
	}

	public Mail bccRecipient(String recipient) {
		if (StringUtils.isBlank(recipient)) {
			return this;
		}
		if (!EmailValidator.getInstance().isValid(recipient)) {
			throw new IllegalArgumentException("Recipient must be a valid e-mail, got=" + recipient);
		}
		this.bccRecipients.add(recipient);
		return this;
	}

	public Mail bccRecipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			bccRecipient(recipient);
		}
		return this;
	}

	public Mail bccRecipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			bccRecipient(recipient);
		}
		return this;
	}

	public Mail html() {
		this.html = true;
		return this;
	}

	public Mail subject(String subject) {
		this.subject = subject;
		return this;
	}

	public Mail content(String content) {
		this.content = content;
		return this;
	}

	public Mail content(String content, boolean html) {
		this.content = content;
		this.html = html;
		return this;
	}

	public Mail attachment(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("Filepath must be informed");
		}
		this.attachments.add(new FileAttachment(filePath));
		return this;
	}

	public Mail attachment(String filePath, String name) {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("Filepath must be informed");
		}
		this.attachments.add(new FileAttachment(filePath, name));
		return this;
	}

	public Mail attachment(byte[] data, String name, String extension) {
		if (data == null) {
			return this;
		}
		this.attachments.add(new ByteArrayAttachment(data, name, extension));
		return this;
	}

	public InternetAddress getSender() throws AddressException {
		return new InternetAddress(sender);
	}

	private static InternetAddress[] transformToInternetAddress(List<String> recipients) throws Exception {
		if (CollectionUtils.isEmpty(recipients)) {
			return new InternetAddress[0];
		}
		List<InternetAddress> addresses = new ArrayList<>();
		for (String recipient : recipients) {
			addresses.add(new InternetAddress(recipient));
		}

		return addresses.toArray(new InternetAddress[addresses.size()]);
	}

	public InternetAddress[] getRecipients() throws Exception {
		if (CollectionUtils.isEmpty(recipients)) {
			throw new IllegalArgumentException("No recipients were set");
		}
		return transformToInternetAddress(recipients);
	}

	public InternetAddress[] getCcRecipients() throws Exception {
		return transformToInternetAddress(ccRecipients);
	}

	public InternetAddress[] getBccRecipients() throws Exception {
		return transformToInternetAddress(bccRecipients);
	}

	public String getSubject() {
		return this.subject;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public boolean getHtml() {
		return html;
	}

	public String getContent() {
		return content;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getSenderAsString() {
		return this.sender;
	}

	public List<String> getRecipientsAsString() {
		return this.recipients;
	}

	public List<String> getCcRecipientsAsString() {
		return this.ccRecipients;
	}

	public List<String> getBccRecipientsAsString() {
		return this.bccRecipients;
	}

	public interface Attachment {
		String getName();

		String getExtension();

		DataSource getDataSource();
	}

	private class FileAttachment implements Attachment {
		private String filePath;
		private String name;

		public FileAttachment(String filePath) {
			this.filePath = filePath;
			this.name = FilenameUtils.getBaseName(filePath);
		}

		public FileAttachment(String filePath, String name) {
			this.filePath = filePath;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public DataSource getDataSource() {
			return new FileDataSource(new File(filePath));
		}

		@Override
		public String getExtension() {
			return FilenameUtils.getExtension(filePath);
		}
	}

	private class ByteArrayAttachment implements Attachment {
		private byte[] data;
		private String name;
		private String ext;

		public ByteArrayAttachment(byte[] data, String name, String ext) {
			this.data = data;
			this.name = name;
			this.ext = ext;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public DataSource getDataSource() {
			return new ByteArrayDataSource(data, MimeTypes.getMimeType(ext));
		}

		@Override
		public String getExtension() {
			return ext;
		}

	}

}
