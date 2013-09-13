package vialab.SMT.event;

//local imports
import vialab.SMT.*;

public class TouchEvent extends java.util.EventObject {
	//private fields
	private TouchType type;
	private Touch touch;

	//constants
	public enum TouchType {
		UP, DOWN, MOVED
	}

	//constructor
	public TouchEvent( Object source, TouchType type, Touch touch){
		super( source);
		this.type = type;
		this.touch = touch;
	}

	//accessor methods
	public TouchType getTouchType(){
		return type;
	}
	public Touch getTouch(){
		return touch;
	}
}