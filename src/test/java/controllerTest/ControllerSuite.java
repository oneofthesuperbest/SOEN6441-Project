package controllerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for all the controller test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ GameEngineTest.class, MapControllerTest.class })
public class ControllerSuite {
}
