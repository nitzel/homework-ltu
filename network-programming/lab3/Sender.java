package lab3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Sender extends Agent{
	SenderGUI gui;
    protected void setup() {
    	/// set GUI up
    	gui = new SenderGUI();
    	/// fill gui with data
    	// performatives
    	gui.setPerformativeContent(getPerformatives()); // set dropdown
    	gui.setPerformativeSelected("REQUEST"); // select as default
    	// get receivers and add them to the dropdown
		gui.setReceiverContent(getReceivers());
    	
		// actionlistener for the buttons
    	gui.addButtonActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand()){
				case SenderGUI.CMD_UPDATE: // update receiver list
					gui.setReceiverContent(getReceivers());
					break;
				case SenderGUI.CMD_SEND:
					System.out.println("sending!");
					addBehaviour(
							new SendMessage(
									gui.getReceiverSelection(),	// receiver name
									gui.getContentText(), // msg content
									gui.getPerformativeSelectionId())); // msg type
					break;
				case SenderGUI.CMD_CANCEL: // TODO idk whatever! clear everything!
					gui.setContentText("");
					gui.setReceiverContent(new String[0]);
					gui.clearOutput();
					break;
				}
			}
    	});
		gui.addListSentMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					System.out.println(gui.getListSentSelected());
					fillGuiWithMessage(gui.getListSentSelected().getMsg());
				}
			}
		});
		gui.addListReceivedMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					System.out.println(gui.getListReceivedSelected());
					fillGuiWithMessage(gui.getListReceivedSelected().getMsg());
				}
			}
		});
    	
        // receiver-behaviour to display received messages
    	this.addBehaviour(new ReceiveMessage());
    }
	    
	protected String[] getPerformatives(){
		return ACLMessage.getAllPerformativeNames();
	}
	
	protected String[] getReceivers(){
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
        sd.setType( "ReceiverAgent" );
        dfd.addServices(sd);
        
        DFAgentDescription[] agents;
		try {
			agents = DFService.search(this, dfd);
		} catch (FIPAException e) {
			//e.printStackTrace();
			agents = new DFAgentDescription[0]; // say we didnt find any
		}
		// convert agents to string array with their names
		String[] result = new String[agents.length];
		for(int i=0; i<agents.length; i++){
			result[i] = agents[i].getName().getName(); // maybe this one instead of the other?
			//result[i] = agents[i].getName().getLocalName();
		}
		return result;
	}
	

    protected void fillGuiWithMessage(ACLMessage msg){
    	String senderName = msg.getSender().getName();
    	if(senderName.equals(this.getName())){
        	@SuppressWarnings("unchecked")
    		java.util.Iterator<AID> it = msg.getAllReceiver();
    		senderName = it.next().getName();
    	}
    	
    	gui.setPerformativeSelected(msg.getPerformative());
    	gui.setReceiverSelected(senderName);
    	gui.setContentText(msg.getContent());
    }
	
    protected class SendMessage extends OneShotBehaviour {
    	String receiverName;
        String content;
        int performative;
        
    	public SendMessage(String receiverName, String content, int performative){
    		super();
    		this.receiverName = receiverName;
    		this.content = content;
    		this.performative = performative;
    		System.out.println("sendMessage created");
    	}
    	
        public void action() {
    		System.out.println("sendMessage sending");

	        ACLMessage msg = new ACLMessage(performative);
	        msg.addReceiver(new AID(receiverName, AID.ISGUID)); //AID.ISLOCALNAME
	        msg.setLanguage("English");
	        msg.setContent(content);
	        
	        send(msg);
	        
	        gui.addToSent(new ACLMessageToString(msg));
	        //System.out.println("****I Sent Message to::> R1 *****" + "\n" +
	        //"The Content of My Message is::>" + msg.getContent());
        }

    }

    protected class ReceiveMessage extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = getAgent().blockingReceive(100);
            if(msg != null) {
                gui.addToReceived(new ACLMessageToString(msg));//("From `"+senderName+"` as `"+messagePerformative+"`:"+messageContent);
            }
        }
    }
}
