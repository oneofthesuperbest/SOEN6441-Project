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
 * This class is used to test DefaultPhase class
 */
public class DefaultPhaseTest {
    GameEngine d_gameEngineObject;
	
	@Before public void init() {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		d_gameEngineObject = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	}
	
	@Test public void testDefaultPhase() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngineObject, "validatemap", null);
		
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
}
