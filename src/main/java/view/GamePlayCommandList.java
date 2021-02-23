package view;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum GamePlayCommandList {
	/**
	 * This is an enum for loadmap command
	 */
	LOADMAP("loadmap");

	private String d_command;

	/**
	 * This constructor is used to initialize the string command for an enum
	 * 
	 * @param p_command The string value of the command
	 */
	GamePlayCommandList(String p_command) {
		this.d_command = p_command;
	}

	/**
	 * This function is used to get the string value of the command held by the enum
	 * 
	 * @return The string value of the command held by the enum
	 */
	public String getCommandString() {
		return this.d_command;
	}
}