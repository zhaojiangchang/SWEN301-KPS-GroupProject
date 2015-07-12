package main.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import main.logic.Route.TransportType;


public class DijkAir {

	//initialise all paths to be cost of infinity:
	//A fringe is initialised

	//Begin at starting node, and check all the paths which go out from the node.
	//As you 'discover' nodes, ensure to record the cost it has taken to reach nodes.
	//if the 'cost' to reach that node is less than the current cost there (initialised
	//all to infinity), then replace it
	//if the 'cost' to reach that node is higher than the current cost then don't replace it.
	//at each point when a node is reached, as well as the cost there, the origin node is also recorded

	//remember that costs are weighted from the START node.

	//at some point, if a node has reached a point where all the possible incoming nodes
	//have been checked, then we can say that the lowest cost of them is the
	//lowest cost path - ie: the best that you can get. It is now 'finished'

	private Location origin;
	private Location destination;
	private Set<Location> locations;
	private List<Location> finalLocations;

	private double weight;
	private double volume;

	public DijkAir( Location origin, Location destination, double w, double v ) {
		this.origin = origin;
		this.destination = destination;

		this.weight = w;
		this.volume = v;
	}


	public void initialiseGraph(Set<Location> l) {
		locations = l;
		setInfinity();

		PriorityQueue<Location> nodeQueue = new PriorityQueue<Location>();

		origin.setMinDistance(0);
		nodeQueue.add(origin);

		while (!nodeQueue.isEmpty()) {
			Location node = nodeQueue.poll();

			// Visit all outbound routes
			for (Route edge : node.getOutbound() ) {
				if( edge.getTransportType()==Route.TransportType.Standard ) {
					continue;
				}

				Location siblingNode = edge.getDestination();
				double cost = ( edge.getPricePerGramTransport() * weight ) + (edge.getPricePerVolumeTransport()*volume);
				double pathSoFar = node.getMinDistance() + cost;

				if (pathSoFar < siblingNode.getMinDistance() ) { //if the path we are checking is better than the existing
					nodeQueue.remove(siblingNode);
					siblingNode.setMinDistance(pathSoFar);
					siblingNode.setPrevious(node);
					nodeQueue.add(siblingNode);
				}
			}
		}

		/*for( Location LL : nodeQueue ) {
			System.out.println(LL.getName() +":"+ LL.getMinDistance());
		}*/

		finalLocations =  getShortestPathTo(destination) ;

	}

	public void setInfinity() {

		for( Location l : locations ) {
			l.setMinDistance(Double.POSITIVE_INFINITY);
		}

	}

	public void printGraph( List<Location> p ) {

		System.out.println("FINAL PATH: ");

		for( Location loc : p ) {
			System.out.println( loc.toString() );
		}
	}

	public static List<Location> getShortestPathTo(Location end) {
		List<Location> path = new ArrayList<Location>();
		for (Location node = end; node != null; node = node.getPrevious() )
			path.add(node);
		Collections.reverse(path);
		return path;
	}

	/**
	 * Returns the list of routes that connects the locations
	 * Will return an empty array if there is no route or if
	 * there is no single route between two locations which the algorithm thought
	 * there was a route between
	 * @return
	 */
	public List<Route> getBestRoute(){
		List<Route> route = new ArrayList<Route>();
		for(int i=0; i<finalLocations.size()-1; i++){
			Route r = bestOneRoute(finalLocations.get(i), finalLocations.get(i+1));
			if(r==null){
/*				System.out.println("error error error error error can't find route between locations");*/
				return new ArrayList<Route>();
			}
			else{
				route.add(r);
			}
		}
		return route;
	}


	public Route bestOneRoute(Location origin, Location destination){
		Route best = null;
		for(Route rFrom : origin.getOutbound()){
			for(Route rTo: destination.getInbound()){
				if(rFrom.equals(rTo) && rFrom.getTransportType().equals(TransportType.Air)){
					if(rFrom.equals(rTo)){
						if(best==null){
							best=rFrom;
						}
						else if(cost(rFrom)<cost(best)){
							best=rFrom;
						}
					}
				}
			}
		}
		return best;
	}

	private double cost(Route r){
		return r.getPricePerGramTransport()*weight + r.getPricePerVolumeTransport()*volume;
	}

	public double getCostOfRoute(){
		double finalCost = 0;
		for(Route r: getBestRoute()){
			finalCost+=cost(r);
		}
		return finalCost;
	}



}
