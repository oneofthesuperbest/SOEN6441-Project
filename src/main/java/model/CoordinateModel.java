package model;

/**
 * Represents the coordinates of a country.
 */
public class CoordinateModel {
	private int d_x;
	private int d_y;

	/**
	 * Creates a coordinate with the specified x and y values.
	 * 
	 * @param p_x x-coordinate value.
	 * @param p_y y-coordinate value.
	 */
	public CoordinateModel(int p_x, int p_y) {
		this.d_x = p_x;
		this.d_y = p_y;
	}

	/**
	 * Get the x-coordinate value.
	 * 
	 * @return x-coordinate value.
	 */
	public int getX() {
		return d_x;
	}

	/**
	 * Get the y-coordinate value.
	 * 
	 * @return y-coordinate value.
	 */
	public int getY() {
		return d_y;
	}
}