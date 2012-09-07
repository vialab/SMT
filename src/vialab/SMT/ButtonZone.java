package vialab.SMT;

import java.lang.reflect.Method;

import processing.core.PFont;

public class ButtonZone extends Zone {

	private int fontSize;

	private String text;

	private PFont font;

	private float cornerRadius = 12;

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
		this(name, 0, 0, 200, 100, text, 12, font);
	}

	public ButtonZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
	}

	public ButtonZone(int x, int y, int width, int height, String text) {
		this(null, x, y, width, height, text, 12);
	}

	public ButtonZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, null, 12, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text) {
		this(name, x, y, width, height, text, 12, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font) {
		this(name, x, y, width, height, text, 12, font, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font,
			float angle) {
		this(name, x, y, width, height, text, 12, font, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize) {
		this(name, x, y, width, height, text, fontSize, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize,
			PFont font) {
		this(name, x, y, width, height, text, fontSize, font, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize,
			PFont font, float angle) {
		super(name, x, y, width, height);
		this.text = text;
		this.fontSize = fontSize;
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
			Zone picked = client.picker.pick(t);
			if (picked!=null && picked.equals(this)) {
				return true;
			}
		}
		// if none of the touches assigned are on the button, remove all of them
		unassignAll();
		return false;
	}

	private void drawImpl(int buttonColor, int textColor) {
		super.beginDraw();

		stroke(borderColor);
		strokeWeight(borderWeight);
		fill(buttonColor);
		rect(borderWeight, borderWeight, width - 2 * borderWeight, height - 2 * borderWeight,
				cornerRadius);

		if (text != null) {
			if (font != null) {
				textFont(font);
			}
			textAlign(CENTER, CENTER);
			textSize(fontSize);
			fill(textColor);
			text(text, width / 2 - borderWeight, height / 2 - borderWeight);
		}
		super.endDraw();

		super.draw();
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
			pressMethod = SMTUtilities.getZoneMethod(applet, "press", name, this.getClass(), true);
		}
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

}
