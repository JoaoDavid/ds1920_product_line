package ui;

import static i18n.Messages.COMMANDS;
import static i18n.Messages.QUIT;
import static i18n.Messages.UNKNOWN;
import static i18n.Messages.WARNING;

import java.util.Scanner;

import app.Controller;
import i18n.I18N;
public class Screen {
	
	public static String getInput(Scanner in) {
		return in.nextLine();
	}
	
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		boolean quit = false;
		String quitInput = I18N.getString(QUIT);
		String warningInput = I18N.getString(WARNING);
		//TODO Acrescentar alerta como aspect
		//TODO Acrescentar edit de contactos como aspect
		
		while(!quit){
			System.out.println(I18N.getString(COMMANDS));
			System.out.println("	-" + quitInput);
			System.out.println("	-" + warningInput);
			String command = Screen.getInput(in);
			if (warningInput.equals(command)) {
				
			} else if (quitInput.equals(command)) {
				quit = true;
			} else {
				System.out.println(I18N.getString(UNKNOWN));
			}
		}
		
	}
}
