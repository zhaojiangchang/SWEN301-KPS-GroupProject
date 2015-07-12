package main.fileio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.events.BusinessEvent;
import main.logic.Location;
import main.logic.Route;

/**
 * This class reads in all the business events and writes all the events to file
 * This class also keeps track of the currently being displayed event and will pass on the current and
 * change the evnt to the previous or next
 *
 * @author Francine
 *
 */
public class LogHandler {

	private List<BusinessEvent> events = new ArrayList<BusinessEvent>();
	private Set<Route> routes = new HashSet<Route>();
	private Set<Location> locs;
	private BusinessEvent current;
	private SaveXML save;

	public LogHandler(){
		LoadXML load = new LoadXML("xml/saveFile");
		events = load.getEvents();
		routes = load.getRoutes();
		locs = load.getLocations();
		if(events.size()!=0){
			current = events.get(events.size()-1);
		}
		save = new SaveXML("xml/saveFile");
	}

	/**
	 * used for testing loading and saving from empty files
	 * @param empty
	 */
	public LogHandler(boolean empty){
		LoadXML load = new LoadXML("xml/emptyFile");
		events = load.getEvents();
		routes = load.getRoutes();
		locs = load.getLocations();
		if(events.size()!=0){
			current = events.get(events.size()-1);
		}
		save = new SaveXML("xml/emptyFile");
	}

	/** takes a new event from the main class and writes it to file
	 * TODO should the current event reset to the start of the list every time a new event is created?
	 *
	 * @param event
	 * @return
	 */
	public boolean newEvent(BusinessEvent event){
		events.add(event);
		if(writeToFile()) return true;
		return true; // change this return false if unsuccessful
	}

	/**
	 * called when a new event is added. Rewrites the xml file
	 * @return
	 */
	public boolean upDateLog(){
		return save.save(events);
	}

	/**
	 * returns the current event
	 * @return
	 */
	public BusinessEvent getCurrentEvent(){
		return current;
	}

	public BusinessEvent getNewestEvent(){
		this.current = events.get(events.size()-1);
		return current;
	}

	/**
	 * Deletes contents of file then rewrites all the events to file.
	 * This will need to be called every time an event is completed.
	 * TODO change to only append the newer events to the file or an event at a time as they are created?
	 */
	private boolean writeToFile(){
		// delete entire contents of the file

		return save.save(events);
	}

	/**
	 *  returns an event given a date (which includes a time) that the event happens.
	 *  Probably obsolete and will not be used
	 */
	public BusinessEvent getEvent(String date){
		for(BusinessEvent event: events){
			if(event.dateDone().equals(date)){
				return event;
			}
		}
		return null;
	}

	/**
	 *  move the current event back and return it
	 * @return
	 */
	public BusinessEvent getPreviousEvent(){
		if (events.size()==0){
			return null;
		}
		if (events.indexOf(current)==0){
			return null;
		}
		int index = events.indexOf(current);
		index--;
		current = events.get(index);
		return current;
	}

	/**
	 *  move the current event forward and return it
	 * @return
	 */
	public BusinessEvent getNextEvent(){
		if (events.size()==0){
			return null;
		}
		if (events.indexOf(current)==events.size()-1){
			return null;
		}
		int index = events.indexOf(current);
		index++;
		current = events.get(index);
		return current;
	}

	public List<BusinessEvent> getEvents(){
		return events;
	}

	public Set<Route> getRoutes(){
		return routes;
	}

	public Set<Location> getLocations(){
		return locs;
	}

}
