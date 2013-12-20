package vialab.SMT.swipekeyboard;

public class DebugSwipeKeyboardListener implements SwipeKeyboardListener{
	public void swipeCompleted( SwipeKeyboardEvent event){
		System.out.printf(
			"Swipe %s resolved to: ",
			event.getSwipeString());
		for( String word : event.getSuggestions())
			System.out.printf( " %s", word);
		System.out.println();
	}
	public void swipeStarted( SwipeKeyboardEvent event){}
	public void swipeProgressed( SwipeKeyboardEvent event){}
}