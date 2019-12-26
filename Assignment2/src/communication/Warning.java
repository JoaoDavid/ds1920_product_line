package communication;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Warning {
	
	private String message;
	private LocalDateTime from;
	private LocalDateTime to;
	private long period;
	private Contact contact;
	
	public Warning (String message, LocalDateTime from, LocalDateTime to, long period, Contact contact) {
		this.message = message;
		this.from = from;
		this.to = to;
		this.period = period;
		this.contact = contact;
	}
	
	public void warn(){
		String message = "08:00 - Tomar antibiótico";
		contact.sendWarning(message);
		System.out.println(message);
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public long getPeriod() {
		return period;
	}
	
	
}
