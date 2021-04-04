package model;

import java.util.Scanner;

import controller.GameEngine;

/**
 * This class implements the Benevolent behavior of the strategy patten
 */
public class BenevolentStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;
	
	/**
	 * This constructor initialized the data members for current strategy
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player Handle to the player who is following current strategy
	 * @param p_scObj Handle to the scanner object
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
		
		return 0;
	}
}
