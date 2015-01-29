package vialab.SMT.swipekeyboard;

//standard library imports
import java.io.*;
import java.util.Collection;
import java.util.Vector;

/**
 * An object that can resolve swipes to possibly desired words
 */
public class DefaultSwipeResolver implements SwipeResolver{
	/**
	 * The list of words used for looking up matches.
	 */
	protected Vector<String> wordlist;

	/**
	 * Create a new swipe resolver using the word list contained in the file
	 * "resources/dictionary.txt.
	 */
	public DefaultSwipeResolver(){
		wordlist = new Vector<String>();
		InputStream wordfile =
			getClass().getResourceAsStream(
				"/resources/dictionary.txt");
		if( wordfile == null){
			System.err.println( "Could not find the default wordlist");
			return;}
		BufferedReader reader = new BufferedReader(
			new InputStreamReader( wordfile));
		String word = "";
		try {
			while( ( word = reader.readLine()) != null)
				wordlist.add( word);
			reader.close();
		}
		catch( IOException exception){
			exception.printStackTrace();}
	}

	/**
	 * Resolve a swipe string to a list of possible words. The swipe string must
	 * start on the first letter of the word, and end on the last. All the rest
	 * of the characters in the word must be hit in order. Duplicate characters
	 * in the word do not need to be duplicated in the swipe string. For example:
	 * "fod" would suggest "food".
	 * @param  swipe
	 * @return The list of suggestions for the given swipe string.
	 */
	public Collection<String> resolve( String swipe){
		Vector<String> results = new Vector<String>();
		//error checking
		int swipe_n = swipe.length();
		if( swipe_n == 0) return results;

		//all good, load useful info
		char first = swipe.charAt( 0);
		char last = swipe.charAt( swipe.length() - 1);

		//for every word we know
		for( String word : wordlist){
			//check first and last characters
			if( ! ( first == word.charAt( 0) &&
					last == word.charAt( word.length() - 1)))
				continue;
			//check if swipe can be reduced (or extended by repeated chars) to word
			int word_i = 1;
			int swipe_i = 1;
			int word_n = word.length();
			while( word_i < word_n && swipe_i < swipe_n)
				if( word.charAt( word_i) == swipe.charAt( swipe_i))
					word_i ++;
				else
					swipe_i ++;
			if( word_i == word_n && swipe_i == swipe_n - 1)
				results.add( word);
		}
		return results;
	}
}