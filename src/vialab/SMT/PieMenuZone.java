//Based on Pie Menu Implementation by amnon.owed : https://forum.processing.org/topic/pie-menu-in-processing#25080000002077693

package vialab.SMT;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class PieMenuZone extends Zone {

	private class Slice extends Zone {
		private String text;
		private PImage image;
		private String name;
		private Slice parent;
		private ArrayList<Slice> children;
		public boolean disabled = false;
		public Long removalTime;
		public long addTime;

		Slice(String text, PImage image, String name, Slice parent) {
			super(name);
			this.text = text;
			this.image = image;
			this.name = name;
			this.parent = parent;
			this.children = new ArrayList<Slice>();
			sliceList.add(this);
		}

		boolean warnDraw() {
			return false;
		}

		boolean warnTouch() {
			return false;
		}

		public float animationPercentage(long currentTime) {
			if (removalTime != null) {
				return 1 - (currentTime - removalTime) / animationTime;
			}
			else if (currentTime - addTime < animationTime) {
				return (currentTime - addTime) / animationTime;
			}
			else {
				return 1;
			}
		}
	}
	private long currentTime;

	private float animationTime = 500;

	private ArrayList<Slice> sliceList = new ArrayList<Slice>();

	private Slice sliceRoot = new Slice(null, null, null, null);

	private int outerDiameter;

	private int innerDiameter;

	private int selected = -1;

	private boolean visible = true;

	public PieMenuZone(String name, int outerdiameter, int x, int y) {
		this(name, outerdiameter, 50, x, y);
	}

	public PieMenuZone(String name, int outerDiameter, int innerDiameter, int x, int y) {
		super(name, x - outerDiameter / 2, y - outerDiameter / 2, outerDiameter, outerDiameter);
		this.outerDiameter = outerDiameter;
		this.innerDiameter = innerDiameter;
	}

	public void add(String textName) {
		add(textName, (PImage) null);
	}

	public void add(String text, String name) {
		add(text, null, name);
	}

	public void add(String textName, PImage image) {
		add(textName, image, textName.replaceAll("\\s", ""));
	}

	public void add(String text, PImage image, String name) {
		Slice s = new Slice(text, image, name, sliceRoot);
		s.setBoundObject(this.getBoundObject());
		this.sliceRoot.children.add(s);
		s.addTime = System.currentTimeMillis();
	}

	public void addSubmenu(String parent, String textName) {
		addSubmenu(parent, textName, (PImage) null);
	}

	public void addSubmenu(String parent, String text, String name) {
		addSubmenu(text, null, name);
	}

	public void addSubmenu(String parent, String textName, PImage image) {
		addSubmenu(parent, textName, image, textName.replaceAll("\\s", ""));
	}

	public void addSubmenu(String parent, String text, PImage image, String name) {
		Slice backupRoot = sliceRoot;
		if (getSliceFromName(parent) != null) {
			this.sliceRoot = getSliceFromName(parent);
			add(text, image, name);
		}
		else {
			System.err.println("PieMenuZone.addSubmenu: No slice named: " + parent
					+ " was found, addSubmenu failed");
		}
		sliceRoot = backupRoot;
	}

	public void remove(String textName) {
		if (textName != null) {
			Slice s = getSliceFromName(textName);
			// only set removal time, instead of actually removing the slice, as
			// the animation for slice removal still needs to occur
			s.removalTime = System.currentTimeMillis();
		}
	}

	private void actualRemove(Slice s) {
		if (selected >=0 && sliceRoot.children.get(selected).equals(s)) {
			selected = -1;
		}
		// this actually removes the slice by taking away the reference from it
		// parent
		s.parent.children.remove(s);
		sliceList.remove(s);
	}

	public void setDisabled(String name, boolean disabled) {
		Slice s = this.getSliceFromName(name);
		if (s != null) {
			s.disabled = disabled;
		}
	}

	private Slice getSliceFromName(String name) {
		for (Slice s : sliceList) {
			if ((s.name == null && name == null)
					|| (s.name != null && s.name.equalsIgnoreCase(name.replaceAll("\\s", "")))) {
				return s;
			}
		}
		return null;
	}

	public int getDiameter() {
		return (int) (2 * PVector.sub(getOrigin(), getCentre()).mag());
	}

	protected void drawImpl() {
		if (isVisible()) {
			textAlign(CENTER, CENTER);
			imageMode(CENTER);
			noStroke();

			float textdiam = (outerDiameter + innerDiameter) / 3.65f;

			fill(125);
			ellipse(width / 2, height / 2, outerDiameter + 3, outerDiameter + 3);

			float sliceTotal = 0;
			for (Slice s : sliceRoot.children) {
				sliceTotal += s.animationPercentage(currentTime);
			}

			float op = sliceRoot.children.size() / TWO_PI;
			float ss = TWO_PI / sliceTotal;
			float c = 0;

			for (int i = 0; i < sliceRoot.children.size(); i++) {
				Slice sl = sliceRoot.children.get(i);
				float sizeFactor = sl.animationPercentage(currentTime);
				float s = c;
				float e = s + sizeFactor * ss;
				float m = (s + e) / 2;

				if (selected == i) {
					fill(0, 0, 255);
				}
				else if (sl.disabled) {
					fill(200);
				}
				else {
					fill(255);
				}

				if (!sl.children.isEmpty()) {
					fill(200);
					if (sl.disabled) {
						fill(150);
					}
					arc(width / 2, height / 2, outerDiameter, outerDiameter, s + 0.01f / op, e
							- 0.01f / op);
					fill(255, 0, 0);
					if (sl.disabled) {
						fill(200);
					}
					triangle(width / 2 + PApplet.cos(m + (-0.05f / op)) * (outerDiameter / 2)
							* 0.9f, height / 2 + PApplet.sin(m + ((-0.05f) / op))
							* (outerDiameter / 2) * 0.9f, width / 2 + PApplet.cos(m)
							* (outerDiameter / 2) * 0.95f, height / 2 + PApplet.sin(m)
							* (outerDiameter / 2) * 0.95f,
							width / 2 + PApplet.cos(m + (0.05f / op)) * (outerDiameter / 2) * 0.9f,
							height / 2 + PApplet.sin(m + (0.05f / op)) * (outerDiameter / 2) * 0.9f);

					if (selected == i) {
						fill(0, 0, 255);
					}
					else {
						fill(255);
					}
					if (sl.disabled) {
						fill(200);
					}
					arc(width / 2, height / 2, outerDiameter * 0.85f, outerDiameter * 0.85f, s
							+ 0.01f / op, e - 0.01f / op);
				}
				else {
					arc(width / 2, height / 2, outerDiameter, outerDiameter, s + 0.01f / op, e
							- 0.01f / op);
				}
				
				int imageSize = (int) (sizeFactor * ss * textdiam * 0.4f);
				if (sl.image != null) {
					image(sl.image, width / 2 + PApplet.cos(m) * textdiam,
							height / 2 + PApplet.sin(m) * textdiam, imageSize, imageSize);
				}
				else {
					imageSize = 0;
				}
				if (sl.text != null) {
					if (sl.disabled) {
						fill(150);
					}
					else {
						fill(0);
					}
					textSize((sizeFactor * ss * textdiam - imageSize / 2) / 7);
					text(sl.text, width / 2 + PApplet.cos(m) * textdiam,
							height / 2 + PApplet.sin(m) * textdiam + imageSize / 2 + textAscent());
				}
				c += sizeFactor * ss;
			}

			fill(125);
			ellipse(width / 2, height / 2, innerDiameter, innerDiameter);
		}
	}

	@Override
	protected void pickDrawImpl() {
		//current time is incremented once per frame at the start of pickDraw to be consistent
		currentTime = System.currentTimeMillis();
		ArrayList<Slice> toRemove = new ArrayList<Slice>();
		// before drawing, we should make sure to remove Slices that are past
		// their expiration time, as we have no timer to do so
		for (Slice s : sliceList) {
			if (s.removalTime != null && currentTime - s.removalTime > animationTime) {
				toRemove.add(s);
			}
		}
		for (Slice s : toRemove) {
			this.actualRemove(s);
		}
		
		if (isVisible()) {
			ellipse(width / 2, height / 2, outerDiameter, outerDiameter);
		}
	}

	@Override
	protected void touchImpl() {
		Touch t = getActiveTouch(0);
		if (t != null && isVisible()) {
			PVector touchVector = new PVector(t.x, t.y);
			PVector touchInZone = toZoneVector(touchVector);
			float mouseTheta = PApplet.atan2(touchInZone.y - height / 2, touchInZone.x - width / 2);
			float piTheta = mouseTheta >= 0 ? mouseTheta : mouseTheta + TWO_PI;

			selected = -1;
			
			float sliceTotal = 0;
			for (Slice s : sliceRoot.children) {
				sliceTotal += s.animationPercentage(currentTime);
			}
			float ss = TWO_PI / sliceTotal;
			float c = 0;

			for (int i = 0; i < sliceRoot.children.size(); i++) {
				Slice sl = sliceRoot.children.get(i);
				float sizeFactor = sl.animationPercentage(currentTime);
				float s = c;
				float e = s + sizeFactor * ss;
				// only select past the inner diameter
				if (touchVector.dist(getCentre()) > (innerDiameter / 2)) {
					if(piTheta>=s&&piTheta<e){
						selected = i;
					}
				}
				c+=sizeFactor * ss;
			}

			if (selected == -1) {
				if (sliceRoot.parent != null) {
					sliceRoot = sliceRoot.parent;
				}
			}
			else {
				if (sliceRoot.children.get(selected).disabled) {
					selected = -2;
				}
				else if (!sliceRoot.children.get(selected).children.isEmpty()
						&& touchVector.dist(getCentre()) > (outerDiameter / 2) * 0.85f) {
					sliceRoot = sliceRoot.children.get(selected);
				}
			}
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (!visible) {
			reset();
		}
	}

	@Override
	protected void touchUpImpl(Touch t) {
		if (selected >= 0 && selected < sliceRoot.children.size()) {
			Slice s = sliceRoot.children.get(selected);
			if (!s.disabled) {
				//invoke both touchUp and press methods for the Slice zones, as either can be used
				s.pressInvoker();
				s.touchUpInvoker(t);
				if (!s.children.isEmpty()) {
					// make the selected Slice the root of the tree if it has
					// children, for a submenu.
					sliceRoot = s;
					selected = -1;
				}
			}
		}
		else if (selected == -1) {
			if (sliceRoot.parent == null) {
				// pressed in the middle of the menu, so hide it.
				this.setVisible(false);
			}
			else {
				// pressed in middle of a submenu, so go back
				sliceRoot = sliceRoot.parent;
			}
		}
		else {
			// disabled, do nothing
		}
	}

	/**
	 * @return The name of the selected slice of the PieMenuZone, or null if
	 *         none are selected
	 */
	public String getSelectedName() {
		try {
			return sliceRoot.children.get(selected).name;
		}
		catch (Exception e) {
			return null;
		}
	}

	public void setBoundObject(Object obj) {
		super.setBoundObject(obj);
		for (Slice s : sliceRoot.children) {
			s.setBoundObject(obj);
		}
	}

	public void reset() {
		sliceRoot = getSliceFromName(null);
	}
}
