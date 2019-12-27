package communication;

import java.util.ArrayList;
import java.util.List;

public class ContactList {
	
	List<Contact> contacts;

	public ContactList() {
		contacts = new ArrayList<>();
		
		//TODO just for testing
		contacts.add(new Contact("Ze", 91, true));
		contacts.add(new Contact("Joca", 93, true));
		contacts.add(new Contact("Rita", 96, true));
	}
	
	public void addContact(String name, int number) {
		contacts.add(new Contact(name, number, false));
	}
	
	public void notifyDefinedContacts(String message) {
		for (Contact contact : contacts) {
			contact.sendNotification(message);
		}
	}
	
	//TODO Remover para um aspect
	public void changeNotifySetting(String name, boolean isNotified) {
		for (Contact contact : contacts) {
			if(contact.getName().equals(name)) {
				contact.setNotified(isNotified);
			}
		}
	}

	public boolean validateContact(String contactName){
		for (Contact contact : contacts) {
			if(contactName.equals(contact.getName())){
				return true;
			}
		}
		return false;
	}
	
	public List<Contact> getContacts() {
		return contacts;
	}
}
