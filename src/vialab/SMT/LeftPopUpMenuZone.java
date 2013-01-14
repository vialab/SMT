package vialab.SMT;

public class LeftPopUpMenuZone extends Zone {
	private MenuZone menu;

	public LeftPopUpMenuZone(int x, int y, int width, int height, int mwidth, int mheight,
			String... menuButtonNames) {
		super(x, y, width, height);
		menu = new MenuZone(-mwidth, (-mheight / 2) + height / 2, mwidth, mheight);
		int i = 0;
		for (String name : menuButtonNames) {
			int yButton = mheight / 20 + i * ((mheight * 9 / 10) / menuButtonNames.length);
			if (i != 0) {
				yButton += ((mheight / 10) / menuButtonNames.length - 1);
			}
			if (menuButtonNames.length == 1) {
				yButton += mheight / 20;
			}
			int hButton = (mheight * 8 / 10) / menuButtonNames.length;
			menu.add(new ButtonZone(name.replace(" ", ""), mwidth / 20, yButton, mwidth * 9 / 10,
					hButton, name, hButton / 3 + 10));
			i++;
		}
	}

	protected void touchUpImpl(Touch t) {
		if (children.contains(menu)) {
			remove(menu);
		}
		else {
			add(menu);
		}
	}

	protected void touchImpl() {
	}

	protected void drawImpl() {
		fill(255, 0, 0);
		triangle(0, height / 2, width, 0, width, height);
	}

	private class MenuZone extends Zone {
		MenuZone(int x, int y, int width, int height) {
			super(x, y, width, height);
		}

		protected void drawImpl() {
			fill(125);
			rect(0, 0, width, height);
		}
	}
}
