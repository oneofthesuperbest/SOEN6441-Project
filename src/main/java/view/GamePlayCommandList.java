package view;

public enum GamePlayCommandList {
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

	GamePlayCommandList(String p_command) {
		this.d_command = p_command;
	}

	private String d_command;

	public String getCommandString() {
		return this.d_command;
	}
}