package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;
import controller.MapController;

public class RandomStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;
	
	int d_remainingReinforcements = -1;
	int d_maxnumberOfOrder = 10;
	
	/**
	 * This constructor initialized the data members for current strategy
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player Handle to the player who is following current strategy
	 * @param p_scObj Handle to the scanner object
	 */
	RandomStrategy(GameEngine p_gameEngine, Player p_player, Scanner p_scObj) {
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
			d_maxnumberOfOrder = 10;
		}
		if(d_remainingReinforcements > 0) {
			int l_randomCountry = (int) Math.random() * d_player.getOwnedCountry().size();
			d_gameEngine.getPhase().delop(
					new DeployOrder(d_player.getOwnedCountry().get(l_randomCountry).getName(), d_remainingReinforcements, d_player, d_gameEngine));
			d_remainingReinforcements = 0;
			d_maxnumberOfOrder--;
			return 1;
		} else if(d_maxnumberOfOrder > 0) {
			int l_randomAction = (int) Math.random() * 2;
			if(l_randomAction == 0 || !d_player.containsCard(2)) {
				int l_randomCountry = (int) Math.random() * d_player.getOwnedCountry().size();
				MapController l_mapController = new MapController(this.d_gameEngine);
				ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(d_player.getOwnedCountry().get(l_randomCountry));
				d_gameEngine.getPhase().advance(
						new AdvanceOrder(d_player.getOwnedCountry().get(l_randomCountry).getName(),
								l_neighbors.get(0).getName(), d_player.getOwnedCountry().get(l_randomCountry).getArmies(), d_player, d_gameEngine));
			} else {
				int l_randomCountry = (int) Math.random() * d_player.getOwnedCountry().size();
				int l_randomTargetCountry = (int) Math.random() * d_gameEngine.getMapState().getListOfCountries().size();
				d_gameEngine.getPhase().airlift(
						new AirliftOrder(d_player.getOwnedCountry().get(l_randomCountry).getName(),
								d_gameEngine.getMapState().getListOfCountries().get(l_randomTargetCountry).getName(), d_player.getOwnedCountry().get(l_randomCountry).getArmies(), d_player, d_gameEngine));
				d_player.hasCard(2);
			}
			d_maxnumberOfOrder--;
			return 1;
		}
		
		d_remainingReinforcements = -1;
		return 0;
	}
}
