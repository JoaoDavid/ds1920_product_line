package devices.functionality;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import events.LightSignalEvent;

public class NotificationLED {
	
	private Bezirk bezirk;
	
	public NotificationLED() {
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Wearable's Notification LED");		
	}
	
	public void subscribeLightSingnalEvents() {
		final EventSet lightSignalEvents = new EventSet(LightSignalEvent.class);

		lightSignalEvents.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof LightSignalEvent) {
					//LightSignalEvent lightSignalEvent = (LightSignalEvent) event;
					System.out.println("Light Signal ");
				}			
			}
		});
        bezirk.subscribe(lightSignalEvents);
	}

}
