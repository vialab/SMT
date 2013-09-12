package vialab.SMT;

//standard library imports
import java.awt.event.*;
import java.util.Vector;

//smt imports
import vialab.SMT.event.*;
import vialab.SMT.zone.*;

public class SwipeKeyboard extends Zone implements SwipeKeyListener{
	//Private fields
	private Vector<SwipeKeyZone> keys;
	SwipeKeyZone key1;
	SwipeKeyZone key2;
	SwipeKeyZone key3;
	SwipeKeyZone key4;

	//Constructors
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
		SMT.add( key1);
		SMT.add( key2);
		SMT.add( key3);
		SMT.add( key4);
		this.add( key1);
		this.add( key2);
		this.add( key3);
		this.add( key4);
		
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
	public void swipeStarted( SwipeKeyEvent swipeKeyEvent){
		System.out.printf("Swipe Started: %s\n", swipeKeyEvent.getKeyChar());
	}
	public void swipeHit( SwipeKeyEvent swipeKeyEvent){
		System.out.printf("Swipe Hit: %s\n", swipeKeyEvent.getKeyChar());
	}
	public void swipeEnded( SwipeKeyEvent swipeKeyEvent){
		System.out.printf("Swipe Ended: %s\n", swipeKeyEvent.getKeyChar());
	}
}