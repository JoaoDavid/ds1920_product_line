package devices.functionality;

import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import events.ActivityEvent;

public class ActivityTracker {
	
	private Bezirk bezirk;
	
	public ActivityTracker() {
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Wearable's Activity Tracker");		
	}
	
	public void startTrackingActivity() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	ActivityEvent activityEvent = new ActivityEvent(); 
                bezirk.sendEvent(activityEvent);
            }
        }, 1000, 1000);
	}
}
