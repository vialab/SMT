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

public class SwipeKeyZone extends KeyZone {
	//private major fields
	private Vector<SwipeKeyListener> swipeListeners;

	//debug fields
	private static final boolean debug = false;

	//Constructors
	public SwipeKeyZone( int keyCode, char keyChar){
		this( keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( int keyCode, char keyChar, int keyLocation){
		this( "SwipeKeyZone", keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( String name, int keyCode, char keyChar){
		this( name, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
	}
	public SwipeKeyZone( String name, int keyCode, char keyChar, int keyLocation){
		super( name, keyCode, keyChar, keyLocation);
		//other initialization
		swipeListeners = new Vector<SwipeKeyListener>();
	}

	//SMT overrides
	public void touchDownImpl( Touch touch) {
		if( bufferTouchEvent == TouchEvent.ASSIGN)
			invokeSwipeStarted( touch);
		super.touchDownImpl( touch);
	}
	public void touchUpImpl( Touch touch) {
		if( bufferTouchEvent == TouchEvent.UNASSIGN)
			invokeSwipeEnded( touch);
		super.touchUpImpl( touch);
	}
	@Override
	public void touchImpl() {}
	@Override
	public void pressImpl( Touch touch) {}

	//entered detection
	public void assign(Iterable<? extends Touch> touches) {
		for( Touch touch : touches ){
			invokeSwipeHit( touch);
		}
		super.assign( touches);
	}

	//exited detection
	public void unassign(Touch touch) {
		super.unassign( touch);
	}

	//event invocation functions
	public void invokeSwipeStarted( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_STARTED, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeStarted( event);
		}
	}
	public void invokeSwipeHit( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_HIT, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeHit( event);
		}
	}
	public void invokeSwipeEnded( Touch touch){
		SwipeKeyEvent event = constructSwipeEvent(
			SwipeKeyEvent.SWIPE_ENDED, touch);
		for( SwipeKeyListener listener : swipeListeners){
			listener.swipeEnded( event);
		}
	}

	//public functions
	public void addSwipeKeyListener( SwipeKeyListener listener){
		swipeListeners.add( listener);
	}

	//utility functions
	private SwipeKeyEvent constructSwipeEvent( int id, Touch touch){
		return new SwipeKeyEvent(
			this, id, this.keyCode, this.keyChar, this.keyLocation, touch);
	}
}