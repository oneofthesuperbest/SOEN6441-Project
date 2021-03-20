package modelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for all the model test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ DeployOrderTest.class, AdvanceOrderTest.class, AirliftOrderTest.class })
public class ModelSuite {
}
