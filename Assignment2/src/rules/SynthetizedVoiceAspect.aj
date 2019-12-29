package rules;

import app.Controller;
import ui.SynthetizedVoice;

public aspect SynthetizedVoiceAspect {
	pointcut sendEvent(Controller c, String msg): target(c) && args(msg) && call(void Controller.process(String));
	
	after(Controller c, String msg) : sendEvent(c, msg){
		SynthetizedVoice.getInstance().playVoice(msg);
	}
}
