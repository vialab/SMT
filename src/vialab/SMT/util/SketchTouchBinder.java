package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * A class that maps touch coordinates the current sketch's dimensions.
 **/
public class SketchTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update = - 1;

	//contructors
	/**
	 * Create a new sketch touch binder that maps to the current sketch's dimensions.
	 */
	public SketchTouchBinder(){
		super();
		adapter = SMT.getSystemAdapter();
	}

	//touch binder overrides
	/**
	 * Updates the touch binder to match the current applet dimensions.
	 */
	@Override
	public void update(){
		//only update when needed
		if( adapter.getLastUpdateTime() > last_update){
			//get sketch bounds
			Rectangle sketch_bounds = adapter.getSketchBounds();
			//create bind matrix
			PMatrix2D bind_matrix = new PMatrix2D();
			bind_matrix.scale( sketch_bounds.width, sketch_bounds.height);
			//set bind matrix and clamp bounds
			this.setBindMatrix( bind_matrix);
			this.setClampMax( new PVector(
				sketch_bounds.width, sketch_bounds.height));
			//update last time
			last_update = adapter.getLastUpdateTime();
		}
	}
}