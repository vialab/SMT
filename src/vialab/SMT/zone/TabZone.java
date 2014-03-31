package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * TabZone is a container for other Zones, it has tabs along its top, allowing
 * easy switching between different views. It optionally has close buttons on the tabs.
 * Currently, each zone added to TabZone becomes its own Tab, and so to have
 * multiple Zones within a tab, use another zone as a container and add that zone to TabZone.
 */
public class TabZone extends Zone {

	private int TAB_HEIGHT;
	private boolean closeButtons;
	Tab currentTab;

	boolean warnDraw() {
		return false;
	}

	boolean warnTouch() {
		return false;
	}

	private class CloseButton extends ButtonZone {

		public CloseButton(int x, int y, int w, int h) {
			this(null, x, y, w, h);
		}

		public CloseButton(String name, int x, int y, int w, int h) {
			super(name, x, y, w, h);
			setDirect(true);
		}

		@Override
		public void touchUpImpl(Touch touch) {
			if (isButtonDown()) {
				TabZone.this.remove(Tabs.get((Tab) this.getParent()));
			}
			super.touchUpImpl(touch);
		}

		@Override
		public void drawImpl() {
			if (!isButtonDown()) {
				fill(255, 0, 0);
				rect(0, 0, width, height);
				stroke(255);
				line(0, 0, width, height);
				line(width, 0, 0, height);
			}
			else {
				fill(100);
				rect(0, 0, width, height);
				stroke(255);
				line(0, 0, width, height);
				line(width, 0, 0, height);
			}
		}
	}

	private class Tab extends ButtonZone {
		CloseButton close;

		public Tab(String tabName, int x, int y, int w, int h, String tabText) {
			super(tabName, x, y, w, h, tabText);
			if (closeButtons) {
				close = new CloseButton((int) (w - h * 0.9), (int) (h * 0.1), (int) (h * 0.8),
						(int) (h * 0.8));
				this.add(close);
			}
		}

		@Override
		public void pressImpl(Touch t) {
			tabActive(this);
		}

		@Override
		public void setSize(int w, int h) {
			super.setSize(w, h);
			if (closeButtons) {
				this.close.setData((int) (w - h * 0.9), (int) (h * 0.1), (int) (h * 0.8),
						(int) (h * 0.8));
			}
		}
	}

	private void tabActive(Tab tab) {
		// make sure no other tabs will be drawn
		for (Zone child : getChildren()) {
			super.remove(child);
		}
		super.add(Tabs.get(tab));
		this.currentTab = tab;
	}

	private Map<Tab, Zone> Tabs = Collections.synchronizedMap(new LinkedHashMap<Tab, Zone>());

	
    public TabZone() {
		this(false);
	}

	public TabZone(String name) {
		this(name, false);
		// TODO Auto-generated constructor stub
	}

	public TabZone(int x, int y, int width, int height) {
		this(x, y, width, height, false);
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, 50, false);
	}

	public TabZone(String name, int x, int y, int width, int height, int tabHeight) {
		this(name, x, y, width, height, tabHeight, false);
	}

	public TabZone(boolean closeButtons) {
		this(0, 0, SMT.getApplet().width, SMT.getApplet().height, closeButtons);
	}

	public TabZone(String name, boolean closeButtons) {
		this(name, 0, 0, SMT.getApplet().width, SMT.getApplet().height, closeButtons);
		// TODO Auto-generated constructor stub
	}

	public TabZone(int x, int y, int width, int height, boolean closeButtons) {
		this(null, x, y, width, height, closeButtons);
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name, int x, int y, int width, int height, boolean closeButtons) {
		this(name, x, y, width, height, 50, closeButtons);
	}

    /**
	 * @param name    - String: The name of the zone
     * @param x       - int: X-coordinate of the upper left corner of the zone
	 * @param y       - int: Y-coordinate of the upper left corner of the zone
	 * @param width   - int: Width of the zone
	 * @param height  - int: Height of the zone
	 * @param tabHeight - int: The height of the tab
     * @param closeButtons - boolean: Display close/exit buttons for tabs
     *
     */
	public TabZone(String name, int x, int y, int width, int height, int tabHeight,
			boolean closeButtons) {
		super(name, x, y, width, height);
		this.TAB_HEIGHT = tabHeight;
		this.closeButtons = closeButtons;
	}

	/**
	 * This adds the Given zone to a tab, the Tabs name will be Tab1, Tab2, etc
	 * depending on the number of current Tabs. This will fail badly when used
	 * with remove(Zone), so this method should not be used. The text displayed
	 * on the tab will be the tabname (Tab1,Tab2,etc) and the Zone's name and
	 * toString().
	 * 
	 * @param zone     - Zone: Zone to add to the TabZone
	 * @return Whether the zone was added
	 */
	@Override
	public boolean add(Zone zone) {
		return this.add(zone, "Tab" + (Tabs.size() + 1), "Tab" + (Tabs.size() + 1) + ": "
				+ zone.name + "::" + zone.toString());
	}

	/**
	 * This adds the Given zone to a tab, with the given nameText used for both
	 * reflection methods and the name displayed on the tab itself.
	 * 
	 * @param zone  - Zone: Zone to add to the TabZone
	 * @param tabName - String: The Tab name for use in reflection methods: drawNAME(), 
     *               touchNAME(), etc, if tabName="NAME". This name will be displayed on the Tab
	 * @return Whether the zone was added
	 */
	public boolean add(Zone zone, String tabName) {
		return add(zone, tabName, tabName);
	}

	/**
	 * This adds the Given zone to a tab, with the given tabName and tabText
	 * 
	 * @param zone
	 * @param tabName
	 *            The Tab name for use in reflection methods: drawTabName(),
	 *            touchTabName(), etc
	 * @param tabText
	 *            The text to display on the Tab
	 * @return Whether the zone was added
	 */
	public boolean add(Zone zone, String tabName, String tabText) {
		// resize all tabs to equal size with constant all tabs size
		for (Tab tab : Tabs.keySet()) {
			tab.setSize(width / (Tabs.keySet().size() + 1), TAB_HEIGHT);
		}
		Tab tab = new Tab(tabName, 0, 0, width / (Tabs.keySet().size() + 1), TAB_HEIGHT, tabText);
		Tabs.put(tab, zone);
		SMT.add(tab);
		boolean result = super.add(zone);
		SMT.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		tabActive(tab);
		return result;
	}

    /**
     * Used to remove a tab from the TabZone.
     * 
     * @param zone  - Zone: The zone to remove
     */
	@Override
	public boolean remove(Zone zone) {
		// if there is a tab for this zone remove it
		if (Tabs.containsValue(zone)) {
			// avoid concurrent modification exception by removing after finding
			// the tabs
			LinkedList<Tab> tabsToRemove = new LinkedList<Tab>();
			for (Tab key : Tabs.keySet()) {
				if (Tabs.get(key).equals(zone)) {
					tabsToRemove.add(key);
					SMT.remove(key);
				}
			}
			for (Tab tab : tabsToRemove) {
				Tabs.remove(tab);
			}
		}
		// resize all tabs to equal size with constant all tabs size
		for (Tab tab : Tabs.keySet()) {
			tab.setSize(width / Tabs.keySet().size(), TAB_HEIGHT);
		}
		boolean result = super.remove(zone);
		SMT.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		// avoid negative array location, and instead set currentTab to null
		// when no more tabs
		if (Tabs.keySet().size() >= 1) {
			tabActive(Tabs.keySet().toArray(new Tab[Tabs.size()])[Tabs.size() - 1]);
		}
		else {
			currentTab = null;
		}
		return result;
	}

    /**
     * Used to override what is drawn into this zone
     */
	@Override
	public void drawImpl() {
		fill(125);
		rect(0, 0, width, height);
	}
}
