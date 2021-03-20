package model;

import java.util.ArrayList;

import controller.GameEngine;
import controller.MapController;

public class AdvanceOrder extends Order {
	String d_targetCountryName;
	String d_sourceCountryName;
	int d_numberOfArmies;
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
		d_sourceCountryName = p_sourceCountryName;
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
			ArrayList<CountryModel> l_listOfOwnedCountries = d_issuer.getOwnedCountry();
			for (CountryModel l_country : l_listOfOwnedCountries) {
				if (l_country.getName().equals(d_targetCountryName)) {
					int l_currentArmies = l_country.getArmies();
					l_country.setArmies((d_numberOfArmies + l_currentArmies));
					l_currentArmies = d_sourceCountry.getArmies();
					d_sourceCountry.setArmies((d_numberOfArmies - l_currentArmies));
					printOrder();
					return;
				}
			}
			int l_currentAttackingArmies = d_numberOfArmies;
			int l_currentDefendingArmies = d_targetCountry.getArmies();
			int remainingDefendingArmies = l_currentDefendingArmies - (int) Math.round(l_currentAttackingArmies * 0.6);
			int remainingAttackingArmies = l_currentAttackingArmies - (int) Math.round(l_currentDefendingArmies * 0.7);
			d_sourceCountry.setArmies((d_sourceCountry.getArmies() - d_numberOfArmies));
			if(remainingDefendingArmies <= 0 && remainingAttackingArmies > 0) {
				d_targetCountry.setArmies(remainingAttackingArmies);
				d_issuer.addOwnedCountry(d_targetCountry);
				d_targetCountry.getOwner().removeOwnedCountry(d_targetCountry);
				d_targetCountry.setOwner(d_issuer);
			} else {
				d_targetCountry.setArmies(remainingDefendingArmies);
			}
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
			if (l_country.getName().equals(d_sourceCountryName)) {
				d_sourceCountry = l_country;
				if(d_sourceCountry.getArmies() >= d_numberOfArmies) {
					MapController l_mapController = new MapController(d_gameEngine);
					ArrayList<CountryModel> l_neighbors = l_mapController.getNeighbors(l_country);
					for (CountryModel l_countryNeighbor : l_neighbors) {
						if (l_countryNeighbor.getName().equals(d_targetCountryName)) {
							d_targetCountry = l_countryNeighbor;
							return true;
						}
					}
					printUnsuccessfulOrder("Can't advance armies from " + d_sourceCountryName + ". Country aren't neighbors.");
					return false;
				} else {
					printUnsuccessfulOrder("Can't advance armies on " + d_targetCountryName + ". Player doesn't have enough armies.");
					return false;
				}
			}
		}
		printUnsuccessfulOrder("Can't advance armies from " + d_sourceCountryName + ". Country doesn't belong to player " + d_issuer.getName());
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void printOrder() {
		String l_effectOfCommand = "Advanced " + d_numberOfArmies + " armies from " + d_sourceCountryName + " to " + d_targetCountryName;
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