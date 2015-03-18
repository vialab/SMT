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
		
	/**
	 *
	 * @param name      - String: Name of the Zone
	 */
	public ButtonZone(String name) {
		this(name, null);
	}

	/**
	 *
	 * @param name      - String: Name of the Zone
	 * @param text      - String: Text displayed within the zone
	 */
	public ButtonZone(String name, String text) {
		this(name, text, null);
	}

	/**
	 *
	 * @param name      - String: Name of the Zone
	 * @param text      - String: Text displayed within the zone
	 * @param font      - PFont: The font used to display the text
	 */
	public ButtonZone(String name, String text, PFont font) {
		this(name, 0, 0, 200, 100, text, 16, font);
	}

	/**
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 */
	public ButtonZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
	}

	/**
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 */
	public ButtonZone(int x, int y, int width, int height, String text) {
		this(null, x, y, width, height, text, 16);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 */
	public ButtonZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, null, 16, null, 0);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text) {
		this(name, x, y, width, height, text, 16, null, 0);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 * @param font      - PFont: The font used to display the text
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font) {
		this(name, x, y, width, height, text, 16, font, 0);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 * @param font      - PFont: The font used to display the text
	 * @param angle     - float: Angle of the button zone
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text, PFont font,
			float angle) {
		this(name, x, y, width, height, text, 16, font, angle);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 * @param fontSize  - int: Size of the font
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize) {
		this(name, x, y, width, height, text, fontSize, null, 0);
	}

	/**
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 * @param fontSize  - int: Size of the font
	 * @param font      - PFont: The font used to display the text
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize,
			PFont font) {
		this(name, x, y, width, height, text, fontSize, font, 0);
	}

	/**
	 *
	 * @param name      - String: Name of the Zone
	 * @param x         - int: X-coordinate of the upper left corner of the zone
	 * @param y         - int: Y-coordinate of the upper left corner of the zone
	 * @param width     - int: Width of the zone
	 * @param height    - int: Height of the zone
	 * @param text      - String: Text displayed within the zone
	 * @param fontSize  - int: Size of the font
	 * @param font      - PFont: The font used to display the text
	 * @param angle     - float: Angle of the button zone
	 */
	public ButtonZone(String name, int x, int y, int width, int height, String text, int fontSize,
			PFont font, float angle) {
		super(name, x, y, width, height);
		this.text = text;
		this.fontSize = fontSize;
		this.font = font;
		if(this.font == null){
			this.font = applet.createFont("Lucida Sans", fontSize);
		}
		setAngle(angle);
	}

	/**
	 * Gets the zone's text.
	 * @return the text of the button zone
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the zone's text
	 * @param text      - String: The zone's text for displaying.
	 */
	public void setText(String text) {
		this.text = text;
	}
		
	/**
	 * Gets the zone's font
	 * @return this button's font
	 */
	public PFont getFont() {
		return font;
	}
		
	/**
	 * Sets the zone's font
	 * @param font      - PFont: The font used to display the zone's text.
	 */
	public void setFont(PFont font) {
		this.font = font;
	}

	/**
	 * Gets the angle used to display the zone's text.
	 * @return the angle of this zone's text
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * Sets the zone's angle that is used to display the zone's text.
	 * @param angle      - float: The angle
	 */
	public void setAngle(float angle) {
		this.angle = angle;
/*
		beginTouch();
		rotateAbout(angle, CENTER);
		endTouch();*/
	}

	/**
	 * Used to override what is drawn in the zone.
	 */
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
	 * Used to determine if the button is currently being pushed down.
	 * Returns true if it is.
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
				textFont(font, fontSize);
			}
			textAlign(CENTER, CENTER);
			textSize(fontSize);
			fill(textColor);
			text(text, width / 2 - borderWeight, height / 2 - borderWeight);
		}
	}

	/**
	 * Gets the font size that is used to display the zone's text
	 * @return this zone's font size
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the font size that is used to display the zone's text
	 * @param fontSize      - int: The font size
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Override
	protected void invokePressMethod( Touch touch) {
		if (!deactivated) {
			// only allow press if we are not deactivated
			super.invokePressMethod( touch);
		}
	}
}
