package vialab.SMT;


/**
 * Used to specify what kind of touch visualization and information to display.
 * 
 * NONE - None disables all drawing of the touches. It likely should not be used unless an OS-based or the sketch itself is providing the visualisation of the touches.
 * 
 * DEBUG - Debug mode will show some debugging info near the touch path, and some extra debug messages on the console.
 * 
 * SMOOTH - Smooth mode has a Ripples-like visual representation of touches, and shows whether the touch was assigned or not with an inward or outward ripple effect.
 *
 * TEXTURED - Textured mode uses some nice textures and some fancy opengl. Its kind of an upgrade from SMOOTH.
 */
public enum TouchDraw {

	/**
	 * None disables all drawing of the touches, it likely should not be used
	 * unless an OS-based or the sketch itself is providing the visualisation of
	 * the touches
	 */
	NONE,

	/**
	 * Custom mode will simple use the user-specified TouchDrawer
	 */
	CUSTOM,

	/**
	 * Debug mode will show some debugging info near the touch path, and some
	 * extra debug messages on the console
	 */
	DEBUG,

	/**
	 * Smooth mode has a Ripples-like visual representation of touches, and
	 * shows whether the touch was assigned or not with an inward or outward
	 * ripple effect
	 */
	SMOOTH,

	/**
	 * Textured mode uses some nice textures and some fancy opengl.
	 * Its kind of an upgrade from SMOOTH.
	 */
	TEXTURED;
}
