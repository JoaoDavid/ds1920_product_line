package devices.functionality;

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

        //sends the event
        bezirk.sendEvent(buttonPressEvent);
        System.err.println("Published button press event: " + buttonPressEvent.toString());
    }

}
