package vialab.SMT.event;

//local imports
import vialab.SMT.*;
import vialab.SMT.zone.*;

public class SwipeKeyEvent extends java.util.EventObject {
	//private fields
	private int id;
	private int keyCode;
	private char keyChar;
	private int keyLocation;
	private Touch touch;

	//constants
	// used integer constants instead of enum to allow easier extension.
	public static final int SWIPE_STARTED = 1;
	public static final int SWIPE_HIT = 2;
	public static final int SWIPE_ENDED = 3;

	public SwipeKeyEvent( Object source, int id, int keyCode, char keyChar,
			int keyLocation, Touch touch){
		super( source);
		this.id = id;
		this.keyCode = keyCode;
		this.keyChar = keyChar;
		this.keyLocation = keyLocation;
		this.touch = touch;
	}
	/*KeyEvent( Component source, int id, long when, int modifiers,
			int keyCode, char keyChar)
	KeyEvent( Component source, int id, long when, int modifiers,
			int keyCode, char keyChar, int keyLocation)*/

	//accessor methods
	public char getKeyChar(){
		return keyChar;
	}
	public int getKeyCode(){
		return keyCode;
	}
	public Touch getTouch(){
		return touch;
	}
}