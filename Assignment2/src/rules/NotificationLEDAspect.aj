package rules;

import devices.Wearable;
import devices.functionality.NotificationLED;

public aspect NotificationLEDAspect {
	pointcut inDevice(): within(Wearable);
	
	before(): inDevice() && execution(* *.main(..)){
		NotificationLED notificationLed = new NotificationLED();
		notificationLed.subscribeLightSingnalEvents();
	}
}
