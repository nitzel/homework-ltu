package lab4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jade.core.AID;

// jade.core.replication
// jade.core.management


import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/**
 * coordinates the attacking minions and creates new
 * @author nitzel
 *
 */
public class Overseer extends Agent{
	public static String TYPE_MINION = "minion";
	public static String TYPE_SPAWNER = "minionspawner";
	public static String TYPE_OVERSEER = "minionoverseer";
	
	protected OverseerGUI gui;
	
    protected void setup() {
        // setup GUI
        gui = new OverseerGUI();
        gui.setSpawnernum("?");
        gui.setCurrent(0);
        gui.setTarget("TeamWhateverLB-1369488506.eu-west-1.elb.amazonaws.com:2223");
    	gui.addButtonActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()){
				case OverseerGUI.CMD_SET_TARGET: // update receiver list
					System.out.println("sending new target "+gui.getTarget());
					sendMsgTarget(gui.getTarget());
					break;
				case OverseerGUI.CMD_SET_MINIONS:
					System.out.println("set new minions");
					int diff = gui.getDiff();
					if(diff > 0){
						System.out.println("creating "+diff);
						sendMsgSpawn(diff);
					} else {
						diff *= -1;
						System.out.println("killing "+diff);
						sendMsgKill(diff);
					}
					break;
				}
				System.out.println("done");
			}
    	});
        
        
        
        
        
        System.out.println("Hello World! My name is " + getAID().getLocalName());
        ReceiveMessageBehaviour rm = new ReceiveMessageBehaviour(this, 10);
        addBehaviour(rm);    
        addBehaviour( new UpdateBehaviour(this,500));
        
        registerAgent();
    }
    
	/** 
	 * Registration with the DF 
	 */
    protected void registerAgent(){
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TYPE_OVERSEER);
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies(Overseer.TYPE_OVERSEER);
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
     * @param num Minions to spawn
     * @return spawned minions
     */
    protected int sendMsgSpawn(int num){
    	// TODO spread evenly depending on usage
    	String[] receivers = getReceivers(Overseer.TYPE_SPAWNER);
    	if(receivers.length==0){
    		System.err.println("no spawners");
    		return 0;
    	}
    	int numPerSpawner = num/receivers.length;
    	sendMsg(ACLMessage.REQUEST, receivers, numPerSpawner+"");
    	
    	return numPerSpawner*receivers.length;
    }
    protected void sendMsgKill(int num){
    	String[] receivers = getReceivers(Overseer.TYPE_MINION, num);
    	sendMsg(ACLMessage.CANCEL, receivers, null);
    }
    protected void sendMsgTarget(String target){
    	System.out.println("getting receivers");
    	String[] receivers = getReceivers(Overseer.TYPE_MINION);
    	System.out.println("sending message");
    	sendMsg(ACLMessage.PROPAGATE, receivers, target);
    	System.out.println("target set!");
    }
    protected void sendMsg(int performative, String receiver, String content){
    	sendMsg(performative, new String[]{receiver}, content);
    }
    protected void sendMsg(int performative, String[] receivers, String content){
        ACLMessage msg = new ACLMessage(performative);
        for(String receiver : receivers){
        	System.out.print("+"+receiver);
        	msg.addReceiver(new AID(receiver, AID.ISGUID));
        	System.out.println("!");
        }
        if(content!=null)
        	msg.setContent(content);
        
        send(msg);
    }
    
    protected String[] getReceivers(String type){
    	return getReceivers(type, -1);
    }
    /**
     * 
     * @param type
     * @param max Maximum amount of receivers to be returned. max=-1 takes all
     * @return
     */
	protected String[] getReceivers(String type, int max){
    	/*
    	http://www.iro.umontreal.ca/~vaucher/Agents/Jade/primer5.html
    	To search the DF, you must create a DFD [with no AID] where the relevant fields are initialised 
    	to the properties you require. The search returns an array of DFDs (with AIDs) whose attributes 
    	match your description and you can extract the ID of suitable agents from those entries. 
    	Generally, you either want to find ONE agent with the services you need or ALL of them. 
    	The documentation says that, by default, the search returns an array which is either empty or 
    	contains a single DFD and to get more, you must add a third parameter: 
    	searchConstraints where you specify the max number of replies (-1 means ALL). */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(type);
        dfd.addServices(sd);
        
        DFAgentDescription[] agents;
		try {
			agents = DFService.search(this, dfd);
		} catch (FIPAException e) {
			//e.printStackTrace();
			agents = new DFAgentDescription[0]; // say we didnt find any
		}
		// convert agents to string array with their names
		int len = Math.min(agents.length, max==-1?agents.length:max);

		String[] result = new String[len];

		for(int i=0; i<len; i++){
			result[i] = agents[i].getName().getName(); 
		}
		return result;
	}

    public void takeDown(){
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
    }
    public class ReceiveMessageBehaviour extends TickerBehaviour {
        
    	public ReceiveMessageBehaviour(Agent a, long period) {
			super(a, period);
		}

		@Override
    	public void onTick() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            }
        }
    }
    public class UpdateBehaviour extends TickerBehaviour {
		public UpdateBehaviour(Agent a, long period) {
			super(a, period);
		}

		@Override
		protected void onTick() {
			gui.setSpawnernum(getReceivers(TYPE_SPAWNER).length+"");
			gui.setCurrent(getReceivers(TYPE_MINION).length);
		}
    }
}
