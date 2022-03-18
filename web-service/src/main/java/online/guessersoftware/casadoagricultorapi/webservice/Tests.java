package online.guessersoftware.casadoagricultorapi.webservice;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Tests {

	public static void main(String[] args) throws IOException {

		String year = "2020";
		String month = "marco";
		String monthAsNumber = "03";
		String day = "31";
		String slash = "-";
		String dot = ".";
		String pdf = "pdf";

		String fileFullPathAndName = "/home/alex/Desktop/Senai/TCC/Projects/pdfs/" //
				+ day + slash + monthAsNumber + slash + year + dot + pdf;

		// GETS THE HTML OF THE YEAR
		Document yearDoc = Jsoup.connect("https://www.ceasa.sc.gov.br/index.php/cotacao-de-precos/" + year).get();
		// GET THE MONTH'S HTML URL USING TAG FILTERS
		String monthUrl = yearDoc.select("a[href*=" + month + "]").first().attr("abs:href");
		// GETS THE HTML OF THE MONTH
		Document monthDoc = Jsoup.connect(monthUrl).get();
		// GET THE DAY'S COTATION FILE USING TAG FILTERS
		String urlFileDay = monthDoc.select("a[href*=" + day + "-" + monthAsNumber + "]").first().attr("abs:href");

		URL url = new URL(urlFileDay);
		InputStream is = url.openStream();
		BufferedInputStream fileParse = new BufferedInputStream(is);
		PDDocument document = PDDocument.load(fileParse);
		document.save(fileFullPathAndName);
	}

}
