package model;

/**
 * This interface is used to provide a framework for all types of orders
 */
public abstract class Order {
	/**
	 * This function is used to execute the order
	 */
	public abstract void execute();

	/**
	 * This functions is used to check if the order is valid
	 * 
	 * @return true if valid else returns false
	 */
	public abstract boolean isValid();

	/**
	 * This function is used to print the effect of order
	 */
	public abstract void printOrder();

	/**
	 * This function is used to print error that occured while executing the order
	 * 
	 * @param p_errorMessage The message to be printed
	 */
	public abstract void printUnsuccessfulOrder(String p_errorMessage);

	/**
	 * Return player that issued the order
	 * 
	 * @return The object of the player
	 */
	public abstract Player getPlayer();
}
