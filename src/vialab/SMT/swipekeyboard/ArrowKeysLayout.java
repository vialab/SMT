package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.event.KeyEvent;

//processing library imports
import processing.core.*;

//local library imports
import vialab.SMT.*;

/**
 * A keyboard ayout that provides just the arrow keys.
 */
public class ArrowKeysLayout extends SwipeKeyboardLayout{
	public static final int base_width = 100;
	public static final int buffer = 5;
	/**
	 * Defines all the actions required to set a keyboard's layout, including
	 * creation, organization, and linking of keys.
	 * @param  keyboard The keyboard to be set up.
	 */
	public void setup( SwipeKeyboard keyboard){
		//set size
		keyboard.setWidth( base_width * 3 + buffer * 2);
		keyboard.setHeight( base_width * 2 + buffer);
		//anchor
		AnchorZone anchor = new AnchorZone();
		keyboard.addAnchor( anchor);
		anchor.setSize( keyboard.getWidth(), keyboard.getHeight());
		anchor.setCornerRounding( 5);
		//keys
		SwipeKeyZone key_left = new SwipeKeyZone( "Key Left", KeyEvent.VK_LEFT, '<');
		SwipeKeyZone key_right = new SwipeKeyZone( "Key Right", KeyEvent.VK_RIGHT, '>');
		SwipeKeyZone key_up = new SwipeKeyZone( "Key Up", KeyEvent.VK_UP, 'ʌ');
		SwipeKeyZone key_down = new SwipeKeyZone( "Key Down", KeyEvent.VK_DOWN, '∨');

		key_left.translate( 0, base_width / 2);
		key_right.translate( ( base_width + buffer) * 2, base_width / 2);
		key_up.translate( base_width + buffer, 0);
		key_down.translate( base_width + buffer, base_width + buffer);

		key_left.setCornerRounding( 7);
		key_right.setCornerRounding( 7);
		key_up.setCornerRounding( 7);
		key_down.setCornerRounding( 7);

		key_left.setLabel( "<");
		key_right.setLabel( ">");
		key_up.setLabel( "ʌ");
		key_down.setLabel( "∨");

		PShape icon_left = SMT.getApplet().loadShape( "resources/arrow_left.svg");
		PShape icon_right = SMT.getApplet().loadShape( "resources/arrow_right.svg");
		PShape icon_up = SMT.getApplet().loadShape( "resources/arrow_up.svg");
		PShape icon_down = SMT.getApplet().loadShape( "resources/arrow_down.svg");

		/*System.out.printf("left: %f, %f\n",
			icon_left.width, icon_left.height);
		System.out.printf("right: %f, %f\n",
			icon_right.width, icon_right.height);
		System.out.printf("up: %f, %f\n",
			icon_up.width, icon_up.height);
		System.out.printf("down: %f, %f\n",
			icon_down.width, icon_down.height);*/

		key_left.setIcon( icon_left);
		key_right.setIcon( icon_right);
		key_up.setIcon( icon_up);
		key_down.setIcon( icon_down);

		keyboard.addSwipeKey( key_left);
		keyboard.addSwipeKey( key_right);
		keyboard.addSwipeKey( key_up);
		keyboard.addSwipeKey( key_down);
	}
}