package vialab.SMT;

import java.awt.event.KeyEvent;

public class TextZone extends Zone {

	public String text = "";

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
		if (keysRecievedFromApplet) {
			applet.registerKeyEvent(this);
		}
	}

	@Override
	public void draw() {
		super.beginDraw();
		fill(255);
		rect(0, 0, width, height);
		fill(0);
		text(text, 10, 10, width, height);
		super.endDraw();
		super.draw();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.print(e.getKeyChar());
		this.text += e.getKeyChar();
		super.keyTyped(e);
	}

	public void keyEvent(KeyEvent event) {
		switch (event.getID()) {
		case KeyEvent.KEY_RELEASED:
			keyReleased(event);
			break;
		case KeyEvent.KEY_TYPED:
			keyTyped(event);
			break;
		case KeyEvent.KEY_PRESSED:
			keyPressed(event);
			break;
		}
	}
}
