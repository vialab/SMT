 package vialab.SMT.test;

//standard library imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

//processing imports
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

//smt imports
import vialab.SMT.*;
import vialab.SMT.swipekeyboard.*;

//SMT library imports

/**
 * Test program for the experimental SwipeKeyboard class
 * by Kalev Kalda Sikes
 */
public class TestSwipeKeyboard extends PApplet {

	// display properties
	int window_width = 1200;
	int window_height = 800;
	int window_halfWidth;
	int window_halfHeight;
	final int fps_limit = 60;

	// objects
	SwipeKeyboard keyboard;
	SwipeKeyboard arrows;

	// other
	PShape arrow_down;
	PShape arrow_left;
	PShape arrow_right;
	PShape arrow_up;
	PShape caps;
	PShape shift;


	// main functions
	public void setup(){
		window_halfWidth = window_width / 2;
		window_halfHeight = window_height / 2;
		//processing library setup
		frameRate( fps_limit);
		size( window_width, window_height, P3D);
		textMode( SHAPE);
		frame.setTitle("Swipe Keyboard Test");
		//smt library setup
		SMT.init( this, TouchSource.AUTOMATIC);
		SMT.setTouchDraw( TouchDraw.TEXTURED);

		//add keyboards
		keyboard = new SwipeKeyboard( SwipeKeyboard.condensedLayout);
		keyboard.translate( 40, 300);
		SMT.add( keyboard);

		arrows = new SwipeKeyboard( SwipeKeyboard.arrowKeysLayout);
		SMT.add( arrows);

		//add text zone
		SwipeDisplayer texty = new SwipeDisplayer( "Texty", 500, 0, 500, 200);
		SMT.add( texty);
		keyboard.addKeyListener( texty);
		keyboard.addSwipeKeyboardListener( texty);

		arrow_down = loadShape( "resources/arrow_down.svg");
		arrow_left = loadShape( "resources/arrow_left.svg");
		arrow_right = loadShape( "resources/arrow_right.svg");
		arrow_up = loadShape( "resources/arrow_up.svg");
		caps = loadShape( "resources/caps.svg");
		shift = loadShape( "resources/shift.svg");
		
		System.out.printf( "arrow_down: %f, %f\n",
			arrow_down.width, arrow_down.height);
		System.out.printf( "arrow_left: %f, %f\n",
			arrow_left.width, arrow_left.height);
		System.out.printf( "arrow_right: %f, %f\n",
			arrow_right.width, arrow_right.height);
		System.out.printf( "arrow_up: %f, %f\n",
			arrow_up.width, arrow_up.height);
		System.out.printf( "caps: %f, %f\n",
			caps.width, caps.height);
		System.out.printf( "shift: %f, %f\n",
			shift.width, shift.height);
	}

	public void draw(){
		fill( 50, 50, 50);
		//draw background
		rect( 0, 0, window_width, window_height);
		//draw textures
		shape( arrow_down, 100, 0);
		shape( arrow_left, 200, 0);
		shape( arrow_right, 300, 0);
		shape( arrow_up, 400, 0);
		shape( caps, 500, 0, 100, 100);
		shape( shift, 600, 0, 100, 100);
	}

	public void stop(){
		super.stop();
	}

	private class SwipeDisplayer extends Zone
			implements SwipeKeyboardListener, KeyListener {
		String content;
		public SwipeDisplayer( String name, int x, int y, int width, int height){
			super( name, x, y , width, height);
			content = new String();
		}
		public void drawImpl(){
			pushStyle();
			noFill();
			strokeWeight( 3);
			stroke( 200, 120, 120, 150);
			rect( 0, 0, width, height);
			popStyle();
		}
		public void touchImpl(){
			rst();
		}

		//swipe events
		public void swipeCompleted( SwipeKeyboardEvent event){
			System.out.printf("Swipe Completed:\n\t");
			for( String suggestion : event.getSuggestions())
				System.out.printf( "%s ", suggestion);
			System.out.println();
		}
		public void swipeStarted( SwipeKeyboardEvent event){
			System.out.printf("Swipe Started:\n\t");
			for( String suggestion : event.getSuggestions())
				System.out.printf( "%s ", suggestion);
			System.out.println();
		}
		public void swipeProgressed( SwipeKeyboardEvent event){
			System.out.printf("Swipe Progressed:\n\t");
			for( String suggestion : event.getSuggestions())
				System.out.printf( "%s ", suggestion);
			System.out.println();
		}

		//key events
		public void keyPressed( KeyEvent event){
			System.out.printf( "Key Pressed: %c\n", event.getKeyChar());
		}
		public void keyReleased( KeyEvent event){
			System.out.printf( "Key Released: %c\n", event.getKeyChar());
		}
		public void keyTyped( KeyEvent event){
			System.out.printf( "Key Typed: %c\n", event.getKeyChar());
		}

	}

	// program entry point
	static public void main( String[] args) {
		String[] appletArgs = new String[] { "vialab.SMT.test.TestSwipeKeyboard"};
		if (args != null) {
			PApplet.main(concat(appletArgs, args));
		} else {
			PApplet.main(appletArgs);
		}
	}
}
