package vialab.SMT.event;

/**
 * The interface for recieving touch events.
 */
public interface TouchListener extends java.util.EventListener{
	/**
	 * Invoked when a touch as been created (pressed).
	 * @param touchEvent The event object containing all relevant data.
	 */
	public void handleTouchDown( TouchEvent touchEvent);
	/**
	 * Invoked when a touch as been destroyed (released).
	 * @param touchEvent The event object containing all relevant data.
	 */
	public void handleTouchUp( TouchEvent touchEvent);
	/**
	 * Invoked when a touch as been moved.
	 * @param touchEvent The event object containing all relevant data.
	 */
	public void handleTouchMoved( TouchEvent touchEvent);
}