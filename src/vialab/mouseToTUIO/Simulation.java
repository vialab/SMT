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
package vialab.mouseToTUIO;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;

import processing.event.MouseEvent;

import com.illposed.osc.OSCBundle;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPacket;
import com.illposed.osc.OSCPortOut;
/** 
 * This class creates TUIO messages depending on mouse events and then sends them out to port 3333.<P>
 *
 * University of Ontario Institute of Technology.
 * Summer Research Assistant with Dr. Christopher Collins (Summer 2011) collaborating with Dr. Mark Hancock.<P>
 * 
 * @author  Erik Paluka 
 * @date  July, 2011
 * @version 1.0
 */
public class Simulation implements Runnable {
	/**OSC Port*/
	private OSCPortOut oscPort;
	
	/**Last frame time*/
	long lastFrameTime = -1;
	
	/**Current fame*/
	int currentFrame = 0;
	/**Last used session ID*/
	int sessionID = -1;
	/**Width of the PApplet*/
	int windowWidth = MouseToTUIO.width;
	/**Height of the PApplet*/
	int windowHeight = MouseToTUIO.height;
	
	/**Shape of a rectangle (PApplet screen size)*/
	Shape table;
	
	/**Currently selected cursor*/
	Finger selectedCursor = null;
	
	/**Vector of the sticky cursors*/
	Vector<Integer> stickyCursors = new Vector<Integer>();
	/**Vector of the joint cursors*/
	Vector<Integer> jointCursors = new Vector<Integer>();
	
	/**Flag set true if the simulator's thread is running*/
	private boolean running = false;
	
	

	
	/**
	 * Constructor, resets the simulator and create a table the size of the PApplet
	 * 
	 * @param host String Host
	 * @param port int Port
	 */
	public Simulation(String host, int port) {
		super();

		try {
			oscPort = new OSCPortOut(java.net.InetAddress.getByName(host), port);
		} catch (SocketException e) {
			oscPort = null;
			e.printStackTrace();
		} catch (UnknownHostException e) {
			oscPort = null;
			e.printStackTrace();
		} 
		 
		reset();
		table = new Rectangle2D.Float(0,0,windowWidth,windowHeight);
	}

	/**
	 * Sends the given OSCPacket
	 * 
	 * @param packet OSCPacket
	 */
	private void sendOSC(OSCPacket packet) {
		try { 
			oscPort.send(packet); 
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Deletes a cursor
	 */
	private void cursorDelete() {
		
		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		Enumeration<Integer> cursorList = MouseToTUIO.cursorList.keys();
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
	void cursorMessage() {

		OSCBundle cursorBundle = new OSCBundle();
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		Enumeration<Integer> cursorList = MouseToTUIO.cursorList.keys();
		while (cursorList.hasMoreElements()) {
			Integer s_id = cursorList.nextElement();
			aliveMessage.addArgument(s_id);

		}

		Finger cursor = selectedCursor;
		if (cursor == null) return;

		Point point = cursor.getPosition();
		float xpos = (point.x)/(float)windowWidth;
		float ypos = (point.y)/(float)windowHeight;
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
	void completeCursorMessage() {
		Vector<OSCMessage> messageList = new Vector<OSCMessage>();
		
		OSCMessage frameMessage = new OSCMessage("/tuio/2Dcur");
		frameMessage.addArgument("fseq");
		frameMessage.addArgument(-1);
		
		OSCMessage aliveMessage = new OSCMessage("/tuio/2Dcur");
		aliveMessage.addArgument("alive");
		
		Enumeration<Integer> cursorList = MouseToTUIO.cursorList.keys();
		
		while (cursorList.hasMoreElements()) {
			Integer s_id = cursorList.nextElement();
			aliveMessage.addArgument(s_id);

			Finger cursor = MouseToTUIO.cursorList.get(s_id);
			Point point = cursor.getPosition();
					
			float xpos = (point.x)/(float)windowWidth;
			float ypos = (point.y)/(float)windowHeight;
			
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
		for (i=0;i<(messageList.size()/10);i++) {
			OSCBundle oscBundle = new OSCBundle();			
			oscBundle.addPacket(aliveMessage);
			
			for (int j=0;j<10;j++)
				oscBundle.addPacket((OSCPacket)messageList.elementAt(i*10+j));
			
			oscBundle.addPacket(frameMessage);
			sendOSC(oscBundle);
		} 
		
		if ((messageList.size()%10!=0) || (messageList.size()==0)) {
			OSCBundle oscBundle = new OSCBundle();			
			oscBundle.addPacket(aliveMessage);
			
			for (int j=0;j<messageList.size()%10;j++)
				oscBundle.addPacket((OSCPacket)messageList.elementAt(i*10+j));	
			
			oscBundle.addPacket(frameMessage);
			sendOSC(oscBundle);
		}
	}

	/**
	 * Resets the simulator
	 */
	public void reset() {
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
	 * Updates the selected cursor
	 * 
	 * @param me MouseEvent - The mouse dragged event
	 */
	public void mouse_dragged (MouseEvent me) {
		long currentFrameTime = System.currentTimeMillis();
		long dt = currentFrameTime - lastFrameTime;
		if (dt<16) return;

		Point pt = new Point(me.getX(),me.getY());
		int x = me.getX();
		int y = me.getX();
		
		if (selectedCursor!=null){
			if (table.contains(pt)) {
				if(selectedCursor!=null) {
					if (jointCursors.contains(selectedCursor.sessionID)) {
						Point selPoint = selectedCursor.getPosition();
						int dx = pt.x - selPoint.x;
						int dy = pt.y - selPoint.y;
					
						Enumeration<Integer> joints = jointCursors.elements();
						while (joints.hasMoreElements()) {
							int jointId = joints.nextElement();
							if (jointId == selectedCursor.sessionID) continue;
							Finger joint_cursor = MouseToTUIO.getCursor(jointId);
							Point joint_point = joint_cursor.getPosition();
							MouseToTUIO.updateCursor(joint_cursor, joint_point.x+dx,joint_point.y+dy);
						}
						MouseToTUIO.updateCursor(selectedCursor,pt.x,pt.y);
						completeCursorMessage();
					} else {
						MouseToTUIO.updateCursor(selectedCursor,pt.x,pt.y);
						cursorMessage();
					} }
				} else {
					selectedCursor.stop();
					cursorMessage();
					if (stickyCursors.contains(selectedCursor.sessionID)) stickyCursors.removeElement(selectedCursor.sessionID);
					if (jointCursors.contains(selectedCursor.sessionID)) jointCursors.removeElement(selectedCursor.sessionID);
					MouseToTUIO.removeCursor(selectedCursor);
					cursorDelete();
					selectedCursor = null;
				}
			} else {
				if (table.contains(pt)) {
					sessionID++;
					selectedCursor = MouseToTUIO.addCursor(sessionID,x,y);
					cursorMessage();
					if (me.isShiftDown() || me.getButton()==java.awt.event.MouseEvent.BUTTON2) stickyCursors.addElement(selectedCursor.sessionID);
				}
			}
			
			lastFrameTime = currentFrameTime;
	}

	/**
	 * Adds a cursor to the table state
	 * 
	 * @param me MouseEvent - The mouse pressed event
	 */
	public void mouse_pressed (MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		
		Enumeration<Finger> cursorList = MouseToTUIO.cursorList.elements();
		while (cursorList.hasMoreElements()) {
			Finger cursor = cursorList.nextElement();
			Point point = cursor.getPosition();
			
			if (point.distance(x,y)<7) {
				int selCur = -1;
				if (selectedCursor!=null) selCur = selectedCursor.sessionID;
				
				if ((me.isShiftDown() || me.getButton()==java.awt.event.MouseEvent.BUTTON2) && selCur != cursor.sessionID) {
					stickyCursors.removeElement(cursor.sessionID);
					if (jointCursors.contains(cursor.sessionID)) jointCursors.removeElement(cursor.sessionID);
					MouseToTUIO.removeCursor(cursor);
					cursorDelete();
					selectedCursor = null;
					return;
				} else if (me.isControlDown() || me.getButton()==java.awt.event.MouseEvent.BUTTON2) {
					if (jointCursors.contains(cursor.sessionID)) jointCursors.removeElement(cursor.sessionID);
					else jointCursors.addElement(cursor.sessionID);
					return;
				} else {
					selectedCursor = cursor;
					return;
				} 
			}
		}
			
		if (me.isControlDown() || me.getButton()==java.awt.event.MouseEvent.BUTTON3) return;
		
		if (table.contains(new Point(x, y))) {                       	
			sessionID++;
			selectedCursor = MouseToTUIO.addCursor(sessionID,x,y);
			cursorMessage();
			if (me.isShiftDown() || me.getButton()==java.awt.event.MouseEvent.BUTTON2) stickyCursors.addElement(selectedCursor.sessionID);
			return;
		}

		selectedCursor = null;
	}
	
	/**
	 * Removes the cursor or makes it stationary if it is a sticky cursor
	 * 
	 * @param me MouseEvent - The mouse released event
	 */
	public void mouse_released (MouseEvent me) {
		if ( (selectedCursor!=null) )  {
			if (!stickyCursors.contains(selectedCursor.sessionID)) {
				selectedCursor.stop();
				cursorMessage();
				if (jointCursors.contains(selectedCursor.sessionID)) jointCursors.removeElement(selectedCursor.sessionID);
				MouseToTUIO.removeCursor(selectedCursor);
				cursorDelete();
			} else {
				selectedCursor.stop();
				cursorMessage();
			}
			
			selectedCursor = null;
		}
	}

	/**
	 * Sends the table state every second
	 */
	public void run() {
		running = true;
		while(running) {
			try { Thread.sleep(1000); }
			catch (Exception e) {}

			long currentFrameTime = System.currentTimeMillis();
            long dt = currentFrameTime - lastFrameTime;
            if (dt>1000) {
				completeCursorMessage();
			}
		}
	}
}
