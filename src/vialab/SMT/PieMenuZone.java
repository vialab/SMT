//Based on Pie Menu Implementation by amnon.owed : https://forum.processing.org/topic/pie-menu-in-processing#25080000002077693

package vialab.SMT;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Pie menu user interface widget. 
 * Creates a pie menu GUI element.
 *
 */
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
		float textSize;

		Slice(String text, PImage image, String name, Slice parent){
			super(name);
			this.text = text;
			this.image = image;
			this.name = name;
			this.parent = parent;
			this.children = new ArrayList<Slice>();
			sliceList.add(this);
		}

		@Override
		protected void pickDrawImpl(){
			// make sure we do not pickDraw, as we leave that to the PieMenuZone
		}

		@Override
		protected void drawImpl(){
			if (isVisible()){
				int imageSize = (width + height) / 2;
				if (image != null){
					image(image, 0, 0, width, height);
				}
				else if ( method_draw == null){
					// only assume no image if both our own PImage is null and
					// method_draw is not implemented
					imageSize = 0;
				}
				if (text != null){
					if (disabled){
						fill(150);
					}
					else {
						fill(0);
					}
					textAlign(CENTER);
					textSize(textSize);
					text(text, width / 2, height / 2 + imageSize / 2 + textAscent() + 1);
				}
			}
		}

		boolean warnTouch(){
			return false;
		}

		public float animationPercentage(long currentTime){
			if (removalTime != null){
				return 1 - (currentTime - removalTime) / animationTime;
			}
			else if (currentTime - addTime < animationTime){
				return (currentTime - addTime) / animationTime;
			}
			else {
				return 1;
			}
		}
	}

	private long currentTime;

	public float animationTime = 1000;

	private ArrayList<Slice> currentSlices = new ArrayList<Slice>();

	private ArrayList<Slice> sliceList = new ArrayList<Slice>();

	private Slice topRoot = new Slice(null, null, null, null);

	private Slice sliceRoot = topRoot;

	private int outerDiameter;

	private int innerDiameter;

	private int selected = -1;

	private boolean visible = true;

		/**
		 * @param name    - String: The name of the zone
		 * @param outerdiameter      - int: Inner diameter of the Pie Menu
		 * @param x       - int: X-coordinate of the upper left corner of the zone
		 * @param y       - int: Y-coordinate of the upper left corner of the zone
		 *
		 */
	public PieMenuZone(String name, int outerdiameter, int x, int y){
		this(name, outerdiameter, 50, x, y);
	}

		/**
		 * @param name    - String: The name of the zone
		 * @param outerDiameter      - int: Inner diameter of the Pie Menu
		 * @param innerDiameter      - int: Outer diameter of the Pie Menu
		 * @param x       - int: X-coordinate of the upper left corner of the zone
		 * @param y       - int: Y-coordinate of the upper left corner of the zone
		 *
		 */
	public PieMenuZone(String name, int outerDiameter, int innerDiameter, int x, int y){
		super(name, x - outerDiameter / 2, y - outerDiameter / 2, outerDiameter, outerDiameter);
		this.outerDiameter = outerDiameter;
		this.innerDiameter = innerDiameter;
	}

	/**
	 * [add description]
	 * @param textName [description]
	 */
	public void add(String textName){
		add(textName, (PImage) null);
	}

	/**
	 * [add description]
	 * @param text [description]
	 * @param name [description]
	 */
	public void add(String text, String name){
		add(text, null, name);
	}

	/**
	 * [add description]
	 * @param textName [description]
	 * @param image    [description]
	 */
	public void add(String textName, PImage image){
		add(textName, image, textName.replaceAll("\\s", ""));
	}

	/**
	 * [add description]
	 * @param text  [description]
	 * @param image [description]
	 * @param name  [description]
	 */
	public void add(String text, PImage image, String name){
		Slice s = new Slice(text, image, name, topRoot);
		s.setBoundObject(this.getBoundObject());
		this.topRoot.children.add(s);
		s.addTime = System.currentTimeMillis();
		this.currentSlices.add(s);
		super.add(s);
	}

	/**
	 * [addSubmenu description]
	 * @param parent [description]
	 * @param textName [description]
	 */
	public void addSubmenu(String parent, String textName){
		addSubmenu(parent, textName, (PImage) null);
	}
	/**
	 * [addSubmenu description]
	 * @param parent [description]
	 * @param text [description]
	 * @param name [description]
	 */
	public void addSubmenu(String parent, String text, String name){
		addSubmenu(text, null, name);
	}
	/**
	 * [addSubmenu description]
	 * @param parent   [description]
	 * @param textName [description]
	 * @param image    [description]
	 */
	public void addSubmenu(String parent, String textName, PImage image){
		addSubmenu(parent, textName, image, textName.replaceAll("\\s", ""));
	}

	/**
	 * [addSubmenu description]
	 * @param parent [description]
	 * @param text [description]
	 * @param image [description]
	 * @param name [description]
	 */
	public void addSubmenu(String parent, String text, PImage image, String name){
		Slice p = getSliceFromName(parent);
		if (p != null){
			Slice s = new Slice(text, image, name, p);
			s.setBoundObject(this.getBoundObject());
			p.children.add(s);
			s.addTime = System.currentTimeMillis();
		}
		else {
			System.err.println("PieMenuZone.addSubmenu: No slice named: " + parent
					+ " was found, addSubmenu failed");
		}
	}

	/**
	 * [remove description]
	 * @param textName [description]
	 */
	public void remove(String textName){
		if (textName != null){
			Slice s = getSliceFromName(textName);
			remove(s);
		}
	}

	/**
	 * [remove description]
	 * @param  z [description]
	 * @return   [description]
	 */
	@Override
	public boolean remove(Zone z){
		if (z instanceof Slice){
			Slice s = (Slice) z;
			// only set removal time, instead of actually removing the slice, as
			// the animation for slice removal still needs to occur
			s.removalTime = System.currentTimeMillis();
			s.parent.children.remove(s);
			return sliceList.remove(s);
		}
		return super.remove(z);
	}

	/**
	 * [finishRemove description]
	 * @param s [description]
	 */
	private void finishRemove(Slice s){
		if (selected >= 0 && selected < currentSlices.size()
				&& currentSlices.get(selected).equals(s)){
			selected = -1;
		}
		currentSlices.remove(s);
		// remove from Zone children, but only if it is not already removed (another remove would give a warning)
		if(children.contains(s)){
			super.remove(s);
		}
	}

	public void setDisabled(String name, boolean disabled){
		Slice s = this.getSliceFromName(name);
		if (s != null){
			s.disabled = disabled;
		}
	}

	private Slice getSliceFromName(String name){
		for (Slice s : sliceList){
			if ((s.name == null && name == null)
					|| (s.name != null && s.name.equalsIgnoreCase(name.replaceAll("\\s", "")))){
				return s;
			}
		}
		return null;
	}

	public int getDiameter(){
		return (int) (2 * PVector.sub(getOrigin(), getCentre()).mag());
	}

	protected void drawImpl(){
		if (isVisible()){
			noStroke();

			float textdiam = (outerDiameter + innerDiameter) / 3.65f;

			fill(125);
			ellipse(width / 2, height / 2, outerDiameter + 3, outerDiameter + 3);

			float sliceTotal = 0;
			for (Slice s : currentSlices){
				sliceTotal += s.animationPercentage(currentTime);
			}

			float op = currentSlices.size() / TWO_PI;
			float ss = TWO_PI / sliceTotal;
			float c = 0;

			for (int i = 0; i < currentSlices.size(); i++){
				Slice sl = currentSlices.get(i);
				float sizeFactor = sl.animationPercentage(currentTime);
				float s = c;
				float e = s + sizeFactor * ss;
				float m = (s + e) / 2;

				if (selected == i){
					fill(0, 0, 255);
				}
				else if (sl.disabled){
					fill(200);
				}
				else {
					fill(255);
				}

				if (!sl.children.isEmpty()){
					fill(200);
					if (sl.disabled){
						fill(150);
					}
					arc(width / 2, height / 2, outerDiameter, outerDiameter, s + 0.01f / op, e
							- 0.01f / op);
					fill(255, 0, 0);
					if (sl.disabled){
						fill(200);
					}
					triangle(width / 2 + PApplet.cos(m + (-0.05f / op)) * (outerDiameter / 2)
							* 0.9f, height / 2 + PApplet.sin(m + ((-0.05f) / op))
							* (outerDiameter / 2) * 0.9f, width / 2 + PApplet.cos(m)
							* (outerDiameter / 2) * 0.95f, height / 2 + PApplet.sin(m)
							* (outerDiameter / 2) * 0.95f,
							width / 2 + PApplet.cos(m + (0.05f / op)) * (outerDiameter / 2) * 0.9f,
							height / 2 + PApplet.sin(m + (0.05f / op)) * (outerDiameter / 2) * 0.9f);

					if (selected == i){
						fill(0, 0, 255);
					}
					else {
						fill(255);
					}
					if (sl.disabled){
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
				sl.setLocation(width / 2 - sl.width / 2 + PApplet.cos(m) * textdiam, height / 2
						- sl.height / 2 + PApplet.sin(m) * textdiam);
				sl.width = imageSize;
				sl.height = imageSize;
				sl.textSize = (sizeFactor * ss * textdiam - imageSize / 2) / 7;
				c += sizeFactor * ss;
			}

			fill(125);
			ellipse(width / 2, height / 2, innerDiameter, innerDiameter);
		}
	}

	@Override
	protected void pickDrawImpl(){
		// current time is incremented once per frame at the start of pickDraw
		// to be consistent
		currentTime = System.currentTimeMillis();
		ArrayList<Slice> toRemove = new ArrayList<Slice>();
		// before drawing, we should make sure to remove Slices that are past
		// their expiration time
		for (Slice s : currentSlices){
			if (s.removalTime != null && currentTime - s.removalTime > animationTime){
				toRemove.add(s);
			}
		}
		for (Slice s : toRemove){
			this.finishRemove(s);
		}

		if (isVisible()){
			ellipse(width / 2, height / 2, outerDiameter, outerDiameter);
		}
	}

	@Override
	protected void touchImpl(){
		Touch t = getActiveTouch(0);
		if (t != null && isVisible()){
			PVector touchVector = new PVector(t.x, t.y);
			PVector touchInZone = toZoneVector(touchVector);
			float mouseTheta = PApplet.atan2(touchInZone.y - height / 2, touchInZone.x - width / 2);
			float piTheta = mouseTheta >= 0 ? mouseTheta : mouseTheta + TWO_PI;

			selected = -1;

			float sliceTotal = 0;
			for (Slice s : currentSlices){
				sliceTotal += s.animationPercentage(currentTime);
			}
			float ss = TWO_PI / sliceTotal;
			float c = 0;

			for (int i = 0; i < currentSlices.size(); i++){
				Slice sl = currentSlices.get(i);
				float sizeFactor = sl.animationPercentage(currentTime);
				float s = c;
				float e = s + sizeFactor * ss;
				// only select past the inner diameter
				if (touchVector.dist(getCentre()) > (innerDiameter / 2)){
					if (piTheta >= s && piTheta < e){
						selected = i;
					}
				}
				c += sizeFactor * ss;
			}

			if (selected == -1){
				if (sliceRoot.parent != null){
					// transition menu back to parent
					sliceRoot = sliceRoot.parent;
					transitionToSliceRoot();
				}
			}
			else {
				if (currentSlices.get(selected).disabled){
					selected = -2;
				}
				else if (currentSlices.get(selected).removalTime != null){
					// removalTime is set, therefore it is in the process of
					// being removed, so do not allow selection
					selected = -3;
				}
				else if (!currentSlices.get(selected).children.isEmpty()
						&& touchVector.dist(getCentre()) > (outerDiameter / 2) * 0.85f){
					// transition menu to child
					sliceRoot = currentSlices.get(selected);
					transitionToSliceRoot();
				}
			}
		}
	}

	private void transitionToSliceRoot(){
		for (Slice s : currentSlices){
			// set removal time for current slices
			s.removalTime = currentTime;
		}
		for (Slice s : sliceRoot.children){
			// add slices to current, make sure to null out removalTime to reset
			// slices
			s.addTime = currentTime;
			s.removalTime = null;
			if (!currentSlices.contains(s)){
				currentSlices.add(s);
				super.add(s);
			}
		}
	}

	public boolean isVisible(){
		return visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
		if (!visible){
			reset();
			// dont leave any child draws behind when hiding
			for (Slice s : currentSlices){
				super.remove(s);
			}
		}
		else {
			// make sure to restore child draws when making visible
			for (Slice s : currentSlices){
				super.add(s);
			}
		}
	}

	@Override
	protected void touchUpImpl(Touch t){
		if (selected >= 0 && selected < currentSlices.size()){
			Slice s = currentSlices.get(selected);
			if (!s.disabled){
				// invoke both touchUp and press methods for the Slice zones, as
				// either can be used
				s.invokeTouchUpMethod(t);
				s.invokePressMethod(t);
				if (!s.children.isEmpty()){
					// make the selected Slice the root of the tree if it has
					// children, for a submenu.
					sliceRoot = s;
					transitionToSliceRoot();
					selected = -1;
				}
			}
		}
		else if (selected == -1){
			if (sliceRoot.parent == null){
				// pressed in the middle of the menu, so hide it.
				this.setVisible(false);
			}
			else {
				// pressed in middle of a submenu, so go back
				sliceRoot = sliceRoot.parent;
				transitionToSliceRoot();
			}
		}
		else {
			// disabled, do nothing
		}
	}

	/**
	 * @return The name of the selected slice of the PieMenuZone, or null if
	 *         none are selected, which also occurs whenever a submenu
	 *         transition takes place
	 */
	public String getSelectedName(){
		try {
			return currentSlices.get(selected).name;
		}
		catch (Exception e){
			return null;
		}
	}

	/**
	 * [setBoundObject description]
	 * @param obj [description]
	 */
	public void setBoundObject(Object obj){
		super.setBoundObject(obj);
		for (Slice s : sliceRoot.children){
			s.setBoundObject(obj);
		}
	}

	/**
	 * [reset description]
	 */
	public void reset(){
		sliceRoot = topRoot;
		transitionToSliceRoot();
		selected = -1;
	}
}
