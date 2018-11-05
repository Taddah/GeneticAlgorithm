package main;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("logger");
		
		logger.log(Level.INFO, "Lancement du main");

	}

}
