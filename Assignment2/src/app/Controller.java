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
import ui.SynthetizedVoice;

public class Controller {	
	
	private Bezirk bezirk;
	private ContactList contacts;
	private WarningManager warnMan;
	//TODO REMOVE THESE TWO LISTS
	//private List<MovementDetectedAlert> movAlerts;
	//private List<InactivityAlert> inacAlerts;
	private Screen screen;

	public Controller() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Controller");
		contacts = new ContactList(); //TODO remove afterwards
		warnMan = new WarningManager();
		//movAlerts = new ArrayList<MovementDetectedAlert>();
		//inacAlerts = new ArrayList<InactivityAlert>();
		//inacAlerts.add(new InactivityAlert(LocalTime.of(15, 00), LocalTime.of(22, 00), 1));
		//movAlerts.add(new MovementDetectedAlert(LocalTime.of(9, 0), LocalTime.of(23, 59), "Cozinha"));
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
		
	public Bezirk getBezirk(){
		return this.bezirk;
	}
	
	public ContactList getContacts(){
		return this.contacts;
	}
	
	public void subscribeEvents() {
		//TEST EMPTY METHOD
	}
	
	public void sendLightSignal() {
		final LightSignalEvent lightSignalEvent = new LightSignalEvent();
        bezirk.sendEvent(lightSignalEvent);
	}

	public static void main(String[] args) {
		SynthetizedVoice voice = new SynthetizedVoice("pt");
		voice.playVoice("vamos a praia");
		voice.playVoice("vamos a praia");
		voice.playVoice("vamos a praia");
		voice.playVoice("vamos a praia");
		voice.start();
		Controller app = new Controller();
		app.startScreen();
		app.subscribeEvents();
		app.sendLightSignal();
	}
}
