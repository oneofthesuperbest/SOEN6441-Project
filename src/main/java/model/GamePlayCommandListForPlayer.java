package model;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum GamePlayCommandListForPlayer { 
	DEPLOY("deploy") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[]{1, 0}; 
		}
	}, SHOWMAP("showmap"), STOP("");

	private String d_command;
	
	/**
	 * This constructor is used to initialize the string command for an enum
	 * 
	 * @param p_command
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
	 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
	 */
	public int[] getCommandTypes() {
		return new int[] {};
	}
}