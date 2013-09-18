package vialab.SMT.zone;

//standard library imports
import java.awt.Dimension;

//smt imports
import vialab.SMT.*;

public class AnchorZone extends Zone {
	//drawing fields
	private int cornerRounding_topLeft;
	private int cornerRounding_topRight;
	private int cornerRounding_bottomLeft;
	private int cornerRounding_bottomRight;
	//constructor
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
	@Override
	public void drawImpl() {
		fill( 0, 0, 0, 150);
		strokeWeight(4);
		stroke( 0, 0, 0, 200);
		rect( 0, 0, dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomLeft, cornerRounding_bottomRight);
	}
	@Override
	public void pickDrawImpl() {
		rect(0, 0, dimension.width, dimension.height,
			cornerRounding_topLeft, cornerRounding_topRight,
			cornerRounding_bottomLeft, cornerRounding_bottomRight);
	}
	@Override
	public void touchImpl() {}
	@Override
	public void assign(Iterable<? extends Touch> touches) {
		Zone parent = this.getParent();
		if( parent != null)
			parent.assign( touches);
		else
			super.assign( touches);
	}
	public void setCornerRounding( int rounding){
		cornerRounding_topLeft = rounding;
		cornerRounding_topRight = rounding;
		cornerRounding_bottomLeft = rounding;
		cornerRounding_bottomRight = rounding;
	}
	public void setCornerRounding(
			int topLeft, int topRight, int bottomLeft, int bottomRight){
		cornerRounding_topLeft = topLeft;
		cornerRounding_topRight = topRight;
		cornerRounding_bottomLeft = bottomLeft;
		cornerRounding_bottomRight = bottomRight;
	}
}