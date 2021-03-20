package model;

import java.util.ArrayList;

import controller.GameEngine;

public class DeployOrder implements Order {
	String d_targetCountryName;
	int d_numberOfArmies;
	Player d_issuer;
	GameEngine d_gameEngine;

	CountryModel d_country;

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
	 * This function is used to execute the current order of the player
	 */
	public void execute() {
		if (isValid()) {
			int l_currentArmies = d_country.getArmies();
			d_country.setArmies((d_numberOfArmies + l_currentArmies));
			printOrder();
			return;
		}
	}

	/**
	 * This function is used to validate command before executing
	 */
	public boolean isValid() {
		ArrayList<CountryModel> l_listOfOwnedCountries = d_issuer.getOwnedCountry();
		for (CountryModel l_country : l_listOfOwnedCountries) {
			if (l_country.getName().equals(d_targetCountryName)) {
				d_country = l_country;
				return true;
			}
		}
		return false;
	}

	/**
	 * This function is used to print the effect of the command in the log and prompt
	 */
	public void printOrder() {
		String l_effectOfCommand = "Deployed " + d_numberOfArmies + " armies on country " + d_targetCountryName;
		System.out.println(l_effectOfCommand);
		d_gameEngine.getLogEntryBuffer()
				.addLogEntry(l_effectOfCommand);
	}
}