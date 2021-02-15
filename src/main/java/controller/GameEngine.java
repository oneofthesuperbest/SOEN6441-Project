package controller;

import java.util.Scanner;
import model.MapState;
import model.PlayerModel;
import model.PlayersState;

/**
 * This class is the main game engine which carry out commands of the players
 */
public class GameEngine {
	private MapState d_mapState = new MapState();
	private PlayersState d_playerState = new PlayersState();
	String d_commandSeparator = " ";

	/**
	 * Get the current map state.
	 * @return Current map state.
	 */
	public MapState getMapState() {
		return d_mapState;
	}
	
	/**
	 * Get the current players state.
	 * @return Current players state.
	 */
	public PlayersState getPlayersState() {
		return d_playerState;
	}
	
	/**
	 * Used to load the GameEngine console for game play phase
	 * @return none
	 */
	public void loadGameEngine() {
		System.out.println("GameEngine console loaded.");
		ValidateCommandController l_VCVObject = new ValidateCommandController();
		@SuppressWarnings("resource")
		Scanner l_scannerObject = new Scanner(System.in);
		while(true) {
			System.out.println("Enter your command");
			String l_command = l_scannerObject.nextLine();
			l_VCVObject.isValidCommand(l_command, this);
		}
	}
	
	/**
	 * Used to add/remove players 
	 * @param p_commandList
	 */
	public void addRemovePlayers(String[] p_commandList) {
		for(int l_index = 1; l_index < p_commandList.length; l_index++) {
			if(p_commandList[l_index].equals(GamePlayCommandList.ADD.getCommandString())) {
				l_index++; 
				d_playerState.addPlayer(new PlayerModel(p_commandList[l_index]));
				System.out.println("Player '" + p_commandList[l_index] + "' is added");
			} else {
				l_index++; 
				int l_returnValue = d_playerState.removePlayer(p_commandList[l_index]);
				if(l_returnValue == 0) {
					System.out.println("Player '" + p_commandList[l_index] + "' not found");
				} else {
					System.out.println("Player '" + p_commandList[l_index] + "' is removed");
				}
			}
		}
	}
}
