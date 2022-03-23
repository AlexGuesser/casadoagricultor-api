package online.guessersoftware.casadoagricultorapi.webservice.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TypeList {

	NORMAL("Normal"), ORGANICO("Organico");

	private String type;

	TypeList(String type) {
		this.type = type;
	}

	public static List<String> getValues() {
		List<TypeList> enumList = Arrays.asList(TypeList.values());
		return enumList.stream().map(typeEnum -> typeEnum.type).collect(Collectors.toList());
	}

}
