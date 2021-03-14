package controllerTest;

import static org.junit.Assert.*;

import controller.GameEngine;
import controller.MapController;
import model.ContinentModel;
import model.CountryModel;
import model.LogEntryBuffer;
import view.FileEntryLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to test MapController class functionalities
 */
public class MapControllerTest {

	LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
	FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
	GameEngine l_gameEngineObj = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	MapController l_mapController = new MapController(l_gameEngineObj);

	/**
	 * Load a valid map before every test case.
	 */
	@Before
	public void loadMapBeforeTest() {
		// load a valid map.
		l_mapController.loadMapData("src/test/test_resources/testmap.map", false, false);
	}

	/**
	 * Clear the map after every test case.
	 */
	@After
	public void clearMapAfterTest() {
		l_gameEngineObj.getMapState().clear();
	}

	/**
	 * Test if the map is a fully connected graph. - The entire graph must be fully
	 * connected.
	 */
	@Test
	public void mapValFullyConnectedTest() {
		// isolate a country.
		l_mapController.removeNeighbor("Alberta", "BC");
		assertFalse(l_mapController.isMapValid());
	}

	/**
	 * Test if Continent is fully connected. - The continent must be a fully
	 * connected sub graph.
	 */
	@Test
	public void mapValFullyConnectedContinentTest() {
		// disconnect countries in the continent.
		l_mapController.removeNeighbor("Cali", "LA");
		assertFalse(l_mapController.isMapValid());
	}

	/**
	 * Test if new Continent is added successfully.
	 */
	@Test
	public void addContinentTest() {
		// add a new continent.
		l_mapController.addContinent("NewContinent", 21);
		ArrayList<ContinentModel> l_continentList = l_gameEngineObj.getMapState().getListOfContinents();
		ContinentModel l_lastContinent = l_continentList.get(l_continentList.size() - 1);

		assertEquals("NewContinent", l_lastContinent.getName());
		assertEquals(21, l_lastContinent.getArmy());
	}

	/**
	 * Test if the country is added successfully.
	 */
	@Test
	public void addCountryTest() {
		// add a new country.
		l_mapController.addCountry("NewCountry", "Canada");
		ArrayList<CountryModel> l_countryList = l_gameEngineObj.getMapState().getListOfCountries();
		CountryModel l_lastCountry = l_countryList.get(l_countryList.size() - 1);

		assertEquals("NewCountry", l_lastCountry.getName());
		assertEquals("Canada", l_lastCountry.getContinent().getName());
	}

	/**
	 * Test if continent is empty. - A continent cannot be empty.
	 */
	@Test
	public void mapValEmptyContinentTest() {
		// add an empty country.
		l_mapController.addContinent("EmptyContinent", 21);
		assertFalse(l_mapController.isMapValid());
	}
}
