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
	public void addPlayers(String[] p_command) {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void startGame() {
		printErrorMessage(this);
	}
}
