package vialab.SMT.util;

//standard library imports
import java.awt.*;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * Just a little class for describing how to map ([0,1], [0,1]) tuio coordinates to ([0,applet.width], [0,applet.height]) touch coordinates.
 **/
public class ScreenTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update = - 1;

	//contructors
	public ScreenTouchBinder(){
		super();
		adapter = SMT.getSystemAdapter();
	}

	//touch binder overrides
	@Override
	public void update(){
		//only update when needed
		if( adapter.getLastUpdateTime() > last_update){
			//get sketch bounds
			Rectangle sketch_bounds = adapter.getSketchBounds();
			//get display bounds
			Rectangle screen_bounds = adapter.getScreenBounds();
			//create bind matrix
			PMatrix2D bind_matrix = new PMatrix2D();
			bind_matrix.translate( - sketch_bounds.x, - sketch_bounds.y);
			bind_matrix.translate( screen_bounds.x, screen_bounds.y);
			bind_matrix.scale( screen_bounds.width, screen_bounds.height);
			//set bind matrix and clamp bounds
			this.setBindMatrix( bind_matrix);
			this.setClampMax( new PVector(
				sketch_bounds.width, sketch_bounds.height));
			//update last time
			last_update = adapter.getLastUpdateTime();
		}
	}

	//accessors
}