package se.ltu.netprog.javaprog.server;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Class who implements a runnable and will calculate fibonacci numbers
 * @author cm
 *
 */
public class FibonacciCalculator implements Runnable {
	
	private MySignal oSiganl;
	private int iAmountConnectionsAccepted;
	private final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Constructor
	 * @param _oSiganl Signal that tells the thread to stop
	 * @param _lWriter Writer to write to tcp socket
	 */
	public FibonacciCalculator(MySignal _oSiganl,int _iAmountConnectionsAccepted) {
		iAmountConnectionsAccepted = _iAmountConnectionsAccepted;
		oSiganl = _oSiganl;

	}

	/**
	 * Method who gets called when the thread gets started
	 */
	@Override
	public void run() {
		//http://alvinalexander.com/java/java-exec-processbuilder-process-1 
		
        int iPrev1=0, iPrev2=1;
        
	    LocalDateTime actDate = LocalDateTime.now();
	    LocalDateTime nextDate = LocalDateTime.now().plusSeconds(30);

	    // Fibonacci numbers are the integer sequence 
        //0, 1, 1, 2, 3, 5, 8, 13, 21, ..., 
        //in which each item is formed by adding the previous two. T
	    boolean bStop = false;
		for(;bStop == false;)
		{
            int savePrev1 = iPrev1;
            iPrev1 = iPrev2;
            iPrev2 = savePrev1 + iPrev2;
            
			//LOGGER.info("the actual fibonacci number is" + iPrev2);
            try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				LOGGER.severe("Thread couldn't sleep before calculating next number!");
			}
            actDate = LocalDateTime.now();
            if (actDate.isAfter(nextDate))
            {
                bStop = true;                
            }
		} 
		
		LOGGER.info("runnable fibonacci calcular " + iAmountConnectionsAccepted + " has finished his job");
	
	}
	
}
