package rules;

import devices.Wearable;
import devices.functionality.Button;

public aspect ButtonAspect {
	pointcut inDevice(): within(Wearable);
	
	before(): inDevice() && execution(* *.main(..)){
		Button button = new Button();
		button.pressButtonPeriodicly();
	}
}
