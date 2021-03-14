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
	public String getString() {
		return "map editing";
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
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Edit continent called with following command: " + p_command);
		l_executeCVObject.editContinent(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void editCountry(String[] p_command) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Edit country called with following command: " + p_command);
		l_executeCVObject.editCountry(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void editNeighbor(String[] p_command) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Edit neighbor called with following command: " + p_command);
		l_executeCVObject.editNeighbor(d_gameEngineObject, p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void showMap() {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Show map command called.");
		l_executeCVObject.showMap(d_gameEngineObject);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveMap(String p_filename) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Save map command called.");
		boolean l_returnValue = l_executeCVObject.saveMap(d_gameEngineObject, p_filename);
		if (l_returnValue) {
			System.out.println("Moving out of map editing phase.");
			d_gameEngineObject.getLogEntryBuffer().addLogEntry("Map saved to " + p_filename);
			d_gameEngineObject.setPhase(0);
		} else {
			d_gameEngineObject.getLogEntryBuffer().addLogEntry("Invalid map: Map wasn't saved.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void validate() {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Validate command called.");
		l_executeCVObject.validateMap(d_gameEngineObject);
	}
}
