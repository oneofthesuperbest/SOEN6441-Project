package modelTest;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.Adapter;
import controller.GameEngine;
import model.CountryModel;
import model.LogEntryBuffer;
import model.Order;
import model.Player;
import view.FileEntryLogger;
import view.ValidateCommandView;

/**
 * This class is used to test BombOrder class
 */
public class BombOrderTest {
	Order d_order;
	CountryModel d_country;
	CountryModel d_targetCountry;
	Player d_player;

	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		Adapter l_mapController = new Adapter(l_gameEngine);
		l_mapController.loadMapData("src/test/test_resources/testmap_3Countries_Domination.map", false, false);
		l_gameEngine.setPhase(3);
		d_player = new Player("Test", "", l_gameEngine, l_scannerObject);
		Player l_targetPlayer = new Player("Test1", "", l_gameEngine, l_scannerObject);
		d_country = l_gameEngine.getMapState().getListOfCountries().get(0);
		d_targetCountry = l_gameEngine.getMapState().getListOfCountries().get(2);
		d_player.addOwnedCountry(d_country);
		d_player.getCards().add(0);
		d_country.setOwner(d_player);
		l_targetPlayer.addOwnedCountry(d_targetCountry);
		d_targetCountry.setOwner(l_targetPlayer);
		d_country.setArmies(2);
		d_targetCountry.setArmies(2);
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(l_gameEngine, "bomb "+d_targetCountry.getName(), this.d_player);
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testOrderExecute1() {
		// Executing function
		this.d_player.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(1, d_targetCountry.getArmies());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testOrderExecute2() {
		// Executing function
		this.d_player.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(1, d_targetCountry.getArmies());
		
		// Check if country belongs to same player
		assertEquals("Test1", d_targetCountry.getOwner().getName());
	}
}
