package vialab.SMT.util;

//libtuio imports
import TUIO.*;

//processing imports
import processing.core.*;

/**
 * Just a little class for describing how to map ([0,1], [0,1]) tuio coordinates to ([0,applet.width], [0,applet.height]) touch coordinates.
 **/
public abstract class TouchBinder {
	private PMatrix2D bind_matrix;
	private PVector clamp_min;
	private PVector clamp_max;

	protected TouchBinder(){
		bind_matrix = new PMatrix2D();
		clamp_min = new PVector( 0, 0);
		clamp_max = new PVector( 1, 1);
	}

	/**
	 * Binds the x and y components into 
	 **/
	public PVector bind( TuioPoint point){
		PVector raw = new PVector(
			point.getX(), point.getY());
		PVector real = bind_matrix.mult( raw, null);
		//clamp min bounds
		if( real.x < clamp_min.x)
			real.x = clamp_min.x;
		if( real.y < clamp_min.y)
			real.y = clamp_min.y;
		//clamp max bounds
		if( real.x > clamp_max.x)
			real.x = clamp_max.x;
		if( real.y > clamp_max.y)
			real.y = clamp_max.y;
		return real;
	}

	public abstract void update();

	//accessors
	protected void setBindMatrix( PMatrix2D matrix){
		this.bind_matrix = matrix;
	}
	public void setClampMin( PVector min){
		clamp_min = min;
	}
	public void setClampMax( PVector max){
		clamp_max = max;
	}
}