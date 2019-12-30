package app;

import java.time.LocalDateTime;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import app.ui.Screen;
import app.ui.SynthetizedVoice;
import communication.Contact;
import communication.ContactList;
import communication.warnings.WarningManager;
import devices.events.LightSignalEvent;

public class Controller {	
	
	private Bezirk bezirk;
	private ContactList contacts;
	private WarningManager warnMan;
	//TODO REMOVE THESE TWO LISTS
	private Screen screen;

	public Controller() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Controller");
		contacts = new ContactList(); //TODO remove afterwards
		warnMan = new WarningManager();
		warnMan.addNewWarning("Tomar antibiotico", LocalDateTime.of(2019, 12, 29, 20, 30), LocalDateTime.of(2019, 12, 29, 23, 00), 10000, new Contact("teste", 1, true));
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
	
	public static void main(String[] args) {
		SynthetizedVoice voice = SynthetizedVoice.getInstance();
		voice.playVoice("vamos a praia");
		voice.playVoice("vamos a praia");
		voice.playVoice("vamos a praia");
		Controller app = new Controller();
		app.startScreen();
	}
}
