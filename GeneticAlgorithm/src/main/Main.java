package main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main--- Exécution de la librairie
 * 
 * @author Thomas
 * @since 03/12/2018
 *
 */
public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger("logger");
		
		logger.log(Level.INFO, "Lancement du main");

	}

}
