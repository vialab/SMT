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
		this( null, 0, 0, 100, 100);
	}
	/**
	 * Create a new viewport zone with the specified initial position and size
	 * @param x the desired x position of the viewport zone, relative to it's parent zone
	 * @param y the desired y position of the viewport zone, relative to it's parent zone
	 * @param width the desired width of the viewport zone
	 * @param height the desired height of the viewport zone
	 */
	public ViewportZone( int x, int y, int width, int height){
		this( null, x, y, width, height);
	}
	/**
	 * Create a new viewport zone with the specified initial position and size
	 * @param name the desired name of the zone
	 * @param x the desired x position of the viewport zone, relative to it's parent zone
	 * @param y the desired y position of the viewport zone, relative to it's parent zone
	 * @param width the desired width of the viewport zone
	 * @param height the desired height of the viewport zone
	 */
	public ViewportZone( String name, int x, int y, int width, int height){
		super( name, x, y, width, height);
		this.setDirect( false);
		container = new Container( this.getName());
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
	 * Add the specified zone as a child of this zone.
	 * @param zone 
	 * @return true, if the specified zone was already a child of this zone, false otherwise
	 */
	@Override
	public boolean remove( Zone zone){
		//remove the container instead
		return container.remove( zone);
	}

	@Override
	public void setName( String name){
		super.setName( name);
		if( container != null)
			container.setName( name);
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
	 *
	 * This method should never be invoked, but could be, theoretically.
	 */
	@Override
	public void touch(){}

	//warn overrides
	@Override
	boolean warnDraw(){
		return false;
	}
	@Override
	boolean warnTouch(){
		return false;
	}

	//utility functions
	/**
	 * Refreshes this viewport's graphics object.
	 */
	public void refresh(){
		this.refreshResolution();
	}

	/**
	 * Refreshes this viewport's view matrix.
	 */
	public void resetView(){
		container.resetMatrix();
	}

	//accessor functions
	/**
	 * Sets the name of the zone used to contain all children so that it's touchMyZone( Zone) method can be written in the PApplet.
	 * @param name the desired name of the container
	 */
	public void setContainerName( String name){
		container.setName( name);
	}
	/**
	 * Sets the zone used to contain all children.
	 * 
	 * Use this if you want to use a custom class that extends ViewportZone.Container for this viewport's container. This function will not migrate any current children from the old container to this new contain. It is recommended that if a migration is required or desireable, it be done manually.
	 * @param container the desired container
	 */
	public void setContainer( Container container){
		super.remove( this.container);
		this.container = container;
		super.add( this.container);
	}
	/**
	 * Gets the zone currently being used to contain all children.
	 * 
	 * Use this if you want to access the container directly, or if you want to be able to switch containers in a viewport zone, then be able to switch them back.
	 *
	 * One does not need to use this function to add zones to the viewport. ViewportZone.add( Zone) actually adds the zones to the viewport's container.
	 *
	 * @return the zone currently being used to contain all children.
	 */
	public Container getContainer(){
		return this.container;
	}

	//subclasses
	/**
	 * A little subclass used for containing all the zones added to the viewport zone
	 */
	protected class Container extends Zone {
		public Container(){
			this( "ViewportContainerZone");
		}
		public Container( String name){
			super( name, 0, 0, 100, 100);
		}
		//override the drawing method invokation functions to permanantly disable draing and pick-drawing, as well as prevent conflicts with the PApplet methods for the parent view-port's draw and pick draw methods.
		@Override
		public void invokeDrawMethod(){}
		@Override
		public void invokePickDrawMethod(){}

		@Override
		public void draw(){}
		@Override
		public void pickDraw(){}
		@Override
		//default touch method is pinch
		public void touch(){
			this.pinch();
		}

		//warn overrides
		@Override
		boolean warnDraw(){
			return false;
		}
		@Override
		boolean warnTouch(){
			return false;
		}
	}
}