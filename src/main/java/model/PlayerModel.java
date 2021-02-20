package model;

import java.util.ArrayList;
import java.util.Scanner;

import controller.GameEngine;
import controller.GamePlayCommandList;
import controller.MapController;

/**
 * Represents a player
 */
public class PlayerModel {
	private String d_name;
	private ArrayList<CountryModel> d_listOfOwnedCountries = new ArrayList<CountryModel>();
	private int d_reinforementsArmies;
	private GameEngine d_gameEngineContext;
	private Scanner d_scannerObject;
	private ArrayList<Order> d_listOfOrders = new ArrayList<Order>();

	/**
	 * Creates a player with the specified name.
	 * 
	 * @param p_name The players name.
	 */
	public PlayerModel(String p_name, GameEngine p_gameEngine, Scanner p_scannerObject) {
		this.d_name = p_name;
		d_gameEngineContext = p_gameEngine;
		d_scannerObject = p_scannerObject;
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
		d_listOfOwnedCountries.add(p_country);
	}

	/**
	 * This function is used to get the list of countries owned by the player
	 * 
	 * @return List of countries owned by the player
	 */
	public ArrayList<CountryModel> getOwnedCountry() {
		return d_listOfOwnedCountries;
	}

	/**
	 * This function is used to set the reinforcement armies for the player at the
	 * start of each game loop
	 * 
	 * @param p_armies The value that will be used to initialize players
	 *                 reinforcement armies
	 */
	public void setReinforcementsArmies(int p_armies) {
		d_reinforementsArmies = p_armies;
	}

	/**
	 * This function is used to get the reinforcement armies of the player
	 * 
	 * @return The number of reinforcement armies held by the player
	 */
	public int getReinforcementsArmies() {
		return d_reinforementsArmies;
	}

	/**
	 * This function is used to ask player to issue their orders
	 * 
	 * @return It returns 1 if player has issued an order and 0 if player decides to
	 *         stop giving orders.
	 */
	public int issueOrder() {
		boolean l_issuedOrder = false;
		while (!l_issuedOrder) {
			System.out.println(this.getName() + " issue your order");
			String l_command = d_scannerObject.nextLine();
			if (l_command.equals(GamePlayCommandListForPlayer.STOP.getCommandString())) {
				d_reinforementsArmies = 0;
				return 0;
			} else if (l_command.equals(GamePlayCommandListForPlayer.SHOWMAP.getCommandString())) {
				MapController l_mapController = new MapController(d_gameEngineContext);
				l_mapController.showMapForGamePlay();
			} else {
				int l_returnValue = validateOrder(l_command);
				if (l_returnValue > 0) {
					d_reinforementsArmies -= l_returnValue;
					l_issuedOrder = true;
				}
			}
		}
		return 1;
	}

	/**
	 * This function is used to validate the command
	 * @param p_command The command entered by the player
	 * @return 'number greater than 0' if the command is valid and order was issued or it returns 0 if the order was incorrect
	 */
	public int validateOrder(String p_command) {
		String[] l_commandArray = p_command.split(" ");
		if(l_commandArray[0].equals(GamePlayCommandListForPlayer.DEPLOY.getCommandString())) {
			int[] l_numberOfRequiredParameters = GamePlayCommandListForPlayer.DEPLOY.getCommandTypes();
			for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
				if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
					try {
						@SuppressWarnings("unused")
						int l_testIfInteger = Integer
								.parseInt(l_commandArray[(l_parameterIndex + 1)]);
						if(l_testIfInteger > d_reinforementsArmies) {
							System.out
							.println("You exceeded your current reinforcement armies");
							return 0;
						}
					} catch (NumberFormatException e) {
						System.out
								.println("Invalid parameter type: One of the parameter is not of type integer");
						return 0;
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid number of parameters: Missing parameters");
						return 0;
					}
				} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
					try {
						@SuppressWarnings("unused")
						String l_stringParamter = (l_commandArray[(l_parameterIndex + 1)]);
						boolean l_isCountryOwned = false;
						for(CountryModel l_ownedCountries : d_listOfOwnedCountries) {
							if(l_ownedCountries.getName().equals(l_stringParamter)) {
								l_isCountryOwned = true;
								break;
							}
						}
						if(!l_isCountryOwned) {
							System.out
							.println("You do not own country '" + l_stringParamter + "'");
							return 0;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid number of parameters: Missing parameters");
						return 0;
					}
				}
			}
		} else {
			System.out.println("Invalid command: Please check your command");
			return 0;
		}
		createOrder(l_commandArray);
		return Integer.parseInt(l_commandArray[2]);
	}
	
	/**
	 * This function is used to create order
	 * @param p_commandArray The command string split by " "
	 */
	public void createOrder(String[] p_commandArray) {
		d_listOfOrders.add(new Order(p_commandArray[0], p_commandArray[1], Integer.parseInt(p_commandArray[2]), this));
	}
	
	/**
	 * This function is used to call the next order held by the player
	 */
	public void nextOrder() {
		d_listOfOrders.get(0).execute();
		d_listOfOrders.remove(0);
	}
	
	/**
	 * This function is used to return the list of order held by the player
	 * @return Arraylist of order that haven't been executed yet
	 */
	public ArrayList<Order> getOrders() {
		return d_listOfOrders;
	}
}