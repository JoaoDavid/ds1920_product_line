package rules;

import i18n.I18N;
import ui.Screen;
import static i18n.Messages.CONTACT;
import static i18n.Messages.ALERT;

public aspect ScreenAspect {
	pointcut input(): call(String Screen.getInput(*));
	
	before(): input(){
		System.out.println("	-" + I18N.getString(CONTACT));
		System.out.println("	-" + I18N.getString(ALERT));
	}
	
	after() returning(String in): input(){
		if(in.equals(I18N.getString(CONTACT))){
			//TODO fazer caso de editar contacto
			System.out.println("TEST");
		}else if(in.equals(I18N.getString(ALERT))){
			//TODO fazer caso de editar alertas
			System.out.println("TEST2");
		}
	}
}
