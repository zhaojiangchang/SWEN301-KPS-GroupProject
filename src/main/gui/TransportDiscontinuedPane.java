package main.gui;

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
 * The TransportDiscontinuedPane class is a JPanel which is added onto the JFrame
 * once the Transport Discontinued button is clicked. TransportDiscontinuedPane class is
 * responsible for letting the user select the transport they want to remove
 * and pass the information into the system.
 *
 * @author zhaojiang chang
 *
 */
public class TransportDiscontinuedPane extends Panel{

	private JButton delete;

	/**
	 * Create the TransportDiscontinuedPane by passing the gui it is on
	 *
	 * @param gui the gui the TransportDiscontinuedPane is on
	 */
	public TransportDiscontinuedPane(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelComboRoute = new JLabel("Select Route", SwingConstants.CENTER);
		comboBoxRouteList();
		delete = new JButton("Delete");
		
		add(labelComboRoute);
		add(comboBoxRoute);
		add(delete);
	}

	@Override
	protected void addListenner() {
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == delete){
					if(selectedRoute==null){
						JOptionPane.showMessageDialog(null, "Please select a route", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
					else{
						int g = JOptionPane.YES_NO_OPTION;
						int response = JOptionPane.showConfirmDialog(null, "Do you want to discontinue a transport?", "Discontinue Transport", g);
						if(response == JOptionPane.YES_OPTION){
							addBusinessEvent("transportDiscontinued");
							refreshComboBoxRouteList();
						}
					}

				}
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
}
