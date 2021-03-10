package model;

import java.util.ArrayList;

/**
 * Represents the current players state
 */
public class PlayersState {
	private ArrayList<Player> d_listOfPlayers = new ArrayList<Player>();

	/**
	 * Adds a new player to the list of players
	 * 
	 * @param p_playerObject New player that needs to be added
	 * @return It returns 1 if player was added and 0 if player was found with same
	 *         name
	 */
	public int addPlayer(Player p_playerObject) {
		String l_playerName = p_playerObject.getName();
		for (int l_index = 0; l_index < d_listOfPlayers.size(); l_index++) {
			if (d_listOfPlayers.get(l_index).getName().equals(l_playerName)) {
				return 0;
			}
		}
		d_listOfPlayers.add(p_playerObject);
		return 1;
	}

	/**
	 * Removes a player from the list of players and returns 1 if player was
	 * successfully removed. It returns 0 if player was not found.
	 * 
	 * @param p_playerName Name of player that needs to be removed
	 * @return It returns 1 if player was removed and 0 if player was not found
	 */
	public int removePlayer(String p_playerName) {
		for (int l_index = 0; l_index < d_listOfPlayers.size(); l_index++) {
			if (d_listOfPlayers.get(l_index).getName().equals(p_playerName)) {
				d_listOfPlayers.remove(l_index);
				return 1;
			}
		}
		return 0;
	}

	/**
	 * This function return the entire list of players
	 * 
	 * @return ArrayList contains the PlayerModel object
	 */
	public ArrayList<Player> getPlayers() {
		return this.d_listOfPlayers;
	}
}
