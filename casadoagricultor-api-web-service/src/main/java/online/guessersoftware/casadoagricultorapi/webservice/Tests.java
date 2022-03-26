package online.guessersoftware.casadoagricultorapi.webservice;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class Tests {

	// Find your Account Sid and Token at twilio.com/console
	public static final String ACCOUNT_SID = "ACeed0b8b3485187697c8c979c6920373a";
	public static final String AUTH_TOKEN = "b16589a4e29e15d4fb8d5efea172229f";

	public static void main(String[] args) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator( //
				new com.twilio.type.PhoneNumber("whatsapp:+5548988115548"), //
				new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), //
				"Mensagem de teste automática enviada por alguma aplicação da Guesser Software") //
				.create(); //
		System.out.println(message.getSid());
	}

}
