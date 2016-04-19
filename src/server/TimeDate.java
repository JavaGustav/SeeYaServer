package server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Just to return date and time.
 * @author Gustav Frigren
 *
 */
public class TimeDate {
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	
	public String getDateTime() {
		Date date = new Date();
		String dateTime = df.format(date);
		return dateTime;
	}

}