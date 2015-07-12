package main.logic;

/**
 * A clerk should be logged on to the program at all times, it is the programs
 * recorded identity of the person currently using it.
 *
 * @author Cameron Probert
 *
 */
public class Clerk {

	// Name and ID are final fields so they cannot be changed after creating the
	// clerk
	protected final String name;
	protected final String id;

	// Password is not final so that it can be changed at a later date if
	// needed
	protected String password;

	/**
	 * Creates a new Clerk with the given strings
	 *
	 * @param name
	 * @param id
	 * @param password
	 */
	public Clerk(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
	}

	/**
	 * Returns the name of the Clerk
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the id of the Clerk
	 *
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the password of the Clerk
	 *
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns true of the given id and password match this user's id and
	 * password
	 *
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean isUser(String id, String password) {
		// TODO Auto-generated method stub
		return this.id.equals(id) && this.password.equals(password);
	}

	/**
	 * Returns the users credentials without the password as that is private
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Type: Clerk");
		sb.append("\nName: " + name);
		sb.append("\nID: " + id);
		return sb.toString();
	}
}
