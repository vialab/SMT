package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;


import processing.core.PApplet;
import processing.core.PGraphics;
import TUIO.TuioCursor;

//import processing.core.PConstants;
//import javax.media.opengl.GL;
//use GLGraphics instead now
import codeanticode.glgraphics.*;

public class SMTZonePicker {
	private final int BG_PICK_COLOR = 255;
	private final int START_PICK_COLOR = 0;
	private final int PICK_COLOR_INC = 75;

	private int currentPickColor = START_PICK_COLOR;

	private GLGraphicsOffScreen pickBuffer;

	private PApplet applet;

	private Map<Integer, Zone> zonesByPickColor = Collections
			.synchronizedMap(new LinkedHashMap<Integer, Zone>());

	private SortedSet<Integer> activePickColors = new TreeSet<Integer>();

	PGraphics getGraphics(){
		return this.pickBuffer;
	}
	
	public SMTZonePicker() {
		this.applet = TouchClient.parent;
		initPickBuffer();
	}

	public void add(Zone zone) {
		// TODO: a uniform distribution would be ideal here
		zone.setPickColor(currentPickColor);
		//pickBuffer.beginDraw();
		zonesByPickColor.put(currentPickColor, zone);
		activePickColors.add(currentPickColor);
		//pickBuffer.endDraw();
		currentPickColor += PICK_COLOR_INC;
		currentPickColor %= 256;
		
		for(Zone child: zone.children){
			this.add(child);
		}
	}

	public boolean contains(Zone zone) {
		return zonesByPickColor.containsValue(zone);
	}

	public Zone remove(Zone zone) {
		activePickColors.remove(zone.getPickColor());
		return zonesByPickColor.remove(zone.getPickColor());
	}

	public Zone pick(TuioCursor t) {
		// if (idToZoneMap.containsKey(t.sessionID)) {
		// return idToZoneMap.get(t.sessionID);
		// }

		// for (Zone zone : zones) {
		// if (zone.isAssigned(t)) {
		// return zone;
		// }
		// }
		int screenX = t.getScreenX(TouchClient.parent.width);
		int screenY = t.getScreenY(TouchClient.parent.height);
		
		//new pixel color method that works when using GLGraphicsOffScreen
		int[] pixels = new int[applet.width*applet.height];
		pickBuffer.getTexture().getBuffer(pixels);
		//was upsidedown with some settings
		//int pickColor = pixels[(applet.height-screenY)*applet.width+screenX];
		int pickColor = pixels[(screenY)*applet.width+screenX];
		
				//old method that doesn't work with GLGraphicsOffscreen
				//pickBuffer.color(pickBuffer.get(screenX, screenY));
		//System.out.print(screenX+" "+screenY+" "+pickColor+" "+pickBuffer.color((float)BG_PICK_COLOR)+" ");
		
		if (pickColor == pickBuffer.color((float)BG_PICK_COLOR)) {
			return null;
		}
		else {
			try {
				int pickColorGray = 0x000000ff & pickColor;

				Zone zone = zonesByPickColor.get(pickColorGray);
				if (zone != null) {
					return zone;
				}

				int less = activePickColors.headSet(pickColorGray).last();
				int more = activePickColors.tailSet(pickColorGray).first();

				if (Math.abs(less - pickColorGray) < Math.abs(more - pickColorGray)) {
					return zonesByPickColor.get(less);
				}
				else {
					return zonesByPickColor.get(more);
				}
			}
			catch (NoSuchElementException e) {
				return null;
			}
		}

		// // check zones **layers matter--last zone created is on top (wins)
		// for (Zone zone : zones) {
		// if (zone != null && zone.contains(xScreen, yScreen)) {
		// idToZoneMap.put(id, zone);
		// return zone;
		// }
		// }
		// return null;
	}

	public void renderPickBuffer() {
		if (applet.g.width != pickBuffer.width || applet.g.height != pickBuffer.height) {
			initPickBuffer();
		}

		pickBuffer.beginDraw();
		pickBuffer.background(BG_PICK_COLOR);
		pickBuffer.endDraw();
		for (Zone zone : zonesByPickColor.values()) {
			if (zone.getParent() != null) {
				// the parent should handle the drawing
				continue;
			}
			//pickBuffer.pushMatrix();
			//pickBuffer.applyMatrix(zone.matrix);
			zone.drawForPickBuffer(pickBuffer);
			//pickBuffer.popMatrix();
		}
		//pickBuffer.endDraw();
	}

	private void initPickBuffer() {
		// pickBuffer = applet.createGraphics(applet.g.width, applet.g.height,
		// applet.g.getClass()
		// .getName());
		pickBuffer = new GLGraphicsOffScreen(applet,applet.g.width, applet.g.height);
		pickBuffer.noSmooth();
		pickBuffer.noLights();
		pickBuffer.noTint();
		// pickBuffer = applet.g;
	}

}
