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

public class KeyZone extends Zone {
	//protected major fields
	protected Vector<KeyListener> keyListeners;
	protected int keyCode;
	protected char keyChar;
	protected int keyLocation;

	//drawing fields
	protected PVector position;
	protected String label;
	protected int cornerRounding_topLeft;
	protected int cornerRounding_topRight;
	protected int cornerRounding_bottomLeft;
	protected int cornerRounding_bottomRight;

	//debug fields
	private static final boolean debug = false;

	//private utility fields
	protected TouchEvent bufferTouchEvent;

	//Constructors
	public KeyZone( int keyCode){
		this( keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	public KeyZone( int keyCode, char keyChar){
		this( keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public KeyZone( int keyCode, char keyChar, int keyLocation){
		this( "KeyZone", keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public KeyZone( String name, int keyCode){
		this( name, keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	public KeyZone( String name, int keyCode, char keyChar){
		this( name, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
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
	}

	//SMT Overrides
	@Override
	public void drawImpl() {
		//draw key
		fill( 255, 255, 255, 200);
		strokeWeight(4);
		stroke( 50, 50, 50, 255);
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);
		//draw text
		fill( 20, 20, 20, 255);
		textSize( Math.round( dimension.height * 0.7));
		textAlign( CENTER);
		float halfAscent = textAscent()/2;
		float halfDescent = textDescent()/2;
		text( label,
			position.x + halfDimension.width,
			position.y + halfDimension.height + halfAscent - halfDescent);
	}
	@Override
	public void pickDrawImpl() {
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);
	}
	public void touchDownImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchDown");
		bufferTouchEvent = TouchEvent.TOUCH_DOWN;
	}
	public void touchUpImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchUp");
		bufferTouchEvent = TouchEvent.TOUCH_UP;
	}
	@Override
	public void touchImpl() {}
	@Override
	public void pressImpl( Touch touch) {
		/*if( debug) System.out.printf("%s %s %s\n", name, keyChar, "press");
		bufferTouchEvent = TouchEvent.PRESS;*/
	}

	//entered detection
	public void assign(Iterable<? extends Touch> touches) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "assign");
		bufferTouchEvent = TouchEvent.ASSIGN;
		super.assign( touches);
	}

	//exited detection
	public void unassign(Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "unassign");
		bufferTouchEvent = TouchEvent.UNASSIGN;
		super.unassign( touch);
	}

	//key event invocation methods
	public void invokeKeyPressedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_PRESSED);
		for( KeyListener listener : keyListeners){
			listener.keyPressed( event);
		}
	}
	public void invokeKeyReleasedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_RELEASED);
		for( KeyListener listener : keyListeners){
			listener.keyReleased( event);
		}
	}
	public void invokeKeyTypedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_TYPED);
		for( KeyListener listener : keyListeners){
			listener.keyTyped( event);
		}
	}

	//public functions
	public void addKeyListener( KeyListener listener){
		keyListeners.add( listener);
	}

	//public accessor methods
	public void setLabel( String label){
		this.label = label;
	}
	public void setCornerRounding( int rounding){
		cornerRounding_topLeft = rounding;
		cornerRounding_topRight = rounding;
		cornerRounding_bottomLeft = rounding;
		cornerRounding_bottomRight = rounding;
	}
	public void setCornerRounding(
			int topLeft, int topRight, int bottomLeft, int bottomRight){
		cornerRounding_topLeft = topLeft;
		cornerRounding_topRight = topRight;
		cornerRounding_bottomLeft = bottomLeft;
		cornerRounding_bottomRight = bottomRight;
	}

	//utility functions
	protected KeyEvent constructKeyEvent( int id){
		//is it okay to use 0 for KeyEvent's constructor's modifiers parameter?
		return new KeyEvent(
			this.applet, id, System.currentTimeMillis(), 0,
			this.keyCode, this.keyChar, this.keyLocation);
	}

	//enums
	public static enum TouchEvent {
		TOUCH_DOWN,
		TOUCH_UP,
		PRESS,
		ASSIGN,
		UNASSIGN
	}
}