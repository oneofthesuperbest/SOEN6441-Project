package view;

import controller.GameEngine;
import controller.MapController;

import java.io.IOException;

/**
 * This class is used to execute entered command i.e.: call respective functions from
 * controller that will process the command.
 */
public class ExecuteCommandView {
	
	/**
	 * This function is used to read a map file and pass the content to controller
	 * for processing and creation of relevant objects
	 * 
	 * @param p_filename The map file to be read
	 * @param p_gameEngineObject Data read from map will be added to the p_gameEngineObject
	 * @return void It returns nothings
	 */
	void readMapFile(GameEngine p_gameEngineObject, String p_filename) {
		MapController l_mapController = new MapController(p_gameEngineObject);
		try {
			// Load the data from map into the game Engine.
			l_mapController.loadMapData(p_filename);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The function calls the editContinent method with relevant parameter list.
	 * @param p_gameEngineObject The edits will be made to this object.
	 * @param p_commandParameters The list of validated sub commands to performs relevant edits.
	 */
	void editContinent(GameEngine p_gameEngineObject, String[] p_commandParameters){
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.editContinent(p_commandParameters);
	}

	void showMap(GameEngine p_gameEngineObject){
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.showMap();
	}
}
