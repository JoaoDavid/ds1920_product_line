package config.aspects;

import app.Controller;
import app.ui.SynthetizedVoice;
import communication.Contact;

public aspect SynthetizedVoiceAspect {
	pointcut sendEvent(Controller c, String msg): target(c) && args(msg) && call(void Controller.process(String));
	
	pointcut contact(String msg): args(msg) && call(void Contact.sendWarning(String));
	
	after(Controller c, String msg) : sendEvent(c, msg){
		SynthetizedVoice.getInstance().playVoice(msg);
	}
	
	after(String msg): contact(msg){
		SynthetizedVoice.getInstance().playVoice(msg);
	}
}
