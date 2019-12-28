package devices.functionality;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import events.ActivityUpdateEvent;

public class ActivityTracker {
	
	private Bezirk bezirk;
	private LocalTime lastTimeActive;
	
	public ActivityTracker() {
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Wearable's Activity Tracker");
        lastTimeActive = LocalTime.now();
	}
	
	public void startTrackingActivity() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	ActivityUpdateEvent activityEvent = new ActivityUpdateEvent(lastTimeActive); 
                bezirk.sendEvent(activityEvent);
            }
        }, 1000, 1000);
	}
	
	public void startMoving() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	lastTimeActive = LocalTime.now();
            	System.err.println("Im moving now " + lastTimeActive);
            }
        }, 1000, 36000000);
	}
}
