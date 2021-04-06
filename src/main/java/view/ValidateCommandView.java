package view;

import controller.GameEngine;
import model.AdvanceOrder;
import model.AirliftOrder;
import model.BlockadeOrder;
import model.BombOrder;
import model.DeployOrder;
import model.NegotiateOrder;
import model.Player;

/**
 * This class is used to validate entered command. It will check if a command is
 * valid for current phase and it will also check the syntax and number of
 * parameters passed.
 */
public class ValidateCommandView {
	String d_commandSeparator = " ";

	/**
	 * This functions is used to check if the base command is valid or not
	 * 
	 * @param p_gameEngineObject The object of GameEngine class
	 * @param p_command          The command entered by user
	 * @param p_player           The player who issued the command
	 * @return Greater than 1 if valid else 0
	 */
	public int checkCommand(GameEngine p_gameEngineObject, String p_command, Player p_player) {
		String[] l_commandParameters = p_command.split(d_commandSeparator);
		if (p_command.equals(CommandList.STOP.getCommandString())) {
			return p_gameEngineObject.getPhase().stop();
		} else if (l_commandParameters[0].equals(CommandList.EDITMAP.getCommandString())) {
			// Check if base command is editmap
			System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
			if (l_commandParameters.length == 2) {
				p_gameEngineObject.getPhase().editMap(l_commandParameters[1]);
			} else {
				if (l_commandParameters.length < 2) {
					System.out.println("Incorrect command: filename not entered.");
				} else {
					System.out.println(
							"Incorrect command: Extra parameters passed. editmap command only requires 1 paramater.");
				}
			}
		} else if (l_commandParameters[0].equals(CommandList.LOADMAP.getCommandString())) {
			// Check if base command is loadmap
			System.out.println("Valid base command. Checking if all the parameters (if any) are valid...");
			if (l_commandParameters.length == 2) {
				p_gameEngineObject.getPhase().loadMap(l_commandParameters[1]);
			} else {
				if (l_commandParameters.length < 2) {
					System.out.println("Incorrect command: filename not entered.");
				} else {
					System.out.println(
							"Incorrect command: Extra parameters passed. loadmap command only requires 1 parameter.");
				}
				return 0;
			}
		} else if (l_commandParameters[0].equals(CommandList.TOURNAMENT.getCommandString())) {
			// validate all sub-commands and parameters of tournament command
			System.out.println("Validating all sub-commands and parameters of tournament command...");
			int l_returnValue = validateSubCommands(l_commandParameters, CommandList.TOURNAMENT);
			if (l_returnValue == 1) {
				p_gameEngineObject.getPhase().startTournament(l_commandParameters);
				return 1;
			}
		} else {
			int l_returnValue = isValidMapEditing(p_gameEngineObject, l_commandParameters);
			if (l_returnValue == 0) {
				l_returnValue = isValidGamePlay(p_gameEngineObject, l_commandParameters);
				if (l_returnValue == 0) {
					l_returnValue = isValidOrderCommand(p_gameEngineObject, l_commandParameters, p_player);
					if (l_returnValue == 0) {
						System.out.println("Invalid command: Please check your command");
					}
				}
			}
			return l_returnValue;
		}
		return 1;
	}

	/**
	 * This function is use to check if command is valid map editing command
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 * @return 1 if successful else 0
	 */
	int isValidMapEditing(GameEngine p_gameEngineObject, String[] p_commandParameters) {
		if (p_commandParameters[0].equals(CommandList.SHOWMAP.getCommandString())) {
			// ------- Call ShowMap functions
			if (p_commandParameters.length > 1) {
				System.out.println(
						"Incorrect command: Extra parameters passed. showmap command doesn't require a parameter.");
			}
			p_gameEngineObject.getPhase().showMap();
			// Returning 3 to indicate showmap is not an order
			return 3;
		} else if (p_commandParameters[0].equals(CommandList.VALIDATEMAP.getCommandString())) {
			// ------- Call ValidateMap functions
			if (p_commandParameters.length > 1) {
				System.out.println(
						"Incorrect command: Extra parameters passed. validatemap command doesn't require a parameter.");
			}
			p_gameEngineObject.getPhase().validate();
			return 1;
		} else if (p_commandParameters[0].equals(CommandList.SAVEMAP.getCommandString())) {
			if (p_commandParameters.length == 2) {
				// ------- Call ValidateMap function and based on the boolean value return call
				// SaveMap
				p_gameEngineObject.getPhase().saveMap(p_commandParameters[1]);
				return 1;
			} else {
				if (p_commandParameters.length < 2) {
					System.out.println("Incorrect command: filename not entered.");
				} else {
					System.out.println(
							"Incorrect command: Extra parameters passed. savemap command only requires 1 parameter.");
				}
			}
		} else if (p_commandParameters[0].equals(CommandList.EDITCONTINENT.getCommandString())) {
			// validate all sub-commands and parameters of editcontinent command
			System.out.println("Validating all sub-commands and parameters of editcontinent command...");
			int l_returnValue = validateSubCommands(p_commandParameters, CommandList.EDITCONTINENT);
			if (l_returnValue == 1) {
				// ------- Call EditContinent function
				p_gameEngineObject.getPhase().editContinent(p_commandParameters);
				return 1;
			}
		} else if (p_commandParameters[0].equals(CommandList.EDITCOUNTRY.getCommandString())) {
			// validate all sub-commands and parameters of editcountry command
			System.out.println("Validating all sub-commands and parameters of editcountry command...");
			int l_returnValue = validateSubCommands(p_commandParameters, CommandList.EDITCOUNTRY);
			if (l_returnValue == 1) {
				// ------- Call EditCountry function
				p_gameEngineObject.getPhase().editCountry(p_commandParameters);
				return 1;
			}
		} else if (p_commandParameters[0].equals(CommandList.EDITNEIGHBOR.getCommandString())) {
			// validate all sub-commands and parameters of editneighbor command
			System.out.println("Validating all sub-commands and parameters of editneighbor command...");
			int l_returnValue = validateSubCommands(p_commandParameters, CommandList.EDITNEIGHBOR);
			if (l_returnValue == 1) {
				// ------- Call EditNeighbour function
				p_gameEngineObject.getPhase().editNeighbor(p_commandParameters);
				return 1;
			}
		}
		return 0;
	}

	/**
	 * This function is use to check if command is valid game player command
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 * @return 1 if successful else 0
	 */
	int isValidGamePlay(GameEngine p_gameEngineObject, String[] p_commandParameters) {
		if (p_commandParameters[0].equals(CommandList.GAMEPLAYER.getCommandString())) {
			// validate all sub-commands and parameters of gameplayer command
			System.out.println("Validating all sub-commands and parameters of gameplayer command...");
			int l_returnValue = validateSubCommands(p_commandParameters, CommandList.GAMEPLAYER);
			if (l_returnValue == 1) {
				// ------- Call add/remove players function
				p_gameEngineObject.getPhase().addPlayers(p_commandParameters);
				return 1;
			}
		} else if (p_commandParameters[0].equals(CommandList.ASSIGNCOUNTRIES.getCommandString())) {
			p_gameEngineObject.getPhase().startGame();
			return 1;
		}
		return 0;
	}

	/**
	 * This function is use to check if command is valid order command
	 * 
	 * @param p_gameEngineObject  This is the main GameEngine object
	 * @param p_commandParameters This is the list of sub-commands and their
	 *                            parameters
	 * @param p_player            The player who issued the command
	 * @return 0 if unsuccessful, 1 if successful and 2 if the command was to stop
	 */
	int isValidOrderCommand(GameEngine p_gameEngineObject, String[] p_commandParameters, Player p_player) {
		if (p_commandParameters[0].equals(CommandList.DEPLOY.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.DEPLOY);
			if (l_returnValue == 1) {
				System.out.println("Issuing deploy order");
				return p_gameEngineObject.getPhase().delop(new DeployOrder(p_commandParameters[1],
						Integer.parseInt(p_commandParameters[2]), p_player, p_gameEngineObject));
			}
		} else if (p_commandParameters[0].equals(CommandList.ADVANCE.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.ADVANCE);
			if (l_returnValue == 1) {
				System.out.println("Issuing advance order");
				return p_gameEngineObject.getPhase()
						.advance(new AdvanceOrder(p_commandParameters[1], p_commandParameters[2],
								Integer.parseInt(p_commandParameters[3]), p_player, p_gameEngineObject));
			}
		} else if (p_commandParameters[0].equals(CommandList.BOMB.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.BOMB);
			if (l_returnValue == 1) {
				System.out.println("Issuing bomb order");
				return p_gameEngineObject.getPhase()
						.bomb(new BombOrder(p_commandParameters[1], p_player, p_gameEngineObject));
			}
		} else if (p_commandParameters[0].equals(CommandList.BLOCKADE.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.BLOCKADE);
			if (l_returnValue == 1) {
				System.out.println("Issuing blockade order");
				return p_gameEngineObject.getPhase()
						.blockade(new BlockadeOrder(p_commandParameters[1], p_player, p_gameEngineObject));
			}
		} else if (p_commandParameters[0].equals(CommandList.AIRLIFT.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.AIRLIFT);
			if (l_returnValue == 1) {
				System.out.println("Issuing airlift order");
				return p_gameEngineObject.getPhase()
						.airlift(new AirliftOrder(p_commandParameters[1], p_commandParameters[2],
								Integer.parseInt(p_commandParameters[3]), p_player, p_gameEngineObject));
			}
		} else if (p_commandParameters[0].equals(CommandList.NEGOTIATE.getCommandString())) {
			int l_returnValue = validateOrderParameters(p_commandParameters, CommandList.NEGOTIATE);
			if (l_returnValue == 1) {
				System.out.println("Issuing negotiate order");
				return p_gameEngineObject.getPhase()
						.negotiate(new NegotiateOrder(p_commandParameters[1], p_player, p_gameEngineObject));
			}
		}
		return 0;
	}

	/**
	 * This function is used to validate all the parameters in player order command
	 * 
	 * @param p_commandParameters The command in array form
	 * @param p_mainCommand       The pointer to the type of order
	 * @return 1 if valid command else 0
	 */
	int validateOrderParameters(String[] p_commandParameters, CommandList p_mainCommand) {
		int[] l_numberOfRequiredParameters = p_mainCommand.getCommandTypes();
		for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
			if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
				try {
					@SuppressWarnings("unused")
					int l_testIfInteger = Integer.parseInt(p_commandParameters[(l_parameterIndex + 1)]);
				} catch (NumberFormatException e) {
					System.out.println("Invalid parameter type: One of the parameter is not of type integer");
					return 0;
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Invalid number of parameters: Missing parameters");
					return 0;
				}
			} else if (l_numberOfRequiredParameters[l_parameterIndex] == 1) {
				try {
					@SuppressWarnings("unused")
					String l_stringParamter = (p_commandParameters[(l_parameterIndex + 1)]);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Invalid number of parameters: Missing parameters");
					return 0;
				}
			}
		}
		if (p_commandParameters.length > (l_numberOfRequiredParameters.length + 1)) {
			System.out.println(
					"Invalid number of parameters: Extra parameter(s) present. deploy command only requires 2 parameters.");
			return 0;
		}
		return 1;
	}

	/**
	 * This function is used to traverse through all the sub-commands for a specific
	 * main command and return 0 if any sub-commands or number of parameters passed
	 * for each sub-command is incorrect. Otherwise it returns 1
	 * 
	 * @param p_commandParameters list of all the sub-commands
	 * @param p_mainCommand       Enum pointer that corresponds to the main command
	 * @return Integer 0 if validation fails or 1 if it passes
	 */
	public int validateSubCommands(String[] p_commandParameters, CommandList p_mainCommand) {
		int l_sunCommandIndex = 0;
		for (int l_index = 1; l_index < p_commandParameters.length; l_index++) {
			String l_commandOrParameter = p_commandParameters[l_index];
			l_sunCommandIndex++;
			if (l_commandOrParameter.equals(CommandList.ADD.getCommandString())
					|| l_commandOrParameter.equals(CommandList.M.getCommandString())
					|| l_commandOrParameter.equals(CommandList.P.getCommandString())) {
				int[] l_numberOfRequiredParameters = l_commandOrParameter.equals(CommandList.ADD.getCommandString())
						? p_mainCommand.getAddCommandTypes()
						: p_mainCommand.getMCommandTypes();
				for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
					if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
						try {
							@SuppressWarnings("unused")
							int l_testIfInteger = Integer
									.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
						} catch (NumberFormatException e) {
							if (p_commandParameters[(l_index + l_parameterIndex + 1)]
									.equals(CommandList.ADD.getCommandString())
									|| p_commandParameters[(l_index + l_parameterIndex + 1)]
											.equals(CommandList.REMOVE.getCommandString())) {
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
							if (p_commandParameters[(l_index + l_parameterIndex + 1)]
									.equals(CommandList.ADD.getCommandString())
									|| p_commandParameters[(l_index + l_parameterIndex + 1)]
											.equals(CommandList.REMOVE.getCommandString())) {
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
			} else if (l_commandOrParameter.equals(CommandList.REMOVE.getCommandString())
					|| l_commandOrParameter.equals(CommandList.G.getCommandString())
					|| l_commandOrParameter.equals(CommandList.D.getCommandString())) {
				int[] l_numberOfRequiredParameters = l_commandOrParameter.equals(CommandList.REMOVE.getCommandString())
						? p_mainCommand.getRemoveCommandTypes()
						: p_mainCommand.getGCommandTypes();
				for (int l_parameterIndex = 0; l_parameterIndex < l_numberOfRequiredParameters.length; l_parameterIndex++) {
					if (l_numberOfRequiredParameters[l_parameterIndex] == 0) {
						try {
							@SuppressWarnings("unused")
							int l_testIfInteger = Integer
									.parseInt(p_commandParameters[(l_index + l_parameterIndex + 1)]);
						} catch (NumberFormatException e) {
							if (p_commandParameters[(l_index + l_parameterIndex + 1)]
									.equals(CommandList.ADD.getCommandString())
									|| p_commandParameters[(l_index + l_parameterIndex + 1)]
											.equals(CommandList.REMOVE.getCommandString())) {
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
							if (p_commandParameters[(l_index + l_parameterIndex + 1)]
									.equals(CommandList.ADD.getCommandString())
									|| p_commandParameters[(l_index + l_parameterIndex + 1)]
											.equals(CommandList.REMOVE.getCommandString())) {
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