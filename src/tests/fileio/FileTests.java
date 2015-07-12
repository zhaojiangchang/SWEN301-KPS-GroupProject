package tests.fileio;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import main.events.*;
import main.fileio.LoadXML;
import main.fileio.SaveXML;
import main.logic.InvalidLocationException;
import main.logic.Location;
import main.logic.NoDaysToShipException;
import main.logic.Route;
import main.logic.Route.DaysOfWeek;
import main.logic.Route.TransportType;


/** tests reading and writing from and to xml
 *
 * @author burlinfran
 *
 */
public class FileTests {

	private final static Location origin = new Location("Auckland");
	private final static Location destination = new Location("Wellington");
	private final static String firmName = "Transport firm A";
	private final static TransportType tt = TransportType.Standard;
	private final static String clerk = "Sammy";
	private final static String date = "2015/03/03 10:53:00";

	private final static double ppgTran = 10;
	private final static double ppvTran = 15;
	private final static double ppgCust = 10;
	private final static double ppvCust = 15;

	private final static double changedppgTran = 20;
	private final static double changedppvTran = 25;
	private final static double changedppgCust = 20;
	private final static double changedppvCust = 25;
	private final static double timeTaken = 0;
	private final static double depFreq = 2;
	private final static double weight = 200;
	private final static double volume = 25;
	private final static double priority = 1;
	private final static double revenue = 27;
	private final static DaysOfWeek day = DaysOfWeek.Monday;

	private Set<Route> routes;
	private Set<Location> locs;

	public FileTests(){

		setUpOne();
	}

	/**
	 * sets up the handler and load and save classes
	 */
	private void setUpOne(){
		List<BusinessEvent> events = eventsOne();
		SaveXML save = new SaveXML("xml/saveFile");
		save.save(events);

		LoadXML load = new LoadXML("xml/saveFile");
		events = load.getEvents();
		for(BusinessEvent e: events){
			for(String s: e.description()){
				//System.out.println(s);
			}
		}
		locs = load.getLocations();
		routes = load.getRoutes();

		testRoutes();
		testLocation();
		//testLocation(load.getLocations());
	}

	/*@Test
	public void testRouteDelete() {

	}*/

	@Test
	public void testLocation(){
		assertTrue(locs.size()==2);
		Location[] locArray = new Location[locs.size()];
		int i=0;
		for(Location loc: locs){
			locArray[i] = loc;
			i++;
		}
		Route[] rArray = new Route[routes.size()];
		i=0;
		for(Route r: routes){
			rArray[i] = r;
			i++;
		}
		System.out.println("length of origin out bound" + locArray[0].getOutbound().size());
		for(Route r: locArray[0].getOutbound() ){
			System.out.println("origin out bound "+r.toString());
			System.out.println("");
		}
		for(Route r: locArray[1].getInbound() ){
			System.out.println("destination inbound "+r.toString());
			System.out.println("");
		}
		assertTrue(locArray[0].getOutbound().contains(rArray[0]));
		assertTrue(locArray[1].getInbound().contains(rArray[0]));
	}

	/**
	 * tests the values of the final route is what is expected
	 */
	@Test
	public void testRoutes() {
		assertTrue(routes.size()==1);
		for(Route r: routes){
			assertTrue(r.getOrigin().equals(origin));
			assertTrue(r.getDestination().equals(destination));
			assertTrue(r.getTransportFirm().equals(firmName));
			assertTrue(r.getPricePerGramCustomer() == changedppgCust);
			assertTrue(r.getPricePerVolumeCustomer() == changedppvCust);
			assertTrue(r.getPricePerGramTransport() == changedppgTran);
			assertTrue(r.getPricePerVolumeTransport() == changedppvTran);
			assertTrue(r.getDepartureFrequency() == 2);
			TransportType tt = TransportType.Standard;
			assertTrue(r.getTransportType().equals(tt));
			Set<DaysOfWeek> days = new HashSet<DaysOfWeek>();
			days.add(day);
			assertTrue(r.getDays().equals(days));
			//System.out.println(r.toString());
		}
	}

	/**
	 * this is a public method which can be called anywhere which return a list of business events which can be used for testing
	 *
	 * creates every type of event except a delete event
	 * @return
	 */
	public static List<BusinessEvent> eventsOne(){
		List<Route> routes = new ArrayList<Route>();
		try {
			Route route = new Route(origin, destination, firmName, tt, ppgTran, ppvTran, ppgCust, ppvCust, depFreq, day);
			routes.add(route);
		} catch (NoDaysToShipException e) {
			e.printStackTrace();
		} catch (InvalidLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BusinessEvent> events = new ArrayList<BusinessEvent>();

		OpenNewRoute open = new OpenNewRoute(clerk, date, routes);
		events.add(open);

		MailDelivery mail = new MailDelivery(clerk, date, origin.getName(), destination.getName(), weight, volume, priority, revenue, timeTaken, routes);
		events.add(mail);

		CustomerPriceChange change = new CustomerPriceChange(clerk, date, ppgCust, changedppgCust, ppvCust, changedppvCust, routes);
		events.add(change);

		routes.get(0).setPricePerGramCustomer(changedppgCust);
		routes.get(0).setPricePerVolumeCustomer(changedppvCust);

		TransportUpdate update = new TransportUpdate(clerk, date, ppgTran, changedppgTran, ppvTran, changedppvTran, routes);
		events.add(update);

		routes.get(0).setPricePerGramTransport(changedppgTran);
		routes.get(0).setPricePerVolumeTransport(changedppvTran);



		return events;
	}

	public static void main(String[] args){
		new FileTests();
	}

}
