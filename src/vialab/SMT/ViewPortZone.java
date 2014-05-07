package vialab.SMT;

//processing imports
import processing.core.*;

public class ViewPortZone extends Zone {
	//fields
	private Container container;

	//constructor
	public ViewPortZone(){
		super();
		this.setDirect( false);
		container = new Container();
		super.add( container);
	}
	public ViewPortZone( int x, int y, int width, int height){
		super( x, y, width, height);
		this.setDirect( false);
		container = new Container();
		super.add( container);
	}

	//zone overrides
	@Override
	public void assign( Touch... touches){
		//assign to the container instead
		container.assign( touches);
	}

	@Override
	public boolean add( Zone zone){
		//add to the container instead
		return container.add( zone);
	}

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

	@Override
	public void draw(){
		background( 10, 10, 10, 180);
	}
	@Override
	public void touch(){}

	//subclasses
	private class Container extends Zone {
		//do nothing for the zone methods
		public void draw(){}
		public void pickDraw(){}
		public void touch(){
			this.rst();
		}
	}
}