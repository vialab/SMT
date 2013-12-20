package vialab.SMT.swipekeyboard;

//standard library imports
import java.util.Collection;

public class SwipeKeyboardEvent extends java.util.EventObject{
	private Collection<String> words;
	private SwipeResolver resolver;
	private String swipe;
	private Type type;

	public SwipeKeyboardEvent(
			Object source, Type type, String swipe, SwipeResolver resolver){
		super( source);
		this.type = type;
		this.swipe = swipe;
		this.resolver = resolver;
		this.words = null;
	}

	public Collection<String> getSuggestions(){
		if( words == null && resolver != null)
			words = resolver.resolve( swipe);
		return words;
	}
	public String getSwipeString(){
		return swipe;
	}

	public enum Type {
		SWIPE_COMPLETED, SWIPE_STARTED, SWIPE_PROGRESSED}
}