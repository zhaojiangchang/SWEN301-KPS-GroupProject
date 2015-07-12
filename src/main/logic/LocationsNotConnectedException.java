package main.logic;

public class LocationsNotConnectedException extends Exception {
	public LocationsNotConnectedException(){
		super("The two locations are not connected");
	}
}
