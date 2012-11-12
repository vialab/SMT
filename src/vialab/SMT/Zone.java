/*
 * Simple Multitouch Library Copyright 2011 Erik Paluka, Christopher Collins -
 * University of Ontario Institute of Technology Mark Hancock - University of
 * Waterloo
 * 
 * Parts of this library are based on: TUIOZones http://jlyst.com/tz/
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PMatrix3D;
import processing.core.PVector;
import vialab.SMT.KeyboardZone.KeyZone;
import TUIO.TuioTime;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This is the main zone class which all other Zones extend. It holds the zone's
 * coordinates, size, matrices, etc.. It was done with help from the tuioZones
 * library.
 * <P>
 * 
 * University of Ontario Institute of Technology. Erik Paluka, Summer Research
 * Assistant (Summer 2011). Zach Cook, SurfNet Intern (2012-2013). With Dr.
 * Christopher Collins collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka, Zach Cook
 * @version 1.0
 */
public class Zone extends PGraphicsDelegate implements PConstants, KeyListener {
	/** Processing PApplet */
	static PApplet applet;

	/** TouchClient */
	static TouchClient client;

	/** The zone's transformation matrix */
	protected PMatrix3D matrix = new PMatrix3D();

	private PMatrix3D backupMatrix = null;

	/** The zone's inverse transformation matrix */
	protected PMatrix3D inverse = new PMatrix3D();

	public int x, y, height, width;

	// A LinkedHashMap will allow insertion order to be maintained.
	// A synchronized one will prevent concurrent modification (which can happen
	// pretty easily with the draw loop + touch event handling).
	/** All of the currently active touches for this zone */
	public Map<Long, Touch> activeTouches = Collections
			.synchronizedMap(new LinkedHashMap<Long, Touch>());

	protected CopyOnWriteArrayList<Zone> children = new CopyOnWriteArrayList<Zone>();

	private int pickColor = -1;

	private TuioTime lastUpdate = TuioTime.getSessionTime();

	// private boolean pickInitialized = false;

	private Zone parent = null;

	private float rntRadius;

	protected Method drawMethod = null;

	protected Method pickDrawMethod = null;

	protected Method touchMethod = null;

	protected Method keyPressedMethod = null;

	protected Method keyReleasedMethod = null;

	protected Method keyTypedMethod = null;

	protected Method touchUpMethod = null;

	protected Method touchDownMethod = null;

	protected Method touchMovedMethod = null;

	protected String name = null;

	protected String renderer = null;

	protected static boolean grayscale = false;

	/**
	 * The direct flag controls whether rendering directly onto
	 * parent/screen/pickBuffer (direct), or into an image (not direct) If
	 * drawing into an image we have assured size(cant draw outside of zone),
	 * and background() will work for just the zone, but we lose a large amount
	 * of performance.
	 */
	private boolean direct = !false; // don't use indirect rendering yet by
										// default, still has sampling issues

	private boolean touchImpl;

	private boolean pickImpl;
	
	PGraphics drawPG;
	
	PGraphics zonePG;

	/**
	 * Check state of the direct flag.
	 * <P>
	 * The direct flag controls whether rendering directly onto
	 * parent/screen/pickBuffer (direct), or into an image (not direct) If
	 * drawing into an image we have assured size(cant draw outside of zone),
	 * and background() will work for just the zone, but we lose a large amount
	 * of performance.
	 * 
	 * @return whether zone is rendering directly onto screen/pickBuffer, or not
	 */
	public boolean isDirect() {
		return direct;
	}

	/**
	 * Change state of the direct flag.
	 * <P>
	 * The direct flag controls whether rendering directly onto
	 * parent/screen/pickBuffer (direct), or into an image (not direct) If
	 * drawing into an image we have assured size(cant draw outside of zone),
	 * and background() will work for just the zone, but we lose a large amount
	 * of performance.
	 * 
	 * @param direct
	 *            controls rendering directly onto screen/pickBuffer, or not
	 */
	public void setDirect(boolean direct) {
		this.direct = direct;
	}

	/**
	 * Zone constructor, no name, (x,y) position is (0,0) , width and height are
	 * 1
	 */
	public Zone() {
		this(null);
	}

	/**
	 * Zone constructor, with a name, (x,y) position is (0,0) , width and height
	 * are 1
	 * 
	 * @param name
	 *            Name of the zone, used in the draw, touch ,etc methods
	 */
	public Zone(String name) {
		this(name, TouchClient.defaultRenderer);
	}

	/**
	 * Zone constructor, with a name, (x,y) position is (0,0) , width and height
	 * are 1
	 * 
	 * @param name
	 *            Name of the zone, used in the draw, touch ,etc methods
	 * @param renderer
	 *            The PGraphics renderer that draws the Zone
	 */
	public Zone(String name, String renderer) {
		this(name, 0, 0, 1, 1, renderer);
	}

	/**
	 * @param x
	 *            The x position of the zone
	 * @param y
	 *            The y position of the zone
	 * @param width
	 *            of the zone
	 * @param height
	 *            of the zone
	 */
	public Zone(int x, int y, int width, int height) {
		this(x, y, width, height, TouchClient.defaultRenderer);
	}

	/**
	 * @param x
	 *            The x position of the zone
	 * @param y
	 *            The y position of the zone
	 * @param width
	 *            of the zone
	 * @param height
	 *            of the zone
	 * @param renderer
	 *            The renderer that draws the zone
	 */
	public Zone(int x, int y, int width, int height, String renderer) {
		this(null, x, y, width, height, renderer);
	}

	/**
	 * @param name
	 *            The name of the zone, used for the reflection methods
	 *            (drawname(),touchname(),etc)
	 * @param x
	 *            The x position of the zone
	 * @param y
	 *            The y position of the zone
	 * @param width
	 *            of the zone
	 * @param height
	 *            of the zone
	 */
	public Zone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, TouchClient.defaultRenderer);
	}

	/**
	 * @param name
	 *            The name of the zone, used for the reflection methods
	 *            (drawname(),touchname(),etc)
	 * @param x
	 *            The x position of the zone
	 * @param y
	 *            The y position of the zone
	 * @param width
	 *            of the zone
	 * @param height
	 *            of the zone
	 * @param renderer
	 *            The renderer that draws the zone
	 */
	public Zone(String name, int x, int y, int width, int height, String renderer) {
		super();

		applet = TouchClient.parent;
		client = TouchClient.client;

		if (applet == null | client == null) {
			System.err
					.println("Error: Cannot Instantiate zone before TouchClient, zones should be declared outside of setup, and initialized during setup after the touchClient has started. Expect serious issues if you see this message.");
		}

		this.renderer = renderer;

		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;

		this.rntRadius = Math.min(width, height) / 4;

		// matrix.translate(x, y);

		init();
		resetMatrix();
		setName(name);
	}

	/**
	 * @param name
	 *            The new name of the zone
	 */
	public void setName(String name) {
		loadMethods(name);
		this.name = name;
	}

	/**
	 * @param name
	 *            The name of the zone to load the methods from
	 */
	protected void loadMethods(String name) {
		boolean warnDraw = true;
		boolean warnTouch = true;
		boolean warnKeys = false;
		boolean warnPick = false;
		boolean warnTouchUDM = false;
		if (this instanceof ButtonZone || this instanceof ImageZone || this instanceof TabZone
				|| this instanceof TextZone || this instanceof SliderZone
				|| this instanceof KeyboardZone) {
			warnDraw = false;
		}
		if (this instanceof ButtonZone || this instanceof TabZone || this instanceof TextZone
				|| this instanceof SliderZone) {
			warnTouch = false;
		}

		if (TouchClient.warnUnimplemented != null) {
			if (TouchClient.warnUnimplemented.booleanValue()) {
				warnDraw = true;
				warnTouch = true;
				warnKeys = true;
				warnPick = true;
				warnTouchUDM = true;
			}
			else {
				warnDraw = false;
				warnTouch = false;
				warnKeys = false;
				warnPick = false;
			}
		}

		loadMethods(name, warnDraw, warnTouch, warnKeys, warnPick, warnTouchUDM);
	}

	/**
	 * @param name
	 *            The name of the zone to load methods from, used as a suffix
	 * @param warnDraw
	 *            Display a warning when the draw method doesn't exist
	 * @param warnTouch
	 *            Display a warning when the touch method doesn't exist
	 * @param warnKeys
	 *            Display a warning when the keyTyped/keyPressed/keyReleased
	 *            methods don't exist
	 * @param warnPick
	 *            Display a warning when the pickDraw method doesn't exist
	 * @param warnTouchUDM
	 *            Display a warning when the touchUp/touchDown/touchMoved
	 *            methods don't exist
	 */
	protected void loadMethods(String name, boolean warnDraw, boolean warnTouch, boolean warnKeys,
			boolean warnPick, boolean warnTouchUDM) {
		if (name != null) {
			drawMethod = SMTUtilities
					.getZoneMethod(applet, "draw", name, this.getClass(), warnDraw);

			pickDrawMethod = SMTUtilities.getZoneMethod(applet, "pickDraw", name, this.getClass(),
					warnPick);
			touchMethod = SMTUtilities.getZoneMethod(applet, "touch", name, this.getClass(),
					warnTouch);

			keyPressedMethod = SMTUtilities.getZoneMethod(applet, "keyPressed", name,
					this.getClass(), warnKeys);
			keyReleasedMethod = SMTUtilities.getZoneMethod(applet, "keyReleased", name,
					this.getClass(), warnKeys);
			keyTypedMethod = SMTUtilities.getZoneMethod(applet, "keyTyped", name, this.getClass(),
					warnKeys);
			touchUpMethod = SMTUtilities.getZoneMethod(applet, "touchUp", name, this.getClass(),
					warnTouchUDM);

			touchDownMethod = SMTUtilities.getZoneMethod(applet, "touchDown", name,
					this.getClass(), warnTouchUDM);

			touchMovedMethod = SMTUtilities.getZoneMethod(applet, "touchMoved", name,
					this.getClass(), warnTouchUDM);
		}

		touchImpl = SMTUtilities.checkImpl("touch", this.getClass());
		SMTUtilities.checkImpl("draw", this.getClass());
		pickImpl = SMTUtilities.checkImpl("pickDraw", this.getClass());
	}

	/**
	 * This function [re]creates the PGraphics for the zone, for advanced use
	 * only, override when something needs to be done when the PGraphics is
	 * recreated, ie. during a zone resize
	 */
	protected void init() {
		if(pg!=null){
			matrix.preApply((PMatrix3D) zonePG.getMatrix());
			zonePG.popMatrix();
		}
		
		//pgraphics for the zone
		zonePG = applet.createGraphics(width, height, renderer);
		//pgraphics for drawing
		drawPG=zonePG;
		//pgraphics that all methods call be default
		pg=zonePG;
		
		zonePG.pushMatrix();
		zonePG.setMatrix(new PMatrix3D());
	}

	/**
	 * @return The name of the zone
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return A Collection<Touch> containing all touches that are active on the
	 *         zone
	 */
	public Collection<Touch> getTouches() {
		return Collections.unmodifiableCollection(activeTouches.values());
	}

	/**
	 * @return A Set<Long> containing the long id's of the zone's touches
	 */
	public Set<Long> getIds() {
		return Collections.unmodifiableSet(activeTouches.keySet());
	}

	/**
	 * @return A Map<Long, Touch> which maps each touch id to the touch for the
	 *         zones active touches
	 */
	public Map<Long, Touch> getTouchMap() {
		return Collections.unmodifiableMap(activeTouches);
	}

	/**
	 * @return The number of touches currently on the zone
	 */
	public int getNumTouches() {
		return activeTouches.size();
	}

	/**
	 * @return Whether the zone has touches currently on it
	 */
	public boolean isActive() {
		return !activeTouches.isEmpty();
	}

	/**
	 * @return Whether the zone or one of its children has touches currently on
	 *         it
	 */
	public boolean isChildActive() {
		if (isActive()) {
			return true;
		}

		for (Zone child : children) {
			if (child.isChildActive()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Assigns a touch to this zone, maintaining the order of touches.
	 * 
	 * @param touches
	 *            A number of Touch objects, variable number of arguments
	 */
	public void assign(Touch... touches) {
		for (Touch touch : touches) {
			activeTouches.put(touch.sessionID, touch);
		}
	}

	/**
	 * Assigns a touch to this zone, maintaining the order of touches.
	 * 
	 * @param touches
	 *            A number of Touch objects, in an Iterable object that contains
	 *            Touch objects
	 */
	public void assign(Iterable<? extends Touch> touches) {
		for (Touch touch : touches) {
			activeTouches.put(touch.sessionID, touch);
		}
	}

	/**
	 * @param touch
	 * @return Whether the given touch is assigned to this zone
	 */
	public boolean isAssigned(Touch touch) {
		return isAssigned(touch.sessionID);
	}

	/**
	 * 
	 * @param id
	 * @return Whether the Touch corresponding to the given id is assigned to
	 *         this zone
	 */
	public boolean isAssigned(long id) {
		return activeTouches.containsKey(id);
	}

	/**
	 * Unassigns the given Touch from this zone, removing it from activeTouches.
	 * 
	 * @param touch
	 */
	public void unassign(Touch touch) {
		unassign(touch.sessionID);
	}

	/**
	 * Unassigns all Touch objects from this zone, clearing activeTouches.
	 */
	public void unassignAll() {
		activeTouches.clear();
	}

	/**
	 * Unassigns the Touch corresponding to the sessionID given from this zone,
	 * removing it from activeTouches.
	 * 
	 * @param sessionID
	 */
	public void unassign(long sessionID) {
		if (activeTouches.containsKey(sessionID)) {
			activeTouches.remove(sessionID);
		}
	}

	/**
	 * Override this if something needs to occur before any drawing commands
	 */
	@Override
	protected void beginDraw() {
		if (direct) {
			if (getParent() == null) {
				drawPG = applet.g;
			}
			else {
				drawPG = getParent().drawPG;
			}
			pg=drawPG;
			pg.pushMatrix();
			pg.applyMatrix(matrix);
		}
		else {
			super.beginDraw();
			background(0, 0, 0, 0);
		}
		pg.pushStyle();
	}

	/**
	 * Override this if something needs to occur after drawing commands
	 */
	@Override
	protected void endDraw() {
		if (!direct) {
			super.endDraw();
		}
		else {
			pg.popMatrix();
		}
		pg.popStyle();
		pg=zonePG;
	}

	/**
	 * Override this if something needs to occur before pickdrawing commands
	 */
	protected void beginPickDraw() {
		//update with changes from zonePG
		matrix.preApply((PMatrix3D) zonePG.getMatrix());
		zonePG.popMatrix();
		zonePG.pushMatrix();
		zonePG.setMatrix(new PMatrix3D());
		
		if (direct) {
			if (getParent() == null) {
				drawPG = applet.g;
			}
			else {
				drawPG = getParent().drawPG;
			}
			pg=drawPG;
			pg.pushMatrix();
			pg.applyMatrix(matrix);
		}
		else {
			super.beginDraw();
		}
		pg.pushStyle();

		noLights();
		noTint();
		noStroke();

		if (grayscale) {
			fill(pickColor);
		}
		else {
			fill(red(pickColor), green(pickColor), blue(pickColor));
		}
	}

	/**
	 * Override this if something needs to occur after pickdrawing commands
	 */
	protected void endPickDraw() {
		if (!direct) {
			super.endDraw();
		}
		else {
			pg.popMatrix();
		}
		pg.popStyle();
		pg=zonePG;
	}

	/**
	 * Call this to before matrix operations on a zone to make them apply when
	 * not in a touch method.
	 * <P>
	 * Needs a matching endTouch() call to be made afterwards, otherwise the
	 * matrix stack will overflow with too many pushMatrix commands.
	 * <P>
	 * Override this if something needs to occur before touch commands
	 */
	public void beginTouch() {
		pg=drawPG;
		pg.pushMatrix();
		pg.setMatrix(new PMatrix3D());
	}

	/**
	 * Call this to before matrix operations on a zone to make them apply when
	 * not in a touch method.
	 * <P>
	 * Needs a matching beginTouch() call to be made beforehand, otherwise the
	 * matrix stack will underflow with too many popMatrix commands.
	 * <P>
	 * Override this if something needs to occur after touch commands
	 */
	public void endTouch() {
		matrix.preApply((PMatrix3D) pg.getMatrix());
		pg.popMatrix();
	}

	int getPickColor() {
		return pickColor;
	}

	void setPickColor(int color) {
		this.pickColor = color;
		// pickInitialized = false;
	}

	void removePickColor() {
		pickColor = -1;
	}

	/**
	 * @param zone
	 *            The zone to add to this zone
	 * @return Whether the zone was successfully added or not
	 */
	public boolean add(Zone zone) {
		if (zone != null) {
			// if the parent already is in the client zoneList, then add the
			// child
			if (TouchClient.zoneList.contains(this)) {
				client.add(zone);
			}
			zone.parent = this;
			if (!children.contains(zone)) {
				return children.add(zone);
			}
		}
		return false;
	}

	/**
	 * @param child
	 *            The child of this zone to remove
	 * @return Whether the zone was successfully removed or not
	 */
	public boolean remove(Zone child) {
		if (child != null) {
			// if the parent is in the client zoneList, then remove the child
			// from
			// the zoneList, but only if it is in children
			if (TouchClient.zoneList.contains(this) && this.children.contains(child)) {
				client.remove(child);
			}
			child.parent = null;
			return children.remove(child);
		}
		return false;
	}

	/**
	 * This disconnects this zone from its children and vice versa
	 */
	public void clearZones() {
		for (Zone zone : children) {
			zone.parent = null;
		}
		children.clear();
	}

	/**
	 * @param index
	 * @return The child at the given index, or null if the index is invalid
	 */
	public Zone getChild(int index) {
		return children.get(index);
	}

	/**
	 * @return The number of children of this zone
	 */
	public int getChildCount() {
		return children.size();
	}

	/**
	 * @return An array containing this zone's children
	 */
	public Zone[] getChildren() {
		return Collections.unmodifiableList(children).toArray(new Zone[getChildCount()]);
	}

	/**
	 * @return The zone that is the parent of this zone
	 */
	public Zone getParent() {
		return parent;
	}

	/**
	 * Reset the transformation matrix of the zone
	 */
	@Override
	public void resetMatrix() {
		matrix.reset();
		matrix.translate(x, y);
	}

	/**
	 * Changes the coordinates and size of the zone.
	 * 
	 * @param xIn
	 *            int - The zone's new x-coordinate.
	 * @param yIn
	 *            int - The zone's new y-coordinate.
	 * @param wIn
	 *            int - The zone's new width.
	 * @param hIn
	 *            int - The zone's new height.
	 */
	public void setData(int xIn, int yIn, int wIn, int hIn) {
		this.x = xIn;
		this.y = yIn;
		this.width = wIn;
		this.height = hIn;
		init();
		resetMatrix();
	}

	/**
	 * Moves the zone to a given location with a reset matrix
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		resetMatrix();
	}

	/**
	 * This recreates the zone's PGraphics with a new size
	 * 
	 * @param w
	 *            The new width of the zone
	 * @param h
	 *            The new height of the zone
	 */
	@Override
	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		init();
		resetMatrix();
	}

	/**
	 * Get the zone x-coordinate. Upper left corner for rectangle.
	 * 
	 * @return x int representing the upper left x-coordinate of the zone.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the zone y-coordinate. Upper left corner for rectangle.
	 * 
	 * @return y int representing the upper left y-coordinate of the zone.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Get the zone's original width.
	 * 
	 * @return width int representing the width of the zone.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Get the zone's original height.
	 * 
	 * @return height int representing the height of the zone.
	 * 
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * @return The rotation radius of the zone, which is used by at least rnt()
	 *         for now, controlling when rotation is done
	 */
	public float getRntRadius() {
		return rntRadius;
	}

	/**
	 * @param rntRadius
	 *            The new rotation radius of the zone, which is used by at least
	 *            rnt() for now, controlling when rotation is done
	 */
	public void setRntRadius(float rntRadius) {
		this.rntRadius = rntRadius;
	}

	/**
	 * Tests to see if the x and y coordinates are in the zone. If the zone's
	 * matrix has been changed, reset its inverse matrix. This method is also
	 * used to place the x and y coordinate in the zone's matrix space and saves
	 * it to localX and localY.
	 * 
	 * 
	 * @param x
	 *            float - X-coordinate to test
	 * @param y
	 *            float - Y-coordinate to test
	 * @return boolean True if x and y is in the zone, false otherwise.
	 */
	public boolean contains(float x, float y) {
		PVector mouse = new PVector(x, y);
		PVector world = this.toZoneVector(mouse);

		return (world.x > 0) && (world.x < this.width) && (world.y > 0) && (world.y < this.height);
	}

	/**
	 * Translates the zone, its group, and its children
	 */
	public void drag() {
		drag(true, true);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param dragX
	 *            Whether to drag along the x-axis
	 * @param dragY
	 *            Whether to drag along the y-axis
	 */
	public void drag(boolean dragX, boolean dragY) {
		drag(dragX, dragX, dragY, dragY);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param dragLeft
	 *            Allow dragging left
	 * @param dragRight
	 *            Allow dragging Right
	 * @param dragUp
	 *            Allow dragging Up
	 * @param dragDown
	 *            Allow dragging Down
	 */
	public void drag(boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown) {
		drag(dragLeft, dragRight, dragUp, dragDown, Integer.MIN_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param dragLeft
	 *            Allow dragging left
	 * @param dragRight
	 *            Allow dragging Right
	 * @param dragUp
	 *            Allow dragging Up
	 * @param dragDown
	 *            Allow dragging Down
	 * @param leftLimit
	 *            Limit on how far to be able to drag left
	 * @param rightLimit
	 *            Limit on how far to be able to drag right
	 * @param upLimit
	 *            Limit on how far to be able to drag up
	 * @param downLimit
	 *            Limit on how far to be able to drag down
	 */
	public void drag(boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown,
			int leftLimit, int rightLimit, int upLimit, int downLimit) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(1);
			drag(pairs.get(0), dragLeft, dragRight, dragUp, dragDown, leftLimit, rightLimit,
					upLimit, downLimit);
		}
	}
	
	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param dragX
	 *            Allow dragging along the x-axis
	 * @param dragY
	 *            Allow dragging along the y-axis
	 * @param leftLimit
	 *            Limit on how far to be able to drag left
	 * @param rightLimit
	 *            Limit on how far to be able to drag right
	 * @param upLimit
	 *            Limit on how far to be able to drag up
	 * @param downLimit
	 *            Limit on how far to be able to drag down
	 */
	public void drag(boolean dragX, boolean dragY, int leftLimit, int rightLimit, int upLimit,
			int downLimit) {
		drag(dragX, dragX, dragY, dragY, leftLimit, rightLimit, upLimit, downLimit);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param from
	 *            The Touch to drag from
	 * @param to
	 *            The Touch to drag to
	 */
	public void drag(Touch from, Touch to) {
		drag(from, to, true, true);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param from
	 *            The Touch to drag from
	 * @param to
	 *            The Touch to drag to
	 * @param dragX
	 *            Whether to drag along the x-axis
	 * @param dragY
	 *            Whether to drag along the y-axis
	 */
	public void drag(Touch from, Touch to, boolean dragX, boolean dragY) {
		drag(from, to, dragX, dragX, dragY, dragY);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param from
	 *            The Touch to drag from
	 * @param to
	 *            The Touch to drag to
	 * @param dragLeft
	 *            Allow dragging left
	 * @param dragRight
	 *            Allow dragging Right
	 * @param dragUp
	 *            Allow dragging Up
	 * @param dragDown
	 *            Allow dragging Down
	 */
	public void drag(Touch from, Touch to, boolean dragLeft, boolean dragRight, boolean dragUp,
			boolean dragDown) {
		drag(new TouchPair(from, to), dragLeft, dragRight, dragUp, dragDown);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param pair
	 *            The TouchPair to drag to/from
	 * @param dragX
	 *            Whether to drag along the x-axis
	 * @param dragY
	 *            Whether to drag along the y-axis
	 */
	void drag(TouchPair pair, boolean dragLeft, boolean dragRight, boolean dragUp, boolean dragDown) {
		drag(pair, dragLeft, dragRight, dragUp, dragDown, Integer.MIN_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param pair
	 *            The TouchPair to drag to/from
	 * @param dragLeft
	 *            Allow dragging left
	 * @param dragRight
	 *            Allow dragging Right
	 * @param dragUp
	 *            Allow dragging Up
	 * @param dragDown
	 *            Allow dragging Down
	 * @param leftLimit
	 *            Limit on how far to be able to drag left
	 * @param rightLimit
	 *            Limit on how far to be able to drag right
	 * @param upLimit
	 *            Limit on how far to be able to drag up
	 * @param downLimit
	 *            Limit on how far to be able to drag down
	 */
	void drag(TouchPair pair, boolean dragLeft, boolean dragRight, boolean dragUp,
			boolean dragDown, int leftLimit, int rightLimit, int upLimit, int downLimit) {
		if (pair.matches()) {
			lastUpdate = maxTime(pair);
			return;
		}

		if ((dragLeft && pair.to.x < pair.from.x) || (dragRight && pair.to.x > pair.from.x)) {
			translate(pair.to.x - pair.from.x, 0);
		}

		if (dragUp && pair.to.y > pair.from.y || dragDown && pair.to.y < pair.from.y) {
			translate(0, pair.to.y - pair.from.y);
		}

		// get position with fromZoneVector (not counting any transformations
		// applied during since the last endTouch())
		PVector prevPos = fromZoneVector(new PVector(0, 0));

		// get the movement limits, left and up is negative, right and down
		// positive
		int maxLeftMove = -(int) (prevPos.x - leftLimit);
		int maxRightMove = (int) (rightLimit - (prevPos.x + width));
		int maxUpMove = -(int) (prevPos.y - upLimit);
		int maxDownMove = (int) (downLimit - (prevPos.y + height));

		// respect the limits by translating back to limit if needed
		if ((pair.to.x - pair.from.x) < maxLeftMove) {
			translate(-((pair.to.x - pair.from.x) - maxLeftMove), 0);
		}
		if ((pair.to.x - pair.from.x) > maxRightMove) {
			translate(-((pair.to.x - pair.from.x) - maxRightMove), 0);
		}
		if ((pair.to.y - pair.from.y) < maxUpMove) {
			translate(0, -((pair.to.y - pair.from.y) - maxUpMove));
		}
		if ((pair.to.y - pair.from.y) > maxDownMove) {
			translate(0, -((pair.to.y - pair.from.y) - maxDownMove));
		}
		lastUpdate = maxTime(pair);
	}

	/**
	 * * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param fromX
	 *            The x value to drag from
	 * @param fromY
	 *            The y value to drag from
	 * @param toX
	 *            The x value to drag to
	 * @param toY
	 *            The y value to drag to
	 */
	public void drag(int fromX, int fromY, int toX, int toY) {
		drag(fromX, fromY, toX, toY, true, true);
	}

	/**
	 * Performs translate on the current graphics context. Should typically be
	 * called inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param fromX
	 *            The x value to drag from
	 * @param fromY
	 *            The y value to drag from
	 * @param toX
	 *            The x value to drag to
	 * @param toY
	 *            The y value to drag to
	 * @param dragX
	 *            Whether to drag along the x-axis
	 * @param dragY
	 *            Whether to drag along the y-axis
	 */
	public void drag(int fromX, int fromY, int toX, int toY, boolean dragX, boolean dragY) {
		if (dragX) {
			translate(toX - fromX, 0);
		}

		if (dragY) {
			translate(0, toY - fromY);
		}
	}

	/**
	 * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 */
	public void rst() {
		rst(true, true, true, true);
	}

	/**
	 * * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param rotate
	 *            true if rotation should happen
	 * @param scale
	 *            true if scale should happen
	 * @param translate
	 *            true if translation should happen
	 */
	public void rst(boolean rotate, boolean scale, boolean translate) {
		rst(rotate, scale, translate, translate);
	}

	/**
	 * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param rotate
	 *            true if rotation should happen
	 * @param scale
	 *            true if scale should happen
	 * @param translateX
	 *            true if x-translation should happen
	 * @param translateY
	 *            true if y-translation should happen
	 */
	public void rst(boolean rotate, boolean scale, boolean translateX, boolean translateY) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(2);
			rst(pairs.get(0), pairs.get(1), rotate, scale, translateX, translateY);
		}
	}

	/**
	 * * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param from1
	 * @param from2
	 * @param to1
	 * @param to2
	 */
	public void rst(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, true, true, true);
	}

	/**
	 * * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param from1
	 * @param from2
	 * @param to1
	 * @param to2
	 * @param rotate
	 * @param scale
	 * @param translate
	 */
	public void rst(Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale,
			boolean translate) {
		rst(from1, from2, to1, to2, rotate, scale, translate, translate);
	}

	/**
	 * * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param from1
	 * @param from2
	 * @param to1
	 * @param to2
	 * @param rotate
	 * @param scale
	 * @param translateX
	 * @param translateY
	 */
	public void rst(Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale,
			boolean translateX, boolean translateY) {
		rst(new TouchPair(from1, to1), new TouchPair(from2, to2), rotate, scale, translateX,
				translateY);
	}

	/**
	 * * Performs rotate/scale/translate on the current graphics context. Should
	 * typically be called inside a {@link Zone#beginTouch()} and
	 * {@link Zone#endTouch()}.
	 * 
	 * @param first
	 *            The first TouchPair
	 * @param second
	 *            The second TouchPair
	 * @param rotate
	 * @param scale
	 * @param translateX
	 * @param translateY
	 */
	void rst(TouchPair first, TouchPair second, boolean rotate, boolean scale, boolean translateX,
			boolean translateY) {

		if (first.matches() && second.matches()) {
			// nothing to do
			lastUpdate = maxTime(first, second);
			return;
		}

		if (first.from == null) {
			first.from = first.to;
		}

		// PMatrix3D matrix = new PMatrix3D();
		if (translateX || translateY) {
			if (translateX) {
				translate(first.to.x, 0);
			}
			if (translateY) {
				translate(0, first.to.y);
			}
		}
		else {
			translate(this.x + this.width / 2, this.y + this.height / 2);
			// TODO: even better, add a centreOfRotation parameter
			// TODO: even more better, add a moving vs. non-moving
			// centreOfRotation
		}

		if (!second.isEmpty() && !second.isFirst() && (rotate || scale)) {
			PVector fromVec = second.getFromVec();
			fromVec.sub(first.getFromVec());

			PVector toVec = second.getToVec();
			toVec.sub(first.getToVec());

			float toDist = toVec.mag();
			float fromDist = fromVec.mag();
			if (toDist > 0 && fromDist > 0) {
				float angle = PVector.angleBetween(fromVec, toVec);
				PVector cross = PVector.cross(fromVec, toVec, new PVector());
				cross.normalize();

				if (angle != 0 && cross.z != 0 && rotate) {
					rotate(angle, cross.x, cross.y, cross.z);
				}
				if (scale) {
					scale(toDist / fromDist);
				}
			}
		}

		if (translateX || translateY) {
			if (translateX) {
				translate(-first.from.x, 0);
			}
			if (translateY) {
				translate(0, -first.from.y);
			}
		}
		else {
			translate(-(this.x + this.width / 2), -(this.y + this.height / 2));
		}

		lastUpdate = maxTime(first, second);
	}

	/**
	 * Rotates and scales the zone. Uses two touches. Only works inside the
	 * zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void rs() {
		rst(true, true, false);
	}

	/**
	 * Scales the zone. Uses two touches Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void pinch() {
		rst(false, true, true);
	}

	/**
	 * Scales the zone. Uses 4 Touch objects, specifiying the from and to for
	 * two moving touchpoints
	 * 
	 * @param from1
	 * @param from2
	 * @param to1
	 * @param to2
	 */
	public void pinch(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, false, true, true);
	}

	/**
	 * Rotates the zone, using two touches, about the centre Only works inside
	 * the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void rotate() {
		rst(true, false, false);
	}

	/**
	 * Rotates the zone. Uses 4 Touch objects, specifiying the from and to for
	 * two moving touchpoints
	 * 
	 * @param from1
	 * @param from2
	 * @param to1
	 * @param to2
	 */
	public void rotate(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, true, false, false);
	}

	/**
	 * Rotates the zone around the specified x- & y-coordinates
	 * 
	 * @param angle
	 *            The angle to rotate specified in radians
	 * @param x
	 * @param y
	 */
	public void rotateAbout(float angle, int x, int y) {
		translate(x, y);
		rotate(angle);
		translate(-x, -y);
	}

	/**
	 * Rotates the zone around either the centre or corner
	 * 
	 * @param angle
	 *            The angle to rotate specified in radians
	 * @param mode
	 *            CENTER or CORNER
	 */
	public void rotateAbout(float angle, int mode) {
		if (mode == CORNER) {
			rotateAbout(angle, x, y);
		}
		else if (mode == CENTER) {
			rotateAbout(angle, x + width / 2, y + height / 2);
		}
	}

	/**
	 * Horizontal Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void hSwipe() {
		drag(true, false);
	}

	/**
	 * Horizontal Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void hSwipe(int leftLimit, int rightLimit) {
		drag(true, false, leftLimit, rightLimit, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Vertical Swipe. Uses a single touch. Only works inside the zone's touch
	 * method, or between calls to beginTouch() and endTouch()
	 */
	public void vSwipe() {
		drag(false, true);
	}

	/**
	 * Swipe which only allows moving left. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeLeft() {
		drag(true, false, false, false);
	}

	/**
	 * Swipe which only allows moving right. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeRight() {
		drag(false, true, false, false);
	}

	/**
	 * Swipe which only allows moving up. Uses a single touch. Only works inside
	 * the zone's touch method, or between calls to beginTouch() and endTouch()
	 */
	public void swipeUp() {
		drag(false, false, true, false);
	}

	/**
	 * Swipe which only allows moving down. Uses a single touch. Only works
	 * inside the zone's touch method, or between calls to beginTouch() and
	 * endTouch()
	 */
	public void swipeDown() {
		drag(false, false, false, true);
	}

	void draw() {
		draw(true, false);
	}

	void drawForPickBuffer() {
		draw(true, true);
	}

	void draw(boolean drawChildren, boolean picking) {
		if (picking) {
			beginPickDraw();
		}
		else {
			beginDraw();
		}
		PGraphics temp = applet.g;
		applet.g = pg;

		if (picking) {
			if (pickDrawMethod == null && !pickImpl) {
				rect(0, 0, width, height);
			}
			else {
				pickDrawImpl();
				SMTUtilities.invoke(pickDrawMethod, applet, this);
			}
		}
		else {
			drawImpl();
			SMTUtilities.invoke(drawMethod, applet, this);
		}
		if (drawChildren) {
			drawDirectChildren(pg, picking);
		}

		applet.g = temp;

		if (picking) {
			endPickDraw();
		}
		else {
			endDraw();
		}
		if (!direct) {
			drawIndirectImage(pg, drawChildren, picking);
		}
	}

	void drawIndirectImage(PGraphics img, boolean drawChildren, boolean picking) {
		if (img != null) {
			img.flush();
			applet.g.pushMatrix();
			// apply parent matrices from farthest ancestor to parent
			LinkedList<Zone> ancestors = new LinkedList<Zone>();
			Zone parent = this.getParent();
			while (parent != null) {
				ancestors.addFirst(parent);
				parent = parent.getParent();
			}
			for (Zone zone : ancestors) {
				applet.g.applyMatrix(zone.matrix);
			}
			applet.g.applyMatrix(matrix);
			applet.g.image(img.get(), 0, 0);
			applet.g.popMatrix();

			if (drawChildren) {
				drawIndirectChildren(applet.g, picking);
			}
		}

	}

	void drawDirectChildren(PGraphics pg, boolean picking) {
		for (Zone child : children) {
			if (child.direct) {
				drawChild(child, pg, picking);
			}
		}
	}

	void drawIndirectChildren(PGraphics g, boolean picking) {
		for (Zone child : children) {
			if (!child.direct) {
				drawChild(child, g, picking);
			}
		}
	}

	void drawChildren(PGraphics g, boolean picking) {
		for (Zone child : children) {
			drawChild(child, g, picking);
		}
	}

	void drawChild(Zone child, PGraphics pg, boolean picking) {
		// only draw/pickDraw when the child is in zonelist (parent zone's are
		// responsible for adding/removing child to/from zonelist)
		if (TouchClient.zoneList.contains(child)) {
			child.draw(true, picking);
		}
	}

	/**
	 * Draws the rotation radius of the zone
	 */
	public void drawRntCircle() {
		pushStyle();
		pushMatrix();
		noFill();
		strokeWeight(5);
		stroke(255, 127, 39, 155);
		ellipseMode(RADIUS);
		ellipse(width / 2, height / 2, rntRadius, rntRadius);
		popMatrix();
		popStyle();
	}

	void touch() {
		touch(true);
	}

	void touch(boolean touchChildren) {
		touch(touchChildren, false);
	}

	void touch(boolean touchChildren, boolean isChild) {
		if (isActive()) {
			PGraphics temp = applet.g;
			applet.g = pg;

			beginTouch();
			touchImpl();
			SMTUtilities.invoke(touchMethod, applet, this);
			endTouch();

			if (touchMethod == null && !(this instanceof ButtonZone) && !(this instanceof KeyZone)
					&& !(this instanceof SliderZone) && !touchImpl) {
				unassignAll();
			}

			applet.g = temp;
		}

		if (touchChildren) {
			for (Zone child : children) {
				if (child.isChildActive()) {
					child.backupMatrix = child.matrix.get();

					child.beginTouch();
					child.applyMatrix(matrix);
					child.endTouch();

					child.touch(touchChildren, true);

					child.backupMatrix = null;

					child.beginTouch();
					PMatrix3D inverse = new PMatrix3D();
					inverse.apply(matrix);
					inverse.invert();
					child.applyMatrix(inverse);
					child.endTouch();
				}
			}
		}
	}

	/**
	 * Rotate and translate.\n Single finger gesture, Which only translates
	 * inside of the rotation radius, and rotates and translates outside of this
	 * radius.\n Using a default radius of min(width, height)/4, A quarter of
	 * the smallest side of the zone. Only works inside the zone's touch method,
	 * or between calls to beginTouch() and endTouch().
	 */
	public void rnt() {
		rnt(rntRadius);
	}

	/**
	 * Rotate and translate. Single finger gesture, Which only translates inside
	 * of the rotation radius, and rotates and translates outside of this
	 * radius. Only works inside the zone's touch method, or between calls to
	 * beginTouch() and endTouch()
	 * 
	 * @param centreRadius
	 *            The rotation radius (how far out from centre before rotation
	 *            is applied)
	 */
	public void rnt(float centreRadius) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(1);
			rnt(pairs.get(0), centreRadius);
		}
	}

	/**
	 * Rotate and translate. Single finger gesture, Which only translates inside
	 * of the rotation radius, and rotates and translates outside of this
	 * radius. Using a default radius of min(width, height)/4, A quarter of the
	 * smallest side of the zone. Only works inside the zone's touch method, or
	 * between calls to beginTouch() and endTouch()
	 * 
	 * @param from
	 *            The Touch to translate from
	 * @param to
	 *            The Touch to translate to
	 */
	public void rnt(Touch from, Touch to) {
		rnt(new TouchPair(from, to), rntRadius);
	}

	/**
	 * Rotate and translate. Single finger gesture, Which only translates inside
	 * of the rotation radius, and rotates and translates outside of this
	 * radius. Only works inside the zone's touch method, or between calls to
	 * beginTouch() and endTouch()
	 * 
	 * @param from
	 *            The Touch to translate from
	 * @param to
	 *            The Touch to translate to
	 * @param centreRadius
	 *            The rotation radius (how far out from centre before rotation
	 *            is applied)
	 */
	public void rnt(Touch from, Touch to, float centreRadius) {
		rnt(new TouchPair(from, to), centreRadius);
	}

	/**
	 * Rotate and translate. Single finger gesture, Which only translates inside
	 * of the rotation radius, and rotates and translates outside of this
	 * radius. Only works inside the zone's touch method, or between calls to
	 * beginTouch() and endTouch()
	 * 
	 * @param pair
	 *            A TouchPair to use for the from/to points
	 * @param centreRadius
	 *            The rotation radius (how far out from centre before rotation
	 *            is applied)
	 */
	void rnt(TouchPair pair, float centreRadius) {

		if (pair.matches()) {
			// nothing to do
			lastUpdate = maxTime(pair);
			return;
		}

		// PMatrix3D matrix = new PMatrix3D();
		translate(pair.to.x, pair.to.y);

		PVector centre = getCentre();

		PVector fromVec = pair.getFromVec();
		fromVec.sub(centre);

		PVector toVec = pair.getToVec();
		toVec.sub(centre);

		float toDist = toVec.mag();
		float fromDist = fromVec.mag();
		if (toDist > 0 && fromDist > centreRadius) {
			float angle = PVector.angleBetween(fromVec, toVec);
			PVector cross = PVector.cross(fromVec, toVec, new PVector());
			cross.normalize();

			if (angle != 0 && cross.z != 0) {
				rotate(angle, cross.x, cross.y, cross.z);
			}
		}

		translate(-pair.from.x, -pair.from.y);
		lastUpdate = maxTime(pair);
	}

	/**
	 * @return A PVector containing the centre point of the Zone
	 */
	public PVector getCentre() {
		PVector centre = new PVector(width / 2, height / 2);
		return matrix.mult(centre, new PVector());
	}

	private TuioTime maxTime(Iterable<Touch> touches) {
		ArrayList<TuioTime> times = new ArrayList<TuioTime>();
		for (Touch t : touches) {
			if (t != null) {
				times.add(t.currentTime);
			}
		}
		return Collections.max(times, SMTUtilities.tuioTimeComparator);
	}

	private TuioTime maxTime(TouchPair... pairs) {
		ArrayList<Touch> touches = new ArrayList<Touch>(pairs.length * 2);
		for (TouchPair pair : pairs) {
			touches.add(pair.from);
			touches.add(pair.to);
		}
		return maxTime(touches);
	}

	/**
	 * Gets a touch on the zone by index, 0 is first touch, 1 is second, etc
	 * 
	 * @param n
	 *            Which touch to get
	 * @return The Nth touch on the zone, or null if there is no such touch
	 */
	public Touch getActiveTouch(int n) {
		int i = 0;
		for (Touch t : activeTouches.values()) {
			if (i == n) {
				return t;
			}
			i++;
		}
		return null;
	}

	private List<TouchPair> getTouchPairs() {
		ArrayList<TouchPair> pairs = new ArrayList<TouchPair>(activeTouches.size());
		for (Touch touch : activeTouches.values()) {
			pairs.add(new TouchPair(SMTUtilities.getLastTouchAtTime(touch, lastUpdate), touch));
		}
		return pairs;
	}

	private List<TouchPair> getTouchPairs(int minSize) {
		List<TouchPair> pairs = getTouchPairs();
		for (int i = pairs.size(); i < minSize; i++) {
			pairs.add(new TouchPair());
		}
		return pairs;
	}

	void touchDown(Touch touch) {
		assign(touch);
	}

	void touchUp(Touch touch) {
		unassign(touch);
	}

	void touchesMoved(List<Touch> currentLocalState) {
		assign(currentLocalState);
	}

	/**
	 * Clones the zone and all child zones
	 */
	@Override
	public Zone clone() {
		return clone(Integer.MAX_VALUE);
	}

	/**
	 * Clones the zone and optionally any child zones up to the specified
	 * generation of children
	 * 
	 * @param cloneMaxChildGenerations
	 *            - Max limit on how many generations of children to clone (0 -
	 *            None, 1 - First Generation children, ... , Integer.MAX_VALUE -
	 *            All generations of children)
	 */
	public Zone clone(int cloneMaxChildGenerations) {
		Zone clone = new Zone(this.getName(), this.x, this.y, this.width, this.height);
		// use the backupMatrix if the zone being clone has its matrix modified
		// by parent, and so has backupMatrix set
		if (this.backupMatrix != null) {
			clone.matrix = this.backupMatrix.get();
		}
		else {
			clone.matrix = this.matrix.get();
		}
		clone.inverse = this.inverse.get();

		if (cloneMaxChildGenerations > 0) {
			for (Zone child : this.getChildren()) {
				clone.add(child.clone(cloneMaxChildGenerations - 1));
			}
		}
		return clone;
	}

	/**
	 * This makes a PVector into one relative to the zone's matrix
	 * 
	 * @param global
	 *            The PVector to put into relative coordinate space
	 * @return A PVector relative to the zone's coordinate space
	 */
	public PVector toZoneVector(PVector global) {
		PMatrix3D temp = new PMatrix3D();
		// list ancestors in order from most distant to closest, in order to
		// apply their matrix's in order
		LinkedList<Zone> ancestors = new LinkedList<Zone>();
		Zone zone = this;
		while (zone.getParent() != null) {
			zone = zone.getParent();
			ancestors.addFirst(zone);
		}
		// apply ancestors matrix's in proper order to make sure image is
		// correctly oriented, but not when backupMatrix is set, as then it
		// already has its parents applied to it.
		if (backupMatrix == null) {
			for (Zone i : ancestors) {
				temp.apply(i.matrix);
			}
		}
		temp.apply(matrix);

		temp.invert();
		PVector out = new PVector();
		temp.mult(global, out);
		return out;
	}

	/**
	 * This makes a PVector into a global coordinates from the given local
	 * zone's coordinate space
	 * 
	 * @param local
	 *            The PVector to put into global coordinate space
	 * @return A PVector relative to the global coordinate space
	 */
	public PVector fromZoneVector(PVector local) {
		PMatrix3D temp = new PMatrix3D();
		// list ancestors in order from most distant to closest, in order to
		// apply their matrix's in order
		LinkedList<Zone> ancestors = new LinkedList<Zone>();
		Zone zone = this;
		while (zone.getParent() != null) {
			zone = zone.getParent();
			ancestors.addFirst(zone);
		}
		// apply ancestors matrix's in proper order to make sure image is
		// correctly oriented, but not when backupMatrix is set, as then it
		// already has its parents applied to it.
		if (backupMatrix == null) {
			for (Zone i : ancestors) {
				temp.apply(i.matrix);
			}
		}
		temp.apply(matrix);

		PVector out = new PVector();
		temp.mult(local, out);
		return out;
	}

	/**
	 * Changes the ordering of the child zones, placing the given one on top
	 * 
	 * @param zone
	 *            The child to place on top of the others
	 */
	public void putChildOnTop(Zone zone) {
		// only remove and add if actually in the children arraylist and not
		// already the last(top) already
		if (this.children.contains(zone) && (children.indexOf(zone) < children.size() - 1)) {
			this.children.remove(zone);
			this.children.add(zone);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressedImpl(e);
		SMTUtilities.invoke(keyPressedMethod, applet, this, e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyReleasedImpl(e);
		SMTUtilities.invoke(keyReleasedMethod, applet, this, e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keyTypedImpl(e);
		SMTUtilities.invoke(keyTypedMethod, applet, this, e);
	}

	void touchUpInvoker() {
		touchUpImpl();
		SMTUtilities.invoke(touchUpMethod, applet, this);
	}

	void touchDownInvoker() {
		touchDownImpl();
		SMTUtilities.invoke(touchDownMethod, applet, this);
	}

	void touchMovedInvoker() {
		touchMovedImpl();
		SMTUtilities.invoke(touchMovedMethod, applet, this);
	}

	/**
	 * Override to specify a default behavior for draw
	 */
	protected void drawImpl() {
	}

	/**
	 * Override to specify a default behavior for touch
	 */
	protected void touchImpl() {
	}

	/**
	 * Override to specify a default behavior for pickDraw
	 */
	protected void pickDrawImpl() {
	}

	/**
	 * Override to specify a default behavior for touchDown
	 */
	protected void touchDownImpl() {
	}

	/**
	 * Override to specify a default behavior for touchUp
	 */
	protected void touchUpImpl() {
	}

	/**
	 * Override to specify a default behavior for touchMoved
	 */
	protected void touchMovedImpl() {
	}

	/**
	 * Override to specify a default behavior for keyPressed
	 */
	protected void keyPressedImpl(KeyEvent e) {
	}

	/**
	 * Override to specify a default behavior for keyReleased
	 */
	protected void keyReleasedImpl(KeyEvent e) {
	}

	/**
	 * Override to specify a default behavior for keyTyped
	 */
	protected void keyTypedImpl(KeyEvent e) {
	}
}
