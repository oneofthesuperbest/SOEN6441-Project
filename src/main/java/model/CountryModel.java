package model;

import java.util.ArrayList;

/**
 * Represents a country.
 */
public class CountryModel {
	private int d_order;
	private String d_name;
	private int d_continentOrder;
	private CoordinateModel d_coordinate;
	private PlayerModel d_owner;

	private ArrayList<CountryModel> d_neighbourCountries = new ArrayList<CountryModel>();

	/**
	 * Creates a country with the specified order, name, continent order and, coordinate.
	 * @param p_order			Order of the country according to the map file.
	 * @param p_name            Name of the country according to the map file.
	 * @param p_continentOrder  The order of the continent that this country belongs to.
	 * @param p_coordinate  	The Coordinate of this country.
	 */
	public CountryModel(int p_order, String p_name, int p_continentOrder, CoordinateModel p_coordinate) {
		this.d_order = p_order;
		this.d_name = p_name;
		this.d_continentOrder = p_continentOrder;
		this.d_coordinate = p_coordinate;
	}

	/**
	 * Get the order of the country.
	 * @return Order of the country.
	 */
	public int getOrder() {
		return this.d_order;
	}

	/**
	 * Get the name of the country.
	 * @return Name of the country.
	 */
	public String getName() {
		return this.d_name;
	}

	/**
	 * Get the continent order of the continent to which this country belongs.
	 * @return The continent order to which this country belongs.
	 */
	public int getContinentOrder() {
		return this.d_continentOrder;
	}

	/**
	 * Get the coordinates of this country.
	 * @return The coordinates of this country
	 */
	public CoordinateModel getCoordinate() {
		return this.d_coordinate;
	}

	/**
	 * Get the list of neighbours of this country.
	 * @return List of neighbours.
	 */
	public ArrayList<CountryModel> getNeighbourCountries() {
		return d_neighbourCountries;
	}

	/**
	 * Get the owner of this country.
	 * @return The player who owns this country.
	 */
	public PlayerModel getOwner() {
		return this.d_owner;
	}

	/**
	 * Set the owner of this country.
	 * @param p_owner Player who owns this country.
	 */

	public void setOwner(PlayerModel p_owner) {
		if (p_owner != this.d_owner) {
			this.d_owner = p_owner;
		}
	}

}