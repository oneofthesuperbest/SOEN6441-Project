package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;

/**
 * Controller class for handling map data.
 */
public class MapController {
	private GameEngine d_gameEngine;

	/**
	 * Create a new map controller with the specified GameEngine.
	 * 
	 * @param p_gameEngine A GameEngine object which is populated with the map data.
	 */
	public MapController(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
	}

	/**
	 * Load the map contents into the game.
	 * 
	 * @param p_fileName The complete path of the file.
	 * @param p_createNewFile Used to indicate if a new map need to be created if file is not present.
	 * @param p_allowInvalid Used to indicate if invalid map is allowed to load.
	 * @return true if map was loaded successfully else it returns false
	 */
	public boolean loadMapData(String p_fileName, boolean p_createNewFile, boolean p_allowInvalid) {
	    // Clear the current map.
        d_gameEngine.getMapState().clear();

		List<String> l_lines = null;
		// try to read the file. If it does not exist, load a new map based on
		// p_createNewFile boolean.
		try {
			l_lines = readMap(p_fileName);

		} catch (IOException e) {
			if (p_createNewFile) {
				System.out.println("File not found. Loaded an empty map.");
                return true;

			} else {
			    System.out.println("error: file not found.");
                return false;
            }
		}

        for (int l_idx = 0; l_idx < l_lines.size(); l_idx++) {
            String currentLine = l_lines.get(l_idx);
            // ignore the comments in .map file.
            if (currentLine.startsWith(";")) {
                continue;
            }
            String beginningWord = currentLine.split(" ")[0];
            switch (beginningWord) {
                case "[continents]": {
                    l_idx = loadMapContinentsFromFile(l_idx, l_lines);
                    break;
                }
                case "[countries]": {
                    l_idx = loadMapCountriesFromFile(l_idx, l_lines);
                    break;
                }
                case "[borders]": {
                    l_idx = loadMapBordersFromFile(l_idx, l_lines);
                    break;
                }
                case "[files]":
                case "":
                default: {
                    break;
                }
            }
        }


		// Validate map
        if(!isMapValid()){
            if (!p_allowInvalid){
                System.out.println("THE MAP WAS NOT LOADED. INVALID MAP.");
                d_gameEngine.getMapState().clear();
                return false;
            }
            else {
                System.out.println("Map Loaded successfully but is not valid in its current state.");
                return true;
            }
        } else {
            System.out.println("Map Loaded successfully.");
            return true;
        }
	}

	/**
	 * Load the continents from map file.
	 * 
	 * @param p_idx   Index of the current line.
	 * @param p_lines List of all the lines in the map file.
	 * @return current index.
	 */
	public int loadMapContinentsFromFile(int p_idx, List<String> p_lines) {
		p_idx += 1;
		// l_continentId is not being used at. keeping it the next dev plan.
		int l_continentId = 1;
		while (checkSameBlock(p_idx, p_lines)) {
			String[] l_segments = p_lines.get(p_idx).split(" ");

			String l_continentName = l_segments[0];
			int l_continentArmy = Integer.parseInt(l_segments[1]);
			// Default color.
			String l_color = "#00000";
			if (l_segments.length == 3) {
				l_color = l_segments[2];
			}

			d_gameEngine.getMapState().getListOfContinents()
					.add(new ContinentModel(l_continentName, l_color, l_continentArmy));

			p_idx++;
			l_continentId++;
		}
		System.out.println("...Reading Continents. Total: " + d_gameEngine.getMapState().getListOfContinents().size());
		return p_idx;
	}

	/**
	 * Load the countries from map file.
	 * 
	 * @param p_idx   Index of the current line
	 * @param p_lines List of all the lines in the map file.
	 * @return current index
	 */
	public int loadMapCountriesFromFile(int p_idx, List<String> p_lines) {
		p_idx += 1;
		while (checkSameBlock(p_idx, p_lines)) {
			String[] l_segments = p_lines.get(p_idx).split(" ");

			int l_countryId = Integer.parseInt(l_segments[0]);
			String l_countryName = l_segments[1];
			int l_continentIdMap = Integer.parseInt(l_segments[2]);

			ContinentModel l_parentContinent = null;
			for (ContinentModel l_Continent : d_gameEngine.getMapState().getListOfContinents()) {
				int l_parentContinentOrder = d_gameEngine.getMapState().getListOfContinents().indexOf(l_Continent) + 1;
				if (l_parentContinentOrder == l_continentIdMap) {
					l_parentContinent = l_Continent;
					break;
				}
			}

			// default coordinates
			int l_x_coordinate = -1;
			int l_y_coordinate = -1;
			if (l_segments.length == 5) {
				l_x_coordinate = Integer.parseInt(l_segments[3]);
				l_y_coordinate = Integer.parseInt(l_segments[4]);
			}
			CoordinateModel l_coordinate = new CoordinateModel(l_x_coordinate, l_y_coordinate);

			CountryModel l_currentCountry = new CountryModel(l_countryId, l_countryName, l_parentContinent,
					l_coordinate);
			d_gameEngine.getMapState().getListOfCountries().add(l_currentCountry);
			// Add the country to the continent as well.
			l_parentContinent.getCountries().add(l_currentCountry);
			p_idx++;
		}
		System.out.println("...Reading Countries. Total: " + d_gameEngine.getMapState().getListOfCountries().size());
		return p_idx;
	}

	/**
	 * Load the borders from map file.
	 * 
	 * @param p_idx   Index of the current line
	 * @param p_lines List of all the lines in the map file.
	 * @return current index
	 */
	public int loadMapBordersFromFile(int p_idx, List<String> p_lines) {
		int totalCountries = d_gameEngine.getMapState().getListOfCountries().size();
		int[][] l_graph = new int[totalCountries][totalCountries];

		p_idx += 1;
		while (checkSameBlock(p_idx, p_lines)) {
			String[] l_segments = p_lines.get(p_idx).split(" ");
			// parse the string segments into integers.
			int[] l_intSegments = Arrays.stream(l_segments).mapToInt(Integer::parseInt).toArray();

			// slice the intSegments array [1:length]
			int l_start = 1;
			int l_end = l_intSegments.length;
			int[] l_neighbours = IntStream.range(l_start, l_end).map(i -> l_intSegments[i]).toArray();
			int l_countryId = l_intSegments[0];

			for (int neighbour : l_neighbours) {
				// creating only one way connections at the moment.
				// Assuming, 1-way connections are possible.
				l_graph[l_countryId - 1][neighbour - 1] = 1;
			}

			p_idx++;
		}

		d_gameEngine.getMapState().setBorderGraph(l_graph);
		System.out.println("...Reading borders.");
		return p_idx;
	}

	/**
	 * Checks whether the given line is a part of an existing block in the map file.
	 * The check is based on the fact that the lines in a block are not blank and
	 * contain at least 1 space.
	 *
	 * @param p_idx   Index of the current line.
	 * @param p_lines List of all the lines in the map.
	 * @return Boolean indicating whether the current line is a part of the existing
	 *         block.
	 */
	public boolean checkSameBlock(int p_idx, List<String> p_lines) {
		if (p_idx >= p_lines.size()) {
			return false;
		}
		String l_currentLine = p_lines.get(p_idx);
		return !l_currentLine.equals("") && l_currentLine.contains(" ");
	}

	/**
	 * Helper method for retrieving the CountryModel object from countryId which is
	 * the countryName.
	 * 
	 * @param p_id Name of the country.
	 * @return Country.
	 */
	public CountryModel getCountryById(String p_id) {
		for (CountryModel country : d_gameEngine.getMapState().getListOfCountries()) {
			if (country.getName().equals(p_id)) {
				return country;
			}
		}
		return null;
	}

	/**
	 * Helper method for retrieving the ContinentModel object from continentId which
	 * is the name.
	 * 
	 * @param p_id Name of the continent.
	 * @return Continent.
	 */
	public ContinentModel getContinentById(String p_id) {
		for (ContinentModel l_continent : d_gameEngine.getMapState().getListOfContinents()) {
			if (l_continent.getName().equals(p_id)) {
				return l_continent;
			}
		}
		return null;
	}

	/**
	 * editContinent takes in relevant commandParameters and calls the associated
	 * methods which are addContinent and removeContinent.
	 * 
	 * @param p_commandParameters List of command parameter.
	 */
	public void editContinent(String[] p_commandParameters) {
		int l_totalParameterSize = p_commandParameters.length;
		for (int l_i = 0; l_i < l_totalParameterSize; l_i++) {
			String l_command = p_commandParameters[l_i];
			switch (l_command) {
			case "-add": {
				l_i++;
				String l_continentId = p_commandParameters[l_i];
				l_i++;
				int l_continentValue = Integer.parseInt(p_commandParameters[l_i]);
				addContinent(l_continentId, l_continentValue);
				break;
			}

			case "-remove": {
				l_i++;
				String l_continentId = p_commandParameters[l_i];
				removeContinent(l_continentId);
				break;
			}

			default: {
				break;
			}
			}
		}
	}

	/**
	 * addContinent adds valid continents to the map. A continent may not be added
	 * if it already exists in the gameEngine.
	 * 
	 * @param p_continentId    Name of the continent.
	 * @param p_continentValue The army value of the continent.
	 */
	public void addContinent(String p_continentId, int p_continentValue) {
		// If the continent is already present.
		if (getContinentById(p_continentId) != null) {
			System.out.println("error: Unable to add continent with id: " + p_continentId + ". Already exists.");
			return;
		}

		// Create a new continent and add it to the list of continents.
		String l_color = "#00000";
		ContinentModel l_newContinent = new ContinentModel(p_continentId, l_color, p_continentValue);
		d_gameEngine.getMapState().getListOfContinents().add(l_newContinent);

		System.out.println("Continent with id: " + p_continentId + " added successfully.");
	}

	/**
	 * removerContinent removes a continent from the map.
	 * 
	 * @param p_continentId Name of the continent to be removed.
	 */
	private void removeContinent(String p_continentId) {
		// If the continent doesn't exist.
		ContinentModel l_continentToRemove = getContinentById(p_continentId);
		if (l_continentToRemove == null) {
			System.out.println("error: Unable to remove continent with id: " + p_continentId + ". Does not exists.");
			return;
		}

		// delete all the countries within this continent.
		// delete the continent from mapState.
		ArrayList<String> l_childCountries = new ArrayList<>();
		for (CountryModel l_country : l_continentToRemove.getCountries()) {
			l_childCountries.add(l_country.getName());
		}

		for (String l_countryName : l_childCountries) {
			removeCountry(l_countryName);
		}

		d_gameEngine.getMapState().getListOfContinents().remove(l_continentToRemove);
		System.out.println("Continent with id: " + p_continentId + "removed successfully.");
	}

	/**
	 * Remove a country from the map using the countryId which is countryName.
	 * 
	 * @param p_countryId Name of the country to be removed.
	 */
	public void removeCountry(String p_countryId) {
		CountryModel l_country = getCountryById(p_countryId);
		if (l_country == null) {
			System.out.println("error: Unable to remove continent with id: " + p_countryId + ". Does not exists.");
			return;
		}
		// remove country from the continent, the list of countries, borders.
		int l_countryOrder = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country);
		l_country.getContinent().getCountries().remove(l_country);
		d_gameEngine.getMapState().getListOfCountries().remove(l_country);
		removeBorder(l_countryOrder);
	}

	/**
	 * Remove the border associated with the given position. A new border matrix is
	 * created which does not include the country for which the border is to be
	 * removed.
	 * 
	 * @param p_countryPosition position of the country (in the country list) for
	 *                          which the border is to be removed.
	 */
	public void removeBorder(int p_countryPosition) {
		int[][] l_currentBorderGraph = d_gameEngine.getMapState().getBorderGraph();
		int l_newSize = l_currentBorderGraph[0].length - 1;

		int[][] l_newBorderGraph = new int[l_newSize][l_newSize];

		// Copy the new graph while avoiding the position of the country to be removed.

		for (int row = 0; row < p_countryPosition; row++) {
			for (int col = 0; col < p_countryPosition; col++) {
				l_newBorderGraph[row][col] = l_currentBorderGraph[row][col];
			}
		}

		for (int row = l_newSize - 1; row >= p_countryPosition; row--) {
			for (int col = l_newSize - 1; col >= p_countryPosition; col--) {
				l_newBorderGraph[row][col] = l_currentBorderGraph[row + 1][col + 1];
			}
		}

		d_gameEngine.getMapState().setBorderGraph(l_newBorderGraph);
	}

	/**
	 * editCountry takes in relevant commandParameters and calls the associated
	 * methods which are addCountry and removeCountry.
	 * 
	 * @param p_commandParameters List of command parameter.
	 */
	public void editCountry(String[] p_commandParameters) {
		int l_totalParameterSize = p_commandParameters.length;
		for (int l_i = 0; l_i < l_totalParameterSize; l_i++) {
			String l_command = p_commandParameters[l_i];
			switch (l_command) {
			case "-add": {
				l_i++;
				String l_countryId = p_commandParameters[l_i];
				l_i++;
				String l_continentId = p_commandParameters[l_i];
				addCountry(l_countryId, l_continentId);
				break;
			}

			case "-remove": {
				l_i++;
				String l_countryId = p_commandParameters[l_i];
				removeCountry(l_countryId);
				break;
			}

			default: {
				break;
			}
			}
		}
	}

	/**
	 * Add a new country to the map using Country name and the continent name.
	 * 
	 * @param p_countryId   Name of the country.
	 * @param p_continentId Name of the continent.
	 */
	public void addCountry(String p_countryId, String p_continentId) {
		CountryModel l_countryToAdd = getCountryById(p_countryId);
		ContinentModel l_parentContinent = getContinentById(p_continentId);
		if (l_countryToAdd != null) {
			System.out.println("error: Unable to add country with id: " + p_countryId + ". Already exists.");
			return;
		}

		if (l_parentContinent == null) {
			System.out.println("error: Unable to add country with id: " + p_countryId + ". CONTINENT does not exist.");
			return;
		}

		allocateSpaceNewCountry(p_countryId, l_parentContinent);
		System.out.println("Country with id: " + p_countryId + " added successfully.");
	}

	/**
	 * Helper function to create a new country. It creates a new country, updates
	 * the border graph, and adds the country to the list of countries and the
	 * parent continent.
	 * 
	 * @param p_countryId       Name of the country to be created.
	 * @param p_parentContinent Continent to which the country is to be added.
	 */
	public void allocateSpaceNewCountry(String p_countryId, ContinentModel p_parentContinent) {
		CountryModel l_countryToAdd = getCountryById(p_countryId);
		if (l_countryToAdd != null) {
			System.out.println("error: Unable to add country with id: " + p_countryId + ". Already exists.");
			return;
		}
		int insertionOrder = d_gameEngine.getMapState().getBorderGraph()[0].length + 1;
		l_countryToAdd = new CountryModel(insertionOrder, p_countryId, p_parentContinent, new CoordinateModel(-1, -1));

		d_gameEngine.getMapState().getListOfCountries().add(l_countryToAdd);
		p_parentContinent.getCountries().add(l_countryToAdd);

		// update the graph.
		int[][] l_currentBorderGraph = d_gameEngine.getMapState().getBorderGraph();
		int[][] l_newBorderGraph = new int[insertionOrder][insertionOrder];

		for (int row = 0; row < insertionOrder - 1; row++) {
			l_newBorderGraph[row] = Arrays.copyOf(l_currentBorderGraph[row], insertionOrder);
			l_newBorderGraph[insertionOrder - 1][row] = 0;
		}

		for (int col = 0; col < insertionOrder; col++) {
			l_newBorderGraph[insertionOrder - 1][col] = 0;
		}

		d_gameEngine.getMapState().setBorderGraph(l_newBorderGraph);
	}

	/**
	 * editNeighbor takes in relevant commandParameters and calls the associated
	 * methods which are addNeighbor and removeNeighbor.
	 * 
	 * @param p_commandParameters List of command parameter.
	 */
	public void editNeighbor(String[] p_commandParameters) {
		int l_totalParameterSize = p_commandParameters.length;
		for (int l_i = 0; l_i < l_totalParameterSize; l_i++) {
			String l_command = p_commandParameters[l_i];
			switch (l_command) {
			case "-add": {
				l_i++;
				String l_countryId = p_commandParameters[l_i];
				l_i++;
				String l_neighborId = p_commandParameters[l_i];
				addNeighbor(l_countryId, l_neighborId);
				break;
			}

			case "-remove": {
				l_i++;
				String l_countryId = p_commandParameters[l_i];
				l_i++;
				String l_neighborId = p_commandParameters[l_i];
				removeNeighbor(l_countryId, l_neighborId);
				break;
			}

			default: {
				break;
			}
			}
		}
	}

	/**
	 * Add a border between the give countries.
	 * 
	 * @param p_countryId  Name of the country.
	 * @param p_neighborId Name of the neighbor country.
	 */
	public void addNeighbor(String p_countryId, String p_neighborId) {
		// check countries.
		CountryModel l_country = getCountryById(p_countryId);
		CountryModel l_neighbor = getCountryById(p_neighborId);

		if (l_country == null) {
			System.out.println("error adding neighbors. Country with id: " + p_countryId + " not found.");
			return;
		}

		if (l_neighbor == null) {
			System.out.println("error adding neighbors. Country with id: " + p_neighborId + " not found.");
			return;
		}

		// get positions in the graph.
		int l_countryPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country);
		int l_neighborPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_neighbor);

		d_gameEngine.getMapState().getBorderGraph()[l_countryPosition][l_neighborPosition] = 1;

		System.out.println("Added " + p_neighborId + " as a neighbor of " + p_countryId + ".");
	}

	/**
	 * Remove the border between the given countries.
	 * 
	 * @param p_countryId  Name of the Country.
	 * @param p_neighborId Name of the neighbor country.
	 */
	public void removeNeighbor(String p_countryId, String p_neighborId) {
		// check countries.
		CountryModel l_country = getCountryById(p_countryId);
		CountryModel l_neighbor = getCountryById(p_neighborId);

		if (l_country == null) {
			System.out.println("error removing neighbors status. Country with id: " + p_countryId + " not found.");
			return;
		}

		if (l_neighbor == null) {
			System.out.println("error removing neighbors status. Country with id: " + p_neighborId + " not found.");
			return;
		}

		// get positions in the graph.
		int l_countryPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country);
		int l_neighborPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_neighbor);

		d_gameEngine.getMapState().getBorderGraph()[l_countryPosition][l_neighborPosition] = 0;

		System.out.println("Removed " + p_neighborId + " as a neighbor of " + p_countryId + ".");
	}

	/**
	 * Display the contents of the map.
	 */
	public void showMap() {
		System.out.println("MAP DETAILS");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.printf("%5s %15s %10s %15s", "INDEX", "CONTINENT", "ARMY", "COLOR");
		System.out.println("");
		System.out.println("-----------------------------------------------------------------------------");
		for (ContinentModel l_continent : d_gameEngine.getMapState().getListOfContinents()) {
			int l_continentOrder = d_gameEngine.getMapState().getListOfContinents().indexOf(l_continent) + 1;
			String l_name = l_continent.getName();
			String l_color = l_continent.getColor();
			int l_army = l_continent.getArmy();
			int totalCountries = l_continent.getCountries().toArray().length;
			System.out.printf("%5s %15s %10s %15s", l_continentOrder, l_name, l_army, l_color, totalCountries);
			System.out.println("");
			System.out.println("\t\t Countries:");
			// countries
			ArrayList<CountryModel> l_countries = l_continent.getCountries();
			for (CountryModel l_country : l_countries) {
				printCountryDetails(l_country);
			}
			System.out.println("\t----------------------------------------------------------");
		}
	}

	/**
	 * Helper method for printing only the country details.
	 * 
	 * @param p_country The country for which the details are to be printed.
	 */
	private void printCountryDetails(CountryModel p_country) {
		int l_position = d_gameEngine.getMapState().getListOfCountries().indexOf(p_country) + 1;
		String l_countryName = p_country.getName();
		String l_countryStr = "";
		l_countryStr += l_position + " " + l_countryName + " ";

		String l_neighborStr = "";
		l_neighborStr += "Neighbors: ";
		ArrayList<CountryModel> l_neighborCountries = getNeighbors(p_country);
		for (CountryModel l_neighbor : l_neighborCountries) {
			l_neighborStr += l_neighbor.getName() + " | ";
		}
		System.out.printf("\t\t\t%-30s %30s\n", l_countryStr, l_neighborStr);
	}
	
	/**
	 * Display the contents of the map along with assignment of player and count of armies
	 * in each countries.
	 */
	public void showMapForGamePlay() {
		String l_listOfCountries = "";
		System.out.println("MAP DETAILS");
		System.out.println("-----------------------------------------------------------------------------");
		System.out.print("\tCONTINENT VALUE\tCONTINENT");
		System.out.println("");
		System.out.println("-----------------------------------------------------------------------------");
		for (ContinentModel l_continent : d_gameEngine.getMapState().getListOfContinents()) {
			String l_name = l_continent.getName();
			int l_army = l_continent.getArmy();
			System.out.print("\t" + l_army + "\t" + l_name);
			System.out.println("");
			System.out.println("Countries map:");
			// countries
			ArrayList<CountryModel> l_countries = l_continent.getCountries();
			l_listOfCountries += printCountryMapForGamePlay(l_countries);
			System.out.println("\t----------------------------------------------------------");
		}
		System.out.println("");
		System.out.println(l_listOfCountries);
		System.out.println("\t----------------------------------------------------------");
	}
	
	/**
	 * Helper method for printing only the countries map to facilitate game play
	 * 
	 * @param p_countries The countries for which the mapping is to be printed.
	 * @return Concatenated countries names with players name who owns it and no. of armies
	 */
	private String printCountryMapForGamePlay(ArrayList<CountryModel> p_countries) {
		String l_listOfCountries = "";
		String l_topLabels = "\t";
		String l_sideLabels = "";
		ArrayList<Integer> l_neighbourMap = new ArrayList<Integer>();
		for (CountryModel l_country : p_countries) {
			ArrayList<Integer> l_neighbourXMap = new ArrayList<Integer>(Collections.nCopies(l_neighbourMap.size(),0));
			l_sideLabels += (l_country.getCountryIdMap() + "\t");
			ArrayList<CountryModel> l_neighborCountries = getNeighbors(l_country);
			for(CountryModel l_neighbourCountry : l_neighborCountries) {
				int l_index = l_neighbourMap.indexOf(l_neighbourCountry.getCountryIdMap());
			    if(l_index >= 0) {
			    	l_neighbourXMap.set(l_index, 1);
			    } else {
			    	l_topLabels += (l_neighbourCountry.getCountryIdMap() + "\t");
					l_neighbourMap.add(l_neighbourCountry.getCountryIdMap());
					l_neighbourXMap.add(1);
			    }
			}
			for(int l_mapIndex = 0; l_mapIndex < l_neighbourXMap.size() - 1; l_mapIndex++) {
				if(l_neighbourXMap.get(l_mapIndex) == 0) {
					l_sideLabels += "\t";
				} else {
					l_sideLabels += "\tX";
				}
			}
			l_sideLabels += "\n";
			l_listOfCountries += (l_country.getCountryIdMap() + ": " + l_country.getName() + "\t\t\t\tArmies:" +l_country.getArmies());
			if(l_country.getOwner() == null) {
				l_listOfCountries += ("\tOwned by: NO ONE\n");
			} else {
				l_listOfCountries += ("\tOwned by:" +l_country.getOwner().getName()+ "(Reinforcements - " +l_country.getOwner().getReinforcementsArmies()+ ")\n");
			}
		}
		System.out.println(l_topLabels);
		System.out.println(l_sideLabels);
		return l_listOfCountries;
	}

	/**
	 * Write the map to file.
	 * 
	 * @param fileName Filename to which the map is to be written.
	 * @return true if map was saved successful else returns false
	 */
	public boolean saveMap(String fileName) {
		if (!isMapValid()) {
			System.out.println("savemap command failed: Map not valid");
			return false;
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);
			writer.write("; custom map, saved by the us.\n\n\n");

			writer.write("[continents]\n");
			saveContinents(writer);

			writer.write("[countries]\n");
			saveCountries(writer);

			writer.write("[borders]\n");
			saveBorders(writer);

			writer.close();
			
			return true;

		} catch (IOException e) {
			System.out.println("Error while writing to file. Invalid filename.");
			return false;
		}
	}

	/**
	 * A helper method to validate the map
	 * 
	 * @return true if the map is valid, false if not valid
	 */
	public boolean isMapValid() {
		if (!validateMap()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Write the Continent data in the map file.
	 * 
	 * @param p_writer FileWriter object.
	 * @throws IOException Input-output related exceptions while writing to file.
	 */
	public void saveContinents(FileWriter p_writer) throws IOException {
		ArrayList<ContinentModel> l_continents = d_gameEngine.getMapState().getListOfContinents();
		for (ContinentModel l_continent : l_continents) {
			String l_continentStr = l_continent.getName() + " " + l_continent.getArmy() + " " + l_continent.getColor();
			p_writer.write(l_continentStr + "\n");
		}

		p_writer.write("\n");
		System.out.println("...saved continents to file.");
	}

	/**
	 * Write Country data in the map file.
	 * 
	 * @param p_writer FileWriter object.
	 * @throws IOException Input-output related exceptions while writing to file.
	 */
	public void saveCountries(FileWriter p_writer) throws IOException {
		ArrayList<CountryModel> l_countries = d_gameEngine.getMapState().getListOfCountries();
		for (CountryModel l_country : l_countries) {
			int l_countryOrd = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country) + 1;
			int l_continentOrd = d_gameEngine.getMapState().getListOfContinents().indexOf(l_country.getContinent()) + 1;
			CoordinateModel coordinates = l_country.getCoordinate();
			String l_countryStr = l_countryOrd + " " + l_country.getName() + " " + l_continentOrd + " "
					+ coordinates.getX() + " " + coordinates.getY();
			p_writer.write(l_countryStr + "\n");
		}

		p_writer.write("\n");
		System.out.println("...saved countries to file.");
	}

	/**
	 * Write border data in the map file.
	 * 
	 * @param p_writer FileWriter object.
	 * @throws IOException Input-output related exceptions while writing to file.
	 */
	public void saveBorders(FileWriter p_writer) throws IOException {
		ArrayList<CountryModel> l_countries = d_gameEngine.getMapState().getListOfCountries();

		for (CountryModel l_country : l_countries) {
			String l_countryStr = "";
			int l_countryPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country) + 1;
			l_countryStr += l_countryPosition + " ";
			ArrayList<CountryModel> l_neighbors = getNeighbors(l_country);
			for (CountryModel l_neighbor : l_neighbors) {
				int l_neighborPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_neighbor) + 1;
				l_countryStr += l_neighborPosition + " ";
			}
			p_writer.write(l_countryStr + "\n");
		}

		p_writer.write("\n");
		System.out.println("...saved borders to file.");
	}

	/**
	 * Get the list of neighboring countries of the given country.
	 * 
	 * @param p_country The country object for which we need to get the neighbors
	 * @return A list of countries which may be empty.
	 */
	public ArrayList<CountryModel> getNeighbors(CountryModel p_country) {
		ArrayList<CountryModel> l_neighbors = new ArrayList<>();
		int l_countryPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(p_country);

		int[][] borderGraph = d_gameEngine.getMapState().getBorderGraph();

		for (int l_position = 0; l_position < borderGraph[l_countryPosition].length; l_position++) {
			if (borderGraph[l_countryPosition][l_position] == 1) {
				l_neighbors.add(d_gameEngine.getMapState().getListOfCountries().get(l_position));
			}
		}

		return l_neighbors;
	}

	/**
	 * A utility method to read the contents from file.
	 * 
	 * @param l_fileName The complete path of the file.
	 * @return A List of all the lines in the file.
	 *
	 * @throws IOException Exception while reading the map file.
	 */
	public List<String> readMap(String l_fileName) throws IOException {
		List<String> l_lines;
		Path l_path = Paths.get(l_fileName);

		l_lines = Files.readAllLines(l_path, StandardCharsets.UTF_8);
		return l_lines;
	}

	/**
	 * Validates Map Data
	 * <ul>
	 * <li>Checks if there are any Duplicate Continents</li>
	 * <li>Checks if there are any Duplicate Countries</li>
	 * <li>Checks if the borders are defined in the map or not</li>
	 * <li>Checks if the map is a connected graph</li>
	 * <li>Checks if each continent as itself is a connected graph</li>
	 * </ul>
	 * 
	 * @return true if the map is valid
	 */
	public boolean validateMap() {
		ArrayList<ContinentModel> l_listOfContinents = d_gameEngine.getMapState().getListOfContinents();
		ArrayList<CountryModel> l_listOfCountries = d_gameEngine.getMapState().getListOfCountries();
		int[][] l_bordergraph = d_gameEngine.getMapState().getBorderGraph();
		boolean l_countriesAreVaid = validateCountries(l_listOfCountries);
		boolean l_continentsAreVaid = validateContinents(l_listOfContinents);
		boolean l_bordersAreVaid = validateBorders(l_bordergraph);
		int isMyGraphconnected = 0;
		for (int i = 0; i < l_bordergraph.length; i++) {
			boolean connectedCheck = validateGraph(l_bordergraph, i, false);
			if (connectedCheck == false) {
				isMyGraphconnected = +1;
			}
		}
		if (isMyGraphconnected == 0) {
			System.out.println("Validation Check: The map is a connected graph ");
		} else {
			System.out.println("Validation Check Failed: The map is not a connected graph ");
		}

		boolean l_isCountryAConnectedGraph = isCountryAConnectedGraph(l_listOfContinents, l_listOfCountries,
				l_bordergraph);
		if (l_countriesAreVaid == false || l_continentsAreVaid == false || l_bordersAreVaid == false
				|| isMyGraphconnected > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks whether the map contains at least one country and if there are any
	 * duplicate countries
	 * 
	 * @param p_listOfCountries The list of countries defined in the map
	 * @return a boolean to convey whether the map has valid number of countries or
	 *         not
	 */
	public boolean validateCountries(ArrayList<CountryModel> p_listOfCountries) {
		boolean areCountriesValid = true;
		ArrayList<String> l_Countries = new ArrayList<String>();
		for (int l_idx = 0; l_idx < p_listOfCountries.size(); l_idx++) {
			l_Countries.add(p_listOfCountries.get(l_idx).getName());
		}
		if (p_listOfCountries.size() > 0) {
			areCountriesValid = true;
			if (hasDuplicates(l_Countries) == true) {
				System.out.println(
						"Validation Check Failed: A country should not be duplicated or A Country cannot be assigned to multiple continents");
				areCountriesValid = false;
			} else {
				System.out.println("Validation Check: No duplicates countries found");
				areCountriesValid = true;
			}
		} else {
			System.out.println("Validation Check Failed: Map must contain at least one country");
			areCountriesValid = false;
		}
		return areCountriesValid;
	}

	/**
	 * Checks whether the map contains at least one country and if there are any
	 * duplicate continents
	 * 
	 * @param p_listOfContinents the complete list of continents defined in the map
	 * @return a boolean to convey whether the map has valid number of continents or
	 *         not
	 */
	public boolean validateContinents(ArrayList<ContinentModel> p_listOfContinents) {
		boolean l_areContinentsValid = true;
		ArrayList<String> l_Continents = new ArrayList<String>();
		for (int l_idx = 0; l_idx < p_listOfContinents.size(); l_idx++) {
			l_Continents.add(p_listOfContinents.get(l_idx).getName());
		}
		if (p_listOfContinents.size() > 0) {
			l_areContinentsValid = true;
			if (hasDuplicates(l_Continents) == true) {
				System.out.println("Validation Check Failed: A map should not have two continents with the same name");
				l_areContinentsValid = false;
			} else {
				System.out.println("Validation Check: No duplicates continents found");
				l_areContinentsValid = true;
			}
		} else {
			System.out.println("Validation Check Failed: Map must contain at least one continent");
			l_areContinentsValid = false;
		}
		// the below check is to see if continent has countries or is empty
		if (isEmptyContinent(p_listOfContinents) == true) {
			l_areContinentsValid = false;
		} else {
			l_areContinentsValid = true;
		}		
		return l_areContinentsValid;
	}
	
	/**
	 * Checks whether any of the continents is empty.
	 * 
	 * @param p_listOfContinents the complete list of continents defined in the map
	 * @return true if any of the continent is empty, false if all continents have at
	 *         least one country in them
	 */
	public boolean isEmptyContinent(ArrayList<ContinentModel> p_listOfContinents) {
		int emptyCheck = 0;
		for (ContinentModel continent : p_listOfContinents) {
			ArrayList<CountryModel> countriesInContinent = continent.getCountries();
			if (countriesInContinent.size() == 0) {
				System.out.println(
						"Validation Check Failed: There are no countries in the continent " + continent.getName());
				emptyCheck++;
			}
		}
		if (emptyCheck > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if the given array list has duplicates
	 * 
	 * @param p_mapData a array list of strings in which duplicates has to be
	 *                  identified
	 * @return a boolean specifying whether the given array has duplicates or not
	 */
	public boolean hasDuplicates(ArrayList<String> p_mapData) {
		boolean haveDuplicates = true;
		Set<String> l_mapSet = new HashSet<String>(p_mapData);
		if (l_mapSet.size() < p_mapData.size()) {
			haveDuplicates = true;
		} else {
			haveDuplicates = false;
		}
		return haveDuplicates;
	}

	/**
	 * Check to see if borders are defined in the map
	 * 
	 * @param p_borderGraph a association graph specifying the connection between
	 *                      the countries
	 * @return a boolean specifying whether borders are defined or not
	 */
	public boolean validateBorders(int[][] p_borderGraph) {
		if (p_borderGraph.length > 0) {
			System.out.println("Validation Check: Borders are defined in the map");
			return true;
		} else {
			System.out.println("Validation Check Failed: A Map must contain border associations between the countries");
			return false;
		}
	}

	/**
	 * Checks whether the graph is connected or not
	 * 
	 * @param p_adjacencyMatrix A two dimensional array representation of a graph
	 * @param p_source           The node or position in matrix for which we need to
	 *                           check adjacency for
	 * @param isSubGraph         true or false based on what kind of graph the
	 *                           method should validate
	 * @return a boolean based on whether the graph is connected or not
	 */
	public boolean validateGraph(int p_adjacencyMatrix[][], int p_source, boolean isSubGraph) {
		int l_numberOfNodes = p_adjacencyMatrix[p_source].length - 1;
		int[] l_visited = new int[l_numberOfNodes + 1];
		Stack<Integer> stack = new Stack<Integer>();
		int i, element;
		l_visited[p_source] = 1;
		stack.push(p_source);
		while (!stack.isEmpty()) {
			element = stack.pop();
			if (isSubGraph == false) {
				i = 1;
			} else {
				i = 0;
			}
			while (i <= l_numberOfNodes) {
				if (p_adjacencyMatrix[element][i] == 1 && l_visited[i] == 0) {
					stack.push(i);
					l_visited[i] = 1;
				}
				i++;
			}
		}

		int count = 0;
		for (int v = 1; v <= l_numberOfNodes; v++)
			if (l_visited[v] == 1) {
				count++;
			}

		if (count == l_numberOfNodes) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * To check if all the countries in a continent form a connected graph among
	 * themselves
	 * 
	 * @param p_listOfContinents The list of countries defined in the map
	 * @param p_listOfCountries  The complete list of continents defined in the map
	 * @param p_graph            a association graph specifying the connection
	 *                           between the countries
	 * @return true if map is connected graph else it returns false
	 */
	public boolean isCountryAConnectedGraph(ArrayList<ContinentModel> p_listOfContinents,
			ArrayList<CountryModel> p_listOfCountries, int[][] p_graph) {
		int disconnectedContinents = 0;
		for (ContinentModel continent : p_listOfContinents) {
			ArrayList<CountryModel> CM = continent.getCountries();
			ArrayList<Integer> countryIds = new ArrayList<Integer>();
			for (CountryModel l_country : continent.getCountries()) {
				countryIds.add(l_country.getCountryIdMap());
			}
			;
			int countriesInThisContinent = countryIds.size();
			int[][] l_subgraph = new int[countriesInThisContinent][countriesInThisContinent];
			int i = 0;
			for (int id : countryIds) {

				for (int j = 0; j < countriesInThisContinent; j++) {

					l_subgraph[i][j] = p_graph[id - 1][countryIds.get(j) - 1];
				}
				i++;
			}
			Stack<Integer> stack = new Stack<Integer>();
			for (int countriesInContinent = 0; countriesInContinent < l_subgraph.length; countriesInContinent++) {
				boolean isDirected = validateGraph(l_subgraph, countriesInContinent, true);
				if (isDirected == false) {
					disconnectedContinents = +1;
					int countryId = countryIds.get(countriesInContinent);
					String country = p_listOfCountries.get(countryId - 1).getName();
					System.out.println("Validation Check Failed: The country " + country + " in continent "
							+ continent.getName() + " does not form a connected graph");
				}
			}
		}
		if (disconnectedContinents == 0) {
			System.out.println("Validation Check: All the countries form a directed graph within their continent");
			return true;
		} else {
			System.out.println(
					"Validation Check Failed: Invalid Map,countries in a continent has to form a directed graph");
			return false;
		}
	}

}