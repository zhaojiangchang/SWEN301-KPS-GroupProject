package main.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.logic.Location;
import main.logic.Route;
import java.awt.SystemColor;

/**
 * The MailDeliveryPane class is a JPanel which is added onto the JFrame
 * once the Mail Delivery button is clicked. MailDeliveryPane class is
 * responsible for letting the user enter the information of the new delivery
 * and pass the information into the system.
 *
 * @author zhaojiang chang
 *
 */
public class MailDeliveryPane extends Panel {

	private static final long serialVersionUID = 1L;

	// buttons on the panel
	private JButton reset;
	private JButton add;

	/**
	 * Create the MailDeliveryPane by passing the gui it is on
	 *
	 * @param gui the gui the MailDeliveryPane is on
	 */
	public MailDeliveryPane(GUI gui) {
		super(gui);
		setBackground(SystemColor.window);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelComboOrigin = new JLabel("Origin", SwingConstants.CENTER);
		comboBoxOrigin = new JComboBox(getOrigins());
		//comboBoxOrigin.setEditable(true);
		comboBoxOrigin.setSelectedItem(null);
		comboBoxListenner(comboBoxOrigin, "origin");

		JLabel labelComboDestination = new JLabel("Destination", SwingConstants.CENTER);
		comboBoxDestination = new JComboBox(getDestinations());
		//comboBoxDestination.setEnabled(true);
		comboBoxDestination.setSelectedItem(null);

		comboBoxListenner(comboBoxDestination, "destination");

		JLabel labelWeight= new JLabel("Weight", SwingConstants.CENTER);
		textWeight = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textWeight);

		JLabel labelVolume= new JLabel("Volume", SwingConstants.CENTER);
		textVolume = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textVolume);

		JLabel labelPriority= new JLabel("Priority", SwingConstants.CENTER);
		comboBoxPriority = new JComboBox(priorityList);
		comboBoxPriority.setSelectedIndex(1);
		comboBoxListenner(comboBoxPriority, "priority");
		JTextField textTime = new JTextField(20);
		textTime.setEnabled(false);
		textTime.setEditable(false);
		textTime.setBackground(SystemColor.controlHighlight);
		textTime.disable();

		reset = new JButton("Reset");
		add = new JButton("Add");
		add(labelComboOrigin);
		add(comboBoxOrigin);
		add(labelComboDestination);
		add(comboBoxDestination);
		add(labelWeight);
		add(textWeight);
		add(labelVolume);
		add(textVolume);
		add(labelPriority);
		add(comboBoxPriority);
		add(reset);
		add(add);
		add(textTime);
	}

	@Override
	protected void addListenner() {
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == add){
					if(origin.equals("")||destination.equals("")||((Number)textWeight.getValue()).doubleValue()==0.0||
							((Number)textVolume.getValue()).doubleValue()==0.0||priority.equals("")){

						JOptionPane.showMessageDialog(null, "Some data missing!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
					else{
						int g = JOptionPane.YES_NO_OPTION;
						int response = JOptionPane.showConfirmDialog(null, "Add new mail delivery?", "Add new Mail Delivery", g);
						if(response == JOptionPane.YES_OPTION){
							addBusinessEvent("mailDelivery");
						}
					}
				}
			}
		});
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == reset){
					int g = JOptionPane.YES_NO_OPTION;
					int response = JOptionPane.showConfirmDialog(null, "Do you want to reset Values?", "Reset values", g);
					if(response == JOptionPane.YES_OPTION){
						comboBoxOrigin.setSelectedItem(null);
						comboBoxDestination.setSelectedItem(null);
						init();
					}
				}
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent e ) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == textWeight) {
			amount = ((Number)textWeight.getValue()).doubleValue();
		}  else if (source == textVolume) {
			amount = ((Number)textVolume.getValue()).doubleValue();
		}
	}

	// the following methods are the getters
	public static double getMDTextWeight() {
		return ((Number)textWeight.getValue()).doubleValue();
	}

	public static double getMDTextVolume() {
		return ((Number)textVolume.getValue()).doubleValue();
	}
}
