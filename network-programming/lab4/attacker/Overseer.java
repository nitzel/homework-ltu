package lab4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jade.core.AID;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

/*
-gui
-jade_domain_df_maxresult 100000
"Overseer:lab4.Overseer;Spawner1:lab4.MinionSpawner;Spanner2:lab4.MinionSpawner;stdminion:lab4.Minion;stdminion2:lab4.Minion;stdminion3:lab4.Minion"
 */

/**
 * coordinates the attacking minions and creates new
 * @author nitzel
 *
 */
@SuppressWarnings("serial")
public class Overseer extends Agent{
	public static String TYPE_MINION = "minion";
	public static String TYPE_SPAWNER = "nefario";
	public static String TYPE_OVERSEER = "gru";
	public static int GET_RECEIVERS_ALL = -1;
	protected OverseerGUI gui;
	protected int spawnerNum = 0;
	
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
				case OverseerGUI.CMD_PAUSE_ATTACK: // update receiver list
					System.out.println("stopping");
					sendMsgTarget(":0");
					break;
				case OverseerGUI.CMD_SET_MINIONS:
					int newAmount = gui.getNew();
					System.out.println("setting minionAmount to "+newAmount);
					sendMsgSpawn(newAmount);
					break;
				case OverseerGUI.CMD_SET_INTERVAL:
					int newInterval = gui.getInterval();
					System.out.println("setting minion interval to "+newInterval);
					sendMsgInterval(newInterval);
					break;
				}
			}
    	});
    	gui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e){
				doDelete();
			}
		});
    	
        ReceiveMessageBehaviour rm = new ReceiveMessageBehaviour(this, 10);
        addBehaviour(rm);    
        addBehaviour( new UpdateBehaviour(this,1000));
        
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
     * @param num Minions to have per spawner
     * @return spawned minions
     */
    protected int sendMsgSpawn(int num){
    	String[] receivers = getReceivers(this, Overseer.TYPE_SPAWNER);
    	if(receivers.length==0){
    		System.err.println("no spawners");
    		return 0;
    	}
    	//int numPerSpawner = num/receivers.length;
    	//sendMsg(ACLMessage.REQUEST, receivers, numPerSpawner+"");
    	
    	
    	// spread evenly on spawners
    	for(int i = receivers.length; i>0; i--){
    		int minionsToOwn = num / i;
    		
    		sendMsg(ACLMessage.REQUEST, receivers[i-1], minionsToOwn+"");
    		
    		num -= minionsToOwn;
    	}
    	
    	return num; //numPerSpawner*receivers.length;
    }
    protected void sendMsgInterval(int interval){
    	String[] receivers = getReceivers(this, Overseer.TYPE_MINION);
    	sendMsg(ACLMessage.INFORM, receivers, interval+"");
    }
    
    protected void sendMsgKill(int num){
    	String[] receivers = getReceivers(this, Overseer.TYPE_MINION, num);
    	sendMsg(ACLMessage.CANCEL, receivers, null);
    }
    protected void sendMsgTarget(String target){
    	String[] receivers = getReceivers(this, Overseer.TYPE_MINION);
    	sendMsg(ACLMessage.PROPAGATE, receivers, target);
    }
    protected void sendMsg(int performative, String receiver, String content){
    	sendMsg(performative, new String[]{receiver}, content);
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
    
    static public String[] getReceivers(Agent agent, String type){
    	return getReceivers(agent, type, Overseer.GET_RECEIVERS_ALL, null, null);
    }
    static public String[] getReceivers(Agent agent, String type, int max){
    	return getReceivers(agent, type, max, null, null);
    }
    static public String[] getReceivers(Agent agent, String type, int max, Agent owner){
    	return getReceivers(agent, type, max, owner, null);
    }
    /**
     * 
     * @param type
     * @param max Maximum amount of receivers to be returned. max=-1 takes all
     * @param owner The one to own the search clients
     * @return
     */
	static public String[] getReceivers(Agent agent, String type, int max, Agent owner, String name){
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
        if(type != null)
        	sd.setType(type);
        if(name != null)
        	sd.setName(name);
        if(owner != null)
        	sd.setOwnership(owner.getName());
        dfd.addServices(sd);
        
        //jade.domain.FIPAAgentManagement.SearchConstraints searchParams = new jade.domain.FIPAAgentManagement.SearchConstraints();
        //searchParams.setMaxResults(1000L);
        //searchParams.setMaxDepth(1000L);
        
        DFAgentDescription[] agents;
		try {
			agents = DFService.search(agent, dfd);
		} catch (FIPAException e) {
			//e.printStackTrace();
			agents = new DFAgentDescription[0]; // say we didnt find any
		}
		if(type.equals(Overseer.TYPE_MINION))
			System.err.println("Found "+agents.length+" agents of "+type);
		// convert agents to string array with their names
		int len = Math.min(agents.length, (max==Overseer.GET_RECEIVERS_ALL)?agents.length:max);

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
        gui.close();
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
			int spawnerNumNew = getReceivers(getAgent(), TYPE_SPAWNER).length;
			if(spawnerNumNew != spawnerNum){
				// TODO resend the spawn minions message to spread them 
			}
			gui.setSpawnernum(spawnerNum + "");
			gui.setCurrent(getReceivers(getAgent(), TYPE_MINION).length);
		}
    }
}
