package vialab.SMT.zone;

//standard library imports
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

//processing imports
import processing.core.PVector;

//local imports
import vialab.SMT.*;
import vialab.SMT.event.*;

/** @class SwipeKeyZone
 * This class defines a zone that represents a key in a keyboard.
 **/
public class KeyZone extends Zone {
	/////////////////////////////
	// protected major fields //
	/////////////////////////////
	/**
	 * This object's list of KeyListeners
	 */
	protected Vector<KeyListener> keyListeners;
	/**
	 * The KeyEvent-style key code represented by this key.
	 */
	protected int keyCode;
	/**
	 * The KeyEvent-style key char represented by this key.
	 */
	protected char keyChar;
	/**
	 * The KeyEvent-style key location of this key.
	 */
	protected int keyLocation;

	/////////////////////
	// drawing fields //
	/////////////////////
	/**
	 * A vector defining the current position of this zone.
	 */
	protected PVector position;
	/**
	 * The text that appears on this key.
	 */
	protected String label;
	/**
	 * The degree of rounding of the top left corner of this key.
	 */
	protected int cornerRounding_topLeft;
	/**
	 * The degree of rounding of the top right corner of this key.
	 */
	protected int cornerRounding_topRight;
	/**
	 * The degree of rounding of the bottom left corner of this key.
	 */
	protected int cornerRounding_bottomLeft;
	/**
	 * The degree of rounding of the bottom right corner of this key.
	 */
	protected int cornerRounding_bottomRight;

	///////////////////
	// debug fields //
	///////////////////
	/**
	 * Enables and disables debug print statements
	 */
	private static final boolean debug = false;

	/////////////////////////////
	// private utility fields //
	/////////////////////////////
	/**
	 * A buffer of the touch events that have involved this key in the past.
	 * Used to interpret more complicated interactions.
	 */
	protected TouchEvent[] touchEventBuffer;

	///////////////////
	// Constructors //
	///////////////////
	/**
	 * The basic constructor, with the minimal fields required.
	 * The name field defaults to the class name.
	 * The keyChar field defaults to KeyEvent.CHAR_UNDEFINED.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public KeyZone( int keyCode){
		this( keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	/**
	 * The basic constructor, plus the key character field.
	 * The name field defaults to the class name.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar     The KeyEvent style key char for the key.
	 */
	public KeyZone( int keyCode, char keyChar){
		this( keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	/**
	 * The full constructor, with all fields, minus zone name.
	 * The name field defaults to the class name.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar     The KeyEvent style key char for the key.
	 * @param  keyLocation The KeyEvent style key location for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public KeyZone( int keyCode, char keyChar, int keyLocation){
		this( "KeyZone", keyCode, keyChar, keyLocation);
	}
	/**
	 * The full constructor, with all fields, minus key character and location
	 * The keyChar field defaults to KeyEvent.CHAR_UNDEFINED.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  name        The human-friendly name of the key.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public KeyZone( String name, int keyCode){
		this( name, keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	/**
	 * The full constructor, with all fields, minus key location.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  name        The human-friendly name of the key.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyChar     The KeyEvent style key char for the key.
	 */
	public KeyZone( String name, int keyCode, char keyChar){
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
	public KeyZone( String name, int keyCode, char keyChar, int keyLocation){
		super( name);

		//validate key code, char, and location
		//if id is KEY_TYPED and keyChar is CHAR_UNDEFINED; or if id is KEY_TYPED
		// and keyCode is not VK_UNDEFINED; or if id is KEY_TYPED and keyLocation
		// is not KEY_LOCATION_UNKNOWN; or if keyLocation is not one of the legal
		// values enumerated above.
		//keyLocation is element ofKEY_LOCATION_UNKNOWN, KEY_LOCATION_STANDARD,
		// KEY_LOCATION_LEFT, KEY_LOCATION_RIGHT, and KEY_LOCATION_NUMPAD
		//IllegalArgumentException

		//set key code, char, and location
		this.keyCode = keyCode;
		this.keyChar = keyChar;
		this.keyLocation = keyLocation;
		label = String.valueOf( keyChar);

		//set drawing fields
		position = new PVector( 0, 0);
		dimension = new Dimension( 100, 100);
		halfDimension = new Dimension(
			dimension.width / 2,
			dimension.height / 2);
		cornerRounding_topLeft = 0;
		cornerRounding_topRight = 0;
		cornerRounding_bottomLeft = 0;
		cornerRounding_bottomRight = 0;

		//other initialization
		keyListeners = new Vector<KeyListener>();
		touchEventBuffer = new TouchEvent[ 5];
	}

	////////////////////
	// SMT Overrides //
	////////////////////
	/**
	 * Draws the key.
	 */
	@Override
	public void drawImpl() {
		//draw key
		fill( 20, 20, 20, 255);
		strokeWeight(4);
		stroke( 50, 50, 50, 255);
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);
		//draw text
		fill( 255, 255, 255, 200);
		textSize( Math.round( dimension.height * 0.6));
		textAlign( CENTER);
		float halfAscent = textAscent()/2;
		float halfDescent = textDescent()/2;
		text( label,
			position.x + halfDimension.width,
			position.y + halfDimension.height + halfAscent - halfDescent);
	}
	/**
	 * Draws the touch selection area of the key.
	 */
	@Override
	public void pickDrawImpl() {
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);
	}
	/**
	 * Overrides the Zone touch method to define null behavior.
	 */
	@Override
	public void touchImpl() {}
	/**
	 * Detects the touchDown event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchDownImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchDown");
		if( touchEventBuffer[0] == TouchEvent.ASSIGN)
			invokeKeyPressedEvent();
		bufferTouchEvent( TouchEvent.TOUCH_DOWN);
	}
	/**
	 * Detects the touchUp event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchUpImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchUp");
		if( touchEventBuffer[1] == TouchEvent.TOUCH_DOWN &&
				touchEventBuffer[0] == TouchEvent.UNASSIGN)
			invokeKeyReleasedEvent();
		bufferTouchEvent( TouchEvent.TOUCH_UP);
	}
	/**
	 * Detects the press event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void pressImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "press");
		//bufferTouchEvent( TouchEvent.PRESS);
	}
	/**
	 * Detects a touch entering event and responds by invoking a SwipeHit event.
	 * @param touches The list of touches passed to the function by SMT
	 */
	public void assign(Iterable<? extends Touch> touches) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "assign");
		bufferTouchEvent( TouchEvent.ASSIGN);
		super.assign( touches);
	}
	/**
	 * Detects a touch exiting event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	public void unassign(Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "unassign");
		bufferTouchEvent( TouchEvent.UNASSIGN);
		super.unassign( touch);
	}

	///////////////////////////////////
	// key event invocation methods //
	///////////////////////////////////
	/**
	 * Creates a KeyPressed event and sends it to all listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeKeyPressedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_PRESSED);
		for( KeyListener listener : keyListeners){
			listener.keyPressed( event);
		}
	}
	/**
	 * Creates a KeyReleased event and sends it to all listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeKeyReleasedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_RELEASED);
		for( KeyListener listener : keyListeners){
			listener.keyReleased( event);
		}
	}
	/**
	 * Creates a KeyTyped event and sends it to all listeners for handling.
	 * @param touch The touch involved in the event
	 */
	public void invokeKeyTypedEvent(){
		System.out.println("asdf");
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_TYPED);
		for( KeyListener listener : keyListeners){
			listener.keyTyped( event);
		}
	}

	///////////////////////
	// public functions //
	///////////////////////
	/**
	 * Adds a KeyListener to our list of listeners
	 * @param listener A KeyListener to be added to our list of listeners.
	 */
	public void addKeyListener( KeyListener listener){
		keyListeners.add( listener);
	}

	//////////////////////////////
	// public accessor methods //
	//////////////////////////////
	/**
	 * Sets the text that is displayed on the key.
	 * @param label The text to be displayed on the key.
	 */
	public void setLabel( String label){
		this.label = label;
	}
	/**
	 * Sets the degree of rounding of the key's corners.
	 * @param rounding The pixel radius of the rounding effect.
	 */
	public void setCornerRounding( int rounding){
		cornerRounding_topLeft = rounding;
		cornerRounding_topRight = rounding;
		cornerRounding_bottomLeft = rounding;
		cornerRounding_bottomRight = rounding;
	}
	/**
	 * Sets the degree of rounding of the key's corners.
	 * @param topLeft     The pixel radius of the top left corner's rounding
	 *                    effect.
	 * @param topRight    The pixel radius of the top right corner's rounding
	 *                    effect.
	 * @param bottomLeft  The pixel radius of the bottom left corner's rounding
	 *                    effect.
	 * @param bottomRight The pixel radius of the bottom right corner's rounding
	 *                    effect.
	 */
	public void setCornerRounding(
			int topLeft, int topRight, int bottomLeft, int bottomRight){
		cornerRounding_topLeft = topLeft;
		cornerRounding_topRight = topRight;
		cornerRounding_bottomLeft = bottomLeft;
		cornerRounding_bottomRight = bottomRight;
	}

	////////////////////////
	// utility functions //
	////////////////////////
	/**
	 * Puts together a KeyEvent for the given id.
	 * @param  id The desired id field for the KeyEvent.
	 * @return    A KeyEvent with the given id field, using this object as the
	 * source, and this object's fields for the other fields required by the
	 * event's constructor.
	 */
	protected KeyEvent constructKeyEvent( int id){
		//is it okay to use 0 for KeyEvent's constructor's modifiers parameter?
		return new KeyEvent(
			this.applet, id, System.currentTimeMillis(), 0,
			this.keyCode, this.keyChar, this.keyLocation);
	}

	////////////////////////////////
	// private utility functions //
	////////////////////////////////
	/**
	 * Adds a KeyZone.TouchEvent to the event buffer.
	 * @param event The KeyZone.TouchEvent to be added to the event buffer.
	 */
	private void bufferTouchEvent( TouchEvent event){
		for( int i = 1; i < touchEventBuffer.length; i++)
			touchEventBuffer[ i] = touchEventBuffer[ i - 1];
		touchEventBuffer[ 0] = event;
	}

	////////////
	// enums //
	////////////
	/**
	 * An enumerated type representing the types of possible touch interactions.
	 * Not to be confused with vialab.SMT.event.TouchEvent.
	 */
	public static enum TouchEvent {
		TOUCH_DOWN,
		TOUCH_UP,
		PRESS,
		ASSIGN,
		UNASSIGN
	}
}