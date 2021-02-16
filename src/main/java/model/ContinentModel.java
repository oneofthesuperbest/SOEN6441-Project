package model;

import java.util.ArrayList;

/**
 * Represents a continent.
 */
public class ContinentModel {
	private int d_ContinentId;
	private String d_name;
	private int d_army;
	private String d_color;
	private ArrayList<CountryModel> d_listOfCountries = new ArrayList<CountryModel>();

	/**
	 * Create a continent with the specified id, name, color and, army.
	 * @param p_continentId Id of the continent according to the map file.
	 *                      NOTE: <p>This id will become irrelevant when a map
	 *                      is saved after edits since the new ids are
	 *                      interpreted from the position of the continent in
	 *                      the list of continents.</p>
	 * @param p_name  Name of the continent according to the map file.
	 * @param p_color Color of the continent according to the map file.
	 * @param p_army  Army value for the continent according to the map file.
	 */
	public ContinentModel(int p_continentId, String p_name, String p_color, int p_army) {
		this.d_ContinentId = p_continentId;
		this.d_name = p_name;
		this.d_color = p_color;
		this.d_army = p_army;
		this.d_army = p_army;
	}

	/**
	 * Get the id of the continent.
	 * @return id of the continent.
	 */
	public int getContinentId() {
		return this.d_ContinentId;
	}

	/**
	 * Get the name of the continent.
	 * @return Name of the country.
	 */
	public String getName() {
		return this.d_name;
	}

	/**
	 * Get the army value for the continent.
	 * @return Int representing the army value.
	 */
	public int getArmy() {
		return this.d_army;
	}

	/**
	 * Get the color of the continent.
	 * @return String representing the color.
	 */
	public String getColor(){
		return this.d_color;
	}

	/**
	 * Get the ids of the countries under this continent.
	 * @return ArrayList of country ids.
	 */
	public ArrayList<CountryModel> getCountries() {
		return this.d_listOfCountries;
	}
}
