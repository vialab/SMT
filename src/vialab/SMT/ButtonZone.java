package vialab.SMT;

import processing.core.PFont;

/**
 * ButtonZone is a simple button, that when touched calls the
 * press[ButtonName](), or the pressImpl() function if ButtonZone is extended
 * with an overridden pressImpl()
 */
public class ButtonZone extends Zone {

	public boolean deactivated = false;

	protected int fontSize;

	protected String text;

	protected PFont font;

	protected int color = 200;

	protected int pressedColor = 150;

	protected int borderWeight = 1;

	protected int borderColor = 0;

	protected int textColor = 0;

	protected int pressedTextColor = 0;

	private float angle = 0;

	protected int deactivatedColor = 255;

	protected int deactivatedTextColor = 175;

	boolean warnPress() {
		return true;
	}

	boolean warnTouch() {
		return false;
	}

	boolean warnDraw() {
		return false;
	}

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
		this(name, 0, 0, 200, 100, text, 16, font);
	}

	public ButtonZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
	}

	public ButtonZone(int x, int y, int width, int height, String text) {
		this(null, x, y, width, height, text, 16);
	}

	public ButtonZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, null, 16, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text) {
		this(name, x, y, width, height, text, 16, null, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font) {
		this(name, x, y, width, height, text, 16, font, 0);
	}

	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font,
			float angle) {
		this(name, x, y, width, height, text, 16, font, 0);
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
	public void drawImpl() {
		if (deactivated) {
			drawImpl(deactivatedColor, deactivatedTextColor);
		}
		else {
			if (isButtonDown()) {
				drawImpl(pressedColor, pressedTextColor);
			}
			else {
				drawImpl(color, textColor);
			}
		}
	}

	/**
	 * @return Whether the button is currently pushed down
	 */
	public boolean isButtonDown() {
		return (this.getNumTouches() > 0);
	}

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
			text(text, width / 2 - borderWeight, height / 2 - borderWeight);
		}
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Override
	protected void pressInvoker() {
		if (!deactivated) {
			// only allow press if we are not deactivated
			super.pressInvoker();
		}
	}
}
