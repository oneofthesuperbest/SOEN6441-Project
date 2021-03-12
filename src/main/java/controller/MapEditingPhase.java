package controller;

/**
 * This class represents the map editing phase.
 */
public class MapEditingPhase extends IntermediateMapEditingPhase {
	
	/**
	 * {@inheritDoc}
	 */
	MapEditingPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	String getString() {
		return "Map editing";
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editMap(String p_mapPath) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void loadMap(String p_mapPath) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editContinent(String[] p_command) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editCountry(String[] p_command) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editNeighbor(String[] p_command) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void showMap() {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void saveMap(String p_filename) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void validate() {
		printErrorMessage(this);
	}
}
