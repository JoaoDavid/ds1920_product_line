package devices.functionality;

import java.util.Timer;
import java.util.TimerTask;

import com.bezirk.middleware.Bezirk;
import com.bezirk.middleware.java.proxy.BezirkMiddleware;

import events.ButtonPressEvent;


public class Button {
	
	private Bezirk bezirk;

	public Button() {
		BezirkMiddleware.initialize();
        bezirk = BezirkMiddleware.registerZirk("Wearable's Button");
	}
	
	public void pressButton() {
        final ButtonPressEvent buttonPressEvent = new ButtonPressEvent();

        bezirk.sendEvent(buttonPressEvent);
        System.out.println("Published button press event: " + buttonPressEvent.toString());
    }
	
	public void pressButtonPeriodicaly(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	pressButton();
            }
        }, 0, 10000);
    }

}
