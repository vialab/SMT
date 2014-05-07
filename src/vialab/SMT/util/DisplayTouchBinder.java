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
public class DisplayTouchBinder extends TouchBinder {

	//fields
	private SystemAdapter adapter;
	private long last_update;
	private boolean mode_id = false;
	private boolean mode_index = false;
	private String id = null;
	private int index = -1;

	//constructors
	public DisplayTouchBinder( String id){
		super();
		adapter = SMT.getSystemAdapter();
		this.id = id;
		this.mode_id = true;
		this.update();
	}
	public DisplayTouchBinder( int index){
		super();
		adapter = SMT.getSystemAdapter();
		this.index = index;
		this.mode_index = true;
		this.update();
	}

	//touch binder overrides
	@Override
	public void update(){

	}

	//accessors
	public String getID(){
		return id;
	}
	public int getIndex(){
		return index;
	}
}