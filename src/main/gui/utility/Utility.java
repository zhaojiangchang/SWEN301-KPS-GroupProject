package main.gui.utility;

import javax.swing.JOptionPane;

/**
 * Utility methods for the GUI
 * 
 * @author Cameron Probert
 *
 */
public class Utility {

	/**
	 * Shows a dialog box with the specified title and message to the user
	 * 
	 * @param title
	 *            The title for the dialog box
	 * @param message
	 *            The message for the dialog box
	 */
	public static void showMessageDialog(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Creates a dialog asking the user for confirmation
	 * 
	 * @param title
	 *            The title of the dialog box
	 * @param message
	 *            The message of the dialog box
	 * @return A boolean of the user's choice
	 */
	public static boolean confirmationDialog(String title, String message) {
		return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
				message, title, JOptionPane.OK_CANCEL_OPTION);

	}
}
