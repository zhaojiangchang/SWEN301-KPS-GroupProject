package main.events;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.logic.Route;

/**
 * a business event which describes an update to the cost to the company to send a package along a route
 * @author burlinfran
 *
 */
public class TransportUpdate extends BusinessEvent {

	private double oldPricePerGram;
	private double newPricePerGram;

	private double oldPricePerVolume;
	private double newPricePerVolume;

	public TransportUpdate( String clerk, String date, double og, double ng, double ov, double nv, List<Route> routes ) {
		this.clerk = clerk;
		this.date = date;
		oldPricePerGram = og;
		newPricePerGram = ng;
		oldPricePerVolume = ov;
		newPricePerVolume = nv;
		this.routes = routes;
	}

	public void setNewPricePerGram( int i) {
		oldPricePerGram = newPricePerGram;
		newPricePerGram = i;
	}

	public void setNewPricePerVolume( int i ) {
		oldPricePerVolume = newPricePerVolume;
		newPricePerVolume = i;
	}

	public double getOldPricePerGram() {
		return oldPricePerGram;
	}

	public double getNewPricePerGram() {
		return newPricePerGram;
	}

	public double getOldPricePerVolume() {
		return oldPricePerVolume;
	}

	public double getNewPricePerVolume() {
		return newPricePerVolume;
	}


	@Override
	public String toString() {
		return "TransportUpdate : \n------------------------------------\noldPricePerGram=" + oldPricePerGram
				+ ", \nnewPricePerGram=" + newPricePerGram
				+ ", \noldPricePerVolume=" + oldPricePerVolume
				+ ", \nnewPricePerVolume=" + newPricePerVolume + "\n";
	}

	@Override
	public Element toXML(Document doc) {
		Element transport = doc.createElement("event");

		Attr attr = doc.createAttribute("type");
		attr.setValue("Transport Update");
		transport.setAttributeNode(attr);

		routesToXML(doc, transport);
		essentialInfo(doc, transport);

		Element oldPPGram = doc.createElement("oldPricePGram");
		oldPPGram.appendChild(doc.createTextNode(String.valueOf(getOldPricePerGram())));
		transport.appendChild(oldPPGram);

		Element newPPGram = doc.createElement("newPricePGram");
		newPPGram.appendChild(doc.createTextNode(String.valueOf(getNewPricePerGram())));
		transport.appendChild(newPPGram);

		Element oldPPVolume = doc.createElement("oldPricePVolume");
		oldPPVolume.appendChild(doc.createTextNode(String.valueOf(getOldPricePerVolume())));
		transport.appendChild(oldPPVolume);

		Element newPPVolume = doc.createElement("newPricePVolume");
		newPPVolume.appendChild(doc.createTextNode(String.valueOf(getNewPricePerVolume())));
		transport.appendChild(newPPVolume);

		return transport;
	}

	@Override
	public List<String> listDesc() {
		List<String> des = new ArrayList<String>();

		des.add("Transport Update : ");
		des.add("------------------------------------");
		des.add("old price per gram = " + oldPricePerGram);
		des.add("new price per gram = " + newPricePerGram);
		des.add("old price per volume = " + oldPricePerVolume);
		des.add("new price per volume = " + newPricePerVolume);
		return des;
	}

}
