package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//libtuio imports
import TUIO.*;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * Just a little class for describing how to map ([0,1], [0,1]) tuio coordinates to ([0,applet.width], [0,applet.height]) touch coordinates.
 **/
public class SketchTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update = - 1;

	//contructors
	public SketchTouchBinder(){
		super();
		adapter = SMT.getSystemAdapter();
	}

	//touch binder overrides
	@Override
	public void update(){
		if( adapter.getLastUpdateTime() > last_update){
			Rectangle sketch_bounds = adapter.getSketchBounds();
			PMatrix2D bind_matrix = new PMatrix2D();
			bind_matrix.scale( 100, 100);
			//bind_matrix.scale( sketch_bounds.width, sketch_bounds.height);
			this.setBindMatrix( bind_matrix);
			this.setClampMin( new PVector( 30, 30));
			this.setClampMax( new PVector( 70, 70));
			last_update = adapter.getLastUpdateTime();
		}
	}

	//accessors
}