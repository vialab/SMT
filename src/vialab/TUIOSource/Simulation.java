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
package vialab.TUIOSource;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;

/**
 * This class is the parent class of MouseToTUIO etc to allow easy formation of
 * TUIO messages in response to events.
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
public class Simulation implements Runnable {

	/** Default host. (Implements TUIO: host "127.0.0.1") */
	static String host = "127.0.0.1";

	/** Default port. (Implements TUIO: port "3333") */
	static int port = 3333;

	/** OSC Port */
	private OSCPortOut oscPort;

	/** Last frame time */
	long lastFrameTime = -1;

	/** Current fame */
	int currentFrame = 0;
	/** Last used session ID */
	int sessionID = -1;
	/** Width of the PApplet */
	int windowWidth;
	/** Height of the PApplet */
	int windowHeight;

	/** Shape of a rectangle (PApplet screen size) */
	Shape table;

	/** Currently selected cursor */
	Finger selectedCursor = null;

	/** Vector of the sticky cursors */
	Vector<Integer> stickyCursors = new Vector<Integer>();
	/** Vector of the joint cursors */
	Vector<Integer> jointCursors = new Vector<Integer>();

	/** Hash table of touch cursors with their session IDs as the key */
	protected static Hashtable<Integer, Finger> cursorList = new Hashtable<Integer, Finger>();

	/** Flag set true if the simulator's thread is running */
	private boolean running = false;

	/**
	 * Constructor, resets the simulator and create a table the size of the
	 * PApplet
	 * 
	 * @param host
	 *            String Host
	 * @param port
	 *            int Port
	 */
	public Simulation(int width, int height) {
		super();

		this.windowWidth = width;
		this.windowHeight = height;

		try {
			oscPort = new OSCPortOut(java.net.InetAddress.getByName(host), port);
		}
		catch (SocketException e) {
			oscPort = null;
			e.printStackTrace();
		}
		catch (UnknownHostException e) {
			oscPort = null;
			e.printStackTrace();
		}

		reset();
		table = new Rectangle2D.Float(0, 0, windowWidth, windowHeight);
	}

	/**
	 * Sends the given OSCPacket
	 * 
	 * @param packet
	 *            OSCPacket
	 */
	private void sendOSC(OSCPacket packet) {
		try {
			oscPort.send(packet);
		}
		catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a cursor
	 */
	protected void cursorDelete() {

		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		Enumeration<Integer> cursorList = Simulation.cursorList.keys();
		while (cursorList.hasMoreElements()) {
			Integer s_id = cursorList.nextElement();
			aliveMessage.addArgument(s_id);
		}

		currentFrame++;
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);

		cursorBundle.addPacket(aliveMessage);
		cursorBundle.addPacket(frameMessage);

		sendOSC(cursorBundle);
	}

	/**
	 * Sends a single cursor message
	 */
	protected void cursorMessage() {

		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		Enumeration<Integer> cursorList = Simulation.cursorList.keys();
		while (cursorList.hasMoreElements()) {
			Integer s_id = cursorList.nextElement();
			aliveMessage.addArgument(s_id);

		}

		Finger cursor = selectedCursor;
		if (cursor == null)
			return;

		Point point = cursor.getPosition();
		float xpos = (point.x) / (float) windowWidth;
		float ypos = (point.y) / (float) windowHeight;
		OSCMessage setMessage = new OSCMessage("/tuio/2Dcur");
		setMessage.addArgument("set");
		setMessage.addArgument(cursor.sessionID);
		setMessage.addArgument(xpos);
		setMessage.addArgument(ypos);
		setMessage.addArgument(cursor.xSpeed);
		setMessage.addArgument(cursor.ySpeed);
		setMessage.addArgument(cursor.mAccel);

		currentFrame++;
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);

		cursorBundle.addPacket(aliveMessage);
		cursorBundle.addPacket(setMessage);
		cursorBundle.addPacket(frameMessage);

		sendOSC(cursorBundle);
	}

	/**
	 * Sends a complete cursor message
	 */
	protected void completeCursorMessage() {
		Vector<OSCMessage> messageList = new Vector<OSCMessage>();

		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(-1);

		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");

		Enumeration<Integer> cursorList = Simulation.cursorList.keys();

		while (cursorList.hasMoreElements()) {
			Integer s_id = cursorList.nextElement();
			aliveMessage.addArgument(s_id);

			Finger cursor = Simulation.cursorList.get(s_id);
			Point point = cursor.getPosition();

			float xpos = (point.x) / (float) windowWidth;
			float ypos = (point.y) / (float) windowHeight;

			OSCMessage setMessage = new OSCMessage("/tuio/2Dcur");
			setMessage.addArgument("set");
			setMessage.addArgument(s_id);
			setMessage.addArgument(xpos);
			setMessage.addArgument(ypos);
			setMessage.addArgument(cursor.xSpeed);
			setMessage.addArgument(cursor.ySpeed);
			setMessage.addArgument(cursor.mAccel);
			messageList.addElement(setMessage);
		}

		int i;
		for (i = 0; i < (messageList.size() / 10); i++) {
			OSCBundle oscBundle = new OSCBundle();
			oscBundle.addPacket(aliveMessage);

			for (int j = 0; j < 10; j++)
				oscBundle.addPacket((OSCPacket) messageList.elementAt(i * 10 + j));

			oscBundle.addPacket(frameMessage);
			sendOSC(oscBundle);
		}

		if ((messageList.size() % 10 != 0) || (messageList.size() == 0)) {
			OSCBundle oscBundle = new OSCBundle();
			oscBundle.addPacket(aliveMessage);

			for (int j = 0; j < messageList.size() % 10; j++)
				oscBundle.addPacket((OSCPacket) messageList.elementAt(i * 10 + j));

			oscBundle.addPacket(frameMessage);
			sendOSC(oscBundle);
		}
	}

	/**
	 * Resets the simulator
	 */
	protected void reset() {
		sessionID = -1;
		stickyCursors.clear();
		jointCursors.clear();

		lastFrameTime = -1;

		OSCBundle objBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dobj");
		aliveMessage.addArgument("alive");

		OSCMessage frameMessage = new OSCMessage("/tuio/2Dobj");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(-1);

		objBundle.addPacket(aliveMessage);
		objBundle.addPacket(frameMessage);
		sendOSC(objBundle);

		OSCBundle curBundle = new OSCBundle();
		aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");

		frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(-1);

		curBundle.addPacket(aliveMessage);
		curBundle.addPacket(frameMessage);
		sendOSC(curBundle);
	}

	/**
	 * Sends the table state every second I dont think there is any reason to
	 * use this
	 */
	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {}

			long currentFrameTime = System.currentTimeMillis();
			long dt = currentFrameTime - lastFrameTime;
			if (dt > 1000) {
				completeCursorMessage();
			}
		}
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
	protected Finger addCursor(int sID, int x, int y) {
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
	protected void updateCursor(Finger cursor, int x, int y) {
		cursor.update(x, y, windowWidth, windowHeight);
	}

	/**
	 * Returns the touch cursor with the given Session ID
	 * 
	 * @param sID
	 *            int - Session ID
	 * @return cursorList.get(sID) Touch
	 */
	protected Finger getCursor(int sID) {
		return cursorList.get(sID);
	}

	/**
	 * Removes the touch cursor
	 * 
	 * @param cursor
	 *            Touch - The touch cursor
	 */
	protected final void removeCursor(Finger cursor) {
		cursorList.remove(cursor.sessionID);
	}
}
