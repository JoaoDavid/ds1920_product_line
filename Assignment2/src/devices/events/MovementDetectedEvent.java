package devices.events;

import static config.i18n.Messages.MOV_LOCATION;

import java.time.LocalTime;

import com.bezirk.middleware.messages.Event;

import config.DateTimeFormats;
import config.i18n.I18N;

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
		return I18N.getString(MOV_LOCATION) +" "+ location + " - " + time.format(DateTimeFormats.FORMATTER_TIME);
	}
}
