package model;

import java.util.Scanner;

/**
 * This class is the base/abstract strategy class for strategy pattern
 */
public abstract class Strategy {
	/**
	 * This method is used to implement issueOrder() functionality based on the strategy
	 * @param p_scObj Handle to the scanner object (To be used for 'Player' strategy behavior)
	 */
	public abstract void issueOrder(Scanner p_scObj);
}
