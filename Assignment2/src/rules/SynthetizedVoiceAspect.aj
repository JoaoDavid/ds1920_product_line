package rules;

import app.Controller;
import communication.Contact;
import ui.SynthetizedVoice;

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
