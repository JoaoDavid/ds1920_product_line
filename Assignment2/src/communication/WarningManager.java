package communication;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WarningManager {
	
	private Timer timer;

	public WarningManager() {
		timer = new Timer();
	}
	
	public void addNewWarning(String message, LocalDateTime from, LocalDateTime to, long period, Contact contact) {
		Date date = Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(LocalDateTime.now().isAfter(to)) {
            		this.cancel();
            	} else {
            		Warning warning = new Warning(message, from, to, period, contact);
            		warning.warn();
            		System.out.println("ENVIAR AVISO");
            	}                
            }
        }, date, period);
	}
	
	

}
