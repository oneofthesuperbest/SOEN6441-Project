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
 * This class is used to test StartUpPhase class
 */
public class StartUpPhaseTest {
	GameEngine d_gameEngineObject;
	ValidateCommandView l_VCVObject;

	/**
	 * This function initializes context
	 */
	@Before	public void init() {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		l_VCVObject = new ValidateCommandView();
		d_gameEngineObject = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	}

	/**
	 * This function is used to test validation and switching of phases
	 */
	@Test public void testDefaultPhase() {
		l_VCVObject.checkCommand(d_gameEngineObject, "loadmap res/maps/brasil/brasil.map", null);
		assertEquals("start-up", d_gameEngineObject.getPhase().getString());
		
		l_VCVObject.checkCommand(d_gameEngineObject, "assigncountries", null);
		assertEquals("issue order", d_gameEngineObject.getPhase().getString());
	}
}
