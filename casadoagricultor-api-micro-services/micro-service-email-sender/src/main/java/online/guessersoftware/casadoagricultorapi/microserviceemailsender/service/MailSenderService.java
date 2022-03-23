package online.guessersoftware.casadoagricultorapi.microserviceemailsender.service;

import java.util.Date;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;

@Service
public class MailSenderService {

	private static final Logger log = LogManager.getLogger(MailSenderService.class);

	@Autowired
	private JavaMailSender emailSender;

	public boolean send(Mail mail) {
		try {
			final MimeMessage msg = emailSender.createMimeMessage();
			msg.setFrom(mail.getSender());
			msg.setRecipients(RecipientType.TO, mail.getRecipients());
			if (ArrayUtils.isNotEmpty(mail.getCcRecipients())) {
				msg.setRecipients(RecipientType.CC, mail.getCcRecipients());
			}
			if (ArrayUtils.isNotEmpty(mail.getBccRecipients())) {
				msg.setRecipients(RecipientType.BCC, mail.getBccRecipients());
			}

			msg.setSentDate(new Date());
			msg.setSubject(javax.mail.internet.MimeUtility.encodeText(mail.getSubject(), Constants.CNF_MAIL_SERVER_CHARSET, null));
			fillHeaders(msg, mail);
			fillContent(msg, mail);

			log.debug("Trying to send mail from=" + mail.getSenderAsString() + " to=" + mail.getRecipientsAsString() + " cc=" + mail.getCcRecipientsAsString()
					+ " bcc=" + mail.getBccRecipientsAsString() + " with subject=" + mail.getSubject());
			emailSender.send(msg);
			log.info("Successfully sent mail from=" + mail.getSenderAsString() + " to=" + mail.getRecipientsAsString() + " cc=" + mail.getCcRecipientsAsString()
					+ " bcc=" + mail.getBccRecipientsAsString() + " with subject=" + mail.getSubject());
			return true;
		} catch (Exception e) {
			log.error("Error while trying to send mail from=" + mail.getSenderAsString() + " to=" + mail.getRecipientsAsString() + " cc="
					+ mail.getCcRecipientsAsString() + " bcc=" + mail.getBccRecipientsAsString() + " with subject=" + mail.getSubject(), e);
			return false;
		}
	}

	private void fillHeaders(MimeMessage msg, Mail newMail) {
		if (newMail.getHeaders() == null || newMail.getHeaders().keySet().isEmpty()) {
			return;
		}
		for (String headerName : newMail.getHeaders().keySet()) {
			try {
				msg.setHeader(headerName, newMail.getHeaders().get(headerName));
			} catch (Exception e) {
				log.error("Error setting mail header " + headerName);
			}
		}
	}

	private void fillContent(MimeMessage msg, Mail newMail) throws Exception {
		if (CollectionUtils.isEmpty(newMail.getAttachments())) {
			if (newMail.getHtml()) {
				msg.setContent(StringUtils.defaultString(newMail.getContent()), "text/html; charset=\"" + Constants.CNF_MAIL_SERVER_CHARSET + "\"");
			} else {
				msg.setText(StringUtils.defaultString(newMail.getContent()), Constants.CNF_MAIL_SERVER_CHARSET);
			}
		} else {
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			if (newMail.getHtml()) {
				mbp1.setContent(StringUtils.defaultString(newMail.getContent()), "text/html; charset=\"" + Constants.CNF_MAIL_SERVER_CHARSET + "\"");
			} else {
				mbp1.setText(StringUtils.defaultString(newMail.getContent()), Constants.CNF_MAIL_SERVER_CHARSET);
			}
			mp.addBodyPart(mbp1);
			for (Mail.Attachment attach : newMail.getAttachments()) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				mbp2.setDataHandler(new DataHandler(attach.getDataSource()));
				mbp2.setFileName(attach.getName() + "." + attach.getExtension());
				mp.addBodyPart(mbp2);
			}
			msg.setContent(mp);
		}
	}

}
