package vialab.SMT.util;

//standard library imports
import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.*;
import javax.media.opengl.GL;

//processing imports
import processing.core.*;
import processing.opengl.*;

//local imports
import vialab.SMT.*;
import vialab.SMT.renderer.*;

public class ZonePicker {
	//constants
	private final static int BACKGROUND_COLOR = 0;
	private final static int START_COLOR = 0x000000;
	private final static int MAX_COLOR = 0xffffff;
	private final static int POSSIBLE_COLORS =
		( MAX_COLOR - START_COLOR) + 1;
	
	//fields
	private ByteBuffer buffer;
	private int currentColor;
	public PGraphics3D picking_context;
	private Map<Integer, Zone> zoneMap =
		Collections.synchronizedMap(
			new LinkedHashMap<Integer, Zone>());
	private P3DDSRenderer renderer;

	public ZonePicker() {
		renderer = SMT.getRenderer();
		this.picking_context = (PGraphics3D) SMT.getApplet().createGraphics(
			renderer.width, renderer.height, PConstants.P3D);
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

		for( Zone child : zone.getChildren())
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

	public Zone pick( int x, int y) {
		//clamp x and y
		if( y >= picking_context.height)
			y = picking_context.height - 1;
		if( x >= picking_context.width)
			x = picking_context.width - 1;
		if( y < 0)
			y = 0;
		if( x < 0)
			x = 0;

		PGL pgl = picking_context.beginPGL();
		int pixel;
		// force fallback until 2.0b10
		if( ! SMT.fastPickingEnabled() || pgl == null)
			//really slow way(max 70 fps on a high end card vs 200+ fps with readPixels), with loadPixels at the end of render()
			pixel = picking_context.pixels[ x + y * picking_context.width];
		else {
			buffer.clear();
			pgl.readPixels(
				x, picking_context.height - y,
				1, 1, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
				buffer);
			pixel = buffer.getInt();
		}
		picking_context.endPGL();

		if( zoneMap.containsKey( pixel)) {
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
		else return null;
	}

	public void render(){
		//set up for rendering the pick buffer
		renderer.pushDelegate( picking_context);
		renderer.beginDraw();
		renderer.clear();
		//render the pick buffer
		SMT.getRootZone().invokePickDraw();
		renderer.endDraw();
		renderer.flush();
		// If fast picking disabled, use loadPixels() which is really slow (max 70 fps on a high end card vs 200+ fps with readPixels) as a backup.
		PGL pgl = renderer.beginPGL();
		if ( ! SMT.fastPickingEnabled() || pgl == null)
			renderer.loadPixels();
		renderer.endPGL();
		renderer.popDelegate();
	}
}
