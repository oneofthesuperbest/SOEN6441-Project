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
	 * @param p_o: view to be added to the list of observers to be notified.
	 */
	public void attach(Observer p_o) {
		this.observers.add(p_o);
	}

	/**
	 * detach a view from the model.
	 * 
	 * @param p_o: view to be removed from the list of observers.
	 */
	public void detach(Observer p_o) {
		if (!observers.isEmpty()) {
			observers.remove(p_o);
		}
	}

	/**
	 * Notify all the views attached to the model.
	 * 
	 * @param p_observable: object that contains the information to be observed.
	 */
	public void notifyObservers(Observable p_observable) {
		for (Observer l_observer : observers) {
			l_observer.update(p_observable);
		}
	}
}
