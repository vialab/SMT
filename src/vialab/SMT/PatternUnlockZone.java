package vialab.SMT;

import java.util.ArrayList;

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
			ellipse(width / 2, height / 2, width, height);
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

	public boolean accepted = false;
	private ArrayList<Integer> code = new ArrayList<Integer>();
	private CircleZone[] pattern = new CircleZone[9];

	public PatternUnlockZone(int x, int y, int width, int height) {
		super(x, y, width, height);
		for (int i = 0; i < 9; i++) {
			pattern[i] = new CircleZone(width / 24 + (i % 3) * width / 3, width / 24 + (i / 3)
					* width / 3, width / 4, width / 4, i);
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
		text(pass, 400, 400);
		if (pass.length() >= 5) {
			if ((pass.equals("67412") || pass.equals("21476")) && !accepted) {
				System.out.println("passcode accepted");
				accepted = true;
			}
		}
	}

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