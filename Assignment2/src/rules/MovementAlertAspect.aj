package rules;

import static i18n.Messages.INVALID_MOV_END;
import static i18n.Messages.INVALID_MOV_START;
import static i18n.Messages.MOV_ALERT;
import static i18n.Messages.MOV_END;
import static i18n.Messages.MOV_PLACE;
import static i18n.Messages.MOV_START;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.Controller;
import communication.alerts.MovementDetectedAlert;
import i18n.I18N;
import ui.Screen;

public aspect MovementAlertAspect {
	private List<MovementDetectedAlert> Controller.movAlerts = new ArrayList<>();
	
	private List<MovementDetectedAlert> Screen.movAlerts;

	pointcut start(Controller c): within(Controller) && target(c) && call(void startScreen(..));
	
	before(Controller c): start(c){
		c.getScreen().setMovAlerts(c.getMovAlerts());
	}
	
	public List<MovementDetectedAlert> Controller.getMovAlerts(){
		return this.movAlerts;
	}
	
	public void Screen.setMovAlerts(List<MovementDetectedAlert> movAlerts){
		this.movAlerts = movAlerts;
	}
	
	public void Screen.addMovAlert(MovementDetectedAlert a){
		this.movAlerts.add(a);
	}
	
	pointcut print(Screen s): target(s) && call(String Screen.chooseAlert(..));
	
	before(Screen s): print(s){
		System.out.println(I18N.getString(MOV_ALERT));
	}
	
	after(Screen s) returning(String selection): print(s){
		if(selection.equals(I18N.getString(MOV_ALERT))){
			addMovAlert(s, new Scanner(System.in));
		}
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
}
