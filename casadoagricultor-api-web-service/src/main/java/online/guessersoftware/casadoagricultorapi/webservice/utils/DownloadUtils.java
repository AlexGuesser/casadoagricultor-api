package online.guessersoftware.casadoagricultorapi.webservice.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.guessersoftware.casadoagricultorapi.common.constants.Constants;
import online.guessersoftware.casadoagricultorapi.webservice.service.StorageService;

@Component
public class DownloadUtils {

	@Autowired
	private StorageService storage;

	private final Logger log = LogManager.getLogger(getClass());

//	public static void main(String[] args) {
//		List<String> years = Arrays.asList("2017", "2018-1", "2019", "2020", "2021", "2022");
//		for (String year : years) {
//			List<MonthsPortugueseEnum> months = Arrays.asList(MonthsPortugueseEnum.values());
//			for (MonthsPortugueseEnum monthEnum : months) {
//				List<DaysEnum> days = Arrays.asList(DaysEnum.values());
//				for (DaysEnum dayEnum : days) {
//					CotationsDownloadRequest request = //
//							CotationsDownloadRequestBuilder //
//									.usingThis() //
//									.day(dayEnum.getDay()) //
//									.monthNumber(monthEnum.getMonthAsNumber()) //
//									.monthString(monthEnum.getMonthAsString()) //
//									.year(year) //
//									.destinyFolder(Constants.DEFAULT_DESTINY_FOLDER + year + "/") //
//									.baseUrl(Constants.DEFAULT_BASE_URL) //
//									.build(); //
//					DownloadUtils downloader = new DownloadUtils();
//					downloader.downloadCotationsAndSavesOnLocalMachine(request);
//				}
//			}
//		}
//	}

	public void downloadCotationsAndSavesOnLocalMachine(CotationsDownloadRequest request) {
		try {
			log.info("Will try to download and save cotation using request as: " + request);

			String fileFullPathAndName = request.getDestinyFolder() + request.getYear() + Constants.SLASH + request.getMonthNumber() + Constants.SLASH
					+ request.getDay() + Constants.DOT + Constants.PDF;

			// GETS THE HTML OF THE YEAR
			Document yearDoc = Jsoup.connect(request.getBaseUrl() + request.getYear()).get();
			if (yearDoc == null) {
				log.warn("HTML of year not found by url: " + request.getBaseUrl() + request.getYear());
				return;
			}

			// GET THE MONTH'S HTML URL USING TAG FILTERS
			Element monthATag = yearDoc.select("a[href*=" + request.getMonthString() + "]").first();
			if (monthATag == null) {
				log.warn("Link TAG not found inside year HTML for href filter: " + request.getMonthString());
				return;
			}
			String monthUrl = monthATag.attr("abs:href");

			// GETS THE HTML OF THE MONTH
			Document monthDoc = Jsoup.connect(monthUrl).get();
			if (monthDoc == null) {
				log.warn("HTML of month not found by url:" + monthUrl);
				return;
			}

			// GET THE DAY'S COTATION FILE USING TAG FILTERS
			String dayTagFilter = request.getDay() + "-" + request.getMonthNumber();
			Element dayATag = monthDoc.select("a[href*=" + dayTagFilter + "]").first();
			if (dayATag == null) {
				log.warn("Link TAG not found inside month HTML for href filter: " + dayTagFilter);
				return;
			}
			String cotationDayFileUrl = dayATag.attr("abs:href");

			URL url = new URL(cotationDayFileUrl);
			InputStream is = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is);
			PDDocument document = PDDocument.load(fileParse);
			document.save(fileFullPathAndName);
			document.close();
		} catch (Exception e) {
			log.error("Error ocurred while downloading and saving PDF from São José Ceasa using request as " + request + ". Exception: ", e);
		}
	}

	public PDDocument downloadCotationsUploadToGoogle(CotationsDownloadRequest request) {
		try {
			log.info("Will try to download and upload cotation to google using request as: " + request);

			String fileFullName = request.getYear() + Constants.SLASH + request.getMonthNumber() + Constants.SLASH + request.getDay() + Constants.DOT
					+ Constants.PDF;

			// GETS THE HTML OF THE YEAR
			Document yearDoc = Jsoup.connect(request.getBaseUrl() + request.getYear()).get();
			if (yearDoc == null) {
				log.warn("HTML of year not found by url: " + request.getBaseUrl() + request.getYear());
				return null;
			}

			// GET THE MONTH'S HTML URL USING TAG FILTERS
			Element monthATag = yearDoc.select("a[href*=" + request.getMonthString() + "]").first();
			if (monthATag == null) {
				log.warn("Link TAG not found inside year HTML for href filter: " + request.getMonthString());
				return null;
			}
			String monthUrl = monthATag.attr("abs:href");

			// GETS THE HTML OF THE MONTH
			Document monthDoc = Jsoup.connect(monthUrl).get();
			if (monthDoc == null) {
				log.warn("HTML of month not found by url:" + monthUrl);
				return null;
			}

			// GET THE DAY'S COTATION FILE USING TAG FILTERS
			String dayTagFilter = request.getDay() + "-" + request.getMonthNumber();
			Element dayATag = monthDoc.select("a[href*=" + dayTagFilter + "]").first();
			if (dayATag == null) {
				log.warn("Link TAG not found inside month HTML for href filter: " + dayTagFilter);
				return null;
			}
			String cotationDayFileUrl = dayATag.attr("abs:href");

			URL url = new URL(cotationDayFileUrl);
			InputStream is = url.openStream();

			storage.upload(is, fileFullName, request.getDestinyFolder());

			// IF I DON'T OPEN THE INPUT STREAM AGAIN, AN EXCEPTION OCCURS
			InputStream is2 = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is2);
			PDDocument document = PDDocument.load(fileParse);
			return document;
		} catch (Exception e) {
			log.error("Error ocurred while downloading and uploading PDF from São José Ceasa using request as " + request + ". Exception: ", e);
			return null;
		}
	}

}
