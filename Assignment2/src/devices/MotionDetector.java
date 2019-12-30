package devices;

import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import config.i18n.I18N;
import config.i18n.Messages;
import devices.events.MovementDetectedEvent;

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
                System.out.println(I18N.getString(Messages.PUBLISHED) + movementDetectedEvent);
            }
        }, 1000, 5000);
	}
	
	public static void main(String[] args) {
		if(args.length == 1) {
			MotionDetector motionDetector = new MotionDetector(args[0]);
		motionDetector.startMonitoringMovement();
		} else {
			System.err.println(I18N.getString(Messages.MD_ERROR));
			System.err.println("example: Cozinha");
		}		
	}

}
