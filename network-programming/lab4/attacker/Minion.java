package lab4;

import java.net.Socket;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * A Minion is a worker agent which takes tasks from anyone 
 * - usually an Overseer or the MinionSpawner owning it - 
 * to attack a target (open and close tcp connections) to test
 * its behavior under heavy load
 * 
 * Behaviours
 * - Kills itself if the owner is dead
 * - Receivs and deals with messages like
 * 	- REQUEST:data Set target ("ip:port")
 *  - INFORM:data Change interval("interval" in ms)
 *  - CANCEL to delete the minion
 * @author nitzel
 */
@SuppressWarnings("serial")
public class Minion extends Agent{
	protected BehaviourOpenTCPConnection tcpb;
	/** Agent owning this one, probably a MinionSpawner */
	protected Agent owner;	
	
    protected void setup() {    	
        System.out.println("Hello World! My name is " + getAID().getLocalName());

        registerAgent();
        
        // create and set behaviours
        tcpb = new BehaviourOpenTCPConnection(this, 1000, null, 2223);
        addBehaviour(tcpb);	// the tcp attack behaviour
        addBehaviour(new BehaviourReceiveMessage(this, 100)); // receiving and dealing with messages
        addBehaviour(new BehaviourKillIfOwnerIsDead(this, 1000, owner)); 
    }

	/** 
	 * Registration with the DF
	 * also gets the owner via getArguments() and sets the membervar
	 */
    protected void registerAgent(){
    	Object [] args = getArguments();
    	if(args!=null){
    		Object tmp = args[0];
    		if(Agent.class.isInstance(tmp)){
    			owner = (Agent)tmp;
    		}
    	}
	    DFAgentDescription dfd = new DFAgentDescription();
	    ServiceDescription sd = new ServiceDescription();
	    sd.setType(Overseer.TYPE_MINION);
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies(Overseer.TYPE_MINION);
	    sd.setName(getName());
	    if(owner!=null){
	    	sd.setOwnership(owner.getName());
	    }
	    dfd.setName(getAID());
	    dfd.addServices(sd);
	    try {
	    	DFService.register(this,dfd);
	    } catch (FIPAException e) {
	        System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
	        doDelete();
	    }
    }
    /**
     * This is called when the agent is stopped. It then deregisters from the DF
     */
    @Override
    protected void takeDown(){
    	tcpb.onEnd(); // close connections
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
    }
    
    /**
     * This message checks every X seconds for new messages
     * The types are mentioned in the class description
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
		
    	public BehaviourReceiveMessage(Agent a, long period) {
			super(a, period); // 100msec between the calls
		}
    	/**
    	 * onTick checks for a new message and executes the task it contains
    	 * - attack a target / stop attack
    	 * - set interval
    	 * - delete agent
    	 */
		public void onTick() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            	int performative = msg.getPerformative();
            	String content = msg.getContent();
            	
            	switch(performative){
            	case ACLMessage.PROPAGATE: // received new target
            		String[] target = content.split(":");
            		if(target.length != 2){
            			System.err.println("Minion received invalid target `"+content+"`");
            		}
            		try {
						tcpb.setTarget(target[0], Integer.parseInt(target[1]));
					} catch (NumberFormatException e) {
						System.err.println("Minion can't parse port: `"+target[1]+"`");
					}
            		break;
            	case ACLMessage.INFORM: // new interval
            		int interval = Integer.parseInt(content);
            		tcpb.reset(interval);
            		break;
            	case ACLMessage.CANCEL: // stop attack, delete agent
            		getAgent().doDelete();
            		break;
            	default:
            		System.err.println("Minion received unkown performative:"+performative+"/content"+content);
            	}
            }
		}
    }
    
    /**
     * checks if it's spawner is still around
     * if not, the agent kills itself
     * 
     * Owner can be null
     * @author nitzel
     *
     */
    protected class BehaviourKillIfOwnerIsDead extends TickerBehaviour {
    	Agent owner;
		public BehaviourKillIfOwnerIsDead(Agent agent, long period, Agent owner) {
			super(agent, period);
			this.owner = owner;
		}
		@Override
		protected void onTick() {
			if(owner == null) return;
			int ownerNum = Overseer.getReceivers(getAgent(), Overseer.TYPE_SPAWNER, Overseer.GET_RECEIVERS_ALL, null, owner.getName()).length;
			if(ownerNum==0)
				getAgent().doDelete();
		}
    }
    
    /**
     * Opens and closes tcp connection :)
     * Is runnable to be non-blocking while 
     * @author nitzel
     *
     */
    protected class BehaviourOpenTCPConnection extends TickerBehaviour implements Runnable{
    	String target;
    	int targetPort;
    	//java.util.Vector<Socket> sockets; // list of open connections // must be closed onEnd()
    	public BehaviourOpenTCPConnection(Agent agent, long period, String target, int targetPort) {
			super(agent, period);

			//sockets = new java.util.Vector<>();
			this.setTarget(target, targetPort);
		}
    	/**
    	 * 
    	 * @param target new target to attack. use null or emptystring to halt attack
    	 * @param targetPort targets port
    	 */
    	public void setTarget(String target, int targetPort){
    		this.onEnd(); // clear all connections
			this.target = target;
			this.targetPort = targetPort;
    	}
    	
		@Override
		protected void onTick() {
			new Thread(this).start();
		}

		/**
		 * Opens a socket to target and closes it then
		 */
		@Override
		public void run() {
			if(target == null || target.length()==0) return;
			try {
				Socket s = new Socket(target, targetPort);
				//sockets.add(s); //add socket to list
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
    }
}
