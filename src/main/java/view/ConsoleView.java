package view;

import java.util.Scanner;

import controller.GameEngine;
import model.LogEntryBuffer;

/**
 * This class provides the console to the user. This class allows user to give
 * commands via IDE console
 */
public class ConsoleView {
	private int d_phase; // 1 for Map editor phase and 2 for Game phase
	GameEngine d_gameEngineObject;
	// Observable LogEntryBuffer class to push log strings.
	LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
	// Observer FileEntryLogger which waits for notification from LogEntryBuffer
	FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);

	/**
	 * This functions initializes the console and prompts users for commands
	 */
	void startConsole() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		Scanner l_scannerObject = new Scanner(System.in);
		// Need to use the same Scanner object, as creating a new scanner object throws
		// NoSuchElementException
		d_gameEngineObject = new GameEngine(l_scannerObject);
		String l_command;
		while (true) {
			System.out.println("Enter your command");
			l_command = l_scannerObject.nextLine();
			l_VCVObject.checkCommand(d_gameEngineObject, l_command, null);

			if (d_gameEngineObject.getPhase().getString().equals("start-up")) {
				// if phase 2 then exit loop and call GameEngine loadmap
				break;
			}
		}

		System.out.println("Loading GameEngine console...");
		d_gameEngineObject.loadGameEngineConsole();
	}

	/**
	 * This function is used to set the phase to specified value
	 * 
	 * @param p_phase The phase you want to enter
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

	/**
	 * This function returns the LogEntryBuffer object which
	 * is used to add log entries.
	 * @return A LogEntryBuffer object which is used to push log entries into the file.
	 */
	LogEntryBuffer getLogEntryBuffer(){
		return d_logEntryBuffer;
	}
}
