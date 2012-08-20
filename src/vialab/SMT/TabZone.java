package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TabZone extends Zone {

	private int TAB_HEIGHT;
	Tab currentTab;

	private class Tab extends ButtonZone {
		public Tab(String tabName, int x, int y, int w, int h) {
			super(tabName, x, y, w, h);
		}

		@Override
		public void touchDown(Touch touch) {
			if (!isButtonDown()) {
				tabActive(this);
			}
			super.touchDown(touch);
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
		this(0, 0, applet.width, applet.height);
	}

	public TabZone(String name) {
		this(name, 0, 0, applet.width, applet.height);
		// TODO Auto-generated constructor stub
	}

	public TabZone(int x, int y, int width, int height) {
		this(null, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, 50);
	}

	public TabZone(String name, int x, int y, int width, int height, int tabHeight) {
		super(name, x, y, width, height);
		this.TAB_HEIGHT = tabHeight;
	}

	@Override
	public boolean add(Zone zone) {
		if (zone.name == null) {
			return this.add(zone, zone.toString());
		}
		return this.add(zone, zone.name + zone.toString());
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
			for (Tab key : Tabs.keySet()) {
				if (Tabs.get(key).equals(zone)) {
					Tabs.remove(key);
					client.remove(key);
				}
			}
		}
		// resize all tabs to equal size with constant all tabs size
		for (Tab tab : Tabs.keySet()) {
			tab.setSize(width / Tabs.keySet().size(), TAB_HEIGHT);
		}
		boolean result = super.remove(zone);
		client.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		tabActive(Tabs.keySet().toArray(new Tab[Tabs.size()])[Tabs.size() - 1]);
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
