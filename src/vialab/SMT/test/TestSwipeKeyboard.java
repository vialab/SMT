 package vialab.SMT.test;

//standard library imports
import java.awt.event.KeyEvent;
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

	// constants
	int window_width = 1200;
	int window_height = 800;
	final int fps_limit = 60;

	// objects
	SwipeKeyboard keyboard;

	// other
	int window_halfWidth;
	int window_halfHeight;

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

		//add keyboard test
		//keyboard = new SwipeKeyboard();
		keyboard = new SwipeKeyboard( SwipeKeyboard.condensedLayout);
		keyboard.addSwipeKeyboardListener( new DebugSwipeKeyboardListener());
		keyboard.translate( 40, 300);
		SMT.add( keyboard);
		SMT.add( new SwipeKeyboard( SwipeKeyboard.arrowKeysLayout));

		//add text zone
		SwipeDisplayer texty = new SwipeDisplayer( "Texty", 500, 0, 500, 200);
		SMT.add( texty);
	}

	public void draw(){
		fill( 255, 255, 255);
		//draw background
		rect( 0, 0, window_width, window_height);
	}

	public void stop(){
		super.stop();
	}

	private class SwipeDisplayer extends Zone
			implements SwipeKeyboardListener {
		String content;
		public SwipeDisplayer( String name, int x, int y, int width, int height){
			super( name, x, y , width, height);
			content = new String();
		}
		public void drawImpl(){}
		public void touchImpl(){
			rst();
		}

		public void swipeCompleted( SwipeKeyboardEvent event){}
		public void swipeStarted( SwipeKeyboardEvent event){}
		public void swipeProgressed( SwipeKeyboardEvent event){}
	}

	// program entry point
  static public void main( String[] passedArgs) {
    String[] appletArgs = new String[] { "vialab.SMT.test.TestSwipeKeyboard"};
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
