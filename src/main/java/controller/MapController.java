package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
	 * Helper method for retrieving the CountryModel object from countryId which is
	 * the countryName.
	 * 
	 * @param p_id Name of the country.
	 * @return Country.
	 */
	public CountryModel getCountryById(String p_id) {
		for (CountryModel l_country : d_gameEngine.getMapState().getListOfCountries()) {
			if (l_country.getName().equals(p_id)) {
				return l_country;
			}
		}
		return null;
	}

	/**
	 * Helper method for retrieving the ContinentModel object from continentId which
	 * is the name.
	 * 
	 * @param p_id Name of the continent.
	 * @return Continent object.
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
		System.out.println("Continent with id: " + p_continentId + " removed successfully.");
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
		System.out.println("Country with id: " + p_countryId + " was removed successfully.");
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

		for (int l_row = 0; l_row < p_countryPosition; l_row++) {
			for (int l_col = 0; l_col < p_countryPosition; l_col++) {
				l_newBorderGraph[l_row][l_col] = l_currentBorderGraph[l_row][l_col];
			}
		}

		for (int l_row = l_newSize - 1; l_row >= p_countryPosition; l_row--) {
			for (int l_col = l_newSize - 1; l_col >= p_countryPosition; l_col--) {
				l_newBorderGraph[l_row][l_col] = l_currentBorderGraph[l_row + 1][l_col + 1];
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
		int l_insertionOrder = d_gameEngine.getMapState().getBorderGraph().length + 1;
		l_countryToAdd = new CountryModel(l_insertionOrder, p_countryId, p_parentContinent,
				new CoordinateModel(-1, -1));

		d_gameEngine.getMapState().getListOfCountries().add(l_countryToAdd);
		p_parentContinent.getCountries().add(l_countryToAdd);

		// update the graph.
		int[][] l_currentBorderGraph = d_gameEngine.getMapState().getBorderGraph();
		int[][] l_newBorderGraph = new int[l_insertionOrder][l_insertionOrder];

		for (int l_row = 0; l_row < l_insertionOrder - 1; l_row++) {
			l_newBorderGraph[l_row] = Arrays.copyOf(l_currentBorderGraph[l_row], l_insertionOrder);
			l_newBorderGraph[l_insertionOrder - 1][l_row] = 0;
		}

		for (int l_col = 0; l_col < l_insertionOrder; l_col++) {
			l_newBorderGraph[l_insertionOrder - 1][l_col] = 0;
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
			int l_totalCountries = l_continent.getCountries().toArray().length;
			System.out.printf("%5s %15s %10s %15s", l_continentOrder, l_name, l_army, l_color, l_totalCountries);
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
	 * Display the contents of the map along with assignment of player and count of
	 * armies in each countries.
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
	 * @return Concatenated countries names with players name who owns it and no. of
	 *         armies
	 */
	private String printCountryMapForGamePlay(ArrayList<CountryModel> p_countries) {
		String l_listOfCountries = "";
		String l_topLabels = "\t";
		String l_sideLabels = "";
		ArrayList<Integer> l_neighbourMap = new ArrayList<Integer>();
		for (CountryModel l_country : p_countries) {
			ArrayList<Integer> l_neighbourXMap = new ArrayList<Integer>(Collections.nCopies(l_neighbourMap.size(), 0));
			l_sideLabels += (l_country.getCountryIdMap() + "\t");
			ArrayList<CountryModel> l_neighborCountries = getNeighbors(l_country);
			for (CountryModel l_neighbourCountry : l_neighborCountries) {
				int l_index = l_neighbourMap.indexOf(l_neighbourCountry.getCountryIdMap());
				if (l_index >= 0) {
					l_neighbourXMap.set(l_index, 1);
				} else {
					l_topLabels += (l_neighbourCountry.getCountryIdMap() + "\t");
					l_neighbourMap.add(l_neighbourCountry.getCountryIdMap());
					l_neighbourXMap.add(1);
				}
			}
			for (int l_mapIndex = 0; l_mapIndex < l_neighbourXMap.size(); l_mapIndex++) {
				if (l_neighbourXMap.get(l_mapIndex) == 0) {
					l_sideLabels += "\t";
				} else {
					l_sideLabels += "X\t";
				}
			}
			l_sideLabels += "\n";
			l_listOfCountries += (l_country.getCountryIdMap() + ": " + l_country.getName() + "\t\t\t\tArmies:"
					+ l_country.getArmies());
			if (l_country.getOwner() == null) {
				l_listOfCountries += ("\tOwned by: NO ONE\n");
			} else {
				String l_cards = "| ";
				ArrayList<Integer> l_cardsList = l_country.getOwner().getCards();
				for (int l_card : l_cardsList) {
					if (l_card == 0) {
						l_cards += "bomb | ";
					} else if (l_card == 1) {
						l_cards += "blockade | ";
					} else if (l_card == 2) {
						l_cards += "airlift | ";
					} else if (l_card == 3) {
						l_cards += "negotiate | ";
					}
				}
				l_listOfCountries += ("\tOwned by:" + l_country.getOwner().getName() + "(Cards: " + l_cards
						+ ") (Reinforcements - " + l_country.getOwner().getReinforcementsArmies() + ")\n");
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
		MapValidator l_mapValidator = new MapValidator(this.d_gameEngine);
		if (!l_mapValidator.isMapValid()) {
			System.out.println("savemap command failed: Map not valid");
			return false;
		}
		FileWriter l_writer = null;
		try {
			l_writer = new FileWriter(fileName);
			l_writer.write("; custom map, saved by the us.\n\n\n");

			l_writer.write("[continents]\n");
			saveContinents(l_writer);

			l_writer.write("[countries]\n");
			saveCountries(l_writer);

			l_writer.write("[borders]\n");
			saveBorders(l_writer);

			l_writer.close();

			return true;

		} catch (IOException e) {
			System.out.println("Error while writing to file. Invalid filename.");
			return false;
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
			CoordinateModel l_coordinates = l_country.getCoordinate();
			String l_countryStr = l_countryOrd + " " + l_country.getName() + " " + l_continentOrd + " "
					+ l_coordinates.getX() + " " + l_coordinates.getY();
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

		int[][] l_borderGraph = d_gameEngine.getMapState().getBorderGraph();

		for (int l_position = 0; l_position < l_borderGraph[l_countryPosition].length; l_position++) {
			if (l_borderGraph[l_countryPosition][l_position] == 1) {
				l_neighbors.add(d_gameEngine.getMapState().getListOfCountries().get(l_position));
			}
		}

		return l_neighbors;
	}

}