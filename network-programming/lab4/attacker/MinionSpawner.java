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

/**
 * The minionspawner is an agent that waits for commands from the 
 * Overseer to then create/delete new Minions.
 * @author nitzel
 *
 */
@SuppressWarnings("serial")
public class MinionSpawner extends Agent{
	/**
	 * general configuration of the agent like
	 * - registering with df
	 * - adding behaviours
	 */
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
    /**
     * Kills the whole java system when the spawner dies. Massive destruction!
     */
    @Override
    public void takeDown(){
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
    	// todo maybe kill program here
        System.exit(0);
    }
    /**
     * Send a CANCEL message to Minions to kill them 
     * @param receivers a list of Minions
     * @param num How many to kill
     */
    protected void sendMsgKill(String[] receivers, int num){
    	String[] recvs = new String[num];
    	System.out.println(".........killing "+num+" of "+receivers.length);
    	for(int i=0; i<Math.min(num, receivers.length); i++){ // only take the desired receivers
    		System.out.println("killing "+receivers[i]);
    		recvs[i]=receivers[i];
    	}
    	// send kill-message to them
    	sendMsg(ACLMessage.CANCEL, recvs, null);
    }
    /**
     * Forwards a message to all the minions owned by this spawner
     * @param msg the message to forward
     */
    protected void forwardToMinions(ACLMessage msg){
    	String[] myMinions = Overseer.getReceivers(this, 
				Overseer.TYPE_MINION, 
				Overseer.GET_RECEIVERS_ALL, 
				this);
    	sendMsg(msg.getPerformative(), myMinions, msg.getContent());
    }
    /**
     * Send a message to eg. minions
     * @param performative
     * @param receivers list of receivers
     * @param content
     */
    protected void sendMsg(int performative, String[] receivers, String content){
        ACLMessage msg = new ACLMessage(performative);
        for(String receiver : receivers){ // add all receivers to the message
        	msg.addReceiver(new AID(receiver, AID.ISGUID));
        }
        if(content!=null)
        	msg.setContent(content);
        
        send(msg);
    }
    
    /**
	 * Receives Messages and spawns minions
	 * - REQUEST:data ("numberOfMinionsToMaintain")
     * @author nitzel
     *
     */
    protected class BehaviourReceiveMessage extends TickerBehaviour {
    	int id; // id of the Spawner
    	int counter; // how many minions this class has spawned so far
    	public BehaviourReceiveMessage(Agent a, long period, int id) {
    		super(a, period); // 100msec between calls
			this.id = id; // id of the spawner
			this.counter = 0; // id + counter = id of Minion
		}
    	/**
    	 * Here the REQUEST messages are processed and depending on their content
    	 * minions are created or removed to have the desired amount running.
    	 */
		@Override
		public void onTick() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            	int performative = msg.getPerformative();
            	String content = msg.getContent();
            	
            	switch(performative){
            	case ACLMessage.REQUEST: // start new agents
            		int desiredNum = Integer.parseInt(content);
            		
            		// get all Minions that this object owns
            		String[] myMinions = Overseer.getReceivers(getAgent(), 
            							Overseer.TYPE_MINION, 
            							Overseer.GET_RECEIVERS_ALL, 
            							getAgent());
            		// num is the offset from the actual number of minions to the desired number
            		int num = desiredNum - myMinions.length; 
            		
            		System.out.println("spawn "+num+" to end up with "+content+" new minions! :)");
            		
            		if(num>0) {// create agents because we have less than desired
                		// AgentContainer to spawn minions in here
                		AgentContainer ct = getAgent().getContainerController();
	            		for(int i=0; i<num; counter++, i++){
	            			try {
	            				// create a new agent with unique ID and the spawner as its parent
								AgentController newMinion = ct.createNewAgent("minion"+id+":"+counter, "lab4.Minion",new Object[]{getAgent()});
								// start the new agent
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
            								// forward these performatives to all my minions
            	case ACLMessage.PROPAGATE: 	// received new target
        	case ACLMessage.INFORM: 		// new interval
        			System.out.println("Forwarding msg to minions: "+ACLMessage.getPerformative(performative)+" : "+content);
            		forwardToMinions(msg);
            		break;
            	default:
            		System.err.println("Spawner received unkown performative:"+ACLMessage.getPerformative(performative)+"/content"+content);
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
		/**
		 * counts Overseers and if there is none left, it kills itself
		 */
		@Override
		protected void onTick() {
			int overseerNum = Overseer.getReceivers(getAgent(), Overseer.TYPE_OVERSEER, Overseer.GET_RECEIVERS_ALL).length;
			if(overseerNum==0)
				getAgent().doDelete();
		}
    }
}
