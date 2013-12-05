package vialab.SMT.swipekeyboard;

//standard library imports
import java.util.Collection;
import java.util.Vector;

public class DefaultSwipeResolver implements SwipeResolver{
	Vector<String> wordlist;

	public DefaultSwipeResolver(){
		wordlist = new Vector<String>();
		wordlist.add( "happy");
		wordlist.add( "birthday");
		wordlist.add( "to");
		wordlist.add( "you");
		wordlist.add( "erik");
	}

	public Collection<String> resolve( String swipe){
		Vector<String> results = new Vector<String>();
		int swipe_n = swipe.length();
		char first = swipe.charAt( 0);
		char last = swipe.charAt( swipe.length() - 1);
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