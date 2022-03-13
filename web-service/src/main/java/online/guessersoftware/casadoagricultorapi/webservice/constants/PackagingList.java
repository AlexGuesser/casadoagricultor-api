package online.guessersoftware.casadoagricultorapi.webservice.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PackagingList {

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

	private PackagingList(String packing) {
		this.packing = packing;
	}

	public static List<String> getValues() {
		List<PackagingList> enumList = Arrays.asList(PackagingList.values());
		return enumList.stream().map(packingEnum -> packingEnum.packing).collect(Collectors.toList());
	}

}
