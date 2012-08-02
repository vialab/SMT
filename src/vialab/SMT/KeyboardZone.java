/**
 * 
 */
package vialab.SMT;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * @author Zach
 * 
 */
public class KeyboardZone extends Zone {

	private int MODIFIERS = 0;

	private Component keyboardComponent = new Component() {
		private static final long serialVersionUID = -3237916182106172342L;
	};

	private class KeyZone extends ButtonZone {
		private Keys key;

		public KeyZone(int x, int y, int width, int height, Keys key) {
			super(x, y, width, height, Character.toString(key.keyChar).toUpperCase());
			this.key = key;
		}

		@Override
		public void touchDown(Touch touch) {
			if (isButtonDown()) {
				// send key press using KeyEvent to listeners
				for (KeyListener l : keyListeners) {
					l.keyPressed(new KeyEvent(keyboardComponent, KeyEvent.KEY_PRESSED, System
							.currentTimeMillis(), MODIFIERS, key.keyCode, key.keyChar));
				}
			}

			super.touchDown(touch);
		}

		@Override
		public void touchUp(Touch touch) {
			if (isButtonDown()) {
				// send key release and typed using KeyEvent to listeners
				for (KeyListener l : keyListeners) {
					l.keyReleased(new KeyEvent(keyboardComponent, KeyEvent.KEY_RELEASED, System
							.currentTimeMillis(), MODIFIERS, key.keyCode, key.keyChar));
					if (true) {
						l.keyTyped(new KeyEvent(keyboardComponent, KeyEvent.KEY_TYPED, System
								.currentTimeMillis(), MODIFIERS, KeyEvent.VK_UNDEFINED, key.keyChar));
					}
				}
			}

			super.touchUp(touch);
		}
	}

	private static final int DEFAULT_HEIGHT = 200;
	private static final int DEFAULT_WIDTH = 500;

	private enum Keys {
		KY_1('1', KeyEvent.VK_0), KY_2('2', KeyEvent.VK_0), KY_3('3', KeyEvent.VK_0), KY_4('4',
				KeyEvent.VK_0), KY_5('5', KeyEvent.VK_0), KY_6('6', KeyEvent.VK_0), KY_7('7',
				KeyEvent.VK_0), KY_8('8', KeyEvent.VK_0), KY_9('9', KeyEvent.VK_0), KY_0('0',
				KeyEvent.VK_0), KY_Q('Q', KeyEvent.VK_0), KY_W('W', KeyEvent.VK_0), KY_E('E',
				KeyEvent.VK_0), KY_R('R', KeyEvent.VK_0), KY_T('T', KeyEvent.VK_0), KY_Y('Y',
				KeyEvent.VK_0), KY_U('U', KeyEvent.VK_0), KY_I('I', KeyEvent.VK_0), KY_O('O',
				KeyEvent.VK_0), KY_P('P', KeyEvent.VK_0), KY_A('A', KeyEvent.VK_0), KY_S('S',
				KeyEvent.VK_0), KY_D('D', KeyEvent.VK_0), KY_F('F', KeyEvent.VK_0), KY_G('G',
				KeyEvent.VK_0), KY_H('H', KeyEvent.VK_0), KY_J('J', KeyEvent.VK_0), KY_K('K',
				KeyEvent.VK_0), KY_L('L', KeyEvent.VK_0), KY_Z('Z', KeyEvent.VK_0), KY_X('X',
				KeyEvent.VK_0), KY_C('C', KeyEvent.VK_0), KY_V('V', KeyEvent.VK_0), KY_B('B',
				KeyEvent.VK_0), KY_N('N', KeyEvent.VK_0), KY_M('M', KeyEvent.VK_0);

		private final char keyChar;
		private final int keyCode;

		Keys(char s, int code) {
			keyChar = s;
			keyCode = code;
		}

		public String toString() {
			return "" + keyChar;
		}
	}

	private ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();

	public KeyboardZone() {
		this(null);
	}

	public KeyboardZone(int x, int y) {
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public KeyboardZone(String name) {
		this(name, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public KeyboardZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
	}

	public KeyboardZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);

		for (int i = 0; i < 10; i++) {
			this.add(new KeyZone(i * 50, 0, 50, 50, Keys.values()[i]));
		}
		for (int i = 0; i < 10; i++) {
			this.add(new KeyZone(i * 50, 50, 50, 50, Keys.values()[10 + i]));
		}
		for (int i = 0; i < 9; i++) {
			this.add(new KeyZone(i * 50, 100, 50, 50, Keys.values()[20 + i]));
		}
		for (int i = 0; i < 7; i++) {
			this.add(new KeyZone(i * 50, 150, 50, 50, Keys.values()[29 + i]));
		}
		
		/*for(Zone zone : this.children){
			zone.setDirect(true);
		}*/
		
		//add the processing applet as a KeyListener by default
		this.addKeyListener(applet);
	}

	public void draw() {
		super.beginDraw();
		fill(0);
		rect(0, 0, width, height);
		super.endDraw();
		super.draw();
	}

	public void addKeyListener(KeyListener l) {
		this.keyListeners.add(l);
	}
}
