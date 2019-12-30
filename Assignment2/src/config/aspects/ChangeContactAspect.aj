package config.aspects;

import static config.i18n.Messages.CHOOSE_CONTACT;
import static config.i18n.Messages.CONTACT;
import static config.i18n.Messages.CONTACT_OPERATION;
import static config.i18n.Messages.CONTACT_UNAVAILABLE;
import static config.i18n.Messages.C_ADD_OP;
import static config.i18n.Messages.C_EDIT_OP;
import static config.i18n.Messages.C_ENTER_NAME;
import static config.i18n.Messages.C_ENTER_NUMBER;
import static config.i18n.Messages.C_INVALID_NUMBER;
import static config.i18n.Messages.C_INVALID_OP;
import static config.i18n.Messages.C_NUMBER_EXISTS;
import static config.i18n.Messages.INSERT_NOTIFICATION;
import static config.i18n.Messages.INVALID_NOTIFICATION;

import java.util.Scanner;

import app.ui.Screen;
import communication.Contact;
import communication.ContactList;
import config.i18n.I18N;

public aspect ChangeContactAspect {

	pointcut input(Screen s): target(s) && call(String getInput(*));
	
	before(Screen s): input(s){
		System.out.println(I18N.getString(CONTACT));
	}
	
	after(Screen s) returning(String in): input(s){
		if(in.equals(I18N.getString(CONTACT))){
			System.out.println(I18N.getString(CONTACT_OPERATION));
			System.out.println(I18N.getString(C_ADD_OP));
			System.out.println(I18N.getString(C_EDIT_OP));
			boolean valid = false;
			Scanner scan = new Scanner(System.in);
			while(!valid){
				String input = scan.nextLine();
				if(input.equals(I18N.getString(C_ADD_OP))){
					valid = true;
					addContact(s, scan);
				}else if(input.equals(I18N.getString(C_EDIT_OP))){
					valid = true;
					ContactList cs = s.getContacts();
					System.out.println(I18N.getString(CHOOSE_CONTACT));
					int i = 0;
					for ( Contact c : cs.getContacts()) {
						System.out.println(i++ + "-" +  c.toString());
					}
					changeContact(cs, scan);
				}else{
					System.out.println(I18N.getString(C_INVALID_OP));
				}
			}
		}
	}
	
	private void addContact(Screen s,  Scanner scan) {
		System.out.println(I18N.getString(C_ENTER_NAME));
		String name = scan.nextLine();
		int number = 0;
		boolean valid = false;
		while(!valid){
			System.out.println(I18N.getString(C_ENTER_NUMBER));
			try{
				number = Integer.parseInt(scan.nextLine());
				if(s.getContacts().validateContactNumber(number)){
					System.out.println(I18N.getString(C_NUMBER_EXISTS));
				}else{
					valid = true;
					s.getContacts().addContact(name, number);
				}
			}catch (NumberFormatException e){
				System.out.println(I18N.getString(C_INVALID_NUMBER));
			}
		}
	}

	private void changeContact(ContactList contacts, Scanner input){
		boolean selected = false;
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
				System.out.println(I18N.getString(INSERT_NOTIFICATION));
				boolean settingSelected = false;
				while(!settingSelected){
					String setting = input.nextLine().toUpperCase();
					switch(setting){
						case "Y":
							contacts.changeNotifySetting(number, true);
							settingSelected = true;
							break;
						case "N":
							contacts.changeNotifySetting(number, false);
							settingSelected = true;
							break;
						default:
							System.out.println(I18N.getString(INVALID_NOTIFICATION));
					}
				}
			}
		}
	}
}
