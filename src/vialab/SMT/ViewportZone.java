package vialab.SMT;

//processing imports
import processing.core.*;

/**
 * A zone that acts like a viewport.
 */
public class ViewportZone extends Zone {
	//fields
	private Container container;

	//constructor
	/**
	 * Create a new viewport zone with the default initial position and size
	 */
	public ViewportZone(){
		super();
		this.setDirect( false);
		container = new Container();
		super.add( container);
	}
	/**
	 * Create a new viewport zone with the specified initial position and size
	 * @param x the desired x position of the viewport zone, relative to it's parent zone
	 * @param y the desired y position of the viewport zone, relative to it's parent zone
	 * @param width the desired width of the viewport zone
	 * @param height the desired height of the viewport zone
	 */
	public ViewportZone( int x, int y, int width, int height){
		super( x, y, width, height);
		this.setDirect( false);
		container = new Container();
		super.add( container);
	}

	//zone overrides
	/**
	 * Assign touches to this zone. In the case of the viewport zone, touches are actually redirected to the internal container zone.
	 * @param touches the list of touches to add to this zone.
	 */
	@Override
	public void assign( Touch... touches){
		//assign to the container instead
		container.assign( touches);
	}

	/**
	 * Add the specified zone as a child of this zone.
	 * @param zone 
	 * @return true, if the specified zone was already a child of this zone, false otherwise
	 */
	@Override
	public boolean add( Zone zone){
		//add to the container instead
		return container.add( zone);
	}

	/**
	 * Viewport zone overrides the invokeTouch function in order to redirect any changes to it's transformation matrix to the internal container zone.
	 */
	@Override
	public void invokeTouch(){
		//save our current matrix
		PMatrix3D pretouch = new PMatrix3D( matrix);
		PMatrix3D pretouch_inv = new PMatrix3D( matrix);
		pretouch_inv.invert();
		//do the touching stuff
		super.invokeTouch();
		//get the change
		PMatrix3D posttouch = new PMatrix3D( matrix);
		PMatrix3D change = new PMatrix3D();
		change.apply( pretouch_inv);
		change.apply( posttouch);
		//apply the change to our container instead
		container.matrix.apply( change);
		//revert the change to our matrix
		this.matrix = pretouch;
	}

	/**
	 * Draws a dark translucent rectangle
	 */
	@Override
	public void draw(){
		background( 10, 10, 10, 180);
	}
	/**
	 * The viewport zone does nothing on touch - because all the touches are assigned to the container zone.
	 */
	@Override
	public void touch(){}

	//subclasses
	/**
	 * A little subclass used for containing all the zones added to the viewport zone
	 */
	private class Container extends Zone {
		//do nothing for the zone methods
		/**
		 * Draws nothing
		 */
		public void draw(){}
		/**
		 * Cannot be picked
		 */
		public void pickDraw(){}
		/**
		 * RSTs on touch
		 */
		public void touch(){
			this.rst();
		}
	}
}