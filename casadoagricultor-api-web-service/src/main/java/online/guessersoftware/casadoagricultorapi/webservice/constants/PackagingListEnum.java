package online.guessersoftware.casadoagricultorapi.webservice.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PackagingListEnum {

	CAIXA("Caixa"), //
	UNIDADE("Unidade"), //
	KILO("Kilo"), //
	SACO("Saco"), //
	MACO("Maço"), //
	MOLHO("Molho"), //
	BANDEJA("Bandeja"), //
	LITRO("Litro"), //
	DUZIA("Dúzia"), //
	FARDO("Fardo");

	private String packing;

	private PackagingListEnum(String packing) {
		this.packing = packing;
	}

	public static List<String> getValues() {
		List<PackagingListEnum> enumList = Arrays.asList(PackagingListEnum.values());
		return enumList.stream().map(packingEnum -> packingEnum.packing).collect(Collectors.toList());
	}

}
