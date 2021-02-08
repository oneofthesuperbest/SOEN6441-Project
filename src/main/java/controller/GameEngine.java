package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.ContinentModel;
import model.CountryModel;

/**
 * This class is the main game engine which carry out commands of the players
 */
public class GameEngine {
	ArrayList<ContinentModel> d_listOfContinents = new ArrayList<ContinentModel>();
	ArrayList<CountryModel> d_listOfCountries = new ArrayList<CountryModel>();
	int[][] d_borderGraph;

	/**
	 * Set the list of continents.
	 * @param p_listOfContinents List of continents.
	 */
	public void setListOfContinents(ArrayList<ContinentModel> p_listOfContinents) {
		this.d_listOfContinents = p_listOfContinents;
	}

	/**
	 * Get the list of continents.
	 * @return list of continents.
	 */
	public ArrayList<ContinentModel> getListOfContinents() {
		return d_listOfContinents;
	}

	/**
	 * Set the list of countries.
	 * @param p_listOfCountries List of countries.
	 */
	public void setListOfCountries(ArrayList<CountryModel> p_listOfCountries) {
		this.d_listOfCountries = p_listOfCountries;
	}

	/**
	 * Get the list of countries.
	 * @return List of countries.
	 */
	public ArrayList<CountryModel> getListOfCountries() {
		return d_listOfCountries;
	}

	/**
	 * Set the border graph.
	 * @param p_borderGraph Border graph.
	 */
	public void setBorderGraph(int[][] p_borderGraph) {
		this.d_borderGraph = p_borderGraph;
	}

	/**
	 * Get the border graph.
	 * @return The border graph.
	 */
	public int[][] getBorderGraph() {
		return d_borderGraph;
	}
}
