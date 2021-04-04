package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;
import controller.MapController;

/**
 * This class implements the behaviour of Cheater strategy
 */
public class CheaterStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;

	/**
	 * This constructor initialized the data members for current strategy
	 * 
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player     Handle to the player who is following current strategy
	 * @param p_scObj      Handle to the scanner object
	 */
	CheaterStrategy(GameEngine p_gameEngine, Player p_player, Scanner p_scObj) {
		this.d_gameEngine = p_gameEngine;
		this.d_player = p_player;
		this.d_scannerObject = p_scObj;
	}

	/**
	 * {@inheritDoc}
	 */
	public int issueOrder() {
		ArrayList<String> l_listOfConqueredCountries = new ArrayList<String>();
		for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
			if (!l_listOfConqueredCountries.contains(l_playerCountry.getName())) {
				MapController l_mapController = new MapController(this.d_gameEngine);
				ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_playerCountry);
				for (CountryModel l_neighbor : l_neighbors) {
					if (!l_neighbor.getOwner().getName().equals(d_player.getName())) {
						l_listOfConqueredCountries.add(l_neighbor.getName());
						l_neighbor.setOwner(d_player);
						d_player.addOwnedCountry(l_neighbor);
					}
				}
			}
		}
		for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
			MapController l_mapController = new MapController(this.d_gameEngine);
			ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_playerCountry);
			for (CountryModel l_neighbor : l_neighbors) {
				if (!l_neighbor.getOwner().getName().equals(d_player.getName())) {
					int l_currentArmies = l_playerCountry.getArmies();
					l_playerCountry.setArmies(l_currentArmies * 2);
					break;
				}
			}
		}
		return 0;
	}
}
