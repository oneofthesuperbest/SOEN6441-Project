package controller;

import model.Order;

/**
 * This class implements functions related to issue order phase
 */
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
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Delop order was issued.");
		System.out.println("Delop order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int advance(Order p_order) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Advance order was issued.");
		System.out.println("Advance order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void saveGame(String p_filename) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Game save command was issued.");
		System.out.println("Game save command was issued.");
		GameLoader p_gameLoader = new GameLoader(this.d_gameEngineObject);
		p_gameLoader.saveGame(p_filename);
	}

	/**
	 * {@inheritDoc}
	 */
	public int bomb(Order p_order) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Bomb order was issued.");
		System.out.println("Bomb order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int blockade(Order p_order) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Blockade order was issued.");
		System.out.println("Blockade order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int negotiate(Order p_order) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Negotiate order was issued.");
		System.out.println("Negotiate order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int airlift(Order p_order) {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Airlift order was issued.");
		System.out.println("Airlift order was issued.");
		p_order.getPlayer().addOrder(p_order);
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	public int stop() {
		this.d_gameEngineObject.getLogEntryBuffer().addLogEntry("Player decided to stop issuing orders.");
		return 2;
	}
}
