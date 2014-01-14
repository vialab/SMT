package vialab.SMT;

//standard library imports
import java.awt.Dimension;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Vector;

//processing imports
import processing.core.PVector;

//smt imports
import vialab.SMT.*;
import vialab.SMT.event.*;
import vialab.SMT.swipekeyboard.*;

/**
 * A Zone that provides swipe keyboard functionality.
 */
public class SwipeKeyboard extends Zone
		implements SwipeKeyListener, KeyListener, TouchListener{
	///////////////////
	// debug fields //
	///////////////////
	/**
	 * Enables and disables debug print statements
	 */
	private static final boolean debug = false;

	////////////////////
	// public fields //
	////////////////////
	/** A list of all the anchors contained by this keyboard */
	private Vector<AnchorZone> anchors;
	/** A list of all the keys contained by this keyboard */
	private Vector<KeyZone> keys;
	/** A list of all objects listening swipe events from this keyboard */
	private Vector<SwipeKeyboardListener> swipeListeners;
	/** A list of all objects listening swipe events from this keyboard */
	private Vector<KeyListener> keyListeners;
	/** A list of all the swipeKeys contained by this keyboard */
	private Vector<SwipeKeyZone> swipeKeys;

	/////////////////////
	// private fields //
	/////////////////////
	/**
	 * Indicates whether a swipe is currently in progress.
	 */
	private boolean swipe_inProgress;
	/**
	 * The stack of swipeKeyEvents to be resolved once the current swipe finishes.
	 * Should be empty if there is no swipe in progress.
	 */
	private Vector<SwipeKeyEvent> swipeStack;
	private SwipeResolver resolver;
	/**
	 * A list of all touches currently invovled in the current swipe.
	 * Should be empty if there is no swipe in progress.
	 */
	private Vector<Touch> touches;

	/////////////////////////////
	// private drawing fields //
	/////////////////////////////
	/** Indicates whether the keyboard's background should be drawn. */
	private boolean drawBackground = false;
	/** The location of the keyboard. */
	protected PVector position;
	/** The degree of rounding of the top left corner of this key. */
	protected int cornerRounding_topLeft;
	/** The degree of rounding of the top right corner of this key. */
	protected int cornerRounding_topRight;
	/** The degree of rounding of the bottom left corner of this key. */
	protected int cornerRounding_bottomLeft;
	/** The degree of rounding of the bottom right corner of this key. */
	protected int cornerRounding_bottomRight;

	///////////////////
	// constructors //
	///////////////////
	/** The default constructor for the keyboard. Uses the prototype Layout */
	public SwipeKeyboard(){
		this( condensedLayout);
	}
	/**
	 * The main constructor for the keyboard.
	 * @param  layout The desired keyboard layout.
	 */
	public SwipeKeyboard( SwipeKeyboardLayout layout){
		this( layout, null);
	}
	/**
	 * The main constructor for the keyboard.
	 * @param  resolver The desired swipe resolver.
	 */
	public SwipeKeyboard( SwipeResolver resolver){
		this( condensedLayout, resolver);
	}
	/**
	 * The main constructor for the keyboard.
	 * @param  layout The desired keyboard layout.
	 * @param  resolver The desired swipe resolver.
	 */
	public SwipeKeyboard( SwipeKeyboardLayout layout, SwipeResolver resolver){
		super( "Swipe Keyboard");
		anchors = new Vector<AnchorZone>();
		keys = new Vector<KeyZone>();
		swipeKeys = new Vector<SwipeKeyZone>();

		//drawing fields
		position = new PVector( 0, 0);
		cornerRounding_topLeft = 0;
		cornerRounding_topRight = 0;
		cornerRounding_bottomLeft = 0;
		cornerRounding_bottomRight = 0;

		//setup layout
		layout.setup( this);
		for( AnchorZone anchor : anchors)
			this.add( anchor);
		for( KeyZone key : keys)
			this.add( key);
		for( SwipeKeyZone key : swipeKeys)
			this.add( key);

		//setup swipe resolver
		this.resolver = resolver;
		if( this.resolver == null)
			try{
				this.resolver = new DefaultSwipeResolver();
			}
			catch( FileNotFoundException exception){
				System.err.println(
				"[SwipeKeyboard] Loading the default swipe resolver failed. Swiping words will not work. Try setting the resolver to your own implementation via the SwipeKeyboard.setSwipeResolver( SwipeResolver) function or the SwipeKeyboard( SwipeResolver) constructor.");
			}

		//other
		swipe_inProgress = false;
		touches = new Vector<Touch>();
		keyListeners = new Vector<KeyListener>();
		swipeListeners = new Vector<SwipeKeyboardListener>();
		swipeStack = new Vector<SwipeKeyEvent>();
	}

	//////////////////////////////
	// public access functions //
	//////////////////////////////
	/**
	 * Adds an anchor to the keyboard.
	 * @param anchor The anchor to be added to this keyboard.
	 */
	public void addAnchor( AnchorZone anchor){
		this.anchors.add( anchor);
	}
	/**
	 * Adds an key to the keyboard.
	 * @param key The key to be added to this keyboard.
	 */
	public void addKey( KeyZone key){
		this.keys.add( key);
		key.addKeyListener( this);
	}
	/**
	 * Adds an swipe key to the keyboard.
	 * @param key The swipe key to be added to this keyboard.
	 */
	public void addSwipeKey( SwipeKeyZone key){
		this.swipeKeys.add( key);
		key.addKeyListener( this);
		key.addSwipeKeyListener( this);
	}

	/**
	 * Adds an key or swipe listener to the keyboard.
	 * If listener is a swipe listener, it will be added as both a key and
	 * swipe listener.
	 * @param listener A listener to which events should be sent.
	 */
	public void addListener( KeyListener listener){
		addKeyListener( listener);
		if( listener instanceof SwipeKeyboardListener)
			addSwipeKeyboardListener( (SwipeKeyboardListener) listener);
	}

	/**
	 * Adds a swipe listener to the keyboard.
	 * If listener is a swipe listener, it will be added as only a swipe listener.
	 * @param listener A listener to which events should be sent.
	 */
	public void addSwipeKeyboardListener( SwipeKeyboardListener listener){
		swipeListeners.add( listener);
	}

	/**
	 * Adds an key listener to the keyboard.
	 * If listener is a swipe listener, it will be added as only a key listener.
	 * @param listener A listener to which events should be sent.
	 */
	public void addKeyListener( KeyListener listener){
		keyListeners.add( listener);
	}

	////////////////////
	// SMT overrides //
	////////////////////
	/**
	 * Draws the keyboard.
	 */
	@Override
	public void drawImpl() {
		if( drawBackground){
			fill( 0, 0, 0, 200);
			noStroke();
			rect(
				position.x, position.y,
				dimension.width, dimension.height,
				cornerRounding_topLeft, cornerRounding_topRight,
				cornerRounding_bottomRight, cornerRounding_bottomLeft);
		}
	}
	/**
	 * Draws the touch selection area of the keyboard.
	 */
	@Override
	public void pickDrawImpl() {}
	/**
	 * Overrides the Zone touch method to define rotate and stretch behavior.
	 */
	@Override
	public void touchImpl() {
		rst();
	}
	@Override
	public void assign(Iterable<? extends Touch> touches) {
		if( ! swipe_inProgress)
			super.assign( touches);
	}

	/////////////////////////
	// KeyListener handles //
	/////////////////////////
	/**
	 * Listens to the occurrance of a KeyPressed event and responds accordingly.
	 * @param event The KeyPressed event that has occured.
	 */
	@Override
	public void keyPressed( KeyEvent event){
		if( debug)
			System.out.printf( "Key Down: %s\n", event.getKeyChar());
		for( KeyListener listener : keyListeners)
			listener.keyPressed( event);
	}
	/**
	 * Listens to the occurrance of a KeyReleased event and responds accordingly.
	 * @param event The KeyReleased event that has occured.
	 */
	@Override
	public void keyReleased( KeyEvent event){
		if( debug)
			System.out.printf( "Key Up: %s\n", event.getKeyChar());
		for( KeyListener listener : keyListeners)
			listener.keyReleased( event);
	}
	/**
	 * Listens to the occurrance of a KeyTyped event and responds accordingly.
	 * @param event The KeyTyped event that has occured.
	 */
	@Override
	public void keyTyped( KeyEvent event){
		if( debug)
			System.out.printf( "Key Typed: %s\n", event.getKeyChar());
		for( KeyListener listener : keyListeners)
			listener.keyTyped( event);
	}

	///////////////////////////////
	// SwipeKeyListener handles //
	///////////////////////////////
	/**
	 * Listens to the occurrance of a SwipeStarted event and responds accordingly.
	 * @param event The SwipeStarted that has occured.
	 */
	@Override
	public void swipeStarted( SwipeKeyEvent event){
		Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Started: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());
		touch.addTouchListener( this);
		touches.add( touch);
		swipe_inProgress = true;
		swipeStack.add( event);
	}
	/**
	 * Listens to the occurrance of a SwipeHit event and responds accordingly.
	 * @param event The SwipeHit that has occured.
	 */
	@Override
	public void swipeHit( SwipeKeyEvent event){
		Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Hit: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());
		if( swipe_inProgress)
			swipeStack.add( event);
	}
	/**
	 * Listens to the occurrance of a SwipeEnded event and responds accordingly.
	 * @param event The SwipeEnded that has occured.
	 */
	@Override
	public void swipeEnded( SwipeKeyEvent event){
		Touch touch = event.getTouch();
		if( debug) System.out.printf( "Swipe Ended: %s TouchID: %d\n",
			event.getKeyChar(), touch.getSessionID());
	}

	/////////////////////////////
	// touch listener handles //
	/////////////////////////////
	/**
	 * Listens to the occurrance of a TouchDown event and responds accordingly.
	 * @param event The TouchDown that has occured.
	 */
	@Override
	public void handleTouchDown( TouchEvent event){}
	/**
	 * Listens to the occurrance of a TouchUp event and responds accordingly.
	 * @param event The TouchUp that has occured.
	 */
	@Override
	public void handleTouchUp( TouchEvent event){
		Touch touch = event.getTouch();
		if( debug)
			System.out.printf( "Touch died. TouchID: %d\n", touch.getSessionID());
		touches.remove( touch);
		if( touches.isEmpty()){
			swipe_inProgress = false;
			completeSwipe();
		}
	}
	/**
	 * Listens to the occurrance of a TouchMoved event and responds accordingly.
	 * @param event The TouchMoved that has occured.
	 */
	@Override
	public void handleTouchMoved( TouchEvent event){}

	//////////////////////////////
	// private utility methods //
	//////////////////////////////
	/**
	 * Converts the current stack of swipe events into a string and sends that
	 * string to all swipe listeners for processing.
	 */
	private void completeSwipe(){
		String swipe = new String();
		//load string
		for( SwipeKeyEvent event : swipeStack)
			swipe += event.getKeyChar();
		swipeStack.clear();

		//this shouldn't happen, but just in case:
		if( swipe.length() == 0) return;

		//preprocess swipe string
		// convert to lower case
		swipe = swipe.toLowerCase();
		// remove duplicate chars
		char last = swipe.charAt( 0);
		String result = String.valueOf( last);
		for( int i = 1; i < swipe.length(); i++){
			char current = swipe.charAt( i);
			if( current != last)
				result += current;
			last = current;
		}
		swipe = result;

		SwipeKeyboardEvent event = new SwipeKeyboardEvent(
			this, SwipeKeyboardEvent.Type.SWIPE_COMPLETED, swipe, resolver);
		for( SwipeKeyboardListener listener : swipeListeners)
			listener.swipeCompleted( event);
	}

	/////////////////////
	// public methods //
	/////////////////////
	/**
	 * Defines whether or not the background of the keyboard should be drawn.
	 * @param enabled True, if the background should be drawn, or false, if it
	 *                should not be.
	 */
	public void setBackgroundEnabled( boolean enabled){
		drawBackground = enabled;
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

	//////////////
	// layouts //
	//////////////
	/**
	 * A keyboard ayout that provides just the arrow keys.
	 */
	public static final SwipeKeyboardLayout arrowKeysLayout =
			new ArrowKeysLayout();
	/**
	 * A keyboard layout that provides only the basic key set.
	 */
	public static final SwipeKeyboardLayout condensedLayout =
			new CondensedLayout();
	/**
	 * A keyboard layout that provides an extended key set.
	 */
	public static final SwipeKeyboardLayout extendedLayout =
			new ExtendedLayout();
	/**
	 * A prototype keyboard layout designed to prove feasibility.
	 */
	public static final SwipeKeyboardLayout prototypeLayout =
			new SwipeKeyboardLayout(){
		/**
		 * Defines all the actions required to set a keyboard's layout, including
		 * creation, organization, and linking of keys.
		 * @param  keyboard The keyboard to be set up.
		 */
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