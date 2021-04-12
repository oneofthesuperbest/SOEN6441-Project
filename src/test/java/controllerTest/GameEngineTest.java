package controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.GameEngine;
import model.ContinentModel;
import model.CoordinateModel;
import model.CountryModel;
import model.LogEntryBuffer;
import model.Player;
import view.FileEntryLogger;

/**
 * This class is used to test GameEngine class
 */
public class GameEngineTest {
	/**
	 * This holds the player object
	 */
	public static Player d_player;
	
	/**
	 * This holds the test game engine object
	 */
	public static GameEngine d_gameEngineCase;
	
	/**
	 * This holds the tested game engine object
	 */
	public static GameEngine d_gameEngineTestCase;

	/**
	 * Initialize test cases
	 */
	@BeforeClass
	public static void init() {
		// Initializing test case for reinforcements
		ContinentModel l_continent = new ContinentModel("TestContinent", "red", 2);
		CountryModel l_countryR = new CountryModel(1, "TestCountry", l_continent, new CoordinateModel(1, 2));
		Scanner l_scannerObject = new Scanner(System.in);
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		d_player = new Player("Test", "", l_gameEngine, l_scannerObject);
		d_player.addOwnedCountry(l_countryR);
		d_player.setReinforcementsArmies(5);
		
		// Initializing test case for reinforcements
		Scanner l_dummyScannerObject = new Scanner(System.in);
		d_gameEngineTestCase = new GameEngine(l_dummyScannerObject, d_logEntryBuffer, d_fileEntryLogger);
		d_gameEngineTestCase.getMapState().getListOfContinents().add(new ContinentModel("Continent1", "red", 2));
		d_gameEngineTestCase.getMapState().getListOfContinents().add(new ContinentModel("Continent2", "blue", 4));
		ArrayList<ContinentModel> l_listOfCoontinents = d_gameEngineTestCase.getMapState().getListOfContinents();
		d_gameEngineTestCase.getPlayersState()
				.addPlayer(new Player("Player1", "", d_gameEngineTestCase, l_dummyScannerObject));
		d_gameEngineTestCase.getPlayersState()
				.addPlayer(new Player("Player2", "", d_gameEngineTestCase, l_dummyScannerObject));
		ArrayList<Player> l_listOfPlayers = d_gameEngineTestCase.getPlayersState().getPlayers();

		CountryModel l_country = new CountryModel(1, "Continent1Country1", l_listOfCoontinents.get(0),
				new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(2, "Continent1Country2", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(3, "Continent1Country3", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(4, "Continent2Country1", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(5, "Continent2Country2", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(6, "Continent2Country3", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(7, "Continent2Country4", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineTestCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		// Initializing test values
		l_listOfPlayers.get(0).setReinforcementsArmies(3);
		l_listOfPlayers.get(1).setReinforcementsArmies(3);
		// Initializing tested case
		d_gameEngineCase = new GameEngine(l_dummyScannerObject, d_logEntryBuffer, d_fileEntryLogger);
		d_gameEngineCase.getMapState().getListOfContinents().add(new ContinentModel("Continent1", "red", 2));
		d_gameEngineCase.getMapState().getListOfContinents().add(new ContinentModel("Continent2", "blue", 4));
		l_listOfCoontinents = d_gameEngineCase.getMapState().getListOfContinents();
		d_gameEngineCase.getPlayersState().addPlayer(new Player("Player1", "", d_gameEngineCase, l_dummyScannerObject));
		d_gameEngineCase.getPlayersState().addPlayer(new Player("Player2", "", d_gameEngineCase, l_dummyScannerObject));
		l_listOfPlayers = d_gameEngineCase.getPlayersState().getPlayers();
		l_country = new CountryModel(1, "Continent1Country1", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(2, "Continent1Country2", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		l_country = new CountryModel(3, "Continent1Country3", l_listOfCoontinents.get(0), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(0).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(4, "Continent2Country1", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(5, "Continent2Country2", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(6, "Continent2Country3", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(1));
		l_listOfPlayers.get(1).addOwnedCountry(l_country);
		l_country = new CountryModel(7, "Continent2Country4", l_listOfCoontinents.get(1), new CoordinateModel(1, 2));
		d_gameEngineCase.getMapState().getListOfCountries().add(l_country);
		l_listOfCoontinents.get(1).getCountries().add(l_country);
		l_country.setOwner(l_listOfPlayers.get(0));
		l_listOfPlayers.get(0).addOwnedCountry(l_country);
		// calling functions that assigns reinforcements
		d_gameEngineCase.assignReinforcements();
	}

	/**
	 * This function is used to test assign Reinforcements functionality
	 */
	@Test
	public void testAssignReinforcements1() {
		// checking if first players got the reinforcements as per the test case
		assertEquals(d_gameEngineCase.getPlayersState().getPlayers().get(0).getReinforcementsArmies(),
				d_gameEngineTestCase.getPlayersState().getPlayers().get(0).getReinforcementsArmies());
	}
	
	/**
	 * This function is used to test assign Reinforcements functionality
	 */
	@Test
	public void testAssignReinforcements2() {
		// checking if second players got the reinforcements as per the test case
		assertEquals(d_gameEngineCase.getPlayersState().getPlayers().get(1).getReinforcementsArmies(),
				d_gameEngineTestCase.getPlayersState().getPlayers().get(1).getReinforcementsArmies());
	}

}
