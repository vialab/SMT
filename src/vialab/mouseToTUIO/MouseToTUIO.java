/*
 * Modified version of TUIO Simulator - part of the reacTIVision project
 * http://reactivision.sourceforge.net/
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
package vialab.mouseToTUIO;

import java.awt.Point;
import java.util.Enumeration;

import javax.swing.SwingUtilities;

import processing.event.MouseEvent;

/**
 * This class responds to mouse events by sending a corresponding TUIO message.
 * <P>
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka
 * @author Zach Cook
 * @date September, 2012
 * @version 2.0
 */
public class MouseToTUIO extends Simulation {

	/** Default host. (Implements TUIO: host "127.0.0.1") */
	public static String host = "127.0.0.1";

	/** Default port. (Implements TUIO: port "3333") */
	public static int port = 3333;

	/**
	 * Sets up everything to get ready to begin converting mouse events to TUIO
	 * messages. Sets the screen to be full-screen if the boolean/flag is set to
	 * true.
	 * 
	 * @param applet
	 *            PApplet - Processing PApplet
	 */
	public MouseToTUIO(int width, int height) {
		super(host, port, width, height);
	}
	
	/**
	 * Updates the selected cursor
	 * 
	 * @param me
	 *            MouseEvent - The mouse dragged event
	 */
	public void mouseDragged(MouseEvent me) {
		long currentFrameTime = System.currentTimeMillis();
		long dt = currentFrameTime - lastFrameTime;
		if (dt < 16)
			return;

		Point pt = new Point(me.getX(), me.getY());
		int x = me.getX();
		int y = me.getX();

		if (selectedCursor != null) {
			if (table.contains(pt)) {
				if (selectedCursor != null) {
					if (jointCursors.contains(selectedCursor.sessionID)) {
						Point selPoint = selectedCursor.getPosition();
						int dx = pt.x - selPoint.x;
						int dy = pt.y - selPoint.y;

						Enumeration<Integer> joints = jointCursors.elements();
						while (joints.hasMoreElements()) {
							int jointId = joints.nextElement();
							if (jointId == selectedCursor.sessionID)
								continue;
							Finger joint_cursor = getCursor(jointId);
							Point joint_point = joint_cursor.getPosition();
							updateCursor(joint_cursor, joint_point.x + dx,
									joint_point.y + dy);
						}
						updateCursor(selectedCursor, pt.x, pt.y);
						completeCursorMessage();
					}
					else {
						updateCursor(selectedCursor, pt.x, pt.y);
						cursorMessage();
					}
				}
			}
			else {
				selectedCursor.stop();
				cursorMessage();
				if (stickyCursors.contains(selectedCursor.sessionID))
					stickyCursors.removeElement(selectedCursor.sessionID);
				if (jointCursors.contains(selectedCursor.sessionID))
					jointCursors.removeElement(selectedCursor.sessionID);
				removeCursor(selectedCursor);
				cursorDelete();
				selectedCursor = null;
			}
		}
		else {
			if (table.contains(pt)) {
				sessionID++;
				selectedCursor = addCursor(sessionID, x, y);
				cursorMessage();
				if (me.isShiftDown()
						|| SwingUtilities.isRightMouseButton((java.awt.event.MouseEvent) (me
								.getNative())))
					stickyCursors.addElement(selectedCursor.sessionID);
			}
		}

		lastFrameTime = currentFrameTime;
	}

	/**
	 * Adds a cursor to the table state
	 * 
	 * @param me
	 *            MouseEvent - The mouse pressed event
	 */
	public void mousePressed(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();

		Enumeration<Finger> cursorList = Simulation.cursorList.elements();
		while (cursorList.hasMoreElements()) {
			Finger cursor = cursorList.nextElement();
			Point point = cursor.getPosition();

			if (point.distance(x, y) < 7) {
				int selCur = -1;
				if (selectedCursor != null)
					selCur = selectedCursor.sessionID;

				if ((me.isShiftDown() || SwingUtilities
						.isRightMouseButton((java.awt.event.MouseEvent) (me.getNative())))
						&& selCur != cursor.sessionID) {
					stickyCursors.removeElement(cursor.sessionID);
					if (jointCursors.contains(cursor.sessionID))
						jointCursors.removeElement(cursor.sessionID);
					removeCursor(cursor);
					cursorDelete();
					selectedCursor = null;
					return;
				}
				else if (me.isControlDown()
						|| SwingUtilities.isMiddleMouseButton((java.awt.event.MouseEvent) (me
								.getNative()))) {
					if (jointCursors.contains(cursor.sessionID))
						jointCursors.removeElement(cursor.sessionID);
					else
						jointCursors.addElement(cursor.sessionID);
					return;
				}
				else {
					selectedCursor = cursor;
					return;
				}
			}
		}

		if (me.isControlDown()
				|| SwingUtilities.isMiddleMouseButton((java.awt.event.MouseEvent) (me.getNative())))
			return;

		if (table.contains(new Point(x, y))) {
			sessionID++;
			selectedCursor = addCursor(sessionID, x, y);
			cursorMessage();
			if (me.isShiftDown()
					|| SwingUtilities.isRightMouseButton((java.awt.event.MouseEvent) (me
							.getNative())))
				stickyCursors.addElement(selectedCursor.sessionID);
			return;
		}

		selectedCursor = null;
	}

	/**
	 * Removes the cursor or makes it stationary if it is a sticky cursor
	 * 
	 * @param me
	 *            MouseEvent - The mouse released event
	 */
	public void mouseReleased(MouseEvent me) {
		if ((selectedCursor != null)) {
			if (!stickyCursors.contains(selectedCursor.sessionID)) {
				selectedCursor.stop();
				cursorMessage();
				if (jointCursors.contains(selectedCursor.sessionID))
					jointCursors.removeElement(selectedCursor.sessionID);
				removeCursor(selectedCursor);
				cursorDelete();
			}
			else {
				selectedCursor.stop();
				cursorMessage();
			}

			selectedCursor = null;
		}
	}

	/**
	 * Currently not used
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseMoved(MouseEvent me) {
	}

	/**
	 * Currently not used
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseClicked(MouseEvent me) {
	}

	/**
	 * Currently not used
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseEntered(MouseEvent me) {
	}

	/**
	 * Currently not used
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseExited(MouseEvent me) {
	}

	public void mouseEvent(MouseEvent event) {
		switch (event.getAction()) {
		case MouseEvent.PRESSED:
			mousePressed(event);
			break;
		case MouseEvent.RELEASED:
			mouseReleased(event);
			break;
		case MouseEvent.CLICKED:
			mouseClicked(event);
			break;
		case MouseEvent.DRAGGED:
			mouseDragged(event);
			break;
		case MouseEvent.MOVED:
			mouseMoved(event);
			break;
		case MouseEvent.ENTERED:
			mouseEntered(event);
			break;
		case MouseEvent.EXITED:
			mouseExited(event);
			break;
		}
	}
}
