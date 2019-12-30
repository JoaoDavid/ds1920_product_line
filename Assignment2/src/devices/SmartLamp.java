package devices;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import devices.events.LightSignalEvent;

public class SmartLamp {
	
	private Bezirk bezirk;
	private String location;
	
	public SmartLamp(String location) {
		this.location = location;
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Smart Lamp");		
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
	
	public String getLocation() {
		return location;
	}

	public static void main(String[] args) {
		if(args.length == 1) {
			SmartLamp smartLamp = new SmartLamp(args[0]);
			smartLamp.subscribeLightSingnalEvents();
		} else {
			System.err.println("Add location as argument");
			System.err.println("example: Bedroom");
		}	
		
	}

}
