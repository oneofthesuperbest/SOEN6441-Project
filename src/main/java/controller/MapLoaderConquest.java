package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;

/**
 * This class is used to load warzone maps
 */
public class MapLoaderConquest {
	private GameEngine d_gameEngine;
	HashMap<String, String[]> d_countryBorders = new HashMap<String, String[]>();
	HashMap<String, Integer> d_countryIds = new HashMap<String, Integer>();

	/**
	 * Create a new map controller with the specified GameEngine.
	 * 
	 * @param p_gameEngine A GameEngine object which is populated with the map data.
	 */
	public MapLoaderConquest(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
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
			String l_currentLine = l_lines.get(l_idx);
			String l_beginningWord = l_currentLine.split(" ")[0];
			switch (l_beginningWord) {
				case "[Continents]": {
					l_idx = loadMapContinentsFromFile(l_idx, l_lines);
					break;
				}
				case "[Territories]": {
					l_idx = loadMapCountriesFromFile(l_idx, l_lines);
					break;
				}
				case "[Map]":
				case "":
				default: {
					break;
				}
			}
		}
		loadMapBorders();

		// Validate map
		MapValidator l_mapValidator = new MapValidator(this.d_gameEngine);
		if (!l_mapValidator.isMapValid()) {
			if (!p_allowInvalid) {
				System.out.println("THE MAP WAS NOT LOADED. INVALID MAP.");
				d_gameEngine.getMapState().clear();
				return false;
			} else {
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
		while (checkSameBlock(p_idx, p_lines)) {
			String[] l_segments = p_lines.get(p_idx).split("=");

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
		}
		System.out.println("...Reading Continents. Total: " + d_gameEngine.getMapState().getListOfContinents().size());
		return p_idx;
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
	 * Load the countries from map file.
	 * 
	 * @param p_idx   Index of the current line
	 * @param p_lines List of all the lines in the map file.
	 * @return current index
	 */
	public int loadMapCountriesFromFile(int p_idx, List<String> p_lines) {
		p_idx += 1;
		int l_countryIdCount = 1;
		while (checkSameBlock(p_idx, p_lines)) {
			if (p_lines.get(p_idx).equals("")) {
				p_idx++;
				continue;
			}
			String[] l_segments = p_lines.get(p_idx).split(",");

			String l_countryName = l_segments[0];
			d_countryIds.put(l_countryName, (l_countryIdCount - 1));
			int l_countryId = l_countryIdCount++;

			ContinentModel l_parentContinent = null;
			for (ContinentModel l_Continent : d_gameEngine.getMapState().getListOfContinents()) {
				if (l_Continent.getName().equals(l_segments[3])) {
					l_parentContinent = l_Continent;
					break;
				}
			}

			// default coordinates
			int l_x_coordinate = -1;
			int l_y_coordinate = -1;
			CoordinateModel l_coordinate = new CoordinateModel(l_x_coordinate, l_y_coordinate);

			CountryModel l_currentCountry = new CountryModel(l_countryId, l_countryName, l_parentContinent,
					l_coordinate);
			d_gameEngine.getMapState().getListOfCountries().add(l_currentCountry);
			// Add the country to the continent as well.
			l_parentContinent.getCountries().add(l_currentCountry);
			
			d_countryBorders.put(l_countryName, l_segments);
			p_idx++;
		}
		System.out.println("...Reading Countries. Total: " + d_gameEngine.getMapState().getListOfCountries().size());
		return p_idx;
	}

	/**
	 * Load the borders from map file.
	 */
	public void loadMapBorders() {
		int l_totalCountries = d_gameEngine.getMapState().getListOfCountries().size();
		int[][] l_graph = new int[l_totalCountries][l_totalCountries];
		
		int l_currentCountryId = 0;
		for(@SuppressWarnings("rawtypes") Map.Entry l_country : d_countryBorders.entrySet()) {
			String[] l_neighbourMap = ((String[]) l_country.getValue());
			for(int l_n = 4; l_n < l_neighbourMap.length; l_n++) {
				int l_neighbourId = d_countryIds.get(l_neighbourMap[l_n]);
				l_graph[l_currentCountryId][l_neighbourId] = 1;
			}
			l_currentCountryId++;
		}
		
		d_gameEngine.getMapState().setBorderGraph(l_graph);
		System.out.println("...Reading borders.");
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
		return true;
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
		MapController l_mapController = new MapController(d_gameEngine);
		ArrayList<CountryModel> l_countries = d_gameEngine.getMapState().getListOfCountries();

		for (CountryModel l_country : l_countries) {
			String l_countryStr = "";
			int l_countryPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_country) + 1;
			l_countryStr += l_countryPosition + " ";
			ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_country);
			for (CountryModel l_neighbor : l_neighbors) {
				int l_neighborPosition = d_gameEngine.getMapState().getListOfCountries().indexOf(l_neighbor) + 1;
				l_countryStr += l_neighborPosition + " ";
			}
			p_writer.write(l_countryStr + "\n");
		}

		p_writer.write("\n");
		System.out.println("...saved borders to file.");
	}
}
