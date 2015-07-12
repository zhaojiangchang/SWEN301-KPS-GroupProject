package main.events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.logic.Route;

/**
 * A business event which describes sending a package on a delivery
 *
 * @author burlinfran
 *
 */
public class MailDelivery extends BusinessEvent {

	private String origin;
	private String destination;
	private double weight;
	private double volume;
	private double priority;
	private double revenue;
	private double timeTaken;

	// TODO Get rid of the 'time' parameter
	public MailDelivery(String clerk, String date, String or, String des,
			double we, double vol, double prio, double rev, double timeT,
			List<Route> routes) {
		this.clerk = clerk;
		this.date = date;
		origin = or;
		destination = des;
		weight = we;
		volume = vol;
		priority = prio;
		revenue = rev;
		timeTaken = timeT;
		this.routes = routes;

	}

	public boolean isReceived() {
		return (timeTaken != 0);
	}

	// getters
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public double getWeight() {
		return weight;
	}

	public double getVolume() {
		return volume;
	}

	public double getPriority() {
		return priority;
	}

	public double getRevenue() {
		return revenue;
	}

	public double getTimeTaken() {
		return timeTaken;
	}

	// setters
	public void setWeight(int w) {
		weight = w;
	}

	public void setVolume(int v) {
		volume = v;
	}

	public void setPriority(int p) {
		priority = p;
	}

	/**
	 * Finds the total time taken to complete this delivery, from creating the
	 * mail delivery event Code adapted from user London's question on
	 * http://stackoverflow.com/questions
	 * /5351483/calculate-date-time-difference-in-java
	 * 
	 * @author Cameron Probert
	 * @param endTime
	 */
	public void setTimeTaken(String endTime) {

		// If the timeTaken has already been set then do nothing
		if (timeTaken != 0) {
			return;
		}

		// Custom date format
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(date);
			System.out.println("D1: " + d1.toString());
			d2 = format.parse(endTime);
			System.out.println("D2: " + d2.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		double diffHours = ((double)diff / (60 * 60 * 1000));
		timeTaken = diffHours;
	}

	@Override
	public Element toXML(Document doc) {
		// TODO also need to get whether it has been received or not
		Element mail = doc.createElement("event");

		Attr attr = doc.createAttribute("type");
		attr.setValue("Mail Delivery");
		mail.setAttributeNode(attr);

		routesToXML(doc, mail);
		essentialInfo(doc, mail);

		Element origin = doc.createElement("origin");
		origin.appendChild(doc.createTextNode(getOrigin()));
		mail.appendChild(origin);

		Element destination = doc.createElement("destination");
		destination.appendChild(doc.createTextNode(getDestination()));
		mail.appendChild(destination);

		Element weight = doc.createElement("weight");
		weight.appendChild(doc.createTextNode(String.valueOf(getWeight())));
		mail.appendChild(weight);

		Element volume = doc.createElement("volume");
		volume.appendChild(doc.createTextNode(String.valueOf(getVolume())));
		mail.appendChild(volume);

		Element priority = doc.createElement("priority");
		priority.appendChild(doc.createTextNode(String.valueOf(getPriority())));
		mail.appendChild(priority);

		Element revenue = doc.createElement("revenue");
		revenue.appendChild(doc.createTextNode(String.valueOf(getRevenue())));
		mail.appendChild(revenue);

		Element timeTaken = doc.createElement("timeTaken");
		timeTaken
				.appendChild(doc.createTextNode(String.valueOf(getTimeTaken())));
		mail.appendChild(timeTaken);

		return mail;
	}

	@Override
	public String toString() {
		String str = origin + " " + destination + " $" + revenue + " (";
		if (priority == 0) {
			str += "Standard)";
		} else {
			str += "Air)";
		}
		return str;
	}

	@Override
	public List<String> listDesc() {
		List<String> des = new ArrayList<String>();

		des.add("Mail Delivery : ");
		des.add("------------------------------------");
		des.add("origin = " + origin);
		des.add("destination = " + destination);
		des.add("weight = " + weight);
		des.add("volume = " + volume);
		des.add("priority = " + priority);
		des.add("revenue = " + revenue);
		des.add("time taken = " + timeTaken);
		des.add("is received = " + isReceived());
		return des;
	}

	public String shortDes() {
		String des = origin + " to " + destination + " (";
		if (priority == 0) {
			des += "Standard)";
		} else {
			des += "Air)";
		}
		return des;
	}

}
