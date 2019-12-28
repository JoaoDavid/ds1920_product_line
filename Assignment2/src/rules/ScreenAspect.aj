package rules;

import static i18n.Messages.ALERT;
import static i18n.Messages.CHOOSE_ALERT;
import static i18n.Messages.CHOOSE_CONTACT;
import static i18n.Messages.CONTACT;
import static i18n.Messages.CONTACT_UNAVAILABLE;
import static i18n.Messages.INAC_ALERT;
import static i18n.Messages.INAC_END;
import static i18n.Messages.INAC_PERIOD;
import static i18n.Messages.INAC_START;
import static i18n.Messages.INSERT_NOTIFICATION;
import static i18n.Messages.INVALID_INAC_END;
import static i18n.Messages.INVALID_INAC_PERIOD;
import static i18n.Messages.INVALID_INAC_START;
import static i18n.Messages.INVALID_MOV_END;
import static i18n.Messages.INVALID_MOV_START;
import static i18n.Messages.INVALID_NOTIFICATION;
import static i18n.Messages.MOV_ALERT;
import static i18n.Messages.MOV_END;
import static i18n.Messages.MOV_PLACE;
import static i18n.Messages.MOV_START;
import static i18n.Messages.UNAVAILABLE_ALERT;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
	
	public void Screen.addInacAlert(InactivityAlert a){
		this.inacAlerts.add(a);
	}
	
	public void Screen.addMovAlert(MovementDetectedAlert a){
		this.movAlerts.add(a);
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

	/**
	 * Reads input given and adds a specified alert
	 * @param s - Screen containing the information
	 */
	private void addAlert(Screen s) {
		Scanner input = new Scanner(System.in);
		boolean valid = false;
		while(!valid){
			String aType = input.nextLine();
			if(aType.equals(I18N.getString(MOV_ALERT))){
				valid = true;
				addMovAlert(s, input);
			}else if(aType.equals(I18N.getString(INAC_ALERT))){
				valid = true;
				addInacAlert(s, input);
			}else{
				System.out.println(I18N.getString(UNAVAILABLE_ALERT));
			}
		}
	}

	/**
	 * Adds an Inactivity alert pattern
	 */
	private void addInacAlert(Screen s, Scanner in) {
		boolean valid = false;
		int period = 0;
		while(!valid){
			System.out.println(I18N.getString(INAC_PERIOD));
			try{
				period = Integer.parseInt(in.nextLine());
				valid = true;
			}catch(NumberFormatException e){
				System.out.println(I18N.getString(INVALID_INAC_PERIOD));
			}
		}
		//TODO refractor both while codes into a private method later
		LocalTime start = getPeriod(in,I18N.getString(INAC_START),I18N.getString(INVALID_INAC_START));
		LocalTime end = getPeriod(in,I18N.getString(INAC_END),I18N.getString(INVALID_INAC_END));
		s.addInacAlert(new InactivityAlert(start, end, period));
	}
	

	private void addMovAlert(Screen s, Scanner in) {
		System.out.println(I18N.getString(MOV_PLACE));
		String  place = in.nextLine();
		LocalTime start = getPeriod(in,I18N.getString(MOV_START),I18N.getString(INVALID_MOV_START));
		LocalTime end = getPeriod(in,I18N.getString(MOV_END),I18N.getString(INVALID_MOV_END));
		s.addMovAlert(new MovementDetectedAlert(start, end, place));
	}
	
	private LocalTime getPeriod(Scanner in, String command, String fail){
		boolean valid = false;
		LocalTime result = null;
		while(!valid){
			System.out.println(command);
			String startPeriod = in.nextLine();
			try{
				result = LocalTime.parse(startPeriod);
				valid = true;
			}catch(DateTimeParseException e){
				System.out.println(fail);
			}
		}
		return result;
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
