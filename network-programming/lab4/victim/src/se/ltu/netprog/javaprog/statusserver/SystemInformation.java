package se.ltu.netprog.javaprog.statusserver;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ptql.ProcessFinder;
import org.hyperic.sigar.ptql.ProcessQuery;

/**
 * Class who implements a runnable and will print out system information
 * @author cm
 *
 */
public class SystemInformation implements Runnable {
	
	private PrintWriter lWriter = null;
	private MySignal oSignal;
	private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private long lPID = -1;

	/**
	 * Constructor
	 * @param _oSiganl Signal that tells the thread to stop
	 * @param _lWriter Writer to write to tcp socket
	 */
	public SystemInformation(MySignal _oSignal, PrintWriter _lWriter, long _lPID) {
		oSignal = _oSignal;
		lWriter = _lWriter;
		lPID = _lPID;

	}

	/**
	 * Method who gets called when the thread gets started
	 */
	@Override
	public void run() {
		for(;oSignal.getStatus() != false;)
		{         
            lWriter.println(getSystemStatistics());
            lWriter.flush();
            try {
				Thread.sleep(oSignal.getiPrintInterval()*1000);
			} catch (InterruptedException e) {
				LOGGER.severe("Thread couldn't sleep before calculating next number!");
			}
		} 
		
		LOGGER.info("runnable SystemInformation has finished his job");
		
	}
	
	/**
	 * This method returns a String containing system statistics
	 * The statistics are retrieved with the help of a library
	 * @return
	 */
	public String getSystemStatistics(){
		// create object to retrieve values
		Sigar sigar = new Sigar();
	    Mem memory = null;
	    CpuPerc cpuperc = null;
	    NetInfo netinfo = null;
	    ProcState procstate = null;
	    
	    // get the amount of running threads from the process id
	    String sThreadsRunning =  "";
        if (lPID != 0)
	    {
        	 try {
				procstate = sigar.getProcState(lPID);
	        	 sThreadsRunning = "" + procstate.getThreads() + " threads running";
			} catch (SigarException e) {
				sThreadsRunning = "error counting threads";
			}
	    }
	    // retrieve values
	    try {
	    	memory = sigar.getMem();
	        cpuperc = sigar.getCpuPerc();
	        netinfo = sigar.getNetInfo();

	       	        
	    } catch (SigarException se) {
	        se.printStackTrace();
	    }
	    double dMemoryUsedPercentage = memory.getUsedPercent();
	    double dCPUUsed = cpuperc.getCombined()*100;
	    String sHostname = netinfo.getHostName();

	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    
	    
	    return dateFormat.format(date) + ";" + sHostname + ": CPU: " + (int)dCPUUsed + "% used; RAM: " + (int) dMemoryUsedPercentage + "% used;" + sThreadsRunning;
	}
	
	


	
}
