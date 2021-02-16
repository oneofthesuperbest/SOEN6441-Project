package model;

/**
 * Represents a country.
 */
public class CountryModel {
	private int d_countryId;
	private String d_name;
	private ContinentModel d_continent;
	private CoordinateModel d_coordinate;
	private PlayerModel d_owner;

	/**
	 * Creates a country with the specified order, name, continent order and, coordinate.
	 * @param p_countryId			Order of the country according to the map file.
	 * @param p_name            Name of the country according to the map file.
	 * @param p_continent  The continent that this country belongs to.
	 * @param p_coordinate  	The Coordinate of this country.
	 */
	public CountryModel(int p_countryId, String p_name, ContinentModel p_continent, CoordinateModel p_coordinate) {
		this.d_countryId = p_countryId;
		this.d_name = p_name;
		this.d_continent = p_continent;
		this.d_coordinate = p_coordinate;
	}

	/**
	 * Get the order of the country.
	 * @return Order of the country.
	 */
	public int getCountryId() {
		return this.d_countryId;
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
	 * Set the owner of this country.
	 * @param p_owner Player who owns this country.
	 */

	public void setOwner(PlayerModel p_owner) {
		if (p_owner != this.d_owner) {
			this.d_owner = p_owner;
		}
	}

}