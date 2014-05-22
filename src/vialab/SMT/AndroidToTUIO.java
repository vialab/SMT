/*
 * Modified version of TUIO Simulator - part of the reacTIVision project
 * http://reactivision.sourceforge.net/
 * 
 * Copyright (c) 2005-2009 Martin Kaltenbrunner <mkalten@iua.upf.edu>
 * 
 * This version Copyright (c) 2011 Erik Paluka, Christopher Collins - University
 * of Ontario Institute of Technology Victor Cheung, Mark Hancock - University of Waterloo
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

import java.util.Vector;

import android.view.*;
import android.view.View.OnTouchListener;

/**
 * This class responds to Android touch events by sending a corresponding TUIO
 * message. Currently Processing (2.x) does not support registering "touchEvent"
 * to the parenting PApplet, so a walk-around is used: make this class listen to
 * and handle touchEvents explicitly.
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * 
 * @author Victor Cheung
 * @author Zach Cook
 * @version 1.0
 */
class AndroidToTUIO implements OnTouchListener {

	private static final int MAX_TOUCHPOINTS = 10;
	private static final int FRAME_RATE = 40;

	private static Simulation sim;

	private long startTime;
	private long lastTime = 0;

	/** Vector of active touch points */
	Vector<Finger> touchPoints;

	/** Currently selected cursor */
	Finger selectedCursor = null;

	/**
	 * Sets up everything to get ready to begin converting Android touch events
	 * to TUIO messages. Sets the screen to be full-screen if the boolean/flag
	 * is set to true.
	 * 
	 * @param width width of the screen
	 * @param height height of the screen
	 * @param port port to connect to
	 */
	public AndroidToTUIO( int width, int height, int port) {
		super();
		sim = new Simulation( width, height, port);
	}

	/**
	 * Updates the selected cursor, used when Processing supports
	 * registerMethod("touchEvent", this)
	 * 
	 * @param v The view receiving the touch
	 * @param me The Android touch event
	 */
	public boolean onTouch( View v, MotionEvent me) {

		// set up the last update time for the touch cursor
		long timeStamp = System.currentTimeMillis() - startTime;
		long dt = timeStamp - lastTime;
		lastTime = timeStamp;

		// always send on ACTION_DOWN & ACTION_UP -- these are the events when a
		// gesture begins or ends,
		// additional touches or removal of touches during these events are
		// different events
		if ((me.getAction() == MotionEvent.ACTION_DOWN)
				|| (me.getAction() == MotionEvent.ACTION_UP))
			dt = 1000;

		int pointerCount = me.getPointerCount();

		// limit pointerCount, but it usually would not go over that for current
		// Android devices
		if (pointerCount > MAX_TOUCHPOINTS) {
			pointerCount = MAX_TOUCHPOINTS;
		}

		if (me.getAction() == MotionEvent.ACTION_UP) {
			// this is tricky because even when there is no touch left (a total
			// lift),
			// this event is reported with the last touch, so pointerCount is
			// never 0
			if (me.getPointerCount() == 1) {
				// means there is no touch left
				sim.removeCursor(touchPoints.firstElement()); // touchPoints
																// should only
																// have 1 touch
																// item
				touchPoints.clear();
			}
		}
		else {
			// any action other than all touches are gone. e.g., more or less
			// touches, moved touches
			// procedure is all the same: refresh the active touch points
			// remove touches that are gone
			for (int i = 0; i < touchPoints.size(); i++) {

				boolean pointStillAlive = false;
				for (int j = 0; j < me.getPointerCount(); j++) {
					if (me.getPointerId(j) == touchPoints.get(i).getTouchId()) {
						pointStillAlive = true;
						break;
					}
				}

				if (pointStillAlive == false) {
					sim.removeCursor(touchPoints.get(i));
					touchPoints.remove(i);
					i = 0;
				}
			}

			// update existing touches or add new touches
			for (int i = 0; i < pointerCount; i++) {
				int id = me.getPointerId(i);

				// update if this touch already exists
				boolean pointExists = false;
				for (int j = 0; j < touchPoints.size(); j++) {
					if (touchPoints.get(j).getTouchId() == id) {
						sim.updateCursor(touchPoints.get(j), (int) me.getX(i), (int) me.getY(i));
						sim.cursorMessage(touchPoints.get(j));
						pointExists = true;
						break;
					}
				}

				// add if this touch is new
				if (pointExists == false) {
					selectedCursor = sim.addCursor((int) me.getX(i), (int) me.getY(i));
					selectedCursor.setTouchId(id);
					touchPoints.add(selectedCursor);
					sim.cursorMessage(selectedCursor);
					selectedCursor = null;
				}

			}
		}

		// if(dt>(1000/FRAME_RATE)) sim.allCursorMessage();

		return true;
	}

	/**
	 * Updates the selected cursor, used when Processing supports
	 * registerMethod("touchEvent", this)
	 * 
	 * @param me The Android touch event
	 */
	public boolean onTouchEvent(Object me) {
		MotionEvent ame = (MotionEvent) me;

		// set up the last update time for the touch cursor
		long timeStamp = System.currentTimeMillis() - startTime;
		long dt = timeStamp - lastTime;
		lastTime = timeStamp;

		// always send on ACTION_DOWN & ACTION_UP -- these are the events when a
		// gesture begins or ends,
		// additional touches or removal of touches during these events are
		// different events
		if ((ame.getAction() == MotionEvent.ACTION_DOWN)
				|| (ame.getAction() == MotionEvent.ACTION_UP))
			dt = 1000;

		int pointerCount = ame.getPointerCount();

		// limit pointerCount, but it usually would not go over that for current
		// Android devices
		if (pointerCount > MAX_TOUCHPOINTS) {
			pointerCount = MAX_TOUCHPOINTS;
		}

		if (ame.getAction() == MotionEvent.ACTION_UP) {
			// this is tricky because even when there is no touch left (a total
			// lift),
			// this event is reported with the last touch, so pointerCount is
			// never 0
			if (ame.getPointerCount() == 1) {
				// means there is no touch left
				sim.removeCursor(touchPoints.firstElement()); // touchPoints
																// should only
																// have 1 touch
																// item
				touchPoints.clear();
			}
		}
		else {
			// any action other than all touches are gone. e.g., more or less
			// touches, moved touches
			// procedure is all the same: refresh the active touch points
			// remove touches that are gone
			for (int i = 0; i < touchPoints.size(); i++) {

				boolean pointStillAlive = false;
				for (int j = 0; j < ame.getPointerCount(); j++) {
					if (ame.getPointerId(j) == touchPoints.get(i).getTouchId()) {
						pointStillAlive = true;
						break;
					}
				}

				if (pointStillAlive == false) {
					sim.removeCursor(touchPoints.get(i));
					touchPoints.remove(i);
					i = 0;
				}
			}

			// update existing touches or add new touches
			for (int i = 0; i < pointerCount; i++) {
				int id = ame.getPointerId(i);

				// update if this touch already exists
				boolean pointExists = false;
				for (int j = 0; j < touchPoints.size(); j++) {
					if (touchPoints.get(j).getTouchId() == id) {
						sim.updateCursor(touchPoints.get(j), (int) ame.getX(i), (int) ame.getY(i));
						sim.cursorMessage(touchPoints.get(j));
						pointExists = true;
						break;
					}
				}

				// add if this touch is new
				if (pointExists == false) {
					selectedCursor = sim.addCursor((int) ame.getX(i), (int) ame.getY(i));
					selectedCursor.setTouchId(id);
					touchPoints.add(selectedCursor);
					sim.cursorMessage(selectedCursor);
					selectedCursor = null;
				}

			}
		}

		// if(dt>(1000/FRAME_RATE)) sim.allCursorMessage();

		return true;
	}

	/**
	 * Resets AndroidToTUIO state, just making the already available
	 * functionality visible
	 */
	public void reset() {
		sim.reset();
		touchPoints.clear();
	}
}
