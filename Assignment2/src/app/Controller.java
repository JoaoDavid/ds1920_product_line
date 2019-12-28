package app;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.addressing.ZirkEndPoint;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;
import com.bezirk.middleware.messages.Event;
import com.bezirk.middleware.messages.EventSet;

import alerts.InactivityAlert;
import alerts.MovementDetectedAlert;
import communication.Contact;
import communication.ContactList;
import communication.WarningManager;
import events.ActivityUpdateEvent;
import events.ButtonPressEvent;
import events.LightSignalEvent;
import events.MovementDetectedEvent;
import ui.Screen;

public class Controller {	
	
	private Bezirk bezirk;
	private ContactList contacts;//TODO must be injected
	private WarningManager warnMan;
	private List<MovementDetectedAlert> movAlerts;
	private List<InactivityAlert> inacAlerts;
	private Screen screen;

	public Controller() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Controller");
		contacts = new ContactList(); //TODO remove afterwards
		warnMan = new WarningManager();
		movAlerts = new ArrayList<MovementDetectedAlert>();
		inacAlerts = new ArrayList<InactivityAlert>();
		inacAlerts.add(new InactivityAlert(LocalTime.of(15, 00), LocalTime.of(22, 00), 1));
		movAlerts.add(new MovementDetectedAlert(LocalTime.of(9, 0), LocalTime.of(23, 59), "Cozinha"));
		warnMan.addNewWarning("ola", LocalDateTime.of(2019, 12, 27, 14, 05), LocalDateTime.of(2019, 12, 27, 14, 06), 10000, new Contact("teste", 1, true));
		warnMan.addNewWarning("ola", LocalDateTime.of(2019, 12, 27, 14, 04), LocalDateTime.of(2019, 12, 27, 14, 04), 10000, new Contact("teste2", 1, true));
		screen = new Screen(contacts);
	}
	
	public void startScreen(){
		this.screen.start();
	}
	
	public Screen getScreen(){
		return this.screen;
	}
		
	public void subscribeEvents() {
		final EventSet eventSet = new EventSet(MovementDetectedEvent.class, ButtonPressEvent.class, ActivityUpdateEvent.class);

        eventSet.setEventReceiver(new EventSet.EventReceiver() {			
			@Override
			public void receiveEvent(Event event, ZirkEndPoint sender) {
				if(event instanceof MovementDetectedEvent) {
					MovementDetectedEvent movementDetectedEvent = (MovementDetectedEvent) event;
					for (MovementDetectedAlert currAlert : movAlerts) {
						if(currAlert.getLocation().equals(movementDetectedEvent.getLocation()) && 
								currAlert.happenedBetweenThreshold(movementDetectedEvent.getTime())) {
							contacts.notifyDefinedContacts(movementDetectedEvent.toString());
						}
					}
					System.out.println(movementDetectedEvent);
				} else if(event instanceof ButtonPressEvent) {
					ButtonPressEvent buttonPressEvent = (ButtonPressEvent) event;
					contacts.notifyDefinedContacts("Button pressed");
					System.out.println("Button pressed " + buttonPressEvent);
				} else if(event instanceof ActivityUpdateEvent) {
					ActivityUpdateEvent activityEvent = (ActivityUpdateEvent) event;
					for (InactivityAlert currAlert : inacAlerts) {
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
								contacts.notifyDefinedContacts("inatividade - " + currAlert.getDuration() + " min - " + currAlert.toString());
								System.out.println("entrou 1");
							}
						} else if(currAlert.getStart().isAfter(activityEvent.getLastTimeActive())){//esta fora do intervalo
							if(currAlert.happenedBetweenThreshold(now) && 
									now.minusMinutes(currAlert.getDuration()).isAfter(currAlert.getStart())) {
								currAlert.trigger();
								contacts.notifyDefinedContacts("inatividade - " + currAlert.getDuration() + " min - " + currAlert.toString());
								System.out.println("entrou 2");
							}
						}
					}
					//System.out.println("Activity Update Event " + activityEvent);
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
		app.startScreen();
		app.subscribeEvents();
		app.sendLightSignal();
	}
}
