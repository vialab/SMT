package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Vector;

//processing imports
import processing.core.PVector;

//local imports
import vialab.SMT.*;

/**
 * This class defines a zone that represents a modifier key in a keyboard.
 **/
public class ModifierKeyZone extends KeyZone {
	//////////////////
	// debug fields //
	//////////////////
	/** Enables and disables debug print statements */
	private static final boolean debug = false;
	
	// modifier fields
	/** Stores the modifier mask of this key.
	 * If this key is not a modifier, it should be 0. */
	protected int modifierMask;
	/** Whether the key is locked on or now */
	protected boolean locked = false;

	// drawing fields
	protected Color stroke_locked;
	protected Color stroke_backup;

	//////////////////
	// Constructors //
	//////////////////
	/**
	 * The basic constructor, plus the key character field.
	 * The name field defaults to the class name.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public ModifierKeyZone( int keyCode){
		this( keyCode, KeyEvent.KEY_LOCATION_STANDARD);
	}
	/**
	 * The full constructor, with all fields, minus zone name.
	 * The name field defaults to the class name.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyLocation The KeyEvent style key location for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public ModifierKeyZone( int keyCode, int keyLocation){
		this( "ModifierKeyZone", keyCode, keyLocation);
	}
	/**
	 * The full constructor, with all fields, minus key location.
	 * The keyLocation field defaults to KeyEvent.KEY_LOCATION_STANDARD.
	 * @param  name        The human-friendly name of the key.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public ModifierKeyZone( String name, int keyCode){
		this( name, keyCode, KeyEvent.KEY_LOCATION_STANDARD);
	}
	/**
	 * The full constructor, with all fields.
	 * @param  name        The human-friendly name of the key.
	 * @param  keyCode     The KeyEvent style key code for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 * @param  keyLocation The KeyEvent style key location for the key. See
	 *                     java.awt.event.KeyEvent for the valid values.
	 */
	public ModifierKeyZone( String name, int keyCode, int keyLocation){
		super( name, keyCode, KeyEvent.CHAR_UNDEFINED, keyLocation);
		//other initialization
		stroke_backup = new Color(
			stroke_base.getRed(),
			stroke_base.getGreen(),
			stroke_base.getBlue(),
			stroke_base.getAlpha());
		stroke_locked = new Color( 50, 100, 50, 255);

		//set modifier fields
		if( this.keyCode == KeyEvent.VK_SHIFT)
			modifierMask = KeyEvent.SHIFT_DOWN_MASK;
		else if( this.keyCode == KeyEvent.VK_ALT)
			modifierMask = KeyEvent.ALT_DOWN_MASK;
		else if( this.keyCode == KeyEvent.VK_CONTROL)
			modifierMask = KeyEvent.CTRL_DOWN_MASK;
		else if( this.keyCode == KeyEvent.VK_META)
			modifierMask = KeyEvent.META_DOWN_MASK;
		else if( this.keyCode == KeyEvent.VK_ALT_GRAPH)
			modifierMask = KeyEvent.ALT_GRAPH_DOWN_MASK;
		else
			throw new IllegalArgumentException(
				"ModifierKeyZone was given a keyCode that does not match any known modifiers");

	}

	///////////////////
	// SMT overrides //
	///////////////////
	protected void setDown( boolean down){
		if( down)
			locked = ! locked;
		stroke_base = locked ? stroke_locked : stroke_backup;
		super.setDown( down);
	}

	// Accessor methods
	public int getModifierMask(){
		return modifierMask;
	}
	public boolean isLocked(){
		return locked;
	}
}