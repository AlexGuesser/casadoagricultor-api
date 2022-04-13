package online.guessersoftware.casadoagricultorapi.webservice;

import org.apache.commons.lang3.StringUtils;

public class Tests {

	public static void main(String[] args) {
		String teste = "  sou     uma string muito -   - - louca ";
		System.out.println(StringUtils.normalizeSpace(teste));
	}

}
