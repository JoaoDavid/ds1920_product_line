package alerts;

import java.time.LocalTime;

public class Alert {
	
	private LocalTime start;
	private LocalTime end;
	
	public Alert(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}	
	
	public boolean happenedBetweenThreshold(LocalTime moment) {
		return moment.isAfter(start) && moment.isBefore(end);
	}

}
