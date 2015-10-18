package lab4;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class MinionSpawner extends Agent{
	
    protected void setup() {    	
        System.out.println("Hello World! My name is " + getAID().getLocalName());
        
        registerAgent();
        
        addBehaviour(new BehaviourKillIfOverseerIsDead(this, 1000));
        addBehaviour(new BehaviourReceiveMessage(this, 100, new Random().nextInt()));
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
    
    protected void sendMsgKill(String[] receivers, int num){
    	String[] recvs = new String[num];
    	System.out.println(".........killing "+num+" of "+receivers.length);
    	for(int i=0; i<Math.min(num, receivers.length); i++){
    		System.out.println("killing "+receivers[i]);
    		recvs[i]=receivers[i];
    	}
    	
    	sendMsg(ACLMessage.CANCEL, recvs, null);
    }
    protected void sendMsg(int performative, String[] receivers, String content){
        ACLMessage msg = new ACLMessage(performative);
        for(String receiver : receivers){
        	msg.addReceiver(new AID(receiver, AID.ISGUID));
        }
        if(content!=null)
        	msg.setContent(content);
        
        send(msg);
    }
    
    /**
	 * Receives Messages and spawns minions
     * @author nitzel
     *
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
    	int id, counter;
    	public BehaviourReceiveMessage(Agent a, long period, int id) {
    		super(a, period); // 100msec between calls
			this.id = id; // id of the spawner
			this.counter = 0; // id + counter = id of Minion
		}
		@Override
		public void onTick() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            	int performative = msg.getPerformative();
            	String content = msg.getContent();
            	
            	switch(performative){
            	case ACLMessage.REQUEST: // start new agents
            		int num = Integer.parseInt(content);
            		
            		// get Minions that this object owns
            		String[] myMinions = Overseer.getReceivers(getAgent(), 
            							Overseer.TYPE_MINION, 
            							Overseer.GET_RECEIVERS_ALL, 
            							getAgent());
            		num -= myMinions.length;
            		System.out.println("spawn "+num+" to end up with "+content+" new minions! :)");
            		AgentContainer ct = getAgent().getContainerController();
            		
            		if(num>0) {// create agents because we have less than desired
	            		for(int i=0; i<num; counter++, i++){
	            			try {
								AgentController newMinion = ct.createNewAgent("minion"+id+":"+counter, "lab4.Minion",new Object[]{getAgent()});
								newMinion.start();
							} catch (StaleProxyException e) {
								System.err.println("Spawner failed to create minion"+id+":"+counter);
								e.printStackTrace();
							}
	            		}
            		}
            		else if(num < 0){ // remove agents because we have more than desired
            			sendMsgKill(myMinions, -num);
            		} else {
            		}
            		break;
            	default:
            		System.err.println("Spawner received unkown performative:"+performative+"/content"+content);
            	}
            }
		}
    }
    
    /**
     * checks if there is still an overseer alive
     * if not, the agent kills itself
     * @author nitzel
     *
     */
    protected class BehaviourKillIfOverseerIsDead extends TickerBehaviour {
		public BehaviourKillIfOverseerIsDead(Agent agent, long period) {
			super(agent, period);
		}
		@Override
		protected void onTick() {
			int overseerNum = Overseer.getReceivers(getAgent(), Overseer.TYPE_OVERSEER, Overseer.GET_RECEIVERS_ALL).length;
			if(overseerNum==0)
				getAgent().doDelete();
		}
    }
}
