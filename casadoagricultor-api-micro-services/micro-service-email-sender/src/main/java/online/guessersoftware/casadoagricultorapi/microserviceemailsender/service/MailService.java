package online.guessersoftware.casadoagricultorapi.microserviceemailsender.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;

@Service
public class MailService {

	private static final Logger log = LogManager.getLogger(MailService.class);

	@Autowired
	private MailSenderService emailSender;

	public boolean sendTestMail() {
		log.info("Sending Test mail");

		Mail mail = Mail.build() //
				.sender(Constants.MAIL_DEFAULT_SENDER) //
				.recipients(Constants.MAIL_DEFAULT_RECEIVER) //
				.bccRecipients(Constants.MAIL_DEFAULT_RECEIVER) //
				.replyTo(Constants.MAIL_DEFAULT_RECEIVER) //
				.subject("Mail Test Subject") //
				.content("Mail Test Content", false); //

		return emailSender.send(mail);
	}

	public void sendEmailToProcessingAdmin(Mail mail) {
		log.info("Sending mail about processing cotation file.");
		emailSender.send(mail);
	}

	public boolean sendEmailByRequest(Mail mail) {
		log.info("Sending mail");
		return emailSender.send(mail);
	}

}
