package vialab.SMT.zone;

//standard library imports
import java.awt.event.KeyEvent;
//local imports
import vialab.SMT.zone.*;

public class ArrowKeysLayout extends SwipeKeyboardLayout{
	public static final int base_width = 100;
	public void setup( SwipeKeyboard keyboard){
		//anchor
		AnchorZone anchor = new AnchorZone();
		keyboard.addAnchor( anchor);
		anchor.setWidth( base_width * 3);
		anchor.setHeight( base_width * 2);
		anchor.setCornerRounding( 5);
		//keys
		SwipeKeyZone key_left = new SwipeKeyZone( "Key Left", KeyEvent.VK_LEFT, '<');
		SwipeKeyZone key_right = new SwipeKeyZone( "Key Right", KeyEvent.VK_RIGHT, '>');
		SwipeKeyZone key_up = new SwipeKeyZone( "Key Up", KeyEvent.VK_UP, 'ʌ');
		SwipeKeyZone key_down = new SwipeKeyZone( "Key Down", KeyEvent.VK_DOWN, '∨');
		key_left.translate( 0, base_width/2);
		key_right.translate( base_width*2, base_width/2);
		key_up.translate( base_width, 0);
		key_down.translate( base_width, base_width);
		key_left.setCornerRounding( 7, 0, 7, 0);
		key_right.setCornerRounding( 0, 7, 0, 7);
		key_up.setCornerRounding( 7, 7, 0, 0);
		key_down.setCornerRounding( 0, 0, 7, 7);
		key_left.setLabel( "<");
		key_right.setLabel( ">");
		key_up.setLabel( "ʌ");
		key_down.setLabel( "∨");
		keyboard.addSwipeKey( key_left);
		keyboard.addSwipeKey( key_right);
		keyboard.addSwipeKey( key_up);
		keyboard.addSwipeKey( key_down);
	}
}