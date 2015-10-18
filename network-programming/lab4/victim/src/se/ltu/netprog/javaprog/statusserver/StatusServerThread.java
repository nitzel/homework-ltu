package se.ltu.netprog.javaprog.statusserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;



/**
 * The class ServerThread receives the incomming connection from MainServer in the constructor
 * @author cm
 *
 */
public class StatusServerThread extends Thread
{
	private Socket lSocket;
	private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private BufferedReader lReader = null;
	private PrintWriter lWriter = null;
	private MySignal oSignal = null;
	private Thread mythread = null;
	private int iAmountConnectionsAccepted;
	private long lPID = -1;


	//Constructor receives the socket from MainServer
	public StatusServerThread(Socket lSocket, int _iAmountConnectionsAccepted, long _lPID)
	{
		this.lSocket = lSocket;
		this.iAmountConnectionsAccepted = _iAmountConnectionsAccepted;
		this.lPID = _lPID;
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
			Runnable threadjob = new SystemInformation(oSignal, lWriter, lPID);
			mythread = new Thread(threadjob);
			mythread.start();
			
			lWriter.println("Welcome to the status service");
			lWriter.println("To end the connection type QUIT or q");
			lWriter.println("To change the display interval enter a valid number");
			lWriter.flush();
		}
		catch (IOException e)
		{
			LOGGER.severe("Problem with ServerThread " + iAmountConnectionsAccepted + "." + e.getMessage());
		} 
		
		int iNewIntervalNumber = 5;
		try {
			for (sLine = lReader.readLine(); sLine != null; sLine = lReader.readLine()) {
				if (sLine.equals("QUIT") || sLine.equals("q"))
				{
					LOGGER.info("QUIT was entered -> program will be closed");
					finishJob();
				}
				else
				{
					//check if it is a number
					try {
						iNewIntervalNumber = Integer.parseInt(sLine);
						oSignal.setiPrintInterval(iNewIntervalNumber);
					} catch (NumberFormatException e) {
						lWriter.println("You didn't enter a valid number to change the display interval!");
						lWriter.flush();
					    
					}
				}
			}
		} catch (IOException e) {
			//
		}
		
		if (sLine == null)
		{
			LOGGER.severe("The client of ServerThread " + iAmountConnectionsAccepted + " closed the connection");
			finishJob();
		}

		LOGGER.info("StatusServerThread " + iAmountConnectionsAccepted + " is now finish with his work.");
	}
	

	/**
	 * This method sets the signal that the execution stops and closes all the connections
	 */
	public void finishJob()
	{
		oSignal.changeStatus();
		try {
			mythread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			lWriter.close();
			lSocket.close();
		} catch (IOException e) {
			LOGGER.severe("Error closing the socket or the writer");
		}
	}
	

}