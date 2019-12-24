package devices;

import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import events.MovementDetectedEvent;

public class MotionDetector {
	
	private Bezirk bezirk;
	private String location;
	
	public MotionDetector(String location) {
		this.location = location;
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Motion Detector");		
	}
	
	public void startMonitoringMovement() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	MovementDetectedEvent movementDetectedEvent = 
                		new MovementDetectedEvent(location); 
                bezirk.sendEvent(movementDetectedEvent);
                System.out.println("Published event " + movementDetectedEvent);
            }
        }, 1000, 5000);
	}
	
	public static void main(String[] args) {
		MotionDetector motionDetector = new MotionDetector("Cozinha");
		motionDetector.startMonitoringMovement();
	}

}
