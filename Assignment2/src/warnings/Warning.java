package warnings;

import java.time.LocalDateTime;
import java.time.LocalTime;

import contact.Contact;

public class Warning {
	private String message;
	private LocalDateTime from;
	private LocalDateTime to;
	private LocalTime period;
	private Contact contact;
	
	public Warning (String message, LocalDateTime from, LocalDateTime to, LocalTime period, Contact contact) {
		this.message = message;
		this.from = from;
		this.to = to;
		this.period = period;
		this.contact = contact;
	}
	
	/**
	 * Sends a message to the defined contact
	 */
	public void sendAlertToContact(){
		//TODO
	}
}
