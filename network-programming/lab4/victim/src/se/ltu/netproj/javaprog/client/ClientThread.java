package se.ltu.netproj.javaprog.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;


/**
 * The task of the Client Thread is to communicate with the server.
 * He sends the user commands to the server and prints the response to the gui
 * @author cm
 *
 */
public class ClientThread extends Thread
{
	private String sIPAddress;//IP-Adress of the server
	private int iPort;//Port of the server which shall get contacted
	private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	//Constructor tells the thread the ip and the port of the remote server, 
	public ClientThread(String _sIPAddress, int _iPort)
	{
		sIPAddress = _sIPAddress;
		iPort= _iPort;
	}

	/**
	 * the run-method gets called when the thread starts
	 */
	@Override
	public void run()
	{
			Socket lSocket;
			
			try
			{
				// connect client with the server
				lSocket = new Socket(sIPAddress, iPort);
				OutputStream lToServer = lSocket.getOutputStream();
				InputStream lFromServer = lSocket.getInputStream();
				BufferedReader lReader = new BufferedReader(new InputStreamReader(lFromServer));
				PrintWriter lWriter = new PrintWriter(new OutputStreamWriter(lToServer));

				// wait for the answer of the server
				String lLine = "";				
				while((lLine=lReader.readLine())!=null)
				{
					System.out.println(lLine);
				}
			
				lWriter.close();
				lReader.close();
				lSocket.close();
			}
			catch (UnknownHostException e)
			{
				LOGGER.severe("There was an error connecting to the server " + e.getMessage());
			} catch (IOException e) {
				LOGGER.severe("There was an error closing the connection to the server " + e.getMessage());
			}
			
		
		}			

}
