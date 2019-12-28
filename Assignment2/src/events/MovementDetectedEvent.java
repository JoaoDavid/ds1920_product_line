package events;

import java.time.LocalTime;

import com.bezirk.middleware.messages.Event;

import config.DateTimeFormats;

public class MovementDetectedEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String location;
	private LocalTime time;
	
	public MovementDetectedEvent(String location) {
		this.location = location;
		time = LocalTime.now();
	}

	public String getLocation() {
		return location;
	}

	public LocalTime getTime() {
		return time;
	}
	
	public String toString() {
		return location + " - " + time.format(DateTimeFormats.FORMATTER_TIME);
	}
}
