package controllerTest;

import java.util.Scanner;

import org.junit.Test;

import controller.GameEngine;
import controller.ValidateCommandController;

/**
 * This class test ValidateCommandContoller class functionalities
 */
public class ValidateCommandControllerTest {

	/**
	 * This functions tests command validation functionality
	 */
	@Test
	public void testValidation() {
		Scanner l_scannerObject = new Scanner(System.in);
		ValidateCommandController l_VCC = new ValidateCommandController();
		GameEngine l_gameEngine = new GameEngine(l_scannerObject);

		// check if the function returns null and doesn't throw an error due to calling
		// relevant functions for the command
		l_VCC.isValidCommand("IncorrectCommand", l_gameEngine);
	}

}
