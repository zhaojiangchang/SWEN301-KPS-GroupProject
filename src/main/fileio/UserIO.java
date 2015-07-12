package main.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import main.logic.Clerk;
import main.logic.Manager;

/**
 * UserIO can save and load a list of users that are registered in the program
 * 
 * @author Cameron Probert
 *
 */
public class UserIO {
	
	private static final String FILENAME = "users.data";

	/**
	 * Loads a list of users from a file and returns that as an ArrayList of
	 * Clerks. Throws an exception if the returned list is empty.
	 * 
	 * @return
	 * @throws NoRegisteredUsersException
	 */
	public static Set<Clerk> loadUsers() throws NoRegisteredUsersException {
		Set<Clerk> clerks = new HashSet<Clerk>();
		try {
			Scanner scan = new Scanner(new File(FILENAME));
			scan.nextLine();
			while (scan.hasNext()) {
				String type = scan.next();
				String id = scan.next();
				String password = scan.next();
				String name = scan.nextLine().trim();
				if (type.equals("Manager")) {
					clerks.add(new Manager(name, id, password));
				} else {
					clerks.add(new Clerk(name, id, password));
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (clerks.isEmpty()) {
			throw new NoRegisteredUsersException(
					"There were no users in the file.");
		}
		return clerks;
	}

	/**
	 * Saves a list of users to resources/users
	 * @param allUsers
	 */
	public static void saveUsers(Set<Clerk> allUsers) {
		try {
			PrintStream printer = new PrintStream(new File(FILENAME));
			printer.println("Type\tID\tPassword\tName");
			for (Clerk user : allUsers){
				if (user instanceof Manager){
					printer.print("Manager\t");
				} else {
					printer.print("Clerk\t");
				}
				printer.print(user.getId()+"\t");
				printer.print(user.getPassword()+"\t");
				printer.println(user.getName());
			}
			printer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
