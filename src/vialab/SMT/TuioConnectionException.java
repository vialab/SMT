package vialab.SMT;

/**
 * An exception that indicates that for some reason, a Tuio connection could
 * not be made.
 */
public class TuioConnectionException extends RuntimeException {
	/**
	 * Create a new TuioConnectionException with the given message
	 * @param message The desired exception message
	 */
	public TuioConnectionException( String message){
		super( message);
	}
}