package view;

/**
 * This class contains the main function and is used to start the game
 */
public class MainView {
	/**
	 * This is the main function which is executed first and initializes variables
	 * before start of the game
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		ConsoleView l_consoleHandle = new ConsoleView();
		System.out.println("Starting console...");
		l_consoleHandle.startConsole();
		System.exit(0);
	}

}