package online.guessersoftware.casadoagricultorapi.common.constants;

public class Constants {

	public static final String DATE_REGEX = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
	public static final String NUMBERS = "0123456789";
	public static final String TECH_JOB_PROCESSOR_NAME = "tech_job_processor";
	public static final String DOT = ".";
	public static final String SLASH = "-";
	public static final String PDF = "pdf";
	public static final String DEFAULT_DESTINY_FOLDER = "/home/alex/Desktop/Senai/TCC/Projects/pdfs/";
	public static final String DEFAULT_BUCKET_FOLDER = "gs://casa-do-agricultor-cotations-files/";
	public static final String DEFAULT_BASE_URL = "https://www.ceasa.sc.gov.br/index.php/cotacao-de-precos/";
	public static final String MAIL_REGEX_VALIDATOR = "[_A-Za-z0-9!#$%&'*+-/=?^_`{|}~\\+]+(\\.[_A-Za-z0-9!#$%&'*+-/=?^_`{|}~]+)*@[A-Za-z0-9]+[A-Za-z0-9-]*(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
	public static final String CNF_MAIL_SERVER_CHARSET = "UTF-8";
	public static final String NEWLINE = "<br/>";
	public static final String LOCAL = "LOCAL";
	public static final String CLOUD = "CLOUD";
	
	public static final String MAIL_DEFAULT_SENDER = "alex.guesser@lagoasoft.com.br";
	public static final String MAIL_DEFAULT_RECEIVER = "alex.guesser@hotmail.com";
	public static final String MAIL_PROCESSING_ADMIN_RECEIVER = "alex.guesser@hotmail.com";
	public static final String MAIL_PROCESSING_SUBJECT_BASE = "CASA DO AGRICULTOR COTATION PROCESSING - ";
	public static final String MAIL_PROCESSING_SUBJECT_BASE_NO_COTATION = "CASA DO AGRICULTOR COTATION PROCESSING NO COTATION FILE FOUND";


}
