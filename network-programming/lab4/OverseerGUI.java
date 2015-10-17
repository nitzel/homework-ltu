package lab4;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OverseerGUI {
	public static final String CMD_SET_TARGET = "settarget";
	public static final String CMD_SET_MINIONS = "setminions";

	private JFrame frmWhateversOverseer;
	private JTextField textTarget;
	private JButton btnSetTarget;
	private JSlider slider;
	private JButton btnSetNumber;
	private JSpinner spinCurrent;
	private JSpinner spinNew;
	private JSpinner spinDiff;
	private JLabel lblSpawnernum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OverseerGUI window = new OverseerGUI();
					window.frmWhateversOverseer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Add one actionListener to the 2 buttons and sets their ActionCommand to update/send/cancel
	 * @param a the ActionListener for all 2 buttons
	 */
	public void addButtonActionListener(ActionListener a){
		btnSetTarget.addActionListener(a);
		btnSetTarget.setActionCommand(CMD_SET_TARGET);
		
		btnSetNumber.addActionListener(a);
		btnSetNumber.setActionCommand(CMD_SET_MINIONS);
	}
	/**
	 * @return the text within textTarget
	 */
	public String getTarget(){
		return textTarget.getText();
	}
	/**
	 * @param content will be the new text within the textfield textTarget
	 */
	public void setTarget(String content){
		textTarget.setText(content);
	}
	public void setSpawnernum(String content){
		lblSpawnernum.setText(content);
	}
	
	public void setCurrent(int n){
		spinCurrent.setValue(n);
	}
	public int getNew(){
		return (Integer)spinNew.getValue();
	}
	public int getDiff(){
		return (Integer)spinDiff.getValue();
	}
	
	
	
	
	/**
	 * Create the application.
	 */
	public OverseerGUI() {
		initialize();
		frmWhateversOverseer.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWhateversOverseer = new JFrame();
		frmWhateversOverseer.setTitle("Whatevers overseer");
		frmWhateversOverseer.setBounds(100, 100, 457, 166);
		frmWhateversOverseer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmWhateversOverseer.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTarget = new JLabel("Target");
		GridBagConstraints gbc_lblTarget = new GridBagConstraints();
		gbc_lblTarget.anchor = GridBagConstraints.WEST;
		gbc_lblTarget.insets = new Insets(0, 0, 5, 5);
		gbc_lblTarget.gridx = 0;
		gbc_lblTarget.gridy = 0;
		frmWhateversOverseer.getContentPane().add(lblTarget, gbc_lblTarget);
		
		textTarget = new JTextField();
		textTarget.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					btnSetTarget.doClick();
				}
			}
		});
		textTarget.setToolTipText("url:port");
		textTarget.setText("localhost:2223");
		GridBagConstraints gbc_textTarget = new GridBagConstraints();
		gbc_textTarget.gridwidth = 5;
		gbc_textTarget.insets = new Insets(0, 0, 5, 5);
		gbc_textTarget.fill = GridBagConstraints.HORIZONTAL;
		gbc_textTarget.gridx = 1;
		gbc_textTarget.gridy = 0;
		frmWhateversOverseer.getContentPane().add(textTarget, gbc_textTarget);
		textTarget.setColumns(10);
		
		btnSetTarget = new JButton("set");
		btnSetTarget.setToolTipText("sets the target for all minions");
		GridBagConstraints gbc_btnSetTarget = new GridBagConstraints();
		gbc_btnSetTarget.insets = new Insets(0, 0, 5, 0);
		gbc_btnSetTarget.gridx = 6;
		gbc_btnSetTarget.gridy = 0;
		frmWhateversOverseer.getContentPane().add(btnSetTarget, gbc_btnSetTarget);
		
		JLabel lblMinions = new JLabel("Minions");
		GridBagConstraints gbc_lblMinions = new GridBagConstraints();
		gbc_lblMinions.anchor = GridBagConstraints.WEST;
		gbc_lblMinions.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinions.gridx = 0;
		gbc_lblMinions.gridy = 1;
		frmWhateversOverseer.getContentPane().add(lblMinions, gbc_lblMinions);
		
		slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(false);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(2);
		slider.setValue(0);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 6;
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 1;
		frmWhateversOverseer.getContentPane().add(slider, gbc_slider);
		
		JLabel lblCurrent = new JLabel("Current:");
		GridBagConstraints gbc_lblCurrent = new GridBagConstraints();
		gbc_lblCurrent.anchor = GridBagConstraints.EAST;
		gbc_lblCurrent.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrent.gridx = 0;
		gbc_lblCurrent.gridy = 2;
		frmWhateversOverseer.getContentPane().add(lblCurrent, gbc_lblCurrent);
		
		spinCurrent = new JSpinner();
		spinCurrent.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinCurrent.setToolTipText("The amount of currently running minions");
		spinCurrent.setEnabled(false);
		spinCurrent.setValue(1000); //TODO remove
		GridBagConstraints gbc_spinCurrent = new GridBagConstraints();
		gbc_spinCurrent.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinCurrent.insets = new Insets(0, 0, 5, 5);
		gbc_spinCurrent.gridx = 1;
		gbc_spinCurrent.gridy = 2;
		frmWhateversOverseer.getContentPane().add(spinCurrent, gbc_spinCurrent);
		
		JLabel lblNewLabel = new JLabel("New:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 2;
		frmWhateversOverseer.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		spinNew = new JSpinner();
		spinNew.setToolTipText("Selected number of minions on the slider");
		GridBagConstraints gbc_spinNew = new GridBagConstraints();
		gbc_spinNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinNew.insets = new Insets(0, 0, 5, 5);
		gbc_spinNew.gridx = 3;
		gbc_spinNew.gridy = 2;
		frmWhateversOverseer.getContentPane().add(spinNew, gbc_spinNew);
		
		JLabel lblNewLabel_1 = new JLabel("Diff:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 2;
		frmWhateversOverseer.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		spinDiff = new JSpinner();
		spinDiff.setToolTipText("change of amount of minions if you press set");
		GridBagConstraints gbc_spinDiff = new GridBagConstraints();
		gbc_spinDiff.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinDiff.insets = new Insets(0, 0, 5, 5);
		gbc_spinDiff.gridx = 5;
		gbc_spinDiff.gridy = 2;
		frmWhateversOverseer.getContentPane().add(spinDiff, gbc_spinDiff);
		
		btnSetNumber = new JButton("set");
		btnSetNumber.setToolTipText("adds/removes DIFF amount of minions");
		GridBagConstraints gbc_btnSetNumber = new GridBagConstraints();
		gbc_btnSetNumber.insets = new Insets(0, 0, 5, 0);
		gbc_btnSetNumber.gridx = 6;
		gbc_btnSetNumber.gridy = 2;
		frmWhateversOverseer.getContentPane().add(btnSetNumber, gbc_btnSetNumber);
		
		JLabel lblSpawner = new JLabel("Spawner:");
		GridBagConstraints gbc_lblSpawner = new GridBagConstraints();
		gbc_lblSpawner.insets = new Insets(0, 0, 0, 5);
		gbc_lblSpawner.gridx = 0;
		gbc_lblSpawner.gridy = 3;
		frmWhateversOverseer.getContentPane().add(lblSpawner, gbc_lblSpawner);
		
		lblSpawnernum = new JLabel("SpawnerNum");
		GridBagConstraints gbc_lblSpawnernum = new GridBagConstraints();
		gbc_lblSpawnernum.anchor = GridBagConstraints.EAST;
		gbc_lblSpawnernum.insets = new Insets(0, 0, 0, 5);
		gbc_lblSpawnernum.gridx = 1;
		gbc_lblSpawnernum.gridy = 3;
		frmWhateversOverseer.getContentPane().add(lblSpawnernum, gbc_lblSpawnernum);
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Integer num = slider.getValue();
				
				spinNew.setValue(num);
				Integer cur = (Integer)spinCurrent.getValue();
				spinDiff.setValue(num-cur);
			}
		});
		
		spinNew.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int num = ((Integer)spinNew.getValue());
				Integer sliderNum = slider.getValue();
				if(num != sliderNum){
					slider.setValue(num);
				}
			}
		});

		spinDiff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int diff = ((Integer)spinDiff.getValue());
				int cur = ((Integer)spinCurrent.getValue());
				Integer sliderNum = slider.getValue();
				if(cur+diff != sliderNum){
					slider.setValue(cur+diff);
				}
			}
		});

		spinCurrent.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				slider.setValue(slider.getValue()); // just update the other fields' content
			}
		});
		
		KeyAdapter changeNumber = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					btnSetNumber.doClick();
				}
			}
		};
		spinNew.addKeyListener(changeNumber);
		spinDiff.addKeyListener(changeNumber);
	}

}
