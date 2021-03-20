package model;

import java.util.ArrayList;

import controller.GameEngine;

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
	public DeployOrder(String p_targetCountryName, int p_numberOfArmies, Player p_player,
			GameEngine p_gameEngine) {
		d_targetCountryName = p_targetCountryName;
		d_numberOfArmies = p_numberOfArmies;
		d_issuer = p_player;
		d_gameEngine = p_gameEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		if (isValid()) {
			int l_currentArmies = d_countryOfInterest.getArmies();
			d_countryOfInterest.setArmies((d_numberOfArmies + l_currentArmies));
			int l_reinforcements = d_issuer.getReinforcementsArmies();
			d_issuer.setReinforcementsArmies((l_reinforcements - d_numberOfArmies));
			printOrder();
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isValid() {
		ArrayList<CountryModel> l_listOfOwnedCountries = d_issuer.getOwnedCountry();
		for (CountryModel l_country : l_listOfOwnedCountries) {
			if (l_country.getName().equals(d_targetCountryName)) {
				d_countryOfInterest = l_country;
				if(d_issuer.getReinforcementsArmies() >= d_numberOfArmies) {
					return true;
				} else {
					printUnsuccessfulOrder("Can't delop armies on " + d_targetCountryName + ". Player doesn't have enough reinforcements.");
					return false;
				}
			}
		}
		printUnsuccessfulOrder("Can't delop armies on " + d_targetCountryName + ". Country doesn't belong to player " + d_issuer.getName());
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Deployed " + d_numberOfArmies + " armies on country " + d_targetCountryName;
		System.out.println(l_effectOfCommand);
		d_gameEngine.getLogEntryBuffer()
				.addLogEntry(l_effectOfCommand);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void printUnsuccessfulOrder(String p_errorMessage) {
		System.out.println(p_errorMessage);
		d_gameEngine.getLogEntryBuffer()
				.addLogEntry(p_errorMessage);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Player getPlayer() {
		return d_issuer;
	}
}