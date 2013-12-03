package vialab.SMT.swipekeyboard;

/**
 * The interface for recieving swipe key events. Not to be confused with
 * recieving swipe keyboard events.
 */
public interface SwipeKeyListener extends java.util.EventListener{
	/**
	 * Invoked when a swipe has been started on a swipe key
	 * @param swipeKeyEvent The event object containing all relevant data.
	 */
	public void swipeStarted( SwipeKeyEvent swipeKeyEvent);
	/**
	 * Invoked when a swipe has hit a swipe key
	 * @param swipeKeyEvent The event object containing all relevant data.
	 */
	public void swipeHit( SwipeKeyEvent swipeKeyEvent);
	/**
	 * Invoked when a swipe has finished on a swipe key
	 * @param swipeKeyEvent The event object containing all relevant data.
	 */
	public void swipeEnded( SwipeKeyEvent swipeKeyEvent);
}