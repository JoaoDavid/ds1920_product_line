package devices;

import devices.functionality.ActivityTracker;
import devices.functionality.Button;

public class Wearable {
	
	public static void main(String[] args) {
		ActivityTracker activityTracker = new ActivityTracker();
		activityTracker.startTrackingActivity();
		
		Button button = new Button();
		button.pressButtonPeriodicly();
	}
}
