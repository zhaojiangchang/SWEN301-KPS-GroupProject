package main.fileio;

/**
 * Exception thrown when there are no users to load in from the file containing
 * all users.
 * 
 * @author Cameron Probert
 *
 */
public class NoRegisteredUsersException extends Exception {

	// Automatically generated UID
	private static final long serialVersionUID = 1006740381526100903L;

	/**
	 * Creates the exception with the given error message
	 * 
	 * @param message
	 */
	public NoRegisteredUsersException(String message) {
		super(message);
	}

}
