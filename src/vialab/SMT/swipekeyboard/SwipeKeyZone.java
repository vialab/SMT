package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

//processing imports
import processing.core.PVector;

//local imports
import vialab.SMT.*;

/**
 * This class defines a zone that represents a key in a swipe keyboard.
 **/
public class SwipeKeyZone extends KeyZone {
	////////////////////////////
	// protected major fields //
	////////////////////////////
	/**
	 * This object's list of SwipeKeyListeners
	 */
	protected Vector<SwipeKeyListener> swipeListeners;

	///////////////////
	// debug fields //
	///////////////////
	/**
	 * Enables and disables debug print statements
	 */
	private static final boolean debug = false;

	///////////////////
	// Constructors //
	///////////////////
	/**
	 * Basic default constructor, with the minimal fields required.
	 * The name field defaults to the class name and keyLocation defaults to
	 * KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  keyCode The KeyEvent style key code for the key. See 
	 *                 java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar The KeyEvent style key char for the key.
	 */
	public SwipeKeyZone( int keyCode, char keyChar){
		this( keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	/**
	 * A constructor, with the minimal fields required, plus key location.
	 * The name field defaults to the class name.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar     The KeyEvent style key char for the key.
	 * @param  keyLocation The KeyEvent style key location for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public SwipeKeyZone( int keyCode, char keyChar, int keyLocation){
		this( "SwipeKeyZone", keyCode, keyChar, keyLocation);
	}
	/**
	 * A constructor, with the minimal fields required, plus zone name.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  name    The human-friendly name of the key.
	 * @param  keyCode The KeyEvent style key code for the key. See
	 *                 java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar The KeyEvent style key char for the key.
	 */
	public SwipeKeyZone( String name, int keyCode, char keyChar){
		this( name, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	/**
	 * The full constructor, with all fields.
	 * @param  name        The human-friendly name of the key.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar     The KeyEvent style key char for the key.
	 * @param  keyLocation The KeyEvent style key location for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public SwipeKeyZone( String name, int keyCode, char keyChar, int keyLocation){
		super( name, keyCode, keyChar, keyLocation);
		//other initialization
		swipeListeners = new Vector<SwipeKeyListener>();
	}

	////////////////////
	// SMT overrides //
	////////////////////
	/**
	 * Detects the touchDown event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchDownImpl( Touch touch) {
		if( touchEventBuffer[0] == TouchEvent.ASSIGN)
			invokeSwipeStarted( touch);
		super.touchDownImpl( touch);
	}
	/**
	 * Detects the touchUp event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchUpImpl( Touch touch) {
		if( touchEventBuffer[0] == TouchEvent.UNASSIGN)
			invokeSwipeEnded( touch);
		super.touchUpImpl( touch);
	}
	/**
	 * Detects a touch entering event and responds by invoking a SwipeHit event.
	 * @param touches The list of touches passed to the function by SMT
	 */
	public void assign(Iterable<? extends Touch> touches) {
		for( Touch touch : touches )
			invokeSwipeHit( touch);
		super.assign( touches);
	}
	/**
	 * Detects a touch exiting event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	public void unassign(Touch touch) {
		super.unassign( touch);
	}

	/////////////////////////////////
	// Event invocation functions //
	/////////////////////////////////
	/**
	 * Creates a SwipeStarted event and sends it to all listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeSwipeStarted( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_STARTED, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeStarted( event);
		}
	}
	/**
	 * Creates a SwipeHit event and sends it to our listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeSwipeHit( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_HIT, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeHit( event);
		}
	}
	/**
	 * Creates a SwipeEnded event and sends it to our listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeSwipeEnded( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_ENDED, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeEnded( event);
		}
	}

	///////////////////////
	// public functions //
	///////////////////////
	/**
	 * Adds a SwipeKeyListener to our list of listeners.
	 * @param listener A SwipeKeyListener to be added to our list of listeners
	 */
	public void addSwipeKeyListener( SwipeKeyListener listener){
		swipeListeners.add( listener);
	}

	////////////////////////
	// utility functions //
	////////////////////////
	/**
	 * Puts together a SwipeKeyEvent for the given id and touch.
	 * @param  id    The desired id field for the SwipeKeyEvent
	 * @param  touch The desired touch field for the SwipeKeyEvent
	 * @return       A SwipeKeyEvent with the given id and touch fields, using
	 * this object as the source, and this object's fields for the other fields
	 * required by the event's constructor.
	 */
	private SwipeKeyEvent constructSwipeEvent( int id, Touch touch){
		char keyChar = this.alternate_enabled ?
			this.keyChar_alternate : this.keyChar;
		return new SwipeKeyEvent(
			this, id, this.keyCode, keyChar, this.keyLocation, touch);
	}
}