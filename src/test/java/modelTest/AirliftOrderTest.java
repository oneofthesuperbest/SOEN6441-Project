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
 * This class is used to test AirliftOrder class
 */
public class AirliftOrderTest {
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
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		d_country = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		d_targetCountry = new CountryModel(2, "TargetCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		l_gameEngine.getMapState().getListOfContinents().add(l_continent);
		l_gameEngine.getMapState().getListOfCountries().add(d_targetCountry);
		l_gameEngine.getMapState().getListOfCountries().add(d_country);
		l_gameEngine.setPhase(3);
		d_player = new Player("Test", l_gameEngine, l_scannerObject);
		Player l_targetPlayer = new Player("Test1", l_gameEngine, l_scannerObject);
		d_player.addOwnedCountry(d_country);
		d_country.setOwner(d_player);
		l_targetPlayer.addOwnedCountry(d_targetCountry);
		d_targetCountry.setOwner(l_targetPlayer);
		d_country.setArmies(2);
		d_targetCountry.setArmies(2);
		this.d_player.getCards().add(2);
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(l_gameEngine, "airlift TestCountry TargetCountry 2", this.d_player);
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Executing function
		this.d_player.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(1, d_targetCountry.getArmies());
		
		// Check if country belongs to same player
		assertEquals("Test1", d_targetCountry.getOwner().getName());
		
		//check armies have been reduced
		assertEquals(0, d_country.getArmies());
	}
}
