package app;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import events.ActivityEvent;
import events.ButtonPressEvent;
import events.MovementDetectedEvent;

public class App {	
	
	private Bezirk bezirk;

	public App() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("App");
	}
	
	public void subscribeEvents() {
		final EventSet eventSet = new EventSet(MovementDetectedEvent.class, ButtonPressEvent.class, ActivityEvent.class);

        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof MovementDetectedEvent) {
					MovementDetectedEvent movementDetectedEvent = (MovementDetectedEvent) event;
					System.out.println(movementDetectedEvent);
				} else if(event instanceof ButtonPressEvent) {
					ButtonPressEvent buttonPressEvent = (ButtonPressEvent) event;
					System.out.println("Button pressed " + buttonPressEvent);
				} else if(event instanceof ActivityEvent) {
					ActivityEvent activityEvent = (ActivityEvent) event;
					System.out.println("Activity Event " + activityEvent);
				}				
			}
		});
        bezirk.subscribe(eventSet);
	}

	public static void main(String[] args) {
		App app = new App();
		app.subscribeEvents();
	}

}
