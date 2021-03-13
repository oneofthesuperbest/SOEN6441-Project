package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import model.ContinentModel;
import model.CountryModel;

/**
 * A class for handling map validation.
 */
public class MapValidator {
	private GameEngine d_gameEngine;

	/**
	 * Create a new map controller with the specified GameEngine.
	 * 
	 * @param p_gameEngine A GameEngine object which is populated with the map data.
	 */
	public MapValidator(GameEngine p_gameEngine) {
		d_gameEngine = p_gameEngine;
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
		int isMyGraphconnected = 0;
		for (int i = 0; i < l_bordergraph.length; i++) {
			boolean connectedCheck = validateGraph(l_bordergraph, i, true);
			if (connectedCheck == false) {
				isMyGraphconnected = +1;
			}
		}
		if (isMyGraphconnected == 0) {
			System.out.println("Validation Check: The map is a connected graph ");
		} else {
			System.out.println("Validation Check Failed: The map is not a connected graph ");
		}

		boolean l_isCountryAConnectedGraph = isCountryAConnectedGraph(l_listOfContinents, l_listOfCountries,
				l_bordergraph);
		if (l_countriesAreVaid == false || l_continentsAreVaid == false || l_bordersAreVaid == false
				|| isMyGraphconnected > 0) {
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
		boolean areCountriesValid = true;
		ArrayList<String> l_Countries = new ArrayList<String>();
		for (int l_idx = 0; l_idx < p_listOfCountries.size(); l_idx++) {
			l_Countries.add(p_listOfCountries.get(l_idx).getName());
		}
		if (p_listOfCountries.size() > 0) {
			areCountriesValid = true;
			if (hasDuplicates(l_Countries) == true) {
				System.out.println(
						"Validation Check Failed: A country should not be duplicated or A Country cannot be assigned to multiple continents");
				areCountriesValid = false;
			} else {
				System.out.println("Validation Check: No duplicates countries found");
				areCountriesValid = true;
			}
		} else {
			System.out.println("Validation Check Failed: Map must contain at least one country");
			areCountriesValid = false;
		}
		return areCountriesValid;
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
		int emptyCheck = 0;
		for (ContinentModel continent : p_listOfContinents) {
			ArrayList<CountryModel> countriesInContinent = continent.getCountries();
			if (countriesInContinent.size() == 0) {
				System.out.println(
						"Validation Check Failed: There are no countries in the continent " + continent.getName());
				emptyCheck++;
			}
		}
		if (emptyCheck > 0) {
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
		boolean haveDuplicates = true;
		Set<String> l_mapSet = new HashSet<String>(p_mapData);
		if (l_mapSet.size() < p_mapData.size()) {
			haveDuplicates = true;
		} else {
			haveDuplicates = false;
		}
		return haveDuplicates;
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
		Stack<Integer> stack = new Stack<Integer>();
		int i, element;
		l_visited[p_source] = 1;
		stack.push(p_source);
		while (!stack.isEmpty()) {
			element = stack.pop();
			if (isSubGraph == false) {
				i = 1;
			} else {
				i = 0;
			}
			while (i < l_numberOfNodes) {
				if (p_adjacencyMatrix[element][i] == 1 && l_visited[i] == 0) {
					stack.push(i);
					l_visited[i] = 1;
				}
				i++;
			}
		}

		int count = 0;
		for (int v = 0; v < l_numberOfNodes; v++)
			if (l_visited[v] == 1) {
				count++;
			}

		if (count == l_numberOfNodes) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * To check if all the countries in a continent form a connected graph among
	 * themselves
	 * 
	 * @param p_listOfContinents The list of countries defined in the map
	 * @param p_listOfCountries  The complete list of continents defined in the map
	 * @param p_graph            a association graph specifying the connection
	 *                           between the countries
	 * @return true if map is connected graph else it returns false
	 */
	public boolean isCountryAConnectedGraph(ArrayList<ContinentModel> p_listOfContinents,
			ArrayList<CountryModel> p_listOfCountries, int[][] p_graph) {
		int disconnectedContinents = 0;
		for (ContinentModel continent : p_listOfContinents) {
			ArrayList<CountryModel> CM = continent.getCountries();
			ArrayList<Integer> countryIds = new ArrayList<Integer>();
			for (CountryModel l_country : continent.getCountries()) {
				countryIds.add(d_gameEngine.getMapState().getListOfCountries().indexOf(l_country));
			}
			;
			int countriesInThisContinent = countryIds.size();
			int[][] l_subgraph = new int[countriesInThisContinent][countriesInThisContinent];
			int i = 0;
			for (int id : countryIds) {

				for (int j = 0; j < countriesInThisContinent; j++) {

					l_subgraph[i][j] = p_graph[id][countryIds.get(j)];
				}
				i++;
			}
			Stack<Integer> stack = new Stack<Integer>();
			for (int countriesInContinent = 0; countriesInContinent < l_subgraph.length; countriesInContinent++) {
				boolean isDirected = validateGraph(l_subgraph, countriesInContinent, true);
				if (isDirected == false) {
					disconnectedContinents = +1;
					int countryId = countryIds.get(countriesInContinent);
					String country = p_listOfCountries.get(countryId).getName();
					System.out.println("Validation Check Failed: The country " + country + " in continent "
							+ continent.getName() + " does not form a connected graph");
				}
			}
		}
		if (disconnectedContinents == 0) {
			System.out.println("Validation Check: All the countries form a directed graph within their continent");
			return true;
		} else {
			System.out.println(
					"Validation Check Failed: Invalid Map,countries in a continent has to form a directed graph");
			return false;
		}
	}

}
