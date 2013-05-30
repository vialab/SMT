package vialab.SMT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.opengl.PGL;

import java.nio.ByteBuffer;

import javax.media.opengl.GL;

class SMTZonePicker {
	private final static int BG_PICK_COLOR = 0x00ffffff;
	private final int START_PICK_COLOR = 0;
	// PICK_COLOR_INC needs a a value that is fairly large to tell the
	// difference between few zones by eye, and need to have a lcm(I,N)=IxN
	// where I is PICK_COLOR_INC and N is the max number of pickColors
	// lcm(I,N)=IxN means that the the pick color will only loop after being
	// added N times, and so assures that we use all pickColors
	// 74 is a valid solution for I when N is 2^8-1, 2^16-1, or 2^24-1
	private final int PICK_COLOR_INC = 74;

	private int currentPickColor = START_PICK_COLOR;

	private PApplet applet;

	private Map<Integer, Zone> zonesByPickColor = Collections
			.synchronizedMap(new LinkedHashMap<Integer, Zone>());

	private SortedSet<Integer> activePickColors = new TreeSet<Integer>();

	int SIZEOF_INT = Integer.SIZE / 8;

	public SMTZonePicker() {
		this.applet = TouchClient.parent;

		// add the background color mapping to null
		zonesByPickColor.put(BG_PICK_COLOR, null);
	}

	public void add(Zone zone) {
		if (activePickColors.size() == BG_PICK_COLOR) {
			// This means every color from 0 to BG_PICK_COLOR-1 has been used,
			// although this should not really occur in use
			System.err.println("Warning, added zone is unpickable, maximum is " + BG_PICK_COLOR
					+ " pickable zones");
		}
		else {
			if (!zonesByPickColor.containsValue(zone)) {
				zone.setPickColor(currentPickColor);
				zonesByPickColor.put(currentPickColor, zone);
				activePickColors.add(currentPickColor);

				do {
					currentPickColor += PICK_COLOR_INC;
					// mod by max/background color, so as to wrap around and
					// never reach it
					currentPickColor %= BG_PICK_COLOR;
				} while (activePickColors.contains(currentPickColor)
						&& activePickColors.size() < BG_PICK_COLOR);

				for (Zone child : zone.children) {
					this.add(child);
				}
			}
		}
	}

	public boolean contains(Zone zone) {
		return zonesByPickColor.containsValue(zone);
	}

	public Zone remove(Zone zone) {
		for (Zone child : zone.children) {
			this.remove(child);
		}
		activePickColors.remove(zone.getPickColor());
		Zone removed = zonesByPickColor.remove(zone.getPickColor());
		zone.setPickColor(-1);
		return removed;
	}

	public Zone pick(Touch t) {
		int pickColor = -1;

		// prevent ArrayOutOfBoundsException, although maybe this should be done
		// in Touch itself
		int x = t.x;
		int y = t.y;
		if (t.y >= applet.g.height) {
			y = applet.g.height - 1;
		}
		if (t.x >= applet.g.width) {
			x = applet.g.width - 1;
		}
		if (t.y < 0) {
			y = 0;
		}
		if (t.x < 0) {
			x = 0;
		}

		PGL pgl = applet.g.beginPGL();
		// force fallback until 2.0b10
		if (!TouchClient.fastPicking || pgl == null || System.getProperty("os.name").equals("Mac OS X")) {
			// really slow way(max 70 fps on a high end card vs 200+ fps with
			// readPixels), with loadPixels at the end of renderPickBuffer()
			pickColor = applet.g.pixels[x + y * applet.g.width] & 0x00FFFFFF;
		}
		else {
			ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1 * SIZEOF_INT);

			pgl.readPixels(t.x, TouchClient.parent.height - t.y, 1, 1, GL.GL_RGBA,
					GL.GL_UNSIGNED_BYTE, buffer);

			// get the first three bytes
			int r = buffer.get() & 0xFF;
			int g = buffer.get() & 0xFF;
			int b = buffer.get() & 0xFF;
			pickColor = (r << 16) + (g << 8) + (b);
			buffer.clear();
		}

		applet.g.endPGL();

		if (zonesByPickColor.containsKey(pickColor)) {
			// if mapped it is either a Zone or null (background)
			return zonesByPickColor.get(pickColor);
		}
		else {
			// only show error in debug mode, since it is much to prevalent
			// still to always show
			if (TouchClient.drawTouchPoints == TouchDraw.DEBUG) {
				// not mapped means a bug in the pickDrawn colors, or maybe that
				// BG_PICK_COLOR or a Zone got unmapped when it should'nt have
				System.err
						.println("PickColor: "
								+ pickColor
								+ " doesn't match any known Zone's pickColor or the background, this indicates it was unmapped when it shouldn't be, or an incorrect color was drawn to the pickBuffer.");
			}
			return null;
		}
	}

	public void renderPickBuffer() {
		// make sure colorMode is correct for the pickBuffer
		applet.g.colorMode(PConstants.RGB, 255);
		applet.g.background(BG_PICK_COLOR, 255);
		ArrayList<Zone> toDraw = new ArrayList<Zone>();
		for (Zone zone : TouchClient.zoneList) {
			if (zone.getParent() == null) {
				toDraw.add(zone);
			}
		}
		// first extract order of all Zones to be drawn, then actually draw
		// them, so that re-parenting, etc can occur in a draw, and we do not
		// get ConcurrentModificationException
		for (Zone zone : toDraw) {
			zone.drawForPickBuffer();
		}
		applet.g.flush();
		
		// force fallback until 2.0b10
		// really slow way(max 70 fps on a high end card vs 200+ fps with
		// readPixels), with loadPixels at the end of renderPickBuffer()
		PGL pgl = applet.g.beginPGL();
		if (!TouchClient.fastPicking || pgl == null || System.getProperty("os.name").equals("Mac OS X")) {
			applet.g.loadPixels();
		}
		applet.g.endPGL();
	}
}
