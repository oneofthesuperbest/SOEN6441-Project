package model;

import java.util.ArrayList;

/**
 * Represents a continent.
 */
public class ContinentModel {
	private int continentOrder;
	private String name;
	private int army;
	private String color;
	private ArrayList<CountryModel> countries = new ArrayList<CountryModel>();

	/**
	 * Create a continent with the specified name, army, color.
	 * @param continentOrder Order of the continent according to the map.
	 * @param name  Name of the continent.
	 * @param color Color of the continent.
	 * @param army  Army value for the continent.
	 */
	public ContinentModel(int continentOrder, String name, String color, int army) {
		this.continentOrder = continentOrder;
		this.name = name;
		this.color = color;
		this.army = army;
	}

	/**
	 * Get the order of the continent
	 * @return order of the continent.
	 */
	public int getContinentOrder() {
		return this.continentOrder;
	}

	/**
	 * Get the name of the continent.
	 *
	 * @return String representing the name of the country.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the army value for the continent.
	 *
	 * @return Int representing the army value.
	 */
	public int getArmy() {
		return this.army;
	}

	/**
	 * Get the color of the continent.
	 *
	 * @return String representing the color.
	 */
	public String getColor(){
		return this.color;
	}

	/**
	 * Get the countries under this continent.
	 * @return ArrayList of countries.
	 */
	public ArrayList<CountryModel> getCountries() {
		return this.countries;
	}
}
