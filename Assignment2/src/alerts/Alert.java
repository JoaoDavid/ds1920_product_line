package alerts;

import java.time.LocalTime;

import config.DateTimeFormats;

public class Alert {
	
	private LocalTime start;
	private LocalTime end;
	private boolean triggered;
	
	public Alert(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
		this.triggered = false;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}	
	
	public boolean isTriggered() {
		return triggered;
	}
	
	public void trigger() {
		this.triggered = true;
	}

	public void resetTrigger() {
		this.triggered = false;
	}
	
	public boolean happenedBetweenThreshold(LocalTime moment) {
		return moment.isAfter(start) && moment.isBefore(end);
	}
	
	public String toString() {
		return "[" + start.format(DateTimeFormats.FORMATTER_TIME) + ","
	+ end.format(DateTimeFormats.FORMATTER_TIME) + "]";
	}

}
