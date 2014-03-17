package vialab.SMT;

import java.awt.Color;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;

import java.nio.ByteBuffer;

import javax.media.opengl.GL;

public class SMTZonePicker {
	//fields
	public PGraphicsOpenGL pg;
	private Map<Integer, Zone> zoneMap =
		Collections.synchronizedMap(
			new LinkedHashMap<Integer, Zone>());
	private ByteBuffer buffer;
	
	private final static int BACKGROUND_COLOR = 0;
	private final static int START_COLOR = 0x000000;
	private final static int MAX_COLOR = 0xffffff;
	private final static int POSSIBLE_COLORS =
		( MAX_COLOR - START_COLOR) + 1;

	private int currentColor;


	public SMTZonePicker() {
		PApplet applet = SMT.parent;
		this.pg = ( PGraphicsOpenGL) applet.createGraphics(
			applet.width, applet.height, PConstants.OPENGL);
		int SIZEOF_INT = Integer.SIZE / 8;
		buffer = ByteBuffer.allocateDirect( SIZEOF_INT);
		currentColor = START_COLOR;
	}

	public void add(Zone zone) {
		//check if we already have this zone
		if( zoneMap.containsValue( zone))
			return;

		if( zoneMap.size() == POSSIBLE_COLORS){
			//We've run out of pick colours :( Maybe look into using the alpha
			System.err.printf(
				"The number of zones has exceeded the maximum number of pickable zones (%d). This recently added zone (%s) will not be pickable.",
				POSSIBLE_COLORS, zone);
			return;
		}

		zone.setPickColor( new Color( currentColor, false));
		int pixelColor = 0xff + ( currentColor << 8);
		zoneMap.put( pixelColor, zone);

		//dont bother searching if we're out of colours anyways
		if( zoneMap.size() < POSSIBLE_COLORS){
			while( zoneMap.containsKey( pixelColor)){
				currentColor += 1;
				pixelColor = 0xff + ( currentColor << 8);
			}
		}

		for( Zone child : zone.children)
			this.add( child);
	}

	public boolean contains( Zone zone) {
		return zoneMap.containsValue( zone);
	}

	public Zone remove( Zone zone) {
		Color color = zone.getPickColor();
		int pixelColor = color.getAlpha() + ( color.getRGB() << 8);
		Zone removed = zoneMap.remove( pixelColor);
		zone.setPickColor( null);
		return removed;
	}

	public Zone pick( Touch touch) {
		// prevent ArrayOutOfBoundsException, although maybe this should be done in Touch itself
		int x = touch.x;
		int y = touch.y;
		if( touch.y >= pg.height)
			y = pg.height - 1;
		if( touch.x >= pg.width)
			x = pg.width - 1;
		if(touch.y < 0)
			y = 0;
		if(touch.x < 0)
			x = 0;

		PGL pgl = pg.beginPGL();
		// force fallback until 2.0b10
		int pixel;
		if ( ! SMT.fastPicking || pgl == null){
			//really slow way(max 70 fps on a high end card vs 200+ fps with readPixels), with loadPixels at the end of renderPickBuffer()
			pixel = pg.pixels[ x + y * pg.width];}
		else {
			buffer.clear();
			pgl.readPixels(
				touch.x, pg.height - touch.y,
				1, 1, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
				buffer);
			pixel = buffer.getInt();
		}
		pg.endPGL();
		//System.out.printf( "pixel: %h\n", pixel);

		//find the zone for that colour
		if( zoneMap.containsKey( pixel)){
			// if mapped it is either a Zone or null (background)
			Zone picked =  zoneMap.get( pixel);
			Zone current = picked;
			while( current != null){
				if( current.stealChildrensTouch)
					return current;
				current = current.getParent();
			}
			return picked;
		}
		else
			return null;
	}

	public void renderPickBuffer() {
		SMT.sketch.drawForPickBuffer();
		System.out.println( SMT.parent.g);
		pg.flush();
		// If fast picking disabled, use loadPixels() which is really slow (max 70 fps on a high end card vs 200+ fps with readPixels) as a backup
		PGL pgl = pg.beginPGL();
		if( ! SMT.fastPicking || pgl == null)
			pg.loadPixels();
		pg.endPGL();
	}
}
