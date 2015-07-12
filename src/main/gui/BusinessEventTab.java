package main.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The BusinessEventTab class is a JPanel which is added onto the JFrame
 * once Business Event Tab is clicked. BusinessEventTab class is
 * responsible for letting the manager choose the event he wants to see.
 *
 * @author zhaojiang chang & zhiheng sun
 *
 */
public class BusinessEventTab extends Panel {
	
	/**
	 * Create the BusinessEventTab by passing the gui it is on
	 *
	 * @param gui the gui the BusinessEventTab is on
	 */
	public BusinessEventTab(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);
		addLabels();
	}

	/**
	 * add the event labels onto the panel
	 */
	protected void addLabels() {
		for (int i = 0; i < currentEvent.size(); i++) {
			JLabel value = new JLabel(currentEvent.get(i), SwingConstants.LEFT);
			value.setFont(new Font("Dialog", Font.PLAIN, 14));
			add(value);
		}
	}

	@Override
	protected void addListenner() {}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {}
}