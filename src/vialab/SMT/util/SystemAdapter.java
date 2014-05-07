package vialab.SMT.util;

//standard library imports
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import javax.swing.*;

//processing imports
import processing.core.*;

/**
 * A utility class for getting system and processing info.
 **/
public class SystemAdapter implements ComponentListener {

	//fields
	private boolean debug = true;
	private long last_update;

	//display fields
	private GraphicsEnvironment environment;
	private GraphicsDevice[] devices;
	private int device_count;
	//window fields
	private JFrame window;
	private JRootPane rootpane;
	private boolean window_update;
	//sketch fields
	private PApplet applet;

	//bounds
	private Rectangle[] display_bounds;
	private Rectangle screen_bounds;
	private Rectangle window_bounds;
	private Rectangle sketch_bounds;

	//constructor
	public SystemAdapter(){
		environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.window = null;
		this.rootpane = null;
		this.screen_bounds = new Rectangle();
		this.sketch_bounds = new Rectangle();
		this.window_bounds = new Rectangle();
		updateDisplays();
		resetLastUpdateTime();
	}
	public SystemAdapter( PApplet applet){
		this();
		connect( applet);
	}

	//functions
	void connect( PApplet applet){
		if( applet != null){
			this.applet = applet;
			this.window = (JFrame) applet.frame;
			this.rootpane = window.getRootPane();
			window.addComponentListener( this);
			updateWindow();
			updateSketch();
		}
		else {
			this.window = null;
			this.rootpane = null;
			this.window_bounds = new Rectangle();
			window.removeComponentListener( this);
			window_update = false;
		}
		resetLastUpdateTime();
	}

	public void update(){
		devices = environment.getScreenDevices();
		//this should detect monitor disconnects
		if( device_count != devices.length){
			updateDisplays();
			if( debug)
				System.out.printf(
					"Display update - count: %d\n", device_count);
		}
		if( window_update){
			updateWindow();
			updateSketch();
			if( debug)
				System.out.printf(
					"Window update - sketch: %d, %d :: %d, %d\n",
					sketch_bounds.x, sketch_bounds.y,
					sketch_bounds.width, sketch_bounds.height);
		}
	}
	private void updateDisplays(){
		devices = environment.getScreenDevices();
		device_count = devices.length;
		display_bounds = new Rectangle[ devices.length];
		//screen min/max coordinates
		int minx = 0;
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		//go through each device
		int i = 0;
		for( GraphicsDevice device : devices){
			GraphicsConfiguration config = device.getDefaultConfiguration();
			Rectangle bounds = config.getBounds();
			display_bounds[ i] = bounds;
			//stretch screen bounds
			int device_maxx = bounds.x + bounds.width;
			int device_maxy = bounds.y + bounds.height;
			if( device_maxx > maxx)
				maxx = device_maxx;
			if( device_maxy > maxy)
				maxy = device_maxy;
			if( bounds.x < minx)
				minx = bounds.x;
			if( bounds.y < miny)
				miny = bounds.y;
		}
		//set screen bounds
		screen_bounds.x = minx;
		screen_bounds.y = miny;
		screen_bounds.width = maxx - minx;
		screen_bounds.height = maxy - miny;
		resetLastUpdateTime();
	}
	private void updateWindow(){
		window_bounds = window.getBounds();
		window_update = false;
		resetLastUpdateTime();
	}
	private void updateSketch(){
		int content_x = window.getX() + rootpane.getX();
		int content_y = window.getY() + rootpane.getY();
		int sketch_borderx = ( rootpane.getWidth() - applet.width) / 2;
		int sketch_bordery = ( rootpane.getHeight() - applet.height) / 2;
		int sketch_x = content_x + sketch_borderx;
		int sketch_y = content_y + sketch_bordery;
		sketch_bounds.x = sketch_x;
		sketch_bounds.y = sketch_y;
		sketch_bounds.width = applet.width;
		sketch_bounds.height = applet.height;
		resetLastUpdateTime();
	}
	private void resetLastUpdateTime(){
		last_update = System.currentTimeMillis();
	}

	//accessors
	public Rectangle getDisplayBounds( int index){
		if( index >= 0 || index < display_bounds.length)
			return display_bounds[ index];
		else throw new ArrayIndexOutOfBoundsException( index);
	}
	public Rectangle getDisplayBounds( String id){
		for( int i = 0; i < devices.length; i++)
			if( devices[i].getIDstring().equals( id))
				return display_bounds[ i];
		throw new NoSuchElementException(
			String.format(
				"Could not find a display with the id %s", id));
	}
	public int getDisplayCount(){
		return device_count;
	}
	public GraphicsDevice[] getDisplays(){
		return devices;
	}

	public Rectangle getScreenBounds(){
		return screen_bounds;
	}
	public Rectangle getSketchBounds(){
		return sketch_bounds;
	}
	//other accessors
	public long getLastUpdateTime(){
		return last_update;
	}

	//window listener functions
	@Override
	public void componentHidden( ComponentEvent event){}
	public void componentMoved( ComponentEvent event){
		window_update = true;
	}
	public void componentResized( ComponentEvent event){
		window_update = true;
	}
	public void componentShown( ComponentEvent event){}
}