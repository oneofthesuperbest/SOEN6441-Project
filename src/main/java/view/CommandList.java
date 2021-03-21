package view;

/**
 * This enum is used to store list of valid commands for map editing phase.
 */
public enum CommandList {
	/**
	 * This is an enum for loadmap command
	 */
	LOADMAP("loadmap"),
	
	/**
	 * This is an enum for gameplayer command
	 */
	GAMEPLAYER("gameplayer") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getAddCommandTypes() {
			return new int[] { 1 };
		}

		/**
		 * This functions returns parameter requirement for -remove sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getRemoveCommandTypes() {
			return new int[] { 1 };
		}
	},
	
	/**
	 * This is an enum for assigncountries command
	 */
	ASSIGNCOUNTRIES("assigncountries"),
	
	/**
	 * This is an enum for editcontinent command
	 */
	EDITCONTINENT("editcontinent") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getAddCommandTypes() {
			return new int[] { 1, 0 };
		}

		/**
		 * This functions returns parameter requirement for -remove sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getRemoveCommandTypes() {
			return new int[] { 1 };
		}
	},

	/**
	 * This is an enum for editcountry command
	 */
	EDITCOUNTRY("editcountry") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getAddCommandTypes() {
			return new int[] { 1, 1 };
		}

		/**
		 * This functions returns parameter requirement for -remove sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getRemoveCommandTypes() {
			return new int[] { 1 };
		}
	},

	/**
	 * This is an enum for editneighbor command
	 */
	EDITNEIGHBOR("editneighbor") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getAddCommandTypes() {
			return new int[] { 1, 1 };
		}

		/**
		 * This functions returns parameter requirement for -remove sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getRemoveCommandTypes() {
			return new int[] { 1, 1 };
		}
	},

	/**
	 * This is an enum for showmap command
	 */
	SHOWMAP("showmap"),

	/**
	 * This is an enum for editmap command
	 */
	EDITMAP("editmap"),

	/**
	 * This is an enum for savemap command
	 */
	SAVEMAP("savemap"),

	/**
	 * This is an enum for validatemap command
	 */
	VALIDATEMAP("validatemap"),

	/**
	 * This is an enum for add command
	 */
	ADD("-add"),

	/**
	 * This is an enum for remove command
	 */
	REMOVE("-remove"),
	
	/**
	 * This is an enum for deploy command
	 */
	DEPLOY("deploy") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1, 0 };
		}
	},
	
	/**
	 * This is an enum for advance command
	 */
	ADVANCE("advance") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1, 1, 0 };
		}
	},
	
	/**
	 * This is an enum for bomb command
	 */
	BOMB("bomb") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1};
		}
	},
	
	/**
	 * This is an enum for blockade command
	 */
	BLOCKADE("blockade") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1};
		}
	},
	
	/**
	 * This is an enum for airlift command
	 */
	AIRLIFT("airlift") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1, 1, 0 };
		}
	},
	
	/**
	 * This is an enum for negotiate command
	 */
	NEGOTIATE("negotiate") {
		/**
		 * This functions returns parameter requirement for -add sub-command
		 * 
		 * @return Array of number of parameters required and their types i.e: 1 for
		 *         string and 0 for integer
		 */
		public int[] getCommandTypes() {
			return new int[] { 1};
		}
	},
	
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
	CommandList(String p_command) {
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

	/**
	 * This functions returns default parameter requirement for -add sub-command
	 * 
	 * @return ParameterTypes Number of parameters required and their types i.e: 1
	 *         for string and 0 for integer
	 */
	public int[] getAddCommandTypes() {
		return new int[] {};
	}

	/**
	 * This functions returns default parameter requirement for -remove sub-command
	 * 
	 * @return Array of number of parameters required and their types i.e: 1 for
	 *         string and 0 for integer
	 */
	public int[] getRemoveCommandTypes() {
		return new int[] {};
	}
	
	/**
	 * This functions returns default parameter requirement
	 * 
	 * @return Array of number of parameters required and their types i.e: 1 for
	 *         string and 0 for integer
	 */
	public int[] getCommandTypes() {
		return new int[] {};
	}
}