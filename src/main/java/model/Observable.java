package model;

import java.util.ArrayList;
import java.util.List;
import view.Observer;

/**
 *
 */
public class Observable {
	private List<Observer> observers = new ArrayList<Observer>();

	/**
	 * attach a view to the model.
	 * 
	 * @param o: view to be added to the list of observers to be notified.
	 */
	public void attach(Observer o) {
		this.observers.add(o);
	}

	/**
	 * detach a view from the model.
	 * 
	 * @param o: view to be removed from the list of observers.
	 */
	public void detach(Observer o) {
		if (!observers.isEmpty()) {
			observers.remove(o);
		}
	}
 
	/**
	 * Notify all the views attached to the model.
	 * 
	 * @param observable: object that contains the information to be observed.
	 */
	public void notifyObservers(Observable observable) {
		for (Observer observer : observers) {
			observer.update(observable);
		}
	}
}
