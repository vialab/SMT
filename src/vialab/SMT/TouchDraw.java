package vialab.SMT;

public enum TouchDraw {

	/**
	 * None disables all drawing of the touches, it likely should not be used
	 * unless an OS-based or the sketch itself is providing the visualisation of
	 * the touches
	 */
	NONE,

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
	SMOOTH;
}
