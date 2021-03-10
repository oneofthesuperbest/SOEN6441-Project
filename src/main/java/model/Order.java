package model;

import java.util.ArrayList;

/**
 * This class is used to stored a order issued by a player
 */
public class Order {
	String d_order;
	String d_targetCountryName;
	int d_numberOfArmies;
	Player d_issuer;

	/**
	 * This constructor is used to initialized the data members i.e.: create an
	 * order
	 * 
	 * @param p_order             Name of the order
	 * @param p_targetCountryName Name of the target country affected by the order
	 * @param p_numberOfArmies    Number of armies to carry out the order
	 * @param p_player            The player who issued the command
	 */
	public Order(String p_order, String p_targetCountryName, int p_numberOfArmies, Player p_player) {
		d_order = p_order;
		d_targetCountryName = p_targetCountryName;
		d_numberOfArmies = p_numberOfArmies;
		d_issuer = p_player;
	}

	/**
	 * This function is used to execute the current order of the player
	 */
	public void execute() {
		ArrayList<CountryModel> l_listOfOwnedCountries = d_issuer.getOwnedCountry();
		for (CountryModel l_country : l_listOfOwnedCountries) {
			if (l_country.getName().equals(d_targetCountryName)) {
				int l_currentArmies = l_country.getArmies();
				l_country.setArmies((d_numberOfArmies + l_currentArmies));
				System.out.println("Deployed " + d_numberOfArmies + " armies on country " + d_targetCountryName);
				return;
			}
		}
	}
}
