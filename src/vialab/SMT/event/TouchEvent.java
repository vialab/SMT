package vialab.SMT.event;

//local imports
import vialab.SMT.*;

/**
 * An event class that describes changes in a touch's state.
 */
public class TouchEvent extends java.util.EventObject {
	/////////////////////
	// private fields //
	/////////////////////
	/**
	 * Defines the type of Touch event that occured.
	 */
	private TouchType type;
	/**
	 * The touch involved in the event
	 */
	private Touch touch;

	////////////////
	// constants //
	////////////////
	/**
	 * An enum to describe the type of touch event that can occur.
	 */
	public enum TouchType {
		UP, DOWN, MOVED
	}

	//////////////////
	// constructor //
	//////////////////
	/**
	 * Constructs a TouchEvent with the specified source, type and touch
	 * @param  source The invoker of the event.
	 * @param  type   The type of touch event that occured.
	 * @param  touch  The touch invovled in the event.
	 */
	public TouchEvent( Object source, TouchType type, Touch touch){
		super( source);
		this.type = type;
		this.touch = touch;
	}

	///////////////////////
	// accessor methods //
	///////////////////////
	/**
	 * Returns the type of touch event that occured.
	 * @return The type of touch event that occured.
	 */
	public TouchType getTouchType(){
		return type;
	}
	/**
	 * Returns the touch involved in the event.
	 * @return The touch involved in the event.
	 */
	public Touch getTouch(){
		return touch;
	}
}