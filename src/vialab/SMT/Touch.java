package vialab.SMT;

import java.awt.Point;
import java.util.Vector;

import processing.core.PApplet;
import TUIO.*;

/**
 * Touch has some state information and extends TuioCursor
 */
public class Touch extends TuioCursor {

	/** Processing PApplet */
	static PApplet applet = TouchClient.parent;
	/** The individual cursor ID number that is assigned to each TuioCursor. */
	public int cursorId;
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

	/**
	 * This constructor takes the attributes of the provided TuioCursor and
	 * assigns these values to the newly created Touch.
	 * 
	 * @param t
	 *            - TuioCursor
	 */
	public Touch(TuioCursor t) {
		super(t);
		this.updateTouch(t);
	}

	/**
	 * This constructor takes the provided Session ID, Cursor ID, X and Y
	 * coordinate and assigns these values to the newly created Touch.
	 * 
	 * @param si
	 *            - Session ID
	 * @param ci
	 *            - Cursor ID
	 * @param xp
	 *            - X Coordinate
	 * @param yp
	 *            - Y Coordinate
	 */
	public Touch(long si, int ci, float xp, float yp) {
		super(si, ci, xp, yp);
		cursorId = getCursorID();
		x = getScreenX(applet.width);
		y = getScreenY(applet.height);
		startTime = getStartTime();
		currentTime = getTuioTime();
		xSpeed = getXSpeed();
		ySpeed = getYSpeed();
		motionSpeed = getMotionSpeed();
		motionAcceleration = getMotionAccel();
		path = getPath();
		sessionID = getSessionID();
		state = getTuioState();
	}

	/**
	 * This constructor takes a TuioTime argument and assigns it along with the
	 * provided Session ID, Cursor ID, X and Y coordinate to the newly created
	 * Touch.
	 * 
	 * @param ttime
	 *            - TuioTime
	 * @param si
	 *            - Session ID
	 * @param ci
	 *            - Cursor ID
	 * @param xp
	 *            - X Coordinate
	 * @param yp
	 *            - Y Coordinate
	 */
	public Touch(TuioTime ttime, long si, int ci, float xp, float yp) {
		super(ttime, si, ci, xp, yp);
		cursorId = getCursorID();
		x = getScreenX(applet.width);
		y = getScreenY(applet.height);
		startTime = getStartTime();
		currentTime = getTuioTime();
		xSpeed = getXSpeed();
		ySpeed = getYSpeed();
		motionSpeed = getMotionSpeed();
		motionAcceleration = getMotionAccel();
		path = getPath();
		sessionID = getSessionID();
		state = getTuioState();
	}

	/**
	 * Get the screen-relative x coordinate
	 * 
	 * @param width
	 *            the screen width in pixels
	 * @return the coordinate of the x position of the touch from 0 to width
	 */
	@Override
	public int getScreenX(int width) {
		return super.getScreenX(width);
	}

	/**
	 * Get the screen-relative y coordinate
	 * 
	 * @param height
	 *            the screen height in pixels
	 * @return the coordinate of the y position of the touch from 0 to height
	 */
	@Override
	public int getScreenY(int height) {
		return super.getScreenY(height);
	}

	/**
	 * @return The TuioPoint containing the last point of the Touch
	 */
	public TuioPoint getLastPoint() {
		Vector<TuioPoint> path = getPath();
		if (path.size() <= 1) {
			return null;
		}

		return path.get(path.size() - 2);
	}

	/**
	 * @param t
	 *            TuioCursor to update the Touch with, since Touch extends
	 *            TuioCursor, it can also take a Touch
	 */
	public void updateTouch(TuioCursor t) {
		cursorId = t.getCursorID();
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
	 * @param index
	 *            The index of the point on the Touch's path (0 is first Point,
	 *            path.size()-1 is the last Point)
	 * @return A Point containing the x,y values of the Touch's path at the
	 *         specified index, returns null if invalid index
	 */
	public Point getPointOnPath(int index) {
		if (index < 0 || index >= path.size()) {
			return null;
		}
		return new Point(path.get(index).getScreenX(applet.width),path.get(index).getScreenY(applet.height));
	}
}
