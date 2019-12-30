package config.aspects;

import static config.i18n.Messages.ALERT;
import static config.i18n.Messages.CHOOSE_ALERT;

import java.util.Scanner;

import app.ui.Screen;
import config.i18n.I18N;

public aspect AlertSelectorAspect {
	
	pointcut input(Screen s, String in): target(s) && args(in) && call(String Screen.process(String));
	
	after(Screen s, String in) returning(String out): input(s,in){
		if(in.equals(I18N.getString(ALERT))){
			System.out.println(I18N.getString(CHOOSE_ALERT));
			s.chooseAlert();
		}
	}
	
	public String Screen.chooseAlert(){
		Scanner in = new Scanner(System.in);
		return in.nextLine();
	}
}
