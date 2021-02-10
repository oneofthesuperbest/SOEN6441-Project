package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.ContinentModel;
import model.CountryModel;
import model.MapState;

/**
 * This class is the main game engine which carry out commands of the players
 */
public class GameEngine {
	private MapState d_mapState = new MapState();

	/**
	 * Get the current map state.
	 * @return Current map state.
	 */
	public MapState getMapState() {
		return d_mapState;
	}
}
