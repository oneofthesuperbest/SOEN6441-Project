package model;

/**
 * This class is the base/abstract strategy class for strategy pattern
 */
public abstract class Strategy {
	/**
	 * This method is used to implement issueOrder() functionality based on the strategy
	 * @return 1 if the order was issued successfully and 0 to indicate no stopping of issueOrders
	 */
	public abstract int issueOrder();
}
