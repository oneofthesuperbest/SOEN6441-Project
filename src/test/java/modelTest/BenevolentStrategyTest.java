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

/**
 * This class is used to test BenevolentStrategy class
 */
public class BenevolentStrategyTest {
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
		d_player = new Player("TestAF", "benevolent", l_gameEngine, l_scannerObject);
		Player l_targetPlayer = new Player("Test1", "", l_gameEngine, l_scannerObject);
		d_country = l_gameEngine.getMapState().getListOfCountries().get(2);
		d_targetCountry = l_gameEngine.getMapState().getListOfCountries().get(1);
		d_player.addOwnedCountry(d_country);
		d_player.setReinforcementsArmies(2);
		d_country.setOwner(d_player);
		d_player.addOwnedCountry(d_targetCountry);
		d_targetCountry.setOwner(d_player);
		l_targetPlayer.addOwnedCountry(l_gameEngine.getMapState().getListOfCountries().get(0));
		l_gameEngine.getMapState().getListOfCountries().get(0).setOwner(l_targetPlayer);
		d_player.issueOrder();
		d_player.issueOrder();
	}

	/**
	 * This function tests if order was issued correctly
	 */
	@Test
	public void testIssueExecute1() {
		assertEquals(0, d_player.issueOrder());
	}

	/**
	 * This function tests if order was issued correctly
	 */
	@Test
	public void testIssueExecute2() {
		assertEquals(2, d_player.getOrders().size());
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecuteOrder1() {
		d_player.nextOrder();
		d_player.nextOrder();

		assertEquals(1, d_country.getArmies());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecuteOrder2() {
		d_player.nextOrder();
		d_player.nextOrder();

		assertEquals(1, d_country.getArmies());
		assertEquals(1, d_targetCountry.getArmies());
	}
}
