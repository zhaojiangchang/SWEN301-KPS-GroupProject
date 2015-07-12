package main.gui;

import javax.swing.JFormattedTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * The TransportCostUpdatePane class is a JPanel which is added onto the JFrame
 * once the Transport Cost Update button is clicked. TransportCostUpdatePane class is
 * responsible for letting the user enter the information of the new cost
 * and pass the information into the system.
 *
 * @author zhaojiang chang
 *
 */
public class TransportCostUpdatePane extends Panel{

	private static final long serialVersionUID = 1L;
	private JButton reset;
	private JButton update;

	/**
	 * Create the TransportCostUpdatePane by passing the gui it is on
	 *
	 * @param gui the gui the TransportCostUpdatePane is on
	 */
	public TransportCostUpdatePane(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelComboRoute = new JLabel("Select Route", SwingConstants.CENTER);
		comboBoxRouteList();

		JLabel labelNewPricePerGram= new JLabel("New price per gram", SwingConstants.CENTER);
		textTPNewCostPerGram = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textTPNewCostPerGram);

		JLabel labelNewPricePerCB= new JLabel("New price per cubic centimeter", SwingConstants.CENTER);
		textTPNewCostPerCubic = new JFormattedTextField(amountFormat);
		formatToDobuleJTextField(textTPNewCostPerCubic);

		reset = new JButton("Reset");
		update = new JButton("Update");
		
		add(labelComboRoute);
		add(comboBoxRoute);
		add(labelNewPricePerGram);
		add(textTPNewCostPerGram);
		add(labelNewPricePerCB);
		add(textTPNewCostPerCubic);
		add(reset);
		add(update);

	}
	@Override
	protected void addListenner() {
		update.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == update){
					if(selectedRoute==null||((Number)textTPNewCostPerGram.getValue()).doubleValue()==0.0||
							((Number)textTPNewCostPerCubic.getValue()).doubleValue()==0.0){
						JOptionPane.showMessageDialog(null, "Some data missing!", "Warning",
								JOptionPane.WARNING_MESSAGE);					}
					else{
						int g = JOptionPane.YES_NO_OPTION;
						int response = JOptionPane.showConfirmDialog(null, "Add new mail delivery?", "Add new Mail Delivery", g);
						if(response == JOptionPane.YES_OPTION){
							addBusinessEvent("transportCostUpdate");
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
		if (source == textCustomerNewPricePerGram) {
			amount = ((Number)textCustomerNewPricePerGram.getValue()).doubleValue();
		}  else if (source == textCustomerNewPricePerCubic) {
			amount = ((Number)textCustomerNewPricePerCubic.getValue()).doubleValue();
		}
	}

	// the following methods are the getters
	public static double getCPUTextCustomerNewPricePerGram() {
		return ((Number)textCustomerNewPricePerGram.getValue()).doubleValue();
	}

	public static double getCPUTextNewPricePerCB() {
		return ((Number)textCustomerNewPricePerCubic.getValue()).doubleValue();
	}
}
