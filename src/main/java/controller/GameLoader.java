package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import model.AdvanceOrder;
import model.AirliftOrder;
import model.BlockadeOrder;
import model.BombOrder;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.DeployOrder;
import model.NegotiateOrder;
import model.Order;
import model.Player;

/**
 * This class is used to load and save games in warzone format
 */
public class GameLoader {
	private GameEngine d_gameEngine;
	HashMap<String, CountryModel> d_countryMap = new HashMap<String, CountryModel>();

	/**
	 * Create a new map controller with the specified GameEngine.
	 * 
	 * @param p_gameEngine A GameEngine object which is populated with the map data.
	 */
	public GameLoader(GameEngine p_gameEngine) {
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
	public boolean loadGame(String p_fileName, boolean p_createNewFile, boolean p_allowInvalid) {
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

		try {
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
				case "[game]": {
					l_idx = loadGameEngine(l_idx, l_lines);
					break;
				}
				case "[players]": {
					l_idx = loadPlayers(l_idx, l_lines);
					break;
				}
				case "[files]":
				case "":
				default: {
					break;
				}
				}
			}
		} catch (Exception e) {
			System.out.println("The file is not a valid game file.");
			return false;
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
				this.d_gameEngine.setPhase(3);
				return true;
			}
		} else {
			System.out.println("Map Loaded successfully.");
			this.d_gameEngine.setPhase(3);
			return true;
		}
	}

	/**
	 * Load the GameEngine data from map file.
	 * 
	 * @param p_idx   Index of the current line.
	 * @param p_lines List of all the lines in the map file.
	 * @return current index.
	 */
	public int loadGameEngine(int p_idx, List<String> p_lines) {
		p_idx += 1;
		this.d_gameEngine.d_maxTurns = Integer.parseInt(p_lines.get(p_idx));
		p_idx += 1;
		this.d_gameEngine.d_currentPlayer = p_lines.get(p_idx);
		p_idx += 1;
		String[] l_segments = p_lines.get(p_idx).split(" ");

		for (String l_player : l_segments) {
			if (!l_player.equals("")) {
				this.d_gameEngine.d_playersMapCompleted.put(l_player, 1);
			}
		}

		p_idx++;
		System.out.println("...Read GameEngine data");
		return p_idx;
	}

	/**
	 * Load the players data from map file.
	 * 
	 * @param p_idx   Index of the current line.
	 * @param p_lines List of all the lines in the map file.
	 * @return current index.
	 */
	public int loadPlayers(int p_idx, List<String> p_lines) {
		System.out.println("...Loading players");
		p_idx++;
		while (p_idx < p_lines.size()) {
			if (p_lines.get(p_idx).equals("[new]")) {
				p_idx++;
			} else {
				break;
			}
			String[] l_player = p_lines.get(p_idx).split(" ");
			Player l_currentPlayer = new Player(l_player[0], l_player[1], this.d_gameEngine,
					this.d_gameEngine.d_scannerObject);
			l_currentPlayer.setReinforcementsArmies(Integer.parseInt(l_player[2]));

			p_idx++;
			String[] l_country = p_lines.get(p_idx).split(" ");
			for (String l_c : l_country) {
				if (!l_c.equals("")) {
					l_currentPlayer.addOwnedCountry(d_countryMap.get(l_c));
					d_countryMap.get(l_c).setOwner(l_currentPlayer);
				}
			}

			p_idx++;
			String[] l_countryC = p_lines.get(p_idx).split(" ");
			for (String l_c : l_countryC) {
				if (!l_c.equals("")) {
					l_currentPlayer.addConcurredCountry(l_c);
				}
			}

			p_idx++;
			String[] l_negPlayers = p_lines.get(p_idx).split(" ");
			for (String l_negPlayer : l_negPlayers) {
				if (!l_negPlayer.equals("")) {
					l_currentPlayer.addNegotiatingPlayer(l_negPlayer);
				}
			}

			p_idx++;
			String[] l_cards = p_lines.get(p_idx).split(" ");
			for (String l_negCard : l_cards) {
				if (!l_negCard.equals("")) {
					l_currentPlayer.d_playersCards.add(Integer.parseInt(l_negCard));
				}
			}

			p_idx++;
			while (p_idx < p_lines.size() && !p_lines.get(p_idx).equals("[new]")) {
				if (!p_lines.get(p_idx).equals("")) {
					String[] l_order = p_lines.get(p_idx).split(" ");
					if (l_order[0].equals("model.AdvanceOrder")) {
						l_currentPlayer.addOrder(new AdvanceOrder(l_order[1], l_order[2], Integer.parseInt(l_order[3]),
								l_currentPlayer, this.d_gameEngine));
					} else if (l_order[0].equals("model.AirliftOrder")) {
						l_currentPlayer.addOrder(new AirliftOrder(l_order[1], l_order[2], Integer.parseInt(l_order[3]),
								l_currentPlayer, this.d_gameEngine));
					} else if (l_order[0].equals("model.BlockadeOrder")) {
						l_currentPlayer.addOrder(new BlockadeOrder(l_order[1], l_currentPlayer, this.d_gameEngine));
					} else if (l_order[0].equals("model.BombOrder")) {
						l_currentPlayer.addOrder(new BombOrder(l_order[1], l_currentPlayer, this.d_gameEngine));
					} else if (l_order[0].equals("model.DeployOrder")) {
						l_currentPlayer.addOrder(new DeployOrder(l_order[1], Integer.parseInt(l_order[2]),
								l_currentPlayer, this.d_gameEngine));
					} else if (l_order[0].equals("model.NegotiateOrder")) {
						l_currentPlayer.addOrder(new NegotiateOrder(l_order[1], l_currentPlayer, this.d_gameEngine));
					}
				}
				p_idx++;
			}

			if (l_currentPlayer.getName().equals("Neutral")) {
				this.d_gameEngine.d_neutralPlayer = l_currentPlayer;
			} else {
				this.d_gameEngine.getPlayersState().addPlayer(l_currentPlayer);
			}
			System.out.println("Added player: " + l_currentPlayer.getName());
		}

		p_idx++;
		System.out.println("...Read Players data");
		return p_idx;
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
	 * @throws Exception if the file is not a gmae file
	 */
	public int loadMapCountriesFromFile(int p_idx, List<String> p_lines) throws Exception {
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
			l_currentCountry.setPotentialArmies(Integer.parseInt(l_segments[5]));
			l_currentCountry.setArmies(Integer.parseInt(l_segments[6]));
			d_countryMap.put(l_countryName, l_currentCountry);
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

	/**
	 * Write the map to file.
	 * 
	 * @param fileName Filename to which the map is to be written.
	 * @return true if map was saved successful else returns false
	 */
	public boolean saveGame(String fileName) {
		MapValidator l_mapValidator = new MapValidator(this.d_gameEngine);
		if (!l_mapValidator.isMapValid()) {
			System.out.println("savegame command failed: Map not valid");
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

			l_writer.write("[game]\n");
			saveGameEngine(l_writer);

			l_writer.write("[players]\n");
			savePlayers(l_writer);

			l_writer.close();

			this.d_gameEngine.setPhase(0);
			return true;

		} catch (IOException e) {
			System.out.println("Error while writing to file. Invalid filename.");
			return false;
		}
	}

	/**
	 * Write the GameEngine data in the map file.
	 * 
	 * @param p_writer FileWriter object.
	 * @throws IOException Input-output related exceptions while writing to file.
	 */
	public void saveGameEngine(FileWriter p_writer) throws IOException {
		p_writer.write(d_gameEngine.d_maxTurns + "\n");
		p_writer.write(d_gameEngine.d_currentPlayer + "\n");
		String l_concludedPlayers = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry l_player : d_gameEngine.d_playersMapCompleted.entrySet()) {
			l_concludedPlayers += (((String) l_player.getKey()) + " ");
		}

		p_writer.write(l_concludedPlayers + "\n");

		p_writer.write("\n");
		p_writer.write("\n");
		System.out.println("...saved GameEngine data to file.");
	}

	/**
	 * Write the Players data in the map file.
	 * 
	 * @param p_writer FileWriter object.
	 * @throws IOException Input-output related exceptions while writing to file.
	 */
	public void savePlayers(FileWriter p_writer) throws IOException {
		ArrayList<Player> l_players = d_gameEngine.getPlayersState().getPlayers();
		for (Player l_player : l_players) {
			String l_playerStr = "[new]\n" + l_player.getName() + " " + l_player.d_playerStrategy.getClass().getName()
					+ " " + l_player.getReinforcementsArmies() + "\n";
			for (CountryModel l_country : l_player.getOwnedCountry()) {
				l_playerStr += (l_country.getName() + " ");
			}
			l_playerStr += "\n";
			for (String l_country : l_player.getConcurredCountries()) {
				l_playerStr += (l_country + " ");
			}
			l_playerStr += "\n";
			for (String l_negPlayer : l_player.getNegotiatingPlayers()) {
				l_playerStr += (l_negPlayer + " ");
			}
			l_playerStr += "\n";
			for (int l_card : l_player.getCards()) {
				l_playerStr += (l_card + " ");
			}
			l_playerStr += "\n";

			for (Order l_order : l_player.getOrders()) {
				if (l_order.getClass().getName().equals("model.AdvanceOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((AdvanceOrder) l_order).d_sourceCountryName
							+ " " + ((AdvanceOrder) l_order).d_targetCountryName + " "
							+ ((AdvanceOrder) l_order).d_numberOfArmies + "\n");
				} else if (l_order.getClass().getName().equals("model.AirliftOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((AirliftOrder) l_order).d_sourceCountryName
							+ " " + ((AirliftOrder) l_order).d_targetCountryName + " "
							+ ((AirliftOrder) l_order).d_numberOfArmies + "\n");
				} else if (l_order.getClass().getName().equals("model.BlockadeOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((BlockadeOrder) l_order).d_targetCountryName
							+ "\n");
				} else if (l_order.getClass().getName().equals("model.BombOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((BombOrder) l_order).d_targetCountryName
							+ "\n");
				} else if (l_order.getClass().getName().equals("model.DeployOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((DeployOrder) l_order).d_targetCountryName
							+ " " + ((DeployOrder) l_order).d_numberOfArmies + "\n");
				} else if (l_order.getClass().getName().equals("model.NegotiateOrder")) {
					l_playerStr += (l_order.getClass().getName() + " " + ((NegotiateOrder) l_order).d_targetPlayerName
							+ "\n");
				}
			}

			p_writer.write(l_playerStr);
		}
		Player l_player = this.d_gameEngine.getNeutralPlayer();
		String l_playerStr = "[new]\n" + l_player.getName() + " " + l_player.d_playerStrategy.getClass().getName() + " "
				+ l_player.getReinforcementsArmies() + "\n";
		for (CountryModel l_country : l_player.getOwnedCountry()) {
			l_playerStr += (l_country.getName() + " ");
		}
		l_playerStr += "\n";
		for (String l_country : l_player.getConcurredCountries()) {
			l_playerStr += (l_country + " ");
		}
		l_playerStr += "\n";
		for (String l_negPlayer : l_player.getNegotiatingPlayers()) {
			l_playerStr += (l_negPlayer + " ");
		}
		l_playerStr += "\n";
		for (int l_card : l_player.getCards()) {
			l_playerStr += (l_card + " ");
		}
		l_playerStr += "\n";
		p_writer.write(l_playerStr + "\n");

		p_writer.write("\n");
		p_writer.write("\n");
		System.out.println("...saved players data to file.");
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
					+ l_coordinates.getX() + " " + l_coordinates.getY() + " " + l_country.getPotentialArmies() + " "
					+ l_country.getArmies();
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
