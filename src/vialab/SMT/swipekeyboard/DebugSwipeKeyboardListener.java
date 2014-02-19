package vialab.SMT.swipekeyboard;

/**
 * A debugging class. Simply prints any swipe's suggestions to the terminal.
 */
public class DebugSwipeKeyboardListener implements SwipeKeyboardListener{
	/**
	 * Prints out the swipe string and its suggestions to the terminal when a
	 * swipe has been completed
	 * @param event The swipe event that has occurred.
	 */
	public void swipeCompleted( SwipeKeyboardEvent event){
		System.out.printf(
			"Swipe %s resolved to: ",
			event.getSwipeString());
		for( String word : event.getSuggestions())
			System.out.printf( " %s", word);
		System.out.println();
	}
	/**
	 * Does nothing.
	 * @param event The swipe event that has occurred.
	 */
	public void swipeStarted( SwipeKeyboardEvent event){}
	/**
	 * Does nothing.
	 * @param event The swipe event that has occurred.
	 */
	public void swipeProgressed( SwipeKeyboardEvent event){}
}