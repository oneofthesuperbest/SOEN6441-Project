package view;

import controller.GameEngine;

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
	 * @param p_phase            Current phase
	 * @return null It returns nothing
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
					System.out.println("Valid parameters. Loading map for editing.");
					l_executeCVObject.readMapFile(p_gameEngineObject, l_commandParameters[1]);
					p_consoleViewObject.setPhase(1);
				} else {
					System.out.println("Incorrect command: filename not entered.");
				}
				return;
			} else if (l_commandParameters[0].equals(GamePlayCommandList.LOADMAP.getCommandString())) {
				System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
				if (l_commandParameters.length == 2) {
					System.out.println("Valid parameters. Loading map.");
					p_consoleViewObject.setPhase(2);
					return;
				} else {
					System.out.println("Incorrect command: filename not entered.");
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
		} else if (l_phase == 2) {
			// Phase 2 needs to be in controller
			for (GamePlayCommandList l_commandParameter : GamePlayCommandList.values()) {
				if (l_commandParameters[0].equals(l_commandParameter.getCommandString())) {
					System.out.println(
							"Valid base command. Checking if all the sub-commands and their parameters (if any) are valid...");

					return;
				}
			}
			System.out.println("Invalid command: Please check your command");
		}
	}

	/**
	 * This function is use to validate list of sub-commands and their parameters. Checking if sub commands are valid or
	 * parameters are of required type and number of parameters
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 * @param p_consoleViewObject This is the object to ConsoleView class
	 * @return null It returns nothing
	 */
	void hasValidMapEditingParameters(GameEngine p_gameEngineObject, String[] p_commandParameters,
			ConsoleView p_consoleViewObject) {
		if (p_commandParameters[0].equals(MapEditingCommandListForUser.SHOWMAP.getCommandString())) {
			// Call ShowMap functions
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.VALIDATEMAP.getCommandString())) {
			// Call ValidateMap functions
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.SAVEMAP.getCommandString())) {
			if (p_commandParameters.length == 2) {
				// Call ValidateMap function and based on the boolean value return call SaveMap
				// function
			} else {
				System.out.println("Incorrect command: filename not entered.");
			}
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITCONTINENT.getCommandString())) {
			// validate all sub-commands and parameters of editcontinent command
			int l_sunCommandIndex = 0;
			for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
				String l_commandOrParameter = p_commandParameters[l_index];
				l_sunCommandIndex++;
				if (l_commandOrParameter.equals(MapEditingCommandListForUser.ADD.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITCONTINENT.getAddCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else if (l_commandOrParameter.equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITCONTINENT.getRemoveCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else {
					System.out.printf("Invalid sub-command at position %d: Please check your list of sub commands",
							l_sunCommandIndex);
					System.out.println("");
					return;
				}
			}
			// Call EditContinent function
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITCOUNTRY.getCommandString())) {
			// validate all sub-commands and parameters of editcountry command
			int l_sunCommandIndex = 0;
			for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
				String l_commandOrParameter = p_commandParameters[l_index];
				l_sunCommandIndex++;
				if (l_commandOrParameter.equals(MapEditingCommandListForUser.ADD.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITCOUNTRY.getAddCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else if (l_commandOrParameter.equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITCOUNTRY.getRemoveCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else {
					System.out.printf("Invalid sub-command at position %d: Please check your list of sub commands",
							l_sunCommandIndex);
					System.out.println("");
					return;
				}
			}
			// Call EditCountry function
		} else if (p_commandParameters[0].equals(MapEditingCommandListForUser.EDITNEIGHBOR.getCommandString())) {
			// validate all sub-commands and parameters of editneighbor command
			int l_sunCommandIndex = 0;
			for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
				String l_commandOrParameter = p_commandParameters[l_index];
				l_sunCommandIndex++;
				if (l_commandOrParameter.equals(MapEditingCommandListForUser.ADD.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITNEIGHBOR.getAddCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else if (l_commandOrParameter.equals(MapEditingCommandListForUser.REMOVE.getCommandString())) {
					int[] l_numberOfRequiredParameters = MapEditingCommandListForUser.EDITNEIGHBOR.getRemoveCommandTypes();
					for(int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
						if(l_numberOfRequiredParameters[l_parameterIndex] == 0) {
							try {
								@SuppressWarnings("unused")
								int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
							} catch(NumberFormatException e) {
								System.out.println("Invalid parameter type: One of the parameter is not of type integer");
								return;
							} catch(ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid number of parameters: Missing parameters");
								return;
							}
						} else if(l_numberOfRequiredParameters[l_parameterIndex] == 1) {
							// We dont't have string parameters
						}
					}
					l_index += (l_numberOfRequiredParameters.length - 1);
				} else {
					System.out.printf("Invalid sub-command at position %d: Please check your list of sub commands",
							l_sunCommandIndex);
					System.out.println("");
					return;
				}
			}
			// Call EditNeighbour function
		}
	}

	void hasValidGamePlayParameters(GameEngine p_gameEngineObject, String[] p_commandParameters,
			ConsoleView p_consoleViewObject) {
		// This needs to be in Controller
	}
}