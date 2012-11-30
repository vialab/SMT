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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import android.view.*;

import vialab.TUIOSource.*;

import TUIO.*;

/**
 * The Core SMT class, it provides the TUIO/Touch Processing client
 * implementation, Specifies the back-end source of Touches via TouchSource, and
 * SMT cannot be used without it.
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka
 * @author Zach Cook
 * @version 1.0
 */
public class TouchClient {

	private static int MAX_PATH_LENGTH = 50;

	private static Process tuioServer;

	/** Processing PApplet */
	public static PApplet parent;

	/** The TouchClient */
	public static TouchClient client;

	/** Gesture Handler */
	//public static GestureHandler handler;

	/** Tuio Client that listens for Tuio Messages via port 3333 UDP */
	public static TuioClient tuioClient;

	/** The main zone list */
	protected static CopyOnWriteArrayList<Zone> zoneList = new CopyOnWriteArrayList<Zone>();

	/** Flag for drawing touch points */
	public static boolean drawTouchPoints = true;

	/** Matrix used to test if the zone has gone off the screen */
	// private PMatrix3D mTest = new PMatrix3D();

	public static SMTZonePicker picker;

	public static SMTTuioListener listener;

	public static SMTTouchManager manager;

	private Method touch;

	public static Boolean warnUnimplemented;

	/**
	 * This controls whether we give a warning for uncalled methods which use
	 * one of the reserved method prefixes (draw,pickDraw,touch,etc and any that
	 * have been added by calls to SMTUtilities.getZoneMethod() with a unique
	 * prefix).
	 * <P>
	 * Default state is on.
	 */
	public static boolean warnUncalledMethods = true;

	/**
	 * The renderer to use as a default for zones, can be P3D, P2D, OPENGL, etc,
	 * upon TouchClient initialization this is set to the same as the PApplet's
	 * renderer
	 */
	public static String defaultRenderer;

	/** TUIO adapter depending on which TouchSource is used */
	AndroidToTUIO att = null;
	MouseToTUIO mtt = null;

	/**
	 * Calling this function overrides the default behavior of showing select
	 * unimplemented based on defaults for each zone specified in loadMethods()
	 * 
	 * @param warn
	 *            whether to warn when there are unimplemented methods for zones
	 */
	public static void setWarnUnimplemented(boolean warn) {
		TouchClient.warnUnimplemented = warn;
	}

	/**
	 * Default Constructor. Default port is 3333 for TUIO
	 * 
	 * @param parent
	 *            PApplet - The Processing PApplet
	 */
	public TouchClient(PApplet parent) {
		this(parent, 3333);
	}

	/**
	 * Constructor. Allows you to select the TouchSource backend from which to
	 * get multi-touch events from
	 * 
	 * @param parent
	 *            PApplet - The Processing PApplet, usually just 'this' when
	 *            using the Processing IDE
	 * @param port
	 *            int - The port to listen on
	 */
	public TouchClient(PApplet parent, int port) {
		this(parent, port, TouchSource.TUIO_DEVICE);
	}

	/**
	 * Constructor. Allows you to select the TouchSource backend from which to
	 * get multi-touch events from
	 * 
	 * @param parent
	 *            PApplet - The Processing PApplet, usually just 'this' when
	 *            using the Processing IDE
	 * @param source
	 *            enum TouchSource - The source of touch events to listen to.
	 *            One of: TouchSource.MOUSE, TouchSource.TUIO_DEVICE,
	 *            TouchSource.ANDROID, TouchSource.WM_TOUCH, TouchSource.SMART
	 */
	public TouchClient(PApplet parent, TouchSource source) {
		this(parent, 3333, source);
	}

	/**
	 * Constructor. Allows you to select the TouchSource backend from which to
	 * get multi-touch events from
	 * 
	 * @param parent
	 *            PApplet - The Processing PApplet, usually just 'this' when
	 *            using the Processing IDE
	 * 
	 * @param port
	 *            int - The port to listen on
	 * 
	 * @param source
	 *            int - The source of touch events to listen to. One of:
	 *            TouchSource.MOUSE, TouchSource.TUIO_DEVICE,
	 *            TouchSource.ANDROID, TouchSource.WM_TOUCH, TouchSource.SMART
	 */
	private TouchClient(final PApplet parent, int port, TouchSource source) {
		// As of now the toolkit only supports OpenGL
		if (!parent.g.isGL()) {
			System.out
					.println("SMT only supports using OpenGL renderers, please use either OPENGL, P2D, or P3D, in the size function e.g  size(displayWidth, displayHeight, P3D);");
		}

		touch = SMTUtilities.getPMethod(parent, "touch");

		TouchClient.parent = parent;
		//parent.registerMethod("dispose", this);
		parent.registerMethod("draw", this);
		parent.registerMethod("pre", this);
		// handler = new GestureHandler();

		picker = new SMTZonePicker();

		TouchClient.client = this;

		defaultRenderer = parent.g.getClass().getName();

		switch (source) {
		case ANDROID:
			// this still uses the old method, should be re-implemented without
			// the socket
			att = new AndroidToTUIO(parent.width, parent.height);
			// parent.registerMethod("touchEvent", att); //when Processing
			// supports this
			tuioClient = new TuioClient(port);
			break;
		case MOUSE:
			// this still uses the old method, should be re-implemented without
			// the socket
			mtt = new MouseToTUIO(parent.width, parent.height);
			parent.registerMethod("mouseEvent", mtt);
			tuioClient = new TuioClient(port);
			break;
		case TUIO_DEVICE:
			tuioClient = new TuioClient(port);
			break;
		case WM_TOUCH:
			if (System.getProperty("os.arch").equals("x86")) {
				this.runWinTouchTuioServer(false);
			}
			else {
				this.runWinTouchTuioServer(true);
			}
			tuioClient = new TuioClient(port);
			break;
		case SMART:
			this.runSmart2TuioServer();
			tuioClient = new TuioClient(port);
			break;
		default:
			break;
		}

		listener = new SMTTuioListener();

		manager = new SMTTouchManager(listener, picker);

		tuioClient.addTuioListener(listener);
		tuioClient.connect();

		parent.hint(PConstants.ENABLE_ACCURATE_2D);

		/**
		 * Disconnects the TuioClient when the PApplet is stopped. Shuts down
		 * any threads, disconnect from the net, unload memory, etc.
		 */
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				if (warnUncalledMethods) {
					SMTUtilities.warnUncalledMethods(parent);
				}
				if (tuioClient.isConnected()) {
					tuioClient.disconnect();
				}
				if (tuioServer != null) {
					tuioServer.destroy();
				}
			}
		}));
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
	public boolean passAndroidTouchEvent(MotionEvent me) {
		return att.onTouchEvent(me);
	}

	/**
	 * Returns the list of zones.
	 * 
	 * @return zoneList
	 */
	public Zone[] getZones() {
		return zoneList.toArray(new Zone[0]);
	}

	/**
	 * Sets the flag for drawing touch points in the PApplet. Draws the touch
	 * points if flag is set to true.
	 * 
	 * @param drawTouchPoints
	 *            boolean - flag
	 */
	public void setDrawTouchPoints(boolean drawTouchPoints) {
		TouchClient.drawTouchPoints = drawTouchPoints;
	}

	/**
	 * Sets the flag for drawing touch points in the PApplet. Draws the touch
	 * points if flag is set to true. Sets the maximum path length to draw to be
	 * max_path_length
	 * 
	 * @param drawTouchPoints
	 *            boolean - flag
	 * 
	 * @param max_path_length
	 *            int - sets maximum path length to draw
	 */
	public void setDrawTouchPoints(boolean drawTouchPoints, int max_path_length) {
		TouchClient.drawTouchPoints = drawTouchPoints;
		TouchClient.MAX_PATH_LENGTH = max_path_length;
	}

	/**
	 * Draws the touch points in the PApplet if flag is set to true.
	 */
	public static void drawTouchPoints() {
		Vector<TuioCursor> curs = tuioClient.getTuioCursors();
		parent.pushStyle();
		parent.strokeWeight(1);
		parent.noFill();
		if (curs.size() > 0) {
			for (int i = 0; i < curs.size(); i++) {
				parent.stroke(255);
				parent.ellipse(curs.get(i).getScreenX(TouchClient.parent.width), curs.get(i)
						.getScreenY(parent.height), 20, 20);
				parent.stroke(0);
				parent.ellipse(curs.get(i).getScreenX(parent.width),
						curs.get(i).getScreenY(parent.height), 22, 22);
				Vector<TuioPoint> path = curs.get(i).getPath();
				if (path.size() > 1) {
					for (int j = 1 + Math.max(0, path.size() - (TouchClient.MAX_PATH_LENGTH + 2)); j < path
							.size(); j++) {
						parent.stroke(255);
						parent.line(path.get(j).getScreenX(parent.width) - 0.5f, path.get(j)
								.getScreenY(parent.height) - 0.5f,
								path.get(j - 1).getScreenX(parent.width) - 0.5f, path.get(j - 1)
										.getScreenY(parent.height) - 0.5f);
						parent.ellipse(path.get(j).getScreenX(parent.width), path.get(j)
								.getScreenY(parent.height), 5, 5);
						parent.stroke(0);
						parent.line(path.get(j).getScreenX(parent.width) + 0.5f, path.get(j)
								.getScreenY(parent.height) + 0.5f,
								path.get(j - 1).getScreenX(parent.width) + 0.5f, path.get(j - 1)
										.getScreenY(parent.height) + 0.5f);
						parent.ellipse(path.get(j).getScreenX(parent.width), path.get(j)
								.getScreenY(parent.height), 7, 7);
					}
				}
			}
		}
		parent.popStyle();
	}

	/**
	 * Adds a zone to the zone list. When a student creates a zone, they must
	 * add it to this list.
	 * 
	 * @param zones
	 *            Zone - The zones to add to the list.
	 */
	public void add(Zone... zones) {
		for (Zone zone : zones) {
			//Zone is being added at top level, make sure its parent is set to null, so that we draw it at TouchClient level
			//the zone will set parent afterwards when adding anyways, so we should always do this to make sure we dont have issues
			//when a zone has not been removed from its parent (and so parent!=null), and so is not drawn after being added to TouchClient
			zone.parent=null;
			
			addToZoneList(zone);
			picker.add(zone);
		}
	}

	private void addToZoneList(Zone zone) {
		if (!zoneList.contains(zone)) {
			zoneList.add(zone);
		}
		for (Zone child : zone.children) {
			addToZoneList(child);
		}
	}

	/**
	 * This adds zones by creating them from XML specs, the XML needs "zone"
	 * tags, and currently supports the following variables: name, x, y, width,
	 * height, img
	 * 
	 * @param xmlFilename
	 *            The XML file to read in for zone configuration
	 * @return The array of zones created from the XML File
	 */
	public Zone[] add(String xmlFilename) {
		List<Zone> zoneList = new ArrayList<Zone>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(parent.createInput(xmlFilename));

			NodeList zones = doc.getElementsByTagName("zone");
			add(zones, zoneList);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return zoneList.toArray(new Zone[zoneList.size()]);
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
	public void grid(int x, int y, int width, int xSpace, int ySpace, Zone... zones) {
		int currentX = x;
		int currentY = y;

		int rowHeight = 0;

		for (Zone zone : zones) {
			if (currentX + zone.width > x + width) {
				currentX = x;
				currentY += rowHeight + ySpace;
				rowHeight = 0;
			}

			zone.setLocation(currentX, currentY);

			currentX += zone.width + xSpace;
			rowHeight = Math.max(rowHeight, zone.height);
		}
	}

	private void add(NodeList zones, List<Zone> zoneList) {
		for (int i = 0; i < zones.getLength(); i++) {
			Node zoneNode = zones.item(i);
			if (zoneNode.getNodeType() == Node.ELEMENT_NODE
					&& zoneNode.getNodeName().equalsIgnoreCase("zone")) {
				add(zoneNode, zoneList);
			}
		}
	}

	private void add(Node node, List<Zone> zoneList) {
		NamedNodeMap map = node.getAttributes();
		Node nameNode = map.getNamedItem("name");
		Node xNode = map.getNamedItem("x");
		Node yNode = map.getNamedItem("y");
		Node widthNode = map.getNamedItem("width");
		Node heightNode = map.getNamedItem("height");

		Node imgNode = map.getNamedItem("img");

		Zone zone;

		String name = null;
		int x, y, width, height;
		if (nameNode != null) {
			name = nameNode.getNodeValue();
		}

		if (imgNode != null) {
			String imgFilename = imgNode.getNodeValue();
			PImage img = parent.loadImage(imgFilename);

			if (xNode != null && yNode != null) {
				x = Integer.parseInt(xNode.getNodeValue());
				y = Integer.parseInt(yNode.getNodeValue());
				if (widthNode != null && heightNode != null) {
					width = Integer.parseInt(widthNode.getNodeValue());
					height = Integer.parseInt(heightNode.getNodeValue());
					zone = new ImageZone(name, img, x, y, width, height);
				}
				else {
					zone = new ImageZone(name, img, x, y);
				}
			}
			else {
				zone = new ImageZone(name, img);
			}
		}
		else {
			if (xNode != null && yNode != null && widthNode != null && heightNode != null) {
				x = Integer.parseInt(xNode.getNodeValue());
				y = Integer.parseInt(yNode.getNodeValue());
				width = Integer.parseInt(widthNode.getNodeValue());
				height = Integer.parseInt(heightNode.getNodeValue());
				zone = new Zone(name, x, y, width, height);
			}
			else {
				zone = new Zone(name);
			}
		}

		zoneList.add(zone);
		add(zone);
		add(node.getChildNodes(), zoneList);
	}

	/**
	 * This removes the zone given from the TouchClient, meaning it will not be
	 * drawn anymore or be assigned touches, but can be added back with a call
	 * to add(zone);
	 * 
	 * @param zone
	 *            The zone to remove
	 * @return Whether the zone was removed
	 */
	public boolean remove(Zone zone) {
		picker.remove(zone);
		return removeFromZoneList(zone);
	}

	private boolean removeFromZoneList(Zone zone) {
		for (Zone child : zone.children) {
			removeFromZoneList(child);
		}
		return zoneList.remove(zone);
	}

	/**
	 * Performs the drawing of the zones in order. Zone on top-most layer gets
	 * drawn last. Goes through the list on zones, pushes the current
	 * transformation matrix, applies the zone's matrix, draws the zone, pops
	 * the matrix, and when at the end of the list, it draws the touch points.
	 */
	public void draw() {
		for (Zone zone : zoneList) {
			if (zone.getParent() != null) {
				// the parent should handle the drawing
				continue;
			}
			zone.draw();
			// zone.drawForPickBuffer(parent.g);
		}
		if (drawTouchPoints) {
			drawTouchPoints();
		}

		// PApplet.println(parent.color((float) 0));
		// PApplet.println(parent.get(100, 100));
	}

	/**
	 * Draws a texture of the pickBuffer at the given x,y position with given
	 * width and height
	 * 
	 * @param x
	 *            - the x position to draw the pickBuffer image at
	 * @param y
	 *            - the y position to draw the pickBuffer image at
	 * @param w
	 *            - the width of the pickBuffer image to draw
	 * @param h
	 *            - the height of the pickBuffer image to draw
	 */
	public void drawPickBuffer(int x, int y, int w, int h) {
		// parent.g.image(picker.image, x, y, w, h);
	}

	/**
	 * Returns a vector containing all the current TuioObjects.
	 * 
	 * @return Vector<TuioObject>
	 */
	public Vector<TuioObject> getTuioObjects() {
		return tuioClient.getTuioObjects();
	}

	/**
	 * @return An array containing all touches that are currently NOT assigned
	 *         to zones
	 */
	public Touch[] getUnassignedTouches() {
		Map<Long, Touch> touches = getTouchMap();
		for (Zone zone : zoneList) {
			for (Touch touch : zone.getTouches()) {
				touches.remove(touch.sessionID);
			}
		}
		return touches.values().toArray(new Touch[touches.size()]);
	}

	/**
	 * @return An array containing all touches that are currently assigned to
	 *         zones
	 */
	public Touch[] getAssignedTouches() {
		List<Touch> touches = new ArrayList<Touch>();
		for (Zone zone : zoneList) {
			for (Touch touch : zone.getTouches()) {
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
	public void assignTouches(Zone zone, Touch... touches) {
		for (Touch touch : touches) {
			manager.assignTouch(zone, touch);
		}
	}

	/**
	 * Returns a collection containing all the current Touches(TuioCursors).
	 * 
	 * @return Collection<Touch>
	 */
	public Collection<Touch> getTouches() {
		return getTouchMap().values();
	}

	/**
	 * @return A Map<Long,Touch> indexing all touches by their session_id's
	 */
	public Map<Long, Touch> getTouchMap() {
		return SMTTouchManager.currentTouchState.idToTouches;
	}

	// /**
	// * This method returns all the current touches that are not actively
	// * affecting any Zone.
	// *
	// * @return the array
	// */
	// public Touch[] getUnassignedTouches() {
	// Vector<TuioCursor> cursors = tuioClient.getTuioCursors();
	// ArrayList<Touch> touches = new ArrayList<>();
	// for (TuioCursor c : cursors) {
	// Zone z = picker.getZoneFromId(c.getSessionID());
	// if (z == null) {
	// touches.add(new Touch(c));
	// }
	// }
	// return touches.toArray(new Touch[touches.size()]);
	// }

	/**
	 * @return An array of all zones that currently have touches/are active.
	 */
	public Zone[] getActiveZones() {
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for (Zone zone : zoneList) {
			if (zone.isActive()) {
				zones.add(zone);
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	/**
	 * @param zone
	 *            The zone to get the touches of
	 * @return A Collection<Touch> containing all touches from the given zone
	 */
	public Collection<Touch> getTouchCollectionFromZone(Zone zone) {
		return zone.getTouchCollection();
	}

	/**
	 * @param zone
	 *            The zone to get the touches of
	 * @return A Touch[] containing all touches from the given zone
	 */
	public Touch[] getTouchesFromZone(Zone zone) {
		return zone.getTouches();
	}

	/**
	 * Returns a the TuioObject associated with the session ID
	 * 
	 * @param s_id
	 *            long - Session ID of the TuioObject
	 * @return TuioObject
	 */
	public TuioObject getTuioObject(long s_id) {
		return tuioClient.getTuioObject(s_id);
	}

	/**
	 * Returns the Touch(TuioCursor) associated with the session ID
	 * 
	 * @param s_id
	 *            long - Session ID of the Touch(TuioCursor)
	 * @return TuioCursor
	 */
	public static Touch getTouch(long s_id) {
		return SMTTouchManager.currentTouchState.getById(s_id);
	}

	/**
	 * @param s_id
	 *            The session_id of the Touch to get the path start of
	 * @return A new Touch containing the state of the touch at path start
	 */
	public static Touch getPathStartTouch(long s_id) {
		TuioCursor c = tuioClient.getTuioCursor(s_id);
		Vector<TuioPoint> path = new Vector<TuioPoint>(c.getPath());

		TuioPoint start = path.firstElement();
		return new Touch(start.getTuioTime(), c.getSessionID(), c.getCursorID(), start.getX(),
				start.getY());
	}

	/**
	 * This creates a new Touch object from the last touch of a given Touch
	 * object, the new Touch will not update.
	 * <P>
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
	public static Touch getLastTouch(Touch current) {
		Vector<TuioPoint> path = current.path;
		if (path.size() > 1) {
			TuioPoint last = path.get(path.size() - 2);
			return new Touch(last.getTuioTime(), current.getSessionID(), current.getCursorID(),
					last.getX(), last.getY());
		}
		return null;
	}

	/**
	 * Returns the number of current Touches (TuioCursors)
	 * 
	 * @return number of current touches
	 */
	public int getTouchCount() {
		return tuioClient.getTuioCursors().size();
	}

	/**
	 * Manipulates the zone's position if it is throw-able after it has been
	 * released by a finger (cursor) Done before each call to draw. Uses the
	 * zone's x and y friction values.
	 */
	public void pre() {
		// TODO: provide some default assignment of touches
		manager.handleTouches();
		parent.g.flush();
		SMTUtilities.invoke(touch, parent);
		for (Zone zone : zoneList) {
			if (zone.getParent() != null) {
				// the parent should handle the touch calling
				continue;
			}
			if (zone.isChildActive()) {
				zone.touch();
			}
		}
	}

	/**
	 * Runs a server that sends TUIO events using Windows 7 Touch events
	 * 
	 * @param is64Bit
	 *            Whether to use the 64-bit version of the exe
	 */
	private void runWinTouchTuioServer(boolean is64Bit) {
		try {
			File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

			if (!(temp.delete())) {
				throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
			}

			if (!(temp.mkdir())) {
				throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
			}
			BufferedInputStream src = new BufferedInputStream(
					TouchClient.class.getResourceAsStream(is64Bit ? "/resources/Touch2Tuio_x64.exe"
							: "/resources/Touch2Tuio.exe"));
			final File exeTempFile = new File(is64Bit ? temp.getAbsolutePath()
					+ "\\Touch2Tuio_x64.exe" : temp.getAbsolutePath() + "\\Touch2Tuio.exe");
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(exeTempFile));
			byte[] tempexe = new byte[4 * 1024];
			int rc;
			while ((rc = src.read(tempexe)) > 0) {
				out.write(tempexe, 0, rc);
			}
			src.close();
			out.close();
			exeTempFile.deleteOnExit();

			BufferedInputStream dllsrc = new BufferedInputStream(
					TouchClient.class.getResourceAsStream(is64Bit ? "/resources/TouchHook_x64.dll"
							: "/resources/TouchHook.dll"));
			final File dllTempFile = new File(is64Bit ? temp.getAbsolutePath()
					+ "\\TouchHook_x64.dll" : temp.getAbsolutePath() + "\\TouchHook.dll");
			BufferedOutputStream outdll = new BufferedOutputStream(
					new FileOutputStream(dllTempFile));
			byte[] tempdll = new byte[4 * 1024];
			int rcdll;
			while ((rcdll = dllsrc.read(tempdll)) > 0) {
				outdll.write(tempdll, 0, rcdll);
			}
			dllsrc.close();
			outdll.close();
			dllTempFile.deleteOnExit();

			Thread serverThread = new Thread() {
				@Override
				public void run() {
					try {
						tuioServer = Runtime.getRuntime().exec(
								exeTempFile.getAbsolutePath() + " " + parent.frame.getTitle());
						BufferedInputStream err = new BufferedInputStream(
								tuioServer.getInputStream());
						String s = "";
						while (err.available() > 0) {
							s += err.read();
						}
						System.out.println(s);
						tuioServer.waitFor();
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
					catch (Exception e) {
						System.err.println("TUIO Server stopped!");
					}
				}
			};
			serverThread.start();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void runSmart2TuioServer() {
		try {
			File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

			if (!(temp.delete())) {
				throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
			}

			if (!(temp.mkdir())) {
				throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
			}
			BufferedInputStream src = new BufferedInputStream(
					TouchClient.class.getResourceAsStream("/resources/SMARTtoTUIO2.exe"));
			final File exeTempFile = new File(temp.getAbsolutePath() + "\\SMARTtoTUIO2.exe");
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(exeTempFile));
			byte[] tempexe = new byte[4 * 1024];
			int rc;
			while ((rc = src.read(tempexe)) > 0) {
				out.write(tempexe, 0, rc);
			}
			src.close();
			out.close();
			exeTempFile.deleteOnExit();

			BufferedInputStream sdksrc = new BufferedInputStream(
					TouchClient.class.getResourceAsStream("/resources/SMARTTableSDK.Core.dll"));
			final File sdkTempFile = new File(temp.getAbsolutePath() + "\\SMARTTableSDK.Core.dll");
			BufferedOutputStream outsdk = new BufferedOutputStream(
					new FileOutputStream(sdkTempFile));
			byte[] tempsdk = new byte[4 * 1024];
			int rcsdk;
			while ((rcsdk = sdksrc.read(tempsdk)) > 0) {
				outsdk.write(tempsdk, 0, rcsdk);
			}
			sdksrc.close();
			outsdk.close();
			sdkTempFile.deleteOnExit();

			BufferedInputStream tuiosrc = new BufferedInputStream(
					TouchClient.class.getResourceAsStream("/resources/libTUIO.dll"));
			final File tuioTempFile = new File(temp.getAbsolutePath() + "\\libTUIO.dll");
			BufferedOutputStream outtuio = new BufferedOutputStream(new FileOutputStream(
					tuioTempFile));
			byte[] tempdll = new byte[4 * 1024];
			int rctuio;
			while ((rctuio = tuiosrc.read(tempdll)) > 0) {
				outtuio.write(tempdll, 0, rctuio);
			}
			tuiosrc.close();
			outtuio.close();
			tuioTempFile.deleteOnExit();

			Thread serverThread = new Thread() {
				@Override
				public void run() {
					try {
						tuioServer = Runtime.getRuntime().exec(exeTempFile.getAbsolutePath());
						BufferedInputStream err = new BufferedInputStream(
								tuioServer.getInputStream());
						String s = "";
						while (err.available() > 0) {
							s += err.read();
						}
						System.out.println(s);
						tuioServer.waitFor();
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
					catch (Exception e) {
						System.err.println("SMARTtoTUIO2.exe Server stopped!");
					}
				}
			};
			serverThread.start();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Runs an exe from a path, presumably for translating native events to tuio
	 * events
	 */
	public static void runExe(final String path) {
		Thread serverThread = new Thread() {
			@Override
			public void run() {
				int max_failures = 3;
				for (int i = 0; i < max_failures; i++) {
					try {
						Process tuioServer = Runtime.getRuntime().exec(path);
						tuioServer.waitFor();
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
						break;
					}
					catch (Exception e) {
						System.err.println("Exe stopped!, restarting.");
					}
				}
				System.out.println("Stopped trying to run exe.");
			}
		};
		serverThread.start();
	}

	/**
	 * @param zone
	 *            The zone to place on top of the others
	 */
	public void putZoneOnTop(Zone zone) {
		if (zoneList.indexOf(zone) < zoneList.size() - 1) {
			if (zone.getParent() != null) {
				zone.getParent().putChildOnTop(zone);
			}
			zoneList.remove(zone);
			zoneList.add(zone);
			// change order of pickBuffer too
			picker.putZoneOnTop(zone);
		}
	}
}
