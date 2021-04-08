package controller;

/**
 * This class implements the adapter class from adapte pattern
 */
public class Adapter extends MapLoaderWarzone {
	MapLoaderConquest d_mapLoaderConquest;

	/**
	 * This constructor initializes the adapter variables
	 * @param p_gameEngine The game engine object
	 */
	public Adapter(GameEngine p_gameEngine) {
		super(p_gameEngine);
		d_mapLoaderConquest = new MapLoaderConquest(p_gameEngine);
	}

	/**
	 * Load the map contents into the game.
	 * 
	 * @param p_fileName      The complete path of the file.
	 * @param p_createNewFile Used to indicate if a new map need to be created if
	 *                        file is not present.
	 * @param p_allowInvalid  Used to indicate if invalid map is allowed to load.
	 * @return true if map was loaded successfully else it returns false
	 */
	public boolean loadMapData(String p_fileName, boolean p_createNewFile, boolean p_allowInvalid) {
		
		return true;
	}
}
