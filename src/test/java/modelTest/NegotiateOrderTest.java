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
 * This class is used to test NegotiateOrder class
 */
public class NegotiateOrderTest {
	Order d_order;
	CountryModel d_country;
	CountryModel d_targetCountry;
	Player d_player;
	Player d_targetPlayer;
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
		Adapter l_mapController = new Adapter(d_gameEngine);
		l_mapController.loadMapData("src/test/test_resources/testmap_3Countries_Domination.map", false, false);
		d_gameEngine.setPhase(3);
		d_player = new Player("Test", "", d_gameEngine, l_scannerObject);
		d_gameEngine.getPlayersState().addPlayer(d_player);
		d_targetPlayer = new Player("TestTarget", "", d_gameEngine, l_scannerObject);
		d_gameEngine.getPlayersState().addPlayer(d_targetPlayer);
		d_country = d_gameEngine.getMapState().getListOfCountries().get(0);
		d_targetCountry = d_gameEngine.getMapState().getListOfCountries().get(1);
		d_player.addOwnedCountry(d_country);
		d_player.getCards().add(3);
		d_country.setOwner(d_player);
		d_targetPlayer.addOwnedCountry(d_targetCountry);
		d_targetCountry.setOwner(d_targetPlayer);
		d_country.setArmies(2);
		d_targetCountry.setArmies(1);
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngine, "negotiate TestTarget", this.d_player);
		this.d_player.nextOrder();
	}

	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testExecute() {
		// Executing function
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngine, "advance "+d_country.getName()+" "+d_targetCountry.getName()+" 2", this.d_player);
		this.d_player.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(1, d_targetCountry.getArmies());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testConquerExecute() {
		// Executing function
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngine, "advance "+d_country.getName()+" "+d_targetCountry.getName()+" 2", this.d_player);
		this.d_player.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(1, d_targetCountry.getArmies());
		
		
		// Check if country belongs to same player
		assertEquals("TestTarget", d_targetCountry.getOwner().getName());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testOtherPlayersCommand() {
		// Executing function
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngine, "advance "+d_targetCountry.getName()+" "+d_country.getName()+" 1", this.d_targetPlayer);
		this.d_targetPlayer.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(2, d_country.getArmies());
	}
	
	/**
	 * This function tests if order was executed correctly
	 */
	@Test
	public void testOtherPlayersConquerCommand() {
		// Executing function
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngine, "advance "+d_targetCountry.getName()+" "+d_country.getName()+" 1", this.d_targetPlayer);
		this.d_targetPlayer.nextOrder();

		// Check if 1 armies were remains on the country
		assertEquals(2, d_country.getArmies());
		
		// Check if country belongs to same player
		assertEquals(1, d_targetCountry.getArmies());
	}
}
