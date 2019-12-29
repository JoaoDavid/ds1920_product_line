package rules;

import static i18n.Messages.ALERT;

import i18n.I18N;
import ui.Screen;

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
