package vialab.SMT.swipekeyboard;

//standard library imports
import java.util.Collection;

public class SwipeKeyboardEvent extends java.util.EventObject{
	private Collection<String> words;
	private String swipe;

	public SwipeKeyboardEvent(
			Object source, String swipe, Collection<String> words){
		super( source);
		this.swipe = swipe;
		this.words = words;
	}

	public Collection<String> getSuggestions(){
		return words;
	}
	public String getSwipeString(){
		return swipe;
	}
}