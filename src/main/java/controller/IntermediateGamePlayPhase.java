package controller;

/**
 * This class is used to implement the common behavior of certain commands
 * generically
 * 
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
	public void editMap(String p_mapPath) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void loadMap(String p_mapPath) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void editContinent(String[] p_command) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void editCountry(String[] p_command) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void editNeighbor(String[] p_command) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void showMap() {
		MapController l_mapController = new MapController(d_gameEngineObject);
		l_mapController.showMapForGamePlay();
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveMap(String p_filename) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate() {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public int stop() {
		printErrorMessage(this);
		return 0;
	}
}
