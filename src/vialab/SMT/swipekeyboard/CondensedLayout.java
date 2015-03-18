package vialab.SMT.swipekeyboard;

//standard library imports
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Vector;

//processing imports
import processing.core.PShape;

//local imports
import vialab.SMT.*;

/**
 * A keyboard layout that provides only the basic key set.
 */
public class CondensedLayout extends SwipeKeyboardLayout{
	/////////////
	// fields //
	/////////////
	/**
	 * The amount of padding between keys, and along the border of the keyboard.
	 */
	public static final int padding = 10;
	/**
	 * Defines whether the arrow keys should be added to the right side of the
	 * keyboard.
	 */
	public boolean enableArrowKeys;

	//////////////////
	// constructor //
	//////////////////
	/**
	 * Initialized the keyboard layout object.
	 */
	public CondensedLayout(){
		//the code to use this field has not be developed yet.
		//The feature is thus disabled.
		enableArrowKeys = false;
	}

	//////////////
	// methods //
	//////////////
	/**
	 * Defines all the actions required to set a keyboard's layout, including
	 * creation, organization, and linking of keys.
	 * @param  keyboard The keyboard to be set up.
	 */
	@Override
	public void setup( SwipeKeyboard keyboard){
		//initialize keys
		//normal keys
		KeyZone key_backspace = new KeyZone( "key backspace",
			KeyEvent.VK_BACK_SPACE, '\b');
		KeyZone key_nums = new KeyZone( "key nums", KeyEvent.VK_NUM_LOCK);
		KeyZone key_comma = new KeyZone( "key comma", KeyEvent.VK_COMMA, ',');
		KeyZone key_space = new KeyZone( "key space", KeyEvent.VK_SPACE, ' ');
		KeyZone key_period = new KeyZone( "key period", KeyEvent.VK_PERIOD, '.');
		KeyZone key_enter = new KeyZone( "key enter", KeyEvent.VK_ENTER, '\n');

		//modifier keys
		ModifierKeyZone key_shift = new ModifierKeyZone( "key shift",
			KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_LEFT);

		//key labels
		key_shift.setLabel( "^");
		key_backspace.setLabel( "<");
		key_nums.setLabel( "#");
		key_space.setLabel( "___");
		key_enter.setLabel( "<_|");

		//load icons
		PShape icon_backspace = SMT.getApplet().loadShape( "resources/backspace.svg");
		PShape icon_enter = SMT.getApplet().loadShape( "resources/enter.svg");
		PShape icon_shift = SMT.getApplet().loadShape( "resources/shift.svg");
		PShape icon_space = SMT.getApplet().loadShape( "resources/space.svg");
		key_backspace.setIcon( icon_backspace);
		key_enter.setIcon( icon_enter);
		key_shift.setIcon( icon_shift);
		key_space.setIcon( icon_space);
		//set insets
		key_backspace.setInset( 25, 25);
		key_enter.setInset( 25, 25);
		key_space.setInset( 25, 40);

		//swipe keys
		SwipeKeyZone key_q = new SwipeKeyZone( "key q", KeyEvent.VK_Q, 'q');
		SwipeKeyZone key_w = new SwipeKeyZone( "key w", KeyEvent.VK_W, 'w');
		SwipeKeyZone key_e = new SwipeKeyZone( "key e", KeyEvent.VK_E, 'e');
		SwipeKeyZone key_r = new SwipeKeyZone( "key r", KeyEvent.VK_R, 'r');
		SwipeKeyZone key_t = new SwipeKeyZone( "key t", KeyEvent.VK_T, 't');
		SwipeKeyZone key_y = new SwipeKeyZone( "key y", KeyEvent.VK_Y, 'y');
		SwipeKeyZone key_u = new SwipeKeyZone( "key u", KeyEvent.VK_U, 'u');
		SwipeKeyZone key_i = new SwipeKeyZone( "key i", KeyEvent.VK_I, 'i');
		SwipeKeyZone key_o = new SwipeKeyZone( "key o", KeyEvent.VK_O, 'o');
		SwipeKeyZone key_p = new SwipeKeyZone( "key p", KeyEvent.VK_P, 'p');
		SwipeKeyZone key_a = new SwipeKeyZone( "key a", KeyEvent.VK_A, 'a');
		SwipeKeyZone key_s = new SwipeKeyZone( "key s", KeyEvent.VK_S, 's');
		SwipeKeyZone key_d = new SwipeKeyZone( "key d", KeyEvent.VK_D, 'd');
		SwipeKeyZone key_f = new SwipeKeyZone( "key f", KeyEvent.VK_F, 'f');
		SwipeKeyZone key_g = new SwipeKeyZone( "key g", KeyEvent.VK_G, 'g');
		SwipeKeyZone key_h = new SwipeKeyZone( "key h", KeyEvent.VK_H, 'h');
		SwipeKeyZone key_j = new SwipeKeyZone( "key j", KeyEvent.VK_J, 'j');
		SwipeKeyZone key_k = new SwipeKeyZone( "key k", KeyEvent.VK_K, 'k');
		SwipeKeyZone key_l = new SwipeKeyZone( "key l", KeyEvent.VK_L, 'l');
		SwipeKeyZone key_z = new SwipeKeyZone( "key z", KeyEvent.VK_Z, 'z');
		SwipeKeyZone key_x = new SwipeKeyZone( "key x", KeyEvent.VK_X, 'x');
		SwipeKeyZone key_c = new SwipeKeyZone( "key c", KeyEvent.VK_C, 'c');
		SwipeKeyZone key_v = new SwipeKeyZone( "key v", KeyEvent.VK_V, 'v');
		SwipeKeyZone key_b = new SwipeKeyZone( "key b", KeyEvent.VK_B, 'b');
		SwipeKeyZone key_n = new SwipeKeyZone( "key n", KeyEvent.VK_N, 'n');
		SwipeKeyZone key_m = new SwipeKeyZone( "key m", KeyEvent.VK_M, 'm');

		//set alternates
		key_q.setAlternateChar( '1');
		key_w.setAlternateChar( '2');
		key_e.setAlternateChar( '3');
		key_r.setAlternateChar( '4');
		key_t.setAlternateChar( '5');
		key_y.setAlternateChar( '6');
		key_u.setAlternateChar( '7');
		key_i.setAlternateChar( '8');
		key_o.setAlternateChar( '9');
		key_p.setAlternateChar( '0');
		key_a.setAlternateChar( '!');
		key_s.setAlternateChar( '@');
		key_d.setAlternateChar( '#');
		key_f.setAlternateChar( '$');
		key_g.setAlternateChar( '%');
		key_h.setAlternateChar( '&');
		key_j.setAlternateChar( '+');
		key_k.setAlternateChar( '?');
		key_l.setAlternateChar( '/');
		key_z.setAlternateChar( '_');
		key_x.setAlternateChar( '\"');
		key_c.setAlternateChar( '\'');
		key_v.setAlternateChar( '(');
		key_b.setAlternateChar( ')');
		key_n.setAlternateChar( '-');
		key_m.setAlternateChar( ':');

		//set alternates
		keyboard.addAlternatableKey( key_q);
		keyboard.addAlternatableKey( key_w);
		keyboard.addAlternatableKey( key_e);
		keyboard.addAlternatableKey( key_r);
		keyboard.addAlternatableKey( key_t);
		keyboard.addAlternatableKey( key_y);
		keyboard.addAlternatableKey( key_u);
		keyboard.addAlternatableKey( key_i);
		keyboard.addAlternatableKey( key_o);
		keyboard.addAlternatableKey( key_p);
		keyboard.addAlternatableKey( key_a);
		keyboard.addAlternatableKey( key_s);
		keyboard.addAlternatableKey( key_d);
		keyboard.addAlternatableKey( key_f);
		keyboard.addAlternatableKey( key_g);
		keyboard.addAlternatableKey( key_h);
		keyboard.addAlternatableKey( key_j);
		keyboard.addAlternatableKey( key_k);
		keyboard.addAlternatableKey( key_l);
		keyboard.addAlternatableKey( key_z);
		keyboard.addAlternatableKey( key_x);
		keyboard.addAlternatableKey( key_c);
		keyboard.addAlternatableKey( key_v);
		keyboard.addAlternatableKey( key_b);
		keyboard.addAlternatableKey( key_n);
		keyboard.addAlternatableKey( key_m);

		//mess with key sizes
		key_shift.setWidth( 100 + 30 + padding);
		key_backspace.setWidth( 100 + 70);
		key_nums.setWidth( 100 + 3 * padding);
		key_space.setWidth( 100 + 410);
		key_enter.setWidth( 100 + 90 + 2 * padding);

		//create rows
		Vector<KeyRow> rows = new Vector<KeyRow>();
		KeyRow row1 = new KeyRow();
		KeyRow row2 = new KeyRow();
		KeyRow row3 = new KeyRow();
		KeyRow row4 = new KeyRow();
		rows.add( row1);
		rows.add( row2);
		rows.add( row3);
		rows.add( row4);
		row1.add( key_q);
		row1.add( key_w);
		row1.add( key_e);
		row1.add( key_r);
		row1.add( key_t);
		row1.add( key_y);
		row1.add( key_u);
		row1.add( key_i);
		row1.add( key_o);
		row1.add( key_p);
		row2.add( key_a);
		row2.add( key_s);
		row2.add( key_d);
		row2.add( key_f);
		row2.add( key_g);
		row2.add( key_h);
		row2.add( key_j);
		row2.add( key_k);
		row2.add( key_l);
		row3.add( key_shift);
		row3.add( key_z);
		row3.add( key_x);
		row3.add( key_c);
		row3.add( key_v);
		row3.add( key_b);
		row3.add( key_n);
		row3.add( key_m);
		row3.add( key_backspace);
		row4.add( key_nums);
		row4.add( key_comma);
		row4.add( key_space);
		row4.add( key_period);
		row4.add( key_enter);
		
		//center rows
		int width = 0;
		int height = padding;
		for( KeyRow row : rows)
			width = Math.max( row.width, width);
		width += 2 * padding;
		for( KeyRow row : rows)
			row.translate( ( width - row.width) / 2, 0);
		//spread rows vertically
		for( KeyRow row : rows){
			row.translate( 0, height);
			height += row.height + padding;
		}

		//set keyboard properties
		keyboard.setSize( width, height);

		//create anchors
		AnchorZone anchor_topLeft = new AnchorZone();
		AnchorZone anchor_topRight = new AnchorZone();
		AnchorZone anchor_bottomLeft = new AnchorZone();
		AnchorZone anchor_bottomRight = new AnchorZone();

		//send anchors to corners
		Dimension halfDim = anchor_topLeft.getHalfSize();
		anchor_topLeft.translate( - halfDim.width, - halfDim.height);
		anchor_topRight.translate( width - halfDim.width, height - halfDim.height);
		anchor_bottomLeft.translate( - halfDim.width, height - halfDim.height);
		anchor_bottomRight.translate( width - halfDim.width, - halfDim.height);

		//add keys to keyboard
		keyboard.addKey( key_backspace);
		keyboard.addKey( key_nums);
		keyboard.addKey( key_comma);
		keyboard.addKey( key_space);
		keyboard.addKey( key_period);
		keyboard.addKey( key_enter);

		//add modifier keys to keyboard
		keyboard.addModifierKey( key_shift);

		//add swipe keys to keyboard
		keyboard.addSwipeKey( key_q);
		keyboard.addSwipeKey( key_w);
		keyboard.addSwipeKey( key_e);
		keyboard.addSwipeKey( key_r);
		keyboard.addSwipeKey( key_t);
		keyboard.addSwipeKey( key_y);
		keyboard.addSwipeKey( key_u);
		keyboard.addSwipeKey( key_i);
		keyboard.addSwipeKey( key_o);
		keyboard.addSwipeKey( key_p);
		keyboard.addSwipeKey( key_a);
		keyboard.addSwipeKey( key_s);
		keyboard.addSwipeKey( key_d);
		keyboard.addSwipeKey( key_f);
		keyboard.addSwipeKey( key_g);
		keyboard.addSwipeKey( key_h);
		keyboard.addSwipeKey( key_j);
		keyboard.addSwipeKey( key_k);
		keyboard.addSwipeKey( key_l);
		keyboard.addSwipeKey( key_z);
		keyboard.addSwipeKey( key_x);
		keyboard.addSwipeKey( key_c);
		keyboard.addSwipeKey( key_v);
		keyboard.addSwipeKey( key_b);
		keyboard.addSwipeKey( key_n);
		keyboard.addSwipeKey( key_m);

		//add anchors to keyboard
		keyboard.addAnchor( anchor_topLeft);
		keyboard.addAnchor( anchor_topRight);
		keyboard.addAnchor( anchor_bottomLeft);
		keyboard.addAnchor( anchor_bottomRight);

		//other options
		keyboard.setBackgroundEnabled( true);
		keyboard.setCornerRounding( 20);
	}

	//////////////////////
	// private classes //
	//////////////////////
	/**
	 * Represents a row of keys.
	 */
	private class KeyRow extends Vector<KeyZone> {
		/**
		 * The width of the row.
		 */
		public int width;
		/**
		 * The height of the row.
		 */
		public int height;
		/**
		 * Initializes the row.
		 */
		public KeyRow(){
			super();
			width = 0;
			height = 0;
		}
		/**
		 * Adds a key to the row, and re-positions it to the end of the row
		 * @param  key The key to be added.
		 * @return     true (as specified by Collection.add(E))
		 */
		public boolean add( KeyZone key){
			super.add( key);
			Dimension keydim = key.getSize();
			if( width != 0) width += padding;
			key.translate( width, 0);
			width += keydim.width;
			height = Math.max( keydim.height, height);
			return true;
		}
		/**
		 * Translates every key in the row.
		 * @param dx The desired change in the x direction.
		 * @param dy The desired change in the y direction.
		 */
		public void translate( int dx, int dy){
			for( KeyZone key : this)
				key.translate( dx, dy);
		}
	}
}