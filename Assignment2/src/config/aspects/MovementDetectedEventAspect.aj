package config.aspects;

import java.time.LocalTime;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import app.Controller;
import communication.alerts.MovementDetectedAlert;
import devices.events.MovementDetectedEvent;

public aspect MovementDetectedEventAspect {
	pointcut subscribe(Controller c): target(c) && call(void startScreen());

	after(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(MovementDetectedEvent.class);
		eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof MovementDetectedEvent) {
					MovementDetectedEvent movementDetectedEvent = (MovementDetectedEvent) event;
					synchronized(c.getMovAlerts()){
						for (MovementDetectedAlert currAlert : c.getMovAlerts()) {
							LocalTime now = LocalTime.now();
							if(currAlert.isTriggered()) {
								if(now.isAfter(currAlert.getEnd())) {
									currAlert.resetTrigger();
								}
								break;
							}
							if(currAlert.getLocation().equals(movementDetectedEvent.getLocation()) && 
									currAlert.happenedBetweenThreshold(movementDetectedEvent.getTime())) {
								currAlert.trigger();
								c.getContacts().notifyDefinedContacts(movementDetectedEvent.toString());
								c.process(movementDetectedEvent.toString());
							}
						}
					}
				}
			}
		});
		c.getBezirk().subscribe(eventSet);
	}
}
