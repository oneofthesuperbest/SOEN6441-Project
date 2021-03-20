package model;

/**
 * This interface is used to provide a framework for all types of orders
 */
public interface Order {
	void execute();
	boolean isValid();
	void printOrder();
}
