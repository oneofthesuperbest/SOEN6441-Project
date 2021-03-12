package controller;

import model.Player;

public abstract class IntermediateOrderPhase extends IntermediateGamePlayPhase {

	/**
	 * {@inheritDoc}
	 */
	IntermediateOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void addPlayers(String[] p_command) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	void startGame() {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	int delop(Player p_player, String p_countryID, int p_numberOfArmies) {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int advance(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies) {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int bomb(Player p_player, String p_countryID) {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int blockade(Player p_player, String p_countryID) {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int negotiate(Player p_player, String p_playerID) {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int airlift(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies) {
		printErrorMessage(this);
		return 0;
	}
	
}
