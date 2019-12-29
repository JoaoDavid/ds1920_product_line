package rules;

import java.time.LocalTime;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import app.Controller;
import communication.alerts.InactivityAlert;
import events.ActivityUpdateEvent;

public aspect ActivityUpdateEventAspect {
	pointcut subscribe(Controller c): target(c) && call(void startScreen());
	
	after(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(ActivityUpdateEvent.class);
        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof ActivityUpdateEvent) {
					ActivityUpdateEvent activityEvent = (ActivityUpdateEvent) event;
					for (InactivityAlert currAlert : c.getInacAlerts()) {
						LocalTime now = LocalTime.now();
						if(currAlert.isTriggered()) {
							if(now.isAfter(currAlert.getEnd())) {
								currAlert.resetTrigger();
							}
							break;
						}
						//esta dentro do intervalo
						if(currAlert.happenedBetweenThreshold(activityEvent.getLastTimeActive())) {
							if(currAlert.happenedBetweenThreshold(now) && 
									now.minusMinutes(currAlert.getDuration()).isAfter(activityEvent.getLastTimeActive())) {
								currAlert.trigger();
								c.getContacts().notifyDefinedContacts("inatividade - " + currAlert.getDuration() + " min - " + currAlert.toString());
								//System.out.println("entrou 1");
							}
						} else if(currAlert.getStart().isAfter(activityEvent.getLastTimeActive())){//esta fora do intervalo
							if(currAlert.happenedBetweenThreshold(now) && 
									now.minusMinutes(currAlert.getDuration()).isAfter(currAlert.getStart())) {
								currAlert.trigger();
								c.getContacts().notifyDefinedContacts("inatividade - " + currAlert.getDuration() + " min - " + currAlert.toString());
								//System.out.println("entrou 2");
							}
						}
					}
				}				
			}
		});
        c.getBezirk().subscribe(eventSet);
	}
}
