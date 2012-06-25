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

import codeanticode.glgraphics.*;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PMatrix3D;
import processing.core.PVector;
import TUIO.TuioTime;

/**
 * This is the main zone class which RectZone and ImageZone extend. It holds the
 * zone's coordinates, size, matrices, friction, etc.. It was done with help
 * from the tuioZones library.
 * <P>
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka
 * @date Summer, 2011
 * @version 1.0
 */

public class Zone extends PGraphicsDelegate implements PConstants {
	/** Processing PApplet */
	static PApplet applet = TouchClient.parent;

	/** TouchClient */
	static TouchClient client = TouchClient.client;

	/** The zone's transformation matrix */
	protected PMatrix3D matrix = new PMatrix3D();

	private PMatrix3D touchMatrix = new PMatrix3D();
	
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

	protected ArrayList<Zone> children = new ArrayList<Zone>();

	protected PGraphics drawGraphics;

	protected PGraphics pickGraphics;

	protected PGraphics touchGraphics;

	private int pickColor = -1;

	private TuioTime lastUpdate = TuioTime.getSessionTime();

	private boolean pickInitialized = false;

	private Zone parent = null;

	private float rntRadius;

	protected Method drawMethod = null;

	protected Method pickDrawMethod = null;

	protected Method touchMethod = null;

	protected String name = null;
	
	private static String defaultRenderer=GLGraphics.GLGRAPHICS;

	protected String renderer = defaultRenderer;

	/**
	 * Zone constructor
	 */
	public Zone() {
		this(null);
	}

	public Zone(String name) {
		this(name, defaultRenderer);
	}

	public Zone(String name, String renderer) {
		this(name, 0, 0, 1, 1, renderer);
	}

	public Zone(int x, int y, int width, int height) {
		this(x, y, width, height, defaultRenderer);
	}

	public Zone(int x, int y, int width, int height, String renderer) {
		this(null, x, y, width, height, renderer);
	}

	public Zone(String name, int x, int y, int width, int height) {
		this(name, x, y, width, height, defaultRenderer);
	}

	public Zone(String name, int x, int y, int width, int height, String renderer) {
		super();
		// drawGraphics = applet.createGraphics(width, height, JAVA2D);
		// pickGraphics = applet.createGraphics(width, height,
		// applet.g.getClass().getName());
		// touchGraphics = applet.createGraphics(width, height,
		// applet.g.getClass().getName());
		//
		// pg = drawGraphics;

		this.renderer = renderer;

		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;

		this.rntRadius = Math.min(width, height) / 4;

		// matrix.translate(x, y);

		init();

		setName(name);
	}

	public void setName(String name) {
		if (name != null) {
			drawMethod = SMTUtilities.getZoneMethod(applet, "draw", name, this.getClass());
			pickDrawMethod = SMTUtilities.getZoneMethod(applet, "pickDraw", name, this.getClass());
			touchMethod = SMTUtilities.getZoneMethod(applet, "touch", name, this.getClass());
		}

		this.name = name;
	}

	public void init() {
	    /*
		drawGraphics = applet.createGraphics(width, height, GLGraphics.GLGRAPHICS);
		pickGraphics = applet.createGraphics(width, height, GLGraphics.GLGRAPHICS);
		touchGraphics = applet.createGraphics(1, 1, P3D);
		*/
		drawGraphics = new GLGraphicsOffScreen(applet,width, height);
		pickGraphics = new GLGraphicsOffScreen(applet,width, height);
		touchGraphics = new GLGraphicsOffScreen(applet,1,1);
		
		pg = drawGraphics;

		resetMatrix();
		// matrix.reset();
		// matrix.translate(x, y);
	}

	public String getName() {
		return name;
	}

	public Collection<Touch> getTouches() {
		return Collections.unmodifiableCollection(activeTouches.values());
	}

	public Set<Long> getIds() {
		return Collections.unmodifiableSet(activeTouches.keySet());
	}

	public Map<Long, Touch> getTouchMap() {
		return Collections.unmodifiableMap(activeTouches);
	}

	public int getNumTouches() {
		return activeTouches.size();
	}

	public boolean isActive() {
		return !activeTouches.isEmpty();
	}

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
	 */
	public void assign(Touch... touches) {
		for (Touch touch : touches) {
			activeTouches.put(touch.sessionID, touch);
		}
	}

	public void assign(Iterable<? extends Touch> touches) {
		for (Touch touch : touches) {
			activeTouches.put(touch.sessionID, touch);
		}
	}

	public boolean isAssigned(Touch touch) {
		return isAssigned(touch.sessionID);
	}

	public boolean isAssigned(long id) {
		return activeTouches.containsKey(id);
	}

	public void unassign(Touch touch) {
		unassign(touch.sessionID);
	}

	public void unassignAll() {
		activeTouches.clear();
	}

	public void unassign(long sessionID) {
		if (activeTouches.containsKey(sessionID)) {
			activeTouches.remove(sessionID);
		}
	}

	@Override
	public void beginDraw() {
		pg = drawGraphics;
		super.beginDraw();
	}

	public void beginPickDraw() {
		pg = pickGraphics;
		super.beginDraw();
		noSmooth();
		noLights();
		noTint();
		noStroke();
		fill(pickColor);
		pickInitialized = true;
	}

	public void endPickDraw() {
		super.endDraw();
	}

	public void beginTouch() {
		pg = touchGraphics;
		super.beginDraw();
		touchMatrix.reset();
		super.setMatrix(touchMatrix);
	}

	public void endTouch() {
		super.endDraw();

		matrix.preApply(touchGraphics.getMatrix(new PMatrix3D()));
	}

	public int getPickColor() {
		return pickColor;
	}

	public void setPickColor(int color) {
		this.pickColor = color;
	}

	public void removePickColor() {
		pickGraphics = null;
		pickColor = -1;
	}

	public boolean add(Zone zone) {
		zone.parent = this;
		return children.add(zone);
	}

	public boolean remove(Zone child) {
		child.parent = null;
		return children.remove(child);
	}

	public void clearZones() {
		for (Zone zone : children) {
			zone.parent = null;
		}
		children.clear();
	}

	public Zone getChild(int index) {
		return children.get(index);
	}

	public int getChildCount() {
		return children.size();
	}

	public Zone[] getChildren() {
		return Collections.unmodifiableList(children).toArray(new Zone[getChildCount()]);
	}

	public Zone getParent() {
		return parent;
	}

	/**
	 * Reset the transformation matrix
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
		x = xIn;
		y = yIn;
		width = wIn;
		height = hIn;
		init();
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
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

	public float getRntRadius() {
		return rntRadius;
	}

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
		this.inverse.reset();
		this.inverse.apply(this.matrix);
		this.inverse.invert();
		PVector world = new PVector();
		PVector mouse = new PVector(x, y);
		this.inverse.mult(mouse, world);

		// return (world.x > this.getX()) && (world.x < this.getX() +
		// this.width)
		// && (world.y > this.getY()) && (world.y < this.getY() + this.height);
		return (world.x > 0) && (world.x < this.width) && (world.y > 0) && (world.y < this.height);
	}

	// /**
	// * Allows the student to scale the zone in their Processing sketch.
	// *
	// * @param sx
	// * float - Scale X Amount
	// * @param sy
	// * float - Scale Y Amount
	// */
	// @Override
	// public void scale(float sx, float sy) {
	// applet.scale(sx, sy);
	// }
	//
	// /**
	// * Allows the student to rotate the zone in their Processing sketch.
	// *
	// * @param angle
	// * float - Rotate angle
	// */
	// @Override
	// public void rotate(float angle) {
	// applet.rotate(angle);
	// }
	//
	// /**
	// * Allows the student to translate the zone in their Processing sketch.
	// *
	// * @param x
	// * float - the amount of translation in the x-direction
	// * @param y
	// * float - the amount of translation in the y-direction
	// */
	// @Override
	// public void translate(float x, float y) {
	// applet.translate(x, y);
	// }

	/**
	 * Translates the zone, its group, and its children if it is set to
	 * draggable, xDraggable or yDraggable
	 * 
	 * @param e
	 *            DragEvent - The DragEvent which encapsulates the event's
	 *            information
	 */
	public void drag() {
		drag(true, true);
	}

	/**
	 * Performs translate on the current graphics context. This is equivalent to
	 * a call to {@link Zone#rst(boolean, boolean, boolean)} with the parameters
	 * false, false, true, but is probably faster. Should typically be called
	 * inside a {@link Zone#beginTouch()} and {@link Zone#endTouch()}.
	 * 
	 * @param dragX
	 * @param dragY
	 */
	public void drag(boolean dragX, boolean dragY) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(1);
			drag(pairs.get(0), dragX, dragY);
		}
	}

	public void drag(Touch from, Touch to) {
		drag(from, to, true, true);
	}

	public void drag(Touch from, Touch to, boolean dragX, boolean dragY) {
		drag(new TouchPair(from, to), dragX, dragY);
	}

	public void drag(TouchPair pair, boolean dragX, boolean dragY) {
		if (pair.matches()) {
			lastUpdate = maxTime(pair);
			return;
		}

		if (dragX) {
			translate(pair.to.x - pair.from.x, 0);
		}

		if (dragY) {
			translate(0, pair.to.y - pair.from.y);
		}

		lastUpdate = maxTime(pair);
	}

	public void drag(int fromX, int fromY, int toX, int toY) {
		drag(fromX, fromY, toX, toY, true, true);
	}

	public void drag(int fromX, int fromY, int toX, int toY, boolean dragX, boolean dragY) {
		if (dragX) {
			translate(toX - fromX, 0);
		}

		if (dragY) {
			translate(0, toY - fromY);
		}
	}

	public void rst() {
		rst(true, true, true, true);
	}

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
	 * @param translate
	 */
	public void rst(boolean rotate, boolean scale, boolean translateX, boolean translateY) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(2);
			rst(pairs.get(0), pairs.get(1), rotate, scale, translateX, translateY);
		}
	}

	public void rst(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, true, true, true);
	}

	public void rst(Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale,
			boolean translate) {
		rst(from1, from2, to1, to2, rotate, scale, translate, translate);
	}

	public void rst(Touch from1, Touch from2, Touch to1, Touch to2, boolean rotate, boolean scale,
			boolean translateX, boolean translateY) {
		rst(new TouchPair(from1, to1), new TouchPair(from2, to2), rotate, scale, translateX,
				translateY);
	}

	public void rst(TouchPair first, TouchPair second, boolean rotate, boolean scale,
			boolean translateX, boolean translateY) {

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
			// TODO: translate to the zone's centre (for rotation and scale with
			// no translate)
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
			// TODO: translate back to the zone's centre
		}

		lastUpdate = maxTime(first, second);
	}

	/**
	 * Scales the zone if it is set to pinchable, xPinchable or yPinchable
	 * 
	 * @param e
	 *            PinchEvent - The PinchEvent which encapsulates the event's
	 *            information
	 */
	public void pinch() {
		rst(false, true, true);
	}

	public void pinch(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, false, true, true);
	}

	/**
	 * Rotates the zone if it is set to rotatable
	 * 
	 * @param e
	 *            RotateEvent - The RotateEvent which encapsulates the event's
	 *            information
	 */
	public void rotate() {
		rst(true, false, false);
	}

	public void rotate(Touch from1, Touch from2, Touch to1, Touch to2) {
		rst(from1, from2, to1, to2, true, false, false);
	}

	/**
	 * Rotates the zone around the specified x- & y-coordinates
	 * 
	 * @param angle
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
	 * Horizontal Swipe Event's default action is translating the zone in the
	 * x-direction by half of the horizontal swipe's distance.
	 * 
	 * @param e
	 *            HSwipeEvent - The Horizontal swipe event
	 */
	public void hSwipe() {
		drag(true, false);
	}

	/**
	 * Vertical Swipe Event's default action is translating the zone in the
	 * y-direction by half of the vertical swipe's distance.
	 * 
	 * @param e
	 *            VSwipeEvent - The Vertical swipe event
	 */
	public void vSwipe() {
		drag(false, true);
	}

	// /**
	// * Tap Event's default action is printing the line
	// * "You (double) tapped a zone".
	// *
	// * @param e
	// * TapEvent - The tap event
	// */
	// public void tap() {
	// }
	//
	// /**
	// * Tap and Hold Event's default action is rotating the zone 90 degrees.
	// *
	// * @param e
	// * TapEvent - The tap event
	// */
	// public void tapAndHoldEvent(TapAndHoldEvent e) {
	// }

	// @Override
	// public void beginDraw() {
	// // // super.beginDraw();
	// // applet.pushMatrix();
	// // applet.translate(x, y);
	// // applet.applyMatrix(matrix);
	//
	// pg.beginDraw();
	// applet.pushMatrix();
	// applet.applyMatrix(matrix);
	// }
	//
	// @Override
	// public void endDraw() {
	// // // super.endDraw();
	// // // applet.translate(-x, -y);
	// // applet.popMatrix();
	//
	// pg.endDraw();
	//
	// applet.image(pg, 0, 0);
	//
	// applet.popMatrix();
	// }

	public void draw() {
		draw(true);
	}

	public void draw(boolean drawChildren) {

		// temporarily make the current graphics context be this Zone's context
		// ( that way we don't have to prefix every call with zone.whatever() )
		PGraphics temp = applet.g;
		applet.g = drawGraphics;

		beginDraw();
		SMTUtilities.invoke(drawMethod, applet, this);
		endDraw();

		applet.g = temp;

		drawImpl(applet.g, drawGraphics, drawChildren);
	}

	public void drawForPickBuffer(PGraphics pickBuffer) {
		drawForPickBuffer(pickBuffer, true);
	}

	public void drawForPickBuffer(PGraphics pickBuffer, boolean drawChildren) {
		if (!pickInitialized && pickDrawMethod == null) {
			beginPickDraw();
			rect(0, 0, width, height);
			endPickDraw();
		}
		
		PGraphics temp = applet.g;
		applet.g = drawGraphics;

		beginPickDraw();
		SMTUtilities.invoke(pickDrawMethod, applet, this);
		endPickDraw();

		applet.g = temp;
		drawImpl(pickBuffer, pickGraphics, drawChildren);
	}
 
	protected void drawImpl(PGraphics g, PGraphics img, boolean drawChildren) {
		if (img != null) {
			if(img==pickGraphics){
				g.beginDraw();
				g.pushMatrix();
				//list ancestors in order from most distant to closest, in order to apply their matrix's in order
				LinkedList<Zone> ancestors=new LinkedList<Zone>();
				Zone z=this;
				while(z.getParent()!=null){
					z=z.getParent();
					ancestors.addFirst(z);
				}
				//apply ancestors matrix's in proper order to make sure image is correctly oriented
				for(Zone i: ancestors){
					g.applyMatrix(i.matrix);
				}
				g.applyMatrix(matrix);
			}
			
			g.image(((GLGraphicsOffScreen)img).getTexture(), 0, 0,width,height);
			
			if(img==pickGraphics){
				g.popMatrix();
				g.endDraw();
			}
		}

		if (drawChildren) {
			drawChildren(g, img == pickGraphics);
		}
	}

	protected void drawChildren(PGraphics g, boolean picking) {
		for (Zone child : children) {			
			if (picking) {
				child.drawForPickBuffer(g);
			}
			else {
				g.pushMatrix();
				g.applyMatrix(child.matrix);
				child.draw();
				g.popMatrix();
			}
		}
	}

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

	public void touch() {
		touch(true);
	}

	public void touch(boolean touchChildren) {
		touchImpl(touchChildren, false);
	}

	protected void touchImpl(boolean touchChildren, boolean isChild) {
		if (isActive()) {
			beginTouch();
			PGraphics temp = applet.g;
			applet.g = drawGraphics;

			SMTUtilities.invoke(touchMethod, applet, this);

			applet.g = temp;
			endTouch();
		}

		if (touchChildren) {
			for (Zone child : children) {
				if (child.isChildActive()) {
					child.backupMatrix=child.matrix.get();
					
					child.beginTouch();
					child.applyMatrix(matrix);
					child.endTouch();

					child.touchImpl(touchChildren, true);
					
					child.backupMatrix=null;

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

	public void rnt() {
		rnt(rntRadius);
	}

	public void rnt(float centreRadius) {
		if (!activeTouches.isEmpty()) {
			List<TouchPair> pairs = getTouchPairs(1);
			rnt(pairs.get(0), centreRadius);
		}
	}

	public void rnt(Touch from, Touch to) {
		rnt(new TouchPair(from, to), rntRadius);
	}

	public void rnt(Touch from, Touch to, float centreRadius) {
		rnt(new TouchPair(from, to), centreRadius);
	}

	public void rnt(TouchPair pair, float centreRadius) {

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

		// Vector a (oldCentre, t);
		// Vector b (oldCentre, tPrime);
		// Vector cross = a.crossProduct(b);
		// Vector trans(t, tPrime);
		//
		// glMatrixMode(GL_MODELVIEW);
		// glPushMatrix();
		// glLoadIdentity();
		//
		// glTranslatef( trans.x, trans.y, trans.z );
		// glTranslatef( oldCentre.x + a.x, oldCentre.y + a.y, oldCentre.z +
		// a.z);
		// glRotatef( radToDeg( a.angleTo(b) ), cross.x, cross.y, cross.z );
		// glTranslatef( -oldCentre.x - a.x, -oldCentre.y - a.y, -oldCentre.z -
		// a.z);
	}

	// public boolean isPressed() {
	// if (!activeTouches.isEmpty()) {
	// List<TouchPair> pairs = getTouchPairs(1);
	// if (pairs.get(0).isFirst()) {
	//
	// }
	// }
	// }
	//
	// public boolean isReleased() {
	//
	// }

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

	private Touch getActiveTouch(int n) {
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
			pairs.add(new TouchPair(SMTUtilities.getLastTouch(touch, lastUpdate), touch));
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

	public void touchDown(Touch touch) {
		assign(touch);
	}

	public void touchUp(Touch touch) {
		unassign(touch);
	}

	public void touchesMoved(List<Touch> currentLocalState) {
		assign(currentLocalState);
	}

	@Override
	public Zone clone() {
		Zone clone=new Zone(this.getName(),this.x,this.y,this.width,this.height);
		if(this.backupMatrix!=null){
			clone.matrix=this.backupMatrix.get();
		}else{
			clone.matrix=this.matrix.get();
		}
		clone.inverse=this.inverse.get();
		for(Zone child:this.getChildren()){
			clone.add(child.clone());
		}
		return clone;
	}	
}
