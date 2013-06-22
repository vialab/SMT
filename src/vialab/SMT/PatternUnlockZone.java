package vialab.SMT;

import java.util.ArrayList;

/**
 * Creates a 'lock' that is unlocked when a
 * specified pattern is inputted by the user.
 * Can be used to perform an action when
 * the user inputs the specified pattern.
 * Similar to the Android screen lock.
 *
 */
public class PatternUnlockZone extends Zone {
	private class CircleZone extends Zone {
		public boolean touched = false;
		private int index;

		CircleZone(int x, int y, int width, int height, int index) {
			super(x, y, width, height);
			this.index = index;
		}

		protected void touchImpl() {
			unassignAll();
			if (!accepted) {
				if (!touched) {
					code.add(index);
				}
				touched = true;
			}
		}

		protected void drawImpl() {
			fill(255);
			ellipse(width / 2, height / 2, width*7/10, height*7/10);
			if (touched) {
				fill(0, 255, 0);
				ellipse(width / 2, height / 2, width / 2, height / 2);
			}
		}

		protected void touchUpImpl(Touch t) {
			if (!accepted) {
				code.clear();
				for (int i = 0; i < 9; i++) {
					pattern[i].touched = false;
				}
			}
		}
	}

	public String passcode = "67412";
	public boolean accepted = false;
	private ArrayList<Integer> code = new ArrayList<Integer>();
	private CircleZone[] pattern = new CircleZone[9];

	public PatternUnlockZone(int x, int y, int width, int height) {
		super(x, y, width, height);
		for (int i = 0; i < 9; i++) {
			pattern[i] = new CircleZone((i % 3) * width / 3, (i / 3) * width / 3, width / 3,
					width / 3, i);
			add(pattern[i]);
		}
	}

	protected void drawImpl() {
		fill(255);
		rect(0, 0, width, height);

		checkPasscode();
	}

	protected void checkPasscode() {
		String pass = "";
		for (Integer i : code) {
			pass += i;
		}
		fill(0);
		textAlign(RIGHT);
		text(pass, width, height);
		if (pass.length() >= 5) {
			if ((pass.equals(passcode) || pass.equals(new StringBuilder(passcode).reverse().toString())) && !accepted) {
				System.out.println("passcode accepted");
				accepted = true;
			}
		}
	}
	
	@Override
	protected void touchImpl() {
		unassignAll();
	}

	@Override
	protected void touchUpImpl(Touch t) {
		code.clear();
		for (int i = 0; i < 9; i++) {
			pattern[i].touched = false;
		}
	}

	void reset() {
		code.clear();
		for (int i = 0; i < 9; i++) {
			pattern[i].touched = false;
		}
		accepted = false;
	}
}