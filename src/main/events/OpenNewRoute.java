package main.events;

import java.util.ArrayList;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.logic.Route;

/**
 * A business event which describes opening a new route
 * @author burlinfran
 *
 */
public class OpenNewRoute extends BusinessEvent {

	public OpenNewRoute (String clerk, String date, List<Route> routes) {
		this.clerk = clerk;
		this.date = date;
		this.routes = routes;
	}



	@Override
	public String toString() {
		return "OpenNewRoute \n------------------------------------\n";
	}



	@Override
	public Element toXML(Document doc) {
		Element save = doc.createElement("event");

		Attr attr = doc.createAttribute("type");
		attr.setValue("New Route");
		save.setAttributeNode(attr);

		routesToXML(doc, save);
		essentialInfo(doc, save);

		return save;
	}



	@Override
	public List<String> listDesc() {
		List<String> des = new ArrayList<String>();

		des.add("Open New Route  : ");
		des.add("------------------------------------");
		return des;
	}





}
