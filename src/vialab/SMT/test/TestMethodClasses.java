 package vialab.SMT.test;

//standard library imports

//processing imports
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

//SMT library imports
import vialab.SMT.*;

/**
 * Test program
 * by Kalev Kalda Sikes
 */
public class TestMethodClasses extends PApplet {

	// display properties
	int window_width = 1600;
	int window_height = 900;
	int window_halfWidth;
	int window_halfHeight;
	final int fps_limit = 60;

	// main functions
	public void setup(){
		window_halfWidth = window_width / 2;
		window_halfHeight = window_height / 2;
		//processing library setup
		frameRate( fps_limit);
		size( window_width, window_height, P3D);
		textMode( SHAPE);
		frame.setTitle("Test");

		//use add method classes
		SMT.addMethodClasses( StaticZoneMethodContainer.class);

		//smt library setup
		SMT.init( this, TouchSource.AUTOMATIC);
		SMT.setTouchDraw( TouchDraw.TEXTURED);

		//add zones
		Zone myZone = new Zone( "MyZone", 10, 10, 100, 100);
		Zone myOtherZone = new Zone( "MyOtherZone", 120, 10, 100, 100);

		//add zones
		SMT.add( myZone);
		SMT.add( myOtherZone);

		//use set bound object
		myZone.setBoundObject( new ZoneMethodContainer());
	}

	public void draw(){
		//draw background
		fill( 50, 50, 50);
		rect( 0, 0, window_width, window_height);
	}

	private class ZoneMethodContainer {
		public void drawMyZone( Zone zone){
			zone.fill( 180, 100, 100);
			zone.rect( 0, 0, 100, 100);
		}
		public void touchMyZone( Zone zone){
			zone.rect( 0, 0, 100, 100);
		}
	}

	// program entry point
	static public void main( String[] args){
		String[] appletArgs = new String[]{ "vialab.SMT.test.TestMethodClasses"};
		if( args != null) {
			PApplet.main( concat( args, appletArgs));
		} else {
			PApplet.main( appletArgs);
		}
	}
}
