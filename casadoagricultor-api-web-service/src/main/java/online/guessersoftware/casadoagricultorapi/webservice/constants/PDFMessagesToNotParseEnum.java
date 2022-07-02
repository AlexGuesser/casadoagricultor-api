package online.guessersoftware.casadoagricultorapi.webservice.constants;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum PDFMessagesToNotParseEnum {

	TERAYDE("Teradyne Brasil -  www.teradynebrasil.com.br - Smart solutions on the web"), //
	CEASA_CONTACT("www.ceasa.sc.gov.br - e-mail : ceasa@ceasa.sc.gov.br"), //
	CEASA_CONTACT_2(
			"Unidade São José : Br 101, km 205, Barreiros, São José - CEP 88117-901 - Telefone +55 (48) 3378-1700"), //
	HEADERS("Classificação EmbalagemTipo Origem Comum"), //
	GOVERNMENT("Governo do Estado de Santa Catarina"), //
	SAR("Secretaria de Estado da Agricultura e da Pesca - SAR"), //
	COTATIONS_REPORT("Relatório de Cotações do dia :"), //
	CEASA_SC("Centrais de Abastecimento do Estado de Santa Catarina - CEASA/SC"), //
	CEASA_SC_2("Centrais de Abastecimento do Estado de Santa Catarina -");

	private String text;

	private PDFMessagesToNotParseEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static boolean contains(String line) {
		List<PDFMessagesToNotParseEnum> enumList = Arrays.asList(PDFMessagesToNotParseEnum.values());
		return enumList.stream().anyMatch(enumElement -> {
			return StringUtils.equals(enumElement.getText(), line);
		});
	}

}
