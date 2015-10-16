package lab4;

// jade.core.replication
// jade.core.management


import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
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
    protected void setup() {

 /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("overseer");
        sd.setName(getName());
        sd.addOntologies("overseer");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
        	DFService.register(this,dfd);
        } catch (FIPAException e) {
	        System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
	        doDelete();
        }

        System.out.println("Hello World! My name is " + getAID().getLocalName());
        ReceiveMessageBehaviour rm = new ReceiveMessageBehaviour();
        addBehaviour(rm);
    }

    public class ReceiveMessageBehaviour extends CyclicBehaviour {
        public void action() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {
            }
        }
    }
}
