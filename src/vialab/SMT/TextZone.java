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
			if (blur) {
				textFont(font);
				textSize(16);
			}else{
				textSize(16);
			}
			width = (int) Math.ceil(textWidth(word));
			fill(255);
			if (blur) {
				textFont(sFont);
				if (selected) {
					textFont(font);
				}
				textSize(16);
				if (width > 0) {
					noStroke();
					rect(0, 0, width, height);
				}
				fill(0);
				text(this.word, 0, 0, width, height);
			}
			else {
				if (selected) {
					fill(0, 0, 255, 127);
				}
				if (width > 0) {
					noStroke();
					rect(0, 0, width, height);
				}
				fill(0);
				text(this.word, 0, 0, width, height);
			}
		}

	}

	private String text = "";

	private String inputText;

	private WordZone currentWordZone;

	private PFont font = applet.createFont("SansSerif", 16);

	private PFont sFont = applet.createFont("SansSerif", 3.0f);

	private boolean blur = false;

	public TextZone(int x, int y, int width, int height, String inputText, boolean blur) {
		super(x, y, width, height);
		this.currentWordZone = new WordZone(0, 0, 0, 20);
		this.inputText = inputText;
		this.blur = blur;
	}

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
		this.currentWordZone = new WordZone(0, 0, 0, 20);
		if (keysRecievedFromApplet) {
			applet.registerMethod("keyEvent", this);
		}

	}

	@Override
	public void drawImpl() {
		fill(255);
		noStroke();
		rect(0, 0, width, height);
		if (inputText != null && inputText.length() > text.length()) {
			addChar(inputText.charAt(text.length()));
		}
	}

	public void addChar(char c) {
		if (c == ' ') {
			this.currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
					currentWordZone.y, 0, 20);
			currentWordZone.word += " ";
		}
		else if (c == '\t') {
			this.currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
					currentWordZone.y, 0, 20);
			currentWordZone.word += "    ";
		}
		else if (c == '\n') {
			this.currentWordZone = new WordZone(0, currentWordZone.y + 20, 0, 20);
		}
		else {
			if (currentWordZone.word.trim().equals("")) {
				currentWordZone = new WordZone(currentWordZone.x + currentWordZone.width,
						currentWordZone.y, 0, 20);
			}
			if (currentWordZone.x + currentWordZone.width + 15 > width) {
				this.currentWordZone.setData(0, currentWordZone.y + 20, 0, 20);
			}
			this.currentWordZone.word += c;
		}
		if (inputText != null && inputText.length() > text.length()) {
			text += inputText.charAt(text.length());
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.addChar(e.getKeyChar());
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
