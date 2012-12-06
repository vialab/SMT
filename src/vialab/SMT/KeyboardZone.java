/**
 * 
 */
package vialab.SMT;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * KeyboardZone is an implementation of an on-screen keyboard, use this zones
 * addKeyListener(Zone) method to add zones such as TextZone to listen to this
 * keyboard
 */
public class KeyboardZone extends Zone {

	private int MODIFIERS = 0;

	private Component keyboardComponent = new Component() {
		private static final long serialVersionUID = -3237916182106172342L;
	};

	class KeyZone extends ButtonZone {
		private Keys key;
		private boolean keyDown;

		public KeyZone(int x, int y, int width, int height, Keys key) {
			super(x, y, width, height, key.text);
			this.key = key;
		}

		@Override
		public void touchDown(Touch touch) {
			super.touchDown(touch);
			if (!keyDown) {
				keyDown();
			}
		}

		@Override
		public void touchMovedImpl(Touch touch) {
			this.setButtonDown();
			if (!keyDown && this.isButtonDown()) {
				keyDown();
			}
		}

		private void keyDown() {
			keyDown = true;
			char k = key.keyChar;
			// if not undefined char and shift is on, set to upper case
			if (key.keyChar != KeyEvent.CHAR_UNDEFINED && (MODIFIERS >> 6) % 2 == 1) {
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
		public void pressImpl() {
			if (keyDown) {
				keyUp();
			}
		}

		private void keyUp() {
			keyDown = false;
			char k = key.keyChar;
			// if not undefined char and shift is on, set to upper case
			if (key.keyChar != KeyEvent.CHAR_UNDEFINED && (MODIFIERS >> 6) % 2 == 1) {
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

	private static final int DEFAULT_HEIGHT = 250;
	private static final int DEFAULT_WIDTH = 750;

	private static final int NUM_KEYBOARD_ROWS = 5;

	private enum Keys {
		KEY_BACK_QUOTE('~', '`', KeyEvent.VK_BACK_QUOTE, false), KEY_1('!', '1', KeyEvent.VK_1,
				false), KEY_2('@', '2', KeyEvent.VK_2, false), KEY_3('#', '3', KeyEvent.VK_3, false), KEY_4(
				'$', '4', KeyEvent.VK_4, false), KEY_5('%', '5', KeyEvent.VK_5, false), KEY_6('^',
				'6', KeyEvent.VK_6, false), KEY_7('$', '7', KeyEvent.VK_7, false), KEY_8('*', '8',
				KeyEvent.VK_8, false), KEY_9('(', '9', KeyEvent.VK_9, false), KEY_0(')', '0',
				KeyEvent.VK_0, false), KEY_MINUS('_', '-', KeyEvent.VK_MINUS, false), KEY_EQUALS(
				'+', '=', KeyEvent.VK_EQUALS, false), KEY_BACKSPACE('\b', KeyEvent.VK_BACK_SPACE,
				false, "Backspace", 2.0f), KEY_TAB('\t', KeyEvent.VK_TAB, false, "Tab", 1.5f), KEY_Q(
				'q', KeyEvent.VK_Q, false), KEY_W('w', KeyEvent.VK_W, false), KEY_E('e',
				KeyEvent.VK_E, false), KEY_R('r', KeyEvent.VK_R, false), KEY_T('t', KeyEvent.VK_T,
				false), KEY_Y('y', KeyEvent.VK_Y, false), KEY_U('u', KeyEvent.VK_U, false), KEY_I(
				'i', KeyEvent.VK_I, false), KEY_O('o', KeyEvent.VK_O, false), KEY_P('p',
				KeyEvent.VK_P, false), KEY_OPEN_BRACKET('{', '[', KeyEvent.VK_OPEN_BRACKET, false), KEY_CLOSE_BRACKET(
				'}', ']', KeyEvent.VK_CLOSE_BRACKET, false), KEY_BACK_SLASH('|', '\\',
				KeyEvent.VK_BACK_SLASH, false, 1.5f), KEY_CAPS_LOCK('\u21EA',
				KeyEvent.VK_CAPS_LOCK, false, "Caps Lock", 1.75f), KEY_A('a', KeyEvent.VK_A, false), KEY_S(
				's', KeyEvent.VK_S, false), KEY_D('d', KeyEvent.VK_D, false), KEY_F('f',
				KeyEvent.VK_F, false), KEY_G('g', KeyEvent.VK_G, false), KEY_H('h', KeyEvent.VK_H,
				false), KEY_J('j', KeyEvent.VK_J, false), KEY_K('k', KeyEvent.VK_K, false), KEY_L(
				'l', KeyEvent.VK_L, false), KEY_SEMICOLON(':', ';', KeyEvent.VK_SEMICOLON, false), KEY_QUOTE(
				'"', '\'', KeyEvent.VK_QUOTE, false), KEY_ENTER('\n', KeyEvent.VK_ENTER, false,
				"Enter", 2.25f), KEY_SHIFT_LEFT(KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT, true,
				"Shift", 2.3f), KEY_Z('z', KeyEvent.VK_Z, false), KEY_X('x', KeyEvent.VK_X, false), KEY_C(
				'c', KeyEvent.VK_C, false), KEY_V('v', KeyEvent.VK_V, false), KEY_B('b',
				KeyEvent.VK_B, false), KEY_N('n', KeyEvent.VK_N, false), KEY_M('m', KeyEvent.VK_M,
				false), KEY_COMMA('<', ',', KeyEvent.VK_COMMA, false), KEY_PERIOD('>', '.',
				KeyEvent.VK_PERIOD, false), KEY_SLASH('?', '/', KeyEvent.VK_SLASH, false), KEY_SHIFT_RIGHT(
				KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_SHIFT, true, "Shift", 2.7f), KEY_CTRL_LEFT(
				KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_CONTROL, true, "Control", 1.5f), KEY_META_LEFT(
				KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_META, true, "Meta", 1.2f), KEY_ALT_LEFT(
				KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_ALT, true, "Alt", 1.2f), KEY_SPACE(' ',
				KeyEvent.VK_SPACE, false, "Space", 6.0f), KEY_ALT_RIGHT(KeyEvent.CHAR_UNDEFINED,
				KeyEvent.VK_ALT, true, "Alt", 1.2f), KEY_META_RIGHT(KeyEvent.CHAR_UNDEFINED,
				KeyEvent.VK_META, true, "Meta", 1.2f), KEY_CONTEXT_MENU(KeyEvent.CHAR_UNDEFINED,
				KeyEvent.VK_CONTEXT_MENU, true, "Menu", 1.2f), KEY_CTRL_RIGHT(
				KeyEvent.CHAR_UNDEFINED, KeyEvent.VK_CONTROL, true, "Control", 1.5f);

		// KEY_DELETE('\u007F', KeyEvent.VK_DELETE, false, "Delete");

		private final boolean isModifier;
		private final char shiftKeyChar;
		private final char keyChar;
		private final int keyCode;
		private final String text;
		private final float keyWidthRatio;

		Keys(char keyChar, int keyCode, boolean isModifier) {
			this(keyChar, keyCode, isModifier, Character.toString(Character.toUpperCase(keyChar)));
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
			return "" + keyChar;
		}
	}

	private ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();

	public KeyboardZone() {
		this(null, true);
	}

	public KeyboardZone(boolean keysSentToApplet) {
		this(null, keysSentToApplet);
	}

	public KeyboardZone(int x, int y) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
	}

	public KeyboardZone(int x, int y, boolean keysSentToApplet) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, keysSentToApplet);
	}

	public KeyboardZone(String name) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
	}

	public KeyboardZone(String name, boolean keysSentToApplet) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, keysSentToApplet);
	}

	public KeyboardZone(int x, int y, int width, int height) {
		this(null, x, y, width, height, true);
	}

	public KeyboardZone(int x, int y, int width, int height, boolean keysSentToApplet) {
		this(null, x, y, width, height, keysSentToApplet);
	}

	public KeyboardZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, true);
	}

	public KeyboardZone(String name, int x, int y, int width, int height, boolean keysSentToApplet) {
		super(name, x, y, width, height);

		int KEYS_PER_ROW = 15;
		int DEFAULT_KEY_WIDTH = width / KEYS_PER_ROW;

		for (Keys k : Keys.values()) {
			this.add(new KeyZone(0, 0, (int) (k.keyWidthRatio * DEFAULT_KEY_WIDTH), this.height
					/ NUM_KEYBOARD_ROWS, k));
		}

		client.grid(0, 0, width, 0, 0, this.children.toArray(new Zone[children.size()]));

		for (Zone zone : this.children) {
			zone.setDirect(true);
		}

		if (keysSentToApplet) {
			// add the processing applet as a KeyListener by default
			this.addKeyListener(applet);
		}
	}

	@Override
	protected void init() {
		super.init();
		client.grid(0, 0, width, 0, 0, this.children.toArray(new Zone[children.size()]));
	}

	@Override
	public void drawImpl() {
		fill(0);
		rect(0, 0, width, height);

		// make sure modifiers have the correct setting as they act
		// differently than normal keys and should be unset even without a
		// touchUp event, although really just a hack, as touchUp should be
		// generated whenever a touch is unassigned from a zone
		boolean shiftDown = false;
		boolean altDown = false;
		boolean ctrlDown = false;
		boolean metaDown = false;
		for (Zone k : this.children) {
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
	 * This is a convenience method, which only adds Zone's as KeyListener
	 * 
	 * @param zone
	 *            The Zone to add to this keyboard as a KeyListener
	 */
	public void addZoneKeyListener(Zone zone) {
		this.addKeyListener(zone);
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
	 * This is a convenience method, which only removes a Zone from being a
	 * KeyListener on this keyboard
	 * 
	 * @param zone
	 *            The Zone to remove from being a KeyListener on this keyboard
	 */
	public void removeZoneKeyListener(Zone zone) {
		this.removeKeyListener(zone);
	}

	/**
	 * This removes a KeyListener from this keyboard
	 * 
	 * @param listener
	 *            The KeyListener to remove from the keyboard, usually a Zone,
	 *            which implements the KeyListener interface
	 */
	public void removeKeyListener(KeyListener listener) {
		this.keyListeners.remove(listener);
	}

	/**
	 * This clears all KeyListeners from this keyboard
	 */
	public void clearKeyListeners(KeyListener listener) {
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
}
