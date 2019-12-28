package rules;

import devices.Wearable;
import devices.functionality.ActivityTracker;

public aspect ActivityTrackerAspect {
	pointcut inDevice(): within(Wearable);
	
	before(): inDevice() && execution(* *.main(..)){
		ActivityTracker activityTracker = new ActivityTracker();
		activityTracker.startTrackingActivity();
		activityTracker.startMoving();
	}
}
