package se.ltu.netprog.javaprog.statusserver;

/**
 * Objects of this class will be used to signal a loop to stop
 * @author cm
 *
 */
public class MySignal{

	  private boolean bStatus = true;
	  private int iPrintInterval = 5;

	  /**
	   * return the actual status of the object
	   * @return true or false
	   */
	  public synchronized boolean getStatus(){
	    return this.bStatus;
	  }

	  /**
	   * change the actual Status to the opposite
	   */
	  public synchronized void changeStatus(){
	    if (bStatus == false)
	    {
	    	bStatus = true;
	    }
	    else
	    {
	    	bStatus = false;
	    }
	  }

	public int getiPrintInterval() {
		return iPrintInterval;
	}

	public void setiPrintInterval(int iPrintInterval) {
		this.iPrintInterval = iPrintInterval;
	}
	  
	  

	}