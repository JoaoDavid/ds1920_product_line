package config;

import java.time.format.DateTimeFormatter;

public class DateTimeFormats {
	
	public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern("HH:mm");
	public static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	public static final DateTimeFormatter FORMATTER_DATE_TIME2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	

}
