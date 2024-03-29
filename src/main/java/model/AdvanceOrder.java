package model;

import java.util.ArrayList;

import controller.GameEngine;
import controller.MapController;

/**
 * This class is used to store advance order
 */
public class AdvanceOrder extends Order {
	/**
	 * Name of the target country
	 */
	public String d_targetCountryName;
	/**
	 * Name of the source country
	 */
	public String d_sourceCountryName;
	/**
	 * number of armies to deploy
	 */
	public int d_numberOfArmies;
	Player d_issuer;
	GameEngine d_gameEngine;

	CountryModel d_sourceCountry = null;
	CountryModel d_targetCountry = null;

	/**
	 * This constructor is used to initialized the data members i.e.: create an
	 * order
	 * 
	 * @param p_sourceCountryName The name of the country from where to advance
	 * @param p_targetCountryName Name of the target country affected by the order
	 * @param p_numberOfArmies    Number of armies to carry out the order
	 * @param p_player            The player who issued the command
	 * @param p_gameEngine        The game engine object
	 */
	public AdvanceOrder(String p_sourceCountryName, String p_targetCountryName, int p_numberOfArmies, Player p_player,
			GameEngine p_gameEngine) {
		this.d_sourceCountryName = p_sourceCountryName;
		this.d_targetCountryName = p_targetCountryName;
		this.d_numberOfArmies = p_numberOfArmies;
		this.d_issuer = p_player;
		this.d_gameEngine = p_gameEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		if (isValid()) {
			ArrayList<CountryModel> l_listOfOwnedCountries = d_issuer.getOwnedCountry();
			for (CountryModel l_country : l_listOfOwnedCountries) {
				if (l_country.getName().equals(this.d_targetCountryName)) {
					int l_currentArmies = l_country.getArmies();
					l_country.setArmies((this.d_numberOfArmies + l_currentArmies));
					l_currentArmies = this.d_sourceCountry.getArmies();
					this.d_sourceCountry.setArmies((l_currentArmies - this.d_numberOfArmies));
					printOrder();
					return;
				}
			}
			int l_currentAttackingArmies = this.d_numberOfArmies;
			int l_currentDefendingArmies = this.d_targetCountry.getArmies();
			int l_remainingDefendingArmies = l_currentDefendingArmies
					- (int) Math.round(l_currentAttackingArmies * 0.6);
			int l_remainingAttackingArmies = l_currentAttackingArmies
					- (int) Math.round(l_currentDefendingArmies * 0.7);
			this.d_sourceCountry.setArmies((this.d_sourceCountry.getArmies() - this.d_numberOfArmies));
			if (l_remainingDefendingArmies <= 0 && l_remainingAttackingArmies > 0) {
				this.d_issuer.addConcurredCountry(this.d_targetCountryName);
				this.d_targetCountry.setArmies(l_remainingAttackingArmies);
				this.d_issuer.addOwnedCountry(this.d_targetCountry);
				this.d_targetCountry.getOwner().removeConcurredCountry(this.d_targetCountryName);
				this.d_targetCountry.getOwner().removeOwnedCountry(this.d_targetCountry);
				this.d_targetCountry.setOwner(this.d_issuer);
			} else {
				this.d_targetCountry.setArmies(l_remainingDefendingArmies);
			}
			printOrder();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isValid() {
		ArrayList<CountryModel> l_listOfOwnedCountries = this.d_issuer.getOwnedCountry();
		for (CountryModel l_country : l_listOfOwnedCountries) {
			if (l_country.getName().equals(this.d_sourceCountryName)) {
				this.d_sourceCountry = l_country;
				if (this.d_sourceCountry.getArmies() >= this.d_numberOfArmies) {
					MapController l_mapController = new MapController(this.d_gameEngine);
					ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_country);
					for (CountryModel l_countryNeighbor : l_neighbors) {
						if (l_countryNeighbor.getName().equals(this.d_targetCountryName)) {
							this.d_targetCountry = l_countryNeighbor;
							if (this.d_issuer.getNegotiatingPlayers()
									.contains(l_countryNeighbor.getOwner().getName())) {
								printUnsuccessfulOrder("Can't advance armies on " + this.d_targetCountryName
										+ ". Players are in negotiation.");
								return false;
							}
							return true;
						}
					}
					printUnsuccessfulOrder(
							"Can't advance armies from " + this.d_sourceCountryName + ". Country aren't neighbors.");
					return false;
				} else {
					printUnsuccessfulOrder("Can't advance armies on " + this.d_targetCountryName
							+ ". Player doesn't have enough armies.");
					return false;
				}
			}
		}
		printUnsuccessfulOrder("Can't advance armies from " + this.d_sourceCountryName
				+ ". Country doesn't belong to player " + this.d_issuer.getName());
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Advanced " + this.d_numberOfArmies + " armies from " + this.d_sourceCountryName
				+ " to " + this.d_targetCountryName;
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