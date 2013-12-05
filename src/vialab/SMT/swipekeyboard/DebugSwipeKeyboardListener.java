package vialab.SMT.swipekeyboard;

public class DebugSwipeKeyboardListener implements SwipeKeyboardListener{
	public void wordSwiped( SwipeKeyboardEvent event){
		System.out.printf(
			"The swipe %s was resolved to the following words:\n\t{",
			event.getSwipeString());
		for( String word : event.getSuggestions())
			System.out.printf( " %s", word);
		System.out.println(" }");
	}
}