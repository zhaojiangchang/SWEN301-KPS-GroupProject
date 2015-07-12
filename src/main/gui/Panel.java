package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.controllers.UIController;
import main.events.MailDelivery;
import main.logic.Location;
import main.logic.Route;

/**
 * The Panel class is an abstract class represented many Pane
 * classes shown on the GUI. The Panel class has several methods
 * that could be extended by other Pane classes.
 *
 * @author Zhiheng Sun and Zhaojiang Chang
 */
public abstract class Panel extends JPanel implements PropertyChangeListener {
	protected UIController controller;
	protected GUI gui;	// the GUI that panel is on
	protected double amount = 0;
	protected int amountInt = 0;
	protected Route selectedRoute = null;
	private String selectedRouteString = "";
	private String selectedLocationString = "";
	protected String selectedMailReceived = "";

	protected NumberFormat amountFormat;
	protected String[] priorityList = {"Air","Standard"};
	protected String[] TransportDateList = {"Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

	// the selected value in the comboboxes
	protected static String origin = "";
	protected static String destination = "";
	protected static String priority = "Standard";
	protected static String transportFirm = "";
	protected static String transportType = "";
	protected static String transportDay = "";
	protected static String selected = "";

	// the comboboxes on the frame
	protected static JComboBox comboBoxRoute;
	protected static JComboBox comboBoxOrigin;
	protected static JComboBox comboBoxDestination;
	protected static JComboBox comboBoxPriority;
	protected static JComboBox comboBoxTransportFirm;
	protected static JComboBox comboBoxTransportType;
	protected static JComboBox comboBoxTransportDay;
	protected static JComboBox comboBoxLocation;
	protected static JComboBox comboBoxMailDel;
	protected static JComboBox comboBoxMailDeled;
	protected static JFormattedTextField textWeight;
	protected static JFormattedTextField textVolume;
	protected static JFormattedTextField textCustomerNewPricePerGram;
	protected static JFormattedTextField textCustomerNewPricePerCubic;
	protected static JFormattedTextField textTPNewCostPerGram;
	protected static JFormattedTextField textTPNewCostPerCubic;
	protected static JFormattedTextField textTPmaxWeight;
	protected static JFormattedTextField textTPmaxVolume;
	protected static JFormattedTextField textTPFrequency;
	protected static JFormattedTextField textTPDuration;

	protected static List<String> currentEvent = new ArrayList<String>();
	private Map<String, Route> routeMap;
	protected JLabel labelComboOrigin;
	protected JLabel labelComboDestination;
	protected static boolean isManager;
	protected boolean isBusinessFiguresRoute = false;
	protected boolean isBusinessFiguresLocation = false;
	protected boolean isComboBoxRouteModified = false;

	public Panel (GUI gui){
		this.gui = gui;
		// set the panel to transparent and call methods to set up buttons and listener
		setOpaque(false);
		this.controller = this.gui.getUIController();

		setUpComponents();
		this.repaint();
		addListenner();
	}

	/**
	 * The following method initializes the components on it and calls method to set the
	 * style of the buttons
	 */
	protected abstract void setUpComponents();

	/**
	 * The following method adds action listeners onto buttons of the panel
	 */
	protected abstract void addListenner();

	protected void formatToDobuleJTextField(JFormattedTextField textField) {
		textField.setValue(new Double(amount));
		textField.setColumns(10);
		textField.addPropertyChangeListener("value", this);
	}
	protected void formatToIntegerJTextField(JFormattedTextField textField) {
		textField.setValue(new Integer(amountInt));
		textField.setColumns(10);
		textField.addPropertyChangeListener("value", this);
	}

	/**
	 * add listener to the given combobox
	 * 
	 * @param comboBox the given combobox
	 * @param type the given type
	 */
	protected void comboBoxListenner(JComboBox comboBox,final String type){
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selected=(String)((JComboBox)e.getSource()).getSelectedItem();

				if(type.equals("origin")){
					origin = selected;
				}
				else if(type.equals("destination")){
					destination =  selected;
				}
				else if(type.equals("priority")){
					priority =  selected;
				}
				else if(type.equals("transportType")){
					transportType = selected;
				}
				else if(type.equals("transportFirm")){
					transportFirm = selected;
				}
				else if(type.equals("transportDay")){
					transportDay = selected;
				}
				else if(type.equals("route")){
					selectedRouteString = selected;
					selectedRoute = routeMap.get(selectedRouteString);
					System.out.println("!!!!!! " + selectedRouteString);
					if (isBusinessFiguresRoute){	
						isBusinessFiguresRoute = false;
						controller.getBFRoute(selectedRoute);
					}
				}
				else if(type.equals("location")){
					selectedLocationString = selected;
					for( Route s: controller.getRoutes()){
						if(s.getOrigin().getName().equals(selectedLocationString)){
							System.out.println(selectedLocationString);
							controller.getBFLocation(s.getOrigin());
						}
						if(s.getDestination().equals(selectedLocationString)){
							controller.getBFLocation(s.getDestination());
						}
					}
				}
				else if(type.equals("mailDel")){
					selectedMailReceived = selected;
					System.out.println("panel 177: "+ selected);
				}
				else if(type.equals("mailDeled")){
					controller.getMailAveTime(selected);	
				}
			}
		});
	}

	/**
	 * add the given event to the system
	 * @param type the type of the event
	 */
	public void addBusinessEvent(String type){
		Map<String, String> currentEvent = new HashMap<String, String>();
		currentEvent.put("clerkName", gui.getCurretUsername());
		currentEvent.put("type", type);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		currentEvent.put("time",dateFormat.format(date));
		if(type.equals("mailDelivery")){
			currentEvent.put("origin", origin);
			currentEvent.put("destination", destination);
			currentEvent.put("weight", "" + textWeight.getValue());
			currentEvent.put("volume", "" + textVolume.getValue());
			currentEvent.put("priority", priority);
			controller.addEvent(null, currentEvent);
		}
		else if (type.equals("customerPriceUpdate")){

			currentEvent.put("customerNewPricePerGram", "" + textCustomerNewPricePerGram.getValue());
			currentEvent.put("customerNewPricePerCubic", "" + textCustomerNewPricePerCubic.getValue());
			controller.addEvent(selectedRoute, currentEvent);
		}
		else if (type.equals("transportCostUpdate")){
			currentEvent.put("transportNewCostPerGram", "" + textTPNewCostPerGram.getValue());
			currentEvent.put("transportNewCostPerCubic", "" + textTPNewCostPerCubic.getValue());
			controller.addEvent(selectedRoute, currentEvent);
		}
		else if (type.equals("transportDiscontinued")){
			controller.addEvent(selectedRoute, currentEvent);
			refreshRouteTab();
		}
		else if (type.equals("createRoute")){
			currentEvent.put("origin", origin);
			currentEvent.put("destination", destination);
			currentEvent.put("transportFirm", transportFirm);
			currentEvent.put("transportType", transportType);
			currentEvent.put("transportDay", transportDay);
			currentEvent.put("transportsCostPerGram", "" + textTPNewCostPerGram.getValue());
			currentEvent.put("transportsCostPerCubic", "" + textTPNewCostPerCubic.getValue());
			currentEvent.put("customerPricePerGram", "" + textCustomerNewPricePerGram.getValue());
			currentEvent.put("customerPricePerCubic", "" + textCustomerNewPricePerCubic.getValue());
			currentEvent.put("frequency", "" + textTPFrequency.getValue());
			controller.addEvent(selectedRoute, currentEvent);
			refreshRouteTab();
		}
	}

	protected void refreshRouteTab(){
		RoutesTab rtb = gui.getRouteTab();
		rtb.removeLabels();
		rtb.addLabels();
	}
	
	/**
	 * put the route list into the route combobox
	 */
	protected void comboBoxRouteList(){
		String[] routeKey = new String[controller.getRoutes().size()];
		Route[] routeValue = new Route[controller.getRoutes().size()];
		int i = 0;
		for(Route r: controller.getRoutes()){
			routeKey[i] = r.shortDescription();
			i++;
		}
		i = 0;
		for(Route r: controller.getRoutes()){
			routeValue[i] = r;
			i++;
		}
		routeMap = new HashMap<String, Route>();
		for(int j = 0; j<routeKey.length; j++){
			routeMap.put(routeKey[j], routeValue[j]);
		}

		comboBoxRoute = new JComboBox(routeKey);
		comboBoxRoute.setSelectedItem(null);
		comboBoxListenner(comboBoxRoute, "route");
	}

	protected void refreshRouteMap(){
		String[] routeKey = new String[controller.getRoutes().size()];
		Route[] routeValue = new Route[controller.getRoutes().size()];
		int i = 0;
		for(Route r: controller.getRoutes()){
			routeKey[i] = r.shortDescription();
			i++;
		}
		i = 0;
		for(Route r: controller.getRoutes()){
			routeValue[i] = r;
			i++;
		}
		routeMap = new HashMap<String, Route>();
		for(int j = 0; j<routeKey.length; j++){
			routeMap.put(routeKey[j], routeValue[j]);
		}

		comboBoxRoute = new JComboBox(routeKey);
		comboBoxRoute.setSelectedItem(null);
		comboBoxListenner(comboBoxRoute, "route");
	}


	/**
	 * get all of the transport firms
	 * @return the list of transport firms
	 */
	protected String[] getTransportFirms(){
		List<String> firms = new ArrayList<String>();
		for(Route r: controller.getRoutes()){
			if(!firms.contains(r.getTransportFirm())){
				firms.add(r.getTransportFirm());
			}
		}
		String[] fs = new String[firms.size()];
		for(int i = 0; i<firms.size(); i++){
			fs[i] = firms.get(i);
		}
		return fs;
	}

	/**
	 * get all of the origins
	 * @return the list of origins
	 */
	protected String[] getOrigins(){
		String[] origins = new String[controller.getRoutes().size()];
		int i = 0;
		for( Route s: controller.getRoutes()){
			if(!isContains(s.getOrigin().getName(), origins)){
				origins[i] = s.getOrigin().getName();
				i++;
			}
		}

		String[] originsNew = new String[i];
		for (int j = 0; j < originsNew.length; j++){
			originsNew[j] = origins[j];
		}
		return originsNew;
	}

	/**
	 * get all of the destinations
	 * @return the list of destinations
	 */
	protected String[] getDestinations(){
		String[] destinations = new String[controller.getRoutes().size()];
		int j = 0;
		for( Route s: controller.getRoutes()){
			if(!isContains(s.getDestination().getName(), destinations)){
				destinations[j] = s.getDestination().getName();
				j++;
			}
		}

		String[] destinationsNew = new String[j];
		for (int i = 0; i < destinationsNew.length; i++){
			destinationsNew[i] = destinations[i];
		}
		return destinationsNew;
	}

	/**
	 * get all of the transport types
	 * @return the list of transport types
	 */
	protected String[] getTransportTypes(){
		List<String> types = new ArrayList<String>();
		types.add("Standard");
		types.add("Air");
		for(Route r: controller.getRoutes()){
			if(!types.contains(r.getTransportType().toString())){
				types.add(r.getTransportType().toString());
			}
		}
		String[] fs = new String[types.size()];
		for(int i = 0; i<types.size(); i++){
			fs[i] = types.get(i);
		}
		return fs;
	}

	/**
	 * get all of the locations
	 * @return the list of locations
	 */
	protected String[] getLocations(){
		String[] location = new String[controller.getRoutes().size()*2];
		int i = 0;
		for( Route s: controller.getRoutes()){
			if(!isContains(s.getOrigin().getName(), location)){
				location[i] = s.getOrigin().getName();
				i++;
			}
		}
		for( Route s: controller.getRoutes()){
			if(!isContains(s.getDestination().getName(), location)){
				location[i] = s.getDestination().getName();
				i++;
			}
		}

		String[] locationNew = new String[i];
		for (int j = 0; j < locationNew.length; j++){
			locationNew[j] = location[j];
		}
		return locationNew;
	}

	/**
	 * check whether a string is contained in an array
	 * @return in or not in
	 */
	protected boolean isContains(String a, String[] b){
		for(int i = 0; i<b.length; i++){
			if(a.equalsIgnoreCase(b[i])){
				return true;
			}
		}
		return false;
	}

	/**
	 * refresh the combobox of the routes
	 */
	protected void refreshComboBoxRouteList(){
		if(comboBoxRoute==null) return;
		comboBoxRoute.removeAllItems();
		isComboBoxRouteModified = true;
		for (Route r: controller.getRoutes()) {
			String rt = r.shortDescription();
			comboBoxRoute.addItem(rt);
		}
	}
	protected void refreshComboBoxMailDeliveryList(){
		if(comboBoxMailDel==null) return;
		comboBoxMailDel.removeAllItems();
		//isComboBoxRouteModified = true;
		System.out.println("size: " + controller.getMailDeliveries().size());
		for (MailDelivery r: controller.getMailDeliveries()) {
			String rt = r.toString();
			comboBoxMailDel.addItem(rt);
		}
	}
	protected void refreshComboBoxMailDeliveredList(){
		if(comboBoxMailDeled==null) return;
		comboBoxMailDeled.removeAllItems();
		//isComboBoxRouteModified = true;
		for (String r: controller.getMailDeliveried()) {
			String rt = r;
			comboBoxMailDel.addItem(rt);
		}
	}
	/**
	 * get all of the routes 
	 * @return  the list of routes
	 */
	protected List<String> routeList(){
		List <String> routes = new ArrayList<String>();
		for (Route r: controller.getRoutes()) {
			String rt = r.shortDescription();
			routes.add(rt);
		}
		return routes;
	}	
	protected String[] mailDelList(){
		String [] mailDels = new String[controller.getMailDeliveries().size()];
		System.out.println(mailDels.length);

		int i = 0;
		for (MailDelivery md: controller.getMailDeliveries()) {
			System.out.println(md.toString());
			mailDels[i] = md.toString();
			i++;
		}
		return mailDels;
	}
	protected String[] mailDeledList(){
		String [] mailDels = new String[controller.getMailDeliveried().size()];
		int i = 0;
		for (String md: controller.getMailDeliveried()) {
			mailDels[i] = md;
			i++;
		}
		return mailDels;
	}

	/**
	 * initialise the comboboxes and the textfields
	 */
	public void init(){
		selected = "";
		transportFirm = "";
		transportType = "";
		transportDay = "";
		selectedRoute = null;
		selectedRouteString = "";
		if(textVolume!=null) textVolume.setValue(0.0);
		if(textWeight!=null) textWeight.setValue(0.0);
		if(textTPmaxWeight!=null) textTPmaxWeight.setValue(0.0);
		if(textTPmaxVolume!=null) textTPmaxVolume.setValue(0.0);
		if(textTPFrequency!=null) textTPFrequency.setValue(0);
		if(textTPDuration!=null) textTPDuration.setValue(0);
		if(textCustomerNewPricePerCubic!=null) textCustomerNewPricePerCubic.setValue(0.0);
		if(textCustomerNewPricePerGram!=null) textCustomerNewPricePerGram.setValue(0.0);
		if(textTPNewCostPerGram!=null) textTPNewCostPerGram.setValue(0.0);
		if(textTPNewCostPerCubic!=null) textTPNewCostPerCubic.setValue(0.0);
	}

	// the following methods are the getters and setters
	public static String getOrigin() {
		return origin;
	}

	public static void setOrigin(String origin) {
		Panel.origin = origin;
	}

	public static String getDestination() {
		return destination;
	}

	public static void setDestination(String destination) {
		Panel.destination = destination;
	}

	public static String getPriority() {
		return priority;
	}

	public static void setPriority(String priority) {
		Panel.priority = priority;
	}

	public static String getTransportType() {
		return transportType;
	}

	public static void setTransportType(String transportType) {
		Panel.transportType = transportType;
	}

	public static String getTransportFirm() {
		return transportFirm;
	}

	public static void setTransportFirm(String transportFirm) {
		Panel.transportFirm = transportFirm;
	}
}
