package viewTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for all the view test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ ExecuteCommandViewTest.class, ValidateCommandViewTest.class})
public class ViewSuite {
}
