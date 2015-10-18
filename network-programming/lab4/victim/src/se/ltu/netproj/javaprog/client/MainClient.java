package se.ltu.netproj.javaprog.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Entry point of my class
 * @author cm
 *
 */
public class MainClient 
{
	 // use the classname for the logger, this way you can refactor
	  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Main method who is the entry point of the program
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//setup the logging functionality for this application
		MyClientLogger.setup();
        ArrayList<ClientThread> myThreads = new ArrayList<ClientThread>();
        
        String sIPAddress = "TeamWhateverLB-1369488506.eu-west-1.elb.amazonaws.com";
        int iPort = 2223;
		

		for(int i = 1; i < 300; i++)
		{
			ClientThread oThread = new ClientThread(sIPAddress, iPort);
			oThread.setDaemon(true);
			oThread.start();
			myThreads.add(oThread);
			LOGGER.info("Started Thread number " + i);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
	    // An empty line or Ctrl-Z terminates the program
	    try {
			while ((s = in.readLine()) != null && s.length() != 0)
			{
				System.out.println(s);
			}
		} catch (IOException e) {
			LOGGER.severe("Error reading from stdin");
		}
	
		
		LOGGER.info("MainClient is now finish");
	}

}
