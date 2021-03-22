package controller;

import model.Order;

/**
 * This abstract class represents the intermediate state were all game play
 * command are invalid
 */
public abstract class IntermediateMapEditingPhase extends Phase {

	/**
	 * {@inheritDoc}
	 */
	IntermediateMapEditingPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addPlayers(String[] p_command) {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void startGame() {
		printErrorMessage(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public int delop(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int advance(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int bomb(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int blockade(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int negotiate(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int airlift(Order p_order) {
		printErrorMessage(this);
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public int stop() {
		printErrorMessage(this);
		return 0;
	}
}
