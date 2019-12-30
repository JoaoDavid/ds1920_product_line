package devices.events;

import java.time.LocalTime;

import com.bezirk.middleware.messages.Event;

import config.DateTimeFormats;

public class ActivityUpdateEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalTime lastTimeActive;
	
	public ActivityUpdateEvent(LocalTime lastTimeActive) {
		this.lastTimeActive = lastTimeActive;
	}

	public LocalTime getLastTimeActive() {
		return lastTimeActive;
	}
	
	public String toString() {
		return lastTimeActive.format(DateTimeFormats.FORMATTER_TIME);
	}

}
