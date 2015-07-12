package main.logic;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import main.events.MailDelivery;

/**
 * The Route class represents a real route between two destinations
 *
 * @author Cameron Probert
 *
 */
public class Route {

	/**
	 * The type of transport that the route uses
	 *
	 * @author Cameron Probert
	 *
	 */
	public enum TransportType {
		Standard, Air
	}

	/**
	 * The days of the week
	 *
	 * @author Cameron Probert
	 *
	 */
	public enum DaysOfWeek {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
	}

	private Location origin;
	private Location destination;
	private double totalTimeToDeliver; // Total number of hours delivered on
										// this route
	private int numTimesDelivered;
	private String transportFirm;
	private TransportType transportType;
	private double pricePerGramTransport;
	private double pricePerVolumeTransport;
	private double pricePerGramCustomer;
	private double pricePerVolumeCustomer;
	private Set<DaysOfWeek> days;
	private double departureFrequency;

	/**
	 * Creates a new Route with the given fields
	 *
	 * @param origin
	 * @param destination
	 * @param transportFirm
	 * @param transportType
	 * @param pricePerGramTransport
	 * @param pricePerVolumeTransport
	 * @param pricePerGramCustomer
	 * @param pricePerVolumeCustomer
	 * @throws NoDaysToShipException
	 *             if no days are given
	 * @throws InvalidLocationException
	 *             if either the origin or destination are null
	 */
	public Route(Location origin, Location destination, String transportFirm,
			TransportType transportType, double pricePerGramTransport,
			double pricePerVolumeTransport, double pricePerGramCustomer,
			double pricePerVolumeCustomer, double departureFrequency,
			DaysOfWeek... days) throws NoDaysToShipException,
			InvalidLocationException {
		if (days.length == 0) {
			throw new NoDaysToShipException(
					"The route has to run on at least one day.");
		}
		if (origin == null) {
			throw new InvalidLocationException(
					"The route has to have a non-null origin");
		}
		if (destination == null) {
			throw new InvalidLocationException(
					"The route has to have a non-null destination");
		}
		this.numTimesDelivered = 0;
		this.totalTimeToDeliver = 0;
		this.origin = origin;
		this.destination = destination;
		this.transportFirm = transportFirm;
		this.transportType = transportType;
		this.pricePerGramTransport = pricePerGramTransport;
		this.pricePerVolumeTransport = pricePerVolumeTransport;
		this.pricePerGramCustomer = pricePerGramCustomer;
		this.pricePerVolumeCustomer = pricePerVolumeCustomer;
		this.departureFrequency = departureFrequency;
		initialiseDays();
		enableDays(days);
	}

	/**
	 * Initialises the days set. Uses an EnumSet to retain the order of the days
	 */
	private void initialiseDays() {
		this.days = EnumSet.noneOf(DaysOfWeek.class);
	}

	/**
	 * Will enable each of the given days of the week
	 */
	public void enableDays(DaysOfWeek... daysToAdd) {
		for (DaysOfWeek day : daysToAdd) {
			days.add(day);
		}
	}

	/**
	 * Removes days of the week from the current set of days of the week. If it
	 * would remove every day that there is then it makes no changes and returns
	 * false. Otherwise it returns true.
	 *
	 * @param daysToRm
	 * @return
	 */
	public boolean disableDays(DaysOfWeek... daysToRm) {
		Set<DaysOfWeek> backup = EnumSet.copyOf(days);
		for (DaysOfWeek day : daysToRm) {
			days.remove(day);
		}
		if (days.size() == 0) {
			days = backup;
			return false;
		}
		return true;
	}

	/**
	 * Returns a set of the days of the week the route runs on
	 */
	public Set<DaysOfWeek> getDays() {
		return days;
	}

	/**
	 * Returns the origin location
	 *
	 * @return
	 */
	public Location getOrigin() {
		return origin;
	}

	/**
	 * Returns the destination location
	 *
	 * @return
	 */
	public Location getDestination() {
		return destination;
	}

	/**
	 * Returns the average time taken to deliver using the route
	 *
	 * @return
	 */
	public double getAverageTimeToDeliver() {
		if (totalTimeToDeliver == 0) {
			return Double.MAX_VALUE;
		}
		return totalTimeToDeliver / numTimesDelivered;
	}

	/**
	 * Returns the name of the Transport firm
	 *
	 * @return
	 */
	public String getTransportFirm() {
		return transportFirm;
	}

	/**
	 * Returns the price per gram to us
	 *
	 * @return
	 */
	public double getPricePerGramTransport() {
		return pricePerGramTransport;
	}

	/**
	 * Returns the price per volume for the customer
	 *
	 * @return
	 */
	public double getPricePerVolumeTransport() {
		return pricePerVolumeTransport;
	}

	/**
	 * Returns the price per gram for the customer
	 *
	 * @return
	 */
	public double getPricePerGramCustomer() {
		return pricePerGramCustomer;
	}

	/**
	 * Returns the price customers pay for volume
	 *
	 * @return
	 */
	public double getPricePerVolumeCustomer() {
		return pricePerVolumeCustomer;
	}

	/**
	 * Returns how often the route runs on days that it does run
	 *
	 * @return
	 */
	public double getDepartureFrequency() {
		return departureFrequency;
	}

	/**
	 * Return the transport types
	 *
	 * @return
	 */
	public TransportType getTransportType() {
		return transportType;
	}

	/**
	 * Sets the average time to deliver for the route
	 *
	 * @return
	 */
	public void addDeliveryTime(double timeToDeliver) {
		this.totalTimeToDeliver += timeToDeliver;
		numTimesDelivered++;
	}

	/**
	 * Sets the price per gram we pay the transport company
	 *
	 * @param pricePerVolumeCustomer
	 */
	public void setPricePerGramTransport(double pricePerGramTransport) {
		this.pricePerGramTransport = pricePerGramTransport;
	}

	/**
	 * Sets the price per volume we pay the transport company
	 *
	 * @param pricePerVolumeCustomer
	 */
	public void setPricePerVolumeTransport(double pricePerVolumeTransport) {
		this.pricePerVolumeTransport = pricePerVolumeTransport;
	}

	/**
	 * Sets the price per gram the customer pays
	 *
	 * @param pricePerVolumeCustomer
	 */
	public void setPricePerGramCustomer(double pricePerGramCustomer) {
		this.pricePerGramCustomer = pricePerGramCustomer;
	}

	/**
	 * Sets the price per volume the customer pays
	 *
	 * @param pricePerVolumeCustomer
	 */
	public void setPricePerVolumeCustomer(double pricePerVolumeCustomer) {
		this.pricePerVolumeCustomer = pricePerVolumeCustomer;
	}

	/**
	 * Sets the departure frequency to the given number. If the number is
	 * greater 24 or less than 0 it will not change and return a false to show
	 * this
	 *
	 * @param departureFrequency
	 * @return
	 */
	public boolean setDepartureFrequency(double departureFrequency) {
		if (departureFrequency <= 24 && departureFrequency > 0) {
			this.departureFrequency = departureFrequency;
			return true;
		}
		return false;
	}

	/**
	 * calculates the revenue for a route given the mail events
	 */
	public double getRevenue(List<MailDelivery> mail){
		double revenue = 0;
		for(MailDelivery m: mail){
			if(m.getRoutes().contains(this)){
				revenue += m.getVolume()*pricePerVolumeCustomer+
						m.getWeight()*pricePerGramCustomer;
			}
		}
		return revenue;
	}

	/**
	 * calculates the expenditure for a route given the mail events
	 */
	public double getExpenditure(List<MailDelivery> mail){
		double expen = 0;
		for(MailDelivery m: mail){
			if(m.getRoutes().contains(this)){
				expen += m.getVolume()*pricePerVolumeTransport+
						m.getWeight()*pricePerGramTransport;
			}
		}
		return expen;
	}

	/**
	 * calculates the number of mail deliveries on this route
	 */
	public int getNumEvents(List<MailDelivery> mail){
		int total = 0;
		for(MailDelivery m: mail){
			if(m.getRoutes().contains(this)){
				total++;
			}
		}
		return total;
	}

	/**
	 * Returns a string representation of this route
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(origin.getName() + " to " + destination.getName());
		builder.append("\naverageTimeToDeliver=");
		builder.append(totalTimeToDeliver);
		builder.append("\ntransportFirm=");
		builder.append(transportFirm);
		builder.append(", transportType=");
		builder.append(transportType.name());
		builder.append("\npricePerGramTransport=");
		builder.append(pricePerGramTransport);
		builder.append(", pricePerVolumeTransport=");
		builder.append(pricePerVolumeTransport);
		builder.append("\npricePerGramCustomer=");
		builder.append(pricePerGramCustomer);
		builder.append(", pricePerVolumeCustomer=");
		builder.append(pricePerVolumeCustomer);
		builder.append("\ndays=");
		for (DaysOfWeek day : days) {
			builder.append(", " + day.name());
		}
		builder.append(", departureFrequency=");
		builder.append(departureFrequency);
		return builder.toString();
	}

	/**
	 * Returns a string representation of this route
	 */
	public List<String> toList() {
		List<String> list = new ArrayList<String>();
		list.add("From " + origin.getName() + " to " + destination.getName());
		list.add("Average Delivery Time = " + totalTimeToDeliver);
		list.add("Transport Firm = " + transportFirm);
		list.add("Transport Type = " + transportType.name());
		list.add("Price Per Gram Transport = " + pricePerGramTransport);
		list.add("Price Per Volume Transport = " + pricePerVolumeTransport);
		list.add("Price Per Gram Customer = " + pricePerGramCustomer);
		list.add("Price Per Volume Customer = " + pricePerVolumeCustomer);
		String days = "Days = ";
		for (DaysOfWeek day : this.days) {
			days += day.name() + " ";
		}
		list.add(days);
		list.add("Departure Frequency = " + departureFrequency);
		return list;
	}

	/**
	 * Returns whether this route is equal to the given object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Route)) {
			return false;
		}
		Route other = (Route) obj;
		if (days == null) {
			if (other.days != null) {
				return false;
			}
		} else if (!days.equals(other.days)) {
			return false;
		}
		if (departureFrequency != other.departureFrequency) {
			return false;
		}
		if (destination == null) {
			if (other.destination != null) {
				return false;
			}
		} else if (!destination.equals(other.destination)) {
			return false;
		}
		if (origin == null) {
			if (other.origin != null) {
				return false;
			}
		} else if (!origin.equals(other.origin)) {
			return false;
		}
		if (transportFirm == null) {
			if (other.transportFirm != null) {
				return false;
			}
		} else if (!transportFirm.equals(other.transportFirm)) {
			return false;
		}
		if (transportType != other.transportType) {
			return false;
		}
		return true;
	}

	public String shortDescription(){
		  String des = "";
		  des += origin.getName() + " to " + destination.getName();
		  des += " via " + transportType.toString();
		  des += " (" + transportFirm + ")";
		  return des;
	}
}
