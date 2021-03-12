package controller;

import model.Player;

public class ExecuteOrderPhase extends IntermediateOrderPhase {
	
	/**
	 * {@inheritDoc}
	 */
	ExecuteOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	String getString() {
		return "execute order";
	}
	
	/**
	 * {@inheritDoc}
	 */
	void showMap() {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	int delop(Player p_player, String p_countryID, int p_numberOfArmies) {
		//-- Validate command and then execute order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int advance(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies) {
		//-- Validate command and then execute order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int bomb(Player p_player, String p_countryID) {
		//-- Validate command and then execute order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int blockade(Player p_player, String p_countryID) {
		//-- Validate command and then execute order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int negotiate(Player p_player, String p_playerID) {
		//-- Validate command and then execute order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	int airlift(Player p_player, String p_countryIDFrom, String p_countryIDTo, int p_numberOfArmies) {
		//-- Validate command and then execute order
		return 0;
	}
}
