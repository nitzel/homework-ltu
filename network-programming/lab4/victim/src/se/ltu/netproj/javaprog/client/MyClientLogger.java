package se.ltu.netproj.javaprog.client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class is used to setup the logging functions for the server application
 * The setup method gets called at the beginning of the main method
 * @author cm
 *
 */
public class MyClientLogger {

	private static  FileHandler fileTxt;
	private static  SimpleFormatter formatterTxt;

	public static void setup()
	{
		// get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		// set the level which shall be logged
		//SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
		logger.setLevel(Level.INFO);
		String sFilename = "ClientLogFile.txt";

		try {
			fileTxt = new FileHandler(sFilename);
		} catch (SecurityException | IOException e) {
			System.out.println("There was an error writting to Logfile " + sFilename);
			e.printStackTrace();
		}
		
		// create a TXT formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
	
	
	
	}

}
	 