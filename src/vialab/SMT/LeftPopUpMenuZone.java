package vialab.SMT;

/**
 * LeftPopUpMenuZone is a popup menu that appears as a red triangle pointing to
 * the left, that when pressed shows a menu with the buttons that were specified
 * by name only, in the constructor of this Zone
 */
public class LeftPopUpMenuZone extends Zone {
	private MenuZone menu;

	/**
	 * @param x      - int: X-coordinate of the upper left corner of the zone
	 * @param y      - int: Y-coordinate of the upper left corner of the zone
	 * @param width  - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param mWidth  - int: Width of the menu
	 * @param mHeight - int: Height of the menu
	 * @param menuButtonNames - String...: The names of the menu buttons
	 */
	public LeftPopUpMenuZone(int x, int y, int width, int height, int mWidth, int mHeight,
			String... menuButtonNames) {
		super(x, y, width, height);
		menu = new MenuZone(-mWidth, (-mHeight / 2) + height / 2, mWidth, mHeight);
		int i = 0;
		for (String name : menuButtonNames) {
			int yButton = mHeight / 20 + i * ((mHeight * 9 / 10) / menuButtonNames.length);
			if (i != 0) {
				yButton += ((mHeight / 10) / menuButtonNames.length - 1);
			}
			if (menuButtonNames.length == 1) {
				yButton += mHeight / 20;
			}
			int hButton = (mHeight * 8 / 10) / menuButtonNames.length;
			menu.add(new ButtonZone(name.replace(" ", ""), mWidth / 20, yButton, mWidth * 9 / 10,
					hButton, name, hButton / 3 + 10));
			i++;
		}
	}

	protected void pressImpl(Touch t) {
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

		protected void touchImpl() {
		}
	}
}
