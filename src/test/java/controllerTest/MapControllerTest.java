package controllerTest;

import static org.junit.Assert.*;

import controller.Adapter;
import controller.GameEngine;
import controller.MapController;
import controller.MapValidator;
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
	GameEngine d_gameEngineObj = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	MapController d_mapController = new MapController(d_gameEngineObj);
	Adapter d_adapter = new Adapter(d_gameEngineObj);
	MapValidator d_mapValidator = new MapValidator(d_gameEngineObj);

	/**
	 * Load a valid map before every test case.
	 */
	@Before
	public void loadMapBeforeTest() {
		// load a valid map.
		d_adapter.loadMapData("src/test/test_resources/testmap.map", false, false);
	}

	/**
	 * Clear the map after every test case.
	 */
	@After
	public void clearMapAfterTest() {
		d_gameEngineObj.getMapState().clear();
	}

	/**
	 * Test if the map is a fully connected graph. - The entire graph must be fully
	 * connected.
	 */
	@Test
	public void mapValFullyConnectedTest() {
		// isolate a country.
		d_mapController.removeNeighbor("Alberta", "BC");
		assertFalse(d_mapValidator.isMapValid());
	}

	/**
	 * Test if Continent is fully connected. - The continent must be a fully
	 * connected sub graph.
	 */
	@Test
	public void mapValFullyConnectedContinentTest() {
		// disconnect countries in the continent.
		d_mapController.removeNeighbor("Cali", "LA");
		assertFalse(d_mapValidator.isMapValid());
	}

	/**
	 * Test if new Continent is added successfully.
	 */
	@Test
	public void addContinentTest() {
		// add a new continent.
		d_mapController.addContinent("NewContinent", 21);
		ArrayList<ContinentModel> l_continentList = d_gameEngineObj.getMapState().getListOfContinents();
		ContinentModel l_lastContinent = l_continentList.get(l_continentList.size() - 1);

		assertEquals("NewContinent", l_lastContinent.getName());
	}
	
	/**
	 * Test if new Continent is added successfully.
	 */
	@Test
	public void addContinentArmyTest() {
		// add a new continent.
		d_mapController.addContinent("NewContinent", 21);
		ArrayList<ContinentModel> l_continentList = d_gameEngineObj.getMapState().getListOfContinents();
		ContinentModel l_lastContinent = l_continentList.get(l_continentList.size() - 1);

		assertEquals(21, l_lastContinent.getArmy());
	}

	/**
	 * Test if the country is added successfully.
	 */
	@Test
	public void addCountryTest() {
		// add a new country.
		d_mapController.addCountry("NewCountry", "Canada");
		ArrayList<CountryModel> l_countryList = d_gameEngineObj.getMapState().getListOfCountries();
		CountryModel l_lastCountry = l_countryList.get(l_countryList.size() - 1);

		assertEquals("NewCountry", l_lastCountry.getName());
	}
	
	/**
	 * Test if the country is added successfully.
	 */
	@Test
	public void addCountryContinentTest() {
		// add a new country.
		d_mapController.addCountry("NewCountry", "Canada");
		ArrayList<CountryModel> l_countryList = d_gameEngineObj.getMapState().getListOfCountries();
		CountryModel l_lastCountry = l_countryList.get(l_countryList.size() - 1);

		assertEquals("Canada", l_lastCountry.getContinent().getName());
	}

	/**
	 * Test if continent is empty. - A continent cannot be empty.
	 */
	@Test
	public void mapValEmptyContinentTest() {
		// add an empty country.
		d_mapController.addContinent("EmptyContinent", 21);
		assertFalse(d_mapValidator.isMapValid());
	}
}
