package controller;

import java.io.IOException;
import java.util.List;

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
		List<String> l_lines = null;
		try {
			l_lines = readMap(p_fileName);
			if(l_lines.get(0).equals("[Map]")) {
				return d_mapLoaderConquest.loadMapData(p_fileName, p_createNewFile, p_allowInvalid);
			} else {
				return super.loadMapData(p_fileName, p_createNewFile, p_allowInvalid);
			}
		} catch (IOException e) {
			if (p_createNewFile) {
				System.out.println("File not found. Loaded an empty map.");
				return true;

			} else {
				System.out.println("error: file not found.");
				return false;
			}
		}
	}
	
	/**
	 * Write the map to file.
	 * 
	 * @param p_fileName Filename to which the map is to be written.
	 * @param p_type The type of map file. 1 for domination and 0 for conquest.
	 * @return true if map was saved successful else returns false
	 */
	public boolean saveMap(String p_fileName, int p_type) {
		if(p_type == 1) {
			return saveMap(p_fileName);
		} else {
			return d_mapLoaderConquest.saveMap(p_fileName);
		}
	}
}
