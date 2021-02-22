package controller;

/**
 * This class is used to validate entered command. It will check if a command is
 * valid for current phase and it will also check the syntax and number of
 * parameters passed.
 */
public class ValidateCommandController {
	String d_commandSeparator = " ";

	/**
	 * This function Validated the base command and calls respective function from
	 * ExecuteCommandView class
	 * 
	 * @param p_gameEngineObject Reference of the GameEngine
	 * @param p_command          The command that needs to be validated
	 */
	public void isValidCommand(String p_command, GameEngine p_gameEngineObject) {
		String[] l_commandParameters = p_command.split(d_commandSeparator);
		for (GamePlayCommandList l_commandParameter : GamePlayCommandList.values()) {
			if (l_commandParameters[0].equals(l_commandParameter.getCommandString())) {
				if (l_commandParameters[0].equals(GamePlayCommandList.LOADMAP.getCommandString())) {
					System.out.println("Invalid command: Map is already loaded...");
					return;
				}
				System.out.println(
						"Valid base command. Checking if all the sub-commands and their parameters (if any) are valid...");
				hasValidGamePlayParameters(p_gameEngineObject, l_commandParameters);
				return;
			}
		}
		System.out.println("Invalid command: Please check your command");
	}

	/**
	 * This function is use to validate list of sub-commands and their parameters.
	 * Checking if sub commands are valid or parameters are of required type and
	 * number of parameters
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 */
	void hasValidGamePlayParameters(GameEngine p_gameEngineObject, String[] p_commandParameters) {
		if (p_commandParameters[0].equals(GamePlayCommandList.SHOWMAP.getCommandString())) {
			MapController l_mapController = new MapController(p_gameEngineObject);
			l_mapController.showMapForGamePlay();
		} else if (p_commandParameters[0].equals(GamePlayCommandList.GAMEPLAYER.getCommandString())) {
			// validate all sub-commands and parameters of gameplayer command
			System.out.println("Validating all sub-commands and parameters of gameplayer command...");
			int l_sunCommandIndex = 0;
			for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
				String l_commandOrParameter = p_commandParameters[l_index];
				l_sunCommandIndex++;
				if (l_commandOrParameter.equals(GamePlayCommandList.ADD.getCommandString())) {
					int[] l_numberOfRequiredParameters = GamePlayCommandList.GAMEPLAYER.getAddCommandTypes();
					for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer
										.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch (NumberFormatException e) {
								System.out
										.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							try {
								@SuppressWarnings("unused")
								String l_stringParamter = (p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						}
					}
					l_index += (l_numberOfRequiredParameters.length);
				} else if (l_commandOrParameter.equals(GamePlayCommandList.REMOVE.getCommandString())) {
					int[] l_numberOfRequiredParameters = GamePlayCommandList.GAMEPLAYER.getRemoveCommandTypes();
					for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer
										.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch (NumberFormatException e) {
								System.out
										.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							try {
								@SuppressWarnings("unused")
								String l_stringParamter = (p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						}
					}
					l_index += (l_numberOfRequiredParameters.length);
				} else {
					System.out.printf("Invalid sub-command at position %d: Please check your list of sub commands",
							l_sunCommandIndex);
					System.out.println("");
					return;
				}
			}
			p_gameEngineObject.addRemovePlayers(p_commandParameters);
		}
	}
}