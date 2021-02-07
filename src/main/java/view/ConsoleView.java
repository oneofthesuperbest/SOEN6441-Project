package view;

import java.util.Scanner;

import controller.GameEngine;

/**
 * This class provides the console to the user. This class allows user to 
 * give commands via IDE console
 */
public class ConsoleView {
	private int d_phase;	// 1 for Map editor phase and 2 for Game phase
	GameEngine d_gameEngineObject;
	
	/**
	 * This functions initializes the console and prompts users for commands
	 * 
	 * @return null It returns nothing
	 */
	void startConsole() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		d_gameEngineObject = new GameEngine();
		@SuppressWarnings("resource")
		Scanner l_scannerObject = new Scanner(System.in);
		while(true) {
			System.out.println("Please enter your command");
			String l_command = l_scannerObject.nextLine();
			l_VCVObject.isValidCommand(d_gameEngineObject, l_command, this);
		}
	}
	
	/**
	 * This function is used to set the phase to specified value
	 * 
	 * @param p_phase The phase you want to enter
	 * @return null It returns nothing
	 */
	void setPhase(int p_phase) {
		this.d_phase = p_phase;
	}
	
	/**
	 * This function returns the current phase
	 * 
	 * @return Integer value held by the variable d_phase
	 */
	int getPhase() {
		return this.d_phase;
	}
}
