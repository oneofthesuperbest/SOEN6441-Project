package view;

import controller.GameEngine;
import controller.MapController;

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
	 * @return It return true if map is loaded else false
	 */
	public boolean readMapFile(GameEngine p_gameEngineObject, String p_filename) {
		MapController l_mapController = new MapController(p_gameEngineObject);
		return l_mapController.loadMapData(p_filename, true, true);
	}
	
	/**
	 * This function is used to load a valid map file (if present) and pass the content to controller
	 * for processing and creation of relevant objects
	 * 
	 * @param p_filename The map file to be read
	 * @param p_gameEngineObject Data read from map will be added to the p_gameEngineObject
	 * @return It return true if map is loaded else false
	 */
	public boolean loadMapFile(GameEngine p_gameEngineObject, String p_filename) {
		MapController l_mapController = new MapController(p_gameEngineObject);
		return l_mapController.loadMapData(p_filename, false, false);
	}

	/**
	 * The function calls the editContinent method with relevant parameter list.
	 * @param p_gameEngineObject The edits will be made to this object.
	 * @param p_commandParameters The list of validated sub commands to performs relevant edits.
	 */
	public void editContinent(GameEngine p_gameEngineObject, String[] p_commandParameters){
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.editContinent(p_commandParameters);
	}

	/**
	 * This function calls the editContinent method with relevant parameter list.
	 * @param p_gameEngineObject The edits will be made to this object.
	 * @param p_commandParameters The list of validated sub commands to performs relevant edits.
	 */
	public void editCountry(GameEngine p_gameEngineObject, String[] p_commandParameters) {
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.editCountry(p_commandParameters);
	}

	/**
	 * Calls the showMap method in the mapController to display the map.
	 * @param p_gameEngineObject GameEngine object that holds the map data.
	 */
	public void showMap(GameEngine p_gameEngineObject){
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.showMap();
	}

	/**
	 * Calls the validateMap method in the mapController to check if the map is valid.
	 * @param p_gameEngineObject GameEngine object that holds the map data.
	 */
	public void validateMap(GameEngine p_gameEngineObject){
		MapController l_mapController = new MapController(p_gameEngineObject);
		if (!l_mapController.validateMap()){
			System.out.println("VALIDATION CHECK FAILED. INVALID MAP.");
		}else{
			System.out.println("VALIDATION CHECK PASSED. MAP IS VALID");
		};
	}

	/**
	 * Save the map to the file described by the filename.
	 * @param p_gameEngineObject GameEngine object that holds the map data.
	 * @param fileName Location where the map will be saved.
	 * @return true if map was saved successful else returns false
	 */
	public boolean saveMap(GameEngine p_gameEngineObject, String fileName){
		MapController l_mapController = new MapController(p_gameEngineObject);
		return l_mapController.saveMap(fileName);
	}

	/**
	 * Calls the editNeighbor method in mapController to add new neighbors or remove neighbors.
	 * @param p_gameEngineObject GameEngine object that holds the map data.
	 * @param p_commandParameters The list of validated sub commands to performs relevant edits.
	 */
	public void editNeighbor(GameEngine p_gameEngineObject, String[] p_commandParameters){
		MapController l_mapController = new MapController(p_gameEngineObject);
		l_mapController.editNeighbor(p_commandParameters);
	}
}
