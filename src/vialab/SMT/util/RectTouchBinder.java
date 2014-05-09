package vialab.SMT.util;

//standard library imports
import java.awt.Rectangle;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * Just a little class for describing how to map ([0,1], [0,1]) tuio coordinates to ([0,applet.width], [0,applet.height]) touch coordinates.
 **/
public class RectTouchBinder extends TouchBinder {

	//fields
	private Rectangle bind_rect;

	//contructors
	public RectTouchBinder( Rectangle rect){
		super();
		this.setBindRect( rect);
	}

	//touch binder overrides
	@Override
	public void update(){}

	//accessors
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