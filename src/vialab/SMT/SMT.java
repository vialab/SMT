/*
 * Modified version of the TUIO processing library - part of the reacTIVision
 * project http://reactivision.sourceforge.net/
 * 
 * Copyright (c) 2005-2009 Martin Kaltenbrunner <mkalten@iua.upf.edu>
 * 
 * This version Copyright (c) 2011 Erik Paluka, Christopher Collins - University
 * of Ontario Institute of Technology Mark Hancock - University of Waterloo
 * contact: christopher.collins@uoit.ca
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License Version 3 as published by the
 * Free Software Foundation.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package vialab.SMT;

//standard library imports
import java.awt.Point;
import java.awt.Rectangle;
import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

//jbox2d imports
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

//libtuio imports
import TUIO.*;

//processing imports
import processing.core.*;
import processing.opengl.*;

//local imports
import vialab.SMT.renderer.*;
import vialab.SMT.util.*;

/**
 * The Core class of the SMT library, it provides the TUIO/Touch Processing client
 * implementation, specifies the back-end source of Touches via TouchSource, and
 * the SMT library cannot be used without it. Use this class to
 * add/remove zones from the sketch/application.
 * 
 * @author Erik Paluka
 * @author Zach Cook
 * @version 4.0
 */
public class SMT {
	public static boolean debug = false;
	static float box2dScale = 0.1f;
	static World world;
	protected static int MAX_PATH_LENGTH = 50;
	protected static LinkedList<Process> tuioServerList =
		new LinkedList<Process>();

	// list of tuio clients
	protected static LinkedList<TuioClient> tuioClientList =
		new LinkedList<TuioClient>();

	/** Flag for drawing touch points */
	private static TouchDraw touchDrawMethod = TouchDraw.TEXTURED;

	protected static Zone sketch;
	protected static PApplet applet;
	//picker is temporarily public for debugging purposes.
	public static ZonePicker picker;
	protected static SMTTuioListener listener;
	protected static SMTTouchManager manager;
	private static SystemAdapter systemAdapter = null;;
	protected static Method touch;
	protected static Boolean warnUnimplemented;
	protected static P3DDSRenderer renderer = null;

	/**
	 * This controls whether we give a warning for uncalled methods which use
	 * one of the reserved method prefixes (draw,pickDraw,touch,etc and any that
	 * have been added by calls to SMTUtilities.getZoneMethod() with a unique
	 * prefix).
	 * 
	 * Default state is on.
	 */
	public static boolean warnUncalledMethods = true;

	public static final String RENDERER = P3DDSRenderer.class.getName();
	public static final String zone_renderer = PConstants.P3D;

	//default SMT.init() parameters
	public static final int default_port = 3333;
	public static final TouchSource default_touchsource =
		TouchSource.AUTOMATIC;

	//TUIO adapters
	protected static AndroidToTUIO att = null;
	protected static MouseToTUIO mtt = null;

	protected static LinkedList<BufferedReader> tuioServerErrList =
		new LinkedList<BufferedReader>();
	protected static LinkedList<BufferedReader> tuioServerOutList =
		new LinkedList<BufferedReader>();
	protected static LinkedList<BufferedWriter> tuioServerInList =
		new LinkedList<BufferedWriter>();

	protected static Body groundBody;
	protected static boolean fastPicking = true;
	protected static Map<Integer, TouchSource> deviceMap =
		Collections.synchronizedMap(
			new LinkedHashMap<Integer, TouchSource>());
	private static EnumMap<TouchSource, TouchBinder> touchBinders =
		new EnumMap<TouchSource, TouchBinder>( TouchSource.class);
	private static TouchSource[] sources_notmouse = new TouchSource[]{
		TouchSource.TUIO_DEVICE, TouchSource.WM_TOUCH,
		TouchSource.SMART, TouchSource.LEAP};

	protected static int mainListenerPort;
	protected static boolean inShutdown = false;
	public static File smt_tempdir = null;
	protected static int zone_count = 0;
	protected static float zonedraw_deltaz = 0.5f;
	protected static int zonedraw_i = 0;

	// utility fields for the touch drawing methods
	private static TexturedTouchDrawer texturedTouchDrawer = null;
	private static TouchDrawer customTouchDrawer = null;
	protected static float touch_radius = 15;
	protected static int touch_sections = 24;

	//processing revision numbers
	private static int p203_revision = 221;
	private static int p21_revision = 223;
	private static int p211_revision = 224;
	private static int p212_revision = 225;
	private static int p22_revision = 226;
	private static int p221_revision = 227;
	//supported processing versions
	private static int revision_unknown = -1;
	private static int revision_min = p211_revision;
	private static String revision_min_name = "2.1.1";
	private static String revision_min_build = "0224";
	private static int revision_max = revision_unknown;
	private static String revision_max_name = "Unknown";
	private static String revision_max_build = "unknown";
	//supported processing version override
	public static boolean pversion_override = false;

	//SMT version information
	public static final int revision = 20;
	public static final String version = "4.1";
	public static final String version_pretty = "SMT 4.1";

	/**
	 * Prevent SMT initialization (kinda) with protected constructor
	 */
	protected SMT(){}

	/**
	 * Initializes SMT, begining listening to a TUIO source on the
	 * default port of 3333 and using automatic touch source selection.
	 * 
	 * @param applet The Processing PApplet, usually just 'this' when using the Processing IDE
	 */
	public static void init( PApplet applet){
		init( applet, default_port, default_touchsource);
	}

	/**
	 * Initializes SMT, begining listening to a TUIO source on the
	 * specified port and using automatic touch source selection.
	 * 
	 * @param applet The Processing PApplet, usually just 'this' when using the Processing IDE
	 * @param port The port to listen on
	 */
	public static void init( PApplet applet, int port){
		init( applet, port, default_touchsource);
	}

	/**
	 * Initializes SMT, begining listening to a TUIO source on the
	 * default port of 3333 and using the specified touch sources.
	 * 
	 * @param applet The Processing PApplet, usually just 'this' when using the Processing IDE
	 * @param sources The touch devices to try to listen to. One or more of: TouchSource.MOUSE, TouchSource.TUIO_DEVICE, TouchSource.ANDROID, TouchSource.WM_TOUCH, TouchSource.SMART, TouchSource.AUTOMATIC.
	 */
	public static void init( PApplet applet, TouchSource... sources){
		init( applet, default_port, sources);
	}

	/**
	 * Initializes SMT, begining listening to a TUIO source on the
	 * specified port and using the specified touch sources.
	 * 
	 * @param applet The Processing PApplet, usually just 'this' when using the Processing IDE
	 * @param port The port to listen on
	 * @param sources The touch devices to try to listen to. One or more of: TouchSource.MOUSE, TouchSource.TUIO_DEVICE, TouchSource.ANDROID, TouchSource.WM_TOUCH, TouchSource.SMART, TouchSource.AUTOMATIC.
	 */
	public static void init(
			PApplet applet, int port, TouchSource... sources){
		if( applet == null)
			throw new NullPointerException(
				"Null applet parameter, pass 'this' to SMT.init() instead of null");

		//check processing version
		if( ! pversion_override)
			if( ! checkProcessingVersion())
				return;

		//check renderer
		if( ! P3DDSRenderer.class.isInstance( applet.g))
			throw new RuntimeException(
				String.format(
					"To use this library you must use SMT.RENDERER as the renderer field in size(). For example: size( 800, 600, SMT.RENDERER). You used %s.",
					applet.g.getClass().getName()));
		renderer = (P3DDSRenderer) applet.g;

		//load applet methods
		SMT.applet = applet;
		SMTUtilities.loadMethods( applet.getClass());

		//load system adapter
		systemAdapter = new SystemAdapter( applet);

		//load default touch bounds
		SMT.setTouchSourceBoundsSketch( TouchSource.MOUSE);
		SMT.setTouchSourceBoundsActiveDisplay( 
			TouchSource.LEAP, TouchSource.SMART,
			TouchSource.TUIO_DEVICE, TouchSource.WM_TOUCH);

		//load touch drawer ( if necessary )
		if( touchDrawMethod == TouchDraw.TEXTURED)
			texturedTouchDrawerNullCheck();

		touch = SMTUtilities.getPMethod( applet, "touch");
		SMT.sketch = new MainZone( 0, 0, applet.width, applet.height);

		//register extra methods
		applet.registerMethod("draw", new SMT());
		applet.registerMethod("pre", new SMT());

		picker = new ZonePicker();
		listener = new SMTTuioListener();
		manager = new SMTTouchManager( listener, picker);
		mainListenerPort = port;

		//remove duplicate touch sources and stuff
		boolean auto_enabled = false;
		boolean tuio_enabled = false;
		Vector<TouchSource> filteredSources = new Vector<TouchSource>();
		for( TouchSource source : sources)
			if( source == TouchSource.AUTOMATIC ||
					source == TouchSource.MULTIPLE)
				auto_enabled = true;
			else if( source == TouchSource.TUIO_DEVICE)
				tuio_enabled = true;
			else if( ! filteredSources.contains( source))
				filteredSources.add( source);

		//the tuio adapter should always started first
		// so it uses the parameter-specified port.
		if( tuio_enabled || auto_enabled){
			try{ connect_tuio( port);}
			catch( TuioConnectionException exception){
				System.out.printf(
					"Opening a tuio listener on port %d failed. Tuio devices probably won't work.\n", port);
			}
			//increment port regardless of success so tuio devices don't get mistaken for other sources
			// if this connection failed, the port is probably going to be incremented anyways when opening a new listener.
			port++;
		}
		//connect to every specified touch source
		for( TouchSource source : filteredSources){
			//open a new listener
			TuioClient client = null;
			while( client == null)
				try{ client = openTuioClient( port);}
				catch( TuioConnectionException exception){
					port++;
				}

			switch (source){
				case ANDROID:
					//connect_auto will take care of this later - ignore
					if( ! auto_enabled)
						connect_android( port);
					break;
				case MOUSE:
					//connect_auto will take care of this later - ignore
					if( ! auto_enabled)
						connect_mouse( port);
					break;
				case LEAP:
					//connect_auto will take care of this later - ignore
					if( ! auto_enabled)
						connect_leap( port);
					break;
				case SMART:
					connect_smart( port);
					break;
				case WM_TOUCH:
					//connect_auto will take care of this later - ignore
					if( ! auto_enabled)
						connect_windows( port);
					break;
				default: break;
			}
			//increment the port so next source's client doesn't conflict
			port++;
		}
		if( auto_enabled) connect_auto( port);

		//there's got to be a better way
		addJVMShutdownHook();

		world = new World( new Vec2( 0.0f, 0.0f), true);
		//top
		groundBody = createStaticBox( 0, -10.0f, applet.width, 10.f);
		//bottom
		createStaticBox( 0, applet.height + 10.0f, applet.width, 10.f);
		//left
		createStaticBox( -10.0f, 0, 10.0f, applet.height);
		//right
		createStaticBox( applet.width + 10.0f, 0, 10.0f, applet.height);
	}

	// touch server connection functions
	private static void connect_auto( int port){
		//gather system information
		boolean os_android =
			System.getProperty( "java.vm.name")
				.equalsIgnoreCase( "dalvik");
		String os_string = System.getProperty( "os.name");
		boolean os_windows = os_string.startsWith( "Windows");

		//other variables
		TuioClient client = null;

		//connect to tuio devices
		// already handled before this method call

		//connect to android touch
		if( os_android){
			client = null; //just in case... aha
			//open a new listener
			while( client == null)
				try{ client = openTuioClient( port);}
				catch( TuioConnectionException exception){
					port++;
				}
			if( SMT.debug) System.out.printf(
				"Trying to connect to %s on port %d\n",
				"android touch", port);
			connect_android( port);
		}

		if( os_windows){
			//connect to leap motion
			client = null;
			while( client == null)
				try{ client = openTuioClient( port);}
				catch( TuioConnectionException exception){
					port++;
				}
			if( SMT.debug) System.out.printf(
				"Trying to connect to %s on port %d\n",
				"leap motion", port);
			connect_leap( port);

			//connect to windows touch
			client = null;
			while( client == null)
				try{ client = openTuioClient( port);}
				catch( TuioConnectionException exception){
					port++;
				}
			if( SMT.debug) System.out.printf(
				"Trying to connect to %s on port %d\n",
				"windows touch", port);
			connect_windows( port);
		}

		//connect to mouse
		if( ! os_android){
			client = null;
			while( client == null)
				try{ client = openTuioClient( port);}
				catch( TuioConnectionException exception){
					port++;
				}
			if( SMT.debug) System.out.printf(
				"Trying to connect to %s on port %d\n",
				"mouse emulation", port);
			connect_mouse( port);
		}
	}
	private static void connect_android( int port){
		att = new AndroidToTUIO(
			applet.width, applet.height, port);
		deviceMap.put( port, TouchSource.ANDROID);
		printConnectMessage( "android touch", port);
	}
	private static void connect_leap( int port){
		SMT.runLeapTuioServer( port);
		deviceMap.put(port, TouchSource.LEAP);
		printConnectMessage( "leap motion", port);
	}
	private static void connect_mouse( int port){
		mtt = new MouseToTUIO( applet.width, applet.height, port);
		applet.registerMethod( "mouseEvent", mtt);
		deviceMap.put( port, TouchSource.MOUSE);
		printConnectMessage( "mouse emulation", port);
	}
	private static void connect_smart( int port){
		SMT.runSmart2TuioServer();
		deviceMap.put( port, TouchSource.SMART);
		printConnectMessage( "not-so-smart table", port);
	}
	private static void connect_tuio( int port){
		TuioClient client = openTuioClient( port);
		tuioClientList.add( client);
		deviceMap.put( port, TouchSource.TUIO_DEVICE);
		printConnectMessage( "tuio devices", port);
	}
	private static void connect_windows( int port){
		boolean system_is32 = ! System.getProperty( "os.arch").equals("x86");
		SMT.runWinTouchTuioServer(
			system_is32, "127.0.0.1", port);
		deviceMap.put( port, TouchSource.WM_TOUCH);
		printConnectMessage( "windows touch", port);
	}

	//touch fitting functions

	// active display binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to active display mode.
	 * Uses the window's current display. This is the default touch fitting method for all touch sources, except TouchSource.MOUSE.
	 **/
	public static void setTouchSourceBoundsActiveDisplay(){
		setTouchSourceBoundsActiveDisplay( sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to active display mode.
	 * Uses the window's current display. This is the default touch fitting method for all touch sources, except TouchSource.MOUSE.
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsActiveDisplay(
			TouchSource... sources){
		TouchBinder binder = new ActiveDisplayTouchBinder();
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to active display mode.\n",
					source);
		}
	}

	// display binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to display mode.
	 * Interprets which display to fit to by the index in the graphics environment's display array.
	 * @param index the index of the display to bind touches onto
	 **/
	public static void setTouchSourceBoundsDisplay( int index){
		setTouchSourceBoundsDisplay( index, sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to display mode.
	 *
	 * This uses the display in the graphics environment's display array that matches id.equals( device.getIDString()). The display id string format is different on every platform. It is recommended to use the indexed version of this function instead.
	 * @param id The string id for the desired display.
	 **/
	public static void setTouchSourceBoundsDisplay( String id){
		setTouchSourceBoundsDisplay( id, sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to display mode.
	 * Interprets which display to fit to by the index in the graphics environment's display array.
	 * @param index the index of the display to bind touches onto
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsDisplay(
			int index, TouchSource... sources){
		TouchBinder binder = new DisplayTouchBinder( index);
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to indexed display mode.\n",
					source);
		}
	}
	/**
	 * Sets the touch fitting method for the given touch sources to display mode.
	 *
	 * This uses the display in the graphics environment's display array that matches id.equals( device.getIDString()). The display id string format is different on every platform. I recommend you use the indexed version of this function instead.
	 * @param id The string id for the desired display.
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsDisplay( String id,
			TouchSource... sources){
		TouchBinder binder = new DisplayTouchBinder( id);
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to id-referenced display mode.\n",
					source);
		}
	}

	// manual bounds binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to rect mode.
	 * Use this function if you want to manually change the touch source bounds.
	 * @param bounds a rectangle describing the desired touch bounds.
	 **/
	public static void setTouchSourceBoundsRect( Rectangle bounds){
		setTouchSourceBoundsRect( bounds, sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to rect mode.
	 * Use this function if you want to manually change the touch source bounds.
	 * @param bounds a rectangle describing the desired touch bounds.
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsRect( Rectangle bounds,
			TouchSource... sources){
		TouchBinder binder = new RectTouchBinder( bounds);
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to rectangle mode.\n",
					source);
		}
	}

	// screen binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to screen mode.
	 * This fits touches onto the entire screen space, covering all displays.
	 **/
	public static void setTouchSourceBoundsScreen(){
		setTouchSourceBoundsScreen( sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to screen mode.
	 * This fits touches onto the entire screen space, covering all displays.
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsScreen(
			TouchSource... sources){
		TouchBinder binder = new ScreenTouchBinder();
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to screen mode.\n",
					source);
		}
	}

	// window binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to window mode.
	 * This fits touches onto the current window's space.
	 **/
	public static void setTouchSourceBoundsSketch(){
		setTouchSourceBoundsSketch( sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to window mode.
	 * This fits touches onto the current window's space.
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsSketch(
			TouchSource... sources){
		TouchBinder binder = new SketchTouchBinder();
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to sketch mode.\n",
					source);
		}
	}

	// window binding method
	/**
	 * Sets the touch fitting method for all touch sources (except mouse) to use the custom specified TouchBinder.
	 * @param binder The custom touch binder that should be used for mapping touches into sketch space
	 **/
	public static void setTouchSourceBoundsCustom( TouchBinder binder){
		setTouchSourceBoundsCustom( binder, sources_notmouse);
	}
	/**
	 * Sets the touch fitting method for the given touch sources to use the custom specified TouchBinder.
	 * @param binder The custom touch binder that should be used for mapping touches into sketch space
	 * @param sources The touch sources that should should be bound by this method
	 **/
	public static void setTouchSourceBoundsCustom(
			TouchBinder binder, TouchSource... sources){
		for( TouchSource source : sources){
			touchBinders.put( source, binder);
			if( SMT.debug)
				System.out.printf(
					"Set the touch bounds for %s input to custom mode.\n",
					source);
		}
	}


	//other functions

	/**
	 * Get the current revision number.
	 * This number increments once for each official release and pre-release.
	 * 
	 * @return an integer unique to this SMT release and greater than all older SMT releases and pre-releases
	 */
	public static int getRevision(){
		return SMT.revision;
	}
	/**
	 * Get a string representing the current SMT version.
	 * This string represents the git tag of this release or pre-release.
	 *
	 * @return a string representing the current SMT version
	 */
	public static String getVersion(){
		return SMT.version;
	}
	/**
	 * Get a pretty, human-readable string representing the current SMT version.
	 *
	 * @return a pretty, nicely formatted string representing the current SMT version
	 */
	public static String getPrettyVersion(){
		return SMT.version_pretty;
	}

	/**
	 * Get smt's renderer.
	 * @return the renderer used by smt
	 */
	public static P3DDSRenderer getRenderer(){
		return renderer;
	}
	/**
	 * Get the system adapter used by smt.
	 * @return the system adapter used by smt
	 */
	public static SystemAdapter getSystemAdapter(){
		return systemAdapter;
	}
	/**
	 * Get the touch binder currently being used by smt for the specified touch source.
	 * @param source the touch source
	 * @return the touch binder used by the specified touch source
	 */
	public static TouchBinder getTouchBinder( TouchSource source){
		return touchBinders.get( source);
	}
	/**
	 * Get the root zone that smt uses to contain all other zones
	 * @return smt's root zone
	 */
	public static Zone getRootZone(){
		return sketch;
	}
	/**
	 * Get the current applet that SMT is hooked to
	 * @return smt's applet
	 */
	public static PApplet getApplet(){
		return applet;
	}
	/**
	 * Get whether fast picking is enabled or not
	 * @return whether fast picking is enabled
	 */
	public static boolean fastPickingEnabled(){
		return fastPicking;
	}

	/**
	 * Set whether to use fast picking or not.
	 * IDK why you wouldn't want to, but hey, I didn't write this.
	 * @param fastPicking whether to use fast picking
	 */
	public static void setFastPicking(boolean fastPicking){
		SMT.fastPicking = fastPicking;
	}

	/**
	 * Calling this function overrides the default behavior of showing select
	 * unimplemented based on defaults for each zone specified in loadMethods()
	 * 
	 * @param warn whether to warn when there are unimplemented methods for zones
	 */
	public static void setWarnUnimplemented(boolean warn){
		SMT.warnUnimplemented = warn;
	}

	// Connection utility methods
	private static TuioClient openTuioClient( int port){
		//try to open connection
		TuioClient client = new TuioClient( port);
		client.connect();
		//return if connection succeeded
		if( client.isConnected()){
			client.addTuioListener(
				new ProxyTuioListener( port, listener));
			tuioClientList.add( client);
			return client;}
		else throw new TuioConnectionException(
				String.format( "Listening on port %d failed.\n", port));
	}

	private static void printConnectMessage( String message, int port){
		if( debug) System.out.printf(
			"Listening to %s using port %d\n", message, port);
	}

	/**
	 * Check that this build of SMT is compatible with the current version of processing.
	 * @return true if the current processing version is compatible with this version of SMT, false otherwise
	 */
	public static boolean checkProcessingVersion(){
		/*int revision = processing.app.Base.getRevision();
		String revision_name = processing.app.Base.getVersionName();
		//check revision lower bound
		if( revision < revision_min){
			System.out.printf(
				"You are using Processing build %s. This build of SMT requires, at minimum, Processing %s ( build %s ). Either upgrade processing or downgrade SMT. You might find a compatible build of SMT at vialab.science.uoit.ca/smt/download.php. Alternatively, to disable this check, set SMT.pversion_override = true.\n",
				revision_name, revision_min_name, revision_min_build);
			return false;}
		//check revision upper bound
		if( revision_max != revision_unknown && revision > revision_max){
			System.out.printf(
				"You are using Processing build %s. This build of SMT requires, at maximum, Processing %s ( build %s ). Either downgrade processing or upgrade SMT. You might find a compatible build of SMT at vialab.science.uoit.ca/smt/download.php. Alternatively, to disable this check, set SMT.pversion_override = true.\n",
				revision_name, revision_max_name, revision_max_build);
			return false;}*/
		//all's good :)
		return true;
	}

	/**
	 * Disconnects the TuioClient when the PApplet is stopped. Shuts down
	 * any threads, disconnect from the net, unload memory, etc.
	 */
	private static void addJVMShutdownHook(){
		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable(){
			public void run(){
				SMT.inShutdown  = true;
				if( warnUncalledMethods)
					SMTUtilities.warnUncalledMethods(applet);
				for( TuioClient client : tuioClientList)
					if( client.isConnected())
						client.disconnect();
				for( BufferedWriter writer : tuioServerInList)
					try{
						writer.newLine();
						writer.flush();
					}
					catch( IOException exception){}

				try{ Thread.sleep( 500);}
				catch( InterruptedException exception){}

				for( Process tuioServer : tuioServerList)
					if( tuioServer != null)
						tuioServer.destroy();
				if( debug)
					System.out.println("SMT has completed shutdown");
			}
		}));
	}
	/**
	 * This creates a static box to interact with the physics engine, collisions
	 * will occur with Zones that have physics == true, keeping them on the
	 * screen
	 * 
	 * @param x X-position
	 * @param y Y-position
	 * @param w Width
	 * @param h Height
	 * @return a jbox2d box body
	 */
	public static Body createStaticBox( float x, float y, float w, float h){
		BodyDef boxDef = new BodyDef();
		boxDef.position.set(
			SMT.box2dScale * (x + w / 2),
			SMT.box2dScale * (applet.height - (y + h / 2)));
		Body box = world.createBody(boxDef);
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(
			SMT.box2dScale * w / 2,
			SMT.box2dScale * h / 2);
		box.createFixture(boxShape, 0.0f);
		return box;
	}

	/**
	 * Redirects the MotionEvent object to AndroidToTUIO, used because current
	 * version of Processing (2.x) does not support registerMethod("touchEvent",
	 * target).
	 * 
	 * @param me
	 *            MotionEvent - the motion event triggered in Android
	 * @return Should the event get consumed elsewhere or not
	 */
	public static boolean passAndroidTouchEvent(Object me){
		return att.onTouchEvent(me);
	}

	/** Gets all zones recognized by SMT
	 * @return A array of all the zones that have been added to SMT
	 */
	public static Zone[] getZones(){
		return getDescendents( sketch).toArray( new Zone[0]);
	}

	//i hate having to write this... it feels bad
	protected static float getNextZone_Z(){
		return zonedraw_deltaz * ( zonedraw_i++);
	}

	private static List<Zone> getDescendents(Zone parent){
		ArrayList<Zone> descendents = new ArrayList<Zone>();
		for(Zone child : parent.getChildren()){
			descendents.add(child);
			descendents.addAll(getDescendents(child));
		}
		return descendents;
	}

	/**
	 * Sets the desired touch draw method. Any of the values of the enum TouchDraw are legal.
	 * @param drawMethod One of TouchDraw.{ NONE, DEBUG, SMOOTH, TEXTURED}
	 * @param drawers the optional touch drawer parameter. only to be used when using TouchDraw.CUSTOM
	 */
	public static void setTouchDraw(
			TouchDraw drawMethod, TouchDrawer... drawers){
		if( drawMethod != TouchDraw.CUSTOM){
			//return if no change necessary
			if( SMT.touchDrawMethod == drawMethod)
				return;
			//set the flag
			SMT.touchDrawMethod = drawMethod;
			//handle setup, if neccessary
			if( SMT.touchDrawMethod == TouchDraw.TEXTURED){
				//if necessary, initialize, otherwise, just update
				texturedTouchDrawerNullCheck();
				texturedTouchDrawer.update();
			}
			//remove unusable object references
			if( customTouchDrawer != null)
				customTouchDrawer = null;
		}
		else {
			//do validation
			if( drawers.length == 0)
				throw new RuntimeException(
					"SMT.setTouchDraw() was given TouchDraw.CUSTOM, but no custom touch drawer. Try SMT.setTouchDraw( TouchDraw.CUSTOM, myTouchDrawer);");
			if( drawers.length > 1)
				System.err.println(
					"[SMT Warning] SMT.setTouchDraw() expected one optional parameter, but was given multiple. The first one will be used.");
			customTouchDrawer = drawers[ 0];
			if( customTouchDrawer == null)
				throw new NullPointerException(
					"The optional TouchDrawer parameter given to SMT.setTouchDraw() was null.");
			//set the drawmethod
			SMT.touchDrawMethod = drawMethod;
		}
	}

	private static void texturedTouchDrawerNullCheck(){
		if( texturedTouchDrawer == null)
			texturedTouchDrawer = new TexturedTouchDrawer();
	}

	/** Gets the object currently being used to draw touches.
	 * @return the object currently being used to draw touches. Null if the touches are not being draw with an object.
	 */
	public static TouchDrawer getTouchDrawer(){
		switch( touchDrawMethod){
			case CUSTOM:
				return customTouchDrawer;
			case TEXTURED:
				return texturedTouchDrawer;
			default:
				return null;
		}
	}


	/**
	 * Sets the desired touch draw method. Any of the values of the enum TouchDraw are legal. Also sets the maximum path length to drawn.
	 * @param drawMethod One of TouchDraw.{ NONE, DEBUG, SMOOTH, TEXTURED}
	 * @param maxPathLength The number of points on a touch's path to draw
	 * @deprecated use setTouchDraw( TouchDraw) and setMaxPathLength( int) as separate calls, rather than together.
	 */
	@Deprecated
	public static void setTouchDraw(
			TouchDraw drawMethod, int maxPathLength){
		setTouchDraw( drawMethod);
		setMaxPathLength( maxPathLength);
	}

	/** Sets the maximum path length to drawn. Does not affect the textured touch drawer.
	 * @param maxPathLength The number of points on a touch's path to draw
	 */
	public static void setMaxPathLength( int maxPathLength){
		SMT.MAX_PATH_LENGTH = maxPathLength;
	}

	/**
	 * Sets the duration of the fade animation that occurs when a touch dies
	 * Note: this option is only obeyed when using TouchDraw.TEXTURED
	 * @param duration_milliseconds the desired duration of the fade animation
	 */
	public static void setTouchFadeDuration( long duration_milliseconds){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setDeathDuration( duration_milliseconds);
	}
	/** Gets the duration of the fade animation that occurs when a touch dies
	 * Note: this function is only accurate when using TouchDraw.TEXTURED
	 * @return the current duration of the fade animation
	 */
	public static long getTouchFadeDuration(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getDeathDuration();
	}
	/** Sets the desired radius of a drawn touch
	 * Note: this option is only obeyed when using TouchDraw.TEXTURED
	 * @param radius the desired radius of a drawn touch, in pixels
	 */
	public static void setTouchRadius( float radius){
		touch_radius = radius;
		if( SMT.touchDrawMethod == TouchDraw.TEXTURED)
			texturedTouchDrawer.update();
	}
	/** Gets the current radius of a drawn touch
	 * Note: this function is only accurate when using TouchDraw.TEXTURED
	 * @return the current radius of a drawn touch, in pixels
	 */
	public static float getTouchRadius(){
		return touch_radius;
	}
	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public static void setTouchColour( float red, float green, float blue, float alpha){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTouchTint( red, green, blue, alpha);
	}
	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public static void setTouchColor( float red, float green, float blue, float alpha){
		setTouchColour( red, green, blue, alpha);
	}
	/** Gets the red aspect of tint of drawn touches.
	 * @return The desired tint's red element
	 */
	public static float getTouchRed(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTouchTintRed();
	}
	/** Gets the green aspect of tint of drawn touches.
	 * @return The desired tint's green element
	 */
	public static float getTouchGreen(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTouchTintGreen();
	}
	/** Gets the blue aspect of tint of drawn touches.
	 * @return The desired tint's blue element
	 */
	public static float getTouchBlue(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTouchTintBlue();
	}
	/** Gets the alpha aspect of tint of drawn touches.
	 * @return The desired tint's alpha element
	 */
	public static float getTouchAlpha(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTouchTintAlpha();
	}
	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public static void setTrailColour( float red, float green, float blue, float alpha){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailTint( red, green, blue, alpha);
	}
	/** Sets the desired tint of drawn touches.
	 * @param red The desired tint's red element
	 * @param green The desired tint's green element
	 * @param blue The desired tint's blue element
	 * @param alpha The desired tint's alpha element
	 */
	public static void setTrailColor( float red, float green, float blue, float alpha){
		setTrailColour( red, green, blue, alpha);
	}
	/** Gets the red aspect of tint of the drawn trail.
	 * @return The desired tint's red element
	 */
	public static float getTrailRed(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailTintRed();
	}
	/** Gets the green aspect of tint of the drawn trail.
	 * @return The desired tint's green element
	 */
	public static float getTrailGreen(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailTintGreen();
	}
	/** Gets the blue aspect of tint of the drawn trail.
	 * @return The desired tint's blue element
	 */
	public static float getTrailBlue(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailTintBlue();
	}
	/** Gets the alpha aspect of tint of the drawn trail.
	 * @return The desired tint's alpha element
	 */
	public static float getTrailAlpha(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailTintAlpha();
	}

	/**
	 * Set whether trail drawing is enabled 
	 * @param enabled whether trail drawing should be enabled 
	 **/
	public static void setTrailEnabled( boolean enabled){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailEnabled( enabled);
	}
	/**
	 * Get whether the trail is enabled
	 * @return whether the trail is enabled
	 **/
	public static boolean getTrailEnabled(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailEnabled();
	}

	/**
	 * Set the time threshold for touch path point selection 
	 * @param threshold the desired time threshold for touch path point selection
	 **/
	public static void setTrailTimeThreshold( int threshold){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailTimeThreshold( threshold);
	}
	/**
	 * Get the time threshold for touch path point selection
	 * @return the time threshold for touch path point selection
	 **/
	public static int getTrailTimeThreshold(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailTimeThreshold();
	}

	/**
	 * Set the point count threshold for touch path point selection 
	 * @param threshold the desired point count threshold for touch path point selection
	 **/
	public static void setTrailPointThreshold( int threshold){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailPointThreshold( threshold);
	}
	/**
	 * Get the point count threshold for touch path point selection
	 * @return the point count threshold for touch path point selection
	 **/
	public static int getTrailPointThreshold(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailPointThreshold();
	}

	/**
	 * Set the C parameter of the smoothing function 
	 * @param c the desired C parameter of the smoothing function
	 **/
	public static void setTrailC( float c){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailC( c);
	}
	/**
	 * Get the C parameter of the smoothing function
	 * @return the C parameter of the smoothing function
	 **/
	public static float getTrailC(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailC();
	}

	/**
	 * Set the base number of points on the curve 
	 * @param t_n the desired base number of points on the curve
	 **/
	public static void setTrailT_N( int t_n){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailT_N( t_n);
	}
	/**
	 * Get the base number of points on the curve
	 * @return the base number of points on the curve
	 **/
	public static int getTrailT_N(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailT_N();
	}

	/**
	 * Set the desired width of the trail.
	 * Making this any greater than the default will probably look bad.
	 * @param width the desired width of the trail
	 **/
	public static void setTrailWidth( float width){
		texturedTouchDrawerNullCheck();
		texturedTouchDrawer.setTrailWidth( width);
	}
	/**
	 * Get Set the desired width of the trail
	 * @return Set the desired width of the trail
	 **/
	public static float getTrailWidth(){
		texturedTouchDrawerNullCheck();
		return texturedTouchDrawer.getTrailWidth();
	}

	/**
	 * Sets the desired number of sections of a drawn touch. Higher amounts result in smoother circles, but have a small performance hit.
	 * Note: this option is only obeyed when using TouchDraw.TEXTURED
	 * @param sections the desired number of sections of a drawn touch
	 */
	public static void setTouchSections( int sections){
		touch_sections = sections;
		if( SMT.touchDrawMethod == TouchDraw.TEXTURED)
			texturedTouchDrawer.update();
	}
	/**
	 * Gets the current section count of a drawn touch
	 * Note: this function is only relevant when using TouchDraw.TEXTURED
	 * @return the current section count of a drawn touch, in pixels
	 */
	public static int getTouchSections(){
		return touch_sections;
	}

	/** Implements the "Smooth" touch draw method */
	public static void drawSmoothTouchPoints(){
		renderer.pushStyle();
		for (Touch touch : SMTTouchManager.currentTouchState){
			renderer.strokeWeight(3);

			long time = System.currentTimeMillis() - touch.startTimeMillis;

			if (touch.isAssigned() && time < 250){
				renderer.noFill();
				renderer.stroke(155, 255, 155, 100);
				renderer.ellipse(touch.x, touch.y, 75 - time / 10, 75 - time / 10);
			}
			else if (!touch.isAssigned() && time < 500){
				{
					renderer.noFill();
					renderer.stroke(255, 155, 155, 100);
					renderer.ellipse(touch.x, touch.y, time / 10 + 50, time / 10 + 50);
				}
			}
			renderer.noStroke();
			renderer.fill(0, 0, 0, 50);
			renderer.ellipse( touch.x, touch.y, 40, 40);
			renderer.fill(255, 255, 255, 50);
			if (touch.isJointCursor){
				renderer.fill(0, 255, 0, 50);
			}
			renderer.ellipse( touch.x, touch.y, 30, 30);
			renderer.noFill();
			renderer.stroke(200, 240, 255, 50);
			Point[] path = touch.getPathPoints();
			if (path.length > 3){
				for (int j = 3 + Math.max(0, path.length - (SMT.MAX_PATH_LENGTH + 2));
						j < path.length; j++){
					float weight = 10 - (path.length - j) / 5;
					if (weight >= 1){
						renderer.strokeWeight(weight);
						renderer.bezier(
							path[j].x, path[j].y,
							path[j - 1].x, path[j - 1].y,
							path[j - 2].x, path[j - 2].y,
							path[j - 3].x, path[j - 3].y);
					}
				}
			}
		}
		renderer.popStyle();
	}

	/** Implements the "Debug" touch draw method
	 * Draws all Touch objects and their path history, with some data to try to
	 * help with debugging
	 */
	public static void drawDebugTouchPoints(){
		renderer.pushStyle();
		for (Touch touch : SMTTouchManager.currentTouchState){
			renderer.fill(255);
			if (touch.isJointCursor)
				renderer.fill(0, 255, 0);
			renderer.stroke(0);
			renderer.ellipse(touch.x, touch.y, 30, 30);
			renderer.line(touch.x, touch.y - 15, touch.x, touch.y + 15);
			renderer.line(touch.x - 15, touch.y, touch.x + 15, touch.y);
			TuioPoint[] path = touch.path.toArray(new TuioPoint[touch.path.size()]);
			TuioPoint lastText = null;
			if( path.length > 3){
				for (int j = 1 + Math.max(0, path.length - (SMT.MAX_PATH_LENGTH + 2)); j < path.length; j++){
					String pointText = "#" + j + " x:" + path[j].getScreenX(renderer.width) + " y:"
							+ path[j].getScreenY(renderer.height) + " touch:"
							+ path[j].getTuioTime().getTotalMilliseconds() + "ms";
					renderer.fill(255);
					renderer.line(path[j].getScreenX(renderer.width),
							path[j].getScreenY(renderer.height),
							path[j - 1].getScreenX(renderer.width),
							path[j - 1].getScreenY(renderer.height));
					renderer.ellipse(path[j].getScreenX(renderer.width),
							path[j].getScreenY(renderer.height), 5, 5);
					if (lastText == null
							|| Math.abs(path[j].getScreenY(renderer.height)
									- lastText.getScreenY(renderer.height)) > 13
							|| Math.abs(path[j].getScreenX(renderer.width)
									- lastText.getScreenX(renderer.width)) > renderer
									.textWidth(pointText) * 1.5){
						renderer.fill(0);
						renderer.textSize(12);
						renderer.text(pointText, path[j].getScreenX(renderer.width) + 5,
								path[j].getScreenY(renderer.height));
						lastText = path[j];
						renderer.fill(255, 0, 0);
						renderer.ellipse(path[j].getScreenX(renderer.width),
								path[j].getScreenY(renderer.height), 5, 5);
					}
				}
			}
		}
		renderer.popStyle();
	}

	/** Adds a zone(s) to the sketch/application.
	 * @param zones The zones to add to the sketch/application
	 * @return Whether all zones were sucessfully added
	 */
	public static boolean add(Zone... zones){
		return SMT.sketch.add(zones);
	}


	/**
	 * Add a new zone, with the given name and default position and size.
	 *
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 */
	public static void addZone( String name){
		SMT.add( new Zone( name));
	}

	/**
	 * Add a new zone, with the given name and renderer, and default position and size.
	 *
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 * @param renderer the PGraphics renderer that draws the Zone
	 */
	public static void addZone( String name, String renderer){
		SMT.add( new Zone( name, renderer));
	}

	/**
	 * Add a new zone, with the given name and size, and default position.
	 * 
	 * @param name name of the zone, used in the draw, touch ,etc methods
	 * @param width the width of the zone
	 * @param height the height of the zone
	 */
	public static void addZone( String name, int width, int height){
		SMT.add( new Zone( name, width, height));
	}

	/**
	 * Add a new zone, with the given name, position, and size.
	 * 
	 * @param name The name of the zone, used for the reflection methods
	 *   (drawname(),touchname(),etc)
	 * @param x The x position of the zone
	 * @param y The y position of the zone
	 * @param width The width of the zone
	 * @param height The height of the zone
	 */
	public static void addZone( String name, int x, int y, int width, int height){
		SMT.add( new Zone( name, x, y, width, height));
	}

	/**
	 * Add a new zone, with the given name, renderer, position, and size.
	 * 
	 * @param name The name of the zone, used for the reflection methods (drawname(),touchname(),etc)
	 * @param x The x position of the zone
	 * @param y The y position of the zone
	 * @param width The width of the zone
	 * @param height The height of the zone
	 * @param renderer The renderer that draws the zone
	 */
	public static void addZone( String name, int x, int y,
			int width, int height, String renderer){
		SMT.add( new Zone( name, x, y, width, height, renderer));
	}

	/**
	 * This adds zones by creating them from XML specs, the XML needs "zone"
	 * tags, and currently supports the following variables: name, x, y, width,
	 * height, img
	 * @param xmlFilename The XML file to read in for zone configuration
	 * @return The array of zones created from the XML File
	 */
	/**public static Zone[] add(String xmlFilename){
		return SMT.sketch.addXMLZone(xmlFilename);
	}*/

	/**
	 * This adds a set of zones to a parent Zone
	 * @param parent The Zone to add the given zones to
	 * @param zones The zones to add to the parent as children
	 * @return true if the given zones were sucessfully added, false otherwise
	 */
	public static boolean addChild(Zone parent, Zone... zones){
		if (parent != null)
			return parent.add( zones);
		else {
			System.err.println("Warning: parent specified in addChild() is null");
			return false;
		}
	}

	/**
	 * This adds a set of zones to a parent Zone
	 * 
	 * @param parentName The name of the Zone to add the given zones to
	 * @param zones The zones to add to the parent as children
	 * @return true if the given zones were sucessfully added, false otherwise
	 */
	public static boolean addChild(String parentName, Zone... zones){
		return addChild( get( parentName), zones);
	}

	/**
	 * This removes a set of zones to a parent Zone
	 * 
	 * @param parent The Zone to add the given zones to
	 * @param zones The zones to add to the parent as children
	 * @return true if the given zones were sucessfully removed, false otherwise
	 */
	public static boolean removeChild(Zone parent, Zone... zones){
		if (parent != null){
			return parent.remove(zones);
		}
		else {
			System.err.println("Warning: parent specified in removeChild() is null");
			return false;
		}
	}

	/**
	 * This removes a set of zones to a parent Zone
	 * 
	 * @param parentName The name of the Zone to add the given zones to
	 * @param zones The zones to add to the parent as children
	 * @return true if the given zones were sucessfully removed, false otherwise
	 */
	public static boolean removeChild(String parentName, Zone... zones){
		return removeChild(get(parentName), zones);
	}

	/**
	 * This organizes the given zones into a grid configuration with the given
	 * x, y, width, height, and spacing
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param xSpace
	 * @param ySpace
	 * @param zones
	 */
	public static void grid(int x, int y, int width, int xSpace, int ySpace, Zone... zones){
		int currentX = x;
		int currentY = y;

		int rowHeight = 0;

		for (Zone zone : zones){
			if (currentX + zone.width > x + width){
				currentX = x;
				currentY += rowHeight + ySpace;
				rowHeight = 0;
			}

			zone.setLocation(currentX, currentY);

			currentX += zone.width + xSpace;
			rowHeight = Math.max(rowHeight, zone.height);
		}
	}

	/**
	 * This removes the zone given from the SMT, meaning it will not be
	 * drawn anymore or be assigned touches, but can be added back with a call
	 * to add(zone);
	 * 
	 * @param name The name of the zone to remove
	 * @return Whether all of the zones were removed successfully
	 */
	public static boolean remove( String name){
		return remove( get( name));
	}

	/**
	 * This removes the zone given from the SMT, meaning it will not be
	 * drawn anymore or be assigned touches, but can be added back with a call
	 * to add(zone);
	 * 
	 * @param zones  The zones to remove
	 * @return Whether all of the zones were removed successfully
	 */
	public static boolean remove( Zone... zones){
		return SMT.sketch.remove( zones);
	}

	public static void clearZones(){
		SMT.sketch.clearChildren();
	}

	/**
	 * Performs the drawing of the zones in order. Zone on top-most layer gets
	 * drawn last. Goes through the list on zones, pushes the current
	 * transformation matrix, applies the zone's matrix, draws the zone, pops
	 * the matrix, and when at the end of the list, it draws the touch points.
	 */
	public static void draw(){

		//invoke zone's draw methods
		zonedraw_i = 0;
		renderer.pushStyle();
		renderer.pushMatrix();
		renderer.ortho();
		sketch.invokeDraw();
		renderer.popMatrix();
		renderer.popStyle();
		//invoke zone's touch methods
		renderer.pushStyle();
		renderer.pushMatrix();
		renderer.ortho();
		sketch.invokeTouch();
		renderer.popMatrix();
		renderer.popStyle();

		//update jbox2d
		updateStep();

		switch( touchDrawMethod){
			case CUSTOM:
				customTouchDrawer.draw( 
					SMTTouchManager.currentTouchState, renderer);
				break;
			case DEBUG:
				drawDebugTouchPoints();
				break;
			case SMOOTH:
				drawSmoothTouchPoints();
				break;
			case TEXTURED:
				texturedTouchDrawer.draw( 
					SMTTouchManager.currentTouchState, renderer);
				break;
			case NONE:
				break;
		}
	}

	/**
	 * Draws a texture of the pickBuffer at the given x,y position with given
	 * width and height
	 * 
	 * @param x the x position to draw the pickBuffer image at
	 * @param y the y position to draw the pickBuffer image at
	 * @param w the width of the pickBuffer image to draw
	 * @param h the height of the pickBuffer image to draw
	 */
	public static void drawPickBuffer(int x, int y, int w, int h){
		 renderer.image( picker.picking_context, x, y, w, h);
	}

	/**
	 * Returns a vector containing all the current TuioObjects.
	 * 
	 * @return Vector&lt;TuioObject&gt;
	 */
	public static Vector<TuioObject> getTuioObjects(){
		return new Vector<TuioObject>(listener.getTuioObjects());
	}

	/**
	 * @return An array containing all touches that are currently NOT assigned
	 *         to zones
	 */
	public static Touch[] getUnassignedTouches(){
		List<Touch> touches = new ArrayList<Touch>();
		for (Touch touch : getTouches()){
			if(!touch.isAssigned()){
				touches.add(touch);
			}
		}
		return touches.toArray(new Touch[touches.size()]);
	}

	/**
	 * @return An array containing all touches that are currently assigned to
	 *         zones
	 */
	public static Touch[] getAssignedTouches(){
		List<Touch> touches = new ArrayList<Touch>();
		for (Zone zone : getZones()){
			for (Touch touch : zone.getTouches()){
				touches.add(touch);
			}
		}
		return touches.toArray(new Touch[touches.size()]);
	}

	/**
	 * @param zone
	 *            The zone to assign touches to
	 * @param touches
	 *            The touches to assign to the zone, variable number of
	 *            arguments
	 */
	public static void assignTouches(Zone zone, Touch... touches){
		zone.assign(touches);
	}

	/**
	 * Returns a collection containing all the current Touches(TuioCursors).
	 * 
	 * @return Collection&lt;Touch&gt;
	 */
	public static Collection<Touch> getTouchCollection(){
		return getTouchMap().values();
	}

	/**
	 * Returns a collection containing all the current Touches(TuioCursors).
	 * 
	 * @return Touch[] containing all touches that are currently mapped
	 */
	public static Touch[] getTouches(){
		return getTouchMap().values().toArray( new Touch[0]);
	}

	/**
	 * Returns a Touch stored at the given index
	 * @param index the index of the desired touch
	 * @return Touch The touch at the desired index
	 */
	public static Touch getTouch( int index){
		Touch[] touches = getTouches();
		return ( index < 0 || index >= touches.length) ?
			null : touches[index];
	}

	/**
	 * @return A Map&lt;Long,Touch&gt; indexing all touches by their session_id's
	 */
	public static Map<Long, Touch> getTouchMap(){
		return SMTTouchManager.currentTouchState.idToTouches;
	}

	// /**
	// * This method returns all the current touches that are not actively
	// * affecting any Zone.
	// *
	// * @return the array
	// */
	// public static Touch[] getUnassignedTouches(){
	// Vector<TuioCursor> cursors = tuioClient.getTuioCursors();
	// ArrayList<Touch> touches = new ArrayList<>();
	// for (TuioCursor c : cursors){
	// Zone z = picker.getZoneFromId(c.getSessionID());
	// if (z == null){
	// touches.add(new Touch(c));
	// }
	// }
	// return touches.toArray(new Touch[touches.size()]);
	// }

	/**
	 * @return An array of all zones that currently have touches/are active.
	 */
	public static Zone[] getActiveZones(){
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for (Zone zone : getZones()){
			if (zone.isActive()){
				zones.add(zone);
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	/**
	 * @param zone
	 *            The zone to get the touches of
	 * @return A Collection&lt;Touch&gt; containing all touches from the given zone
	 */
	public static Collection<Touch> getTouchCollectionFromZone(Zone zone){
		return zone.getTouchCollection();
	}

	/**
	 * @param zone
	 *            The zone to get the touches of
	 * @return A Touch[] containing all touches from the given zone
	 */
	public static Touch[] getTouchesFromZone(Zone zone){
		return zone.getTouches();
	}

	/**
	 * Returns a the TuioObject associated with the session ID
	 * 
	 * @param s_id
	 *            long - Session ID of the TuioObject
	 * @return TuioObject
	 */
	public static TuioObject getTuioObject(long s_id){
		return listener.getTuioObject(s_id);
	}

	/**
	 * Returns the Touch(TuioCursor) associated with the session ID
	 * 
	 * @param s_id
	 *            long - Session ID of the Touch(TuioCursor)
	 * @return TuioCursor
	 */
	public static Touch getTouch(long s_id){
		return SMTTouchManager.currentTouchState.getById(s_id);
	}

	/** wtf is this for...
	 * @param s_id
	 *            The session_id of the Touch to get the path start of
	 * @return A new Touch containing the state of the touch at path start
	 */
	/*public static Touch getPathStartTouch(long s_id){
		TuioCursor c = listener.getTuioCursor(s_id);
		Vector<TuioPoint> path = new Vector<TuioPoint>(c.getPath());

		TuioPoint start = path.firstElement();
		return new Touch(start.getTuioTime(), c.getSessionID(), c.getCursorID(), start.getX(),
				start.getY());
	}*/

	// bla bla Touch from bla touch fds touch touch touch update what?
	/**
	 * This creates a new Touch object from the last touch of a given Touch
	 * object, the new Touch will not update.
	 * 
	 * It is easier to just create a new Touch with the given Touch object as
	 * its parameter "new Touch(current)" where current is the Touch we want the
	 * last touch of, so this is deprecated.
	 * 
	 * @param current
	 *            The Touch to get the last touch from
	 * @return A Touch containing the last touch, will not update, stays
	 *         consistent
	 * @deprecated
	 */
	/*public static Touch getLastTouch( Touch current){
		Vector<TuioPoint> path = current.path;
		if( path.size() > 1){
			TuioPoint last = path.get(path.size() - 2);
			return new Touch(
				last.getTuioTime(), current.getSessionID(), current.getCursorID(),
				last.getX(), last.getY());
		}
		else return null;
	}*/

	/**
	 * Returns the number of current Touches (TuioCursors)
	 * 
	 * @return number of current touches
	 */
	public static int getTouchCount(){
		return getTouches().length;
	}

	/**
	 * Manipulates the zone's position if it is throw-able after it has been
	 * released by a finger (cursor) Done before each call to draw. Uses the
	 * zone's x and y friction values.
	 */
	public static void pre(){
		//update touch binders
		systemAdapter.update();
		for( TouchBinder binder : touchBinders.values())
			binder.update();
		// TODO: provide some default assignment of touches
		zonedraw_i = 0;
		manager.handleTouches();

		if( mtt != null){
			//update touches from mouseToTUIO joint cursors as they are a special case and need to be shown to user
			for( Touch touch : SMTTouchManager.currentTouchState)
				touch.isJointCursor = false;
			for( Integer id : mtt.getJointCursors()){
				Touch touch = SMTTouchManager.currentTouchState.getById( id);
				if( touch != null)
					touch.isJointCursor = true;
			}
		}

		renderer.flush();
		if( getTouches().length > 0)
			SMTUtilities.invoke( touch, applet, null);

		//PGraphics extra = applet.createGraphics( 1, 1, PApplet.P3D);
		//renderer.pushDelegate( (PGraphics3D) extra);
		//renderer.beginDraw();
		//sketch.invokeTouch();
		//renderer.endDraw();
		//renderer.popDelegate();
		//updateStep();
	}

	/**
	 * perform a step of physics using jbox2d, update bodies before and matrix
	 * after of each Zone to keep the matrix and bodies synchronized
	 */
	private static void updateStep(){
		for( Zone zone : getZones()){
			if( zone.getPhysicsEnabled()){
				// generate body and fixture for zone if they do not exist
				if( zone.zoneBody == null && zone.zoneFixture == null){
					zone.zoneBody = world.createBody( zone.zoneBodyDef);
					zone.zoneFixture = zone.zoneBody.createFixture(
						zone.zoneFixtureDef);
				}
				zone.setBodyFromMatrix();
			}
			else {
				// make sure to destroy body for Zones that do not have physics
				// on, as they should not collide with others
				if( zone.zoneBody != null){
					world.destroyBody(zone.zoneBody);
					zone.zoneBody = null;
					zone.zoneFixture = null;
					zone.mJoint = null;
				}
			}
		}

		world.step(1f / applet.frameRate, 8, 3);

		for (Zone zone : getZones()){
			if (zone.getPhysicsEnabled()){
				zone.setMatrixFromBody();
			}
		}
	}

	/**
	 * @return A temp file directory
	 * @throws IOException
	 */
	private static File tempDir(){
		if( smt_tempdir == null){
			smt_tempdir = new File(
				System.getProperty("java.io.tmpdir") + "/SMT");
			smt_tempdir.mkdir();
			smt_tempdir.deleteOnExit();
		}
		return smt_tempdir;
	}

	/**
	 * @param dir The directory to load the resource into
	 * @param resource A string containing the name a program in SMT's resources folder
	 * @return A file written into dir
	 * @throws IOException
	 */
	private static File loadFile(File dir, String resource) throws IOException{
		BufferedInputStream src = new BufferedInputStream(
				SMT.class.getResourceAsStream("/resources/"+resource));
		final File exeTempFile = new File(dir.getAbsolutePath() + "\\"+resource);

		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(exeTempFile));
		byte[] tempexe = new byte[4 * 1024];
		int rc;
		while ((rc = src.read(tempexe)) > 0){
			out.write(tempexe, 0, rc);
		}
		src.close();
		out.close();
		exeTempFile.deleteOnExit();
		return exeTempFile;
	}

	/**
	 * Runs a server that sends TUIO events using Windows 7 Touch events
	 * 
	 * @param is64Bit Whether to use the 64-bit version of the exe
	 */
	private static void runWinTouchTuioServer(
			boolean is64Bit, final String address, final int port){
		try {
			File temp = tempDir();

			File touchhook = loadFile( temp,
				is64Bit ? "TouchHook_x64.dll" : "TouchHook.dll");
			File touch2tuio = loadFile( temp,
				is64Bit ? "Touch2Tuio_x64.exe" : "Touch2Tuio.exe");

			String touch2tuio_path = touch2tuio.getAbsolutePath();
			String window_title = applet.frame.getTitle();
			String exec = String.format(
				"%s %s %s %s", touch2tuio_path, window_title, address, port);
			String error_message = "WM_TOUCH Process died early, make sure Visual C++ Redistributable for Visual Studio 2012 is installed (http://www.microsoft.com/en-us/download/details.aspx?id=30679), otherwise try restarting your computer.";

			TouchSourceThread wintouch_thread = new TouchSourceThread(
				"WM_TOUCH", exec, error_message);
			wintouch_thread.start();
		}
		catch( IOException exception){
			exception.printStackTrace();
		}
	}

	private static void runSmart2TuioServer(){
		try {
			File temp = tempDir();

			File smartsdk = loadFile( temp, "SMARTTableSDK.Core.dll");
			File libtuio = loadFile( temp, "libTUIO.dll");
			File smart2tuio = loadFile( temp, "SMARTtoTUIO2.exe");

			String exec = smart2tuio.getAbsolutePath();

			TouchSourceThread smart_thread = new TouchSourceThread(
				"SMART", exec, "SMART Process died");
			smart_thread.start();
		}
		catch (IOException exception){
			exception.printStackTrace();
		}
	}

	/**
	 * Runs a server that sends TUIO events using Windows 7 Touch events
	 * 
	 * @param is64Bit
	 *            Whether to use the 64-bit version of the exe
	 */
	private static void runLeapTuioServer(final int port){
		try {
			File temp = tempDir();
			File leap = loadFile( temp, "Leap.dll");
			File motionless = loadFile( temp, "motionLess.exe");

			String exec = String.format( "%s %s",
				motionless.getAbsolutePath(), port);
			String error_message = "LEAP Process died early, make sure Visual C++ 2010 Redistributable (x86) is installed (http://www.microsoft.com/en-ca/download/details.aspx?id=5555)";

			TouchSourceThread leap_thread = new TouchSourceThread(
				"LEAP", exec, error_message);
			leap_thread.start();
		}
		catch (IOException exception){
			exception.printStackTrace();
		}
	}

	/**
	 * Runs an exe from a path, presumably for translating native events to tuio events
	 * @param path the path of the executable
	 */
	public static void runExe(final String path){
		new TouchSourceThread( path, path, path + " Process died").start();
	}

	/**
	 * @param zone The zone to place on top of the others
	 */
	public static void putZoneOnTop(Zone zone){
		sketch.putChildOnTop( zone);
	}

	/**
	 * This finds a zone by its name, returning the first zone with the given
	 * name or null.
	 *
	 * This will throw ClassCastException if the Zone is not an instance of the
	 * given class , and non-applicable type compile errors when the given class
	 * does not extend Zone.
	 *
	 * @param <T> the expected class of the zone
	 * @param name The name of the zone to find
	 * @param type a class type to cast the Zone to (e.g. Zone.class)
	 * @return a Zone with the given name or null if it cannot be found
	 */
	@Deprecated
	public static <T extends Zone> T get( String name, Class<T> type){
		for( Zone zone : getZones())
			if( name.equals( zone.name) && type.isInstance( zone))
				return type.cast( zone);
		return null;
	}

	/**
	 * This finds a zone by its name, returning the first zone with the given
	 * name or null
	 * @param name The name of the zone to find
	 * @return a Zone with the given name or null if it cannot be found
	 */
	public static Zone get( String name){
		for( Zone zone : getZones())
			if( name.equals( zone.name))
				return zone;
		return null;
	}

	/**
	 * This adds objects to check for drawZoneName, touchZoneName, etc methods
	 * in, similar to PApplet
	 * @param classes The additional objects to check in for methods
	 * @deprecated Do not use this method - See <a href="https://github.com/vialab/SMT/issues/174">this github issue</a>
	 */
	/*@Deprecated
	public static void addMethodClasses( Class<?>... classes){
		for( Class<?> c : classes)
			SMTUtilities.loadMethods( c);
	}*/

	/**
	 * This class encapsulates all the logic for running a seperate process in a thread
	 * It is used by runWinTouchTuioServer(), runSMart2TuioServer(), and runLeapTuioServer()
	 * To avoid duplicated code. It puts labels on output and allows specifying a error message
	 * for premature shutdown condition.
	 */
	private static class TouchSourceThread extends Thread{
		String label;
		String execArg;
		String prematureShutdownError;

		TouchSourceThread( String label, String execArg, String prematureShutdownError){
			this.label = label;
			this.execArg = execArg;
			this.prematureShutdownError = prematureShutdownError;
		}

		@Override
		public void run(){
			try {
				Process tuioServer = Runtime.getRuntime().exec( execArg);
				BufferedReader tuioServerErr = new BufferedReader(
					new InputStreamReader( tuioServer.getErrorStream()));
				BufferedReader tuioServerOut = new BufferedReader(
					new InputStreamReader( tuioServer.getInputStream()));
				BufferedWriter tuioServerIn = new BufferedWriter(
					new OutputStreamWriter( tuioServer.getOutputStream()));

				tuioServerList.add(tuioServer);
				tuioServerErrList.add(tuioServerErr);
				tuioServerOutList.add(tuioServerOut);
				tuioServerInList.add(tuioServerIn);

				while( true){
					if( SMT.debug && tuioServerErr.ready())
						System.err.println(
							label + ": " + tuioServerErr.readLine());
					if( SMT.debug && tuioServerOut.ready())
						System.out.println(
							label + ": " + tuioServerOut.readLine());

					try {
						int result = tuioServer.exitValue();
						if( SMT.debug)
							System.out.println(label + " return value=" + result);
						if( ! SMT.inShutdown)
							System.err.println( prematureShutdownError);
						break;
					}
					catch( IllegalThreadStateException exception){
						// still running... sleep time
						Thread.sleep(100);
					}
				}
			}
			catch( IOException exception){
				System.err.println( exception.getMessage());
			}
			catch( Exception exception){
				System.err.println( label + " TUIO Server stopped!");
			}
		}
	}
}
