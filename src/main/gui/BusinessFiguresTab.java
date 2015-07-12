package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The BusinessFiguresTab class is a JPanel which is added
 * on the button panel to show users the business figures.
 * BusinessFiguresTab class is responsible for directly
 * letting users know the key general business figures.
 *
 * @author Zhiheng Sun
 *
 */
public class BusinessFiguresTab extends Panel {

	// value labels on the panel
	private static JLabel revenue;
	private static JLabel expenditure;
	private static JLabel events;

	/**
	 * Create the BusinessFiguresTab by passing the gui it is on
	 *
	 * @param gui the gui the BusinessFiguresTab is on
	 */
	public BusinessFiguresTab(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
		this.setPreferredSize(new Dimension(gui.getWidth()*3/5-60,gui.getHeight()-250));

		gui.setBusinessFiguresTotal(this);
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelRevenue= new JLabel(" Total Revenue", SwingConstants.LEFT);
		labelRevenue.setFont(new Font("Dialog", Font.PLAIN, 14));
		revenue = new JLabel();

		JLabel labelExpend= new JLabel(" Total Expenditure", SwingConstants.LEFT);
		labelExpend.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelExpend.setBackground(Color.LIGHT_GRAY);
		expenditure = new JLabel();

		JLabel labelEvents= new JLabel(" Total Number of Events", SwingConstants.LEFT);
		labelEvents.setFont(new Font("Dialog", Font.PLAIN, 14));
		events = new JLabel();

		// format value labels shown business figures
		revenue.setText(" 0.0");
		revenue.setPreferredSize(new Dimension(100, 50));
		revenue.setFont(new Font("Dialog", Font.PLAIN, 14));

		expenditure.setText(" 0.0");
		expenditure.setPreferredSize(new Dimension(100, 50));
		expenditure.setFont(new Font("Dialog", Font.PLAIN, 14));

		events.setText(" 0");
		events.setPreferredSize(new Dimension(100, 50));
		events.setFont(new Font("Dialog", Font.PLAIN, 14));
		add(labelRevenue);
		add(revenue);
		add(labelExpend);
		add(expenditure);
		add(labelEvents);
		add(events);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {}

	@Override
	protected void addListenner() {}

	// controller calls to set the updated revenue
	public void setRevenue(double r){
		revenue.setText("" + r);
	}

	// controller calls to set the updated expenditure
	public void setExpend(double e){
		expenditure.setText("" + e);
	}

	// controller calls to set the updated events
	public void setEvents(int e){
		events.setText("" + e);
	}
}