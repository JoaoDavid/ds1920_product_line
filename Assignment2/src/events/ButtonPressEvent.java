package events;

import java.time.LocalDateTime;

import com.bezirk.middleware.messages.Event;

import config.DateTimeFormats;

public class ButtonPressEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final LocalDateTime moment;

	public ButtonPressEvent() {
		moment = LocalDateTime.now();
	}

	public LocalDateTime getMoment() {
		return moment;
	}
	
	public String toString() {
		return moment.format(DateTimeFormats.FORMATTER_DATE_TIME);
	}

}
