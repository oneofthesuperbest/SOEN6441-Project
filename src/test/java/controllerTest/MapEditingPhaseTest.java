package controllerTest;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.GameEngine;
import model.LogEntryBuffer;
import view.FileEntryLogger;
import view.ValidateCommandView;

/**
 * This class is used to test MapEditingPhase class
 */
public class MapEditingPhaseTest {
	GameEngine d_gameEngineObject;
	ValidateCommandView l_VCVObject;

	/**
	 * This function initializes the context
	 */
	@Before public void init() {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		l_VCVObject = new ValidateCommandView();
		d_gameEngineObject = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
		
		l_VCVObject.checkCommand(d_gameEngineObject, "editmap res/maps/brasil/brasil.map", null);
	}

	/**
	 * This function tests if commands are validated and phases are correctly initialized and transitioned
	 */
	@Test public void testDefaultPhase() {
		assertEquals("map editing", d_gameEngineObject.getPhase().getString());
	}
	
	/**
	 * This function tests if commands are validated and phases are correctly initialized and transitioned
	 */
	@Test public void testSavePhase() {
		l_VCVObject.checkCommand(d_gameEngineObject, "savemap res/maps/saved/TestCase.map 1", null);
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
}
