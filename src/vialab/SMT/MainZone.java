package vialab.SMT;

/**
 * The main zone of smt. All zones are children.
 */
public class MainZone extends Zone{
	/**
	 * Create a new mainzone
	 * 
	 * @param  x the x coordinate
	 * @param  y the y coordinate
	 * @param  w the width of the zone
	 * @param  h the height of the zone
	 */
	public MainZone( int x, int y, int w, int h){
		super( x, y, w, h);
		this.setDirect(true);
	}
	/**
	 * [drawImpl description]
	 */
	public void drawImpl(){}
	/**
	 * [drawImpl description]
	 */
	public void pickDrawImpl(){}
	/**
	 * [drawImpl description]
	 */
	public void touchImpl(){}
}