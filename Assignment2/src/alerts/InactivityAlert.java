package alerts;

import java.time.LocalTime;

public class InactivityAlert extends Alert {
	
	/**
	 * In Minutes
	 */
	private long duration;

	public InactivityAlert(LocalTime from, LocalTime to, long duration) {
		super(from, to);
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
	
	

}
