package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.ContinentModel;
import model.CountryModel;
import model.LogEntryBuffer;
import model.MapState;
import model.Player;
import model.PlayersState;
import view.FileEntryLogger;
import view.CommandList;
import view.ValidateCommandView;

/**
 * This class is the main game engine which carry out commands of the players
 */
public class GameEngine {
	private MapState d_mapState = new MapState();
	private PlayersState d_playerState = new PlayersState();
	String d_commandSeparator = " ";
	Scanner d_scannerObject;
	Phase d_phase;
	// Observable LogEntryBuffer class to push log strings.
	LogEntryBuffer d_logEntryBuffer;
	// Observer FileEntryLogger which waits for notification from LogEntryBuffer
	FileEntryLogger d_fileEntryLogger;
	Player d_neutralPlayer = null;

	/**
	 * This constructor is used to set the scanner object context
	 * 
	 * @param p_scannerObject The context of scanner object
	 * @param p_logEntryBuffer The context of concrete Observer class
	 * @param p_fileEntryLogger The context of concrete Observable class
	 */
	public GameEngine(Scanner p_scannerObject, LogEntryBuffer p_logEntryBuffer, FileEntryLogger p_fileEntryLogger) {
		d_scannerObject = p_scannerObject;
		d_logEntryBuffer = p_logEntryBuffer;
		d_fileEntryLogger = p_fileEntryLogger;
		setPhase(0);
	}

	/**
	 * This function returns the LogEntryBuffer object which is used to add log
	 * entries.
	 * 
	 * @return A LogEntryBuffer object which is used to push log entries into the
	 *         file.
	 */
	public LogEntryBuffer getLogEntryBuffer() {
		return d_logEntryBuffer;
	}
	
	/**
	 * This function returns the pointer to neutral player
	 * @return The player object
	 */
	public Player getNeutralPlayer() {
		return this.d_neutralPlayer;
	}

	/**
	 * This function is used to set the phase based on integer value. 0 for default,
	 * 1 for map editing, 2 for start up, 3 for issue order and 4 for execute order
	 * 
	 * @param p_phase integer value of the phase
	 */
	public void setPhase(int p_phase) {
		if (p_phase == 0) {
			d_logEntryBuffer.addLogEntry("-----------------Default phase initialized-----------------");
			d_phase = new DefaultPhase(this);
		} else if (p_phase == 1) {
			d_logEntryBuffer.addLogEntry("-----------------Map edit phase initialized-----------------");
			d_phase = new MapEditingPhase(this);
		} else if (p_phase == 2) {
			d_logEntryBuffer.addLogEntry("-----------------Game play start-up phase initialized-----------------");
			d_phase = new StartUpPhase(this);
		} else if (p_phase == 3) {
			d_logEntryBuffer.addLogEntry("-----------------Game play issue order phase initialized-----------------");
			d_phase = new IssueOrderPhase(this);
		} else if (p_phase == 4) {
			d_logEntryBuffer.addLogEntry("-----------------Game play execute order phase initialized-----------------");
			d_phase = new ExecuteOrderPhase(this);
		}
	}

	/**
	 * This functions return object of the current phase
	 * 
	 * @return Current phase object
	 */
	public Phase getPhase() {
		return d_phase;
	}

	/**
	 * Get the current map state.
	 * 
	 * @return Current map state.
	 */
	public MapState getMapState() {
		return d_mapState;
	}

	/**
	 * Get the current players state.
	 * 
	 * @return Current players state.
	 */
	public PlayersState getPlayersState() {
		return d_playerState;
	}

	/**
	 * Used to load the GameEngine console for game play phase
	 */
	public void loadGameEngineConsole() {
		System.out.println("GameEngine console loaded.");
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		while (true) {
			if (!this.getPhase().getString().equals("start-up")) {
				// Break out of Game engine console for user
				break;
			} else {
				System.out.println("Enter your command");
				String l_command = "";
				l_command = d_scannerObject.nextLine();
				l_VCVObject.checkCommand(this, l_command, null);
			}
		}
		this.d_neutralPlayer = new Player("Neutral", this, this.d_scannerObject);
		this.loadGameEngine();
	}

	/**
	 * This function loads the Game Engine machine that starts the game and
	 * initializes the main game loop.
	 */
	public void loadGameEngine() {
		System.out.println("Game Engine loaded.");
		while (true) {
			this.setPhase(3);
			this.assignReinforcements();
			this.issueOrderLoop();
			this.setPhase(4);
			this.executeOrderLoop();
			this.removeLostPlayers();
			this.issueCardsAndRefreshNegotiation();
			if(this.checkWinner()) {
				break;
			}
		}
		String l_gameEndMessage = "Player " + this.d_playerState.getPlayers().get(0).getName() +" has won the game!!!";
		this.d_logEntryBuffer.addLogEntry(l_gameEndMessage);
		System.out.println(l_gameEndMessage);
	}
	
	/**
	 * This function checks if the game has end.
	 * @return true if game end else false
	 */
	public boolean checkWinner() {
		if(this.d_playerState.getPlayers().size() == 1) {
			if(this.d_playerState.getPlayers().get(0).getOwnedCountry().size() == this.d_mapState.getListOfCountries().size() && this.d_neutralPlayer.getOwnedCountry().size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function is used to remove all players from the game who doesn't own a country.
	 */
	public void removeLostPlayers() {
		ArrayList<Player> l_players = this.getPlayersState().getPlayers();
		for (int l_playerIndex = 0; l_playerIndex < l_players.size(); l_playerIndex++) {
			if (l_players.get(l_playerIndex).getOwnedCountry().size() == 0) {
				l_players.remove(l_playerIndex);
			}
		}
	}

	/**
	 * This function is used to assign cards to all player who concurred atleast one
	 * country. It also refreshes all of the players that were in negotiation.
	 */
	public void issueCardsAndRefreshNegotiation() {
		ArrayList<Player> l_players = this.getPlayersState().getPlayers();
		for (Player l_player : l_players) {
			if (l_player.getConcurredCountries().size() > 0) {
				l_player.addCard();
				l_player.getConcurredCountries().clear();
			}
			l_player.getNegotiatingPlayers().clear();
		}
	}

	/**
	 * This function is used to loop over all the players in round-robin fashion to
	 * request execute orders
	 */
	public void executeOrderLoop() {
		ArrayList<Player> l_players = this.getPlayersState().getPlayers();
		HashMap<String, Integer> l_playersMapCompleted = new HashMap<String, Integer>();

		while (l_playersMapCompleted.size() < l_players.size()) {
			for (Player l_player : l_players) {
				if (l_playersMapCompleted.get(l_player.getName()) == null) {
					if (l_player.getOrders().size() > 0) {
						l_player.nextOrder();
					} else {
						l_playersMapCompleted.put(l_player.getName(), 1);
					}
				}
			}
		}
		System.out.println("All players have executed their orders");
	}

	/**
	 * This function is used to loop over all the players in round-robin fashion to
	 * request issue orders
	 */
	public void issueOrderLoop() {
		ArrayList<Player> l_players = this.getPlayersState().getPlayers();
		HashMap<String, Integer> l_playersMapCompleted = new HashMap<String, Integer>();

		while (l_playersMapCompleted.size() < l_players.size()) {
			for (Player l_player : l_players) {
				if (l_playersMapCompleted.get(l_player.getName()) == null) {
					if (l_player.getReinforcementsArmies() > 0) {
						int l_returnValue = l_player.issueOrder();
						if (l_returnValue == 0) {
							l_playersMapCompleted.put(l_player.getName(), 1);
						}
					} else {
						l_playersMapCompleted.put(l_player.getName(), 1);
					}
				}
			}
		}
		System.out.println("All players have issued their orders");
	}

	/**
	 * This function is used to assign reinforcements to the players as per the game
	 * rules
	 */
	public void assignReinforcements() {
		ArrayList<ContinentModel> l_listOfContinents = this.getMapState().getListOfContinents();
		HashMap<String, Integer> l_continentsMapOfOwnedCountries = new HashMap<String, Integer>();
		HashMap<String, Integer> l_continentsMapOfControlValues = new HashMap<String, Integer>();
		for (ContinentModel l_continent : l_listOfContinents) {
			int l_continentsOwnedCountries = l_continent.getCountries().size();
			l_continentsMapOfOwnedCountries.put(l_continent.getName(), l_continentsOwnedCountries);
			l_continentsMapOfControlValues.put(l_continent.getName(), l_continent.getArmy());
		}

		ArrayList<Player> l_players = this.getPlayersState().getPlayers();

		for (Player l_player : l_players) {
			ArrayList<CountryModel> l_listOfOwnedCountries = l_player.getOwnedCountry();
			int l_playerReinforcement = Math.max(3, (int) Math.floor(l_listOfOwnedCountries.size() / 3));
			HashMap<String, Integer> l_playersMapOfOwnedCountries = new HashMap<String, Integer>();
			for (CountryModel l_country : l_listOfOwnedCountries) {
				String l_continentName = l_country.getContinent().getName();
				if (l_playersMapOfOwnedCountries.get(l_continentName) == null) {
					l_playersMapOfOwnedCountries.put(l_continentName, 1);
				} else {
					l_playersMapOfOwnedCountries.put(l_continentName,
							(l_playersMapOfOwnedCountries.get(l_continentName) + 1));
				}
			}
			for (Map.Entry<String, Integer> l_map : l_playersMapOfOwnedCountries.entrySet()) {
				if (l_continentsMapOfOwnedCountries.get(l_map.getKey()) == l_map.getValue()) {
					l_playerReinforcement += l_continentsMapOfControlValues.get(l_map.getKey());
				}
			}
			System.out.println("Player '" + l_player.getName() + "' has been assigned " + l_playerReinforcement
					+ " reinforcement armies");
			l_player.setReinforcementsArmies(l_playerReinforcement);
		}
		System.out.println("All Players have been assigned reinforcement armies");
	}

	/**
	 * This function is used to assign countries to all the players as per the rules
	 * and then start the Game Engine.
	 */
	public void assignCountries() {
		ArrayList<CountryModel> l_countries = this.getMapState().getListOfCountries();
		int l_numberOfCountries = l_countries.size();
		ArrayList<Player> l_players = this.getPlayersState().getPlayers();
		int l_numberOfPlayers = l_players.size();

		int l_countryIndex = (int) (Math.random() * (l_numberOfCountries));
		for (Player l_player : l_players) {
			int l_numberOfCountriesTobeAssigned = (int) Math.ceil(l_numberOfCountries / l_numberOfPlayers);
			int l_countryStartIndex = l_countryIndex;
			for (; l_countryIndex != ((l_countryStartIndex + l_numberOfCountriesTobeAssigned) % l_countries.size());) {
				CountryModel l_country = l_countries.get(l_countryIndex);
				l_country.setOwner(l_player);
				l_player.addOwnedCountry(l_country);
				l_countryIndex = ((l_countryIndex + 1) % l_countries.size());
			}
			l_numberOfPlayers--;
			l_numberOfCountries -= l_numberOfCountriesTobeAssigned;
		}
		System.out.println("Countries are assigned to players. Starting Game Engine...");
	}

	/**
	 * Used to add/remove players
	 * 
	 * @param p_commandList The command in array form split by " "
	 */
	public void addRemovePlayers(String[] p_commandList) {
		for (int l_index = 1; l_index < p_commandList.length; l_index++) {
			if (p_commandList[l_index].equals(CommandList.ADD.getCommandString())) {
				l_index++;
				int l_returnValue = d_playerState.addPlayer(new Player(p_commandList[l_index], this, d_scannerObject));
				if (l_returnValue == 0) {
					System.out.println("Player with name '" + p_commandList[l_index] + "' is already present");
				} else {
					System.out.println("Player '" + p_commandList[l_index] + "' is added");
				}
			} else {
				l_index++;
				int l_returnValue = d_playerState.removePlayer(p_commandList[l_index]);
				if (l_returnValue == 0) {
					System.out.println("Player '" + p_commandList[l_index] + "' not found");
				} else {
					System.out.println("Player '" + p_commandList[l_index] + "' is removed");
				}
			}
		}
	}
}
