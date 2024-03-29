package communication;

import static config.i18n.Messages.C_NAME;
import static config.i18n.Messages.C_NOTIFIED;
import static config.i18n.Messages.C_NUMBER;

import config.i18n.I18N;
public class Contact {
	
	private String name;
	private int number;
	private boolean isNotified;
	
	/**
	 * Creates a new contact
	 * 
	 * @param name
	 * @param number
	 * @param sendNotification
	 */
	public Contact(String name, int number, boolean isNotified){
		this.name = name;
		this.number = number;
		this.isNotified = isNotified;
	}


	/**
	 * Send SMS if the contact is set to be notified
	 * 
	 * @param message
	 */
	public void sendNotification(String message) {
		if(isNotified) {
			System.out.println("Sending SMS to " + name + " " + number + " | " + message);
		}
	}
	
	public void sendWarning(String message) {
		System.out.println("Sending SMS to " + name + " " + number + " | " + message);
	}

	public void setNotified(boolean isNotified) {
		this.isNotified = isNotified;
	}

	public String getName() {
		return name;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	@Override
	public String toString(){
		return I18N.getString(C_NAME)+": " + this.name + ", "+I18N.getString(C_NUMBER)+": " + this.number + 
				", "+ I18N.getString(C_NOTIFIED)+": " + this.isNotified;
	}
	
}
