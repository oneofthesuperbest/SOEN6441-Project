package controller;

import view.ExecuteCommandView;

/**
 * This class represents the initial default phase.
 */
public class DefaultPhase extends IntermediateMapEditingPhase {

	/**
	 * {@inheritDoc}
	 */
	DefaultPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	String getString() {
		return "Default";
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editMap(String p_mapPath) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		System.out.println("Valid parameters. Loading map for editing...");
		boolean readMapResult = l_executeCVObject.readMapFile(d_gameEngineObject, p_mapPath);
		if (readMapResult) {
			d_gameEngineObject.setPhase(1);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	void loadMap(String p_mapPath) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		System.out.println("Valid parameters. Loading map...");
		boolean loadMapResult = l_executeCVObject.loadMapFile(d_gameEngineObject, p_mapPath);
		if (loadMapResult) {
			d_gameEngineObject.setPhase(2);
		}
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
