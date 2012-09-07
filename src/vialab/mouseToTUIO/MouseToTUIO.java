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

import java.util.Hashtable;

import processing.event.MouseEvent;

/**
 * This class creates a transparent Container that overlays a PApplet and
 * respond to mouse and key events with corresponding TUIO message. TUIO objects
 * functionality has been removed. It will go into full-screen mode depending on
 * the boolean passed in the constructor. Full screen mode does not work with
 * OpenGL at the moment.
 * <P>
 * 
 * University of Ontario Institute of Technology. Summer Research Assistant with
 * Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.
 * <P>
 * 
 * @author Erik Paluka
 * @date July, 2011
 * @version 1.0
 */
public class MouseToTUIO {
		/** The simulator */
	private static Simulation sim;

	/** Width of the PApplet, overlay and simulator table/rectangle/window */
	protected static int width;

	/** Height of the PApplet, overlay and simulator table/rectangle/window */
	protected static int height;

	/** Hash table of touch cursors with their session IDs as the key */
	protected static Hashtable<Integer, Finger> cursorList = new Hashtable<Integer, Finger>();

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
		super();
		MouseToTUIO.width = width;
		MouseToTUIO.height = height;

		sim = new Simulation(host, port);
	}

	/**
	 * Adds a touch cursor to the hash table
	 * 
	 * @param sID
	 *            int - Session ID
	 * @param x
	 *            int - The x-coordinate of the touch cursor
	 * @param y
	 *            int - The y-coordinate of the touch cursor
	 * @return cursor Touch - The created touch cursor
	 */
	public final static Finger addCursor(int sID, int x, int y) {
		Finger cursor = new Finger(sID, x, y);
		cursorList.put(sID, cursor);
		return cursor;
	}

	/**
	 * Updates a touch cursor
	 * 
	 * @param cursor
	 *            Touch - The touch cursor to update
	 * @param x
	 *            int - The x-coordinate of the touch cursor
	 * @param y
	 *            int - The y-coordinate of the touch cursor
	 */
	public final static void updateCursor(Finger cursor, int x, int y) {
		cursor.update(x, y);
	}

	/**
	 * Returns the touch cursor with the given Session ID
	 * 
	 * @param sID
	 *            int - Session ID
	 * @return cursorList.get(sID) Touch
	 */
	public final static Finger getCursor(int sID) {
		return cursorList.get(sID);
	}

	/**
	 * Removes the touch cursor
	 * 
	 * @param cursor
	 *            Touch - The touch cursor
	 */
	public final static void removeCursor(Finger cursor) {
		cursorList.remove(cursor.sessionID);
	}

	/**
	 * Triggers the simulator's mouse_pressed function
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mousePressed(MouseEvent me) {
		sim.mouse_pressed(me);
	}

	/**
	 * Triggers the simulator's mouse_released function
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseReleased(MouseEvent me) {
		sim.mouse_released(me);
	}

	/**
	 * Triggers the simulator's mouse_dragged function
	 * 
	 * @param me
	 *            MouseEvent
	 */
	public void mouseDragged(MouseEvent me) {
		sim.mouse_dragged(me);
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
