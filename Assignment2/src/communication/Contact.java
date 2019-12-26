package communication;

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
	
}
