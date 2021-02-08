package view;

import controller.GameEngine;

/**
 * This class is used to validate entered command. It will check if a command is
 * valid for current phase and it will also check the syntax and number of
 * parameters passed.
 */
class ValidateCommandView {
	String d_commandSeparator = " ";

	/**
	 * This function Validated the base command and calls respective function from ExecuteCommandView
	 * class
	 * 
	 * @param p_gameEngineObject Reference of the GameEngine
	 * @param p_command          The command that needs to be validated
	 * @param p_phase            Current phase
	 * @return null It returns nothing
	 */
	void isValidCommand(GameEngine p_gameEngineObject, String p_command, ConsoleView p_consoleViewObject) {
		String[] l_commandParameters = p_command.split(d_commandSeparator);
		int l_phase = p_consoleViewObject.getPhase();
		if (l_phase == 0) {
			if (l_commandParameters[0].equals(MapEditingCommandListForUser.EDITMAP.getCommandString())) {
				System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
				if (l_commandParameters.length == 2) {
					ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
					l_executeCVObject.readMapFile(p_gameEngineObject, l_commandParameters[1]);
					p_consoleViewObject.setPhase(1);
				} else {
					System.out.println("Incorrect command: filename not entered.");
				}
				return;
			} else if (l_commandParameters[0].equals(GamePlayCommandList.EDITMAP.getCommandString())) {
				System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
				if (l_commandParameters.length == 2) {
					ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
					l_executeCVObject.readMapFile(p_gameEngineObject, l_commandParameters[1]);
					p_consoleViewObject.setPhase(2);
				} else {
					System.out.println("Incorrect command: filename not entered.");
				}
				return;
			} else {
				System.out.println(
						"Invalid/Incorrect command: Map is empty. Either enter a command for editing a map or loading a map for game play.");
			}
		} else if (l_phase == 1) {
			for (MapEditingCommandListForUser l_commandParameter : MapEditingCommandListForUser.values()) {
				if (l_commandParameters[0].equals(l_commandParameter.getCommandString())) {
					System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");

					return;
				}
			}
			System.out.println("Invalid command: Please check your command");
		} else if (l_phase == 2) {
			for (GamePlayCommandList l_commandParameter : GamePlayCommandList.values()) {
				if (l_commandParameters[0].equals(l_commandParameter.getCommandString())) {
					System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");

					return;
				}
			}
			System.out.println("Invalid command: Please check your command");
		}
	}

	void hasValidMapEditingParameters(GameEngine p_gameEngineObject, String[] p_commandParameters,
			ConsoleView p_consoleViewObject) {

	}

	void hasValidGamePlayParameters(GameEngine p_gameEngineObject, String[] p_commandParameters,
			ConsoleView p_consoleViewObject) {

	}
}