package tests.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import main.logic.DijkStandard;
import main.logic.InvalidLocationException;
import main.logic.Location;
import main.logic.NoDaysToShipException;
import main.logic.Route;

public class SearchTest {
	Location A;
	Location B;
	Location C;
	Location D;
	Location E;
	Location F;
	Location G;

	Route AC;
	Route AB;
	Route BD;
	Route CD;
	Route CG;
	Route DG;
	Route DF;
	Route GE;
	Route DC;

	static Set<Location> locations = new HashSet<Location>();

	public static void main(String[] arguments) throws NoDaysToShipException {
		new SearchTest();
	}

	public SearchTest() throws NoDaysToShipException {
		initialise();
		DijkStandard d = new DijkStandard(A, E, 30, 5);
		d.initialiseGraph(locations);

	}

	public void initialise() throws NoDaysToShipException {

		A = new Location("A");
		B = new Location("B");
		C = new Location("C");
		D = new Location("D");
		E = new Location("E");
		F = new Location("F");
		G = new Location("G");

		try {
			AC = new Route(A, C, null, Route.TransportType.Standard, 30, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);

			AB = new Route(A, B, null, Route.TransportType.Standard, 20, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			BD = new Route(B, D, null, Route.TransportType.Standard, 10, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			CD = new Route(C, D, null, Route.TransportType.Standard, 30, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			CG = new Route(C, G, null, Route.TransportType.Air, 10, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			DG = new Route(D, G, null, Route.TransportType.Standard, 15, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			DF = new Route(D, F, null, Route.TransportType.Standard, 20, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
			GE = new Route(G, E, null, Route.TransportType.Standard, 5, 40, 60,
					70, 2, Route.DaysOfWeek.Monday);
			DC = new Route(D, C, null, Route.TransportType.Standard, 40, 40,
					60, 70, 2, Route.DaysOfWeek.Monday);
		} catch (InvalidLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		locations.add(A);
		locations.add(A);
		locations.add(C);
		locations.add(C);
		locations.add(E);
		locations.add(F);
		locations.add(G);

		Set<Route> AOut = new HashSet<Route>();
		AOut.add(AC);
		AOut.add(AB);
		A.setOutbound(AOut);

		Set<Route> BOut = new HashSet<Route>();
		BOut.add(BD);
		B.setOutbound(BOut);

		Set<Route> BIn = new HashSet<Route>();
		BIn.add(AB);
		B.setInbound(BIn);

		Set<Route> COut = new HashSet<Route>();
		COut.add(CG);
		COut.add(CD);
		C.setOutbound(COut);

		Set<Route> CIn = new HashSet<Route>();
		CIn.add(DC);
		CIn.add(AC);
		C.setInbound(CIn);

		Set<Route> DOut = new HashSet<Route>();
		DOut.add(DC);
		DOut.add(DG);
		DOut.add(DF);
		D.setOutbound(DOut);

		Set<Route> DIn = new HashSet<Route>();
		DIn.add(BD);
		DIn.add(CD);
		D.setInbound(DIn);

		Set<Route> FIn = new HashSet<Route>();
		FIn.add(DF);
		F.setInbound(FIn);

		Set<Route> GOut = new HashSet<Route>();
		GOut.add(GE);
		G.setOutbound(GOut);

		Set<Route> GIn = new HashSet<Route>();
		GIn.add(CG);
		GIn.add(DG);
		G.setInbound(GIn);

		Set<Route> EIn = new HashSet<Route>();
		EIn.add(GE);
		E.setInbound(EIn);

	}

}
