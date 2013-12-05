package vialab.SMT.swipekeyboard;

//standard library imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class DefaultSwipeResolver implements SwipeResolver{
	protected Vector<String> wordlist;

	public DefaultSwipeResolver()
			throws FileNotFoundException {
		wordlist = new Vector<String>();
		File wordfile = new File("resources/dictionary.txt");
		BufferedReader reader = new BufferedReader(
			new FileReader( wordfile));
		String word = "";
		try {
			while( ( word = reader.readLine()) != null)
				wordlist.add( word);
			reader.close();
		}
		catch( IOException exception){}
	}

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