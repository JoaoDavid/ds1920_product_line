package app.ui;

import static config.i18n.Messages.COMMANDS;
import static config.i18n.Messages.CONTACT_UNAVAILABLE;
import static config.i18n.Messages.C_INVALID_NUMBER;
import static config.i18n.Messages.NO_CONTACTS;
import static config.i18n.Messages.QUIT;
import static config.i18n.Messages.UNKNOWN;
import static config.i18n.Messages.WARNING;
import static config.i18n.Messages.WARNING_CONTACT;
import static config.i18n.Messages.WARNING_DATE_ERROR;
import static config.i18n.Messages.WARNING_DATE_FROM;
import static config.i18n.Messages.WARNING_DATE_TO;
import static config.i18n.Messages.WARNING_NAME;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import communication.Contact;
import communication.ContactList;
import communication.warnings.WarningManager;
import config.DateTimeFormats;
import config.i18n.I18N;
import config.i18n.Messages;
public class Screen extends Thread implements Runnable{
	
	private ContactList contacts;
	private WarningManager warnMan;
	
	public Screen(ContactList contacts, WarningManager warnMan){
		this.contacts = contacts;
		this.warnMan = warnMan;
	}

	public String getInput(Scanner in) {
		return in.nextLine();
	}
	
	public ContactList getContacts(){
		return this.contacts;
	}

	@Override
	public void run(){
		Scanner in = new Scanner(System.in);
		boolean quit = false;
		String quitInput = I18N.getString(QUIT);
		String warningInput = I18N.getString(WARNING);
		
		while(!quit){
			System.out.println(I18N.getString(COMMANDS));
			System.out.println(quitInput);
			System.out.println(warningInput);
			String command = getInput(in);
			if (warningInput.equals(command)) {
				addWarning(in);
			} else if (quitInput.equals(command)) {
				quit = true;
			} else {
				System.out.println(I18N.getString(UNKNOWN));
			}
		}		
	}

	private void addWarning(Scanner in) {

		System.out.println(I18N.getString(WARNING_CONTACT));
		Contact contact = null;
		if(!this.contacts.getContacts().isEmpty()){
			int i = 0;
			for ( Contact c : this.contacts.getContacts()) {
				System.out.println(i++ + "-" +  c.toString());
			}
			contact = chooseContact(this.contacts,in);
		}else{
			System.out.println(I18N.getString(NO_CONTACTS));
			return;
		}
		System.out.println(I18N.getString(WARNING_NAME));
		String name = in.nextLine();
		LocalDateTime from = getDate(I18N.getString(WARNING_DATE_FROM),in);
		LocalDateTime to = getDate(I18N.getString(WARNING_DATE_TO),in);
		long period = getPeriod(in);
		warnMan.addNewWarning(name, from, to, period*60*60*1000 , contact);
	}
	
	private long getPeriod(Scanner in) {
		boolean valid = false;
		long period = 0;
		while(!valid){
			System.out.println(I18N.getString(Messages.WARNING_PERIOD));
			try{
				period = Integer.parseInt(in.nextLine());
				valid = true;
			}catch(NumberFormatException e){
				System.out.println(I18N.getString(Messages.INVALID_PERIOD));
			}
		}
		return period;
	}

	private Contact chooseContact(ContactList contacts, Scanner input){
		boolean selected = false;
		Contact result = null;
		while(!selected){
			String in = input.nextLine();
			int number = 0;
			boolean valid = false;
			try{
				number = Integer.parseInt(in);
				valid = number >= 0 && number < contacts.size();
			}catch(NumberFormatException e){
				System.out.println(I18N.getString(C_INVALID_NUMBER));
				continue;
			}
			if(!valid){
				System.out.println(I18N.getString(CONTACT_UNAVAILABLE));
			}else{
				selected = true;
				result = contacts.getContactByIndex(number);
			}
		}
		return result;
	}

	private LocalDateTime getDate(String message,Scanner in){
		LocalDateTime date = null;
		boolean valid = false;
		while(!valid){
			System.out.println(message);
			String fDate = in.nextLine();
			try{
				date = LocalDateTime.parse(fDate,DateTimeFormats.FORMATTER_DATE_TIME2);
				valid = true;
			}catch(DateTimeParseException e){
				System.out.println(I18N.getString(WARNING_DATE_ERROR));	
			}
		}
		return date;
	}
}
