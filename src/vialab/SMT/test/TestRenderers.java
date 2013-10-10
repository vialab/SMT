package vialab.SMT.test;

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
public class TestRenderers extends PApplet {

	// constants
	final int window_width = 1200;
	final int window_height = 800;
	final int fps_limit = 60;

	// other
	int window_halfWidth;
	int window_halfHeight;

	// main functions
	public void setup(){
		window_halfWidth = window_width / 2;
		window_halfHeight = window_height / 2;
		//processing library setup
		frameRate( fps_limit);
		size( window_width, window_height, P2D);
		frame.setTitle("Renderer Test");
		//smt library setup
		SMT.init( this, TouchSource.MULTIPLE);
		SMT.add( new Zone( 100, 100, 400, 400));
	}

	public void draw(){
		fill( 255, 255, 255);
		//draw background
		rect( 0, 0, window_width, window_height);
	}

	public void stop(){
		super.stop();
	}

	// program entry point
  static public void main( String[] passedArgs) {
    String[] appletArgs = new String[] { "vialab.SMT.test.TestRenderers"};
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
