package main.logic;

public class NoDaysToShipException extends Exception {

	// Generated UID
	private static final long serialVersionUID = -8209684358419995861L;

	public NoDaysToShipException(){
		super();
	}

	public NoDaysToShipException(String message){
		super(message);
	}
}
