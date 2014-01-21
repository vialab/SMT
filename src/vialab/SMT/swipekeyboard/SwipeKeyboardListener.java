package vialab.SMT.swipekeyboard;

/**
 * An interface for objects that can listen to a swipe keyboard
 */
public interface SwipeKeyboardListener{
	/**
	 * Invoked when a swipe has completed.
	 * @param event the SwipeKeyboardEvent describing the event
	 */
	public void swipeCompleted( SwipeKeyboardEvent event);
	/**
	 * Invoked when a swipe has started.
	 * @param event the SwipeKeyboardEvent describing the event
	 */
	public void swipeStarted( SwipeKeyboardEvent event);
	/**
	 * Invoked when a swipe has progressed.
	 * @param event the SwipeKeyboardEvent describing the event
	 */
	public void swipeProgressed( SwipeKeyboardEvent event);
}