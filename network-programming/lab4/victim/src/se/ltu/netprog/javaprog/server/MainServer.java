package se.ltu.netprog.javaprog.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
public class MainServer
{
	
	 // use the classname for the logger, this way you can refactor
	  private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	  private static int iAmountConnectionsAccepted;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		//setup the logging functionality for this application
		MyServerLogger.setup();
		iAmountConnectionsAccepted = 0;
		// write the process id to a file
		writePIDtoFile(getPID(),"PID.txt");

		try
		{
			int iPort=2223; // the port the server will listen on
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
				//Wait for a connection
				Socket lSocket = lServerSocket.accept();//accept() blocks the programm until a new connection comes in 
				String sThreadsRunning =  " " + java.lang.Thread.activeCount() + " threads running";
				iAmountConnectionsAccepted++;
				
				//Give the communication to another thread 
				ServerThread myServerThread= new ServerThread(lSocket, iAmountConnectionsAccepted);
				
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

	public static int getiAmountConnectionsAccepted() {
		return iAmountConnectionsAccepted;
	}

	public static void setiAmountConnectionsAccepted(int iAmountConnectionsAccepted) {
		MainServer.iAmountConnectionsAccepted = iAmountConnectionsAccepted;
	}
	
	/**
	 * get the process id of this process
	 * @return
	 */
	 public static long getPID() {
		    String processName =
		      java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		    return Long.parseLong(processName.split("@")[0]);
	 }
	 
	 /**
	  * Write PID to a file
	  * @param lPID
	  * @param sFilename
	  */
	 public static void writePIDtoFile(long lPID, String sFilename)
	 {
			try {
				BufferedWriter outStream= new BufferedWriter(new FileWriter(sFilename, false));
				outStream.write(""+lPID);
				outStream.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	 }
	

	

}
