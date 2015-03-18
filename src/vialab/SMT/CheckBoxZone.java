package vialab.SMT;


/**
 *  Creates a check box and will display a check mark
 *  within it depending on the value given to the 
 *  setChecked() function.
 *
 */
public class CheckBoxZone extends Zone {

	public boolean checked = false;
	
	/**
	 * @param name	 - String: The name of the zone
	 * @param x		- int: X-coordinate of the upper left corner of the zone
	 * @param y		- int: Y-coordinate of the upper left corner of the zone
	 * @param width	- int: Width of the zone
	 * @param height   - int: Height of the zone
	 *
	 */
	public CheckBoxZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}

	/**
	 * Sets the state of the check mark. Passing true
	 * into this function displays a check mark.
	 *
	 * @param checked   - boolean: State of the check mark
	 */
	public void setChecked(boolean checked){
		this.checked = checked;
	}

	protected void drawImpl() {
		fill(255);
		rect(0, 0, width, height, 10);
		if (checked) {
			drawCheckmark();
		}
	}

	protected void drawCheckmark() {
		stroke(0);
		strokeWeight(5);
		line(width, 0, width / 3, height);
		line(width / 3, height, 0, (float) (height * 2. / 3.));
	}

	@Override
	protected void touchUpImpl(Touch t) {
		checked = !checked;
	}
}