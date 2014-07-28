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
	private boolean debug = false;
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
	/**
	 * Create a new system adapter that is not bound to a sketch
	 */
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
	/**
	 * Create a new system adapter that is bound to the specified sketch
	 * @param  applet the applet to connect to
	 */
	public SystemAdapter( PApplet applet){
		this();
		connect( applet);
	}

	//functions
	/**
	 * Connect this adapter to a processing applet's window
	 * @param applet the processing applet
	 */
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

	/**
	 * Update the adapter's information to reflect the current display layout and sketch dimensions
	 */
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
	/**
	 * Update the adapter's display layout information
	 */
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
			display_bounds[ i++] = bounds;
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
	/**
	 * Update the adapter's window bounds information
	 */
	private void updateWindow(){
		window_bounds = window.getBounds();
		window_update = false;
		resetLastUpdateTime();
	}
	/**
	 * Update the adapter's sketch bounds information
	 */
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
	/**
	 * Set the last update time to now
	 */
	private void resetLastUpdateTime(){
		last_update = System.currentTimeMillis();
	}

	//accessors
	/**
	 * Get the bounds of the display that the sketch is currently on.
	 * @return the bounds of the display that the sketch is currently on
	 */
	public Rectangle getActiveDisplayBounds(){
		String active_id = getActiveDisplayID();
		return getDisplayBounds( active_id);
	}
	/**
	 * Get the index of the active display in the display array
	 * @return the index of the currently active display
	 */
	public int getActiveDisplayIndex(){
		String active_id = getActiveDisplayID();
		return getDisplayIndex( active_id);
	}
	/**
	 * Get the id of the active display
	 * @return the id of the active display
	 */
	public String getActiveDisplayID(){
		GraphicsDevice active_device =
			window.getGraphicsConfiguration().getDevice();
		return active_device.getIDstring();
	}
	/**
	 * Get the bounds of the display at the specified index in the display list.
	 * @param  index the index of the display
	 * @return the bounds of the display at the specified index in the display list
	 */
	public Rectangle getDisplayBounds( int index){
		if( index >= 0 || index < display_bounds.length)
			return display_bounds[ index];
		else throw new ArrayIndexOutOfBoundsException( index);
	}
	/**
	 * Get the bounds of the display with the specified id string.
	 * @param  id the id string of the display
	 * @return the bounds of the display with the specified id string
	 */
	public Rectangle getDisplayBounds( String id){
		int index = getDisplayIndex( id);
		return display_bounds[ index];
	}
	/**
	 * Get the bounds of all displays.
	 * @return the bounds of all displays
	 */
	public Rectangle[] getDisplayBounds(){
		return display_bounds;
	}
	/**
	 * Get the number of displays
	 * @return the number of displays
	 */
	public int getDisplayCount(){
		return device_count;
	}

	/**
	 * Get the ID string of the display at the specified index
	 * @param index the index of the display
	 * @return the ID string of the display at the specified index
	 */
	public String getDisplayID( int index){
		if( index >= 0 || index < display_bounds.length)
		return devices[ index].getIDstring();
		else
			throw new ArrayIndexOutOfBoundsException( index);
	}
	/**
	 * Get the IDs of every display
	 * @return an array containing every display's ID string
	 */
	public String[] getDisplayIDs(){
		String[] result = new String[ devices.length];
		for( int i = 0; i < devices.length; i++)
			result[i] = devices[i].getIDstring();
		return result;
	}
	/**
	 * Get the index of the display with the specified id string
	 * @param id the ID string of the display
	 * @return the index of the display
	 */
	public int getDisplayIndex( String id){
		for( int i = 0; i < devices.length; i++)
			if( devices[i].getIDstring().equals( id))
				return i;
		throw new NoSuchElementException(
			String.format(
				"Could not find a display with the id %s", id));
	}

	/**
	 * Get the display at the specified index in the display list.
	 * @param  index the index of the desired display
	 * @return the display at the specified index in the display list
	 */
	public GraphicsDevice getDisplay( int index){
		return devices[ index];
	}
	/**
	 * Get the display with the specified id string.
	 * @param  id the id string of the desired display
	 * @return the display with the specified id string
	 */
	public GraphicsDevice getDisplay( String id){
		for( GraphicsDevice device : devices)
			if( device.getIDstring().equals( id))
				return device;
		return null;
	}
	/**
	 * Get all the displays.
	 * @return all the displays
	 */
	public GraphicsDevice[] getDisplays(){
		return devices;
	}

	/**
	 * Get the bounds of the entire screen.
	 * @return the bounds of the entire screen.
	 */
	public Rectangle getScreenBounds(){
		return screen_bounds;
	}
	/**
	 * Get the bounds of the connected applet's sketch
	 * @return the bounds of the connected applet's sketch
	 */
	public Rectangle getSketchBounds(){
		return sketch_bounds;
	}
	//other accessors
	/**
	 * Get the time at which the adapter last updated
	 * @return the time at which the adapter last updated
	 */
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