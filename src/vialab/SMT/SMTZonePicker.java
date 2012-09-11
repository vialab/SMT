package vialab.SMT;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PConstants;
import processing.opengl.PGL;
import processing.opengl.PGraphics3D;

import TUIO.TuioCursor;

import javax.media.opengl.GL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;


public class SMTZonePicker {
	
	private class CustomPGraphicsOpenGL extends PGraphics3D{
		public CustomPGraphicsOpenGL(PApplet applet,int width, int height, String opengl) {
			super();
			this.setParent(applet);
			this.setPrimary(false);
			this.setSize(width, height);
		}

		void beginPixelRead(){
			super.beginPixelsOp(OP_READ);
		}
		
		void endPixelRead(){
			super.endPixelsOp();
		}
	}
	
	private static boolean BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;

	static int MAX_COLOR_VALUE = 0x00ffffff;

	private final int BG_PICK_COLOR = MAX_COLOR_VALUE;
	private final int START_PICK_COLOR = 0;
	// PICK_COLOR_INC needs a a value that is fairly large to tell the
	// difference between few zones by eye, and need to have a lcm(I,N)=IxN
	// where I is PICK_COLOR_INC and N is the max number of pickColors
	// lcm(I,N)=IxN means that the the pick color will only loop after being
	// added N times, and so assures that we use all pickColors
	// 74 is a valid solution for I when N is 2^8-1, 2^16-1, or 2^24-1
	private final int PICK_COLOR_INC = 74;

	private int currentPickColor = START_PICK_COLOR;

	private CustomPGraphicsOpenGL pickBuffer;

	private PApplet applet;

	private Map<Integer, Zone> zonesByPickColor = Collections
			.synchronizedMap(new LinkedHashMap<Integer, Zone>());

	private SortedSet<Integer> activePickColors = new TreeSet<Integer>();

	int SIZEOF_INT = Integer.SIZE / 8;
	IntBuffer buffer = ByteBuffer.allocateDirect(1 * 1 * SIZEOF_INT).order(ByteOrder.nativeOrder())
			.asIntBuffer();

	PGraphics getGraphics() {
		return this.pickBuffer;
	}

	public SMTZonePicker() {
		this.applet = TouchClient.parent;
		initPickBuffer();
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
			// TODO: a uniform distribution would be ideal here
			zone.setPickColor(currentPickColor);
			// pickBuffer.beginDraw();
			zonesByPickColor.put(currentPickColor, zone);
			activePickColors.add(currentPickColor);
			// pickBuffer.endDraw();
			do {
				currentPickColor += PICK_COLOR_INC;
				// mod 255 instead of mod 256, as to not choose background color
				currentPickColor %= MAX_COLOR_VALUE;
			} while (activePickColors.contains(currentPickColor)
					&& activePickColors.size() < MAX_COLOR_VALUE);

			for (Zone child : zone.children) {
				this.add(child);
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

		// new pixel read method that only reads the needed pixel for when using
		PGL pgl = pickBuffer.beginPGL();
		// bind FBO, read pixel, then unbind FBO
		pickBuffer.beginPixelRead();
		pgl.readPixels(screenX, screenY, 1, 1, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer);
		pickBuffer.endPixelRead();

		int pickColor = buffer.get(0);
		// System.out.println("gray "+(pickColor & 0xFF)+" raw "+pickColor);
		pickBuffer.endPGL();
		
		pickColor = nativeToJavaARGB(pickColor);

		/*
		 * //pixel color method that works when using GLGraphicsOffScreen, but
		 * causes slowdown due to reading all pixels //int[] pixels = new
		 * int[applet.width*applet.height];
		 * //pickBuffer.getTexture().getBuffer(pixels); //was upsidedown with
		 * some settings //int pickColor =
		 * pixels[(applet.height-screenY)*applet.width+screenX]; GLTexture
		 * tex=pickBuffer.getTexture(); tex.loadPixels(); tex.updateTexture();
		 * //pickBuffer.updatePixels(); int pickColor =
		 * tex.pixels[(screenY)*applet.width+screenX];
		 */
		// old method that doesn't work with GLGraphicsOffscreen
		// int pickColor = pickBuffer.color(pickBuffer.get(screenX, screenY));
		// System.out.print(screenX+" "+screenY+" "+pickColor+" "+pickBuffer.color((float)BG_PICK_COLOR)+" ");

		if (pickColor == pickBuffer.color((float) BG_PICK_COLOR)) {
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
		if (applet.g.width != pickBuffer.width || applet.g.height != pickBuffer.height) {
			initPickBuffer();
		}

		// rendering the pickGraphics is now separate from rendering the image
		// to the pickBuffer so non direct zones are first rendered into their
		// pickGraphics then later draw onto the pickBuffer
		for (Zone zone : zonesByPickColor.values()) {
			if (!zone.isDirect()) {
				zone.drawForPickBuffer(pickBuffer);
			}
		}

		pickBuffer.beginDraw();
		pickBuffer.background(BG_PICK_COLOR);
		pickBuffer.endDraw();
		for (Zone zone : zonesByPickColor.values()) {
			if (zone.getParent() != null) {
				// the parent should handle the drawing
				continue;
			}
			// zone does the matrix manipulation to place it self properly
			zone.drawForPickBuffer(pickBuffer);
		}
		
	}

	private void initPickBuffer() {
		// pickBuffer = applet.createGraphics(applet.g.width, applet.g.height,
		// applet.g.getClass()
		// .getName());
		pickBuffer = new CustomPGraphicsOpenGL(applet, applet.g.width, applet.g.height, PConstants.OPENGL);
		pickBuffer.noSmooth();
		pickBuffer.noLights();
		pickBuffer.noTint();
		// pickBuffer = applet.g;
	}

	public void putZoneOnTop(Zone zone) {
		zonesByPickColor.remove(zone.getPickColor());
		zonesByPickColor.put(zone.getPickColor(), zone);
	}

	/**
	 * Converts input native OpenGL value (RGBA on big endian, ABGR on little
	 * endian) to Java ARGB. Copied from processing 2.0 source code
	 */
	public static int nativeToJavaARGB(int color) {
		if (BIG_ENDIAN) { // RGBA to ARGB
			return (color & 0xff000000) | ((color >> 8) & 0x00ffffff);
		}
		else { // ABGR to ARGB
			return (color & 0xff000000) | ((color << 16) & 0xff0000) | (color & 0xff00)
					| ((color >> 16) & 0xff);
		}
	}
}
