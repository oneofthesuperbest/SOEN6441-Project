package controller;

import java.util.Scanner;
import model.MapState;

/**
 * This class is the main game engine which carry out commands of the players
 */
public class GameEngine {
	private MapState d_mapState = new MapState();
	String d_commandSeparator = " ";

	/**
	 * Get the current map state.
	 * @return Current map state.
	 */
	public MapState getMapState() {
		return d_mapState;
	}
	
	/**
	 * Used to load the GameEngine console for game play phase
	 * @return none
	 */
	public void loadGameEngine() {
		System.out.println("GameEngine console loaded.");
		ValidateCommandController l_VCVObject = new ValidateCommandController();
		@SuppressWarnings("resource")
		Scanner l_scannerObject = new Scanner(System.in);
		while(true) {
			System.out.println("Enter your command");
			String l_command = l_scannerObject.nextLine();
			l_VCVObject.isValidCommand(l_command, this);
		}
	}
}
