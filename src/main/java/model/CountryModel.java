package model;

/**
 * Represents a country.
 */
public class CountryModel {
	private int order;
	private String name;
	private int continentOrder;
	private CoordinateModel coordinate;
	private PlayerModel owner;

	/**
	 * Creates a country with the specified order, name, continent and, coordinate.
	 *
	 * @param order       Order of the country in the map.
	 * @param name        Name of the country.
	 * @param continentOrder   The order of the continent that this country belongs to.
	 * @param coordinate  The Coordinate of this country
	 */
	public CountryModel(int order, String name, int continentOrder, CoordinateModel coordinate) {
		this.order = order;
		this.name = name;
		this.continentOrder = continentOrder;
		this.coordinate = coordinate;
	}

	/**
	 * Get the country's order.
	 *
	 * @return An integer representing the country's order.
	 */
	public int getOrder() {
		return this.order;
	}

	/**
	 * Get a country's name.
	 *
	 * @return A string representing the country's name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the continent order of the continent to which this country belongs.
	 *
	 * @return The continent order to which this country belongs.
	 */
	public int getContinentOrder() {
		return this.continentOrder;
	}

	/**
	 * Get the coordinates of this country.
	 *
	 * @return The Coordinate object of this country
	 */
	public CoordinateModel getCoordinate() {
		return this.coordinate;
	}

	/**
	 * Get the owner of this country.
	 *
	 * @return Player object who owns this country.
	 */
	public PlayerModel getOwner() {
		return this.owner;
	}

	/**
	 * Set the owner of this country.
	 *
	 * @param owner Player that owns this country.
	 */

	public void setOwner(PlayerModel owner) {
		if (owner != this.owner) {
			this.owner = owner;
		}
	}

}