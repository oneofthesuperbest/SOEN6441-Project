package modelTest;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Adapter;
import controller.GameEngine;
import model.CountryModel;
import model.LogEntryBuffer;
import model.Order;
import model.Player;
import view.FileEntryLogger;

/**
 * This class is used to test AggressiveStrategy class
 */
public class AggressiveStrategyTest {
	static Order d_order;
	static CountryModel d_country;
	static CountryModel d_targetCountry;
	static Player d_player;
	
	/**
	 * Initialize test case
	 */
	@BeforeClass
	public static void init() {
		// Initializing test case
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		Adapter l_mapController = new Adapter(l_gameEngine);
		l_mapController.loadMapData("src/test/test_resources/testmap_3Countries_Domination.map", false, false);
		l_gameEngine.setPhase(3);
		d_player = new Player("TestAF", "aggressive", l_gameEngine, l_scannerObject);
		Player l_targetPlayer = new Player("Test1", "", l_gameEngine, l_scannerObject);
		d_country = l_gameEngine.getMapState().getListOfCountries().get(2);
		d_targetCountry = l_gameEngine.getMapState().getListOfCountries().get(1);
		d_player.addOwnedCountry(d_country);
		d_player.setReinforcementsArmies(4);
		d_country.setOwner(d_player);
		l_targetPlayer.addOwnedCountry(d_targetCountry);
		d_targetCountry.setOwner(l_targetPlayer);
		l_targetPlayer.addOwnedCountry(l_gameEngine.getMapState().getListOfCountries().get(0));
		l_gameEngine.getMapState().getListOfCountries().get(0).setOwner(l_targetPlayer);
	}
	
	/**
	 * This function tests if order was issued correctly
	 */
	@Test
	public void testExecute() {
		d_player.issueOrder();
		
		assertEquals(0, d_player.issueOrder());
		
		assertEquals(2, d_player.getOrders().size());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecuteOrder() {
		d_player.nextOrder();
		assertEquals(4, d_country.getArmies());
		
		d_player.nextOrder();
		assertEquals(0, d_country.getArmies());
		assertEquals(4, d_targetCountry.getArmies());
		assertEquals("TestAF", d_targetCountry.getOwner().getName());
	}
}
