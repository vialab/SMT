package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import processing.core.PApplet;
import processing.opengl.PGL;

import java.nio.ByteBuffer;

import javax.media.opengl.GL;

class SMTZonePicker {

	private static int MAX_COLOR_VALUE = 0x00ffffff;

	private final int BG_PICK_COLOR = 0xffffffff;
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
		// image=new PImage(applet.width,applet.height);
		if (SMTZonePicker.MAX_COLOR_VALUE == 255) {
			Zone.grayscale = true;
		}
	}

	public void add(Zone zone) {
		if (activePickColors.size() == MAX_COLOR_VALUE) {
			System.err.println("Warning, added zone is unpickable, maximum is " + MAX_COLOR_VALUE
					+ " pickable zones");
		}
		else {
			if (!zonesByPickColor.containsValue(zone)) {
				// TODO: a uniform distribution would be ideal here
				zone.setPickColor(currentPickColor);
				// applet.g.beginDraw();
				zonesByPickColor.put(currentPickColor, zone);
				activePickColors.add(currentPickColor);
				// applet.g.endDraw();
				do {
					currentPickColor += PICK_COLOR_INC;
					// mod 255 instead of mod 256, as to not choose background
					// color
					currentPickColor %= MAX_COLOR_VALUE;
				} while (activePickColors.contains(currentPickColor)
						&& activePickColors.size() < MAX_COLOR_VALUE);

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

		if (System.getProperty("os.name").equals("Mac OS X")) {
			// Fall back to the working but slow method of getting the pixel
			// color on Mac
			pickColor = applet.g.get(t.x, t.y);
		}
		else {
			PGL pgl = applet.g.beginPGL();

			if (pgl == null) {
				System.err.print("GL not available, picking failed");
				return null;
			}

			ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1 * SIZEOF_INT);

			pgl.readPixels(t.x, TouchClient.parent.height - t.y, 1, 1, GL.GL_RGBA,
					GL.GL_UNSIGNED_BYTE, buffer);

			// get the first three bytes
			int r = buffer.get() & 0xFF;
			int g = buffer.get() & 0xFF;
			int b = buffer.get() & 0xFF;
			pickColor = (r << 16) + (g << 8) + (b);

			buffer.clear();

			applet.g.endPGL();
		}

		// System.out.println("pickColor"+pickColor+ "r" + r + "g"
		// + g + "b" + b);

		/*
		 * //pixel color method that works when using GLGraphicsOffScreen, but
		 * causes slowdown due to reading all pixels //int[] pixels = new
		 * int[applet.width*applet.height];
		 * //applet.g.getTexture().getBuffer(pixels); //was upsidedown with some
		 * settings //int pickColor =
		 * pixels[(applet.height-screenY)*applet.width+screenX]; GLTexture
		 * tex=applet.g.getTexture(); tex.loadPixels(); tex.updateTexture();
		 * //applet.g.updatePixels(); int pickColor =
		 * tex.pixels[(screenY)*applet.width+screenX];
		 */
		// old method that doesn't work with GLGraphicsOffscreen
		// int pickColor = applet.g.color(applet.g.get(screenX, screenY));
		// System.out.print(screenX+" "+screenY+" "+pickColor+" "+applet.g.color((float)BG_PICK_COLOR)+" ");

		if (pickColor == BG_PICK_COLOR) {
			return null;
		}
		else {
			try {
				int pickColorGray = MAX_COLOR_VALUE & pickColor;

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
		applet.g.background(BG_PICK_COLOR);
		for (Zone zone : zonesByPickColor.values()) {
			if (zone.getParent() != null) {
				// the parent should handle the drawing
				continue;
			}
			// zone does the matrix manipulation to place it self properly
			zone.drawForPickBuffer();
		}
		// image=applet.g.get();
		applet.g.flush();
	}

	public void putZoneOnTop(Zone zone) {
		zonesByPickColor.remove(zone.getPickColor());
		zonesByPickColor.put(zone.getPickColor(), zone);
	}
}
