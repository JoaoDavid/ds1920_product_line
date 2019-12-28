package rules;

import static i18n.Messages.ALERT;
import static i18n.Messages.CHOOSE_CONTACT;
import static i18n.Messages.CONTACT;
import static i18n.Messages.CONTACT_UNAVAILABLE;
import static i18n.Messages.INSERT_NOTIFICATION;
import static i18n.Messages.INVALID_NOTIFICATION;
import static i18n.Messages.CHOOSE_ALERT;
import static i18n.Messages.MOV_ALERT;
import static i18n.Messages.INAC_ALERT;
import static i18n.Messages.UNAVAILABLE_ALERT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import alerts.InactivityAlert;
import alerts.MovementDetectedAlert;
import app.Controller;
import communication.Contact;
import communication.ContactList;
import i18n.I18N;
import ui.Screen;


public aspect ScreenAspect {
	private List<MovementDetectedAlert> Controller.movAlerts = new ArrayList<>();
	private List<InactivityAlert> Controller.inacAlerts = new ArrayList<>();
	
	private List<MovementDetectedAlert> Screen.movAlerts;
	private List<InactivityAlert> Screen.inacAlerts;

	pointcut start(Controller c): within(Controller) && target(c) && call(void startScreen(..));

	before(Controller c): start(c){
		c.getScreen().setMovAlerts(c.getMovAlerts());
		c.getScreen().setInacAlerts(c.getInacAlerts());
	}
	
	public List<MovementDetectedAlert> Controller.getMovAlerts(){
		return this.movAlerts;
	}
	
	public void Screen.setMovAlerts(List<MovementDetectedAlert> movAlerts){
		this.movAlerts = movAlerts;
	}
	
	public List<InactivityAlert> Controller.getInacAlerts(){
		return this.inacAlerts;
	}
	
	public void Screen.setInacAlerts(List<InactivityAlert> inacAlerts){
		this.inacAlerts = inacAlerts;
	}
	
	pointcut input(Screen s): target(s) && call(String getInput(*));

	before(Screen s): input(s){
		System.out.println("	-" + I18N.getString(CONTACT));
		System.out.println("	-" + I18N.getString(ALERT));
	}

	after(Screen s) returning(String in): input(s){
		if(in.equals(I18N.getString(CONTACT))){
			ContactList cs = s.getContacts();
			System.out.println(I18N.getString(CHOOSE_CONTACT));
			for ( Contact c : cs.getContacts()) {
				System.out.println(c.toString());
			}
			changeContact(cs);
		}else if(in.equals(I18N.getString(ALERT))){
			System.out.println(I18N.getString(CHOOSE_ALERT));
			System.out.println("	-" + I18N.getString(MOV_ALERT));
			System.out.println("	-" + I18N.getString(INAC_ALERT));
			addAlert(s);
		}
	}

	private void addAlert(Screen s) {
		Scanner input = new Scanner(System.in);
		boolean valid = false;
		while(!valid){
			String aType = input.nextLine();
			if(aType.equals(I18N.getString(MOV_ALERT))){
				valid = true;
				addMovAlert(s);
			}else if(aType.equals(I18N.getString(INAC_ALERT))){
				valid = true;
				addInacAlert(s);
			}else{
				System.out.println(I18N.getString(UNAVAILABLE_ALERT));
			}
		}
	}

	private void addInacAlert(Screen s) {
		// TODO Auto-generated method stub
		
	}

	private void addMovAlert(Screen s) {
		// TODO Auto-generated method stub
		
	}

	private void changeContact(ContactList contacts){
		boolean selected = false;
		Scanner input = new Scanner(System.in);
		while(!selected){
			String in = input.nextLine();
			boolean valid = contacts.validateContact(in);
			if(!valid){
				System.out.println(I18N.getString(CONTACT_UNAVAILABLE));
			}else{
				selected = true;
				System.out.println(I18N.getString(INSERT_NOTIFICATION));
				boolean settingSelected = false;
				while(!settingSelected){
					String setting = input.nextLine();
					switch(setting){
						case "Y":
							contacts.changeNotifySetting(in, true);
							settingSelected = true;
							break;
						case "N":
							contacts.changeNotifySetting(in, false);
							settingSelected = true;
							break;
						default:
							System.out.println(I18N.getString(INVALID_NOTIFICATION));
					}
				}
			}
		}
	}
	
}
