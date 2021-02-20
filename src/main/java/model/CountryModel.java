package model;

import java.util.ArrayList;

/**
 * Represents a country.
 */
public class CountryModel {
	private int d_countryIdMap;
	private String d_name;
	private ContinentModel d_continent;
	private CoordinateModel d_coordinate;
	private PlayerModel d_owner;
	private int d_armies;

	/**
	 * Creates a country with the specified order, name, continent order and, coordinate.
	 * @param p_countryIdMap    Ordered id of the country based on the position in the map.
	 * @param p_name            Name of the country according to the map file.
	 * @param p_continent  The continent that this country belongs to.
	 * @param p_coordinate  	The Coordinate of this country.
	 */
	public CountryModel(int p_countryIdMap, String p_name, ContinentModel p_continent, CoordinateModel p_coordinate) {
		this.d_countryIdMap = p_countryIdMap;
		this.d_name = p_name;
		this.d_continent = p_continent;
		this.d_coordinate = p_coordinate;
	}

	/**
	 * Get the id of the country in the map based on the relative position in the map file.
	 * This value will become irrelevant for an editmap phase as soon as remove command is used.
	 * @return
	 */
	public int getCountryIdMap(){
		return d_countryIdMap;
	}

	/**
	 * Get the name of the country.
	 * @return Name of the country.
	 */
	public String getName() {
		return this.d_name;
	}

	/**
	 * Get the continent to which this country belongs.
	 * @return The continent to which this country belongs.
	 */
	public ContinentModel getContinent() {
		return this.d_continent;
	}

	/**
	 * Get the coordinates of this country.
	 * @return The coordinates of this country
	 */
	public CoordinateModel getCoordinate() {
		return this.d_coordinate;
	}

	/**
	 * Get the owner of this country.
	 * @return The player who owns this country.
	 */
	public PlayerModel getOwner() {
		return this.d_owner;
	}
	
	/**
	 * Get the armies stationed on this country
	 * @return Number of armies stationed on this country
	 */
	public int getArmies() {
		return this.d_armies;
	}
	
	/**
	 * Set the armies stationed on this country
	 * @param p_armies Number of armies to be stationed on this country
	 */
	public void setArmies(int p_armies) {
		this.d_armies = p_armies;
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