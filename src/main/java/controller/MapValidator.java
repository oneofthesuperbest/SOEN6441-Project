package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import model.ContinentModel;
import model.CountryModel;

/**
 * This class is used to handle map validation commands
 */
public class MapValidator {
	GameEngine d_gameEngine;

	/**
	 * This constructor initializes the game engine object
	 * 
	 * @param p_gameEngine The game engine object
	 */
	public MapValidator(GameEngine p_gameEngine) {
		this.d_gameEngine = p_gameEngine;
	}

	/**
	 * A helper method to validate the map
	 * 
	 * @return true if the map is valid, false if not valid
	 */
	public boolean isMapValid() {
		if (!validateMap()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validates Map Data
	 * <ul>
	 * <li>Checks if there are any Duplicate Continents</li>
	 * <li>Checks if there are any Duplicate Countries</li>
	 * <li>Checks if the borders are defined in the map or not</li>
	 * <li>Checks if the map is a connected graph</li>
	 * <li>Checks if each continent as itself is a connected graph</li>
	 * </ul>
	 * 
	 * @return true if the map is valid
	 */
	public boolean validateMap() {
		ArrayList<ContinentModel> l_listOfContinents = d_gameEngine.getMapState().getListOfContinents();
		ArrayList<CountryModel> l_listOfCountries = d_gameEngine.getMapState().getListOfCountries();
		int[][] l_bordergraph = d_gameEngine.getMapState().getBorderGraph();
		boolean l_countriesAreVaid = validateCountries(l_listOfCountries);
		boolean l_continentsAreVaid = validateContinents(l_listOfContinents);
		boolean l_bordersAreVaid = validateBorders(l_bordergraph);
		int l_isMyGraphconnected = 0;
		for (int i = 0; i < l_bordergraph.length; i++) {
			boolean connectedCheck = validateGraph(l_bordergraph, i, true);
			if (connectedCheck == false) {
				l_isMyGraphconnected = +1;
			}
		}
		if (l_isMyGraphconnected == 0) {
			System.out.println("Validation Check: The map is a connected graph ");
		} else {
			System.out.println("Validation Check Failed: The map is not a connected graph ");
		}

		if (l_countriesAreVaid == false || l_continentsAreVaid == false || l_bordersAreVaid == false
				|| l_isMyGraphconnected > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks whether the map contains at least one country and if there are any
	 * duplicate countries
	 * 
	 * @param p_listOfCountries The list of countries defined in the map
	 * @return a boolean to convey whether the map has valid number of countries or
	 *         not
	 */
	public boolean validateCountries(ArrayList<CountryModel> p_listOfCountries) {
		boolean l_areCountriesValid = true;
		ArrayList<String> l_Countries = new ArrayList<String>();
		for (int l_idx = 0; l_idx < p_listOfCountries.size(); l_idx++) {
			l_Countries.add(p_listOfCountries.get(l_idx).getName());
		}
		if (p_listOfCountries.size() > 0) {
			l_areCountriesValid = true;
			if (hasDuplicates(l_Countries) == true) {
				System.out.println(
						"Validation Check Failed: A country should not be duplicated or A Country cannot be assigned to multiple continents");
				l_areCountriesValid = false;
			} else {
				System.out.println("Validation Check: No duplicates countries found");
				l_areCountriesValid = true;
			}
		} else {
			System.out.println("Validation Check Failed: Map must contain at least one country");
			l_areCountriesValid = false;
		}
		return l_areCountriesValid;
	}

	/**
	 * Checks whether the map contains at least one country and if there are any
	 * duplicate continents
	 * 
	 * @param p_listOfContinents the complete list of continents defined in the map
	 * @return a boolean to convey whether the map has valid number of continents or
	 *         not
	 */
	public boolean validateContinents(ArrayList<ContinentModel> p_listOfContinents) {
		boolean l_areContinentsValid = true;
		ArrayList<String> l_Continents = new ArrayList<String>();
		for (int l_idx = 0; l_idx < p_listOfContinents.size(); l_idx++) {
			l_Continents.add(p_listOfContinents.get(l_idx).getName());
		}
		if (p_listOfContinents.size() > 0) {
			l_areContinentsValid = true;
			if (hasDuplicates(l_Continents) == true) {
				System.out.println("Validation Check Failed: A map should not have two continents with the same name");
				l_areContinentsValid = false;
			} else {
				System.out.println("Validation Check: No duplicates continents found");
				l_areContinentsValid = true;
			}
		} else {
			System.out.println("Validation Check Failed: Map must contain at least one continent");
			l_areContinentsValid = false;
		}
		// the below check is to see if continent has countries or is empty
		if (isEmptyContinent(p_listOfContinents) == true) {
			l_areContinentsValid = false;
		} else {
			l_areContinentsValid = true;
		}
		return l_areContinentsValid;
	}

	/**
	 * Checks whether any of the continents is empty.
	 * 
	 * @param p_listOfContinents the complete list of continents defined in the map
	 * @return true if any of the continent is empty, false if all continents have
	 *         at least one country in them
	 */
	public boolean isEmptyContinent(ArrayList<ContinentModel> p_listOfContinents) {
		int l_emptyCheck = 0;
		for (ContinentModel l_continent : p_listOfContinents) {
			ArrayList<CountryModel> l_countriesInContinent = l_continent.getCountries();
			if (l_countriesInContinent.size() == 0) {
				System.out.println(
						"Validation Check Failed: There are no countries in the continent " + l_continent.getName());
				l_emptyCheck++;
			}
		}
		if (l_emptyCheck > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checks if the given array list has duplicates
	 * 
	 * @param p_mapData a array list of strings in which duplicates has to be
	 *                  identified
	 * @return a boolean specifying whether the given array has duplicates or not
	 */
	public boolean hasDuplicates(ArrayList<String> p_mapData) {
		boolean l_haveDuplicates = true;
		Set<String> l_mapSet = new HashSet<String>(p_mapData);
		if (l_mapSet.size() < p_mapData.size()) {
			l_haveDuplicates = true;
		} else {
			l_haveDuplicates = false;
		}
		return l_haveDuplicates;
	}

	/**
	 * Check to see if borders are defined in the map
	 * 
	 * @param p_borderGraph a association graph specifying the connection between
	 *                      the countries
	 * @return a boolean specifying whether borders are defined or not
	 */
	public boolean validateBorders(int[][] p_borderGraph) {
		if (p_borderGraph.length > 0) {
			System.out.println("Validation Check: Borders are defined in the map");
			return true;
		} else {
			System.out.println("Validation Check Failed: A Map must contain border associations between the countries");
			return false;
		}
	}

	/**
	 * Checks whether the graph is connected or not
	 * 
	 * @param p_adjacencyMatrix A two dimensional array representation of a graph
	 * @param p_source          The node or position in matrix for which we need to
	 *                          check adjacency for
	 * @param isSubGraph        true or false based on what kind of graph the method
	 *                          should validate
	 * @return a boolean based on whether the graph is connected or not
	 */
	public boolean validateGraph(int p_adjacencyMatrix[][], int p_source, boolean isSubGraph) {
		int l_numberOfNodes = p_adjacencyMatrix[p_source].length;
		int[] l_visited = new int[l_numberOfNodes];
		Stack<Integer> l_stack = new Stack<Integer>();
		int i, element;
		l_visited[p_source] = 1;
		l_stack.push(p_source);
		while (!l_stack.isEmpty()) {
			element = l_stack.pop();
			if (isSubGraph == false) {
				i = 1;
			} else {
				i = 0;
			}
			while (i < l_numberOfNodes) {
				if (p_adjacencyMatrix[element][i] == 1 && l_visited[i] == 0) {
					l_stack.push(i);
					l_visited[i] = 1;
				}
				i++;
			}
		}

		int l_count = 0;
		for (int l_v = 0; l_v < l_numberOfNodes; l_v++)
			if (l_visited[l_v] == 1) {
				l_count++;
			}

		if (l_count == l_numberOfNodes) {
			return true;
		} else {
			return false;
		}

	}
}
