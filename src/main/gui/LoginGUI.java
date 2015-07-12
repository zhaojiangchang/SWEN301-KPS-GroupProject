package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.controllers.UIController;
import main.logic.Clerk;
import main.logic.Manager;

/**
 * The LoginGUI class is a JPanel which is added onto the JFrame
 * once the KPSmart system is started. LoginGUI class is responsible
 * for letting the user enter username and password to log into the system.
 *
 * @author Zhiheng Sun
 *
 */
public class LoginGUI extends Panel{

	// buttons on the panel
	private JButton quit;
	private JButton login;

	/**
	 * Create the LoginGUI by passing the gui it is on
	 *
	 * @param gui the gui the LoginGUI is on
	 */
	public LoginGUI(GUI gui) {
		super(gui);
		setBounds(200, 370, 400, 350);

	}

	@Override
	protected void setUpComponents() {
		// textFields used on LoginGUI
		gui.getUserId().setText("Username");
		gui.getUserId().setPreferredSize(new Dimension(250, 50));
		gui.getUserId().setFont(new Font("Arial", Font.PLAIN, 20));
		gui.getUserId().setForeground(new Color(130, 130, 130));
		gui.getUserId().setBackground(new Color(50, 50, 50));
		gui.getUserId().setBorder(null);

		gui.getPassword().setText("Password");
		gui.getPassword().setPreferredSize(new Dimension(250, 50));
		gui.getPassword().setFont(new Font("Arial", Font.PLAIN, 20));
		gui.getPassword().setForeground(new Color(130, 130, 130));
		gui.getPassword().setBackground(new Color(50, 50, 50));
		gui.getPassword().setBorder(null);
		gui.getPassword().setEchoChar((char)0);

		// buttons used on LoginGUI
		quit = new JButton("Quit");
		login = new JButton("Login");

		// spaces used on LoginGUI
		JLabel space1 = new JLabel("");
		space1.setPreferredSize(new Dimension(350, 15));
		JLabel space2 = new JLabel("");
		space2.setPreferredSize(new Dimension(350, 10));
		JLabel space3 = new JLabel("");
		space3.setPreferredSize(new Dimension(15, 60));

		// add textfields, buttons and spaces onto the LoginGUI
		add(gui.getUserId());
		add(space1);
		add(gui.getPassword());
		add(space2);
		setButtonStyle(quit, 140, new Color(255,165,0));
		add(space3);
		setButtonStyle(login, 140, new Color(30,144,255));
	}

	@Override
	protected void addListenner() {
		// if button login is clicked and username and password are verified,
		// LoginGUI will be removed and FunctionGUI will be added
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JButton button = (JButton) ae.getSource();
				if(button == login){
					Clerk clerk = controller.checkLogin(gui.getUserId().getText(), gui.getPassword().getText());
					// verify username and password
					if ((gui.getUserId().getText().equals("") || gui.getUserId().getText().equals("Username"))
							&& (gui.getPassword().getText().equals("") || gui.getPassword().getText().equals("Password"))){
						JOptionPane.showMessageDialog(null, "Username and Password can not be empty.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (gui.getUserId().getText().equals("") || gui.getUserId().getText().equals("Username")){
						JOptionPane.showMessageDialog(null, "Username can not be empty.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (gui.getPassword().getText().equals("") || gui.getPassword().getText().equals("Password")){
						JOptionPane.showMessageDialog(null, "Password can not be empty.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if(clerk != null){
						if(clerk instanceof Manager){
							isManager = true;
						}
						gui.setUsername(gui.getUserId().getText());
						gui.setCurrentUser(clerk);
						gui.removePanel(LoginGUI.this);
						gui.removePanel(gui.getBackgroundPanel());
						gui.addBGPanel(gui.getBackgroundBlank());
						gui.addPanel(new FunctionGUI(gui));
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Username and Password.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// if button quit is clicked, the frame will be closed
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// slightly change the background color of the login button once the mouse over login button
		login.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				login.setBackground(new Color(30,80,255));
			}

			public void mouseExited(MouseEvent evt) {
				login.setBackground(new Color(30,144,255));
			}

			public void mouseReleased(MouseEvent evt){
				login.setBackground(new Color(30,144,255));
			}

			public void mousePressed(MouseEvent evt){}
		});

		// slightly change the background color of the quit button once the mouse over quit button
		quit.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				quit.setBackground(new Color(255,111,0));
			}

			public void mouseExited(MouseEvent evt) {
				quit.setBackground(new Color(255,165,0));
			}

			public void mouseReleased(MouseEvent evt){
				quit.setBackground(new Color(255,165,0));
			}

			public void mousePressed(MouseEvent evt){}
		});

		// if username textfield is clicked, the default value "Username" will be removed
		gui.getUserId().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (gui.getUserId().getText().equals("Username")){
					gui.getUserId().setText("");
					gui.getUserId().setForeground(new Color(255, 255, 255));
				}
			}
		});

		// if username textfield is changed, the color of the value will be changed to white
		gui.getUserId().getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				changeColor();
			}
			public void removeUpdate(DocumentEvent e) {
				changeColor();
			}
			public void insertUpdate(DocumentEvent e) {
				changeColor();
			}

			public void changeColor() {
				gui.getUserId().setForeground(new Color(255, 255, 255));
			}
		});

		// if password textfield is focused, the default value "Password" will be removed
		// when the user enters password, the characters will be shown as "*"
		gui.getPassword().addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (gui.getPassword().getText().equals("Password")){
					gui.getPassword().setText("");
					gui.getPassword().setEchoChar('*');
					gui.getPassword().setForeground(new Color(255, 255, 255));
				}
			}
			public void focusLost(FocusEvent e) {}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {}

	/**
	 * The following method sets the button style by the given
	 * characteristics and adds the button onto the panel
	 *
	 * @param button	the given button to set style on
	 * @param buttonWidth	the given width of the button
	 * @param defaultColor	the default color of the given button
	 */
	protected void setButtonStyle (final JButton button, int buttonWidth, final Color defaultColor){
		// set the button size and font
		button.setPreferredSize(new Dimension(buttonWidth, 45));
		button.setFont(new Font("Arial", Font.PLAIN, 30));
		button.setBackground(defaultColor);
		button.setHorizontalTextPosition(SwingConstants.CENTER);

		button.setBackground(defaultColor);
		button.setForeground(Color.WHITE);

		// set the button to transparent
		button.setBorder(null);
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
		button.setFocusPainted(true);

		// add the button to the panel
		add(button);
	}
}
