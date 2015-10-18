package lab4;

import java.net.Socket;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Minion extends Agent{
	protected BehaviourOpenTCPConnection tcpb;
	protected Agent owner;
	
    protected void setup() {    	
        System.out.println("Hello World! My name is " + getAID().getLocalName());

        registerAgent();
        
        tcpb = new BehaviourOpenTCPConnection(this, 1000, null, 2223);
        addBehaviour(tcpb);
        addBehaviour(new BehaviourReceiveMessage(this, 100));
        addBehaviour(new BehaviourKillIfOwnerIsDead(this, 1000, owner));
    }

	/** 
	 * Registration with the DF 
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
	    	System.out.println("setting owner of "+getLocalName()+" to: " + owner.getName());
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
     * if agent is stopped
     */
    @Override
    protected void takeDown(){
    	tcpb.onEnd(); // close connections
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
    }
    
    /**
     * What to do on the receival of a message
     * @author nitzel
     *
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
		
    	public BehaviourReceiveMessage(Agent a, long period) {
			super(a, period); // 100msec between the calls
		}

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
						//tcpb.restart();
					} catch (NumberFormatException e) {
						System.err.println("Minion can't parse port: `"+target[1]+"`");
					}
            		break;
            	case ACLMessage.INFORM: // new interval
            		int interval = Integer.parseInt(content);
            		System.out.println(getAgent().getLocalName()+" changed period to "+interval);
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
     * checks if there it's spawner is still around
     * if not, the agent kills itself
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
     * Opens a tcp connection :)
     * Is runnable to be non-blocking
     * @author nitzel
     *
     */
    protected class BehaviourOpenTCPConnection extends TickerBehaviour implements Runnable{
    	String target;
    	int targetPort;
    	//java.util.Vector<Socket> sockets; // list of open connections
    	public BehaviourOpenTCPConnection(Agent agent, long period, String target, int targetPort) {
			super(agent, period);

			//sockets = new java.util.Vector<>();
			this.setTarget(target, targetPort);
		}
    	
    	public void setTarget(String target, int targetPort){
    		this.onEnd(); // clear all connections
			this.target = target;
			this.targetPort = targetPort;
    	}
    	
		@Override
		protected void onTick() {
			new Thread(this).start();
		}

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
		
		@Override
		public int onEnd(){
			/*for(Socket s : sockets){ // close all open sockets
				try {
					s.close();
				} catch (IOException e) {}
			}*/
			return 0;
		}
    }
}
