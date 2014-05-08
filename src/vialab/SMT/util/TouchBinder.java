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
	private boolean debug = true;

	protected TouchBinder(){
		bind_matrix = new PMatrix2D();
		clamp_min = new PVector( 0, 0);
		clamp_max = new PVector( 1, 1);
	}

	/**
	 * Binds the x and y components into 
	 **/
	public PVector bind( float x, float y){
		PVector raw = new PVector( x, y);
		if( debug)
			System.out.printf( "raw: %f, %f\n",
				raw.x, raw.y);
		PVector real = bind_matrix.mult( raw, null);
		if( debug)
			System.out.printf( "real: %f, %f\n",
				real.x, real.y);
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
		if( debug)
			System.out.printf( "clamped: %f, %f\n",
				real.x, real.y);
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
	protected PMatrix2D getBindMatrix(){
		return bind_matrix;
	}
	public PVector getClampMin(){
		return clamp_min;
	}
	public PVector getClampMax(){
		return clamp_max;
	}
	public void setDebug( boolean enabled){
		this.debug = enabled;
	}
}