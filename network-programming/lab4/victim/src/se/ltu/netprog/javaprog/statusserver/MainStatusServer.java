package se.ltu.netprog.javaprog.statusserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;



/**
 * This class is a SocketServer which listens on incoming connections. When there comes an incoming connection a new Thread gets started.
 * The MainServer executes the commands send until the word QUIT is received.
 * To remove java 1.7 and install java 1.8:
 * sudo yum remove java-1.7.0-openjdk
 * sudo yum install java-1.8.0


 * @author cm
 *
 */
public class MainStatusServer
{
	 // use the classname for the logger, this way you can refactor
	  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//setup the logging functionality for this application
		MyServerLogger.setup();
		int iAmountConnectionsAccepted = 0;

		try
		{
			int iPort=2224; // the port the server will listen on
			ServerSocket lServerSocket = new ServerSocket(iPort);
			LOGGER.info("ServerSocket was started on port "+iPort+"!");
			
			/**
			 *  ServerSocket's task is not really the communication. His only task is to accept new connections. 
			 *  The connections get worwarded to another port to get the server port free for new connections.
			 *  The accept() method is part of the infinity loop and creates a new thread for every new connection
			 * http://openbook.galileodesign.de/javainsel5/javainsel16_007.htm
			 * http://www.java-forum.org/netzwerkprogrammierung/5507-netzwerkgrundlagen-serversocket-socket.html
			 * http://www.avajava.com/tutorials/lessons/how-do-i-make-a-socket-connection-to-a-server.html
			 */
			while (true)
			{
				// get PID of the MainServer
				long lPID = getProcessID("PID.txt");
				
				//Wait for a connection
				Socket lSocket = lServerSocket.accept();//accept() blocks the programm until a new connection comes in 
				iAmountConnectionsAccepted++;
				
				//Give the communication to another thread 
				StatusServerThread myServerThread= new StatusServerThread(lSocket, iAmountConnectionsAccepted, lPID);
				
				//start the thead
				myServerThread.start();
				LOGGER.info("Connection number " + iAmountConnectionsAccepted + " was given to a Thread");
		
			}
			//lServerSocket.close();
			
		}
		catch (IOException e)
		{
			LOGGER.severe("Problem: " + e.getMessage());
			
		}
		
		
	}
	
	/**
	 * Read the process id from the file which was created from the server program
	 * @param sFilename
	 * @return
	 */
	public static long getProcessID(String sFilename)
	{
		long lPID = -1;
		try {
			BufferedReader inputStream= new BufferedReader(new FileReader(sFilename));
			String sLine = inputStream.readLine();
			lPID = Long.parseLong(sLine);
			
			inputStream.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (NumberFormatException e) {			    
		}
		
		return lPID;
	}
	
	
	
	

}
