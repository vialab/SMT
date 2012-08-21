package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class TabZone extends Zone {

	private int TAB_HEIGHT;
	private boolean closeButtons;
	Tab currentTab;

	private class CloseButton extends ButtonZone {

		public CloseButton(int x, int y, int w, int h) {
			this(null, x, y, w, h);
		}

		public CloseButton(String name, int x, int y, int w, int h) {
			super(name, x, y, w, h);
			setDirect(true);
		}

		@Override
		public void touchUp(Touch touch) {
			if (isButtonDown()) {
				TabZone.this.remove(Tabs.get((Tab) this.getParent()));
			}
			super.touchUp(touch);
		}

		@Override
		public void draw() {
			super.beginDraw();
			if (!isButtonDown()) {
				fill(255, 0, 0);
				rect(0, 0, width, height);
				stroke(255);
				line(0, 0, width, height);
				line(width, 0, 0, height);
			}
			else {
				fill(0);
				rect(0, 0, width, height);
				stroke(255);
				line(0, 0, width, height);
				line(width, 0, 0, height);
			}
			super.endDraw();
			// super.draw();
		}
	}

	private class Tab extends ButtonZone {
		CloseButton close;

		public Tab(String tabName, int x, int y, int w, int h) {
			super(tabName, x, y, w, h, tabName);
			if (closeButtons) {
				close = new CloseButton((int) (w - h * 0.9), (int) (h * 0.1), (int) (h * 0.8),
						(int) (h * 0.8));
				this.add(close);
			}
		}

		@Override
		public void touchDown(Touch touch) {
			if (!isButtonDown()) {
				tabActive(this);
			}
			super.touchDown(touch);
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
		for (Zone zone : this.Tabs.values()) {
			client.remove(zone);
		}
		client.add(Tabs.get(tab));
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
		this(0, 0, applet.width, applet.height, closeButtons);
	}

	public TabZone(String name, boolean closeButtons) {
		this(name, 0, 0, applet.width, applet.height, closeButtons);
		// TODO Auto-generated constructor stub
	}

	public TabZone(int x, int y, int width, int height, boolean closeButtons) {
		this(null, x, y, width, height, closeButtons);
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name, int x, int y, int width, int height, boolean closeButtons) {
		this(name, x, y, width, height, 50, closeButtons);
	}

	public TabZone(String name, int x, int y, int width, int height, int tabHeight,
			boolean closeButtons) {
		super(name, x, y, width, height);
		this.TAB_HEIGHT = tabHeight;
		this.closeButtons = closeButtons;
	}

	@Override
	public boolean add(Zone zone) {
		if (zone.name == null) {
			return this.add(zone, zone.toString());
		}
		return this.add(zone, zone.name + "::" + zone.toString());
	}

	public boolean add(Zone zone, String tabName) {
		// resize all tabs to equal size with constant all tabs size
		for (Tab tab : Tabs.keySet()) {
			tab.setSize(width / (Tabs.keySet().size() + 1), TAB_HEIGHT);
		}
		Tab tab = new Tab(tabName, 0, 0, width / (Tabs.keySet().size() + 1), TAB_HEIGHT);
		Tabs.put(tab, zone);
		client.add(tab);
		zone.setDirect(true);
		boolean result = super.add(zone);
		client.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		tabActive(tab);
		return result;
	}

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
					client.remove(key);
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
		client.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
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

	@Override
	public void draw() {
		super.beginDraw();
		fill(125);
		rect(0, 0, width, height);
		super.endDraw();
		super.draw();
	}
}
