package vialab.SMT;

import processing.core.PVector;

/**
 * SliderZone provides a simple GUI Slider that is designed for touch.
 * 
 * @author Zach
 */
public class SliderZone extends Zone {

	private int currentValue;

	private int minValue;

	private int maxValue;

	private String label;

	private int majorTickSpacing;

	private int minorTickSpacing;

	boolean warnDraw(){
		return false;
	}

	boolean warnTouch(){
		return false;
	}

    /**
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param minValue  - int: Minimum value of the slider
	 * @param maxValue  - int: Maximum value of the slider
	 */
	public SliderZone(int x, int y, int width, int height, int minValue, int maxValue){
		this(null, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, null);
	}

     /**
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param minValue  - int: Minimum value of the slider
	 * @param maxValue  - int: Maximum value of the slider
	 * @param label   - String: Text label of the zone
	 */
	public SliderZone(int x, int y, int width, int height, int minValue, int maxValue, String label){
		this(null, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, label);
	}

     /**
	 * @param name    - String: The name of the zone
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param minValue  - int: Minimum value of the slider
	 * @param maxValue  - int: Maximum value of the slider
	 */
	public SliderZone(String name, int x, int y, int width, int height, int minValue, int maxValue){
		this(name, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, null);
	}

     /**
	 * @param name    - String: The name of the zone
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param minValue  - int: Minimum value of the slider
	 * @param maxValue  - int: Maximum value of the slider
	 * @param label   - String: Text label of the zone
	 */
	public SliderZone(String name, int x, int y, int width, int height, int minValue, int maxValue,
			String label){
		this(name, x, y, width, height, (maxValue - minValue) / 2, minValue, maxValue, 5, 25, label);
	}

	 /**
	 * @param name    - String: The name of the zone
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param currentValue - int: Current value of the slider
	 * @param minValue  - int: Minimum value of the slider
	 * @param maxValue  - int: Maximum value of the slider
	 * @param minorTickSpacing - int: Spacing between the minor ticks of the slider
	 * @param majorTickSpacing - int: Spacing between the major ticks of the slider
	 * @param label   - String: Text label of the zone
	 */
	public SliderZone(String name, int x, int y, int width, int height, int currentValue,
			int minValue, int maxValue, int minorTickSpacing, int majorTickSpacing, String label){
		super(name, x, y, width, height);
		this.currentValue = currentValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.majorTickSpacing = majorTickSpacing;
		this.minorTickSpacing = minorTickSpacing;
		this.label = label;
	}

	/**
	 * Get the current value of the slider
	 * @return the current value of the slider
	 */
	public int getCurrentValue(){
		return currentValue;
	}

	/**
	 * Set the value of the slider
	 * @param currentValue the desired value of the slider
	 */
	public void setCurrentValue(int currentValue){
		this.currentValue = currentValue;
	}

	/**
	 * [getMinValue description]
	 * @return [description]
	 */
	public int getMinValue(){
		return minValue;
	}

	/**
	 * [setMinValue description]
	 * @param minValue [description]
	 */
	public void setMinValue(int minValue){
		this.minValue = minValue;
	}

	/**
	 * [getMaxValue description]
	 * @return [description]
	 */
	public int getMaxValue(){
		return maxValue;
	}

	/**
	 * [setMaxValue description]
	 * @param maxValue [description]
	 */
	public void setMaxValue(int maxValue){
		this.maxValue = maxValue;
	}

	/**
	 * [getLabel description]
	 * @return [description]
	 */
	public String getLabel(){
		return label;
	}

	/**
	 * [setLabel description]
	 * @param label [description]
	 */
	public void setLabel(String label){
		this.label = label;
	}

	/**
	 * [getMajorTickSpacing description]
	 * @return [description]
	 */
	public int getMajorTickSpacing(){
		return majorTickSpacing;
	}

	/**
	 * [setMajorTickSpacing description]
	 * @param majorTickSpacing [description]
	 */
	public void setMajorTickSpacing(int majorTickSpacing){
		this.majorTickSpacing = majorTickSpacing;
	}

	/**
	 * [getMinorTickSpacing description]
	 * @return [description]
	 */
	public int getMinorTickSpacing(){
		return minorTickSpacing;
	}

	/**
	 * [setMinorTickSpacing description]
	 * @param minorTickSpacing [description]
	 */
	public void setMinorTickSpacing(int minorTickSpacing){
		this.minorTickSpacing = minorTickSpacing;
	}

	@Override
	protected void touchImpl(){
		for (Touch t : getTouches()){
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
	protected void drawImpl(){
		fill(255);
		rect(0, 0, width, height);

		drawKnob();

		drawMinMaxCur();

		drawTicks();
	}

	protected void drawMinMaxCur(){
		fill(0);
		textAlign(CENTER);
		text(currentValue, width / 2, height*3/10);
		text(minValue, width/10, height*9/10);
		text(maxValue, width*9/10, height*9/10);
	}

	protected void drawTicks(){
		// draw the ticks
		for (int i = minValue; i <= maxValue; i++){
			if (i % majorTickSpacing == 0){
				// draw major tick
				line(((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10), height * 3 / 8,
						((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10),
						height * 5 / 8);
			}
			else if (i % minorTickSpacing == 0){
				// draw minor tick
				line(((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10), height * 3 / 8,
						((i / (float) maxValue) * width * (float) 8 / 10) + (width / 10),
						height / 2);
			}
		}
	}

	protected void drawKnob(){
		// draw something for the slider knob
		fill(150,100);
		rect((((currentValue - minValue) / (float) maxValue) * width * (float) 8 / 10)
				+ (width / 10) - (width / 20) / 2, 0, width / 20, height);
	}
}
