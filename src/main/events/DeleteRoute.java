package main.events;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.logic.Route;

/**
 * returns a business event which describes deleting an event
 * @author burlinfran
 *
 */
public class DeleteRoute extends BusinessEvent {

	/**
	 * Constructor accepts specific routes to delete
	 * @param r: route
	 */
	public DeleteRoute(String clerk, String date, List<Route> r) {
		this.clerk = clerk;
		this.date = date;
		routes = r;
	}


	@Override
	public String toString() {
		return "DeleteRoute : \n------------------------------------\n";
	}


	@Override
	public Element toXML(Document doc) {
		Element delete = doc.createElement("event");

		Attr attr = doc.createAttribute("type");
		attr.setValue("Delete Route");
		delete.setAttributeNode(attr);

		routesToXML(doc, delete);
		essentialInfo(doc, delete);

		return delete;
	}


	@Override
	public List<String> listDesc() {
		List<String> des = new ArrayList<String>();

		des.add("Delete Route : ");
		des.add("------------------------------------");
		return des;
	}



}
