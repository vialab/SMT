package vialab.SMT;

import java.awt.event.KeyEvent;

import processing.core.PFont;

/**
 * TextZone displays text, and is selectable by touch, each word is
 * independently highlighted by touch, toggled whenever a TouchDown occurs on
 * the word
 */
public class TextZone extends Zone {

	private class WordZone extends Zone {

		public String word;
		private boolean selected = false;

		public WordZone(int x, int y, int width, int height) {
			super(x, y, width, height);
			this.word = "";
			TextZone.this.add(this);
			this.setDirect(true);
		}

		@Override
		public void touchDown(Touch touch) {
			selected = !selected;
			super.touchDown(touch);
		}

		@Override
		public void drawImpl() {
			fill(255);
			if (selected) {
				fill(0, 0, 255, 127);
			}
			if (width > 0) {
				noStroke();
				rect(0, 0, width, height);
			}
			fill(0);
			text(this.word, 0, this.getParent().isDirect() ? 0 : 5, width, height);
		}

	}

	public String text = "";

	private WordZone currentWordZone;

	private PFont font = applet.createFont("Arial", 16);

	public TextZone(int x, int y, int width, int height) {
		this(null, x, y, width, height, false);
	}

	public TextZone(int x, int y, int width, int height, boolean keysRecievedFromApplet) {
		this(null, x, y, width, height, keysRecievedFromApplet);
	}

	public TextZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, false);
	}

	public TextZone(String name, int x, int y, int width, int height, boolean keysRecievedFromApplet) {
		super(name, x, y, width, height);
		this.currentWordZone = new WordZone(0, 0, 1, 20);
		if (keysRecievedFromApplet) {
			applet.registerMethod("keyEvent", this);
		}

	}

	@Override
	public void drawImpl() {
		fill(255);
		noStroke();
		rect(0, 0, width, height);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		textFont(font);
		if (e.getKeyChar() == ' ') {
			this.currentWordZone = new WordZone(currentWordZone.x
					+ (int) textWidth(currentWordZone.word + e.getKeyChar()), currentWordZone.y, 0,
					20);
		}
		else if (e.getKeyChar() == '\t') {
			this.currentWordZone = new WordZone(currentWordZone.x
					+ (int) textWidth(currentWordZone.word + "    "), currentWordZone.y, 1, 20);
		}
		else if (e.getKeyChar() == '\n') {
			this.currentWordZone = new WordZone(0, currentWordZone.y + 20, 1, 20);
		}
		else {
			this.currentWordZone.word += e.getKeyChar();
			this.currentWordZone.setData(currentWordZone.x, currentWordZone.y,
					Math.max((int) textWidth(currentWordZone.word), 1), currentWordZone.height);
		}
		super.keyTyped(e);
	}

	/**
	 * This method is for use by Processing, override it to change what occurs
	 * when a Processing KeyEvent is passed to the TextZone
	 * 
	 * @param event
	 *            The Processing KeyEvent that the textZone will use to change
	 *            its state
	 */
	public void keyEvent(processing.event.KeyEvent event) {
		KeyEvent nevent = (KeyEvent) event.getNative();
		switch (nevent.getID()) {
		case KeyEvent.KEY_RELEASED:
			keyReleased(nevent);
			break;
		case KeyEvent.KEY_TYPED:
			keyTyped(nevent);
			break;
		case KeyEvent.KEY_PRESSED:
			keyPressed(nevent);
			break;
		}
	}
}
