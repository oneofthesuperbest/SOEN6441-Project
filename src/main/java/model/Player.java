package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;

/**
 * Represents a player
 */
public class Player {
	private String d_name;
	private ArrayList<CountryModel> d_listOfOwnedCountries = new ArrayList<CountryModel>();
	private int d_reinforementsArmies;
	private ArrayList<Order> d_listOfOrders = new ArrayList<Order>();
	private ArrayList<String> d_listOfConcurredCountries = new ArrayList<String>();
	private ArrayList<String> d_listOfNegotiatingPlayers = new ArrayList<String>();
	public Strategy d_playerStrategy;

	/**
	 * 0 for bomb card, 1 for blockade card, 2 for airlift card and 3 for negotiate
	 */
	private ArrayList<Integer> d_playersCards = new ArrayList<Integer>();

	/**
	 * Creates a player with the specified name.
	 * 
	 * @param p_name          The players name.
	 * @param p_strategy      The behaviour of the player.
	 * @param p_gameEngine    GameEngine reference.
	 * @param p_scannerObject Scanner object reference.
	 */
	public Player(String p_name, String p_strategy, GameEngine p_gameEngine, Scanner p_scannerObject) {
		this.d_name = p_name;
		if(p_strategy.equals("human")) {
			d_playerStrategy = new PlayerStrategy(p_gameEngine, this, p_scannerObject);
		} else if(p_strategy.equals("aggressive")) {
			d_playerStrategy = new AggressiveStrategy(p_gameEngine, this, p_scannerObject);
		} else if(p_strategy.equals("cheater")) {
			d_playerStrategy = new CheaterStrategy(p_gameEngine, this, p_scannerObject);
		} else if(p_strategy.equals("benevolent")) {
			d_playerStrategy = new BenevolentStrategy(p_gameEngine, this, p_scannerObject);
		} else {
			d_playerStrategy = new RandomStrategy(p_gameEngine, this, p_scannerObject);
		}
	}

	/**
	 * This function is used to check if player has a certain card. It also removes
	 * the card.
	 * 
	 * @param p_cardNumber The card number to check
	 * @return true if the card is present else false
	 */
	public boolean hasCard(int p_cardNumber) {
		for (int l_index = 0; l_index < this.d_playersCards.size(); l_index++) {
			if (this.d_playersCards.get(l_index) == p_cardNumber) {
				this.d_playersCards.remove(l_index);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function is used to check if player has a certain card.
	 * 
	 * @param p_cardNumber The card number to check
	 * @return true if the card is present else false
	 */
	public boolean containsCard(int p_cardNumber) {
		for (int l_index = 0; l_index < this.d_playersCards.size(); l_index++) {
			if (this.d_playersCards.get(l_index) == p_cardNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This function randomly adds a card
	 */
	public void addCard() {
		int l_randomCard = (int) (Math.random() * 4);
		this.d_playersCards.add(l_randomCard);
	}

	/**
	 * This function returns players list of cards.
	 * 
	 * @return List of cards in integer form
	 */
	public ArrayList<Integer> getCards() {
		return this.d_playersCards;
	}

	/**
	 * This function return the list of concurred countries in the turn
	 * 
	 * @return The list of concurred countries
	 */
	public ArrayList<String> getConcurredCountries() {
		return this.d_listOfConcurredCountries;
	}

	/**
	 * This function is used to add a negotiating country to current players list
	 * 
	 * @param p_playerName The name of the player to be negotiated with
	 */
	public void addNegotiatingPlayer(String p_playerName) {
		this.d_listOfNegotiatingPlayers.add(p_playerName);
	}

	/**
	 * This function is used to return the list of players that are in negotiation
	 * with current player
	 * 
	 * @return The list of player names that are negotiating with current player
	 */
	public ArrayList<String> getNegotiatingPlayers() {
		return this.d_listOfNegotiatingPlayers;
	}

	/**
	 * This function is used to add country name to list of concurred country
	 * 
	 * @param p_countryName The name of the concurred country
	 */
	public void addConcurredCountry(String p_countryName) {
		this.d_listOfConcurredCountries.add(p_countryName);
	}

	/**
	 * This function is used to remove country name from list of concurred country
	 * 
	 * @param p_countryName The name of the country to be removed
	 */
	public void removeConcurredCountry(String p_countryName) {
		for (int l_countryIndex = 0; l_countryIndex < this.d_listOfConcurredCountries.size(); l_countryIndex++) {
			if (this.d_listOfConcurredCountries.get(l_countryIndex).equals(p_countryName)) {
				this.d_listOfConcurredCountries.remove(l_countryIndex);
			}
		}
	}

	/**
	 * Get the player's name.
	 * 
	 * @return A string representing the player's name.
	 */
	public String getName() {
		return this.d_name;
	}

	/**
	 * This function is used to add a country to the players list of owned
	 * countries.
	 * 
	 * @param p_country The country object that needs to be added in the players
	 *                  list of owned countries
	 */
	public void addOwnedCountry(CountryModel p_country) {
		this.d_listOfOwnedCountries.add(p_country);
	}

	/**
	 * This function is used to get the list of countries owned by the player
	 * 
	 * @return List of countries owned by the player
	 */
	public ArrayList<CountryModel> getOwnedCountry() {
		return this.d_listOfOwnedCountries;
	}

	/**
	 * This function is used to set the reinforcement armies for the player at the
	 * start of each game loop
	 * 
	 * @param p_armies The value that will be used to initialize players
	 *                 reinforcement armies
	 */
	public void setReinforcementsArmies(int p_armies) {
		this.d_reinforementsArmies = p_armies;
	}

	/**
	 * This function is used to get the reinforcement armies of the player
	 * 
	 * @return The number of reinforcement armies held by the player
	 */
	public int getReinforcementsArmies() {
		return this.d_reinforementsArmies;
	}

	/**
	 * This function is used to removed country owned from players list
	 * 
	 * @param p_countryModel The object of country to be removed
	 */
	public void removeOwnedCountry(CountryModel p_countryModel) {
		this.d_listOfOwnedCountries.remove(p_countryModel);
	}

	/**
	 * This function is used to ask player to issue their orders
	 * 
	 * @return It returns 1 if player has issued an order and 0 if player decides to
	 *         stop giving orders.
	 */
	public int issueOrder() {
		return this.d_playerStrategy.issueOrder();
	}

	/**
	 * This function is used to add order
	 * 
	 * @param p_order The order object
	 */
	public void addOrder(Order p_order) {
		this.d_listOfOrders.add(p_order);
	}

	/**
	 * This function is used to call the next order held by the player
	 */
	public void nextOrder() {
		System.out.println("Player " + getName() + " executing its order");
		this.d_listOfOrders.get(0).execute();
		this.d_listOfOrders.remove(0);
	}

	/**
	 * This function is used to return the list of order held by the player
	 * 
	 * @return Arraylist of order that haven't been executed yet
	 */
	public ArrayList<Order> getOrders() {
		return this.d_listOfOrders;
	}
}