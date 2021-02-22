package controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

import controller.GameEngine;
import controller.MapController;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.Order;
import model.PlayerModel;

/**
 * This class is used to test GameEngine class
 */
public class GameEngineTest {

	/**
	 * This function is used to test assign Reinforcements functionality
	 */
	@Test
	public void testAssignReinforcements() {
		Scanner l_dummyScannerObject = new Scanner(System.in);

		// Initializing test case
		GameEngine l_gameEngineTestCase = new GameEngine(l_dummyScannerObject);
		MapController l_mapControllerTestCase = new MapController(l_gameEngineTestCase);
		l_gameEngineTestCase.getMapState().getListOfContinents().add(new ContinentModel("Continent1", "red", 2));
		l_gameEngineTestCase.getMapState().getListOfContinents().add(new ContinentModel("Continent2", "blue", 4));
		ArrayList<ContinentModel> l_listOfCoontinents = l_gameEngineTestCase.getMapState().getListOfContinents();

		l_gameEngineTestCase.getPlayersState()
				.addPlayer(new PlayerModel("Player1", l_gameEngineTestCase, l_dummyScannerObject));
		l_gameEngineTestCase.getPlayersState()
				.addPlayer(new PlayerModel("Player2", l_gameEngineTestCase, l_dummyScannerObject));
		ArrayList<PlayerModel> l_listOfPlayers = l_gameEngineTestCase.getPlayersState().getPlayers();

		CountryModel l_country = new CountryModel(1, "Continent1Country1", l_listOfCoontinents.get(0),
				new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(2, "Continent1Country2", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(3, "Continent1Country3", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(4, "Continent2Country1", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(5, "Continent2Country2", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(6, "Continent2Country3", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(7, "Continent2Country4", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);

		// Initializing test values
		l_listOfPlayers.get(0).setReinforcementsArmies(3);
		l_listOfPlayers.get(1).setReinforcementsArmies(3);

		// Initializing tested case
		GameEngine l_gameEngineCase = new GameEngine(l_dummyScannerObject);
		MapController l_mapControllerCase = new MapController(l_gameEngineCase);
		l_gameEngineCase.getMapState().getListOfContinents().add(new ContinentModel("Continent1", "red", 2));
		l_gameEngineCase.getMapState().getListOfContinents().add(new ContinentModel("Continent2", "blue", 4));
		l_listOfCoontinents = l_gameEngineCase.getMapState().getListOfContinents();

		l_gameEngineCase.getPlayersState()
				.addPlayer(new PlayerModel("Player1", l_gameEngineCase, l_dummyScannerObject));
		l_gameEngineCase.getPlayersState()
				.addPlayer(new PlayerModel("Player2", l_gameEngineCase, l_dummyScannerObject));
		l_listOfPlayers = l_gameEngineCase.getPlayersState().getPlayers();

		l_country = new CountryModel(1, "Continent1Country1", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(2, "Continent1Country2", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(3, "Continent1Country3", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(4, "Continent2Country1", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(5, "Continent2Country2", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(6, "Continent2Country3", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(7, "Continent2Country4", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		l_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);

		// calling functions that assigns reinforcements
		l_gameEngineCase.assignReinforcements();

		// checking if both players got the reinforcements as per the test case
		assertEquals(l_gameEngineCase.getPlayersState().getPlayers().get(0).getReinforcementsArmies(),
				l_gameEngineTestCase.getPlayersState().getPlayers().get(0).getReinforcementsArmies());
		assertEquals(l_gameEngineCase.getPlayersState().getPlayers().get(1).getReinforcementsArmies(),
				l_gameEngineTestCase.getPlayersState().getPlayers().get(1).getReinforcementsArmies());
	}

	/**
	 * This function is used to check if players order are prevented if the army
	 * number exceeds player reinforcement number
	 */
	@Test
	public void testDeployCheck() {
		// Initializing test case
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		CountryModel l_country = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject);
		PlayerModel l_player = new PlayerModel("Test", l_gameEngine, l_scannerObject);
		l_player.addOwnedCountry(l_country);
		l_player.setReinforcementsArmies(5);

		// Executing function
		l_player.validateOrder("deploy TestCountry 2");
		l_player.validateOrder("deploy TestCountry 2");
		l_player.validateOrder("deploy TestCountry 2");

		// Check if only two order were issued
		assertEquals(2, l_player.getOrders().size());
	}

}
