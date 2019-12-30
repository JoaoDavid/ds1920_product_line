package config.aspects;

import static config.i18n.Messages.BUTTON_PRESSED;

import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import app.Controller;
import config.DateTimeFormats;
import config.i18n.I18N;
import devices.events.ButtonPressEvent;

public aspect ButtonPressEventAspect {

	pointcut subscribe(Controller c): target(c) && call(void startScreen());
	
	after(Controller c): subscribe(c){
		EventSet eventSet = new EventSet(ButtonPressEvent.class);
        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof ButtonPressEvent) {
					ButtonPressEvent buttonPressEvent = (ButtonPressEvent) event;
					String message = I18N.getString(BUTTON_PRESSED) + " " + buttonPressEvent.getMoment().format(DateTimeFormats.FORMATTER_DATE_TIME);
					c.getContacts().notifyDefinedContacts(message);
					c.process(message);
					System.out.println(message);
				}	
			}
		});
        c.getBezirk().subscribe(eventSet);
	}
	
	public void Controller.process(String msg){
		//empty method
	}
}
