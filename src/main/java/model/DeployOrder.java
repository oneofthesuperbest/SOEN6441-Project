package model;

import java.util.ArrayList;

import controller.GameEngine;

/**
 * This class is used to store deploy order
 */
public class DeployOrder extends Order {
	String d_targetCountryName;
	int d_numberOfArmies;
	Player d_issuer;
	GameEngine d_gameEngine;

	CountryModel d_countryOfInterest = null;

	/**
	 * This constructor is used to initialized the data members i.e.: create an
	 * order
	 * 
	 * @param p_targetCountryName Name of the target country affected by the order
	 * @param p_numberOfArmies    Number of armies to carry out the order
	 * @param p_player            The player who issued the command
	 * @param p_gameEngine        The game engine object
	 */
	public DeployOrder(String p_targetCountryName, int p_numberOfArmies, Player p_player, GameEngine p_gameEngine) {
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
			int l_currentArmies = this.d_countryOfInterest.getArmies();
			this.d_countryOfInterest.setArmies((this.d_numberOfArmies + l_currentArmies));
			int l_reinforcements = this.d_issuer.getReinforcementsArmies();
			this.d_issuer.setReinforcementsArmies((l_reinforcements - this.d_numberOfArmies));
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
			if (l_country.getName().equals(this.d_targetCountryName)) {
				this.d_countryOfInterest = l_country;
				if (this.d_issuer.getReinforcementsArmies() >= this.d_numberOfArmies) {
					return true;
				} else {
					printUnsuccessfulOrder("Can't delop armies on " + this.d_targetCountryName
							+ ". Player doesn't have enough reinforcements.");
					return false;
				}
			}
		}
		printUnsuccessfulOrder("Can't delop armies on " + this.d_targetCountryName
				+ ". Country doesn't belong to player " + d_issuer.getName());
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Deployed " + this.d_numberOfArmies + " armies on country "
				+ this.d_targetCountryName;
		System.out.println(l_effectOfCommand);
		this.d_gameEngine.getLogEntryBuffer().addLogEntry(l_effectOfCommand);
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