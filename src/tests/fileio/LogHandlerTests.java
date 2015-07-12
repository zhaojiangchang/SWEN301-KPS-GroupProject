package tests.fileio;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.events.BusinessEvent;
import main.events.OpenNewRoute;
import main.fileio.LogHandler;
import main.logic.InvalidLocationException;
import main.logic.NoDaysToShipException;
import main.logic.Route;

/**
 * tests the log handler including testing what happens if files are found empty
 * @author burlinfran
 *
 */
public class LogHandlerTests {

private BusinessEvent event;
private LogHandler handler;

	public LogHandlerTests(){
		checkToBackBreaks();
		checkToForwardBreaks();

		checkEmptyFile();
	}

	/**
	 * tests empty reading from an empty file does not pull exceptions
	 */
	@Test
	public void checkEmptyFile() {

		// clears the file so this test does what is expected
		PrintWriter writer;
		try {
			writer = new PrintWriter("xml/emptyFile");
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		handler = new LogHandler(true); // load empty file

		List<BusinessEvent> events = FileTests.eventsOne();
		handler.newEvent(events.get(0));




	}

	/**
	 * checks that trying to move to far backward down the list will return a null
	 */
	@Test
	public void checkToBackBreaks() {
		handler = new LogHandler();
		event  = handler.getCurrentEvent();

		event = handler.getPreviousEvent();
		assertFalse(event==null);
		event = handler.getPreviousEvent();
		assertFalse(event==null);
		event = handler.getPreviousEvent();
		assertFalse(event==null);
		event = handler.getPreviousEvent();
		assertTrue(event==null);
	}

	/**
	 * checks that trying to move to far forward down the list will return a null
	 */
	@Test
	public void checkToForwardBreaks() {
		handler = new LogHandler();
		event  = handler.getCurrentEvent();

		event = handler.getNextEvent();
		assertNull(event);
		event = handler.getPreviousEvent();
		assertFalse(event==null);

	}

	private void printEvent(BusinessEvent e){
		for(String s: e.description()){
			System.out.println(s);
		}
	}

	public static void main(String[] args){
		new LogHandlerTests();
	}

}