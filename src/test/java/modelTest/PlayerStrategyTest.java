package modelTest;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.Adapter;
import controller.GameEngine;
import model.LogEntryBuffer;
import view.FileEntryLogger;
import view.ValidateCommandView;

/**
 * This class is used to test PlayerStrategy class
 */
public class PlayerStrategyTest {
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
		d_gameEngine.setPhase(2);
	}
	
	/**
	 * This function tests new player command
	 */
	@Test
	public void testExecute() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(1, l_VCVObject.checkCommand(d_gameEngine, "gameplayer -add Test human", null));
	}
	
	/**
	 * This function tests old player command
	 */
	@Test
	public void testOldExecute() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(0, l_VCVObject.checkCommand(d_gameEngine, "gameplayer -add Test", null));
	}
}
