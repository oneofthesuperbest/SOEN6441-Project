package model;

import java.util.ArrayList;

import controller.GameEngine;

/**
 * This class is used to store bomb order
 *
 */
public class NegotiateOrder extends Order {
	String d_targetPlayerName;
	Player d_issuer;
	GameEngine d_gameEngine;

	/**
	 * This constructor is used to initialized the data members i.e.: create an
	 * order
	 * 
	 * @param p_targetPlayerName Name of the target player affected by the order
	 * @param p_player           The player who issued the command
	 * @param p_gameEngine       The game engine object
	 */
	public NegotiateOrder(String p_targetPlayerName, Player p_player, GameEngine p_gameEngine) {
		this.d_targetPlayerName = p_targetPlayerName;
		this.d_issuer = p_player;
		this.d_gameEngine = p_gameEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		if (isValid()) {
			this.d_issuer.addNegotiatingPlayer(d_targetPlayerName);
			ArrayList<Player> l_players = this.d_gameEngine.getPlayersState().getPlayers();
			for (Player l_player : l_players) {
				if (l_player.getName().equals(d_targetPlayerName)) {
					l_player.addNegotiatingPlayer(this.d_issuer.getName());
					break;
				}
			}
			printOrder();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isValid() {
		if (!this.d_issuer.getName().equals(this.d_targetPlayerName)) {
			boolean l_returnValue = this.d_issuer.hasCard(3);
			if (!l_returnValue) {
				printUnsuccessfulOrder(
						"Can't negotiate with " + this.d_targetPlayerName + ". Player doesn't have negotiate card.");
			}
			return l_returnValue;
		}
		printUnsuccessfulOrder(
				"Can't negotiate with " + this.d_targetPlayerName + ". Player can't negotiate with himself.");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Negotiating with " + this.d_targetPlayerName;
		System.out.println(l_effectOfCommand);
		d_gameEngine.getLogEntryBuffer().addLogEntry(l_effectOfCommand);
	}

	/**
	 * {@inheritDoc}
	 */
	public void printUnsuccessfulOrder(String p_errorMessage) {
		System.out.println(p_errorMessage);
		this.d_gameEngine.getLogEntryBuffer().addLogEntry(p_errorMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	public Player getPlayer() {
		return this.d_issuer;
	}
}