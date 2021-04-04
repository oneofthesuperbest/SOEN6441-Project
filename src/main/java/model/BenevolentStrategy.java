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
		if(d_remainingReinforcements == -1) {
			d_remainingReinforcements = d_player.getReinforcementsArmies();
		}
		if (d_remainingReinforcements > 0) {
			CountryModel l_weakCountries = null;
			for (CountryModel l_playerCountry : d_player.getOwnedCountry()) {
				if (l_weakCountries == null) {
					l_weakCountries = l_playerCountry;
				} else if (l_weakCountries.getArmies() > l_playerCountry.getArmies()) {
					l_weakCountries = l_playerCountry;
				}
			}
			d_gameEngine.getPhase().delop(new DeployOrder(l_weakCountries.getName(), 1, d_player, d_gameEngine));
			d_remainingReinforcements -= 1;
			return 1;
		} else {
			
		}
		
		d_remainingReinforcements = -1;
		return 0;
	}
}
