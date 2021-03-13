package model;

import java.util.Date;

/**
 * An observable class which acts as a buffer for the log data.
 * On addition of a new log entry using the `addLogEntry` method
 * of this class, all the observers are notified and the log entries
 * are made at required locations.
 */
public class LogEntryBuffer extends Observable{
    private String d_entryTime;
    private String d_logEntry;

    /**
     * This method is used to add new log entries to the
     * @param p_logEntry A string containing the log entry
     *                   describing the action that was
     *                   completed.
     */
    public void addLogEntry(String p_logEntry){
        String l_date = new Date().toString();
        d_logEntry = p_logEntry;
        d_entryTime = l_date;

        notifyObservers(this);
    }

    /**
     * A getter method to get the timestamp for the current
     * log entry.
     *
     * @return A string with the timestamp for the current log entry.
     */
    public String getEntryTime() {
        return d_entryTime;
    }

    /**
     * A getter method to get the current
     * log entry.
     *
     * @return A string with the current log entry.
     */
    public String getLogEntry() {
        return d_logEntry;
    }
}
