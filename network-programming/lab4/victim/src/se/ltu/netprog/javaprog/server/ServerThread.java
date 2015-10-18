package se.ltu.netprog.javaprog.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;



/**
 * The class ServerThread receives the incomming connection from MainServer in the constructor
 * @author cm
 *
 */
public class ServerThread extends Thread
{
	private Socket lSocket;
	private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private BufferedReader lReader = null;
	private PrintWriter lWriter = null;
	private MySignal oSignal = null;
	private Thread mythread = null;
	private int iAmountConnectionsAccepted;


	//Constructor receives the socket from MainServer
	public ServerThread(Socket lSocket, int _iAmountConnectionsAccepted)
	{
		this.lSocket = lSocket;
		this.iAmountConnectionsAccepted = _iAmountConnectionsAccepted;
	}

	/**
	 * the run-Methode gets called when the Thread is started 
	 */
	@Override
	public void run()
	{

		String sLine = "";
		
		try
		{
			lReader = new BufferedReader(new InputStreamReader(lSocket.getInputStream()));
			lWriter = new PrintWriter(new OutputStreamWriter(lSocket.getOutputStream()));
			oSignal = new MySignal();
			
			// create a runnable and pass him the command to execute
			Runnable threadjob = new FibonacciCalculator(oSignal, iAmountConnectionsAccepted);
			mythread = new Thread(threadjob);
			mythread.start();
			
			lWriter.println("I'm number " + iAmountConnectionsAccepted );
			lWriter.println("Thank you for visiting!");
			lWriter.flush();
		}
		catch (IOException e)
		{
			LOGGER.severe("Problem with ServerThread " + iAmountConnectionsAccepted + "." + e.getMessage());
		} 
		
		finishJob();
	}
	
	/**
	 * This method sets the signal that the CommandExecution stops and closes all the connections
	 */
	public void finishJob()
	{		
		try {
			lWriter.close();
			lSocket.close();
		} catch (IOException e) {
			LOGGER.severe("Error closing the socket or the writer");
		}
	}
	

}