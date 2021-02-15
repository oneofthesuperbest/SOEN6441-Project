package model;

import java.util.ArrayList;

/**
 * Represents the current players state
 */
public class PlayersState {
	ArrayList<PlayerModel> d_listOfPlayers = new ArrayList<PlayerModel>();
	
	/**
     * Adds a new player to the list of players
     * @param p_playerObject New player that needs to be added
     */
	public void addPlayer(PlayerModel p_playerObject) {
		d_listOfPlayers.add(p_playerObject);
	}
	
	
	/**
     * Removes a player from the list of players and returns 1 if player was successfully removed.
     * It returns 0 if player was not found.
     * @param p_playerName Name of player that needs to be removed
     */
	public int removePlayer(String p_playerName) {
		for(int l_index = 0; l_index < d_listOfPlayers.size(); l_index++) {
			if(d_listOfPlayers.get(l_index).getName().equals(p_playerName)) {
				d_listOfPlayers.remove(l_index);
				return 1;
			}
		}
		return 0;
	}
}
