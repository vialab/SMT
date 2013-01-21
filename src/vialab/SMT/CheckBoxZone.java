package vialab.SMT;

public class CheckBoxZone extends Zone {
	public CheckBoxZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
	}

	public boolean checked = false;

	protected void drawImpl() {
		fill(255);
		rect(0, 0, width, height, 10);
		if (checked) {
			drawCheckmark();
		}
	}

	protected void drawCheckmark() {
		stroke(0);
		strokeWeight(5);
		line(width, 0, width / 3, height);
		line(width / 3, height, 0, (float) (height * 2. / 3.));
	}

	@Override
	protected void touchUpImpl(Touch t) {
		checked = !checked;
	}
}