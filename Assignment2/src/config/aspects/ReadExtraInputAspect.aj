package config.aspects;

import static config.i18n.Messages.ALERT;

import app.ui.Screen;
import config.i18n.I18N;

public aspect ReadExtraInputAspect {
	pointcut input(Screen s): target(s) && call(String getInput(*));
	
	before(Screen s): input(s){
		System.out.println(I18N.getString(ALERT));
	}
	
	after(Screen s) returning(String in): input(s){
		s.process(in);
	}
	
	public String Screen.process(String input){
		return input;
	}
}
