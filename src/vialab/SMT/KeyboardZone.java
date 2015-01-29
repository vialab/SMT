package vialab.SMT;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import processing.core.PFont;

/**
 * KeyboardZone is an implementation of an on-screen keyboard. Use its
 * addKeyListener(Zone) method to allow other zones, such as TextZone, to
 * listen/receive the keyboard input.
 */
public class KeyboardZone extends Zone {
	
	private IndirectDrawingChildZone indirectDrawingChild;
	
	//This inner Class is set indirect and draws the KeyZones into itself
	//allowing the keyboardZone to have children outside of itself, which is
	//used when we add Zones to the KeyboardZones to auto listen to its
	//key events
	class IndirectDrawingChildZone extends Zone{

		protected boolean updateOnlyWhenModified(){
			return true;
		}
		
		IndirectDrawingChildZone(int width, int height){
			super( 0, 0, width, height);
		}
		
		boolean warnDraw(){
			return false;
		}
		
		@Override
		public void touch(){}
		
		@Override
		public void pickDraw(){}

		@Override
		public void draw() {
			KeyboardZone kb = (KeyboardZone) getParent();
			smooth(8);
			fill(kb.backgroundColor, kb.alpha);
			rect(0, 0, width, height);
			
			//update Modifiers from key state changes
			updateModifiersFromKeys();
		}
	};

	public int keyColor = 200;

	public int keyPressedColor = 150;

	public int backgroundColor = 0;

	public int textColor = 0;

	public int alpha = 255;

	public boolean capsLockOn = false;

	private int MODIFIERS = 0;

	boolean warnDraw() {
		return false;
	}
	
	boolean warnTouch(){
		return false;
	}

	private Component keyboardComponent = new Component() {
		private static final long serialVersionUID = -3237916182106172342L;
	};

	private static final int DEFAULT_HEIGHT = 250;
	private static final int DEFAULT_WIDTH = 750;

	private static final int NUM_KEYBOARD_ROWS = 5;

	class KeyZone extends ButtonZone {
		private Keys key;
		private boolean keyDown;

		public KeyZone(int x, int y, int width, int height, Keys key, int fontSize, PFont font) {
			super(null, x, y, width, height, key.text, fontSize, font);
			this.key = key;
		}

		@Override
		public void drawImpl() {
			textMode(SHAPE);
			if (deactivated) {
				drawImpl(color(255, alpha), color(175, alpha));
			}
			else {
				if (isButtonDown()) {
					drawImpl(color(keyPressedColor, alpha), color(textColor, alpha));
				}
				else {
					drawImpl(color(keyColor, alpha), color(textColor, alpha));
				}
			}

			// drawCapsLock state
			if (key.keyCode == KeyEvent.VK_CAPS_LOCK) {
				if (capsLockOn) {
					text("On", width / 2, height * 3 / 4);
				}
				else {
					text("Off", width / 2, height * 3 / 4);
				}
			}
		}

		@Override
		protected void drawImpl(int buttonColor, int textColor) {
			stroke(borderColor);
			strokeWeight(borderWeight);
			fill(buttonColor);
			rect(borderWeight, borderWeight, width - 2 * borderWeight, height - 2 * borderWeight);

			if (text != null) {
				if (font != null) {
					textFont(font);
				}
				textAlign(CENTER, CENTER);
				textSize(fontSize);
				fill(textColor);
				if (((!capsLockOn && isModifierDown(KeyEvent.VK_SHIFT)) || (capsLockOn && !isModifierDown(KeyEvent.VK_SHIFT)))
						&& key.text.length() == 1) {
					text(text.toUpperCase(), width / 2 - borderWeight, height / 2 - borderWeight);
				}
				else {
					text(text, width / 2 - borderWeight, height / 2 - borderWeight);
				}
			}
		}

		@Override
		public void touchDownImpl(Touch touch) {
			super.touchDownImpl(touch);
			if (!keyDown) {
				keyDown();
			}
		}

		@Override
		public void touchMovedImpl(Touch touch) {
			if (!keyDown) {
				keyDown();
			}
		}

		@Override
		public void touchImpl(){}
		
		@Override
		public void assign(Touch... touches) {
			//setModified on our indirect parent if this key transitioned from buttonUp to buttondown
			boolean prevDown = isButtonDown();
			super.assign(touches);
			if(prevDown != isButtonDown()){
				indirectDrawingChild.setModified(true);
			}
		}
		
		@Override
		public void unassign(long id) {
			//setModified on our indirect parent if this key transitioned from buttonDown to buttonUp
			boolean prevDown = isButtonDown();
			super.unassign(id);
			if(prevDown != isButtonDown()){
				indirectDrawingChild.setModified(true);
			}
		}

		private void keyDown() {
			keyDown = true;
			char k = key.keyChar;
			// if not undefined char and shift is on, set to upper case
			if (key.keyChar != KeyEvent.CHAR_UNDEFINED
					&& ((((MODIFIERS >> 6) % 2 == 1) && !capsLockOn) || (((MODIFIERS >> 6) % 2 == 0) && capsLockOn))) {
				k = Character.toUpperCase(key.keyChar);
				// if key has a different value for when shift is down, set it
				// to that
				if (key.keyChar != key.shiftKeyChar) {
					k = key.shiftKeyChar;
				}
			}
			// send key press using KeyEvent to listeners
			for (KeyListener l : keyListeners) {
				l.keyPressed(new KeyEvent(keyboardComponent, KeyEvent.KEY_PRESSED, System
						.currentTimeMillis(), MODIFIERS, key.keyCode, k));
			}
		}

		@Override
		protected void pressImpl(Touch t) {
			// toggle Caps Lock
			if (key.keyCode == KeyEvent.VK_CAPS_LOCK) {
				capsLockOn = !capsLockOn;
			}
			else {
				if (keyDown) {
					keyUp();
				}
			}
		}

		private void keyUp() {
			keyDown = false;
			char k = key.keyChar;
			// if not undefined char and shift is on, set to upper case
			if (key.keyChar != KeyEvent.CHAR_UNDEFINED
					&& ((((MODIFIERS >> 6) % 2 == 1) && !capsLockOn) || (((MODIFIERS >> 6) % 2 == 0) && capsLockOn))) {
				k = Character.toUpperCase(key.keyChar);
				// if key has a different value for when shift is down, set it
				// to that
				if (key.keyChar != key.shiftKeyChar) {
					k = key.shiftKeyChar;
				}
			}
			// send key release and typed using KeyEvent to listeners
			for (KeyListener l : keyListeners) {
				l.keyReleased(new KeyEvent(keyboardComponent, KeyEvent.KEY_RELEASED, System
						.currentTimeMillis(), MODIFIERS, key.keyCode, k));
				if (!key.isModifier) {
					l.keyTyped(new KeyEvent(keyboardComponent, KeyEvent.KEY_TYPED, System
							.currentTimeMillis(), MODIFIERS, KeyEvent.VK_UNDEFINED, k));
				}
			}
		}
	}

	private enum Keys {
		KEY_BACK_QUOTE( '~', '`', KeyEvent.VK_BACK_QUOTE, false),
		KEY_1( '!', '1', KeyEvent.VK_1, false),
		KEY_2( '@', '2', KeyEvent.VK_2, false),
		KEY_3( '#', '3', KeyEvent.VK_3, false),
		KEY_4( '$', '4', KeyEvent.VK_4, false),
		KEY_5( '%', '5', KeyEvent.VK_5, false),
		KEY_6( '^', '6', KeyEvent.VK_6, false),
		KEY_7( '$', '7', KeyEvent.VK_7, false),
		KEY_8( '*', '8', KeyEvent.VK_8, false),
		KEY_9( '(', '9', KeyEvent.VK_9, false),
		KEY_0( ')', '0', KeyEvent.VK_0, false),
		KEY_MINUS( '_', '-', KeyEvent.VK_MINUS, false),
		KEY_EQUALS( '+', '=', KeyEvent.VK_EQUALS, false),
		KEY_BACKSPACE( '\b', KeyEvent.VK_BACK_SPACE, false, "Backspace", 2.0f),
		KEY_TAB( '\t', KeyEvent.VK_TAB, false, "Tab", 1.5f),
		KEY_Q( 'q', KeyEvent.VK_Q, false),
		KEY_W( 'w', KeyEvent.VK_W, false),
		KEY_E( 'e', KeyEvent.VK_E, false),
		KEY_R( 'r', KeyEvent.VK_R, false),
		KEY_T( 't', KeyEvent.VK_T, false),
		KEY_Y( 'y', KeyEvent.VK_Y, false),
		KEY_U( 'u', KeyEvent.VK_U, false),
		KEY_I( 'i', KeyEvent.VK_I, false),
		KEY_O( 'o', KeyEvent.VK_O, false),
		KEY_P( 'p', KeyEvent.VK_P, false),
		KEY_OPEN_BRACKET( '{', '[', KeyEvent.VK_OPEN_BRACKET, false),
		KEY_CLOSE_BRACKET( '}', ']', KeyEvent.VK_CLOSE_BRACKET, false),
		KEY_BACK_SLASH( '|', '\\', KeyEvent.VK_BACK_SLASH, false, 1.5f),
		KEY_CAPS_LOCK( '\u21EA', KeyEvent.VK_CAPS_LOCK, false, "Caps Lock", 1.75f),
		KEY_A( 'a', KeyEvent.VK_A, false),
		KEY_S( 's', KeyEvent.VK_S, false),
		KEY_D( 'd', KeyEvent.VK_D, false),
		KEY_F( 'f', KeyEvent.VK_F, false),
		KEY_G( 'g', KeyEvent.VK_G, false),
		KEY_H( 'h', KeyEvent.VK_H, false),
		KEY_J( 'j', KeyEvent.VK_J, false),
		KEY_K( 'k', KeyEvent.VK_K, false),
		KEY_L( 'l', KeyEvent.VK_L, false),
		KEY_SEMICOLON( ':', ';', KeyEvent.VK_SEMICOLON, false),
		KEY_QUOTE( '"', '\'', KeyEvent.VK_QUOTE, false),
		KEY_ENTER( '\n', KeyEvent.VK_ENTER, false, "Enter", 2.25f),
		KEY_SHIFT_LEFT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT, true, "Shift", 2.3f),
		KEY_Z( 'z', KeyEvent.VK_Z, false),
		KEY_X( 'x', KeyEvent.VK_X, false),
		KEY_C( 'c', KeyEvent.VK_C, false),
		KEY_V( 'v', KeyEvent.VK_V, false),
		KEY_B( 'b', KeyEvent.VK_B, false),
		KEY_N( 'n', KeyEvent.VK_N, false),
		KEY_M( 'm', KeyEvent.VK_M, false),
		KEY_COMMA( '<', ',', KeyEvent.VK_COMMA, false),
		KEY_PERIOD( '>', '.', KeyEvent.VK_PERIOD, false),
		KEY_SLASH( '?', '/', KeyEvent.VK_SLASH, false),
		KEY_SHIFT_RIGHT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT, true, "Shift", 2.7f),
		KEY_CTRL_LEFT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_CONTROL, true, "Control", 1.5f),
		KEY_ALT_LEFT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_ALT, true, "Alt", 1.2f),
		KEY_SPACE( ' ', KeyEvent.VK_SPACE, false, "Space", 9.6f),
		KEY_ALT_RIGHT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_ALT, true, "Alt", 1.2f),
		KEY_CTRL_RIGHT( KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_CONTROL, true, "Control", 1.5f);

		// KEY_DELETE('\u007F', KeyEvent.VK_DELETE, false, "Delete");

		private final boolean isModifier;
		private final char shiftKeyChar;
		private final char keyChar;
		private final int keyCode;
		private final String text;
		private final float keyWidthRatio;

		Keys(char keyChar, int keyCode, boolean isModifier) {
			this(keyChar, keyCode, isModifier, Character.toString(keyChar));
		}

		Keys(char keyChar, int keyCode, boolean isModifier, String text) {
			this(keyChar, keyChar, keyCode, isModifier, text, 1.0f);
		}

		Keys(char keyChar, int keyCode, boolean isModifier, String text, float keyWidthRatio) {
			this(keyChar, keyChar, keyCode, isModifier, text, keyWidthRatio);
		}

		Keys(char shiftKeyChar, char keyChar, int keyCode, boolean isModifier) {
			this(shiftKeyChar, keyChar, keyCode, isModifier, 1.0f);
		}

		Keys(char shiftKeyChar, char keyChar, int keyCode, boolean isModifier, float keyWidthRatio) {
			this(shiftKeyChar, keyChar, keyCode, isModifier, shiftKeyChar + "\n" + keyChar,
					keyWidthRatio);
		}

		Keys(char shiftKeyChar, char keyChar, int keyCode, boolean isModifier, String text,
				float keyWidthRatio) {
			this.shiftKeyChar = shiftKeyChar;
			this.keyChar = keyChar;
			this.keyCode = keyCode;
			this.isModifier = isModifier;
			this.text = text;
			this.keyWidthRatio = keyWidthRatio;
		}

		public String toString() {
			return String.valueOf( keyChar);
		}
	}

	private ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();

		
	/**
	 * [KeyboardZone description]
	 */
	public KeyboardZone( ) {
		this(null, true);
	}

	/**
	 * [KeyboardZone description]
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 */
	public KeyboardZone( boolean keysSentToApplet) {
		this(null, keysSentToApplet);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 */
	public KeyboardZone( int x, int y) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 */
	public KeyboardZone( int x, int y, boolean keysSentToApplet) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, keysSentToApplet);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 */
	public KeyboardZone( String name) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, true, 255);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 */
	public KeyboardZone( String name, boolean keysSentToApplet) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, keysSentToApplet, 255);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 */
	public KeyboardZone( int x, int y, int width, int height) {
		this(null, x, y, width, height, true, 255);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 */
	public KeyboardZone( int x, int y, int width, int height, boolean keysSentToApplet) {
		this(null, x, y, width, height, keysSentToApplet, 255);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 */
	public KeyboardZone( String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, true, 255);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param alpha	Transparency level for keyboard
	 */
	public KeyboardZone( String name, int alpha) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, true, alpha);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 * @param alpha	Transparency level for keyboard
	 */
	public KeyboardZone( String name, boolean keysSentToApplet, int alpha) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, keysSentToApplet, alpha);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param alpha	Transparency level for keyboard
	 */
	public KeyboardZone( int x, int y, int width, int height, int alpha) {
		this(null, x, y, width, height, true, alpha);
	}

	/**
	 * [KeyboardZone description]
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 * @param alpha	Transparency level for keyboard
	 */
	public KeyboardZone( int x, int y, int width, int height, boolean keysSentToApplet, int alpha) {
		this(null, x, y, width, height, keysSentToApplet, alpha);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param alpha	Transparency level for keyboard
	 */
	public KeyboardZone( String name, int x, int y, int width, int height, int alpha) {
		this( name, x, y, width, height, true, alpha);
	}

	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param alpha	Transparency level for keyboard
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 */
	public KeyboardZone( String name, int x, int y, int width, int height,
			boolean keysSentToApplet, int alpha) {
		this( name, x, y, width, height, keysSentToApplet, alpha, 0, 200, 150, 0);
	}
	
	/**
	 * [KeyboardZone description]
	 * @param name	The name of the zone
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 * @param alpha	Transparency level for keyboard
	 * @param backgroundColor	Background colour of the keyboard
	 * @param keyColor	Colour of the keyboard keys
	 * @param keyPressedColor	Colour of the keyboard keys when they are pressed down
	 * @param textColor	Colour of the text in the keyboard keys
	 */
	public KeyboardZone( String name, int x, int y, int width, int height,
			boolean keysSentToApplet, int alpha, int backgroundColor, int keyColor,
			int keyPressedColor, int textColor) {
		this( name, x, y, width, height, keysSentToApplet, alpha, backgroundColor,
			keyColor, keyPressedColor, textColor, null);
	}

	/**
	 * [KeyboardZone description]
	 * 
	 * @param name	The name of the zone
	 * @param x	X-coordinate of the upper left corner of the zone
	 * @param y	Y-coordinate of the upper left corner of the zone
	 * @param width	Width of the zone
	 * @param height	Height of the zone
	 * @param keysSentToApplet	Sets if the key input is sent to Applet
	 * @param alpha	Transparency level for keyboard
	 * @param backgroundColor	Background colour of the keyboard
	 * @param keyColor	Colour of the keyboard keys
	 * @param keyPressedColor	Colour of the keyboard keys when they are pressed down
	 * @param textColor	Colour of the text in the keyboard keys
	 * @param font	Font used to display text in the keyboard keys
	 */
	public KeyboardZone( String name, int x, int y, int width, int height, boolean keysSentToApplet,
			int alpha, int backgroundColor, int keyColor, int keyPressedColor, int textColor, PFont font) {
		super(name, x, y, width, height);

		indirectDrawingChild = new IndirectDrawingChildZone(width, height);
		this.add(indirectDrawingChild);
		
		int KEYS_PER_ROW = 15;
		int DEFAULT_KEY_WIDTH = (width * 9 / 10) / KEYS_PER_ROW;
		int fontSize = (this.height / NUM_KEYBOARD_ROWS) * 16 / 50;

		for (Keys k : Keys.values()) {
			indirectDrawingChild.add(new KeyZone(0, 0, (int) (k.keyWidthRatio * DEFAULT_KEY_WIDTH),
					(this.height * 9 / 10) / NUM_KEYBOARD_ROWS, k, fontSize, font));
		}

		SMT.grid(width / 20, height / 20, (width * 9 / 10), 0, 0,
				indirectDrawingChild.getChildren());

		
		indirectDrawingChild.setDirect(false);
		for (Zone zone : indirectDrawingChild.children) {
			zone.setDirect(true);
		}

		if (keysSentToApplet) {
			// add the processing applet as a KeyListener by default
			this.addKeyListener(applet);
		}

		this.alpha = alpha;
		this.backgroundColor = backgroundColor;
		this.keyColor = keyColor;
		this.keyPressedColor = keyPressedColor;
		this.textColor = textColor;
	}

	/**
	 * Set the size of the keyboard zone
	 * @param w width
	 * @param h height
	 */
	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		int KEYS_PER_ROW = 15;
		int DEFAULT_KEY_WIDTH = (width * 9 / 10) / KEYS_PER_ROW;
		for (Zone child : this.children) {
			if (child instanceof KeyZone) {
				KeyZone keyZone = (KeyZone) child;
				keyZone.setSize((int) (keyZone.key.keyWidthRatio * DEFAULT_KEY_WIDTH),
						(this.height * 9 / 10) / NUM_KEYBOARD_ROWS);
				keyZone.fontSize = ((this.height * 9 / 10) / NUM_KEYBOARD_ROWS) * 16 / 50;
			}
		}
		if(indirectDrawingChild != null){
			SMT.grid(width / 20, height / 20, (width * 9 / 10), 0, 0,
					indirectDrawingChild.getChildren());
		}
	}

	protected void updateModifiersFromKeys() {
		// make sure modifiers have the correct setting as they act
		// differently than normal keys and should be unset even without a
		// touchUp event, although really just a hack, as touchUp should be
		// generated whenever a touch is unassigned from a zone
		boolean shiftDown = false;
		boolean altDown = false;
		boolean ctrlDown = false;
		boolean metaDown = false;
		for (Zone k : indirectDrawingChild.children) {
			if (k instanceof KeyZone) {
				KeyZone key = (KeyZone) k;
				if (key.isButtonDown()) {
					if (key.key.keyCode == KeyEvent.VK_SHIFT) {
						shiftDown = true;
					}
					else if (key.key.keyCode == KeyEvent.VK_CONTROL) {
						ctrlDown = true;
					}
					else if (key.key.keyCode == KeyEvent.VK_ALT) {
						altDown = true;
					}
					else if (key.key.keyCode == KeyEvent.VK_META) {
						metaDown = true;
					}
				}
			}
		}
		if (shiftDown) {
			modifierDown(KeyEvent.VK_SHIFT);
		}
		else {
			modifierUp(KeyEvent.VK_SHIFT);
		}
		if (ctrlDown) {
			modifierDown(KeyEvent.VK_CONTROL);
		}
		else {
			modifierUp(KeyEvent.VK_CONTROL);
		}
		if (altDown) {
			modifierDown(KeyEvent.VK_ALT);
		}
		else {
			modifierUp(KeyEvent.VK_ALT);
		}
		if (metaDown) {
			modifierDown(KeyEvent.VK_META);
		}
		else {
			modifierUp(KeyEvent.VK_META);
		}
	}
	
	/**
	 * [add description]
	 * @param  z [description]
	 * @return   [description]
	 */
	@Override
	public boolean add(Zone z){
		if(!(z instanceof KeyZone)){
			this.addKeyListener(z);
		}
		return super.add(z);
	}
	
	/**
	 * [remove description]
	 * @param  z [description]
	 * @return   [description]
	 */
	@Override
	public boolean remove(Zone z){
		if(!(z instanceof KeyZone)){
			this.removeKeyListener(z);
		}
		return super.remove(z);
	}

	/**
	 * This adds a KeyListener to listen to this keyboard implementation. Since
	 * Zone implements the KeyListener interface, Zone can be passed to this
	 * method
	 * 
	 * @param listener
	 *            The KeyListener to add to the keyboard, usually a Zone, which
	 *            implements the KeyListener interface
	 */
	public void addKeyListener(KeyListener listener) {
		this.keyListeners.add(listener);
	}

	/**
	 * This removes a KeyListener from this keyboard
	 * 
	 * @param listener The KeyListener to remove from the keyboard,
	 *  usually a Zone that implements the KeyListener interface
	 */
	public void removeKeyListener(KeyListener listener) {
		this.keyListeners.remove(listener);
	}

	/**
	 * This clears all KeyListeners from this keyboard
	 */
	public void clearKeyListeners(){
		this.keyListeners.clear();
	}

	/**
	 * This returns an array of all KeyListeners on this keyboard that are Zones
	 * 
	 * @return A Zone[] containing all zones that are KeyListeners on this
	 *         keyboard
	 */
	public Zone[] getZoneKeyListeners() {
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for (KeyListener k : this.keyListeners) {
			if (k instanceof Zone) {
				zones.add((Zone) k);
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	/**
	 * This returns an array of all KeyListeners on this keyboard
	 * 
	 * @return A KeyListener[] containing all KeyListeners on this keyboard
	 */
	public KeyListener[] getKeyListeners() {
		return keyListeners.toArray(new Zone[keyListeners.size()]);
	}

	/**
	 * [modifierDown description]
	 * @param keyCode [description]
	 */
	private void modifierDown(int keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_SHIFT:
				if ((MODIFIERS >> 6) % 2 == 0) {
					// add modifier only if not down already
					MODIFIERS += KeyEvent.SHIFT_DOWN_MASK;
				}
				break;
			case KeyEvent.VK_CONTROL:
				if ((MODIFIERS >> 7) % 2 == 0) {
					// add modifier only if not down already
					MODIFIERS += KeyEvent.CTRL_DOWN_MASK;
				}
				break;
			case KeyEvent.VK_ALT:
				if ((MODIFIERS >> 8) % 2 == 0) {
					// add modifier only if not down already
					MODIFIERS += KeyEvent.ALT_DOWN_MASK;
				}
				break;
			default:
				break;
		}
	}

	/**
	 * [modifierDown description]
	 * @param keyCode [description]
	 */
	private void modifierUp(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_SHIFT:
			if ((MODIFIERS >> 6) % 2 == 1) {
				// remove modifier only if down already
				MODIFIERS -= KeyEvent.SHIFT_DOWN_MASK;
			}
			break;
		case KeyEvent.VK_CONTROL:
			if ((MODIFIERS >> 7) % 2 == 1) {
				// remove modifier only if down already
				MODIFIERS -= KeyEvent.CTRL_DOWN_MASK;

			}
			break;
		case KeyEvent.VK_ALT:
			if ((MODIFIERS >> 8) % 2 == 1) {
				// remove modifier only if down already
				MODIFIERS -= KeyEvent.ALT_DOWN_MASK;

			}
			break;
		default:
			break;
		}
	}

	/**
	 * [getAlpha description]
	 * @return [description]
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * [getAlpha description]
	 * @param alpha [description]
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * [isModifierDown description]
	 * @param  keyCode [description]
	 * @return         [description]
	 */
	public boolean isModifierDown(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_SHIFT:
			if ((MODIFIERS >> 6) % 2 == 1) {
				return true;
			}
			break;
		case KeyEvent.VK_CONTROL:
			if ((MODIFIERS >> 7) % 2 == 1) {
				return true;
			}
			break;
		case KeyEvent.VK_ALT:
			if ((MODIFIERS >> 8) % 2 == 1) {
				return true;
			}
			break;
		default:
			break;
		}

		return false;
	}
}
