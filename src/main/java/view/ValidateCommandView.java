package view;

import controller.GameEngine;
import controller.GamePlayCommandList;

/**
 * This class is used to validate entered command. It will check if a command is
 * valid for current phase and it will also check the syntax and number of
 * parameters passed.
 */
public class ValidateCommandView {
	String d_commandSeparator = " ";

	/**
	 * This function Validated the base command and calls respective function from
	 * ExecuteCommandView class
	 * 
	 * @param p_gameEngineObject Reference of the GameEngine
	 * @param p_command          The command that needs to be validated
	 * @param p_consoleViewObject Reference of ConsoleView
	 */
	void isValidCommand(GameEngine p_gameEngineObject, String p_command, ConsoleView p_consoleViewObject) {
		String[] l_commandParameters = p_command.split(d_commandSeparator);
		int l_phase = p_consoleViewObject.getPhase();
		if (l_phase == 0) {
			// Check if base command is editmap or loadmap, otherwise return
			if (l_commandParameters[0].equals(MapEditingCommandListForUser.EDITMAP.getCommandString())) {
				System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
				if (l_commandParameters.length == 2) {
					ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
					System.out.println("Valid parameters. Loading map for editing...");
					boolean readMapResult = l_executeCVObject.readMapFile(p_gameEngineObject, l_commandParameters[1]);
					if(readMapResult){
						p_consoleViewObject.setPhase(1);
					}
				} else {
					if(l_commandParameters.length < 2) {
						System.out.println("Incorrect command: filename not entered.");
					} else {
						System.out.println("Incorrect command: Extra parameters passed. editmap command only requires 1 paramater.");
					}
				}
				return;
			} else if (l_commandParameters[0].equals(GamePlayCommandList.LOADMAP.getCommandString())) {
				System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
				if (l_commandParameters.length == 2) {
					ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
					System.out.println("Valid parameters. Loading map...");
					boolean loadMapResult = l_executeCVObject.loadMapFile(p_gameEngineObject, l_commandParameters[1]);
					if(loadMapResult){
						p_consoleViewObject.setPhase(2);
					}
				} else {
					if(l_commandParameters.length < 2) {
						System.out.println("Incorrect command: filename not entered.");
					} else {
						System.out.println("Incorrect command: Extra parameters passed. loadmap command only requires 1 parameter.");
					}
				}
				return;
			} else {
				System.out.println(
						"Invalid/Incorrect command: Map is empty. Either enter a command for editing a map or loading a map for game play.");
			}
		} else if (l_phase == 1) {
			// Validate phase 1 commands
			for (MapEditingCommandListForUser l_commandParameter : MapEditingCommandListForUser.values()) {
				if (l_commandParameters[0].equals(l_commandParameter.getCommandString())) {
					System.out.println(
							"Valid base command. Checking if all the sub-commands and their parameters (if any) are valid...");
					hasValidMapEditingParameters(p_gameEngineObject, l_commandParameters, p_consoleViewObject);
					return;
				}
			}
			System.out.println("Invalid command: Please check your command");
		}
	}

	/**
	 * This function is use to validate list of sub-commands and their parameters.
	 * Checking if sub commands are valid or parameters are of required type and
	 * number of parameters
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 * @param p_consoleViewObject This is the object to ConsoleView class
	 */
	void hasValidMapEditingParameters(GameEngine p_gameEngineObject, String[] p_commandParameters,
			ConsoleView p_consoleViewObject) {
		if (p_commandParameters[0].equals(MapEditingCommandListForUser.SHOWMAP.getCommandString())) {
			// ------- Call ShowMap functions
			if(p_commandParameters.length > 1) {
				System.out.println("Incorrect command: Extra parameters passed. showmap command doesn't require a parameter.");
				return;
			}
			ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
			l_executeCVObject.showMap(p_gameEngineObject);

		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.VALIDATEMAP.getCommandString())) {
			// ------- Call ValidateMap functions
			if(p_commandParameters.length > 1) {
				System.out.println("Incorrect command: Extra parameters passed. validatemap command doesn't require a parameter.");
				return;
			}
			ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
			l_executeCVObject.validateMap(p_gameEngineObject);

		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.SAVEMAP.getCommandString())) {
			if (p_commandParameters.length == 2) {
				//------- Call ValidateMap function and based on the boolean value return call SaveMap
				ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
				boolean l_returnValue = l_executeCVObject.saveMap(p_gameEngineObject, p_commandParameters[1]);
				if(l_returnValue) {
					System.out.println("Moving out of map editing phase.");
					p_consoleViewObject.setPhase(0);
				}
			} else {
				if(p_commandParameters.length < 2) {
					System.out.println("Incorrect command: filename not entered.");
				} else {
					System.out.println("Incorrect command: Extra parameters passed. savemap command only requires 1 parameter.");
				}
			}
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITCONTINENT.getCommandString())) {
			// validate all sub-commands and parameters of editcontinent command
			System.out.println("Validating all sub-commands and parameters of editcontinent command...");
			int l_returnValue = validateSubCommands(p_commandParameters, MapEditingCommandListForUser.EDITCONTINENT);
			if (l_returnValue == 1) {
				// ------- Call EditContinent function
				ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
				l_executeCVObject.editContinent(p_gameEngineObject, p_commandParameters);

			}
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITCOUNTRY.getCommandString())) {
			// validate all sub-commands and parameters of editcountry command
			System.out.println("Validating all sub-commands and parameters of editcountry command...");
			int l_returnValue = validateSubCommands(p_commandParameters, MapEditingCommandListForUser.EDITCOUNTRY);
			if (l_returnValue == 1) {
				// ------- Call EditCountry function
				ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
				l_executeCVObject.editCountry(p_gameEngineObject, p_commandParameters);

			}
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITNEIGHBOR.getCommandString())) {
			// validate all sub-commands and parameters of editneighbor command
			System.out.println("Validating all sub-commands and parameters of editneighbor command...");
			int l_returnValue = validateSubCommands(p_commandParameters, MapEditingCommandListForUser.EDITNEIGHBOR);
			if (l_returnValue == 1) {
				// ------- Call EditNeighbour function
				ExecuteCommandView l_executeCVObject = new ExecuteCommandView();
				l_executeCVObject.editNeighbor(p_gameEngineObject, p_commandParameters);

			}
		}
	}

	/**
	 * This function is used to traverse through all the sub-commands for a specific main command and return 0 if
	 * any sub-commands or number of parameters passed for each sub-command is incorrect. Otherwise it returns 1
	 * @param p_commandParameters list of all the sub-commands
	 * @param p_mainCommand Enum pointer that corresponds to the main command
	 * @return Integer 0 if validation fails or 1 if it passes
	 */
	public int validateSubCommands(String[] p_commandParameters, MapEditingCommandListForUser p_mainCommand) {
		int l_sunCommandIndex = 0;
		for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
			String l_commandOrParameter = p_commandParameters[l_index];
			l_sunCommandIndex++;
			if (l_commandOrParameter.equals(MapEditingCommandListForUser.ADD.getCommandString())) {
				int[] l_numberOfRequiredParameters = p_mainCommand.getAddCommandTypes();
				for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
					if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
						try {
							@SuppressWarnings("unused")
							int l_testIfInteger = Integer
									.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
						} catch (NumberFormatException e) {
							if(p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.ADD.getCommandString()) || p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
								System.out.println("Invalid number of parameters: Missing parameter(s)");
								return 0;
							}
							System.out.println("Invalid parameter type: One of the parameter is not of type integer");
							return 0;
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("Invalid number of parameters: Missing parameters");
							return 0;
						}
					} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
						try {
							@SuppressWarnings("unused")
							String l_stringParamter = (p_commandParameters[(l_index + l_parameterIndex + 1)]);
							if(p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.ADD.getCommandString()) || p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
								System.out.println("Invalid number of parameters: Missing parameter(s)");
								return 0;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("Invalid number of parameters: Missing parameters");
							return 0;
						}
					}
				}
				l_index += (l_numberOfRequiredParameters.length);
			} else if (l_commandOrParameter.equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
				int[] l_numberOfRequiredParameters = p_mainCommand.getRemoveCommandTypes();
				for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
					if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
						try {
							@SuppressWarnings("unused")
							int l_testIfInteger = Integer
									.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
						} catch (NumberFormatException e) {
							if(p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.ADD.getCommandString()) || p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
								System.out.println("Invalid number of parameters: Missing parameter(s)");
								return 0;
							}
							System.out.println("Invalid parameter type: One of the parameter is not of type integer");
							return 0;
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("Invalid number of parameters: Missing parameters");
							return 0;
						}
					} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
						try {
							@SuppressWarnings("unused")
							String l_stringParamter = (p_commandParameters[(l_index + l_parameterIndex + 1)]);
							if(p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.ADD.getCommandString()) || p_commandParameters[(l_index + l_parameterIndex + 1)].equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
								System.out.println("Invalid number of parameters: Missing parameter(s)");
								return 0;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							System.out.println("Invalid number of parameters: Missing parameters");
							return 0;
						}
					}
				}
				l_index += (l_numberOfRequiredParameters.length);
			} else {
				System.out.printf("Invalid sub-command at position %d: Please check your list of sub commands",
						l_sunCommandIndex);
				System.out.println("");
				return 0;
			}
		}
		return 1;
	}
}