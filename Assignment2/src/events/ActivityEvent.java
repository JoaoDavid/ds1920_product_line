package events;

import java.time.LocalDateTime;

import com.bezirk.middleware.messages.Event;

public class ActivityEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDateTime time;
	
	public ActivityEvent() {
		time = LocalDateTime.now();
	}

	public LocalDateTime getTime() {
		return time;
	}
	
	public String toString() {
		return time.toString();
	}

}
