package events;

import java.time.LocalDateTime;

import com.bezirk.middleware.messages.Event;

public class ActivityUpdateEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDateTime lastTimeActive;
	
	public ActivityUpdateEvent(LocalDateTime lastTimeActive) {
		this.lastTimeActive = lastTimeActive;
	}

	public LocalDateTime getLastTimeActive() {
		return lastTimeActive;
	}
	
	public String toString() {
		return lastTimeActive.toString();
	}

}
