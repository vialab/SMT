package vialab.SMT.util;

//processing imports
import processing.core.*;

/**
 * Just a little class for describing how to map ([0,1], [0,1]) tuio coordinates onto ([0,applet.width], [0,applet.height]) touch coordinates.
 **/
public class TouchBinder {
	private PMatrix2D bind_matrix;
	private PVector clamp_min;
	private PVector clamp_max;
	private boolean debug = false;

	public TouchBinder(){
		bind_matrix = new PMatrix2D();
		clamp_min = new PVector( 0, 0);
		clamp_max = new PVector( 1, 1);
	}

	/**
	 * Binds the x and y components into 
	 * @param x The x coordinate in [0,1] space to bind onto [0,applet.width] space
	 * @param y The y coordinate in [0,1] space to bind onto [0,applet.height] space
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
		if( clamp_min != null){
			if( real.x < clamp_min.x)
				real.x = clamp_min.x;
			if( real.y < clamp_min.y)
				real.y = clamp_min.y;
		}
		//clamp max bounds
		if( clamp_max != null){
			if( real.x > clamp_max.x)
				real.x = clamp_max.x;
			if( real.y > clamp_max.y)
				real.y = clamp_max.y;
		}
		if( debug)
			System.out.printf( "clamped: %f, %f\n",
				real.x, real.y);
		return real;
	}

	/**
	 * Update this touch binder.
	 */
	public void update(){}

	//set accessors
	/**
	 * Set the bind matrix for this touch binder
	 * @param matrix the desired bind matrix
	 */
	public void setBindMatrix( PMatrix2D matrix){
		this.bind_matrix = matrix;
	}
	/**
	 * Set the minimum desired values of x and y coordinates.
	 * @param min the desired lower clamping bound of x and y coordinates
	 */
	public void setClampMin( PVector min){
		clamp_min = min;
	}
	/**
	 * Set the maximum desired values of x and y coordinates.
	 * @param max the desired upper clamping bound of x and y coordinates
	 */
	public void setClampMax( PVector max){
		clamp_max = max;
	}
	/**
	 * Enables or disabled debug console output for this touch binder
	 * @param enabled whether debug messages should be enabled or not.
	 */
	public void setDebug( boolean enabled){
		this.debug = enabled;
	}

	//get accessors
	/**
	 * Get the bind matrix of this touch binder
	 * @return the bind matrix of this touch binder
	 */
	public PMatrix2D getBindMatrix(){
		return bind_matrix;
	}
	/**
	 * Get the current minimum values of x and y coordinates.
	 * @return the current lower clamping bound of x and y coordinates
	 */
	public PVector getClampMin(){
		return clamp_min;
	}
	/**
	 * Get the current maximum values of x and y coordinates.
	 * @return the current upper clamping bound of x and y coordinates
	 */
	public PVector getClampMax(){
		return clamp_max;
	}
}