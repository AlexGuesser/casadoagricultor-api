package online.guessersoftware.casadoagricultorapi.webservice.transformer;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.microserviceemailsender.service.Mail;
import online.guessersoftware.casadoagricultorapi.webservice.json.MailRequestJson;

public class MailTransformer {

	public static Mail transformToMail(MailRequestJson json) {
		return Mail.build() //
				.sender(Constants.MAIL_DEFAULT_SENDER) //
				.recipient(json.getToEmail()) //
				.replyTo(json.getReplyToEmail()) //
				.subject(json.getSubject()) //
				.content(buildContent(json));// ;
	}

	private static String buildContent(MailRequestJson json) {
		return "Mensagem enviada por: " + json.getName() + " " + json.getLastName() + ".\nMensagem:\n" + json.getMessage();
	}

}
