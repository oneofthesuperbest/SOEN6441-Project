package model;

import java.util.Scanner;

import controller.GameEngine;
import view.ValidateCommandView;

/**
 * This class is used to implement Player behavior of strategy pattern
 */
public class PlayerStrategy extends Strategy {
	Player d_player;
	GameEngine d_gameEngine;
	Scanner d_scannerObject;
	
	/**
	 * This constructor initialized the data members for current strategy
	 * @param p_gameEngine Handle to the game engine object
	 * @param p_player Handle to the player who is following current strategy
	 * @param p_scObj Handle to the scanner object
	 */
	PlayerStrategy(GameEngine p_gameEngine, Player p_player, Scanner p_scObj) {
		this.d_gameEngine = p_gameEngine;
		this.d_player = p_player;
		this.d_scannerObject = p_scObj;
	}

	/**
	 * {@inheritDoc}
	 */
	public int issueOrder() {
		boolean l_issuedOrder = false;
		while (!l_issuedOrder) {
			System.out.println(d_player.getName() + " issue your order");
			String l_command = this.d_scannerObject.nextLine();

			ValidateCommandView l_VCVObject = new ValidateCommandView();
			int returnValue = l_VCVObject.checkCommand(d_gameEngine, l_command, d_player);
			if (returnValue == 1) {
				l_issuedOrder = true;
			} else if (returnValue == 2) {
				return 0;
			}
		}
		return 1;
	}
}
