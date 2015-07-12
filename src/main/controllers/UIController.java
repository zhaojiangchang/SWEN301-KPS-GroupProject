package main.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import main.events.MailDelivery;
import main.gui.GUI;
import main.gui.Panel;
import main.logic.Clerk;
import main.logic.InvalidLoginException;
import main.logic.Location;
import main.logic.Monitor;
import main.logic.Route;

public class UIController {
	private static GUI gui;
	private static Monitor monitor;
	private Clerk clerk;
	//test only
	private List<String>event;

	public UIController(GUI gui, Monitor monitor){
		this.gui = gui;
		this.monitor = monitor;
		this.event = new ArrayList<String>();
	}

	/**
	 * check Logs in a user if they have the correct credentials
	 * and pass current user to gui
	 * @param id
	 * @param password
	 * @return true if log in correctly
	 */
	public Clerk checkLogin(String id, String password){

		try {
			clerk = monitor.logIn(id, password);
			if(clerk!=null){
				gui.setCurrentUser(clerk);
				return clerk;
			}

		} catch (InvalidLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * after click log out button in function panel
	 * call logout method in monitor class to set current user to null
	 */
	public void logOut(){
		monitor.logOut();
	}
	/**
	 *
	 */
	public boolean addNewUser(String id, String password, String name, boolean isManager){
		return (monitor.makeNewUser(id, password, name, isManager));
	}
	public boolean removeNewUser(String id){
		return (monitor.removeUser(id));
	}
	public void addEvent(Route r, Map<String, String> eventInfo) {
		monitor.saveEvent(r, eventInfo);
	}

	public List<String> getMostRecentEvent(){
		return monitor.getMostRecentEvent();
	}

	public List<String> getNextEvent(){
		return monitor.nextEvent();
	}

	public List<String> getPreviousEvent(){
		return monitor.previousEvent();
	}
	public Set<Route> getRoutes(){
		return monitor.getRoutes();
	}
	public void setRouteFigures(double revenue, double expenditure, int numOfEvents, boolean isCritical){
		gui.getBusinessFiguresRoutePane().setRevenue(revenue);
		gui.getBusinessFiguresRoutePane().setExpend(expenditure);
		gui.getBusinessFiguresRoutePane().setEvents(numOfEvents);
		gui.getBusinessFiguresRoutePane().setIsCritical(isCritical);
	}
	public void setLocationFigures(double revenue, double expenditure, int numOfEvents){
		gui.getBusinessFiguresLocationPane().setTotalVol(revenue);
		gui.getBusinessFiguresLocationPane().setTotalWeight(expenditure);
		gui.getBusinessFiguresLocationPane().setNumItems(numOfEvents);
	}
	
	public void setTotalTransportFigures(double revenue, double expenditure, int numOfEvents){
		gui.getBusinessFiguresTab().setRevenue(revenue);
		gui.getBusinessFiguresTab().setExpend(expenditure);
		gui.getBusinessFiguresTab().setEvents(numOfEvents);
	}

	public void getBFRoute(Route selectedRoute) {
		System.out.println("))))" + selectedRoute);

		monitor.getEventsForRoute(selectedRoute);
	}

	public void getBFLocation(Location selectedLocation) {
		System.out.println(selectedLocation.getName());
		monitor.getEventsForLocation(selectedLocation);
	}

	public void updateBusinessFiguresTotal() {
		monitor.calculateBusinessFigures();
	}
	public List<MailDelivery> getMailDeliveries(){
	    return monitor.getNotReceivedDels();
	}
	public List<String> getMailDeliveried(){
	    return monitor.getMailDelGeneric();
	}

	public void updateMailDelTime(MailDelivery selectedMail) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		monitor.setTimeTaken(selectedMail, dateFormat.format(date));
	}

	public void getMailAveTime(String selected) {
		monitor.getAverageDeliveryTime(selected);
	}
	
	public void setAverageDeliveryTime(double time){
		gui.getMailReceivedPane().setAveTime(time);
	}
}
