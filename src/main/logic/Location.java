package main.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.events.MailDelivery;

/**
 * A Location represents a real world port and contains a list of all the routes
 * that come in and leave from this location.
 *
 * @author Cameron Probert
 *
 */
public class Location implements Comparable<Location> {

	private String name;
	private Set<Route> inbound;
	private Set<Route> outbound;
	private Location previous;
	private boolean visited=false;
	private Monitor monitor = null;

	private double minDistance = Double.POSITIVE_INFINITY; //initialise all nodes to be min distance of infinity

	/**
	 * Creates a location with the given name
	 *
	 * @param name
	 */
	public Location(String name) {
		this.name = name;
		this.inbound = new HashSet<Route>();
		this.outbound = new HashSet<Route>();
	}

	public double getMinDistance() {
		return minDistance;
	}

	public void setMinDistance( double d ) {
		minDistance = d;
	}

	public void setPrevious( Location l ) {
		previous = l;
	}

	public Location getPrevious() {
		return previous;
	}

	public boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean b) {
		visited = b;
	}



	/**
	 * Returns the name of the Location
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the set of routes that come in to this location
	 *
	 * @return
	 */
	public Set<Route> getInbound() {
		return inbound;
	}

	/**
	 * Returns the set of routes that leave this location
	 *
	 * @return
	 */
	public Set<Route> getOutbound() {
		return outbound;
	}

	/**
	 * Sets the set of inbound routes to the given set
	 *
	 * @param inbound
	 */
	public void setInbound(Set<Route> inbound) {
		this.inbound = inbound;
	}

	/**
	 * Adds the given routes to the current set of inbound routes
	 *
	 * @param inbound
	 */
	public void addInbound(Route... inbound) {
		for (Route r : inbound){
			this.inbound.add(r);
		}
	}

	/**
	 * Removes the given routes to the current set of inbound routes
	 *
	 * @param inbound
	 */
	public void rmInbound(Route... inbound) {
		for (Route r : inbound){
			this.inbound.remove(r);
		}
	}

	/**
	 * Sets the list of outbound routes to the given set
	 *
	 * @param inbound
	 */
	public void setOutbound(Set<Route> outbound) {
		this.outbound = outbound;
	}

	/**
	 * Adds the given routes to the current set of outbound routes
	 *
	 * @param outbound
	 */
	public void addOutbound(Route... outbound) {
		for (Route r : outbound){
			this.outbound.add(r);
		}
	}

	/**
	 * Removes the given routes to the current set of outbound routes
	 *
	 * @param outbound
	 */
	public void rmOutbound(Route... outbound) {
		for (Route r : outbound){
			this.outbound.remove(r);
		}
	}

	public void attachMonitor(Monitor mon){
		this.monitor = mon;
	}

	/**
	 * Returns whether this Location and another are equal. This also works with
	 * comparing to a String, if the string is the same as this locations name
	 * it will return true.
	 */
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other instanceof String) {
			return this.getName().equals((String) other);
		}
		if (!(other instanceof Location)) {
			return false;
		}
		Location otherLoc = (Location) other;
		return this.getName().equals(otherLoc.getName());
	}

	/**
	 * Returns a representation of this location as a String
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Location");
		sb.append("\nName: " + name);
		return sb.toString();
	}

	@Override
	public int compareTo(Location other) {

		if ( this.minDistance<other.minDistance ) {
			return -1;
		}
		else if ( this.minDistance==other.minDistance ) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public int getDeliveriesIn(){
		int mailIn = 0;
		for(Route r: inbound){
			//System.out.println(r.toString());
		}
		List<MailDelivery> deliveries = monitor.getMailEvents();
		for(MailDelivery del: deliveries){
			System.out.println("Mail del -----------------------------------------------\n" + del.toString());
			List<Route> routes = del.getRoutes();
			for(Route r: routes){
				System.out.println(r.toString());
				if(inbound.contains(r)){
					System.out.println("inside monitor tot del in ----------");
					mailIn++;
				}
			}
		}
		return mailIn;
	}

	public double getTotalVolume(){
		double totalVol = 0;
		for(MailDelivery del: monitor.getMailEvents()){
			for(Route r: del.getRoutes()){
				if(inbound.contains(r)){
					System.out.println("inside monitor tot volume in ----------");
					totalVol+=del.getVolume();
				}
			}
		}
		return totalVol;
	}

	public double getTotalWeight(){
		double totalWeight = 0;
		for(MailDelivery del: monitor.getMailEvents()){
			for(Route r: del.getRoutes()){
				if(inbound.contains(r)){
					System.out.println("inside monitor tot weight in ----------");
					totalWeight+=del.getWeight();
				}
			}
		}
		return totalWeight;
	}
}
