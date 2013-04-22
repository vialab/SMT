//Based on Pie Menu Implementation by amnon.owed : https://forum.processing.org/topic/pie-menu-in-processing#25080000002077693

package vialab.SMT;

import java.util.ArrayList;
import java.util.Iterator;

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

		Slice(String text, PImage image, String name, Slice parent) {
			super(name);
			this.text = text;
			this.image = image;
			this.name = name;
			this.parent = parent;
		    this.children = new ArrayList<Slice>();
		}

		boolean warnDraw() {
			return false;
		}

		boolean warnTouch() {
			return false;
		}
	    
	    public void add(Slice child){
	    	this.children.add(child);
	    }
	    
	    public void remove(Slice child){
	    	children.remove(child);
	    }
	}

	private Slice sliceRoot = new Slice(null,null,null,null);

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
		Slice s = new Slice(text, image, name,sliceRoot);
		s.setBoundObject(this.getBoundObject());
		this.sliceRoot.add(s);
	}

	public void remove(String textName) {
		Iterator<Slice> i = sliceRoot.children.iterator();
		while(i.hasNext()) {
			if (i.next().name.equalsIgnoreCase(textName.replaceAll("\\s", ""))) {
				i.remove();
			}
		}
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

			float op = sliceRoot.children.size() / TWO_PI;
			for (int i = 0; i < sliceRoot.children.size(); i++) {
				float s = (i - 0.49f) / op;
				float e = (i + 0.49f) / op;
				if (selected == i) {
					fill(0, 0, 255);
				}
				else {
					fill(255);
				}
				arc(width / 2, height / 2, outerDiameter, outerDiameter, s, e);
			}

			fill(0);
			for (int i = 0; i < sliceRoot.children.size(); i++) {
				float m = i / op;
				int imageSize = (int) (PI * textdiam / (sliceRoot.children.size() + 1));
				if (sliceRoot.children.get(i).image != null) {
					image(sliceRoot.children.get(i).image, width / 2 + PApplet.cos(m) * textdiam, height / 2
							+ PApplet.sin(m) * textdiam, imageSize, imageSize);
				}
				else {
					imageSize = 0;
				}
				if (sliceRoot.children.get(i).text != null) {
					textSize(textdiam / (sliceRoot.children.size() + 1 + imageSize / 40));
					text(sliceRoot.children.get(i).text, width / 2 + PApplet.cos(m) * textdiam, height / 2
							+ PApplet.sin(m) * textdiam + imageSize / 2 + textAscent());
				}
			}

			fill(125);
			ellipse(width / 2, height / 2, innerDiameter, innerDiameter);
		}
	}

	@Override
	protected void pickDrawImpl() {
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
			float op = sliceRoot.children.size() / TWO_PI;

			selected = -1;
			// only select past the inner diameter
			if (touchVector.dist(getCentre()) > (innerDiameter / 2)) {
				for (int i = 0; i < sliceRoot.children.size(); i++) {
					float s = (i - 0.5f) / op;
					float e = (i + 0.5f) / op;
					if (piTheta >= s && (piTheta <= e || i == 0)) {
						selected = i;
					}
				}
			}
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	protected void pressImpl() {
		if (selected != -1) {
			sliceRoot.children.get(selected).pressInvoker();
		}
		else {
			// pressed in the middle of the menu, so hide it.
			this.setVisible(false);
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
}
