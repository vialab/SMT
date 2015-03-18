package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * A class that maps touch coordinates onto the specified display.
 **/
public class DisplayTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update = - 1;
	private boolean mode_id = false;
	private boolean mode_index = false;
	private String id = null;
	private int index = - 1;

	//constructors
	/**
	 * Create a new touch binder that maps touches onto the display with the specified id string.
	 * @param  id the id string of the desired display
	 */
	public DisplayTouchBinder( String id){
		super();
		adapter = SMT.getSystemAdapter();
		this.id = id;
		this.mode_id = true;
	}
	/**
	 * Create a new touch binder that maps touches onto the display at the specified index in the display list.
	 * @param  index the index of the desired display
	 */
	public DisplayTouchBinder( int index){
		super();
		adapter = SMT.getSystemAdapter();
		this.index = index;
		this.mode_index = true;
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
			Rectangle display_bounds = mode_id ?
				adapter.getDisplayBounds( id):
				adapter.getDisplayBounds( index);
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

	//accessors
	/**
	 * Get the id string of the desired display device.
	 * @return the id string of the desired display device
	 */
	public String getID(){
		return id;
	}
	/**
	 * Get the index of the desired display device.
	 * @return the index of the desired display device
	 */
	public int getIndex(){
		return index;
	}
}