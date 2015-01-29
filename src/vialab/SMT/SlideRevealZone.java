package vialab.SMT;

/**
 * 
 * SlideRevealZone will slide to the right to reveal the text it was given in
 * its constructor, when a swipe/slide gesture is performed on it.
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
			//hSwipe(0, getParent().width);
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
	 * @param x   - int: X-coordinate of the upper left corner of the zone
	 * @param y   - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param text  - String: The text displayed in the zone
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
		fill(0);
		text(revealText, 5, 20, width, height);
	}

	protected void drawCoverRect() {
		rectMode(CORNERS);
		fill(255);
		float childxpos = toZoneVector(getChild(0).getOrigin()).x;
		rect(childxpos, 0, width, height);
		fill(0);
		text("Slide To Right", childxpos + getChild(0).width * 1.1f, height*3/8, width, height);
	}

	/**
	 * The current x position of the slider
	 * @return the current x position of the slider
	 */
	public int getCurrentX() {
		return xPosSlider;
	}

	/**
	 * Returns true if the slider has been moved
	 * @return whether the slider has been moved
	 */
	public boolean hasStarted() {
		return (xPosSlider > 0);
	}

	/**
	 * Returns true if the slider has moved all the way
	 * @return whether the slider has finished moving
	 */
	public boolean hasFinished() {
		return (xPosSlider >= (width - height));
	}

	/**
	 * Returns the maximum length of the slider
	 * @return the maximum length of the slider
	 */
	public int getMaxX() {
		return width - height;
	}

	/**
	 * Returns the minimum length of the slider
	 * @return the minimum length of the slider
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
