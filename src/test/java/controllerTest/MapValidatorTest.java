package controllerTest;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.Adapter;
import controller.GameEngine;
import model.LogEntryBuffer;
import view.FileEntryLogger;

/**
 * This class is used to test MapValidator class
 */
public class MapValidatorTest {
	Adapter d_mapController;
	GameEngine d_gameEngine;

	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		d_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		d_mapController = new Adapter(d_gameEngine);
		// Executing function
		d_mapController.loadMapData("src/test/test_resources/testmap_3CountriesInvalid.map", true, false);
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Check if continent were added
		assertEquals(0, d_gameEngine.getMapState().getListOfContinents().size());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testCountriesExecute() {
		// Check if countries were added
		assertEquals(0, d_gameEngine.getMapState().getListOfCountries().size());
	}
}
