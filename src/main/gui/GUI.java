package main.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.controllers.UIController;
import main.logic.Clerk;

/**
 * The GUI is responsible for setting up the KPSmart system user interface.
 * GUI creates JFrame and added background panel, LoginGUI onto the frame
 * for starting the system.
 *
 * @author Zhiheng Sun
 */

public class GUI {

	private JFrame frame;	// this is the frame the KPSmart system will be shown on
	private JLayeredPane layeredPane;	// this is used to add panel onto the frame
	private Panel backgroundPanel;
	private Panel backgroundBlank;
	private UIController controller;
	protected BusinessFiguresTab bft;
	protected BusinessFiguresRoutePane bfrp;
	protected BusinessFiguresLocationPane bflp;
	protected MailReceivedPane mrp;
	protected RoutesTab rtp;

	// the dimension of the frame
	private static int width = 900;
	private static int height = 770;

	// textFields on all panels
	private JTextField userIdTextF;
	private JPasswordField passwordTextF;

	// the username and password user entered
	public String userId;
	public String password;
	private Clerk clerk;

	/**
	 * Set up the frame and the layeredPane and add the loginGUI onto the layeredPane
	 */
	public void setUp() {
		frame = new JFrame();
		frame.setTitle("KPSmart System");
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		// add the background image to frame
		layeredPane = new JLayeredPane();
		backgroundPanel = new BackgroundPanel(this);
		backgroundBlank = new BackgroundBlank(this);
		addBGPanel(backgroundPanel);

		frame.setLayeredPane(layeredPane);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );

		userIdTextF = new JTextField(18);
		passwordTextF = new JPasswordField(18);

		LoginGUI loginGUI = new LoginGUI(this);
		addPanel(loginGUI);
	}

	/**
	 * add the given panel onto the layeredPane
	 * @param panel the given panel
	 */
	protected void addPanel(Panel panel){
		layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
		frame.repaint();
	}

	/**
	 * remove the given panel from the layeredPane
	 * @param panel the given panel
	 */
	protected void removePanel(Panel panel){
		layeredPane.remove(panel);
		frame.repaint();
	}

	/**
	 * add the given panel onto the background layeredPane
	 * @param panel the given panel
	 */
	protected void addBGPanel(Panel panel){
		layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
	}

	/** the  following methods are getters and setters */
	public void setUIController(UIController u){
		this.controller = u;
	}

	public UIController getUIController(){
		return this.controller;
	}

	protected Panel getBackgroundPanel(){
		return backgroundPanel;
	}

	protected Panel getBackgroundBlank(){
		return backgroundBlank;
	}

	public JTextField getUserId() {
		return userIdTextF;
	}

	public JPasswordField getPassword() {
		return passwordTextF;
	}

	public void setUsername(String text) {
		userId = text;
	}

	public void setPassword(String text) {
		password = text;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public void setCurrentUser(Clerk clerk) {
		this.clerk = clerk;
	}

	public String getCurretUsername() {
		return clerk.getName();
	}

	public void setBusinessFiguresTotal(BusinessFiguresTab businessFiguresTotal){
		this.bft = businessFiguresTotal;
		controller.updateBusinessFiguresTotal();
	}

	public BusinessFiguresTab getBusinessFiguresTab(){
		return bft;
	}

	public void setBusinessFiguresRoutePane(BusinessFiguresRoutePane businessFiguresRoutePane) {
		this.bfrp = businessFiguresRoutePane;
	}
	public BusinessFiguresRoutePane getBusinessFiguresRoutePane(){
		return bfrp;
	}
	
	public void setBusinessFiguresLocationPane(BusinessFiguresLocationPane businessFiguresLocationPane) {
		this.bflp = businessFiguresLocationPane;
	}
	
	public BusinessFiguresLocationPane getBusinessFiguresLocationPane(){
		return bflp;
	}
	
	public void setMailReceivedPane(MailReceivedPane mailReceivednPane) {
		this.mrp = mailReceivednPane;
	}
	
	public MailReceivedPane getMailReceivedPane(){
		return mrp;
	}
	
	public void setRouteTab(RoutesTab routeTap) {
		this.rtp = routeTap;
	}
	
	public RoutesTab getRouteTab(){
		return rtp;
	}
}
