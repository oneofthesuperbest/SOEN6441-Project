package viewTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
import view.ExecuteCommandView;

/**
 * This class contains test cases for ExecuteCommandView class
 */
public class ExecuteCommandViewTest {

	ExecuteCommandView d_ECV;
	GameEngine d_gameEngine;

	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		Scanner l_scannerObject = new Scanner(System.in);
		d_gameEngine = new GameEngine(l_scannerObject);
		d_ECV = new ExecuteCommandView();
	}

	/**
	 * This function is used to test loadmap functionality
	 */
	@Test
	public void testLoadMap() {
		// checks if the current command return false as the file is not present
		assertFalse(d_ECV.loadMapFile(d_gameEngine, "IncorrectFilename.map"));
	}

}
