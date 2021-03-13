package controller;

import model.Order;

public class StartUpPhase extends IntermediateGamePlayPhase {
	
	/**
	 * {@inheritDoc}
	 */
	StartUpPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getString() {
		return "start-up";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addPlayers(String[] p_command) {
		d_gameEngineObject.addRemovePlayers(p_command);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void startGame() {
		d_gameEngineObject.assignCountries();
		d_gameEngineObject.setPhase(3);
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
}
