package lab3;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.Choice;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * A GUI to be used with the Sender-Agent.
 * It offers a wide interface to receive/alter the displayed and install listeners
 * to some of the widgets.
 */
public class SenderGUI {
	/**
	 * Only for testing
	 */
	public static void main(String[] args) {
		new SenderGUI();
	}
	

	public static final String CMD_UPDATE = "update";
	public static final String CMD_SEND = "send";
	public static final String CMD_CANCEL = "cancle";
	
	private JFrame frmSn;
	private JTextField textContent;
	private JButton btnUpdate;
	private JButton btnSend;
	private JButton btnCancel;
	private Choice dropdownReceiver;
	private Choice dropdownPerformative;
	private JList<ACLMessageToString> listSent;
	private JList<ACLMessageToString> listReceived;

	private DefaultListModel<ACLMessageToString> listSentModel;
	private DefaultListModel<ACLMessageToString> listReceivedModel;
	
	/**
	 * clears both JTextAreas 
	 */
	public void clearOutput(){
		listSentModel.clear();
		listReceivedModel.clear();
	}
	
	/**
	 * @return the text within textContent
	 */
	public String getContentText(){
		return textContent.getText();
	}
	/**
	 * @param content will be the new text within the textfield textContent
	 */
	public void setContentText(String content){
		textContent.setText(content);
	}
	
	/**
	 * Clears the dropdown list and adds the fields of itemList as items to it
	 * @param itemList
	 */
	public void setReceiverContent(String[] itemList){
		dropdownReceiver.removeAll();
		for(String item : itemList)
			dropdownReceiver.add(item);
	}
	/**
	 * @return selected item in the performative dropdown selection
	 */
	public String getReceiverSelection(){
		return dropdownReceiver.getSelectedItem();
	}
	/**
	 * @param id Item with this id will be selected. First item has id = 0
	 */
	public void setReceiverSelected(int id){
		dropdownPerformative.select(id);
	}
	/**
	 * @param item Item with this name will be selected.
	 */
	public void setReceiverSelected(String item){
		// find 
		for(int id = 0; id < dropdownReceiver.getItemCount(); id++){
			if(dropdownReceiver.getItem(id).equals(item))
				dropdownReceiver.select(id);
		}
	}
	
	
	
	/**
	 * Clears the dropdown list and adds the fields of itemList as items to it
	 * @param itemList
	 */
	public void setPerformativeContent(String[] itemList){
		dropdownPerformative.removeAll();
		for(String item : itemList)
			dropdownPerformative.add(item);
	}
	/**
	 * @param id Item with this id will be selected. First item has id = 0
	 */
	public void setPerformativeSelected(int id){
		dropdownPerformative.select(id);
	}
	/**
	 * @param item Item with this name will be selected.
	 */
	public void setPerformativeSelected(String item){
		// find 
		for(int id = 0; id < dropdownPerformative.getItemCount(); id++){
			if(dropdownPerformative.getItem(id).equals(item))
				dropdownPerformative.select(id);
		}
	}
	/**
	 * @return selected item in the performative dropdown selection
	 */
	public String getPerformativeSelection(){
		return dropdownPerformative.getSelectedItem();
	}
	/**
	 * @return selected item in the performative dropdown selection, the first one being 0
	 */
	public int getPerformativeSelectionId(){
		return dropdownPerformative.getSelectedIndex();
	}
	
	/**
	 * Adds the string s to the beginning of the textfield textSent
	 * @param s
	 */
	public void addToSent(ACLMessageToString msg){
		listSentModel.add(0, msg);
	}	
	/**
	 * Adds the string s to the beginning of the textfield textReceived
	 * @param s
	 */
	public void addToReceived(ACLMessageToString msg){
		listReceivedModel.add(0, msg);
	}
	
	/**
	 * Add one actionListener to the 3 buttons and sets their ActionCommand to update/send/cancel
	 * @param a the ActionListener for all 3 buttons
	 */
	public void addButtonActionListener(ActionListener a){
		btnUpdate.addActionListener(a);
		btnUpdate.setActionCommand(CMD_UPDATE);
		
		btnSend.addActionListener(a);
		btnSend.setActionCommand(CMD_SEND);
		
		btnCancel.addActionListener(a);
		btnCancel.setActionCommand(CMD_CANCEL);
	}
	/**
	 * Adds a MouseListener to the listSent element
	 */
	public void addListSentMouseListener(MouseListener ml){
		listSent.addMouseListener(ml);
	}
	/**
	 * Adds a MouseListener to the listReceived element
	 */
	public void addListReceivedMouseListener(MouseListener ml){
		listReceived.addMouseListener(ml);
	}
	/**
	 * @return the selected element of the JList
	 */
	public ACLMessageToString getListSentSelected(){
		return listSent.getSelectedValue();
	}
	/**
	 * @return the selected element of the JList
	 */
	public ACLMessageToString getListReceivedSelected(){
		return listReceived.getSelectedValue();
	}
	/**
	 * Create the application.
	 * automatically turn visible
	 */
	public SenderGUI() {
		initialize();
		frmSn.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSn = new JFrame();
		try{ // if possible, use icon from jade
			frmSn.setIconImage(Toolkit.getDefaultToolkit().getImage(SenderGUI.class.getResource("/jade/tools/gui/images/messagetype.gif")));
		} catch(Exception|Error e ){
			System.out.println("Icon not found, using default");
		}
		frmSn.setTitle("Whatever");
		frmSn.setBounds(100, 100, 428, 222);
		frmSn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0, 0, 5};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 5, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0};
		frmSn.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblMsgPerformative = new JLabel("Msg performative");
		GridBagConstraints gbc_lblMsgPerformative = new GridBagConstraints();
		gbc_lblMsgPerformative.insets = new Insets(0, 0, 5, 5);
		gbc_lblMsgPerformative.gridx = 0;
		gbc_lblMsgPerformative.gridy = 0;
		frmSn.getContentPane().add(lblMsgPerformative, gbc_lblMsgPerformative);
		
		JLabel lblSentMessages = new JLabel("Sent messages");
		GridBagConstraints gbc_lblSentMessages = new GridBagConstraints();
		gbc_lblSentMessages.insets = new Insets(0, 0, 5, 5);
		gbc_lblSentMessages.gridx = 3;
		gbc_lblSentMessages.gridy = 0;
		frmSn.getContentPane().add(lblSentMessages, gbc_lblSentMessages);
		
		dropdownPerformative = new Choice();
		GridBagConstraints gbc_dropdownPerformative = new GridBagConstraints();
		gbc_dropdownPerformative.fill = GridBagConstraints.HORIZONTAL;
		gbc_dropdownPerformative.gridwidth = 2;
		gbc_dropdownPerformative.insets = new Insets(0, 0, 5, 5);
		gbc_dropdownPerformative.gridx = 0;
		gbc_dropdownPerformative.gridy = 1;
		frmSn.getContentPane().add(dropdownPerformative, gbc_dropdownPerformative);
		GridBagConstraints gbc_textSent = new GridBagConstraints();
		gbc_textSent.gridheight = 3;
		gbc_textSent.insets = new Insets(0, 0, 5, 5);
		gbc_textSent.fill = GridBagConstraints.BOTH;
		gbc_textSent.gridx = 3;
		gbc_textSent.gridy = 1;
		//frmSn.getContentPane().add(textSent, gbc_textSent);
		JScrollPane sentScroller = new JScrollPane(); 
		frmSn.getContentPane().add(sentScroller, gbc_textSent);
		
		listSent = new JList<>();
		listSent.setToolTipText("double click to reuse data");
		listSent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sentScroller.setViewportView(listSent);
		
		JLabel lblReceiver = new JLabel("Receiver");
		GridBagConstraints gbc_lblReceiver = new GridBagConstraints();
		gbc_lblReceiver.insets = new Insets(0, 0, 5, 5);
		gbc_lblReceiver.gridx = 0;
		gbc_lblReceiver.gridy = 2;
		frmSn.getContentPane().add(lblReceiver, gbc_lblReceiver);
		
		dropdownReceiver = new Choice();
		GridBagConstraints gbc_dropdownReceiver = new GridBagConstraints();
		gbc_dropdownReceiver.fill = GridBagConstraints.HORIZONTAL;
		gbc_dropdownReceiver.insets = new Insets(0, 0, 5, 5);
		gbc_dropdownReceiver.gridx = 0;
		gbc_dropdownReceiver.gridy = 3;
		frmSn.getContentPane().add(dropdownReceiver, gbc_dropdownReceiver);
		
		btnUpdate = new JButton("upd");
		btnUpdate.setToolTipText("fetch all ReceiverAgents available");
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 3;
		frmSn.getContentPane().add(btnUpdate, gbc_btnUpdate);
		
		JLabel lblContent = new JLabel("Content");
		GridBagConstraints gbc_lblContent = new GridBagConstraints();
		gbc_lblContent.insets = new Insets(0, 0, 5, 5);
		gbc_lblContent.gridx = 0;
		gbc_lblContent.gridy = 4;
		frmSn.getContentPane().add(lblContent, gbc_lblContent);
		
		JLabel lblReeivedMessages = new JLabel("Received messages");
		GridBagConstraints gbc_lblReeivedMessages = new GridBagConstraints();
		gbc_lblReeivedMessages.insets = new Insets(0, 0, 5, 5);
		gbc_lblReeivedMessages.gridx = 3;
		gbc_lblReeivedMessages.gridy = 4;
		frmSn.getContentPane().add(lblReeivedMessages, gbc_lblReeivedMessages);
		
		textContent = new JTextField();
		GridBagConstraints gbc_textContent = new GridBagConstraints();
		gbc_textContent.gridwidth = 2;
		gbc_textContent.insets = new Insets(0, 0, 5, 5);
		gbc_textContent.fill = GridBagConstraints.HORIZONTAL;
		gbc_textContent.gridx = 0;
		gbc_textContent.gridy = 5;
		frmSn.getContentPane().add(textContent, gbc_textContent);
		textContent.setColumns(10);
		textContent.setText("Hello How Are You?");
		GridBagConstraints gbc_textReceived = new GridBagConstraints();
		gbc_textReceived.gridheight = 2;
		gbc_textReceived.insets = new Insets(0, 0, 5, 5);
		gbc_textReceived.fill = GridBagConstraints.BOTH;
		gbc_textReceived.gridx = 3;
		gbc_textReceived.gridy = 5;
		//frmSn.getContentPane().add(textReceived, gbc_textReceived);
		JScrollPane receivedScroller = new JScrollPane(); 
		frmSn.getContentPane().add(receivedScroller, gbc_textReceived);
		
		listReceived = new JList<>();
		listReceived.setToolTipText("double click to reuse data");
		listReceived.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		receivedScroller.setViewportView(listReceived);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		frmSn.getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSend = new JButton("Send");
		panel.add(btnSend);
		
		btnCancel = new JButton("Clear");
		panel.add(btnCancel);
		
		
		textContent.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt){
				switch(evt.getKeyCode()){
				case KeyEvent.VK_ENTER:		// ENTER: press send-button
					btnSend.doClick();
				case KeyEvent.VK_ESCAPE:	// ESC: empty input field
					textContent.setText("");
					break;
				}
			}
		});	
		// ListModels for the lists containing sent/received messages
		listSentModel = new DefaultListModel<>();
		listSent.setModel(listSentModel);
		listReceivedModel = new DefaultListModel<>();
		listReceived.setModel(listReceivedModel);
	}

}
