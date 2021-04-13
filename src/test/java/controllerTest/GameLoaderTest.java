package controllerTest;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
import model.LogEntryBuffer;
import view.FileEntryLogger;
import view.ValidateCommandView;

/**
 * This class is used to test GameLoader class
 */
public class GameLoaderTest {
	 GameEngine d_gameEngineObject;
	
	/**
	 * This function initializes the context
	 */
	@Before public void init() {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		d_gameEngineObject = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	}
	
	/**
	 * This function is used to test game load with incorrect map
	 */
	@Test
	public void testGameSave() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngineObject, "loadgame res/maps/brasil/brasil.map,res/maps/saved/newsaved.map", null);
		
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
	
	/**
	 * This function is used to test game load
	 */
	@Test
	public void testGameLoad() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngineObject, "loadgame src/test/test_resources/Game1.map", null);
		
		assertEquals("issue order", d_gameEngineObject.getPhase().getString());
	}
}
