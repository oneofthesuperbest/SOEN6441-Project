package model;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum GamePlayCommandListForPlayer { 
	/**
	 * This is an enum for deploy command
	 */
	DEPLOY("deploy") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * @return Array of number of parameters required and their types i.e: 1 for string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[]{1, 0}; 
		}
	}, 
	
	/**
	 * This is an enum for showmap command
	 */
	SHOWMAP("showmap"), 
	
	/**
	 * This is an enum for stop issuing order
	 */
	STOP("");

	private String d_command;
	
	/**
	 * This constructor is used to initialize the string command for an enum
	 * 
	 * @param p_command The string value of the command held by the enum
	 */
	GamePlayCommandListForPlayer(String p_command) {
		this.d_command = p_command;
	}

	/**
	 * This function is used to get the string value of the command held by the enum
	 * 
	 * @return d_command The string value of the command held by the enum
	 */
	public String getCommandString() {
		return this.d_command;
	}
	
	/**
	 * This functions returns default parameter requirement
	 * @return Array of number of parameters required and their types i.e: 1 for string and 0 for integer
	 */
	public int[] getCommandTypes() {
		return new int[] {};
	}
}