package rules;

import static i18n.Messages.ALERT;
import static i18n.Messages.CHOOSE_CONTACT;
import static i18n.Messages.CONTACT;
import static i18n.Messages.CONTACT_UNAVAILABLE;
import static i18n.Messages.INSERT_NOTIFICATION;
import static i18n.Messages.INVALID_NOTIFICATION;

import java.util.Scanner;

import communication.Contact;
import communication.ContactList;
import i18n.I18N;
import ui.Screen;

public aspect ScreenAspect {
	pointcut input(Screen s): target(s) && call(String getInput(*));

	before(Screen s): input(s){
		System.out.println("	-" + I18N.getString(CONTACT));
		System.out.println("	-" + I18N.getString(ALERT));
	}

	after(Screen s) returning(String in): input(s){
		if(in.equals(I18N.getString(CONTACT))){
			ContactList cs = s.getContacts();
			System.out.println(I18N.getString(CHOOSE_CONTACT));
			for ( Contact c : cs.getContacts()) {
				System.out.println(c.toString());
			}
			changeContact(cs);
		}else if(in.equals(I18N.getString(ALERT))){
			//TODO fazer caso de editar alertas
			System.out.println("TEST2");
		}
	}

	private void changeContact(ContactList contacts){
		boolean selected = false;
		Scanner input = new Scanner(System.in);
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
					String setting = input.nextLine();
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
