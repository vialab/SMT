package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TabZone extends Zone {

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
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public TabZone(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public TabZone(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(Zone zone) {
		return this.add(zone, zone.name);
	}

	public boolean add(Zone zone, String tabName) {
		Tabs.put(new Tab(tabName, 0, 0, width / Tabs.keySet().size(), 50), zone);
		boolean result = super.add(zone);
		client.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		return result;
	}
	
	@Override
	public boolean remove(Zone zone){
		//if there is a tab for this zone remove it
		if(Tabs.containsValue(zone)){
			for(Tab key : Tabs.keySet()){
				if(Tabs.get(key).equals(zone)){
					Tabs.remove(key);
				}
			}
		}
		boolean result = super.remove(zone);
		client.grid(x, y, width, 0, 0, Tabs.keySet().toArray(new Zone[Tabs.keySet().size()]));
		return result;
	}
}
