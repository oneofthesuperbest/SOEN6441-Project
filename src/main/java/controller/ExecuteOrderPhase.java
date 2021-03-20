package controller;

import model.Order;

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
	public String getString() {
		return "execute order";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void showMap() {
		printErrorMessage(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int stop() {
		printErrorMessage(this);
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int delop(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Delop order was executed.");
		System.out.println("Deploy order was executed.");
		p_order.execute();
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int advance(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Advance order was executed.");
		System.out.println("Advance order was executed.");
		p_order.execute();
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int bomb(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Bomb order was executed.");
		System.out.println("Bomb order was executed.");
		p_order.execute();
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int blockade(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Blockade order was executed.");
		System.out.println("Blockade order was executed.");
		p_order.execute();
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int negotiate(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Negotiate order was executed.");
		System.out.println("Negotiate order was executed.");
		p_order.execute();
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int airlift(Order p_order) {
		d_gameEngineObject.getLogEntryBuffer().addLogEntry("Airlift order was executed.");
		System.out.println("Airlift order was executed.");
		p_order.execute();
		return 0;
	}
}
