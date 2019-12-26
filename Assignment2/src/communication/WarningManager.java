package communication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WarningManager {
	
	private Timer timer;

	public WarningManager() {
		timer = new Timer();
	}
	
	/**
	 * Adds a new warning to be sent every period milliseconds
	 * 
	 * @param message
	 * @param from
	 * @param to
	 * @param period frequency of the warning being sent, in milliseconds 
	 * @param contact
	 */
	public void addNewWarning(String message, LocalDateTime from, LocalDateTime to, long period, Contact contact) {
		Date date = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(LocalDateTime.now().isAfter(to)) {
            		this.cancel();
            	} else {          
            		String message = "08:00 - Tomar antibiótico";
            		contact.sendWarning(message);
            	}                
            }
        }, date, period);
	}
	
	

}
