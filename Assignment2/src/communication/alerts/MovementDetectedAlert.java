package communication.alerts;

import java.time.LocalTime;

public class MovementDetectedAlert extends Alert {
	
	private String location;

	public MovementDetectedAlert(LocalTime from, LocalTime to, String location) {
		super(from, to);
		this.location = location;
	}

	public String getLocation() {
		return location;
	}	

}
