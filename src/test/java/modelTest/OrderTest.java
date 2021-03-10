package modelTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.Order;
import model.Player;

/**
 * This class contains test cases for OrderTest class
 */
public class OrderTest {

	Order d_order;
	CountryModel d_country;

	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		d_country = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject);
		Player l_player = new Player("Test", l_gameEngine, l_scannerObject);
		l_player.addOwnedCountry(d_country);
		d_order = new Order("deploy", "TestCountry", 2, l_player);
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Executing function
		d_order.execute();

		// Check if 2 armies were deployed on the country
		assertEquals(2, d_country.getArmies());
	}

}
