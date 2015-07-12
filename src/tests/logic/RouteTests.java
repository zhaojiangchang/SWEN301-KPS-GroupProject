package tests.logic;

import static org.junit.Assert.*;

import main.logic.*;
import main.logic.Route.DaysOfWeek;
import main.logic.Route.*;

import org.junit.*;

/**
 * Testing for the Route class
 * 
 * @author Cameron Probert
 *
 */
public class RouteTests {

	public RouteTests() {
		constructRoute1();
		constructRoute2();
		addDays();
		removeDays();
	}

	/**
	 * Tests removing Days
	 */
	@Test
	public void removeDays() {
		Route route = makeRoute2();
		DaysOfWeek[] actualDays = { DaysOfWeek.Monday, DaysOfWeek.Saturday,
				DaysOfWeek.Wednesday };

		// Remove Tuesday and checks to make sure that the route is unchanged as
		// Tuesday was not in the Set already
		assertTrue(route.disableDays(DaysOfWeek.Tuesday));
		DaysOfWeek[] savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		assertTrue(checkArraysEqual(savedDays, actualDays));

		// Disables Monday and Wednesday and check to see if they are no longer
		// in the set
		assertTrue(route.disableDays(DaysOfWeek.Monday, DaysOfWeek.Wednesday));
		actualDays = new DaysOfWeek[] { DaysOfWeek.Saturday };
		savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		assertTrue(checkArraysEqual(savedDays, actualDays));

		// Try to disable the last day (Saturday) which should return false
		// because you can't remove all the days a route runs for
		assertFalse(route.disableDays(DaysOfWeek.Saturday));
		savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		assertTrue(checkArraysEqual(savedDays, actualDays));
	}

	/**
	 * Tests adding Days
	 */
	@Test
	public void addDays() {
		Route route = makeRoute1();
		route.enableDays(DaysOfWeek.Friday);
		DaysOfWeek[] savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		DaysOfWeek[] actualDays = { DaysOfWeek.Friday };
		assertTrue(checkArraysEqual(savedDays, actualDays));
		route.enableDays(DaysOfWeek.Tuesday);
		savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		assertFalse(checkArraysEqual(savedDays, actualDays));
		savedDays = route.getDays().toArray(
				new DaysOfWeek[route.getDays().size()]);
		actualDays = new DaysOfWeek[] { DaysOfWeek.Friday, DaysOfWeek.Tuesday };
		assertTrue(checkArraysEqual(savedDays, actualDays));
	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void constructRoute1() {
		Route route1 = makeRoute1();
		assertTrue(route1.getOrigin().getName().equals("Auckland"));
		assertTrue(route1.getDestination().getName().equals("Wellington"));
		assertTrue(route1.getTransportFirm().equals("NZPost"));
		assertTrue(route1.getTransportType().equals(TransportType.Standard));
		assertTrue(route1.getPricePerGramCustomer() == 0.1);
		assertTrue(route1.getPricePerVolumeCustomer() == 0.2);
		assertTrue(route1.getPricePerGramTransport() == 0.05);
		assertTrue(route1.getPricePerVolumeTransport() == 0.15);
		assertTrue(route1.getDepartureFrequency() == 4);
		DaysOfWeek[] emptyDays = {};
		assertFalse(route1.getDays().equals(emptyDays));
		DaysOfWeek[] savedDays = route1.getDays().toArray(
				new DaysOfWeek[route1.getDays().size()]);
		DaysOfWeek[] actualDays = { DaysOfWeek.Friday };
		assertTrue(checkArraysEqual(savedDays, actualDays));
	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void constructRoute2() {
		Route route1 = makeRoute2();
		assertTrue(route1.getOrigin().getName().equals("Dubai"));
		assertTrue(route1.getDestination().getName().equals("Christchurch"));
		assertTrue(route1.getTransportFirm().equals("NZCouriers"));
		assertTrue(route1.getTransportType().equals(TransportType.Air));
		assertTrue(route1.getPricePerGramCustomer() == 0.2);
		assertTrue(route1.getPricePerVolumeCustomer() == 0.4);
		assertTrue(route1.getPricePerGramTransport() == 0.1);
		assertTrue(route1.getPricePerVolumeTransport() == 0.3);
		assertTrue(route1.getDepartureFrequency() == 8);
		DaysOfWeek[] emptyDays = {};
		assertFalse(route1.getDays().equals(emptyDays));
		DaysOfWeek[] savedDays = route1.getDays().toArray(
				new DaysOfWeek[route1.getDays().size()]);
		DaysOfWeek[] actualDays = { DaysOfWeek.Monday, DaysOfWeek.Saturday,
				DaysOfWeek.Wednesday };
		assertTrue(checkArraysEqual(savedDays, actualDays));
	}

	/**
	 * Checks to see if 2 DaysOfWeek arrays are equal
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	private static boolean checkArraysEqual(DaysOfWeek[] array1,
			DaysOfWeek[] array2) {
		if (array1.length != array2.length) {
			return false;
		}
		for (DaysOfWeek day1 : array1) {
			boolean contains = false;
			for (DaysOfWeek day2 : array2) {
				if (day1 == day2) {
					contains = true;
					break;
				}
			}
			if (!contains)
				return false;
		}
		for (DaysOfWeek day2 : array2) {
			boolean contains = false;
			for (DaysOfWeek day1 : array1) {
				if (day2 == day1) {
					contains = true;
					break;
				}
			}
			if (!contains)
				return false;
		}
		return true;
	}

	/**
	 * Creates a route
	 * 
	 * @return
	 */
	public static Route makeRoute1() {
		Location origin = new Location("Auckland");
		Location destination = new Location("Wellington");
		String transportFirm = "NZPost";
		TransportType transportType = TransportType.Standard;
		double pricePerGramTransport = 0.05;
		double pricePerVolumeTransport = 0.15;
		double pricePerGramCustomer = 0.1;
		double pricePerVolumeCustomer = 0.2;
		double departureFrequency = 4;
		DaysOfWeek[] days = { DaysOfWeek.Friday };
		try {
			return new Route(origin, destination, transportFirm, transportType,
					pricePerGramTransport, pricePerVolumeTransport,
					pricePerGramCustomer, pricePerVolumeCustomer,
					departureFrequency, days);
		} catch (NoDaysToShipException | InvalidLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a route
	 * 
	 * @return
	 */
	public static Route makeRoute2() {
		Location origin = new Location("Dubai");
		Location destination = new Location("Christchurch");
		String transportFirm = "NZCouriers";
		TransportType transportType = TransportType.Air;
		double pricePerGramTransport = 0.1;
		double pricePerVolumeTransport = 0.3;
		double pricePerGramCustomer = 0.2;
		double pricePerVolumeCustomer = 0.4;
		double departureFrequency = 8;
		DaysOfWeek[] days = { DaysOfWeek.Monday, DaysOfWeek.Wednesday,
				DaysOfWeek.Saturday };
		try {
			return new Route(origin, destination, transportFirm, transportType,
					pricePerGramTransport, pricePerVolumeTransport,
					pricePerGramCustomer, pricePerVolumeCustomer,
					departureFrequency, days);
		} catch (NoDaysToShipException | InvalidLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		new RouteTests();
	}
}
