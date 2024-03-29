package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;
import controller.MapController;

/**
 * This class implements the Benevolent behavior of the strategy patten
 */
public class BenevolentStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;

	int d_remainingReinforcements = -1;

	/**
	 * This constructor initialized the data members for current strategy
	 * 
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player     Handle to the player who is following current strategy
	 * @param p_scObj      Handle to the scanner object
	 */
	BenevolentStrategy(GameEngine p_gameEngine, Player p_player, Scanner p_scObj) {
		this.d_gameEngine = p_gameEngine;
		this.d_player = p_player;
		this.d_scannerObject = p_scObj;
	}

	/**
	 * {@inheritDoc}
	 */
	public int issueOrder() {
		if (d_remainingReinforcements == -1) {
			d_remainingReinforcements = d_player.getReinforcementsArmies();
			for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
				l_playerCountry.setPotentialArmies(l_playerCountry.getArmies());
			}
		}
		if (d_remainingReinforcements > 0) {
			CountryModel l_weakCountries = null;
			for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
				if (l_weakCountries == null) {
					l_weakCountries = l_playerCountry;
				} else if (l_weakCountries.getPotentialArmies() > l_playerCountry.getPotentialArmies()) {
					l_weakCountries = l_playerCountry;
				}
			}
			if (l_weakCountries != null) {
				d_gameEngine.getPhase().delop(new DeployOrder(l_weakCountries.getName(), 1, d_player, d_gameEngine));
				d_remainingReinforcements -= 1;
				l_weakCountries.setPotentialArmies((l_weakCountries.getPotentialArmies() + 1));
				return 1;
			}
		} else {
			CountryModel l_weakCountries = null;
			for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
				MapController l_mapController = new MapController(this.d_gameEngine);
				ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_playerCountry);
				for (CountryModel l_neighbor : l_neighbors) {
					if (l_neighbor.getOwner().getName().equals(d_player.getName())
							&& (l_playerCountry.getPotentialArmies() - l_neighbor.getPotentialArmies()) == 2) {
						l_weakCountries = l_neighbor;
					}
				}
				if (l_weakCountries != null) {
					d_gameEngine.getPhase().advance(new AdvanceOrder(l_playerCountry.getName(),
							l_weakCountries.getName(), 1, d_player, d_gameEngine));
					return 1;
				}
			}
		}

		d_remainingReinforcements = -1;
		return 0;
	}
}
