package online.guessersoftware.casadoagricultorapi.microserviceemailsender.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
	private static Pattern p = Pattern.compile(Constants.EMAIL_REGEX_VALIDATOR);

	private Mail() {
	      //this.sender = bundle.getString(Constants.CNF_MAIL_NOREPLY_ADDRESS);
	   }

	public static Mail build() {
		return new Mail();
	}

	public static NewMail build(Collection<String> recipients) {
		NewMail mail = new NewMail();
		mail.recipients(recipients);
		return mail;
	}

	public static NewMail build(String... recipients) {
		NewMail mail = new NewMail();
		mail.recipients(recipients);
		return mail;
	}

	public NewMail sender(String sender) {
		if (!EmailValidator.getInstance().isValid(sender)) {
			throw new IllegalArgumentException("Sender must be a valid e-mail, got=" + sender);
		}
		this.sender = sender;
		return this;
	}

	public NewMail recipient(String recipient) {
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

	public NewMail recipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			recipient(recipient);
		}
		return this;
	}

	public NewMail recipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			recipient(recipient);
		}
		return this;
	}

	public NewMail replyTo(String replyTo) {
		this.headers.put("Reply-To", replyTo);
		return this;
	}

	public NewMail clearRecipients() {
		recipients.clear();
		return this;
	}

	public NewMail ccRecipient(String recipient) {
		if (!EmailValidator.getInstance().isValid(recipient)) {
			throw new IllegalArgumentException("Recipient must be a valid e-mail, got=" + recipient);
		}
		this.ccRecipients.add(recipient);
		return this;
	}

	public NewMail ccRecipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			ccRecipient(recipient);
		}
		return this;
	}

	public NewMail ccRecipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			ccRecipient(recipient);
		}
		return this;
	}

	public NewMail bccRecipient(String recipient) {
		if (StringUtils.isBlank(recipient)) {
			return this;
		}
		if (!EmailValidator.getInstance().isValid(recipient)) {
			throw new IllegalArgumentException("Recipient must be a valid e-mail, got=" + recipient);
		}
		this.bccRecipients.add(recipient);
		return this;
	}

	public NewMail bccRecipients(String... recipients) {
		if (recipients == null || recipients.length == 0) {
			return this;
		}
		for (String recipient : recipients) {
			bccRecipient(recipient);
		}
		return this;
	}

	public NewMail bccRecipients(Collection<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return this;
		}
		for (String recipient : recipients) {
			bccRecipient(recipient);
		}
		return this;
	}

	public NewMail html() {
		this.html = true;
		return this;
	}

	public NewMail subject(String subject) {
		this.subject = subject;
		return this;
	}

	public NewMail content(String content) {
		this.content = content;
		return this;
	}

	public NewMail content(String content, boolean html) {
		this.content = content;
		this.html = html;
		return this;
	}

	public NewMail attachment(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("Filepath must be informed");
		}
		this.attachments.add(new FileAttachment(filePath));
		return this;
	}

	public NewMail attachment(String filePath, String name) {
		if (StringUtils.isBlank(filePath)) {
			throw new IllegalArgumentException("Filepath must be informed");
		}
		this.attachments.add(new FileAttachment(filePath, name));
		return this;
	}

	public NewMail attachment(byte[] data, String name, String extension) {
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
