package modelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for all the model test classes
 */
@RunWith(Suite.class)
@SuiteClasses({ DeployOrderTest.class, AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrderTest.class,
		BombOrderTest.class, NegotiateOrderTest.class, AggressiveStrategyTest.class, BenevolentStrategyTest.class,
		CheaterStrategyTest.class, RandomStrategyTest.class, PlayerStrategyTest.class })
public class ModelSuite {
}
