package controller;

/**
 * This abstract class represents the intermediate state were all game play command are invalid
 */
public abstract class IntermediateMapEditingPhase extends Phase{

	/**
	 * {@inheritDoc}
	 */
	IntermediateMapEditingPhase(GameEngine p_gameEngine) {
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
