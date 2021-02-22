package viewTest;

import static org.junit.Assert.*;

import org.junit.Test;

import view.MapEditingCommandListForUser;
import view.ValidateCommandView;

/**
 * This class contains test cases for ValidateCommandView class
 */
public class ValidateCommandViewTest {

	/**
	 * This function is used to test Validation functionality
	 */
	@Test public void testValidation() {
		ValidateCommandView l_VCV = new ValidateCommandView();
		String[] l_str = {"IncorrectCommand","-add"};
		
		//check if the above command return 0 as it is incorrect
		assertEquals(0, l_VCV.validateSubCommands(l_str, MapEditingCommandListForUser.EDITCONTINENT));
	}

}
