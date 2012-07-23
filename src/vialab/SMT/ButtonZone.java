package vialab.SMT;

import java.lang.reflect.Method;

import processing.core.PFont;

public class ButtonZone extends Zone {

	private String text;

	private PFont font;

	private float cornerRadius = 25;

	private int color = applet.color(200);

	private int pressedColor = applet.color(255, 100, 100);

	private int borderWeight = 1;

	private int borderColor = applet.color(0);

	private int textColor = applet.color(0);

	private int pressedTextColor = applet.color(0);

	private float angle = 0;

	private Method pressMethod;

	public ButtonZone() {
		this(null);
	}

	public ButtonZone(String name) {
		this(name, null);
	}

	public ButtonZone(String name, String text) {
		this(name, text, null);
	}

	public ButtonZone(String name, String text, PFont font) {
		this(name, 0, 0, 200, 100, text, font);
	}

	public ButtonZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
	}

	public ButtonZone(int x, int y, int width, int height, String text) {
		this(null, x, y, width, height, text);
	}

	public ButtonZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, null, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text) {
		this(name, x, y, width, height, text, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font) {
		this(name, x, y, width, height, text, font, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font,
			float angle) {
		super(name, x, y, width, height);
		this.text = text;
		this.font = font;
		setAngle(angle);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PFont getFont() {
		return font;
	}

	public void setFont(PFont font) {
		this.font = font;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;

		beginTouch();
		rotateAbout(angle, CENTER);
		endTouch();
	}

	@Override
	public void draw() {
		if (isButtonDown()) {
			drawImpl(pressedColor, pressedTextColor);
		}
		else {
			drawImpl(color, textColor);
		}
	}

	public boolean isButtonDown() {
		for (Touch t : getTouches()) {
			if (contains(t.x, t.y)) {
				return true;
			}
		}
		//if none of the touches assigned are on the button, remove all of them
		unassignAll();
		return false;
	}

	private void drawImpl(int buttonColor, int textColor) {
		super.beginDraw();
		smooth();

		noFill();
		stroke(borderColor);
		strokeWeight(borderWeight);
		smooth();
		roundRect(borderWeight, borderWeight, width - 2 * borderWeight, height - 2 * borderWeight,
				cornerRadius);

		fill(buttonColor);
		roundRect(borderWeight, borderWeight, width - 2 * borderWeight, height - 2 * borderWeight,
				cornerRadius);

		if (text != null) {
			if (font != null) {
				textFont(font);
			}
			textAlign(CENTER, CENTER);

			fill(textColor);
			text(text, width / 2 - borderWeight, height / 2 - borderWeight);
		}
		super.endDraw();

		super.draw();
	}

	private void roundRect(float x, float y, float w, float h, float r) {
		ellipseMode(CORNER);

		float ax, ay, hr;

		ax = x + w - r;
		ay = y + h - r;
		hr = r / 2;

		beginShape();
		vertex(x + hr, y);
		vertex(x + w - hr, y);
		vertex(x + w, y + hr);
		vertex(x + w, y + h - hr);
		vertex(x + w - hr, y + h);
		vertex(x + hr, y + h);
		vertex(x, y + h - hr);
		vertex(x, y + hr);
		endShape(CLOSE);

		arc(x, y, r, r, PI, TWO_PI - HALF_PI);
		arc(ax, y, r, r, 3 * HALF_PI, TWO_PI);
		arc(x, ay, r, r, HALF_PI, PI);
		arc(ax, ay, r, r, 0, HALF_PI);

	}

	@Override
	public void touchUp(Touch touch) {
		if (isButtonDown()) {
			SMTUtilities.invoke(pressMethod, applet, this);
		}

		super.touchUp(touch);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		if (name != null) {
			pressMethod = SMTUtilities.getZoneMethod(applet, "press", name, this.getClass());
		}
	}

}
