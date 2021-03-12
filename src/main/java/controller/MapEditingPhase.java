package controller;

import view.ExecuteCommandView;

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
		return "map editing";
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
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		l_executeCVObject.editContinent(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editCountry(String[] p_command) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		l_executeCVObject.editCountry(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void editNeighbor(String[] p_command) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		l_executeCVObject.editNeighbor(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void showMap() {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		l_executeCVObject.showMap(d_gameEngineObject);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void saveMap(String p_filename) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		boolean l_returnValue = l_executeCVObject.saveMap(d_gameEngineObject, p_filename);
		if (l_returnValue) {
			System.out.println("Moving out of map editing phase.");
			d_gameEngineObject.setPhase(0);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	void validate() {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		l_executeCVObject.validateMap(d_gameEngineObject);
	}
}
