package view;

import java.util.Scanner;

import controller.GameEngine;
import model.LogEntryBuffer;

/**
 * This class provides the console to the user. This class allows user to give
 * commands via IDE console
 */
public class ConsoleView {
	GameEngine d_gameEngineObject;

	/**
	 * This functions initializes the console and prompts users for commands
	 */
	void startConsole() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		Scanner l_scannerObject = new Scanner(System.in);
		// Need to use the same Scanner object, as creating a new scanner object throws
		// NoSuchElementException
		// Observable LogEntryBuffer class to push log strings.
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		// Observer FileEntryLogger which waits for notification from LogEntryBuffer
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		d_gameEngineObject = new GameEngine(l_scannerObject, d_logEntryBuffer, d_fileEntryLogger);
		String l_command;
		while (true) {
			System.out.println("Enter your command");
			l_command = l_scannerObject.nextLine();
			l_VCVObject.checkCommand(d_gameEngineObject, l_command, null);

			if (d_gameEngineObject.getPhase().getString().equals("start-up") || d_gameEngineObject.getPhase().getString().equals("issue order")) {
				// if phase 2 then call GameEngine loadmap
				System.out.println("Loading GameEngine console...");
				d_gameEngineObject.loadGameEngineConsole();
			}
		}
	}
}
