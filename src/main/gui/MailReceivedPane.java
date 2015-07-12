package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.events.MailDelivery;

/**
 * The MailReceivedPane class is a JPanel which is added onto the JFrame
 * once the Mail Received button is clicked. MailReceivedPane class is
 * responsible for letting the user confirming the mail received.
 *
 * @author zhaojiang chang
 *
 */
public class MailReceivedPane extends Panel{

	private JButton update;
	private static JLabel aveTime;

	/**
	 * Create the MailReceivedPane by passing the gui it is on
	 *
	 * @param gui the gui the MailReceivedPane is on
	 */
	public MailReceivedPane(GUI gui) {
		super(gui);
		setBounds(300, 0, gui.getWidth()*3/4-10, gui.getHeight());
		gui.setMailReceivedPane(this);
	}

	@Override
	protected void setUpComponents() {
		this.setLayout(new GridLayout(20,2));
		this.setAlignmentX(LEFT_ALIGNMENT);

		JLabel labelComboMail = new JLabel("Mail Delivery", SwingConstants.CENTER);
		comboBoxMailDel = new JComboBox(mailDelList());
		comboBoxMailDel.setSelectedItem(null);
		comboBoxListenner(comboBoxMailDel, "mailDel");
		update = new JButton("Update");
		JTextField blankSpace = new JTextField(20);
		blankSpace.setEnabled(false);
		blankSpace.setEditable(false);
		blankSpace.setBackground(SystemColor.controlHighlight);
		blankSpace.disable();

		JLabel labelComboMailDelivered = new JLabel("Mail Delivered", SwingConstants.CENTER);
		comboBoxMailDeled = new JComboBox(mailDeledList());
		comboBoxMailDeled.setSelectedItem(null);
		comboBoxListenner(comboBoxMailDeled, "mailDeled");

		JLabel labelAveTime = new JLabel("Average Delivery Time", SwingConstants.CENTER);
		aveTime = new JLabel();

		// format value label shown business figures
		aveTime.setText(" 0.0");
		aveTime.setPreferredSize(new Dimension(250, 50));
		aveTime.setFont(new Font("Arial", Font.PLAIN, 15));
		aveTime.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		add(labelComboMail);
		add(comboBoxMailDel);
		add(update);
		add(blankSpace);
		add(labelComboMailDelivered);
		add(comboBoxMailDeled);
		add(labelAveTime);
		add(aveTime);
	}

	@Override
	protected void addListenner() {
		update.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton button = (JButton) e.getSource();
				if(button == update){
					if(comboBoxMailDel.getSelectedItem()==null){
						JOptionPane.showMessageDialog(null, "Please select from Mail Delivery list", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
					else{
						int g = JOptionPane.YES_NO_OPTION;
						int response = JOptionPane.showConfirmDialog(null, "Confirm Received?", "Confirm Received", g);
						if(response == JOptionPane.YES_OPTION){
							for (MailDelivery md: controller.getMailDeliveries()) {
								if(md.toString().equals(selectedMailReceived)){
									controller.updateMailDelTime(md);
									refreshComboBoxMailDeliveryList();
								}
							}
						}
					}

				}
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}

	// controller calls to set the updated average delivery time
	public void setAveTime(double time){
		aveTime.setText(" " + time +" hours.");
	}
}
