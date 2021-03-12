package controller;

import model.Player;

/**
 * This class is used to used an abstract state class, which is used to maintain/handle current phase state
 */
public abstract class Phase {
	GameEngine d_gameEngineObject;
	
	/**
	 * This construtor initializes the game engine object
	 * @param p_gameEngine The current context of game engine object
	 */
	Phase(GameEngine p_gameEngine) {
		d_gameEngineObject = p_gameEngine;
	}
	
	/**
	 * This is a generic print message function to display invalid command error
	 * @param p_phase Object of the current phase
	 */
	void printErrorMessage(Phase p_phase) {
		System.out.println("Invalid command: Command is invalid for " + p_phase.getString() + " phase");
	}
	
	/**
	 * This method is used to get the current phase in string format. 
	 * For e.g.: In GamePlay StartUp Phase, it returns "Gameplay Start up"
	 * @return The current phase in string format
	 */
	abstract String getString();
	
	/**
	 * This function is used to invoke another function that loads Map for editing
	 * @param p_mapPath The path of the map to be loaded for editing
	 */
	abstract void editMap(String p_mapPath);
	
	/**
	 * This function is used to invoke another function that edits continent as per the command
	 * @param p_command The command entered by the user
	 */
	abstract void editContinent(String[] p_command);
	
	/**
	 * This function is used to invoke another function that edits continents as per the command
	 * @param p_command The command entered by the user
	 */
	abstract void editCountry(String[] p_command);
	
	/**
	 * This function is used to invoke another function that edits neighbors as per the command
	 * @param p_command The command entered by the user
	 */
	abstract void editNeighbor(String[] p_command);
	
	/**
	 * This function displays map in user readable form
	 */
	abstract void showMap();
	
	/**
	 * This function is used to invoke another function that saves current map being edited
	 * @param p_filename The filename for the new map
	 */
	abstract void saveMap(String p_filename);
	
	/**
	 * This function is used to validate current map and display appropriate message for the user
	 */
	abstract void validate();
	
	
	/**
	 * This function is used to invoke another function that loads Map for game play
	 * @param p_mapPath The path of the map to be loaded
	 */
	abstract void loadMap(String p_mapPath);
	
	/**
	 * This function is used to invoke another function that adds/removes players as per the command
	 * @param p_command The command entered by the user
	 */
	abstract void addPlayers(String[] p_command);
	
	/**
	 * This function is used to assign players to countries and start the game
	 */
	abstract void startGame();
	
	/**
	 * This function is used to issue deploy order
	 * @param p_player The player who issued the order
	 * @param p_countryID ID of the country to which to deploy the armies
	 * @param p_numberOfArmies Number of armies to deploy
	 * @return 1 if the deploy order was issued else 0
	 */
	abstract int delop(Player p_player, String p_countryID, int p_numberOfArmies);
	
	/**
	 * This function is used to issue advance order
	 * @param p_player The player who issued the order
	 * @param p_countryIDFrom ID of the country from which to deploy the armies
	 * @param p_countryIDTo ID of the country to which to deploy the armies
	 * @param p_numberOfArmies Number of armies to advance
	 * @return 1 if the advance order was issued else 0
	 */
	abstract int advance(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies);
	
	/**
	 * This function is used to issue bomb order
	 * @param p_player The player who issued the order
	 * @param p_countryID ID of the country to bomb
	 * @return 1 if the bomb order was issued else 0
	 */
	abstract int bomb(Player p_player, String p_countryID);
	
	/**
	 * This function is used to issue blockade order
	 * @param p_player The player who issued the order
	 * @param p_countryID ID of the country to block
	 * @return 1 if the blockade order was issued else 0
	 */
	abstract int blockade(Player p_player, String p_countryID);
	
	/**
	 * This function is used to issue negotiate order
	 * @param p_player The player who issued the order
	 * @param p_playerID ID of the player to negotiate with
	 * @return 1 if the negotiate order was issued else 0
	 */
	abstract int negotiate(Player p_player, String p_playerID);
	
	/**
	 * This function is used to issue airlift order
	 * @param p_player The player who issued the order
	 * @param p_countryIDFrom ID of the country from which to deploy the armies
	 * @param p_countryIDTo ID of the country to which to deploy the armies
	 * @param p_numberOfArmies Number of armies to advance
	 * @return 1 if the airlift order was issued else 0
	 */
	abstract int airlift(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies);
}
