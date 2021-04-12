import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import controllerTest.ControllerSuite;
import modelTest.ModelSuite;
import viewTest.ViewSuite;

/**
 * Test suit for all the controller test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ ControllerSuite.class, ModelSuite.class, ViewSuite.class })
public class MainSuite {
}
