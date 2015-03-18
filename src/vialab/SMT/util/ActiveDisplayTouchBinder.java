package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * A class that maps touch coordinates onto the display that the current sketch is on.
 **/
public class ActiveDisplayTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update = - 1;

	//contructors
	/**
	 * Create a new touch binder that maps touches onto the display that the current sketch is on.
	 */
	public ActiveDisplayTouchBinder(){
		super();
		adapter = SMT.getSystemAdapter();
	}

	//touch binder overrides
	/**
	 * Updates the bind matrix to match the current display layout and the current window position.
	 */
	@Override
	public void update(){
		//only update when needed
		if( adapter.getLastUpdateTime() > last_update){
			//get sketch bounds
			Rectangle sketch_bounds = adapter.getSketchBounds();
			//get display bounds
			Rectangle display_bounds =
				adapter.getActiveDisplayBounds();
			//create bind matrix
			PMatrix2D bind_matrix = new PMatrix2D();
			bind_matrix.translate( - sketch_bounds.x, - sketch_bounds.y);
			bind_matrix.translate( display_bounds.x, display_bounds.y);
			bind_matrix.scale( display_bounds.width, display_bounds.height);
			//set bind matrix and clamp bounds
			this.setBindMatrix( bind_matrix);
			this.setClampMax( new PVector(
				sketch_bounds.width, sketch_bounds.height));
			//update last time
			last_update = adapter.getLastUpdateTime();
		}
	}
}