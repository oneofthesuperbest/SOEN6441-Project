package controller;

/**
 * This class is used to implement the common behavior of certain commands generically
 * @author -Bilbo-
 *
 */
public abstract class IntermediateGamePlayPhase extends Phase {
	
	/**
	 * {@inheritDoc}
	 */
	IntermediateGamePlayPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
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
		// ----
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
