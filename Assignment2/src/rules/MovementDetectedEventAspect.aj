package rules;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import alerts.MovementDetectedAlert;
import app.Controller;
import events.MovementDetectedEvent;

public aspect MovementDetectedEventAspect {
	pointcut subscribe(Controller c): target(c) && call(void subscribeEvents());
	
	before(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(MovementDetectedEvent.class);
        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof MovementDetectedEvent) {
					MovementDetectedEvent movementDetectedEvent = (MovementDetectedEvent) event;
					for (MovementDetectedAlert currAlert : c.getMovAlerts()) {
						if(currAlert.getLocation().equals(movementDetectedEvent.getLocation()) && 
								currAlert.happenedBetweenThreshold(movementDetectedEvent.getTime())) {
							c.getContacts().notifyDefinedContacts(movementDetectedEvent.toString());
						}
					}
					System.out.println(movementDetectedEvent);
				}
			}
		});
        c.getBezirk().subscribe(eventSet);
	}
}