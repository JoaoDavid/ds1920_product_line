package ui;

import static i18n.Messages.COMMANDS;
import static i18n.Messages.QUIT;
import static i18n.Messages.UNKNOWN;
import static i18n.Messages.WARNING;

import java.util.Scanner;

import communication.ContactList;
import i18n.I18N;
public class Screen extends Thread implements Runnable{
	
	private ContactList contacts;
	
	public Screen(ContactList contacts){
		this.contacts = contacts;
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
				System.out.println("TEMP: Warning command");
			} else if (quitInput.equals(command)) {
				quit = true;
			} else {
				System.out.println(I18N.getString(UNKNOWN));
			}
		}		
	}
}
