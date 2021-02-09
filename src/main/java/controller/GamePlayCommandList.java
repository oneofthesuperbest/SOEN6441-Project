package controller;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum GamePlayCommandList { 
	GAMEPLAYER("gameplayer") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
		 */
		public int[] getAddCommandTypes() {
			return new int[]{1}; 
		}
		
		/**
		 * This functions returns parameter requirement for -remove sub-command
		 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
		 */
		public int[] getRemoveCommandTypes() {
			return new int[]{1};
		}
	}, SHOWMAP("showmap"), ASSIGNCOUNTRIES("assigncountries"), LOADMAP("loadmap"), ADD("-add"), REMOVE("-remove");

	private String d_command;
	
	/**
	 * This constructor is used to initialize the string command for an enum
	 * 
	 * @param p_command
	 */
	GamePlayCommandList(String p_command) {
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
	 * This functions returns default parameter requirement for -add sub-command
	 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
	 */
	public int[] getAddCommandTypes() {
		return new int[] {};
	}
	
	/**
	 * This functions returns default parameter requirement for -remove sub-command
	 * @return ParameterTypes Number of parameters required and their types i.e: 1 for string and 0 for integer
	 */
	public int[] getRemoveCommandTypes() {
		return new int[] {};
	}
}