package controller;

public class IssueOrderPhase extends IntermediateOrderPhase {
	
	/**
	 * {@inheritDoc}
	 */
	IssueOrderPhase(GameEngine p_gameEngine) {
		super(p_gameEngine);
	}
	
	/**
	 * {@inheritDoc}
	 */
	String getString() {
		return "issue order";
	}
}
