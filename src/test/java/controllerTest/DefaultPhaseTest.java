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
	
    /**
	 * This function initializes the context
	 */
	@Before public void init() {
		LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();
		FileEntryLogger d_fileEntryLogger = new FileEntryLogger(d_logEntryBuffer);
		d_gameEngineObject = new GameEngine(new Scanner(System.in), d_logEntryBuffer, d_fileEntryLogger);
	}
	
	/**
	 * This function tests if phases are correctly initialized and transitioned
	 */
	@Test public void testDefaultPhase() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		l_VCVObject.checkCommand(d_gameEngineObject, "validatemap", null);
		
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
	
	/**
	 * This function tests if tournament works as expected
	 */
	@Test public void testTournament() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(1, l_VCVObject.checkCommand(d_gameEngineObject, "tournament -M res/maps/brasil/brasil.map,res/maps/saved/newsaved.map -P benevolent,cheater,random,aggressive -G 5 -D 20", null));
	}
	
	/**
	 * This function tests if tournament works as expected
	 */
	@Test public void testTournamentPhase() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(1, l_VCVObject.checkCommand(d_gameEngineObject, "tournament -M res/maps/brasil/brasil.map,res/maps/saved/newsaved.map -P benevolent,cheater,random,aggressive -G 5 -D 20", null));
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
	
	/**
	 * This function tests if invalid player tournament works as expected
	 */
	@Test public void testInvalidTournament() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(1, l_VCVObject.checkCommand(d_gameEngineObject, "tournament -M res/maps/brasil/brasil.map,res/maps/saved/newsaved.map -P benevolent,cheater,random,human -G 5 -D 20", null));
	}
	
	/**
	 * This function tests if invalid player tournament works as expected
	 */
	@Test public void testInvalidTournamentPhase() {
		ValidateCommandView l_VCVObject = new ValidateCommandView();
		assertEquals(1, l_VCVObject.checkCommand(d_gameEngineObject, "tournament -M res/maps/brasil/brasil.map,res/maps/saved/newsaved.map -P benevolent,cheater,random,human -G 5 -D 20", null));
		
		assertEquals("default", d_gameEngineObject.getPhase().getString());
	}
}
