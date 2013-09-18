package vialab.SMT.zone;

//standard library imports
import java.awt.event.KeyEvent;
import java.util.Vector;
//local imports
import vialab.SMT.*;
import vialab.SMT.zone.*;

public class CondensedLayout extends SwipeKeyboardLayout{
	//fields
	public static final int padding = 2;
	public boolean enableArrowKeys;

	//constructor
	public CondensedLayout(){
		enableArrowKeys = true;
	}

	//methods
	@Override
	public void setup( SwipeKeyboard keyboard){
		//initialize keys
		//normal keys
		KeyZone key_shift = new KeyZone( "key shift", KeyEvent.VK_SHIFT,
			KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_LEFT);
		KeyZone key_backspace = new KeyZone( "key backspace",
			KeyEvent.VK_BACK_SPACE);

		//swipe keys
		SwipeKeyZone key_q = new SwipeKeyZone( "key q", KeyEvent.VK_Q, 'Q');
		SwipeKeyZone key_w = new SwipeKeyZone( "key w", KeyEvent.VK_W, 'W');
		SwipeKeyZone key_e = new SwipeKeyZone( "key e", KeyEvent.VK_E, 'E');
		SwipeKeyZone key_r = new SwipeKeyZone( "key r", KeyEvent.VK_R, 'R');
		SwipeKeyZone key_t = new SwipeKeyZone( "key t", KeyEvent.VK_T, 'T');
		SwipeKeyZone key_y = new SwipeKeyZone( "key y", KeyEvent.VK_Y, 'Y');
		SwipeKeyZone key_u = new SwipeKeyZone( "key u", KeyEvent.VK_U, 'U');
		SwipeKeyZone key_i = new SwipeKeyZone( "key i", KeyEvent.VK_I, 'I');
		SwipeKeyZone key_o = new SwipeKeyZone( "key o", KeyEvent.VK_O, 'O');
		SwipeKeyZone key_p = new SwipeKeyZone( "key p", KeyEvent.VK_P, 'P');
		SwipeKeyZone key_a = new SwipeKeyZone( "key a", KeyEvent.VK_A, 'A');
		SwipeKeyZone key_s = new SwipeKeyZone( "key s", KeyEvent.VK_S, 'S');
		SwipeKeyZone key_d = new SwipeKeyZone( "key d", KeyEvent.VK_D, 'D');
		SwipeKeyZone key_f = new SwipeKeyZone( "key f", KeyEvent.VK_F, 'F');
		SwipeKeyZone key_g = new SwipeKeyZone( "key g", KeyEvent.VK_G, 'G');
		SwipeKeyZone key_h = new SwipeKeyZone( "key h", KeyEvent.VK_H, 'H');
		SwipeKeyZone key_j = new SwipeKeyZone( "key j", KeyEvent.VK_J, 'J');
		SwipeKeyZone key_k = new SwipeKeyZone( "key k", KeyEvent.VK_K, 'K');
		SwipeKeyZone key_l = new SwipeKeyZone( "key l", KeyEvent.VK_L, 'L');
		SwipeKeyZone key_z = new SwipeKeyZone( "key z", KeyEvent.VK_Z, 'Z');
		SwipeKeyZone key_x = new SwipeKeyZone( "key x", KeyEvent.VK_X, 'X');
		SwipeKeyZone key_c = new SwipeKeyZone( "key c", KeyEvent.VK_C, 'C');
		SwipeKeyZone key_v = new SwipeKeyZone( "key v", KeyEvent.VK_V, 'V');
		SwipeKeyZone key_b = new SwipeKeyZone( "key b", KeyEvent.VK_B, 'B');
		SwipeKeyZone key_n = new SwipeKeyZone( "key n", KeyEvent.VK_N, 'N');
		SwipeKeyZone key_m = new SwipeKeyZone( "key m", KeyEvent.VK_M, 'M');

		//mess with key sizes

		//create rows
		Vector<KeyRow> rows = new Vector<KeyRow>();
		KeyRow row1 = new KeyRow();
		KeyRow row2 = new KeyRow();
		KeyRow row3 = new KeyRow();
		rows.add( row1);
		rows.add( row2);
		rows.add( row3);
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
		row3.add( key_z);
		row3.add( key_x);
		row3.add( key_c);
		row3.add( key_v);
		row3.add( key_b);
		row3.add( key_n);
		row3.add( key_m);

		//spread rows
		row2.translate( 0, row1.height);
		row3.translate( 0, row1.height + row2.height);
		
		//center rows
		int width = 0;
		for( KeyRow row : rows)
			width = Math.max( row.width, width);
		width += padding;
		for( KeyRow row : rows)
			row.translate( (row.width - width)/2, 0);

		//add keys to keyboard
		keyboard.addKey( key_shift);
		keyboard.addKey( key_backspace);

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
	}

	//private classes
	private class KeyRow extends Vector<KeyZone> {
		public int x, y;
		public int width;
		public int height;
		public KeyRow(){
			super();
			width = 0;
			height = padding;
		}
		public boolean add( KeyZone key){
			super.add( key);
			key.translate( width, padding);
			if( width != 0) width += padding;
			width += key.width;
			height = Math.max( key.height + padding, height);
			return true;
		}
		public void translate( int dx, int dy){
			x += dx;
			y += dy;
			for( KeyZone key : this)
				key.translate( dx, dy);
		}
	}
}