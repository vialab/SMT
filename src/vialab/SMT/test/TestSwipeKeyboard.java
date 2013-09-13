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
import vialab.SMT.zone.*;

//SMT library imports

/**
 * Test program for the experimental SwipeKeyboard class
 * by Kalev Kalda Sikes
 */
public class TestSwipeKeyboard extends PApplet {

	//constants
	final int window_width = 1200;
	final int window_height = 800;
	final int fps_limit = 60;

	//objects
	SwipeKeyboard keyboard;

	//other
	int window_halfWidth;
	int window_halfHeight;

	//main functions
	public void setup(){
		window_halfWidth = window_width / 2;
		window_halfHeight = window_height / 2;
		//processing library setup
		frameRate( fps_limit);
		size( window_width, window_height, P3D);
		frame.setTitle("Swipe Keyboard Test");
		//smt library setup
		SMT.init( this, TouchSource.MULTIPLE);

		//add keyboard test
		keyboard = new SwipeKeyboard();
		SMT.add( keyboard);
	}

	public void draw(){
		fill( 255, 255, 255);
		//draw background
		rect( 0, 0, window_width, window_height);
	}

	public void stop(){
		super.stop();
	}

  static public void main( String[] passedArgs) {
    String[] appletArgs = new String[] { "vialab.SMT.test.TestSwipeKeyboard"};
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
