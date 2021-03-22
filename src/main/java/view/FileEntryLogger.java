package view;

import model.LogEntryBuffer;
import model.Observable;
import java.io.FileWriter;
import java.io.IOException;

/**
 * An observer class that waits for notifications from the the LogEntryBuffer
 * class and writes its contents in a text file. In its current state, all the
 * logs are saved into the res/logs/logs.txt file.
 */
public class FileEntryLogger implements Observer {

	/**
	 * Constructor for the FileEntryLogger Class. Attaches `this` instance to a
	 * LogEntryBuffer object.
	 *
	 * @param p_logEntryBuffer Reference to the LogEntryBuffer object which needs to
	 *                         be observed.
	 */
	public FileEntryLogger(LogEntryBuffer p_logEntryBuffer) {
		p_logEntryBuffer.attach(this);
		clearLog();
	}

	/**
	 * Used to clear the log of the previous game
	 */
	void clearLog() {
		String l_directory = "res/logs/";
		String l_filePath = l_directory + "log.txt";
		try {
			FileWriter l_logWriter = new FileWriter(l_filePath, false);
			l_logWriter.write("");
			l_logWriter.close();

		} catch (IOException e) {
			System.out.println("An Error has occurred while adding log entry.");
		}
	}

	/**
	 * Write the current log entry to file after the view has been notified of a
	 * state change in the LogEntryBuffer model.
	 *
	 * @param p_o: Object that contains the log entry details.
	 */
	public void update(Observable p_o) {
		String l_timeStamp = ((LogEntryBuffer) p_o).getEntryTime();
		String l_logEntry = ((LogEntryBuffer) p_o).getLogEntry();
		String l_logEntryStr = "# " + l_timeStamp + " -- " + l_logEntry + "\n";

		logToFile(l_logEntryStr);
	}

	/**
	 * A Helper method to write the log entry to file.
	 *
	 * @param p_logEntry: A string representing a single log entry.
	 */
	private void logToFile(String p_logEntry) {
		String l_directory = "res/logs/";
		String l_filePath = l_directory + "log.txt";
		try {
			FileWriter l_logWriter = new FileWriter(l_filePath, true);
			l_logWriter.write(p_logEntry);
			l_logWriter.close();

		} catch (IOException e) {
			System.out.println("An Error has occurred while adding log entry.");
		}
	}
}