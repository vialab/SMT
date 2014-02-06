package vialab.SMT.swipekeyboard;

//local imports
import vialab.SMT.*;

/**
 * An event class that describes parts of a swipe gesture.
 */
public class SwipeKeyEvent extends java.util.EventObject {
	////////////////////
	// private fields //
	////////////////////
	/**
	 * Defines the type of Swipe event that occured.
	 * Must be one of: SWIPE_STARTED, SWIPE_HIT, or SWIPE_ENDED.
	 */
	private int id;
	/**
	 * The key code of the key involved in the event.
	 */
	private int keyCode;
	/**
	 * The key char of the key involved in the event.
	 */
	private char keyChar;
	/**
	 * The location of the key on the keyboard.
	 * See java.awt.event.KeyEvent's keyLocation field for details.
	 */
	private int keyLocation;
	/**
	 * The touch involved in the event.
	 */
	private Touch touch;

	///////////////
	// constants //
	///////////////
	// used integer constants instead of enum to allow easier extension.
	/**
	 * One of the possible values for the id field. Indicates the beginning of
	 * the swipe.
	 */
	public static final int SWIPE_STARTED = 1;
	/**
	 * One of the possible values for the id field. Indicates a setp in the swipe.
	 */
	public static final int SWIPE_HIT = 2;
	/**
	 * One of the possible values for the id field. Indicates the end of the
	 * swipe.
	 */
	public static final int SWIPE_ENDED = 3;

	/**
	 * Constructor for this class.
	 * @param  source      The object that invoked this event.
	 * @param  id          Defines the type of Swipe event that occured. Must be
	 *                     one of: SWIPE_STARTED, SWIPE_HIT, or SWIPE_ENDED.
	 * @param  keyCode     The key code of the key involved in the event.
	 * @param  keyChar     The key char of the key involved in the event.
	 * @param  keyLocation The location of the key on the keyboard. See
	 *                     java.awt.event.KeyEvent's keyLocation field for
	 *                     details.
	 * @param  touch       The touch involved in the event.
	 */
	public SwipeKeyEvent( Object source, int id, int keyCode, char keyChar,
			int keyLocation, Touch touch){
		super( source);
		this.id = id;
		this.keyCode = keyCode;
		this.keyChar = keyChar;
		this.keyLocation = keyLocation;
		this.touch = touch;
	}

	///////////////////////
	// accessor methods //
	///////////////////////
	/**
	 * Returns the character represented by this step in the swipe.
	 * @return The character represented by this step in the swipe.
	 */
	public char getKeyChar(){
		return keyChar;
	}
	/**
	 * Returns the key code represented by the associated key in the swipe.
	 * @return The key code represented by the associated key in the swipe.
	 */
	public int getKeyCode(){
		return keyCode;
	}
	/**
	 * Returns the touch invovled in this step of the swipe.
	 * @return The touch invovled in this step of the swipe.
	 */
	public Touch getTouch(){
		return touch;
	}
}