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

public class SwipeKeyZone extends Zone {

	//private major fields
	private Vector<SwipeKeyListener> listeners;
	private int keyCode;
	private char keyChar;
	private int keyLocation;

	//private utility fields
	private TouchEvent bufferTouchEvent;

	//drawing fields
	private PVector position;
	private Dimension dimension;
	private Dimension halfDimension;

	//debug fields
	private static final boolean debug = false;

	//Constructors
	public SwipeKeyZone( int keyCode){
		this( keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	public SwipeKeyZone( int keyCode, char keyChar){
		this( keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( int keyCode, char keyChar, int keyLocation){
		this( "SwipeKeyZone", keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( String name, int keyCode){
		this( name, keyCode, KeyEvent.CHAR_UNDEFINED);
	}
	public SwipeKeyZone( String name, int keyCode, char keyChar){
		this( name, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( String name, int keyCode, char keyChar, int keyLocation){
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

		//set drawing fields
		position = new PVector( 0, 0);
		dimension = new Dimension( 100, 100);
		halfDimension = new Dimension(
			dimension.width / 2,
			dimension.height / 2);

		//other initialization
		listeners = new Vector<SwipeKeyListener>();
	}

	//SMT overrides
	@Override
	public void drawImpl() {
		//draw key
		fill( 200, 200, 200, 200);
		strokeWeight(4);
		stroke( 0, 0, 0, 200);
		rect(
			position.x, position.y,
			dimension.width, dimension.height, 7);
		//draw text
		fill( 33, 33, 33, 200);
		textSize( Math.round( dimension.height * 0.7));
		textAlign( CENTER);
		float halfAscent = textAscent()/2;
		float halfDescent = textDescent()/2;
		text( keyChar,
			position.x + halfDimension.width,
			position.y + halfDimension.height + halfAscent - halfDescent);
	}
	@Override
	public void pickDrawImpl() {
		rect( 0, 0, 100, 100, 7);
	}
	public void touchDownImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchDown");
		if( bufferTouchEvent == TouchEvent.ASSIGN)
			invokeSwipeStarted( touch);
		bufferTouchEvent = TouchEvent.TOUCH_DOWN;
	}
	public void touchUpImpl( Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchUp");
		if( bufferTouchEvent == TouchEvent.UNASSIGN)
			invokeSwipeEnded( touch);
		bufferTouchEvent = TouchEvent.TOUCH_UP;
	}
	@Override
	public void touchImpl() {}
	public void pressImpl( Touch touch) {
		/*if( debug) System.out.printf("%s %s %s\n", name, keyChar, "press");
		bufferTouchEvent = TouchEvent.PRESS;*/
	}

	//entered detection
	public void assign(Iterable<? extends Touch> touches) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "assign");
		for( Touch touch : touches ){
			invokeSwipeHit( touch);
		}
		bufferTouchEvent = TouchEvent.ASSIGN;
		super.assign( touches);
	}

	//exited detection
	public void unassign(Touch touch) {
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "unassign");
		bufferTouchEvent = TouchEvent.UNASSIGN;
		super.unassign( touch);
	}

	//event invocation functions
	public void invokeSwipeStarted( Touch touch){
		SwipeKeyEvent event = constructKeyEvent(
			SwipeKeyEvent.SWIPE_STARTED, touch);
		for( SwipeKeyListener listener : listeners){
			listener.swipeStarted( event);
		}
	}
	public void invokeSwipeHit( Touch touch){
		SwipeKeyEvent event = constructKeyEvent(
			SwipeKeyEvent.SWIPE_HIT, touch);
		for( SwipeKeyListener listener : listeners){
			listener.swipeHit( event);
		}
	}
	public void invokeSwipeEnded( Touch touch){
		SwipeKeyEvent event = constructKeyEvent(
			SwipeKeyEvent.SWIPE_ENDED, touch);
		for( SwipeKeyListener listener : listeners){
			listener.swipeEnded( event);
		}
	}
	/*public void invokeKeyPressedEvent(){
		SwipeKeyEvent event = constructKeyEvent( KeyEvent.KEY_PRESSED);
		for( SwipeKeyListener listener : listeners){
			listener.keyPressed( event);
		}
	}
	public void invokeKeyReleasedEvent(){
		SwipeKeyEvent event = constructKeyEvent( KeyEvent.KEY_RELEASED);
		for( SwipeKeyListener listener : listeners){
			listener.keyReleased( event);
		}
	}
	public void invokeKeyTypedEvent(){
		SwipeKeyEvent event = constructKeyEvent( KeyEvent.KEY_TYPED);
		for( SwipeKeyListener listener : listeners){
			listener.keyTyped( event);
		}
	}*/

	//public functions
	public void addSwipeKeyListener( SwipeKeyListener listener){
		listeners.add( listener);
	}

	//utility functions
	private SwipeKeyEvent constructKeyEvent( int id, Touch touch){
		return new SwipeKeyEvent(
			this, id, this.keyCode, this.keyChar, this.keyLocation, touch);
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