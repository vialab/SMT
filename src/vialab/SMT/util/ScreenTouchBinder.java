package vialab.SMT.util;

//libtuio imports
import TUIO.*;

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
	private long last_update;

	//contructors
	public ScreenTouchBinder(){
		super();
	}

	//touch binder overrides
	@Override
	public void update(){}

	//accessors
}