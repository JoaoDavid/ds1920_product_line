package config.aspects;

import java.time.LocalTime;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import app.Controller;
import communication.alerts.InactivityAlert;
import config.i18n.I18N;
import config.i18n.Messages;
import devices.events.ActivityUpdateEvent;

public aspect ActivityUpdateEventAspect {
	pointcut subscribe(Controller c): target(c) && call(void startScreen());

	after(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(ActivityUpdateEvent.class);
		eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof ActivityUpdateEvent) {
					ActivityUpdateEvent activityEvent = (ActivityUpdateEvent) event;
					synchronized(c.getInacAlerts()){
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
									String msg = I18N.getString(Messages.INACTIVITY) + " "+ currAlert.getDuration() + " min - " + currAlert.toString();
									c.getContacts().notifyDefinedContacts(msg);
									c.process(msg);
									//System.out.println("entrou 1");
								}
							} else if(currAlert.getStart().isAfter(activityEvent.getLastTimeActive())){//esta fora do intervalo
								if(currAlert.happenedBetweenThreshold(now) && 
										now.minusMinutes(currAlert.getDuration()).isAfter(currAlert.getStart())) {
									currAlert.trigger();
									String msg = I18N.getString(Messages.INACTIVITY) + " " + currAlert.getDuration() + " min - " + currAlert.toString();
									c.getContacts().notifyDefinedContacts(msg);
									c.process(msg);
									//System.out.println("entrou 2");
								}
							}
						}
					}
				}				
			}
		});
		c.getBezirk().subscribe(eventSet);
	}
}
