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
	public String getString() {
		return "default";
	}

	/**
	 * {@inheritDoc}
	 */
	public void editMap(String p_mapPath) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		System.out.println("Valid parameters. Loading map for editing...");
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Edit map command called.");
		boolean l_readMapResult = l_executeCVObject.readMapFile(d_gameEngineObject, p_mapPath);
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Map is loaded for editing.");
		if (l_readMapResult) {
			d_gameEngineObject.setPhase(1);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void loadMap(String p_mapPath) {
		ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
		System.out.println("Valid parameters. Loading map...");
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Load map command called.");
		boolean l_loadMapResult = l_executeCVObject.loadMapFile(d_gameEngineObject, p_mapPath);
		if (l_loadMapResult) {
			d_gameEngineObject.getLogEntryBuffer().addLogEntry("Map is loaded for game play.");
			d_gameEngineObject.setPhase(2);
		} else {
			d_gameEngineObject.getLogEntryBuffer().addLogEntry("Map is invalid. It wasn't loaded.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void loadGame(String p_filename) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Game load command was issued.");
		System.out.println("Game load command was issued.");
		this.d_gameEngineObject.refresh();
		GameLoader p_gameLoader = new GameLoader(this.d_gameEngineObject);
		if(!p_gameLoader.loadGame(p_filename, false, false)) {
			this.d_gameEngineObject.setPhase(0);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveGame(String p_filename) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void startTournament(String[] p_command) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Starting tournament.");
		d_gameEngineObject.playTournament(p_command);
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
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveMap(String p_filename, int p_type) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate() {
		printErrorMessage(this);
	}
}
