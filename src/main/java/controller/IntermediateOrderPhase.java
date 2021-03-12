package controller;

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
}
