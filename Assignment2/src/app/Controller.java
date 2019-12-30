package app;

import java.time.LocalDateTime;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import app.ui.Screen;
import app.ui.SynthetizedVoice;
import communication.Contact;
import communication.ContactList;
import communication.warnings.WarningManager;

public class Controller {	
	
	private Bezirk bezirk;
	private ContactList contacts;
	private WarningManager warnMan;
	//TODO REMOVE THESE TWO LISTS
	private Screen screen;

	public Controller() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("Controller");
		contacts = new ContactList();
		warnMan = new WarningManager();
		screen = new Screen(contacts,warnMan);
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
	
	public void process(String msg){
		//emoty
	}
}
