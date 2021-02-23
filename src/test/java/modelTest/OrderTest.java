package modelTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import controller.GameEngine;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.Order;
import model.PlayerModel;

/**
 * This class contains test cases for OrderTest class
 */
public class OrderTest {

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Initializing test case
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		CountryModel l_country = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject);
		PlayerModel l_player = new PlayerModel("Test", l_gameEngine, l_scannerObject);
		l_player.addOwnedCountry(l_country);

		// Executing function
		Order l_order = new Order("deploy", "TestCountry", 2, l_player);
		l_order.execute();

		// Check if 2 armies were deployed on the country
		assertEquals(2, l_country.getArmies());
	}

}
