package vialab.SMT.zone;

//standard library imports
import java.awt.event.*;
import java.util.Vector;

//smt imports
import vialab.SMT.*;
import vialab.SMT.event.*;

public class SwipeKeyboard extends Zone implements SwipeKeyListener{
	//debug fields
	private static final boolean debug = false;

	//private fields
	private Vector<SwipeKeyZone> keys;
	private Vector<Touch> touches;
	SwipeKeyZone key1;
	SwipeKeyZone key2;
	SwipeKeyZone key3;
	SwipeKeyZone key4;

	//constructors
	public SwipeKeyboard(){
		keys = new Vector<SwipeKeyZone>();
		//add key test
		key1 = new SwipeKeyZone( "Key1", KeyEvent.VK_A, 'A');
		key2 = new SwipeKeyZone( "Key2", KeyEvent.VK_1, '1');
		key3 = new SwipeKeyZone( "Key3", KeyEvent.VK_DEAD_TILDE, '~');
		key4 = new SwipeKeyZone( "Key4", KeyEvent.VK_P, 'p');
		key1.translate( 20 + 120 * 0, 20);
		key2.translate( 20 + 120 * 1, 20);
		key3.translate( 20 + 120 * 2, 20);
		key4.translate( 20 + 120 * 3, 20);
		keys.add( key1);
		keys.add( key2);
		keys.add( key3);
		keys.add( key4);
		for( SwipeKeyZone key : keys){
			SMT.add( key);
			this.add( key);
			key.addSwipeKeyListener( this);
		}

		//other
		this.translate( 10, 10);
	}

	//SMT overrides
	@Override
	public void drawImpl() {
		fill( 0, 0, 0, 200);
		noStroke();
		rect( 0, 0, 40, 40, 5);
		rect( 0, 100, 40, 40, 5);
		rect( 120 * 4 - 20, 0, 40, 40, 5);
		rect( 120 * 4 - 20, 100, 40, 40, 5);
	}
	@Override
	public void pickDrawImpl() {
		rect( 0, 0, 40, 40, 5);
		rect( 0, 100, 40, 40, 5);
		rect( 120 * 4 - 20, 0, 40, 40, 5);
		rect( 120 * 4 - 20, 100, 40, 40, 5);
	}
	@Override
	public void touchImpl() {
		rst();
	}

	//SwipeKeyListener handles
	public void swipeStarted( SwipeKeyEvent event){
		if( debug) System.out.printf( "Swipe Started: %s TouchID: %d\n",
			event.getKeyChar(), event.getTouch().sessionID);
	}
	public void swipeHit( SwipeKeyEvent event){
		if( debug) System.out.printf( "Swipe Hit: %s TouchID: %d\n",
			event.getKeyChar(), event.getTouch().sessionID);
	}
	public void swipeEnded( SwipeKeyEvent event){
		if( debug) System.out.printf( "Swipe Ended: %s TouchID: %d\n",
			event.getKeyChar(), event.getTouch().sessionID);
	}

	//subclasses
	private class Anchor extends Zone {
	}
}