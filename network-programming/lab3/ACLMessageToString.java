package lab3;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * Contains an ACLMessage object and gives a nicer toString representation
 * to be used within a JList
 */
public class ACLMessageToString {
	protected ACLMessage msg;

	public ACLMessageToString(ACLMessage msg){
		this.msg = msg;
	}
	
	/**
	 * get a nice String which contains all interesting information of a ACLMessage object
	 */
	public String toString(){
        String messagePerformative = ACLMessage.getPerformative(msg.getPerformative());
        String messageContent = msg.getContent();
        String senderName = msg.getSender().getLocalName(); // getName() for GUID
        @SuppressWarnings("unchecked")
        String receiverName = ((java.util.Iterator<AID>)msg.getAllReceiver()).next().getLocalName();
    	
        return "("+messagePerformative+")"+senderName+"→"+receiverName+":"+messageContent;
	}

	/**
	 * Get the ACLMessage object
	 * @return ACLMessage object which is used for communication between agents
	 */
	public ACLMessage getMsg() {
		return msg;
	}
}
