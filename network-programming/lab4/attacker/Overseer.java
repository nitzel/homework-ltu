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
 * The Overseer commands the behavior of the Minions via the MinionSpawners.
 * It tells these who rest in the middle of the communication chain 
 * to do and they obey and create/delete minions and forward the tasks,
 * making sure the Overseers wishes shall be fulfilled.
 * @author nitzel
 */
@SuppressWarnings("serial")
public class Overseer extends Agent{
	/** constant to set the types of the agents in the DF*/
	public static String TYPE_MINION = "minion"
						,TYPE_SPAWNER = "nefario"
						,TYPE_OVERSEER = "gru";
	/** constant for the getReceivers method to declare that max=infinity */
	public static int GET_RECEIVERS_ALL = -1;
	protected OverseerGUI gui;
	/** kept up to date but not used for anything till now */
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
				case OverseerGUI.CMD_SET_MINIONS: // amount of minions shall be changed, send msg to the spawners
					int newAmount = gui.getNew();
					System.out.println("setting minionAmount to "+newAmount);
					sendMsgSpawn(newAmount);
					break;
				case OverseerGUI.CMD_SET_INTERVAL:	// interval between attacks shall be changed, send msg to minions via spawners
					int newInterval = gui.getInterval();
					System.out.println("setting minion interval to "+newInterval);
					sendMsgInterval(newInterval);
					break;
				}
			}
    	});
    	// what to do when the gui is closed
    	gui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e){
				doDelete();
			}
		});
    	// keeping gui info up to date (counting minions and spawners)
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
     * @return number of spawned minions - dont rely on this number
     */
    protected int sendMsgSpawn(int num){
    	String[] receivers = getReceivers(this, Overseer.TYPE_SPAWNER);
    	if(receivers.length==0){
    		System.err.println("no spawners");
    		return 0;
    	}    	
    	
    	// spread evenly on spawners
    	for(int i = receivers.length; i>0; i--){
    		int minionsToOwn = num / i;
    		
    		sendMsg(ACLMessage.REQUEST, receivers[i-1], minionsToOwn+"");
    		
    		num -= minionsToOwn;
    	}
    	
    	return num; //numPerSpawner*receivers.length;
    }
    /**
     * Sends a message to all minions via their spawners to set a new interval
     * @param interval The period to wait between attacks
     */
    protected void sendMsgInterval(int interval){
    	String[] receivers = getReceivers(this, Overseer.TYPE_SPAWNER);
    	sendMsg(ACLMessage.INFORM, receivers, interval+"");
    }
    /**
     * Sends a message to all minions via their spawners to set a new target
     * @param target A string ip:port for the new target, can also use DNS:port
     */
    protected void sendMsgTarget(String target){
    	String[] receivers = getReceivers(this, Overseer.TYPE_SPAWNER);
    	sendMsg(ACLMessage.PROPAGATE, receivers, target);
    }
    /**
     * General purpose message-sending method to send a message (performative, content) to one receiver
     * @param performative
     * @param content
     * @param receiver GUID of the receiver
     */
    protected void sendMsg(int performative, String receiver, String content){
    	sendMsg(performative, new String[]{receiver}, content);
    }
    /**
     * General purpose message-sending method to send a message (performative, content) to an array of receivers
     * @param performative
     * @param content
     * @param receivers - String array with the names of the receivers (GUIDs)
     */
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
     * @param agent The agent starting the search
     * @param type Type of the agents you are looking fore
     * @return All Agent fitting the description. Set parameters to null to ignore them
     */
    static public String[] getReceivers(Agent agent, String type){
    	return getReceivers(agent, type, Overseer.GET_RECEIVERS_ALL, null, null);
    }
    /**
     * @param agent The agent starting the search
     * @param type Type of the agents you are looking fore
     * @param max Maximum amount of results
     * @return All Agent fitting the description. Set parameters to null to ignore them
     */
    static public String[] getReceivers(Agent agent, String type, int max){
    	return getReceivers(agent, type, max, null, null);
    }
    /**
     * @param agent The agent starting the search
     * @param type Type of the agents you are looking fore
     * @param max Maximum amount of results
     * @param owner All the Agents to be found need to have .setOwnership(owner.getName())
     * @return All Agent fitting the description. Set parameters to null to ignore them
     */
    static public String[] getReceivers(Agent agent, String type, int max, Agent owner){
    	return getReceivers(agent, type, max, owner, null);
    }
    /**
     * @param agent The agent starting the search
     * @param type Type of the agents you are looking fore
     * @param max Maximum amount of results
     * @param owner All the Agents to be found need to have .setOwnership(owner.getName())
     * @param name Name of the agent you are looking for
     * @return All Agent fitting the description. Set parameters to null to ignore them
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
	/**
	 * When the Agent is deleted, it will deregister and close the GUI
	 */
    public void takeDown(){
        try {  // deregister
        	DFService.deregister(this); 
    	}catch (Exception e) {}
        gui.close();
    }
    /**
     * - see how many minions are registered
     * - see how many spawners are registered
     * it will update the GUI with this information
     * @author nitzel
     */
    public class UpdateBehaviour extends TickerBehaviour {
		public UpdateBehaviour(Agent a, long period) {
			super(a, period);
		}

		/**
		 * count minions & spawners and update gui
		 */
		@Override
		protected void onTick() {
			int spawnerNumNew = getReceivers(getAgent(), TYPE_SPAWNER).length;
			if(spawnerNumNew != spawnerNum){
				spawnerNum = spawnerNumNew;
				// TODO resend the spawn minions message to spread them 
			}
			gui.setSpawnernum(spawnerNum + "");
			gui.setCurrent(getReceivers(getAgent(), TYPE_MINION).length);
		}
    }
}
