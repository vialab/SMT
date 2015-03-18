package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * A class that maps touch coordinates on a rectangle
 **/
public class RectTouchBinder extends TouchBinder {

	//fields
	private Rectangle bind_rect;

	//contructors
	/**
	 * Create a new touch binder that binds touches onto the desired rectangle
	 * @param  rect the rectangle that represents the desired touch bounds
	 */
	public RectTouchBinder( Rectangle rect){
		super();
		this.setBindRect( rect);
	}

	//accessors
	/**
	 * Transforms a rectangle into a touch bind matrix. This matrix is then used as this touch binder's bind matrix.
	 * @param rect The desired touch bounds
	 */
	public void setBindRect( Rectangle rect){
		this.bind_rect = rect;
		PMatrix2D bind_matrix = new PMatrix2D();
		bind_matrix.translate( rect.x, rect.y);
		bind_matrix.scale( rect.width, rect.height);
		this.setBindMatrix( bind_matrix);
		this.setClampMin(
			new PVector( rect.x, rect.y));
		this.setClampMax(
			new PVector( rect.x + rect.width, rect.y + rect.height));
	}
}