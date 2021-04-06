package controller;

/**
 * This class implements functions that are not part of Issue order or Execute
 * order phase
 */
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
	
	/**
	 * {@inheritDoc}
	 */
	public void startTournament(String[] p_command) {
		printErrorMessage(this);
	}
}
