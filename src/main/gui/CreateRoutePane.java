package main.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import main.logic.Location;
import main.logic.Route;

/**
 * The CreateRoutePane class is a JPanel which is added onto the JFrame
 * once the Create Route button is clicked. CreateRoutePane class is
 * responsible for letting the user enter the information of the new route
 * and pass the information into the system.
 *
 * @author zhaojiang chang
 *
 */
public class CreateRoutePane extends Panel{

	// buttons on the panel
	private int count = 0;
	private static boolean updateButtonClicked = false;
	private JButton reset;
	private JButton add;

	/**
	 * Create the CreateRoutePane by passing the gui it is on
	 *
	 * @param gui the gui the CreateRoutePane is on
	 */
	public CreateRoutePane(GUI gui) {
		super(gui);
		updateButtonClicked = false;
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);
		JLabel labelComboOrigin = new JLabel("Origin", SwingConstants.CENTER);
		comboBoxOrigin = new JComboBox(getLocations());
		comboBoxOrigin.setEditable(true);
		comboBoxOrigin.setSelectedItem(null);
		comboBoxListenner(comboBoxOrigin, "origin");

		JLabel labelComboDestination = new JLabel("Destination", SwingConstants.CENTER);
		comboBoxDestination = new JComboBox(getLocations());
		comboBoxDestination.setEditable(true);
		comboBoxDestination.setSelectedItem(null);
		comboBoxListenner(comboBoxDestination, "destination");

		JLabel labelTransportFirm= new JLabel("Transport Firm", SwingConstants.CENTER);
		comboBoxTransportFirm = new JComboBox(getTransportFirms());
		comboBoxTransportFirm.setEditable(true);
		comboBoxTransportFirm.setSelectedItem(null);
		comboBoxListenner(comboBoxTransportFirm, "transportFirm");

		JLabel labelTransportType= new JLabel("Transport Tpye", SwingConstants.CENTER);
		comboBoxTransportType = new JComboBox(getTransportTypes());
		comboBoxTransportType.setSelectedItem(null);
		comboBoxListenner(comboBoxTransportType, "transportType");

		JLabel labelTransportPricePerGram= new JLabel("Transport $/gram", SwingConstants.CENTER);
		textTPNewCostPerGram = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textTPNewCostPerGram);

		JLabel labelTransportCostPerCB= new JLabel("Transport $/volume", SwingConstants.CENTER);
		textTPNewCostPerCubic = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textTPNewCostPerCubic);

		JLabel labelCustomerPricePerGram= new JLabel("Customer $/gram", SwingConstants.CENTER);
		textCustomerNewPricePerGram = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textCustomerNewPricePerGram);

		JLabel labelCustomerCostPerCB= new JLabel("Customer $/volume", SwingConstants.CENTER);
		textCustomerNewPricePerCubic = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textCustomerNewPricePerCubic);

		JLabel labelFrequency= new JLabel("Frequency departs", SwingConstants.CENTER);
		textTPFrequency = new JFormattedTextField(amountFormat);
		formatToIntegerJTextField(textTPFrequency);

		JLabel labelDay= new JLabel("Transpot Day", SwingConstants.CENTER);
		comboBoxTransportDay = new JComboBox(TransportDateList);
		comboBoxTransportDay.setSelectedItem(null);
		comboBoxListenner(comboBoxTransportDay, "transportDay");

		reset = new JButton("Reset");
		add = new JButton("Add");
		add(labelComboOrigin);
		add(comboBoxOrigin);
		add(labelComboDestination);
		add(comboBoxDestination);
		add(labelTransportFirm);
		add(comboBoxTransportFirm);
		add(labelTransportType);
		add(comboBoxTransportType);
		add(labelTransportPricePerGram);
		add(textTPNewCostPerGram);
		add(labelTransportCostPerCB);
		add(textTPNewCostPerCubic);
		add(labelCustomerPricePerGram);
		add(textCustomerNewPricePerGram);
		add(labelCustomerCostPerCB);
		add(textCustomerNewPricePerCubic);
		add(labelFrequency);
		add(textTPFrequency);
		add(labelDay);
		add(comboBoxTransportDay);
		add(reset);
		add(add);
	}

	@Override
	protected void addListenner() {
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == add){
					if(origin.equals("")||destination.equals("")||transportFirm.equals("")||transportType.equals("")||
							transportDay.equals("")||((Number)textTPNewCostPerGram.getValue()).doubleValue()==0.0||
							((Number)textTPNewCostPerCubic.getValue()).doubleValue()==0.0||
							((Number)textCustomerNewPricePerGram.getValue()).doubleValue()==0.0||
							((Number)textCustomerNewPricePerCubic.getValue()).doubleValue()==0.0||
							((Number)textTPFrequency.getValue()).doubleValue()==0.0){
						JOptionPane.showMessageDialog(null, "Some data missing!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
					else{
						int g = JOptionPane.YES_NO_OPTION;
						int response = JOptionPane.showConfirmDialog(null, "Add new route?", "Add new route", g);
						if(response == JOptionPane.YES_OPTION){
							addBusinessEvent("createRoute");
							refreshComboBoxRouteList();						}
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
						init();
						comboBoxOrigin.setSelectedItem(null);
						comboBoxDestination.setSelectedItem(null);
						comboBoxTransportFirm.setSelectedItem(null);
						comboBoxTransportType.setSelectedItem(null);
						comboBoxTransportDay.setSelectedItem(null);
					}

				}
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == textTPNewCostPerCubic) {
			amount = ((Number)textTPNewCostPerCubic.getValue()).doubleValue();
		}
		else if (source == textTPNewCostPerGram) {
			amount = ((Number)textTPNewCostPerGram.getValue()).doubleValue();
		}
		else if (source == textTPmaxWeight) {
			amount = ((Number)textTPmaxWeight.getValue()).doubleValue();
		}
		else if (source == textTPmaxVolume) {
			amount = ((Number)textTPmaxVolume.getValue()).doubleValue();
		}
		else if (source == textTPFrequency) {
			amount = ((Number)textTPFrequency.getValue()).intValue();
		}
		else if (source == textTPDuration) {
			amount = ((Number)textTPDuration.getValue()).intValue();
		}
	}

	// the following methods are the getters
	public static double getTPCNewPricePerGram() {
		return ((Number)textTPNewCostPerGram.getValue()).doubleValue();
	}

	public static double getTPCNewCostPerCubic() {
		return ((Number)textTPNewCostPerCubic.getValue()).doubleValue();
	}
	public static boolean updateClicked(){
		if(updateButtonClicked==true){
			return true;
		}
		return false;
	}
}
