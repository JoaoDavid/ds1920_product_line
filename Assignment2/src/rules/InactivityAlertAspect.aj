package rules;

import static i18n.Messages.INAC_ALERT;
import static i18n.Messages.INAC_END;
import static i18n.Messages.INAC_PERIOD;
import static i18n.Messages.INAC_START;
import static i18n.Messages.INVALID_INAC_END;
import static i18n.Messages.INVALID_INAC_PERIOD;
import static i18n.Messages.INVALID_INAC_START;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.Controller;
import communication.alerts.InactivityAlert;
import i18n.I18N;
import ui.Screen;

public aspect InactivityAlertAspect {
	private List<InactivityAlert> Controller.inacAlerts = new ArrayList<>();
	
	private List<InactivityAlert> Screen.inacAlerts;
	
	pointcut start(Controller c): within(Controller) && target(c) && call(void startScreen(..));
	
	before(Controller c): start(c){
		c.getScreen().setInacAlerts(c.getInacAlerts());
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
	
	pointcut print(Screen s): target(s) && call(String Screen.chooseAlert(..));
	
	before(Screen s): print(s){
		System.out.println(I18N.getString(INAC_ALERT));
	}
	
	after(Screen s) returning(String selection): print(s){
		if(selection.equals(I18N.getString(INAC_ALERT))){
			addInacAlert(s,new Scanner(System.in));
		}
	}
	
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
		LocalTime start = getPeriod(in,I18N.getString(INAC_START),I18N.getString(INVALID_INAC_START));
		LocalTime end = getPeriod(in,I18N.getString(INAC_END),I18N.getString(INVALID_INAC_END));
		s.addInacAlert(new InactivityAlert(start, end, period));
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
}
