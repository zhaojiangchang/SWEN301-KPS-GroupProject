package main.gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The RoutesTab class is a JPanel which is added onto the JFrame
 * once Routes Tab is clicked. RoutesTab class is responsible for 
 * letting the user choose the route he wants to see.
 *
 * @author zhaojiang chang
 *
 */
public class RoutesTab extends Panel implements MouseListener{

	// value labels on the panel
	private static JLabel routes;
	private static JLabel expenditure;
	private static JLabel events;

	/**
	 * Create the RoutesTab by passing the gui it is on
	 *
	 * @param gui the gui the RoutesTab is on
	 */
	public RoutesTab(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
		this.setPreferredSize(new Dimension(gui.getWidth()*3/5-60,gui.getHeight()-250));
		gui.setRouteTab(this);
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelRevenue= new JLabel("Route List", SwingConstants.LEFT);
		labelRevenue.setFont(new Font("Dialog", Font.PLAIN, 14));
		routes = new JLabel();

		add(labelRevenue);
		addLabels();
	}
	
	/**
	 * add the route labels onto the panel
	 */
	protected void addLabels() {
		for (int i = 0; i < routeList().size(); i++) {
			JLabel value = new JLabel(i+" : "+routeList().get(i), SwingConstants.LEFT);
			value.setFont(new Font("Dialog", Font.PLAIN, 14));
			add(value);
		}
	}
	protected void removeLabels(){
		Component[] comps = this.getComponents();
		for( int i = 0; i<comps.length; i++){
			remove(comps[i]);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e ) {}

	@Override
	protected void addListenner() {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}