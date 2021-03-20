package model;

import java.util.ArrayList;

import controller.GameEngine;
import controller.MapController;

/**
 * This class is used to store bomb order
 *
 */
public class BombOrder extends Order {
	String d_targetCountryName;
	Player d_issuer;
	GameEngine d_gameEngine;

	CountryModel d_targetCountry = null;

	/**
	 * This constructor is used to initialized the data members i.e.: create an
	 * order
	 * 
	 * @param p_targetCountryName Name of the target country affected by the order
	 * @param p_player            The player who issued the command
	 * @param p_gameEngine        The game engine object
	 */
	public BombOrder(String p_targetCountryName, Player p_player,
			GameEngine p_gameEngine) {
		d_targetCountryName = p_targetCountryName;
		d_issuer = p_player;
		d_gameEngine = p_gameEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		if (isValid()) {
			int l_armies = this.d_targetCountry.getArmies();
			int l_remainingArmies = Math.round(l_armies/2);
			this.d_targetCountry.setArmies(l_remainingArmies);
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
				printUnsuccessfulOrder("Can't bomb " + this.d_targetCountryName + ". Country belong to player who issued the order.");
				return false;
			}
			MapController l_mapController = new MapController(this.d_gameEngine);
			ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_country);
			for (CountryModel l_countryNeighbor : l_neighbors) {
				if (l_countryNeighbor.getName().equals(this.d_targetCountryName)) {
					boolean returnValue = this.d_issuer.hasCard(0);
					if(!returnValue) {
						printUnsuccessfulOrder("Can't bomb " + this.d_targetCountryName + ". Player doesn't have bomb card.");
					}
					return returnValue;
				}
			}
		}
		printUnsuccessfulOrder("Can't bomb " + this.d_targetCountryName + ". Country isn't a neighbors of players countries.");
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Bombed " + this.d_targetCountryName;
		System.out.println(l_effectOfCommand);
		d_gameEngine.getLogEntryBuffer()
				.addLogEntry(l_effectOfCommand);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void printUnsuccessfulOrder(String p_errorMessage) {
		System.out.println(p_errorMessage);
		this.d_gameEngine.getLogEntryBuffer()
				.addLogEntry(p_errorMessage);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Player getPlayer() {
		return this.d_issuer;
	}
}