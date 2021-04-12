package modelTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.LogEntryBuffer;
import model.Order;
import model.Player;
import view.FileEntryLogger;
import view.ValidateCommandView;

/**
 * This class is used to test DeployOrder class
 */
public class DeployOrderTest {
	Order d_order;
	CountryModel d_country;
	Player d_player;

	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		d_country = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		l_gameEngine.setPhase(3);
		d_player = new Player("Test", "", l_gameEngine, l_scannerObject);
		d_player.addOwnedCountry(d_country);
		d_player.setReinforcementsArmies(2);
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(l_gameEngine, "deploy TestCountry 2", this.d_player);
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Executing function
		this.d_player.nextOrder();

		// Check if 2 armies were deployed on the country
		assertEquals(2, d_country.getArmies());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testConquerExecute() {
		// Executing function
		this.d_player.nextOrder();

		// Check if 2 armies were deployed on the country
		assertEquals(2, d_country.getArmies());
		
		//check reinforcements have been reduced
		assertEquals(0, d_player.getReinforcementsArmies());
	}
}
