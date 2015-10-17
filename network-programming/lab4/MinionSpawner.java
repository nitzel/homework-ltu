package lab4;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class MinionSpawner extends Agent{
    protected void setup() {    	
        System.out.println("Hello World! My name is " + getAID().getLocalName());
        
        BehaviourReceiveMessage rm = new BehaviourReceiveMessage(this, new Random().nextInt());
        addBehaviour(rm);
        
        registerAgent();
    }

	/** 
	 * Registration with the DF 
	 */
    protected void registerAgent(){
	    DFAgentDescription dfd = new DFAgentDescription();
	    ServiceDescription sd = new ServiceDescription();
	    sd.setType(Overseer.TYPE_SPAWNER);
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies(Overseer.TYPE_SPAWNER);
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
    @Override
    public void takeDown(){
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
    }
    
    /**
	 * Receives Messages and spawns minions
     * @author nitzel
     *
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
    	int id, counter;
    	public BehaviourReceiveMessage(Agent a, int id) {
    		super(a, 100); // 100msec between calls
			this.id = id; // id of the spawner
			this.counter = 0; // id + counter = id of Minion
		}
		@Override
		public void onTick() {
            //Receive a Message
            ACLMessage msg = blockingReceive(100);
            if(msg != null) {
            	int performative = msg.getPerformative();
            	String content = msg.getContent();
            	
            	switch(performative){
            	case ACLMessage.REQUEST: // start new agents
            		int num = Integer.parseInt(content)+counter;
            		System.out.println("spawn "+content+" new minions! :)");
            		AgentContainer ct = getAgent().getContainerController();
            		for(; counter<num; counter++){
            			try {
							ct.createNewAgent("minion"+id+":"+counter, "lab4.Minion",null).start();
						} catch (StaleProxyException e) {
							System.err.println("Spawner failed to create minion"+id+":"+counter);
							e.printStackTrace();
						}
            		}
            		break;
            	default:
            		System.err.println("Spawner received unkown performative:"+performative+"/content"+content);
            	}
            }
		}
    }
}
