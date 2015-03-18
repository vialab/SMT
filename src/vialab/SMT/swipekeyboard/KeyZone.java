package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

//processing imports
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

//libTuio imports
import TUIO.*;

//local imports
import vialab.SMT.*;

/**
 * This class defines a zone that represents a key in a keyboard.
 **/
public class KeyZone extends Zone {
	/////////////////////////////
	// protected major fields //
	/////////////////////////////
	/** This object's list of KeyListeners. */
	protected Vector<KeyListener> keyListeners;
	/** The KeyEvent-style key code represented by this key. */
	protected int keyCode;
	/** The KeyEvent-style key char represented by this key. */
	protected char keyChar;
	/** The KeyEvent-style key char represented by this key when in alternate mode. */
	protected char keyChar_alternate;
	/** A flag to describe whether an alternate key is available. */
	protected boolean alternate_available;
	/** A flag to describe whether the alternate key is enabled. */
	protected boolean alternate_enabled;
	/** The KeyEvent-style key location of this key. */
	protected int keyLocation;
	/** Stores the current state of the key - down or up*/
	private boolean isDown;
	/** The current modifier mask, used for event creation */
	protected int modifierMask = 0;
	/** Whether the shift key is down or now */
	protected boolean shift_down = false;

	/////////////////////
	// drawing fields //
	/////////////////////
	/** A vector defining the current position of this zone. */
	protected PVector position;
	/** The text that appears on this key. */
	protected String label;
	/** The text that appears on this key when the alternate key is enabled. */
	protected String label_alternate;
	/** The icon that appears on this key. */
	protected PShape icon;
	/** Whether the given text appears on this key. */
	protected boolean label_enabled;
	/** Whether the given icon appears on this key. */
	protected boolean icon_enabled;
	/** The degree of rounding of the top left corner of this key. */
	protected int cornerRounding_topLeft;
	/** The degree of rounding of the top right corner of this key. */
	protected int cornerRounding_topRight;
	/** The degree of rounding of the bottom left corner of this key. */
	protected int cornerRounding_bottomLeft;
	/** The degree of rounding of the bottom right corner of this key. */
	protected int cornerRounding_bottomRight;
	/** The inset of the icon in the x axis */
	protected int x_inset;
	/** The inset of the icon in the y axis */
	protected int y_inset;
	/** The color of the outline of the key when it has not been recently hit */
	protected Color stroke_base;
	/** The color of the outline of the key when it has been recently hit */
	protected Color stroke_highlight;
	/** The last time that this key has been hit by a touch */
	protected TuioTime lastTouch;
	/** The duration of the fade animation that occurs when this key has been hit */
	protected static final long fade_duration = 350;


	///////////////////
	// debug fields //
	///////////////////
	/** Enables and disables debug print statements */
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
		this.keyChar_alternate = KeyEvent.CHAR_UNDEFINED;
		this.alternate_available = false;
		this.alternate_enabled = false;
		this.keyLocation = keyLocation;
		this.isDown = false;

		//set drawing fields
		this.label = String.valueOf( keyChar);
		this.label_enabled = true;
		this.icon = null;
		this.icon_enabled = false;
		this.stroke_base = new Color( 10, 10, 10, 255);
		this.stroke_highlight = new Color( 240, 240, 240, 255);
		this.lastTouch = TuioTime.getSessionTime();

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
		x_inset = 20;
		y_inset = 20;

		//other initialization
		keyListeners = new Vector<KeyListener>();
		touchEventBuffer = new TouchEvent[5];
	}

	////////////////////
	// SMT Overrides //
	////////////////////
	/** Draws the key. */
	@Override
	public void drawImpl(){
		//draw key
		pushStyle();
		fill( 20, 20, 20, 255);
		textMode( SHAPE);
		strokeJoin( ROUND);
		strokeCap( ROUND);
		strokeWeight( 4);

		//draw key background
		float ratio = 0;
		if( this.getNumTouches() == 0){
			long session = TuioTime.getSessionTime().getTotalMilliseconds();
			long last = lastTouch.getTotalMilliseconds();
			ratio = ( session - last) / (float) fade_duration;
		}
		Color mix = mixColours( stroke_highlight, stroke_base, ratio);
		stroke( mix.getRed(), mix.getGreen(), mix.getBlue(), mix.getAlpha());
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);

		//draw icon
		if( icon_enabled && icon != null){
			float offset_x = x_inset;
			float offset_y = y_inset;
			float available_width = dimension.width - x_inset * 2;
			float available_height = dimension.height - y_inset * 2;
			float widthScale = icon.getWidth() / available_width;
			float heightScale = icon.getHeight() / available_height;
			float width;
			float height;

			if( widthScale > heightScale){
				width = available_width;
				height = width * icon.getHeight() / icon.getWidth();
				offset_y += ( available_height - height) / 2;
			}
			else {
				height = available_height;
				width = height * icon.getWidth() / icon.getHeight();
				offset_x += ( available_width - width) / 2;
			}

			icon.disableStyle();
			noStroke();
			fill( 255, 255, 255, 255);
			shape( icon,
				position.x + offset_x,
				position.y + offset_y,
				width, height);
		}
		
		//draw label
		String label_text = alternate_enabled ? label_alternate : label;
		if( label_enabled && label_text != null){
			fill( 255, 255, 255, 255);
			textSize( Math.round( dimension.height * 0.6));
			textAlign( CENTER);
			float halfAscent = textAscent() / 2;
			float halfDescent = textDescent() / 2;
			if( shift_down) label_text = label_text.toUpperCase();
			text(
				label_text,
				position.x + halfDimension.width,
				position.y + halfDimension.height + halfAscent - halfDescent);
		}

		//clean up
		popStyle();
	}
	/** Draws the selection area of the key. */
	@Override
	public void pickDrawImpl(){
		rect(
			position.x, position.y,
			dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomRight, cornerRounding_bottomLeft);
	}
	/** Does nothing. */
	@Override
	public void touchImpl(){}
	/**
	 * Detects the touchDown event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchDownImpl( Touch touch){
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchDown");
		/*if( touchEventBuffer[0] == TouchEvent.ASSIGN)
			invokeKeyPressedEvent();*/
		bufferTouchEvent( TouchEvent.TOUCH_DOWN);
	}
	/**
	 * Detects the touchUp event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void touchUpImpl( Touch touch){
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "touchUp");
		/*if( touchEventBuffer[0] == TouchEvent.UNASSIGN &&
				touchEventBuffer[1] == TouchEvent.TOUCH_DOWN &&
				keyChar != KeyEvent.CHAR_UNDEFINED)
			invokeKeyTypedEvent();*/
		bufferTouchEvent( TouchEvent.TOUCH_UP);
	}
	/**
	 * Detects the press event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	@Override
	public void pressImpl( Touch touch){
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "press");
		//bufferTouchEvent( TouchEvent.PRESS);
	}
	/**
	 * Detects a touch entering event and responds by invoking a SwipeHit event.
	 * @param touches The list of touches passed to the function by SMT
	 */
	public void assign( Iterable<? extends Touch> touches){
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "assign");
		if( ! isDown){
			invokeKeyPressedEvent();
			setDown( true);
		}
		bufferTouchEvent( TouchEvent.ASSIGN);
		super.assign( touches);
	}
	/**
	 * Detects a touch exiting event and responds appropriately.
	 * @param touch The vialab.SMT.Touch passed to the function by SMT
	 */
	public void unassign( Touch touch){
		super.unassign( touch);
		if( debug) System.out.printf("%s %s %s\n", name, keyChar, "unassign");
		if( this.getNumTouches() == 0){
			invokeKeyReleasedEvent();
			if( keyChar != KeyEvent.CHAR_UNDEFINED)
				invokeKeyTypedEvent();
			setDown( false);
		}
		bufferTouchEvent( TouchEvent.UNASSIGN);
		lastTouch = touch.currentTime;
	}

	///////////////////////////////////
	// key event invocation methods //
	///////////////////////////////////
	/**
	 * Creates a KeyPressed event and sends it to all listeners for handling.
	 */
	public void invokeKeyPressedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_PRESSED);
		for( KeyListener listener : keyListeners){
			listener.keyPressed( event);
		}
	}
	/**
	 * Creates a KeyReleased event and sends it to all listeners for handling.
	 */
	public void invokeKeyReleasedEvent(){
		KeyEvent event = constructKeyEvent( KeyEvent.KEY_RELEASED);
		for( KeyListener listener : keyListeners){
			listener.keyReleased( event);
		}
	}
	/**
	 * Creates a KeyTyped event and sends it to all listeners for handling.
	 */
	public void invokeKeyTypedEvent(){
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

	/////////////////////////////////
	// public set accessor methods //
	/////////////////////////////////
	/**
	 * Sets whether this key is down or not
	 * @param down whether this key should be down or not
	 */
	protected void setDown( boolean down){
		this.isDown = down;
	}
	/**
	 * Sets the current modifier mask
	 * @param mask the desired mask
	 */
	public void setModifierMask( int mask){
		this.modifierMask = mask;
		this.shift_down =
			( mask & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK;
	}
	/**
	 * Sets an alternate character for the key.
	 * @param keyChar the desired alternate character
	 */
	public void setAlternateChar( char keyChar){
		if( keyChar == KeyEvent.CHAR_UNDEFINED){
			alternate_available = false;
			alternate_enabled = false;
		}
		else{
			keyChar_alternate = keyChar;
			alternate_available = true;
			label_alternate = String.valueOf( keyChar);
		}
	}
	/**
	 * Sets whether the alternate character for the key is enabled.
	 * @param enabled whether to enable or disable the alternate key
	 */
	public void setAlternateEnabled( boolean enabled){
		if( alternate_available)
			alternate_enabled = enabled;
	}
	/**
	 * Sets the text that is displayed on the key.
	 * @param label The text to be displayed on the key.
	 */
	public void setLabel( String label){
		this.label = label;
		this.label_enabled = true;
		this.icon_enabled = false;
	}
	/**
	 * Sets the text that is displayed on the key when the alternate key is enabled.
	 * @param label The text to be displayed on the key.
	 */
	public void setAlternateLabel( String label){
		this.label_alternate = label;
	}
	/**
	 * Sets the icon that is displayed on the key.
	 * @param icon The icon to be displayed on the key.
	 */
	public void setIcon( PShape icon){
		this.icon = icon;
		this.label_enabled = false;
		this.icon_enabled = true;
	}
	/**
	 * Sets whether the text appears on this key.
	 * @param enabled whether the text should appear on this key
	 */
	public void setLabelEnabled( boolean enabled){
		this.label_enabled = enabled;
	}
	/**
	 * Sets whether the icon appears on this key.
	 * @param enabled whether the icon should appear on this key
	 */
	public void setIconEnabled( boolean enabled){
		this.icon_enabled = enabled;
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
	/**
	 * Sets the inset
	 * @param x_inset the inset in the x axis 
	 * @param y_inset the inset in the y axis 
	 */
	public void setInset(
			int x_inset, int y_inset){
		this.x_inset = x_inset;
		this.y_inset = y_inset;
	}

	/////////////////////////////////
	// public get accessor methods //
	/////////////////////////////////
	/** returns whether an alternate key is available or not */
	public boolean getAlternateAvailable(){
		return alternate_available;
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
		//get parameters
		int keyCode = ( id == KeyEvent.KEY_TYPED) ?
			KeyEvent.VK_UNDEFINED : this.keyCode;
		int keyLocation = ( id == KeyEvent.KEY_TYPED) ?
			KeyEvent.KEY_LOCATION_UNKNOWN : this.keyLocation;
		char keyChar = ( alternate_enabled) ?
			this.keyChar_alternate : this.keyChar;
		if( shift_down) keyChar = Character.toUpperCase( keyChar);
		//make and return event
		return new KeyEvent(
			this.applet, id, System.currentTimeMillis(), modifierMask,
			keyCode, keyChar, keyLocation);
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
	/**
	 * Mixes two colours together. When ratio is 0, it used entirely the base
	 * colour. When ratio is 1, it uses entirely the highlight colour. Anywhere
	 * between 0 and 1 mixes the two colours together accordingly.
	 * @param  base The base colour
	 * @param  highlight The hightlight colour
	 * @param  ratio The ratio that determines how the two colours are mixed.
	 * @return The mixed colour
	 */
	private Color mixColours( Color base, Color highlight, float ratio){
		float minned_ratio = Math.min( 1f, ratio);
		float converse = 1 - minned_ratio;
		float red = base.getRed() * converse + highlight.getRed() * minned_ratio;
		float green = base.getGreen() * converse + highlight.getGreen() * minned_ratio;
		float blue = base.getBlue() * converse + highlight.getBlue() * minned_ratio;
		float alpha = base.getAlpha() * converse + highlight.getAlpha() * minned_ratio;
		return new Color(
			clamp( red),
			clamp( green),
			clamp( blue),
			clamp( alpha));
	}
	/**
	 * Clamps a float into an integer colour domain ( 0 through 255 )
	 * @param  c the float to be clamped
	 * @return The closest integer on [ 0, 255] to c
	 */
	private int clamp( float c){
		if( c < 0) return 0;
		else if( c > 255) return 255;
		else return Math.round( c);
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