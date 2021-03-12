package controller;

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
		return "issue order";
	}
	
	/**
	 * {@inheritDoc}
	 */
	void showMap() {
		printErrorMessage(this);
	}
}
