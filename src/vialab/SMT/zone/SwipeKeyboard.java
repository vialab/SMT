package vialab.SMT.zone;

//standard library imports
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

//smt imports
import vialab.SMT.*;
import vialab.SMT.event.*;

public class SwipeKeyboard extends Zone
		implements SwipeKeyListener, KeyListener, TouchListener{
	//debug fields
	private static final boolean debug = false;

	//public fields
	private Vector<AnchorZone> anchors;
	private Vector<KeyZone> keys;
	private Vector<SwipeKeyZone> swipeKeys;
	//private fields
	private boolean swipe_inProgress;
	private Vector<SwipeKeyEvent> swipeStack;
	private Vector<Touch> touches;


	//constructors
	public SwipeKeyboard(){
		this( prototypeLayout);
	}
	public SwipeKeyboard( SwipeKeyboardLayout layout){
		super( "Swipe Keyboard");
		anchors = new Vector<AnchorZone>();
		keys = new Vector<KeyZone>();
		swipeKeys = new Vector<SwipeKeyZone>();

		//setup layout
		layout.setup( this);
		for( AnchorZone anchor : anchors)
			this.add( anchor);
		for( KeyZone key : keys){
			SMT.add( key);
			this.add( key);
			key.addKeyListener( this);
		}
		for( SwipeKeyZone key : swipeKeys){
			SMT.add( key);
			this.add( key);
			key.addKeyListener( this);
			key.addSwipeKeyListener( this);
		}

		//other
		swipe_inProgress = false;
		touches = new Vector<Touch>();
		swipeStack = new Vector<SwipeKeyEvent>();
		this.translate( 10, 10);
	}

	//public access functions
	public void addAnchor( AnchorZone anchor){
		this.anchors.add( anchor);
	}
	public void addKey( KeyZone key){
		this.keys.add( key);
	}
	public void addSwipeKey( SwipeKeyZone key){
		this.swipeKeys.add( key);
	}

	//SMT overrides
	@Override
	public void drawImpl() {}
	@Override
	public void pickDrawImpl() {}
	@Override
	public void touchImpl() {
		rst();
	}

	//SwipeKeyListener handles
	public void swipeStarted( SwipeKeyEvent event){
		Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Started: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());
		touch.addTouchListener( this);
		touches.add( touch);
		swipe_inProgress = true;
		swipeStack.add( event);
	}
	public void swipeHit( SwipeKeyEvent event){
		Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Hit: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());
		if( swipe_inProgress)
			swipeStack.add( event);
	}
	public void swipeEnded( SwipeKeyEvent event){
		/*Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Ended: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());*/
	}

	//touch listener handles
	public void handleTouchDown( TouchEvent event){
	}
	public void handleTouchUp( TouchEvent event){
		Touch touch = event.getTouch();
		if( debug)
			System.out.printf( "Touch died. TouchID: %d\n", touch.getSessionID());
		touches.remove( touch);
		if( touches.isEmpty()){
			swipe_inProgress = false;
			resolveSwipe();
		}
	}
	public void handleTouchMoved( TouchEvent event){
	}

	//private utility methods
	private void resolveSwipe(){
		String swipe = new String();
		//load string
		for( SwipeKeyEvent event : swipeStack)
			swipe += event.getKeyChar();
		swipeStack.clear();
		System.out.printf("Swipe Finished: %s\n", swipe);
		//preprocess swipe string
		//match search
		//match rank
		//invoke wordTyped
	}

	//subclasses
	//layouts
	public static final SwipeKeyboardLayout arrowKeysLayout =
			new ArrowKeysLayout();
	public static final SwipeKeyboardLayout condensedLayout =
			new CondensedLayout();
	public static final SwipeKeyboardLayout extendedLayout =
			new ExtendedLayout();

	public static final SwipeKeyboardLayout prototypeLayout =
			new SwipeKeyboardLayout(){
		public void setup( SwipeKeyboard keyboard){
			//anchors
			AnchorZone anchor_topLeft = new AnchorZone();
			AnchorZone anchor_topRight = new AnchorZone();
			AnchorZone anchor_bottomLeft = new AnchorZone();
			AnchorZone anchor_bottomRight = new AnchorZone();
			anchor_topLeft.translate( 0, 0);
			anchor_topRight.translate( 0, 100);
			anchor_bottomLeft.translate( 120 * 4 - 20, 0);
			anchor_bottomRight.translate( 120 * 4 - 20, 100);
			keyboard.addAnchor( anchor_topLeft);
			keyboard.addAnchor( anchor_topRight);
			keyboard.addAnchor( anchor_bottomLeft);
			keyboard.addAnchor( anchor_bottomRight);
			//swipeKeys
			SwipeKeyZone key1 = new SwipeKeyZone( "Key1", KeyEvent.VK_A, 'A');
			SwipeKeyZone key2 = new SwipeKeyZone( "Key2", KeyEvent.VK_1, '1');
			SwipeKeyZone key3 = new SwipeKeyZone( "Key3", KeyEvent.VK_DEAD_TILDE, '~');
			SwipeKeyZone key4 = new SwipeKeyZone( "Key4", KeyEvent.VK_P, 'p');
			key1.translate( 20 + 120 * 0, 20);
			key2.translate( 20 + 120 * 1, 20);
			key3.translate( 20 + 120 * 2, 20);
			key4.translate( 20 + 120 * 3, 20);
			keyboard.addSwipeKey( key1);
			keyboard.addSwipeKey( key2);
			keyboard.addSwipeKey( key3);
			keyboard.addSwipeKey( key4);
		}
	};
}