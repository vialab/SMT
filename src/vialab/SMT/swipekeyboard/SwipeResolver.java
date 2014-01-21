package vialab.SMT.swipekeyboard;

/**
 * An interface for objects that can resolve swipe strings into possible words.
 */
public interface SwipeResolver {
	/**
	 * Given the swipe string, return a collection of words that the user likely
	 * were trying to 'type'.
	 * @param  swipe the characters that the swipe hit, in order.
	 * @return Words that the user were likely trying to type.
	 */
	public java.util.Collection<String> resolve( String swipe);
}