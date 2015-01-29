package vialab.SMT.swipekeyboard;

//local imports
import vialab.SMT.*;

/**
 * Class that defines how the keys of a swipe keyboard are laid out and set up.
 */
public abstract class SwipeKeyboardLayout {
	/**
	 * Defines all the actions required to set a keyboard's layout, including
	 * creation, organization, and linking of keys.
	 * @param keyboard The keyboard to be set up.
	 */
	public abstract void setup( SwipeKeyboard keyboard);
}