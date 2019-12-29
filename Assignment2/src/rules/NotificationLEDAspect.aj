package rules;

import devices.Wearable;
import devices.wearablefeatures.NotificationLED;

public aspect NotificationLEDAspect {
	
	private NotificationLED Wearable.notificationLed = new NotificationLED();
	
	pointcut inDevice(Wearable c): target(c) && execution(Wearable.new());
	
	after(Wearable c): inDevice(c) {
		c.notificationLed.subscribeLightSingnalEvents();
	}
}
