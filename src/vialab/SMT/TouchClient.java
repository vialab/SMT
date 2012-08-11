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

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import codeanticode.glgraphics.GLGraphics;
import codeanticode.glgraphics.GLGraphicsOffScreen;

import processing.core.PApplet;
import processing.core.PImage;
//import processing.core.PMatrix3D;
import processing.opengl.PGraphicsOpenGL;
import vialab.mouseToTUIO.MouseToTUIO;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;

/**
 * The TUIO Processing client.
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka, Zach Cook
 * @date Summer, 2011
 * @version 1.0
 */
public class TouchClient {
	private static int MAX_PATH_LENGTH = 100;

	/** Processing PApplet */
	static PApplet parent;

	/** The TouchClient */
	static TouchClient client;

	/** Gesture Handler */
	// static GestureHandler handler;

	/** Tuio Client that listens for Tuio Messages via port 3333 UDP */
	static TuioClient tuioClient;

	/** The main zone list */
	protected static CopyOnWriteArrayList<Zone> zoneList = new CopyOnWriteArrayList<Zone>();

	/** Flag for drawing touch points */
	static boolean drawTouchPoints = true;

	/** Matrix used to test if the zone has gone off the screen */
	// private PMatrix3D mTest = new PMatrix3D();

	protected SMTZonePicker picker;

	private SMTTuioListener listener;

	private SMTTouchManager manager;

	private Method touch;

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
	 * Constructor. Allows you to set the port to connect to.
	 * 
	 * @param parent
	 *            PApplet - The Processing PApplet
	 * @param port
	 *            int - The port to connect to.
	 */
	public TouchClient(PApplet parent, int port) {
		this(parent, port, true, false);
	}

	public TouchClient(PApplet parent, boolean emulateTouches) {
		this(parent, emulateTouches, false);
	}

	public TouchClient(PApplet parent, boolean emulateTouches, boolean fullscreen) {
		this(parent, 3333, emulateTouches, fullscreen);
	}

	public TouchClient(PApplet parent, int port, boolean emulateTouches, boolean fullscreen) {
		if (!(parent.g instanceof GLGraphics)) {
			System.err
					.println("Error: Cannot display zones unless renderer is GLGraphics, make sure to import the GLGraphics library, and in setup use size(width,height,GLConstants.GLGRAPHICS)");
		}

		parent.setLayout(new BorderLayout());

		if (emulateTouches) {
			MouseToTUIO mtt = new MouseToTUIO(parent.width, parent.height);
			parent.add(mtt);
			// register the listener if using opengl, as the component wont get
			// the events otherwise
			if (parent.g instanceof PGraphicsOpenGL) {
				parent.registerMouseEvent(mtt);
				parent.registerKeyEvent(mtt);
			}
		}

		// As of now, this code is dead, the toolkit only supports OpenGL, and
		// specifically needs GLGraphics to work properly
		if (!(parent.g instanceof PGraphicsOpenGL)) {
			parent.frame.removeNotify();
			if (fullscreen) {
				parent.frame.setUndecorated(true);
				parent.frame.setIgnoreRepaint(true);
				parent.frame.setExtendedState(Frame.MAXIMIZED_BOTH);

				GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice displayDevice = environment.getDefaultScreenDevice();

				DisplayMode mode = displayDevice.getDisplayMode();
				Rectangle fullScreenRect = new Rectangle(0, 0, mode.getWidth(), mode.getHeight());
				parent.frame.setBounds(fullScreenRect);
				parent.frame.setVisible(true);

				// the following is exclusive mode
				// displayDevice.setFullScreenWindow(parent.frame);
				//
				// Rectangle fullScreenRect = parent.frame.getBounds();

				parent.frame.setBounds(fullScreenRect);
				parent.setBounds((fullScreenRect.width - parent.width) / 2,
						(fullScreenRect.height - parent.height) / 2, parent.width, parent.height);
			}
			parent.frame.addNotify();
			parent.frame.toFront();
		}

		touch = SMTUtilities.getPMethod(parent, "touch");

		TouchClient.parent = parent;
		parent.registerDispose(this);
		parent.registerDraw(this);
		parent.registerPre(this);
		// handler = new GestureHandler();

		picker = new SMTZonePicker();

		TouchClient.client = this;

		tuioClient = new TuioClient(port);

		listener = new SMTTuioListener();

		manager = new SMTTouchManager(listener, picker);

		tuioClient.addTuioListener(listener);
		tuioClient.connect();
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
	 * @param zone
	 *            Zone - The zone to add to the list.
	 */
	public void add(Zone... zones) {
		for (Zone zone : zones) {
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
			parent.pushMatrix();
			parent.applyMatrix(zone.matrix);
			if (zone.isChildActive()) {
				zone.touch();
			}
			zone.draw();
			// zone.drawForPickBuffer(parent.g);
			parent.popMatrix();
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
		parent.g.image(((GLGraphicsOffScreen) picker.getGraphics()).getTexture(), x, y, w, h);
	}

	/**
	 * Returns a vector containing all the current TuioObjects.
	 * 
	 * @return Vector<TuioObject>
	 */
	public Vector<TuioObject> getTuioObjects() {
		return tuioClient.getTuioObjects();
	}

	public Touch[] getUnassignedTouches() {
		Map<Long, Touch> touches = getTouchMap();
		for (Zone zone : zoneList) {
			for (Touch touch : zone.getTouches()) {
				touches.remove(touch.sessionID);
			}
		}
		return touches.values().toArray(new Touch[touches.size()]);
	}

	public Touch[] getAssignedTouches() {
		List<Touch> touches = new ArrayList<Touch>();
		for (Zone zone : zoneList) {
			for (Touch touch : zone.getTouches()) {
				touches.add(touch);
			}
		}
		return touches.toArray(new Touch[touches.size()]);
	}

	public void assignTouches(Zone zone, Touch... touches) {
		for (Touch touch : touches) {
			manager.assignTouch(zone, touch);
		}
	}

	/**
	 * Returns a vector containing all the current Touches(TuioCursors).
	 * 
	 * @return Vector<TuioCursor>
	 */
	public Touch[] getTouches() {
		Collection<Touch> touchList = getTouchMap().values();
		return touchList.toArray(new Touch[touchList.size()]);
	}

	public Map<Long, Touch> getTouchMap() {
		Vector<TuioCursor> cursors = tuioClient.getTuioCursors();
		HashMap<Long, Touch> touches = new HashMap<Long, Touch>();
		for (TuioCursor c : cursors) {
			touches.put(c.getSessionID(), new Touch(c));
		}
		return touches;
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

	public Zone[] getActiveZones() {
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for (Zone zone : zoneList) {
			if (zone.isActive()) {
				zones.add(zone);
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	public Touch[] getTouchesFromZone(Zone zone) {
		Set<Long> ids = zone.getIds();

		Vector<TuioCursor> cursors = tuioClient.getTuioCursors();
		ArrayList<Touch> touches = new ArrayList<Touch>();
		for (Long id : ids) {
			TuioCursor tuioCursor = tuioClient.getTuioCursor(id);
			if (cursors.contains(tuioCursor)) {
				touches.add(new Touch(tuioCursor));
			}
		}
		return touches.toArray(new Touch[touches.size()]);
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
		return new Touch(tuioClient.getTuioCursor(s_id));
	}

	public static Touch getPathStartTouch(long s_id) {
		TuioCursor c = tuioClient.getTuioCursor(s_id);
		Vector<TuioPoint> path = new Vector<TuioPoint>(c.getPath());

		TuioPoint start = path.firstElement();
		return new Touch(start.getTuioTime(), c.getSessionID(), c.getCursorID(), start.getX(),
				start.getY());
	}

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
		manager.handleTouches(parent.g);
		SMTUtilities.invoke(touch, parent);
	}

	/**
	 * Disconnects the TuioClient when the PApplet is stopped. Shuts down any
	 * threads, disconnect from the net, unload memory, etc.
	 */
	public void dispose() {
		if (tuioClient.isConnected()) {
			tuioClient.disconnect();
		}
	}

	/**
	 * Runs a server that sends TUIO events using Windows 7 Touch events
	 * 
	 * @param touch2TuioExePath
	 *            String - the full name (including path) of the exe of
	 *            Touch2Tuio
	 * @see <a href='http://dm.tzi.de/touch2tuio/'>Touch2Tuio</a>
	 */
	public void runWinTouchTuioServer(String touch2TuioExePath) {
		final String tuioServerCommand = touch2TuioExePath + " " + parent.frame.getTitle();

		Thread serverThread = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						Process tuioServer = Runtime.getRuntime().exec(tuioServerCommand);
						tuioServer.waitFor();
					}
					catch (Exception e) {
						System.err.println("TUIO Server stopped!");
					}
				}
			}
		};
		serverThread.start();
	}

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
