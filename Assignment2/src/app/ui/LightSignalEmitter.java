package app.ui;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import devices.events.LightSignalEvent;

public class LightSignalEmitter {
	
	private Bezirk bezirk;
	
	private static LightSignalEmitter instance;
	
	private LightSignalEmitter() {
		BezirkMiddleware.initialize();
		bezirk = BezirkMiddleware.registerZirk("LightSignalEmitter");
	}
	
	public static LightSignalEmitter getInstance() {
		if (instance == null)  {
			instance = new LightSignalEmitter(); //TODO aspecj
		}  
        return instance; 
	}
	
	public void sendLightSignal() {
		final LightSignalEvent lightSignalEvent = new LightSignalEvent();
        bezirk.sendEvent(lightSignalEvent);
	}

}
