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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;

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
public class Simulation {

	/** Default host. (Implements TUIO: host "127.0.0.1") */
	static String host = "127.0.0.1";

	/** Default port. (Implements TUIO: port "3333") */
	static int port = 3333;

	/** OSC Port */
	private OSCPortOut oscPort;

	/** Current fame */
	int currentFrame = 0;
	/** Last used session ID */
	int sessionID = -1;
	/** Width of the PApplet */
	int windowWidth;
	/** Height of the PApplet */
	int windowHeight;

	/** Hash table of touch cursors with their session IDs as the key */
	protected static Hashtable<Integer, Finger> cursorList = new Hashtable<Integer, Finger>();

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
	
	private OSCMessage aliveMessage(){
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		Enumeration<Finger> cursorList = Simulation.cursorList.elements();
		while (cursorList.hasMoreElements()) {
			aliveMessage.addArgument(cursorList.nextElement().sessionID);
		}
		return aliveMessage;
	}
	
	private OSCMessage setMessage(Finger cursor) {
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
		return setMessage;
	}
	
	private OSCMessage frameMessage(int currentFrame){
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(currentFrame);
		return frameMessage;
	}
	
	/**
	 * Deletes a cursor by sending a list of alive cursors without it
	 */
	private void cursorDelete() {
		OSCBundle cursorBundle = new OSCBundle();
		cursorBundle.addPacket(aliveMessage());
		cursorBundle.addPacket(frameMessage(++currentFrame));

		sendOSC(cursorBundle);
	}

	/**
	 * Sends a single cursor message
	 */
	protected void cursorMessage(Finger cursor) {
		if (cursor == null)
			return;
		
		OSCBundle cursorBundle = new OSCBundle();
		cursorBundle.addPacket(aliveMessage()); 
		cursorBundle.addPacket(setMessage(cursor));
		cursorBundle.addPacket(frameMessage(++currentFrame));

		sendOSC(cursorBundle);
	}

	

	/**
	 * Sends a complete cursor message
	 */
	protected void completeCursorMessage() {
		Enumeration<Finger> cursors = cursorList.elements();
		
		while(cursors.hasMoreElements()) {
			OSCBundle oscBundle = new OSCBundle();
			oscBundle.addPacket(aliveMessage());
			
			//send cursors info 10 at a time
			for (int j = 0; j < 10; j++){
				if(!cursors.hasMoreElements()){
					break;
				}
				oscBundle.addPacket(setMessage(cursors.nextElement()));
			}
			
			oscBundle.addPacket(frameMessage(-1));
			sendOSC(oscBundle);
		}
	}

	/**
	 * Resets the simulator
	 */
	protected void reset() {
		sessionID = -1;
		
		cursorList.clear();
		
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
		curBundle.addPacket(aliveMessage());
		curBundle.addPacket(frameMessage(-1));
		sendOSC(curBundle);
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
		cursorDelete();
	}

	public boolean contains(Point p) {
		if(p.x>=0&&p.x<=windowWidth&&p.y>=0&&p.y<=windowHeight){
			return true;
		}
		return false;
	}
}
