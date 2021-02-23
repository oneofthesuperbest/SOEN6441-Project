package viewTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import controller.GameEngine;
import view.ExecuteCommandView;

/**
 * This class contains test cases for ExecuteCommandView class
 */
public class ExecuteCommandViewTest {

	/**
	 * This function is used to test loadmap functionality
	 */
	@Test
	public void testLoadMap() {
		Scanner l_scannerObject = new Scanner(System.in);
		GameEngine l_gameEngine = new GameEngine(l_scannerObject);
		ExecuteCommandView l_ECV = new ExecuteCommandView();

		// checks if the current command return false as the file is not present
		assertFalse(l_ECV.loadMapFile(l_gameEngine, "IncorrectFilename.map"));
	}

}
