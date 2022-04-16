package online.guessersoftware.casadoagricultorapi.webservice.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TypeListEnum {

	NORMAL("Normal"), ORGANICO("Organico"), CONVENCI("Convenci"), CONVENCIONAL("Convencional");

	private String type;

	TypeListEnum(String type) {
		this.type = type;
	}

	public static List<String> getValues() {
		List<TypeListEnum> enumList = Arrays.asList(TypeListEnum.values());
		return enumList.stream().map(typeEnum -> typeEnum.type).collect(Collectors.toList());
	}

}
