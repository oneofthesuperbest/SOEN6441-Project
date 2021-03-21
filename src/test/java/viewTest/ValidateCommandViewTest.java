package viewTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.CommandList;
import view.ValidateCommandView;

/**
 * This class contains test cases for ValidateCommandView class
 */
public class ValidateCommandViewTest {
	
	ValidateCommandView d_VCV;
	
	/**
	 * Initialize test case
	 */
	@Before
	public void init() {
		// Initializing test case
		d_VCV = new ValidateCommandView();
	}

	/**
	 * This function is used to test Validation functionality
	 */
	@Test
	public void testValidation() {
		String[] l_str = { "IncorrectCommand", "-add" };

		// check if the above command return 0 as it is incorrect
		assertEquals(0, d_VCV.validateSubCommands(l_str, CommandList.EDITCONTINENT));
	}

}
