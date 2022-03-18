package online.guessersoftware.casadoagricultorapi.webservice.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import online.guessersoftware.casadoagricultorapi.webservice.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.constants.DaysEnum;
import online.guessersoftware.casadoagricultorapi.webservice.constants.MonthsPortugueseEnum;

public class DownloadUtils {

	private final Logger log = LogManager.getLogger(getClass());

	public static void main(String[] args) {
		List<String> years = Arrays.asList("2017", "2018-1", "2019", "2020", "2021", "2022");
		for (String year : years) {
			List<MonthsPortugueseEnum> months = Arrays.asList(MonthsPortugueseEnum.values());
			for (MonthsPortugueseEnum monthEnum : months) {
				List<DaysEnum> days = Arrays.asList(DaysEnum.values());
				for (DaysEnum dayEnum : days) {
					CotationsDownloadRequest request = //
							CotationsDownloadRequestBuilder //
									.usingThis() //
									.day(dayEnum.getDay()) //
									.monthNumber(monthEnum.getMonthAsNumber()) //
									.monthString(monthEnum.getMonthAsString()) //
									.year(year) //
									.destinyFolder(Constants.DEFAULT_DESTINY_FOLDER + year + "/") //
									.baseUrl(Constants.DEFAULT_BASE_URL) //
									.build(); //
					DownloadUtils downloader = new DownloadUtils();
					downloader.downloadCotationsAndSavesOnLocalMachine(request);
				}
			}
		}
	}

	public void downloadCotationsAndSavesOnLocalMachine(CotationsDownloadRequest request) {
		try {
			log.info("Will try to download and save cotation using request as: " + request);

			String fileFullPathAndName = request.getDestinyFolder() + request.getYear() + Constants.SLASH + request.getMonthNumber()
					+ Constants.SLASH + request.getDay() + Constants.DOT + Constants.PDF;

			// GETS THE HTML OF THE YEAR
			Document yearDoc = Jsoup.connect(request.getBaseUrl() + request.getYear()).get();
			if (yearDoc == null) {
				log.warn("HTML of year not found by url: " + request.getBaseUrl() + request.getYear());
				return;
			}

			// GET THE MONTH'S HTML URL USING TAG FILTERS
			String monthUrl = yearDoc.select("a[href*=" + request.getMonthString() + "]").first().attr("abs:href");
			if (StringUtils.isBlank(monthUrl)) {
				log.warn("Month URL not found inside year HTML from: " + request.getBaseUrl() + request.getYear());
				return;
			}

			// GETS THE HTML OF THE MONTH
			Document monthDoc = Jsoup.connect(monthUrl).get();
			if (monthDoc == null) {
				log.warn("HTML of month not found by url:" + monthUrl);
				return;
			}

			// GET THE DAY'S COTATION FILE USING TAG FILTERS
			String tagFilter = request.getDay() + "-" + request.getMonthNumber();
			Element aTag = monthDoc.select("a[href*=" + tagFilter + "]").first();
			if (aTag == null) {
				log.warn("Link TAG not found for href filter: " + tagFilter);
				return;
			}
			String cotationDayFileUrl = aTag.attr("abs:href");

			URL url = new URL(cotationDayFileUrl);
			InputStream is = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is);
			PDDocument document = PDDocument.load(fileParse);
			document.save(fileFullPathAndName);
		} catch (Exception e) {
			log.error("Error ocurred while downloading and saving PDF from São José Ceasa using request as " + request + ". Exception: ", e);
		}
	}

}
