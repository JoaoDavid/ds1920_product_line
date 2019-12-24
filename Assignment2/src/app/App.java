package app;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import events.MovementDetectedEvent;

public class App {	

	public App() {
		BezirkMiddleware.initialize();
		Bezirk bezirk = BezirkMiddleware.registerZirk("App");	
        final EventSet movementDetectedEvents = new EventSet(MovementDetectedEvent.class);

        movementDetectedEvents.setEventReceiver(new EventSet.EventReceiver() {
			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof MovementDetectedEvent) {
					MovementDetectedEvent movementDetectedEvent = (MovementDetectedEvent) event;
					System.out.println(movementDetectedEvent);
				}
				
			}
		});
        bezirk.subscribe(movementDetectedEvents);
	}



	public static void main(String[] args) {
		App app = new App();
	}

}
