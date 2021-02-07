package view;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum MapEditingCommandListForUser {
	EDITCONTINENT("editcontinent") {
		public int[] getAddCommandTypes() {
			return new int[]{1, 0}; 
		}
		public int[] getRemoveCommandTypes() {
			return new int[]{1};
		}
	},
	EDITCOUNTRY("editcountry") {
		public int[] getAddCommandTypes() {
			return new int[]{1, 1}; 
		}
		public int[] getRemoveCommandTypes() {
			return new int[]{1};
		}
	}, 
	EDITNEIGHBOR("editneighbor") {
		public int[] getAddCommandTypes() {
			return new int[]{1, 1}; 
		}
		public int[] getRemoveCommandTypes() {
			return new int[]{1, 1};
		}
	}, SHOWMAP("showmap"), EDITMAP("editmap"), VALIDATEMAP("validatemap");
	
	private String d_command;
	
	/**
	 * This constructor is used to initialize the string command for an enum
	 * 
	 * @param p_command
	 */
	MapEditingCommandListForUser(String p_command) {
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
}