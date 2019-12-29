package rules;

import static i18n.Messages.CHOOSE_CONTACT;
import static i18n.Messages.CONTACT;
import static i18n.Messages.CONTACT_UNAVAILABLE;
import static i18n.Messages.INSERT_NOTIFICATION;
import static i18n.Messages.INVALID_NOTIFICATION;
import static i18n.Messages.CONTACT_OPERATION;
import static i18n.Messages.C_ADD_OP;
import static i18n.Messages.C_EDIT_OP;
import static i18n.Messages.C_INVALID_OP;
import static i18n.Messages.C_ENTER_NAME;
import static i18n.Messages.C_ENTER_NUMBER;
import static i18n.Messages.C_NUMBER_EXISTS;
import static i18n.Messages.C_INVALID_NUMBER;


import java.util.Scanner;

import communication.Contact;
import communication.ContactList;
import i18n.I18N;
import ui.Screen;

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
					for ( Contact c : cs.getContacts()) {
						System.out.println(c.toString());
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
			boolean valid = contacts.validateContact(in);
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
							contacts.changeNotifySetting(in, true);
							settingSelected = true;
							break;
						case "N":
							contacts.changeNotifySetting(in, false);
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
