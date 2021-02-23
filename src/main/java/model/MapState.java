package model;

import java.util.ArrayList;

/**
 * Represents the current map state inside the GameEngine.
 */
public class MapState {
	ArrayList<ContinentModel> d_listOfContinents = new ArrayList<ContinentModel>();
	ArrayList<CountryModel> d_listOfCountries = new ArrayList<CountryModel>();
	/**
	 * An empty graph is loaded by default.
	 */
	int[][] d_borderGraph = new int[0][0];

	/**
	 * Set the list of continents.
	 * 
	 * @param p_listOfContinents List of continents.
	 */
	public void setListOfContinents(ArrayList<ContinentModel> p_listOfContinents) {
		this.d_listOfContinents = p_listOfContinents;
	}

	/**
	 * Get the list of continents.
	 * 
	 * @return list of continents.
	 */
	public ArrayList<ContinentModel> getListOfContinents() {
		return d_listOfContinents;
	}

	/**
	 * Set the list of countries.
	 * 
	 * @param p_listOfCountries List of countries.
	 */
	public void setListOfCountries(ArrayList<CountryModel> p_listOfCountries) {
		this.d_listOfCountries = p_listOfCountries;
	}

	/**
	 * Get the list of countries.
	 * 
	 * @return List of countries.
	 */
	public ArrayList<CountryModel> getListOfCountries() {
		return d_listOfCountries;
	}

	/**
	 * Set the border graph.
	 * 
	 * @param p_borderGraph Border graph.
	 */
	public void setBorderGraph(int[][] p_borderGraph) {
		this.d_borderGraph = p_borderGraph;
	}

	/**
	 * Get the border graph.
	 * 
	 * @return The border graph.
	 */
	public int[][] getBorderGraph() {
		return d_borderGraph;
	}

	/**
	 * Clear the map data.
	 */
	public void clear() {
		int l_continentLength = d_listOfContinents.size();
		int l_countryLength = d_listOfCountries.size();
		if (l_continentLength > 0 || l_countryLength > 0) {
			System.out.println("Clearing current map data...");
		}
		d_listOfContinents = new ArrayList<ContinentModel>();
		d_listOfCountries = new ArrayList<CountryModel>();
		d_borderGraph = new int[0][0];
	}
}
