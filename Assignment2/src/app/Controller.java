package app;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import communication.ContactList;
import events.ActivityEvent;
import events.ButtonPressEvent;
import events.LightSignalEvent;
import events.MovementDetectedEvent;

public class Controller {	
	
	private Bezirk bezirk;
	private ContactList contacts;//TODO must be injected

	public Controller() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Controller");
		contacts = new ContactList(); //TODO remove afterwards
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
					contacts.notifyDefinedContacts("Button pressed");
					System.out.println("Button pressed " + buttonPressEvent);
				} else if(event instanceof ActivityEvent) {
					ActivityEvent activityEvent = (ActivityEvent) event;
					System.out.println("Activity Event " + activityEvent);
				}				
			}
		});
        bezirk.subscribe(eventSet);
	}
	
	public void sendLightSignal() {
		final LightSignalEvent lightSignalEvent = new LightSignalEvent();
        bezirk.sendEvent(lightSignalEvent);
	}

	public static void main(String[] args) {
		Controller app = new Controller();
		app.subscribeEvents();
		app.sendLightSignal();
	}

}
