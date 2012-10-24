package vialab.SMT;

import processing.core.PFont;
import processing.core.PVector;

/**
 * SliderZone provides a simple GUI Slider that is designed for touch
 * 
 * @author Zach
 */
public class SliderZone extends Zone {
	
	private PFont font = applet.createFont("Arial", 16);

	private int currentValue;

	private int minValue;

	private int maxValue;

	private String label;

	private int majorTickSpacing;

	private int minorTickSpacing;

	public SliderZone(int x, int y, int width, int height, int minValue, int maxValue) {
		this(null, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, null);
	}

	public SliderZone(int x, int y, int width, int height, int minValue, int maxValue, String label) {
		this(null, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, label);
	}

	public SliderZone(String name, int x, int y, int width, int height, int minValue, int maxValue) {
		this(name, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, null);
	}

	public SliderZone(String name, int x, int y, int width, int height, int minValue, int maxValue,
			String label) {
		this(name, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, label);
	}

	/**
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param currentValue
	 * @param minValue
	 * @param maxValue
	 * @param majorTickSpacing
	 * @param minorTickSpacing
	 * @param label
	 */
	public SliderZone(String name, int x, int y, int width, int height, int currentValue,
			int minValue, int maxValue, int minorTickSpacing, int majorTickSpacing, String label) {
		super(name, x, y, width, height);
		this.currentValue = currentValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.majorTickSpacing = majorTickSpacing;
		this.minorTickSpacing = minorTickSpacing;
		this.label = label;
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getMajorTickSpacing() {
		return majorTickSpacing;
	}

	public void setMajorTickSpacing(int majorTickSpacing) {
		this.majorTickSpacing = majorTickSpacing;
	}

	public int getMinorTickSpacing() {
		return minorTickSpacing;
	}

	public void setMinorTickSpacing(int minorTickSpacing) {
		this.minorTickSpacing = minorTickSpacing;
	}

	private void moveKnob() {
		for (Touch t : getTouches()) {
			// if(this.contains(t.x, t.y)){
			PVector touchInZone = this.toZoneVector(new PVector(t.x, t.y));
			this.currentValue = Math
					.min((int) (minValue + (maxValue - minValue)
							* (Math.max(touchInZone.x - (width / 10), 0) / (width * (((float) 8) / 10)))),
							maxValue);
			// }
		}

	}

	@Override
	protected void drawImpl() {
		textFont(font);
		moveKnob();
		fill(255);
		rect(0, 0, width, height);

		// draw something for the slider knob
		ellipseMode(CORNER);
		ellipse((((currentValue - minValue) / (float) maxValue) * width * (float) 8 / 10)
				+ (width / 10) - (width / 20) / 2, 0, width / 20, height);
		fill(0);
		text(currentValue, width/2-10, 15);
		text(minValue, 0, height);
		text(maxValue, width - 30, height);
		fill(255);
		// draw the ticks
		for (int i = minValue; i <= maxValue; i++) {
			if (i % majorTickSpacing == 0) {
				// draw major tick
				line(((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10), height / 2,
						((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10),
						height * 3 / 4);
			}
			else if (i % minorTickSpacing == 0) {
				// draw minor tick
				line(((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10), height / 2,
						((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10),
						height * 5 / 8);
			}
		}
	}
}
