package view;

import model.Observable;

/**
 * Interface class for the Observer, which forces all views to implement the
 * update method.
 */
public interface Observer {

	/**
	 * method to be implemented that reacts to the notification generally by
	 * interrogating the model object and displaying its newly updated state.
	 * 
	 * @param p_o: Reference to the subject class which is being observed for
	 *             changes.
	 */
	public void update(Observable p_o);
}
