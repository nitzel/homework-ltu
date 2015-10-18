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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class OverseerGUI {
	public static final String CMD_SET_TARGET = "settarget";
	public static final String CMD_PAUSE_ATTACK = "pauseattack";
	public static final String CMD_SET_MINIONS = "setminions";
	public static final String CMD_SET_INTERVAL = "setinterval";

	private JFrame frmWhateversOverseer;
	private JTextField textTarget;
	private JButton btnSetTarget;
	private JSlider slider;
	private JButton btnSetNumber;
	private JSpinner spinCurrent;
	private JSpinner spinNew;
	private JSpinner spinDiff;
	private JLabel lblSpawnernum;
	private JSpinner spinInterval;
	private JButton btnSetInterval;
	private JButton btnHalt;

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

	
	
	public void addWindowListener(WindowListener w){
		frmWhateversOverseer.addWindowListener(w);
	}
	
	/**
	 * Add one actionListener to the 2 buttons and sets their ActionCommand to update/send/cancel
	 * @param a the ActionListener for all 2 buttons
	 */
	public void addButtonActionListener(ActionListener a){
		btnSetTarget.addActionListener(a);
		btnSetTarget.setActionCommand(CMD_SET_TARGET);

		btnHalt.addActionListener(a);
		btnHalt.setActionCommand(CMD_PAUSE_ATTACK);
		
		btnSetNumber.addActionListener(a);
		btnSetNumber.setActionCommand(CMD_SET_MINIONS);
		
		btnSetInterval.addActionListener(a);
		btnSetInterval.setActionCommand(CMD_SET_INTERVAL);
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

	/**
	 * sets the value of the spinner spinCurrent
	 */
	public void setCurrent(int n){
		spinCurrent.setValue(n);
	}
	/**
	 * @return the value within the interval-spinner spinNew/desired
	 */
	public int getNew(){
		return (Integer)spinNew.getValue();
	}
	/**
	 * @return the value within the interval-spinner spinDiff
	 */
	public int getDiff(){
		return (Integer)spinDiff.getValue();
	}
	/**
	 * @return the value within the interval-spinner spinInterval
	 */
	public int getInterval(){
		return (Integer)spinInterval.getValue();
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
		frmWhateversOverseer.setBounds(100, 100, 521, 178);
		frmWhateversOverseer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 77, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_textTarget.gridwidth = 6;
		gbc_textTarget.insets = new Insets(0, 0, 5, 5);
		gbc_textTarget.fill = GridBagConstraints.HORIZONTAL;
		gbc_textTarget.gridx = 1;
		gbc_textTarget.gridy = 0;
		frmWhateversOverseer.getContentPane().add(textTarget, gbc_textTarget);
		textTarget.setColumns(10);
		
		btnSetTarget = new JButton("BANANA!");
		btnSetTarget.setToolTipText("Click to set target for all minions and attack");
		GridBagConstraints gbc_btnSetTarget = new GridBagConstraints();
		gbc_btnSetTarget.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSetTarget.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetTarget.gridx = 7;
		gbc_btnSetTarget.gridy = 0;
		frmWhateversOverseer.getContentPane().add(btnSetTarget, gbc_btnSetTarget);
		
		btnHalt = new JButton("HALT");
		btnHalt.setToolTipText("Click to pause attack");
		GridBagConstraints gbc_btnHalt = new GridBagConstraints();
		gbc_btnHalt.insets = new Insets(0, 0, 5, 0);
		gbc_btnHalt.gridx = 8;
		gbc_btnHalt.gridy = 0;
		frmWhateversOverseer.getContentPane().add(btnHalt, gbc_btnHalt);
		
		JLabel lblSpawner = new JLabel("Spawner:");
		GridBagConstraints gbc_lblSpawner = new GridBagConstraints();
		gbc_lblSpawner.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpawner.gridx = 0;
		gbc_lblSpawner.gridy = 1;
		frmWhateversOverseer.getContentPane().add(lblSpawner, gbc_lblSpawner);
		
		lblSpawnernum = new JLabel("SpawnerNum");
		GridBagConstraints gbc_lblSpawnernum = new GridBagConstraints();
		gbc_lblSpawnernum.anchor = GridBagConstraints.EAST;
		gbc_lblSpawnernum.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpawnernum.gridx = 1;
		gbc_lblSpawnernum.gridy = 1;
		frmWhateversOverseer.getContentPane().add(lblSpawnernum, gbc_lblSpawnernum);
		
		JLabel lblAttackInterval = new JLabel("Attack interval (ms):");
		GridBagConstraints gbc_lblAttackInterval = new GridBagConstraints();
		gbc_lblAttackInterval.anchor = GridBagConstraints.WEST;
		gbc_lblAttackInterval.gridwidth = 3;
		gbc_lblAttackInterval.insets = new Insets(0, 0, 5, 5);
		gbc_lblAttackInterval.gridx = 3;
		gbc_lblAttackInterval.gridy = 1;
		frmWhateversOverseer.getContentPane().add(lblAttackInterval, gbc_lblAttackInterval);
		
		spinInterval = new JSpinner();
		spinInterval.setToolTipText("Interval in milliseconds");
		spinInterval.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinInterval = new GridBagConstraints();
		gbc_spinInterval.fill = GridBagConstraints.BOTH;
		gbc_spinInterval.insets = new Insets(0, 0, 5, 5);
		gbc_spinInterval.gridx = 6;
		gbc_spinInterval.gridy = 1;
		frmWhateversOverseer.getContentPane().add(spinInterval, gbc_spinInterval);
		
		btnSetInterval = new JButton("set");
		btnSetInterval.setToolTipText("Set the interval between attacks of the minions");
		GridBagConstraints gbc_btnSetInterval = new GridBagConstraints();
		gbc_btnSetInterval.gridwidth = 2;
		gbc_btnSetInterval.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSetInterval.insets = new Insets(0, 0, 5, 0);
		gbc_btnSetInterval.gridx = 7;
		gbc_btnSetInterval.gridy = 1;
		frmWhateversOverseer.getContentPane().add(btnSetInterval, gbc_btnSetInterval);
		
		JLabel lblMinions = new JLabel("Minions");
		GridBagConstraints gbc_lblMinions = new GridBagConstraints();
		gbc_lblMinions.anchor = GridBagConstraints.WEST;
		gbc_lblMinions.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinions.gridx = 0;
		gbc_lblMinions.gridy = 2;
		frmWhateversOverseer.getContentPane().add(lblMinions, gbc_lblMinions);
		
		slider = new JSlider();
		slider.setMaximum(10000);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(false);
		slider.setMajorTickSpacing(1000);
		slider.setMinorTickSpacing(250);
		slider.setValue(0);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 8;
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 2;
		frmWhateversOverseer.getContentPane().add(slider, gbc_slider);
		
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Integer num = slider.getValue();
				
				spinNew.setValue(num);
				Integer cur = (Integer)spinCurrent.getValue();
				spinDiff.setValue(num-cur);
			}
		});
		
		JLabel lblCurrent = new JLabel("Current:");
		GridBagConstraints gbc_lblCurrent = new GridBagConstraints();
		gbc_lblCurrent.anchor = GridBagConstraints.WEST;
		gbc_lblCurrent.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrent.gridx = 0;
		gbc_lblCurrent.gridy = 3;
		frmWhateversOverseer.getContentPane().add(lblCurrent, gbc_lblCurrent);
		
		spinCurrent = new JSpinner();
		spinCurrent.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinCurrent.setToolTipText("The amount of currently running minions");
		spinCurrent.setEnabled(false);
		GridBagConstraints gbc_spinCurrent = new GridBagConstraints();
		gbc_spinCurrent.fill = GridBagConstraints.BOTH;
		gbc_spinCurrent.insets = new Insets(0, 0, 0, 5);
		gbc_spinCurrent.gridx = 1;
		gbc_spinCurrent.gridy = 3;
		frmWhateversOverseer.getContentPane().add(spinCurrent, gbc_spinCurrent);
		
				spinCurrent.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						slider.setValue(slider.getValue()); // just update the other fields' content
					}
				});
		
		JLabel lblNewLabel = new JLabel("Desired:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		frmWhateversOverseer.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		spinNew = new JSpinner();
		spinNew.setToolTipText("Selected number of minions on the slider");
		GridBagConstraints gbc_spinNew = new GridBagConstraints();
		gbc_spinNew.fill = GridBagConstraints.BOTH;
		gbc_spinNew.insets = new Insets(0, 0, 0, 5);
		gbc_spinNew.gridx = 4;
		gbc_spinNew.gridy = 3;
		frmWhateversOverseer.getContentPane().add(spinNew, gbc_spinNew);
		
		spinNew.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int num = ((Integer)spinNew.getValue());
				Integer sliderNum = slider.getValue();
				if(num != sliderNum){
					slider.setValue(num);
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Diff:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 3;
		frmWhateversOverseer.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		spinDiff = new JSpinner();
		spinDiff.setToolTipText("change of amount of minions if you press set");
		GridBagConstraints gbc_spinDiff = new GridBagConstraints();
		gbc_spinDiff.fill = GridBagConstraints.BOTH;
		gbc_spinDiff.insets = new Insets(0, 0, 0, 5);
		gbc_spinDiff.gridx = 6;
		gbc_spinDiff.gridy = 3;
		frmWhateversOverseer.getContentPane().add(spinDiff, gbc_spinDiff);
		
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
		
		btnSetNumber = new JButton("set");
		btnSetNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSetNumber.setToolTipText("sets the new amount of minion to desired");
		GridBagConstraints gbc_btnSetNumber = new GridBagConstraints();
		gbc_btnSetNumber.gridwidth = 2;
		gbc_btnSetNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSetNumber.gridx = 7;
		gbc_btnSetNumber.gridy = 3;
		frmWhateversOverseer.getContentPane().add(btnSetNumber, gbc_btnSetNumber);
		
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
	
	/**
	 * Closes the window
	 */
	public void close(){
		frmWhateversOverseer.dispatchEvent(new WindowEvent(frmWhateversOverseer, WindowEvent.WINDOW_CLOSING));
	}

}
