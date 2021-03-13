package controller;

import model.Order;

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
	public String getString() {
		return "issue order";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int delop(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int advance(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int bomb(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int blockade(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int negotiate(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int airlift(Order p_order) {
		//-- Validate command and then create order
		return 0;
	}
}
