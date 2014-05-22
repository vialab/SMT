/*
	Modified version of TUIO Simulator - part of the reacTIVision project
	http://reactivision.sourceforge.net/

	Copyright (c) 2005-2009 Martin Kaltenbrunner <mkalten@iua.upf.edu>
 
	This version Copyright (c) 2011 
	Erik Paluka, Christopher Collins - University of Ontario Institute of Technology
	Mark Hancock - University of Waterloo
	contact: christopher.collins@uoit.ca

	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public
	License Version 3 as published by the Free Software Foundation.

	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	General Public License for more details.

	You should have received a copy of the GNU General
	Public License along with this library; if not, write to the
	Free Software Foundation, Inc., 59 Temple Place, Suite 330,
	Boston, MA  02111-1307  USA
 */
package vialab.SMT;

import java.awt.Point;
import java.util.Vector;

/**
 * This class holds touch event information associated with the same session ID
 * (e.g.all the information associated with someone placing their finger on the
 * screen, dragging it, then removing it.)
 * 
 * @author Erik Paluka
 * @version 1.0
 */
class Finger {
	/** Touch Cursor's Session ID */
	protected int sessionID;
	/** Touch Cursor's speed in the x-direction */
	protected float xSpeed;
	/** Touch Cursor's speed in the y-direction */
	protected float ySpeed;
	/** Touch Cursor's speed */
	protected float mSpeed;
	/** Touch Cursor's acceleration */
	protected float mAccel;
	/** Last update time */
	protected long lastTime;
	/** Path of the touch cursor */
	private Vector<Point> path;
	/** ID used by a touch for track keeping in Android */
	protected int touchID = -1;

	/**
	 * Touch Constructor, creates a touch cursor with the given session ID, and
	 * coordinates.
	 * 
	 * @param sID Session ID
	 * @param xPos x-coordinate
	 * @param yPos y-coordinate
	 */
	public Finger(int sID, int xPos, int yPos) {
		this.sessionID = sID;
		path = new Vector<Point>();
		path.addElement( new Point( xPos, yPos));
		this.xSpeed = 0.0f;
		this.ySpeed = 0.0f;
		this.mAccel = 0.0f;
		lastTime = System.currentTimeMillis();
	}

	/**
	 * Updates the touch cursor with the new coordinates
	 * 
	 * @param xPos New x-coordinate
	 * @param yPos New y-coordinate
	 * @param windowWidth Window width
	 * @param windowHeight Window height
	 */
	public final void update( int xpos, int ypos, int windowWidth, int windowHeight) {
		Point lastPoint = getPosition();
		path.addElement( new Point( xpos, ypos));

		// time difference in seconds
		long currentTime = System.currentTimeMillis();
		float dt = ( currentTime - lastTime) / 1000.0f;

		if( dt > 0) {
			float dx = ( xpos - lastPoint.x) / (float) windowWidth;
			float dy = ( ypos - lastPoint.y) / (float) windowHeight;
			float dist = (float) Math.sqrt( dx * dx + dy * dy);
			float newSpeed = dist / dt;
			this.xSpeed = dx / dt;
			this.ySpeed = dy / dt;
			this.mAccel = (newSpeed - mSpeed) / dt;
			this.mSpeed = newSpeed;
		}
		lastTime = currentTime;
	}

	/**
	 * Resets the touch cursors speed, acceleration and time of last update
	 */
	public final void stop() {
		lastTime = System.currentTimeMillis();
		this.xSpeed = 0.0f;
		this.ySpeed = 0.0f;
		this.mAccel = 0.0f;
		this.mSpeed = 0.0f;
	}

	/**
	 * Returns the current position of the touch cursor
	 * 
	 * @return path.lastElement() Point
	 */
	public final Point getPosition() {
		return path.lastElement();
	}

	/**
	 * Returns the path of the touch cursor
	 * 
	 * @return path Vector<Point>
	 */
	public final Vector<Point> getPath() {
		return path;
	}

	/**
	 * Sets the touch ID for Android touches
	 * 
	 * @param tID ID of the Android touch
	 */
	public final void setTouchId(int tID) {
		touchID = tID;
	}

	/**
	 * Returns the touch ID of the associated Android touch
	 * 
	 * @return touchID
	 */
	public final int getTouchId() {
		return touchID;
	}
}
