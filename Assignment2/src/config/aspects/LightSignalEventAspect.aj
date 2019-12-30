package config.aspects;

import app.Controller;
import app.ui.LightSignalEmitter;

public aspect LightSignalEventAspect {
	pointcut sendEvent(Controller c, String msg): target(c) && args(msg) && call(void Controller.process(String));
	
	after(Controller c, String msg) : sendEvent(c, msg){
		LightSignalEmitter.getInstance().sendLightSignal();
	}
}
