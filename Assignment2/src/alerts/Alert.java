package alerts;

import java.time.LocalTime;

public class Alert {
	
	private LocalTime from;
	private LocalTime to;
	
	public Alert(LocalTime from, LocalTime to) {
		this.from = from;
		this.to = to;
	}

	public LocalTime getFrom() {
		return from;
	}

	public LocalTime getTo() {
		return to;
	}	
	
	public boolean happenedBetweenThreshold(LocalTime moment) {
		return moment.isAfter(from) && moment.isBefore(to);
	}

}
