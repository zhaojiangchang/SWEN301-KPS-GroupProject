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
 * The BusinessFiguresRoutePane class is a JPanel which is added
 * onto the JFrame once the Route Data button is clicked.
 * BusinessFiguresRoutePane class is responsible for letting users
 * choose a route and displaying the corresponding business
 * figures.
 *
 * @author Zhiheng Sun
 *
 */
public class BusinessFiguresRoutePane extends Panel {

	// value labels on the panel
	private static JLabel revenue;
	private static JLabel expenditure;
	private static JLabel events;
	private static JLabel isCritical;

	/**
	 * Create the BusinessFiguresRoutePane by passing the gui it is on
	 *
	 * @param gui the gui the BusinessFiguresRoutePane is on
	 */
	public BusinessFiguresRoutePane(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
		gui.setBusinessFiguresRoutePane(this);

	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		// create labels for the business figures
		isBusinessFiguresRoute = true;
		JLabel labelComboRoute = new JLabel("Select Route", SwingConstants.CENTER);
		comboBoxRouteList();

		JLabel labelRevenue= new JLabel(" Revenue", SwingConstants.LEFT);
		revenue = new JLabel();

		JLabel labelExpend= new JLabel(" Expenditure", SwingConstants.LEFT);
		expenditure = new JLabel();

		JLabel labelEvents= new JLabel(" Number of Events", SwingConstants.LEFT);
		events = new JLabel();

		JLabel labelIsCritical = new JLabel(" Is Critical", SwingConstants.LEFT);
		isCritical = new JLabel();

		// format value labels shown business figures
		revenue.setText(" 0.0");
		revenue.setPreferredSize(new Dimension(250, 50));
		revenue.setFont(new Font("Arial", Font.PLAIN, 15));
		revenue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		expenditure.setText(" 0.0");
		expenditure.setPreferredSize(new Dimension(250, 50));
		expenditure.setFont(new Font("Arial", Font.PLAIN, 15));
		expenditure.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		events.setText(" 0");
		events.setPreferredSize(new Dimension(250, 50));
		events.setFont(new Font("Arial", Font.PLAIN, 15));
		events.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		isCritical.setText(" False");
		isCritical.setPreferredSize(new Dimension(250, 50));
		isCritical.setFont(new Font("Arial", Font.PLAIN, 15));
		isCritical.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		// add the labels onto the panel
		add(labelComboRoute);
		add(comboBoxRoute);
		add(labelRevenue);
		add(revenue);
		add(labelExpend);
		add(expenditure);
		add(labelEvents);
		add(events);
		add(labelIsCritical);
		add(isCritical);
	}
	
	@Override
	protected void addListenner() {}

	@Override
	public void propertyChange(PropertyChangeEvent e ) {}

	// controller calls to set the updated revenue
	public void setRevenue(double r){
		revenue.setText("" + r);
	}

	// controller calls to set the updated expenditure
	public void setExpend(double e){
		expenditure.setText("" + e);
	}

	// controller calls to set the updated events
	public void setEvents(double e){
		events.setText("" + e);
	}

	// controller calls to set the updated events
	public void setIsCritical(boolean isCritical){
		this.isCritical.setText("" + isCritical);
	}
}
