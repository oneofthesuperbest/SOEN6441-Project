package controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;

/**
 * This class is used to load warzone maps
 */
public class MapLoaderConquest {
	private GameEngine d_gameEngine;

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
			// ignore the comments in .map file.
			if (l_currentLine.startsWith(";")) {
				continue;
			}
			String l_beginningWord = l_currentLine.split(" ")[0];
			switch (l_beginningWord) {
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
		int l_totalCountries = d_gameEngine.getMapState().getListOfCountries().size();
		int[][] l_graph = new int[l_totalCountries][l_totalCountries];

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

			for (int l_neighbour : l_neighbours) {
				// creating only one way connections at the moment.
				// Assuming, 1-way connections are possible.
				l_graph[l_countryId - 1][l_neighbour - 1] = 1;
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
}
