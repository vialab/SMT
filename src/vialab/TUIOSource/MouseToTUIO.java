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
package vialab.TUIOSource;

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
public class MouseToTUIO {
	
	private static Simulation sim;

	/**
	 * Sets up everything to get ready to begin converting mouse events to TUIO
	 * messages. Sets the screen to be full-screen if the boolean/flag is set to
	 * true.
	 * 
	 * @param applet
	 *            PApplet - Processing PApplet
	 */
	public MouseToTUIO(int width, int height) {
		super();
		sim= new Simulation(width, height);
	}
	
	/**
	 * Updates the selected cursor
	 * 
	 * @param me
	 *            MouseEvent - The mouse dragged event
	 */
	public void mouseDragged(MouseEvent me) {
		long currentFrameTime = System.currentTimeMillis();
		long dt = currentFrameTime - sim.lastFrameTime;
		if (dt < 16)
			return;

		Point pt = new Point(me.getX(), me.getY());
		int x = me.getX();
		int y = me.getX();

		if (sim.selectedCursor != null) {
			if (sim.table.contains(pt)) {
				if (sim.selectedCursor != null) {
					if (sim.jointCursors.contains(sim.selectedCursor.sessionID)) {
						Point selPoint = sim.selectedCursor.getPosition();
						int dx = pt.x - selPoint.x;
						int dy = pt.y - selPoint.y;

						Enumeration<Integer> joints = sim.jointCursors.elements();
						while (joints.hasMoreElements()) {
							int jointId = joints.nextElement();
							if (jointId == sim.selectedCursor.sessionID)
								continue;
							Finger joint_cursor = sim.getCursor(jointId);
							Point joint_point = joint_cursor.getPosition();
							sim.updateCursor(joint_cursor, joint_point.x + dx,
									joint_point.y + dy);
						}
						sim.updateCursor(sim.selectedCursor, pt.x, pt.y);
						sim.completeCursorMessage();
					}
					else {
						sim.updateCursor(sim.selectedCursor, pt.x, pt.y);
						sim.cursorMessage();
					}
				}
			}
			else {
				sim.selectedCursor.stop();
				sim.cursorMessage();
				if (sim.stickyCursors.contains(sim.selectedCursor.sessionID))
					sim.stickyCursors.removeElement(sim.selectedCursor.sessionID);
				if (sim.jointCursors.contains(sim.selectedCursor.sessionID))
					sim.jointCursors.removeElement(sim.selectedCursor.sessionID);
				sim.removeCursor(sim.selectedCursor);
				sim.cursorDelete();
				sim.selectedCursor = null;
			}
		}
		else {
			if (sim.table.contains(pt)) {
				sim.sessionID++;
				sim.selectedCursor = sim.addCursor(sim.sessionID, x, y);
				sim.cursorMessage();
				if (me.isShiftDown()
						|| SwingUtilities.isRightMouseButton((java.awt.event.MouseEvent) (me
								.getNative())))
					sim.stickyCursors.addElement(sim.selectedCursor.sessionID);
			}
		}

		sim.lastFrameTime = currentFrameTime;
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
				if (sim.selectedCursor != null)
					selCur = sim.selectedCursor.sessionID;

				if ((me.isShiftDown() || SwingUtilities
						.isRightMouseButton((java.awt.event.MouseEvent) (me.getNative())))
						&& selCur != cursor.sessionID) {
					sim.stickyCursors.removeElement(cursor.sessionID);
					if (sim.jointCursors.contains(cursor.sessionID))
						sim.jointCursors.removeElement(cursor.sessionID);
					sim.removeCursor(cursor);
					sim.cursorDelete();
					sim.selectedCursor = null;
					return;
				}
				else if (me.isControlDown()
						|| SwingUtilities.isMiddleMouseButton((java.awt.event.MouseEvent) (me
								.getNative()))) {
					if (sim.jointCursors.contains(cursor.sessionID))
						sim.jointCursors.removeElement(cursor.sessionID);
					else
						sim.jointCursors.addElement(cursor.sessionID);
					return;
				}
				else {
					sim.selectedCursor = cursor;
					return;
				}
			}
		}

		if (me.isControlDown()
				|| SwingUtilities.isMiddleMouseButton((java.awt.event.MouseEvent) (me.getNative())))
			return;

		if (sim.table.contains(new Point(x, y))) {
			sim.sessionID++;
			sim.selectedCursor = sim.addCursor(sim.sessionID, x, y);
			sim.cursorMessage();
			if (me.isShiftDown()
					|| SwingUtilities.isRightMouseButton((java.awt.event.MouseEvent) (me
							.getNative())))
				sim.stickyCursors.addElement(sim.selectedCursor.sessionID);
			return;
		}

		sim.selectedCursor = null;
	}

	/**
	 * Removes the cursor or makes it stationary if it is a sticky cursor
	 * 
	 * @param me
	 *            MouseEvent - The mouse released event
	 */
	public void mouseReleased(MouseEvent me) {
		if ((sim.selectedCursor != null)) {
			if (!sim.stickyCursors.contains(sim.selectedCursor.sessionID)) {
				sim.selectedCursor.stop();
				sim.cursorMessage();
				if (sim.jointCursors.contains(sim.selectedCursor.sessionID))
					sim.jointCursors.removeElement(sim.selectedCursor.sessionID);
				sim.removeCursor(sim.selectedCursor);
				sim.cursorDelete();
			}
			else {
				sim.selectedCursor.stop();
				sim.cursorMessage();
			}

			sim.selectedCursor = null;
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
