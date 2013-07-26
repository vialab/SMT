package vialab.SMT;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;
import TUIO.*;

/**
 * Touch has state information of one touch and extends TuioCursor.
 * 
 * @see <a href=http://www.tuio.org/api/java/TUIO/TuioCursor.html>TuioCursor
 *      Javadoc</a>
 */
public class Touch extends TuioCursor {

	boolean isJointCursor = false;

	private CopyOnWriteArrayList<Zone> assignedZones = new CopyOnWriteArrayList<Zone>();

	long originalTimeMillis;

	long startTimeMillis;

	/** Processing PApplet */
	static PApplet applet = SMT.parent;
	/** The individual cursor ID number that is assigned to each TuioCursor. */
	public int cursorID;
	/** Reflects the current state of the TuioComponent. */
	public int state;
	/**
	 * The unique session ID number that is assigned to each TUIO object or
	 * cursor.
	 */
	public long sessionID;
	/** The X coordinate in pixels relative to the PApplet screen width. */
	public int x;
	/** The Y coordinate in pixels relative to the PApplet screen height. */
	public int y;
	/** The X-axis velocity value. */
	public float xSpeed;
	/** The Y-axis velocity value. */
	public float ySpeed;
	/** The motion speed value. */
	public float motionSpeed;
	/** The motion acceleration value. */
	public float motionAcceleration;

	/**
	 * The start time of the TuioCursor/Touch
	 */
	public TuioTime startTime;

	/**
	 * The current time of the TuioCursor/Touch
	 */
	public TuioTime currentTime;
	/**
	 * A Vector of TuioPoints containing all the previous positions of the TUIO
	 * component.
	 */
	public Vector<TuioPoint> path;

	/**
	 * True means the touch is currently down on the screen, false means that
	 * the touch has been lifted up.
	 */
	public boolean isDown;

	private TuioTime prevUpdateTime;

	/**
	 * This constructor takes the attributes of the provided TuioCursor and
	 * assigns these values to the newly created Touch.
	 * 
	 * @param tuioCursor  - TuioCursor: The TUIO Cursor
	 */
	public Touch(TuioCursor tuioCursor) {
		super(tuioCursor);
		this.updateTouch(tuioCursor);
		this.startTimeMillis = System.currentTimeMillis();
		this.originalTimeMillis = this.startTimeMillis;
	}

	/**
	 * This constructor takes the provided Session ID, Cursor ID, X and Y
	 * coordinate and assigns these values to the newly created Touch.
	 * 
	 * @param sessionID   - long: Session ID
	 * @param cursorID   - int: Cursor ID
	 * @param xCoord    - float: X Coordinate
	 * @param yCoord    - float: Y Coordinate
	 */
	public Touch(long sessionID, int cursorID, float xCoord, float yCoord) {
		super(sessionID, cursorID, xCoord, yCoord);
		this.cursorID = getCursorID();
		x = getScreenX(applet.width);
		y = getScreenY(applet.height);
		startTime = getStartTime();
		currentTime = getTuioTime();
		xSpeed = getXSpeed();
		ySpeed = getYSpeed();
		motionSpeed = getMotionSpeed();
		motionAcceleration = getMotionAccel();
		path = getPath();
		this.sessionID = getSessionID();
		state = getTuioState();
	}

	/**
	 * This constructor takes a TuioTime argument and assigns it along with the
	 * provided Session ID, Cursor ID, X and Y coordinate to the newly created
	 * Touch.
	 * 
	 * @param ttime   - TuioTime: TuioTime
	 * @param sessionID   - long: Session ID
	 * @param cursorID  - int: Cursor ID
	 * @param xCoord  - float: X Coordinate
	 * @param yCoord   - float: Y Coordinate
	 */
	public Touch(TuioTime ttime, long sessionID, int cursorID, float xCoord, float yCoord) {
		super(ttime, sessionID, cursorID, xCoord, yCoord);
		this.cursorID = getCursorID();
		x = getScreenX(applet.width);
		y = getScreenY(applet.height);
		startTime = getStartTime();
		currentTime = getTuioTime();
		xSpeed = getXSpeed();
		ySpeed = getYSpeed();
		motionSpeed = getMotionSpeed();
		motionAcceleration = getMotionAccel();
		path = getPath();
		this.sessionID = getSessionID();
		state = getTuioState();
	}

	/**
	 * 
	 * @return The Point containing the last point of the Touch
	 */
	public Point getLastPoint() {
		return getPointOnPath(path.size() - 2);
	}

	/**
	 * @return The Point containing the current point of the Touch
	 */
	public Point getCurrentPoint() {
		return getPointOnPath(path.size() - 1);
	}

	/**
	 * @param t
	 *            TuioCursor to update the Touch with, since Touch extends
	 *            TuioCursor, it can also take a Touch
	 */
	public void updateTouch(TuioCursor t) {
		prevUpdateTime = currentTime;

		cursorID = t.getCursorID();
		x = t.getScreenX(applet.width);
		y = t.getScreenY(applet.height);

		super.startTime = t.getStartTime();
		startTime = t.getStartTime();

		super.currentTime = t.getTuioTime();
		currentTime = t.getTuioTime();

		super.x_speed = t.getXSpeed();
		xSpeed = t.getXSpeed();

		super.y_speed = t.getYSpeed();
		ySpeed = t.getYSpeed();

		super.motion_speed = t.getMotionSpeed();
		motionSpeed = t.getMotionSpeed();

		super.motion_accel = t.getMotionAccel();
		motionAcceleration = t.getMotionAccel();

		super.path = t.getPath();
		path = t.getPath();

		super.session_id = t.getSessionID();
		sessionID = t.getSessionID();

		super.state = t.getTuioState();
		state = t.getTuioState();
	}

	/**
	 * Gets a Point on the Touch's path history
	 * 
	 * @param index
	 *            The index of the point on the Touch's path (0 is first Point,
	 *            Touch.path.size()-1 is the current Point)
	 * @return A Point containing the x,y values of the Touch's path at the
	 *         specified index, returns null if invalid index
	 */
	public Point getPointOnPath(int index) {
		if (index < 0 || index >= path.size()) {
			return null;
		}
		return new Point(path.get(index).getScreenX(applet.width), path.get(index).getScreenY(
				applet.height));
	}

	/**
	 * @return A Zone[] containing all Zones that currently have this touch
	 *         assigned
	 */
	public Zone[] getAssignedZones() {
		return assignedZones.toArray(new Zone[assignedZones.size()]);
	}

	/**
	 * @param zone
	 *            The Zone to assign this Touch to
	 */
	public void assignZone(Zone zone) {
		if (zone != null) {
			assignedZones.add(zone);
			if (!zone.isAssigned(this)) {
				zone.assign(this);
			}
			this.startTimeMillis = System.currentTimeMillis();
		}
	}

	/**
	 * @param zone
	 *            The Zone to unassign this Touch from
	 */
	public void unassignZone(Zone zone) {
		if (zone != null) {
			assignedZones.remove(zone);
			zone.unassign(this);
			this.startTimeMillis = this.originalTimeMillis;
		}
	}

	/**
	 * @return Whether this Touch is currently assigned to a Zone
	 */
	public boolean isAssigned() {
		return !assignedZones.isEmpty();
	}

	/**
	 * @return All the points on the path
	 */
	public Point[] getPathPoints() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < path.size(); i++) {
			points.add(getPointOnPath(i));
		}
		return points.toArray(new Point[points.size()]);
	}

	/**
	 * @return All the points on the path since the previous update
	 */
	public Point[] getNewPathPoints() {
		return getNewPathPoints(false);
	}

	/**
	 * @param join
	 *            Whether to start at the previous frame end point
	 * @return All the points on the path since the previous update
	 */
	public Point[] getNewPathPoints(boolean join) {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = path.size() - 1; i >= 0; i--) {
			TuioPoint tp = path.get(i);
			// once the TuioTimes are greater than the prevUpdateTime we have
			// got all of the new Points
			if (prevUpdateTime != null
					&& tp.getTuioTime().getTotalMilliseconds() <= prevUpdateTime
							.getTotalMilliseconds()) {
				if (join) {
					// one further back if we want to join it up
					points.add(new Point(tp.getScreenX(applet.width), tp.getScreenY(applet.height)));
				}
				break;
			}
			points.add(new Point(tp.getScreenX(applet.width), tp.getScreenY(applet.height)));
		}
		return points.toArray(new Point[points.size()]);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	/**
	 * @param t
	 *            Touch to calculate distance from
	 * @return The distance between this and the given Touch
	 */
	float distance(Touch t) {
		return (float) getCurrentPoint().distance(t.getCurrentPoint());
	}

	public TouchSource getTouchSource() {
		// bottom 48 bits of sessionID are used for actual IDs, top 16 bits for
		// partitioning by port, 0th partition/non partitioned will be used only
		// by the main tuiolistener, all others use the port number for the
		// partition index, and so can be used to find the port, and so the
		// device it came from
		if (sessionID >> 48 == 0) {
			return SMT.deviceMap.get(SMT.mainListenerPort);
		}
		return SMT.deviceMap.get((int) (sessionID >> 48));
	}
	
	/**
	 * @param z
	 * @return the x position of this Touch in local coordinates of the given zone
	 */
	public float getLocalX(Zone z){
		return z.getLocalX(this);
	}
	
	/**
	 * @param z
	 * @return the y position of this Touch in local coordinates of the given zone
	 */
	public float getLocalY(Zone z){
		return z.getLocalY(this);
	}	
}
