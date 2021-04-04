package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;
import controller.MapController;

/**
 * This class is used to implement Aggressive behavior of strategy pattern
 */
public class AggressiveStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;

	CountryModel d_strongestCountry;
	boolean d_airlift = false;

	/**
	 * This constructor initialized the data members for current strategy
	 * 
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player     Handle to the player who is following current strategy
	 * @param p_scObj      Handle to the scanner object
	 */
	AggressiveStrategy(GameEngine p_gameEngine, Player p_player, Scanner p_scObj) {
		this.d_gameEngine = p_gameEngine;
		this.d_player = p_player;
		this.d_scannerObject = p_scObj;
	}

	/**
	 * {@inheritDoc}
	 */
	public int issueOrder() {
		int l_numberOfRemainingReinforcements = d_player.getReinforcementsArmies();
		if (l_numberOfRemainingReinforcements > 0) {
			CountryModel l_strongestCountry = getStrongestCountry();
			d_strongestCountry = l_strongestCountry;
			d_player.addOrder(new DeployOrder(l_strongestCountry.getName(), l_numberOfRemainingReinforcements, d_player,
					d_gameEngine));
			d_player.setReinforcementsArmies(0);
			return 1;
		} else {
			CountryModel l_targetCountry = getTargetCountry();
			if (d_airlift) {
				d_player.addOrder(new AirliftOrder(d_strongestCountry.getName(), l_targetCountry.getName(),
						d_strongestCountry.getArmies(), d_player, d_gameEngine));
				d_player.hasCard(2);
			} else {
				d_player.addOrder(new AdvanceOrder(d_strongestCountry.getName(), l_targetCountry.getName(),
						d_strongestCountry.getArmies(), d_player, d_gameEngine));
			}
			d_airlift = false;
			d_strongestCountry = null;
			// It will stop issuing orders after attack, as any instance there will only be
			// one strongest country and rest of the owned countries will have 0 armies
			return 0;
		}
	}

	/**
	 * This function returns the country owned by the current player with the most
	 * number of armies and adjacent to hostile country.
	 * 
	 * @return The object of the strongest country
	 */
	CountryModel getStrongestCountry() {
		CountryModel l_strongestCountry = null;
		for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
			boolean l_isEdgeCountry = false;
			MapController l_mapController = new MapController(this.d_gameEngine);
			ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_playerCountry);
			for (CountryModel l_neighbor : l_neighbors) {
				if (!l_neighbor.getOwner().getName().equals(d_player.getName())) {
					l_isEdgeCountry = true;
					break;
				}
			}
			if ((d_player.containsCard(2) || l_isEdgeCountry)
					&& (l_strongestCountry == null || l_strongestCountry.getArmies() < l_playerCountry.getArmies())) {
				l_strongestCountry = l_playerCountry;
			}
		}
		return l_strongestCountry;
	}

	/**
	 * This function returns the country whose will be attacked from the strongest
	 * country
	 * 
	 * @return The object of the target country
	 */
	CountryModel getTargetCountry() {
		CountryModel l_targetCountry = null;
		MapController l_mapController = new MapController(this.d_gameEngine);
		ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(d_strongestCountry);
		for (CountryModel l_neighbor : l_neighbors) {
			if (!l_neighbor.getOwner().getName().equals(d_player.getName())) {
				l_targetCountry = l_neighbor;
				break;
			}
		}
		if (l_targetCountry == null) {
			for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
				l_neighbors = l_mapController.getNeighbors(l_playerCountry);
				for (CountryModel l_neighbor : l_neighbors) {
					if (!l_neighbor.getOwner().getName().equals(d_player.getName())) {
						d_airlift = true;
						l_targetCountry = l_neighbor;
						break;
					}
				}
			}
		}
		return l_targetCountry;
	}
}
