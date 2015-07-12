package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The BusinessFiguresLocationPane class is a JPanel which is added
 * onto the JFrame once the Location Data button is clicked.
 * BusinessFiguresLocationPane class is responsible for letting users
 * choose a location and displaying the corresponding business
 * figures.
 *
 * @author Zhiheng Sun
 *
 */
public class BusinessFiguresLocationPane extends Panel {

	// value labels on the panel
	private static JLabel totalVol;
	private static JLabel totalWeight;
	private static JLabel totalNumItems;

	/**
	 * Create the BusinessFiguresLocationPane by passing the gui it is on
	 *
	 * @param gui the gui the BusinessFiguresLocationPane is on
	 */
	public BusinessFiguresLocationPane(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
		gui.setBusinessFiguresLocationPane(this);
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		// create labels for the business figures
		JLabel labelComboLocation = new JLabel("Select Location", SwingConstants.CENTER);
		comboBoxLocation = new JComboBox(getLocations());
		comboBoxLocation.setSelectedItem(null);
		comboBoxListenner(comboBoxLocation, "location");

		JLabel labelVolume= new JLabel(" Total Volume", SwingConstants.LEFT);
		totalVol = new JLabel();

		JLabel labelWeight = new JLabel(" Total Weight", SwingConstants.LEFT);
		totalWeight = new JLabel();

		JLabel labelItems = new JLabel(" Number of Items", SwingConstants.LEFT);
		totalNumItems = new JLabel();

		// format value labels shown business figures
		totalVol.setText(" 0.0");
		totalVol.setPreferredSize(new Dimension(250, 50));
		totalVol.setFont(new Font("Arial", Font.PLAIN, 15));
		totalVol.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		totalWeight.setText(" 0.0");
		totalWeight.setPreferredSize(new Dimension(250, 50));
		totalWeight.setFont(new Font("Arial", Font.PLAIN, 15));
		totalWeight.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		totalNumItems.setText(" 0");
		totalNumItems.setPreferredSize(new Dimension(250, 50));
		totalNumItems.setFont(new Font("Arial", Font.PLAIN, 15));
		totalNumItems.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// add the labels onto the panel
		add(labelComboLocation);
		add(comboBoxLocation);
		add(labelVolume);
		add(totalVol);
		add(labelWeight);
		add(totalWeight);
		add(labelItems);
		add(totalNumItems);
	}
	
	@Override
	protected void addListenner() {}

	@Override
	public void propertyChange(PropertyChangeEvent e ) {}

	// controller calls to set the updated volume
	public void setTotalVol(double vol){
		totalVol.setText("" + vol);
	}

	// controller calls to set the updated weight
	public void setTotalWeight(double wei){
		totalWeight.setText("" + wei);
	}

	// controller calls to set the updated number of items
	public void setNumItems(double items){
		totalNumItems.setText("" + items);
	}
}
