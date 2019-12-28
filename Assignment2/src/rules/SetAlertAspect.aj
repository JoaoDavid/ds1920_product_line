package rules;

import java.util.ArrayList;
import java.util.List;

import alerts.InactivityAlert;
import alerts.MovementDetectedAlert;
import app.Controller;
import ui.Screen;


public aspect SetAlertAspect {
	private List<MovementDetectedAlert> Controller.movAlerts = new ArrayList<>();
	private List<InactivityAlert> Controller.inacAlerts = new ArrayList<>();
	
	private List<MovementDetectedAlert> Screen.movAlerts;
	private List<InactivityAlert> Screen.inacAlerts;

	//pointcut main(Controller c): within(Controller) && target(c) && execution(* new Controller());
	
	/*
	public void Controller.setScreenMovAlerts(){
		this.screen.setMovAlerts();
	}*/
	
	public void Screen.setMovAlerts(List<MovementDetectedAlert> movAlerts){
		this.movAlerts = movAlerts;
	}
	
}
