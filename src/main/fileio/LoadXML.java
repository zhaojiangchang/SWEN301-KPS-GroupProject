package main.fileio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileReader;

import main.events.*;
import main.logic.InvalidLocationException;
import main.logic.Location;
import main.logic.NoDaysToShipException;
import main.logic.Route;
import main.logic.Route.DaysOfWeek;
import main.logic.Route.TransportType;

/**
 * Handles reading in an xml file and instantiating all the objects. Can handle an empty file or a populated file
 *
 * If for whatever reason when reading in a business event and the route that is referenced has not previously been created by
 * a make route business event then the business event is not added to the list of events
 * @author burlinfran
 *
 */
public class LoadXML {

	private List<BusinessEvent> events = new ArrayList<BusinessEvent>();
	private Set<Route> finalRoutes = new HashSet<Route>();
	private Set<Location> locations = new HashSet<Location>();


	public LoadXML(String fileName){
		try {

			File fXmlFile = new File(fileName);
			FileReader fr = new FileReader(fXmlFile);
			if (fr.read()!=-1){ // if file is not empty


				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				doc.getDocumentElement().normalize();


				NodeList nList = doc.getElementsByTagName("event");


				for (int i = 0; i < nList.getLength(); i++) {

					Node nNode = nList.item(i);
					//System.out.println("i is : " + i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						List<Route> routes = readRoutes(eElement);

						String clerk = eElement.getElementsByTagName("clerk").item(0).getTextContent();
						String date = eElement.getElementsByTagName("date").item(0).getTextContent();

						String type = eElement.getAttribute("type");

						if(type.equals("Mail Delivery")){
							mailDelivery(clerk, date, routes, eElement);
						}
						else if(type.equals("Transport Update")){
							updateTransport(clerk, date, routes, eElement);
						}
						else if(type.equals("Delete Route")){
							deleteRoute(clerk, date, routes, eElement);
						}
						else if(type.equals("New Route")){
							newRoute(clerk, date, routes, eElement);
						}
						else if(type.equals("Customer Price Change")){
							updateCustomer(clerk, date, routes, eElement);
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * reads in an update to the customer cost event for a route. This will also find the specified route
	 * in the list (one should have been created by a newRoute event) and change the price of the route
	 * @param clerk
	 * @param date
	 * @param routes
	 * @param eElement
	 */
	private void updateCustomer(String clerk, String date, List<Route> routes,
			Element eElement) {
		String oldPPG = eElement.getElementsByTagName("oldPricePGram").item(0).getTextContent();
		String newPPG = eElement.getElementsByTagName("newPricePGram").item(0).getTextContent();
		String oldPPV = eElement.getElementsByTagName("oldPricePVolume").item(0).getTextContent();
		String newPPV = eElement.getElementsByTagName("newPricePVolume").item(0).getTextContent();



		routes.get(0).setPricePerGramCustomer(Double.parseDouble(oldPPG));
		routes.get(0).setPricePerVolumeCustomer(Double.parseDouble(oldPPV));

		Route r = findRoute(routes.get(0));
		removeRouteFromLocations(routes.get(0));
		if(r!=null){
			r.setPricePerGramCustomer(Double.parseDouble(newPPG));
			r.setPricePerVolumeCustomer(Double.parseDouble(newPPV));
			routes = new ArrayList<Route>();
			routes.add(r);
			// create a customer price update event
			// create a route using the old values. Find the route in the list and modify it
			CustomerPriceChange change = new CustomerPriceChange(clerk, date, Double.parseDouble(oldPPG),
					Double.parseDouble(newPPG), Double.parseDouble(oldPPV), Double.parseDouble(newPPV), routes);
			events.add(change);
		}

	}

	/**
	 * reads in a create a new route business event for a route. This also creates the route and adds it to the list of routes
	 * @param clerk
	 * @param date
	 * @param routes
	 * @param eElement
	 */
	private void newRoute(String clerk, String date, List<Route> routes,
			Element eElement) {

		//create a new route event
		// add the route to the list of routes
		OpenNewRoute create = new OpenNewRoute(clerk, date, routes);
		events.add(create);

		finalRoutes.add(routes.get(0));
		//System.out.println("---------------------------------------route added");
	}

	/**
	 * Creates a delete route business event. This will also find the specified route and delete it from the list of routes.
	 * @param clerk
	 * @param date
	 * @param routes
	 * @param eElement
	 */
	private void deleteRoute(String clerk, String date, List<Route> routes,
			Element eElement) {
		// create a delete route event
		// delete the route from the route
		DeleteRoute delete = new DeleteRoute(clerk, date, routes);
		boolean wasFoundAndDeleted = removeRoute(routes.get(0));
		removeRouteFromLocations(routes.get(0));
		if(wasFoundAndDeleted){
			events.add(delete);
		}
	}

	/**
	 * reads in an update to the transport cost event for a route. This will also find the specified route
	 * in the list (one should have been created by a newRoute event) and changes the price of the route
	 * @param clerk
	 * @param date
	 * @param routes
	 * @param eElement
	 */
	private void updateTransport(String clerk, String date, List<Route> routes,
			Element eElement) {
		String oldPPG = eElement.getElementsByTagName("oldPricePGram").item(0).getTextContent();
		String newPPG = eElement.getElementsByTagName("newPricePGram").item(0).getTextContent();
		String oldPPV = eElement.getElementsByTagName("oldPricePVolume").item(0).getTextContent();
		String newPPV = eElement.getElementsByTagName("newPricePVolume").item(0).getTextContent();




		routes.get(0).setPricePerGramTransport(Double.parseDouble(oldPPG));
		routes.get(0).setPricePerVolumeTransport(Double.parseDouble(oldPPV));

		Route r = findRoute(routes.get(0));
		removeRouteFromLocations(routes.get(0));
		if(r!=null){
			r.setPricePerGramTransport(Double.parseDouble(newPPG));
			r.setPricePerVolumeTransport(Double.parseDouble(newPPV));
			routes.clear();
			routes.add(r);
			// create a transport update event
			// create a route using the old values. Find the route in the list and modify it
			TransportUpdate transport = new TransportUpdate(clerk, date, Double.parseDouble(oldPPG), Double.parseDouble(newPPG),
					Double.parseDouble(oldPPV), Double.parseDouble(newPPV), routes);
			events.add(transport);
		}
	}

	/**
	 * Creates a mail delivery business event which involves a set of routes.
	 * @param clerk
	 * @param date
	 * @param routes
	 * @param eElement
	 */
	private void mailDelivery(String clerk, String date, List<Route> routes, Element eElement) {
		String origin = eElement.getElementsByTagName("origin").item(0).getTextContent();
		String destination = eElement.getElementsByTagName("destination").item(0).getTextContent();
		String weight = eElement.getElementsByTagName("weight").item(0).getTextContent();
		String volume = eElement.getElementsByTagName("volume").item(0).getTextContent();
		String priority = eElement.getElementsByTagName("priority").item(0).getTextContent();
		String revenue = eElement.getElementsByTagName("revenue").item(0).getTextContent();
		String timeTaken = eElement.getElementsByTagName("timeTaken").item(0).getTextContent();

		// create a mail delivery given the route and the strings found. Some strings will need to be converted to integers etc
		boolean allRoutesWereFound = true;
		for(Route route: routes){
			Route r = findRoute(route);
			if(r==null) allRoutesWereFound = false;
			else{
				r.addDeliveryTime(Double.parseDouble(timeTaken));
				routes.clear();
				routes.add(r);
			}
			removeRouteFromLocations(route); // must remove the old route from the locations whether found or not
		}
		if(allRoutesWereFound){
			MailDelivery mail = new MailDelivery(clerk, date, origin, destination, Double.parseDouble(weight),
					Double.parseDouble(volume), Double.parseDouble(priority), Double.parseDouble(revenue), Double.parseDouble(timeTaken), routes);
			events.add(mail);
		}

	}

	/**
	 * Removes a specified route from the list of routes
	 * Will also remove the route from outgoing list of the origin and the incoming list of the destination (Locations)
	 * @param route
	 */
	private boolean removeRoute(Route route) {
		Route r = findRoute(route);
		if(r!=null){
			r.getOrigin().rmInbound(r);
			r.getDestination().rmInbound(r);
			finalRoutes.remove(r);
			return true;
		}
		return false;
	}

	/**
	 * Finds and returns a route matching what was read in which is already in the list
	 * @param route
	 * @return
	 */
	private Route findRoute(Route route) {
		for(Route r : finalRoutes){
			if(route.equals(r))return r;
		}
		return null;
	}

	/**
	 * reads in all the routes as a list of routes
	 * for each route locations are either created or added to depending on whether the location existed before
	 * @param eElement
	 * @return
	 */
	private List<Route> readRoutes(Element eElement) {
		List<Route> routes = new ArrayList<Route>();

		NodeList nList = eElement.getElementsByTagName("route");

		for(int i=0; i<nList.getLength(); i++){

			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element route = (Element) nNode;

				String origin = eElement.getElementsByTagName("origin").item(0).getTextContent();
				String destination = eElement.getElementsByTagName("destination").item(0).getTextContent();
				String transportType = eElement.getElementsByTagName("transportType").item(0).getTextContent();
				TransportType tranType = null;
				for(TransportType t: TransportType.values()){
					if(transportType.equals(t.toString())) tranType=t;
				}
				//String averageTime = eElement.getElementsByTagName("average time").item(0).getTextContent();
				String firmName = eElement.getElementsByTagName("firmName").item(0).getTextContent();
				String CPGTran = eElement.getElementsByTagName("costPGramForTransport").item(0).getTextContent();
				String CPVTran = eElement.getElementsByTagName("costPVolumeForTransport").item(0).getTextContent();
				String CPGCust = eElement.getElementsByTagName("costPGramToCustomer").item(0).getTextContent();
				String CPVCust = eElement.getElementsByTagName("costPVolumeToCustomer").item(0).getTextContent();
				String depFreq = eElement.getElementsByTagName("departureFrequency").item(0).getTextContent();

				DaysOfWeek[] days = getDays(route);
				// make a new route add to list
				Location or = getLocation(origin);
				Location des = getLocation(destination);
				try {
					Route r;
					r = new Route(or, des, firmName, tranType, Double.parseDouble(CPGTran),
							Double.parseDouble(CPVTran), Double.parseDouble(CPGCust),
							Double.parseDouble(CPVCust), Double.parseDouble(depFreq), days);
					or.addOutbound(r);
					des.addInbound(r);

					routes.add(r);

				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (NoDaysToShipException e) {
					e.printStackTrace();
				} catch (InvalidLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return routes;
	}

	/**
	 * Finds the location in the list given a string. If the location does not exist a new one is created
	 * @param origin
	 * @return
	 */
	private Location getLocation(String origin) {
		for(Location location: locations){
			if(location.equals(origin)){
				return location;
			}
		}
		Location loc = new Location(origin);
		locations.add(loc);
		return loc;

	}

	/**
	 * takes the old route and removes it from the origins and destinations
	 * @param route
	 */
	private void removeRouteFromLocations(Route r){
		r.getOrigin().rmOutbound(r);
		r.getDestination().rmInbound(r);
	}

	/**
	 * reads in the days a route operates on
	 * @param route
	 * @return
	 */
	private DaysOfWeek[] getDays(Element route) {
		List<DaysOfWeek> days = new ArrayList<DaysOfWeek>();
		NodeList nList = route.getElementsByTagName("departureDays");
		for(int i=0; i<nList.getLength(); i++){
			Node day = nList.item(i);
			if (day.getNodeType() == Node.ELEMENT_NODE) {
				Element dayElm = (Element) day;
				DaysOfWeek dWeek = null;
				String dayOfWeek = dayElm.getElementsByTagName("day").item(0).getTextContent();

				for(DaysOfWeek d: DaysOfWeek.values()){
					if(dayOfWeek.equals(d.toString())){
						dWeek = d;
					}
				}
				days.add(dWeek);
			}
		}
		DaysOfWeek[] dayDel = new DaysOfWeek[days.size()];
		for(int i=0; i<days.size(); i++){
			dayDel[i] = days.get(i);
		}
		return dayDel;
	}

	public Set<Route> getRoutes(){
		return finalRoutes;
	}

	public List<BusinessEvent> getEvents() {
		return events;
	}

	public Set<Location> getLocations() {
		return locations;
	}

}
