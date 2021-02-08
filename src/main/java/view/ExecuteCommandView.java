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
	 * @return void It returns nothings
	 */
	void readMapFile(GameEngine p_gameEngineObject, String p_filename) {
		MapController l_mapController = new MapController();
		try {
			// Load the data from map.
			l_mapController.loadMapData(p_filename);

			// Populating the game Engine object.
			p_gameEngineObject.setListOfContinents(l_mapController.getContinents());
			p_gameEngineObject.setListOfCountries(l_mapController.getCountries());
			p_gameEngineObject.setBorderGraph(l_mapController.getBorders());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
