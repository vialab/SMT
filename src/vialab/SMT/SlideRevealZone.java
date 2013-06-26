package vialab.SMT;

/**
 * 
 * SlideRevealZone will slide to the right to reveal the text it was given in
 * its constructor
 * 
 */
public class SlideRevealZone extends Zone {
	private String revealText;
	private int xPosSlider = 0;

	private class Slider extends Zone {
		Slider(int x, int y, int width, int height) {
			super(x, y, width, height);
		}

		protected void touchImpl() {
			hSwipe(0, getParent().width);
			xPosSlider = (int) (getParent().toZoneVector(getOrigin()).x);
		}

		protected void drawImpl() {
			fill(125);
			rect(0, 0, width, height);
			if (hasFinished()) {
				fill(255);
			}
			else {
				fill(255, 0, 0);
			}
			triangle(0, 0, 0, height, width, height / 2);
		}
	}

	/**
	 * @param x
	 *            - int: X-coordinate of the upper left corner of the zone
	 * @param y
	 *            - int: Y-coordinate of the upper left corner of the zone
	 * @param width
	 *            - int: Width of the zone
	 * @param height
	 *            - int: Height of the zone
	 * @param text
	 *            - String: The text displayed in the zone
	 */
	public SlideRevealZone(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		add(new Slider(0, 0, height, height));
		revealText = text;
	}

	protected void touchImpl() {
	}

	protected void drawImpl() {
		fill(255);
		rect(0, 0, width, height);

		drawHiddenText();

		drawCoverRect();
	}

	protected void drawHiddenText() {
		textSize(12);
		fill(0);
		text(revealText, 5, 20, width, height);
	}

	protected void drawCoverRect() {
		textSize(12);
		fill(255);
		float childxpos = toZoneVector(getChild(0).getOrigin()).x;
		rect(childxpos, 0, width - childxpos, height);
		fill(0);
		int txtSize = (int) textWidth("Slide To Right");
		if (childxpos + txtSize + getChild(0).width < width) {
			text("Slide To Right", childxpos + txtSize, 20, width - childxpos - txtSize
					- getChild(0).width, 20);
		}
	}

	/**
	 * The current x position of the slider
	 */
	public int getCurrentX() {
		return xPosSlider;
	}

	/**
	 * Returns true if the slider has been moved
	 */
	public boolean hasStarted() {
		return (xPosSlider > 0);
	}

	/**
	 * Returns true if the slider has moved all the way
	 */
	public boolean hasFinished() {
		return (xPosSlider >= (width - height));
	}

	/**
	 * Returns the maximum length of the slider
	 */
	public int getMaxX() {
		return width - height;
	}

	/**
	 * Returns the minimum length of the slider
	 */
	public int getMinX() {
		return 0;
	}

	/**
	 * Resets the zone
	 */
	public void reset() {
		getChild(0).setLocation(0, 0);
		xPosSlider = 0;
	}
}
