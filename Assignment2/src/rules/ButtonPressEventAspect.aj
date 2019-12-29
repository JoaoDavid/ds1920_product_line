package rules;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import app.Controller;
import events.ButtonPressEvent;

public aspect ButtonPressEventAspect {

	pointcut subscribe(Controller c): target(c) && call(void startScreen());
	
	after(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(ButtonPressEvent.class);
        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof ButtonPressEvent) {
					ButtonPressEvent buttonPressEvent = (ButtonPressEvent) event;
					c.getContacts().notifyDefinedContacts("Button pressed");
					System.out.println("Button pressed " + buttonPressEvent);
				}	
			}
		});
        c.getBezirk().subscribe(eventSet);
	}
}
