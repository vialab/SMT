package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.Dimension;

//smt imports
import vialab.SMT.*;

/**
 * A class designed to move it's parent. It does this by passing assigned
 * touches to it's parent, which then calls rst().
 */
public class AnchorZone extends Zone {
	//drawing fields
	private int cornerRounding_topLeft;
	private int cornerRounding_topRight;
	private int cornerRounding_bottomLeft;
	private int cornerRounding_bottomRight;

	//constructors
	/**
	 * Create a new AnchorZone with the default width and height of 40, and
	 * corner rounding of 5.
	 */
	public AnchorZone(){
		dimension = new Dimension( 40, 40);
		halfDimension = new Dimension(
			dimension.width / 2,
			dimension.height / 2);
		cornerRounding_topLeft = 5;
		cornerRounding_topRight = 5;
		cornerRounding_bottomLeft = 5;
		cornerRounding_bottomRight = 5;
	}
	/**
	 * Draws the AnchorZone
	 */
	@Override
	public void drawImpl() {
		fill( 0, 0, 0, 150);
		strokeWeight(4);
		stroke( 0, 0, 0, 200);
		rect( 0, 0, dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomLeft, cornerRounding_bottomRight);
	}
	/**
	 * Draws the picking area of AnchorZone
	 */
	@Override
	public void pickDrawImpl() {
		rect(0, 0, dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomLeft, cornerRounding_bottomRight);
	}
	/**
	 * Does nothing. Touches should never be assigned to this zone, so it should
	 * never be called
	 */
	@Override
	public void touchImpl() {}
	/**
	 * Passes all assigned touches to the zone's parent, so that the parent can
	 * use rst() on them.
	 * @param touches
	 */
	@Override
	public void assign(Iterable<? extends Touch> touches) {
		Zone parent = this.getParent();
		if( parent != null)
			parent.assign( touches);
		else
			super.assign( touches);
	}
	/**
	 * Sets the rounding of each corner uniformly.
	 * @param rounding the desired amount of rounding
	 */
	public void setCornerRounding( int rounding){
		cornerRounding_topLeft = rounding;
		cornerRounding_topRight = rounding;
		cornerRounding_bottomLeft = rounding;
		cornerRounding_bottomRight = rounding;
	}
	/**
	 * Sets the rounding of each corner non-uniformly.
	 * @param topLeft the desired amount of rounding for the top left corner
	 * @param topRight the desired amount of rounding for the top right corner
	 * @param bottomLeft the desired amount of rounding for the bottom left corner
	 * @param bottomRight the desired amount of rounding for the bottom right corner
	 */
	public void setCornerRounding(
			int topLeft, int topRight, int bottomLeft, int bottomRight){
		cornerRounding_topLeft = topLeft;
		cornerRounding_topRight = topRight;
		cornerRounding_bottomLeft = bottomLeft;
		cornerRounding_bottomRight = bottomRight;
	}
}