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
 * Test program for validating that all supported renderers work.
 * by Kalev Kalda Sikes
 */
public class TestZoneAccessors extends PApplet {

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
		size( window_width, window_height, P3D);
		frame.setTitle("Zone Accessor Test");
		//smt library setup
		SMT.init( this, TouchSource.MULTIPLE);
		Zone one = new Zone( "MyZone", 100, 100, 400, 400);
		Zone other = new Zone( "MySubZone", 100, 100, 200, 200);
		one.add( other);
		SMT.add( one);
	}

	public void draw(){
		fill( 255, 255, 255);
		//draw background
		rect(
			-window_halfWidth, -window_halfHeight,
			2 * window_width, 2 * window_height);
	}
	public void stop(){
		super.stop();
	}

	//zone functions
	public void drawMyZone( Zone zone){
		fill( 0x88, 0xdd, 0x88);
		rect( 0, 0, zone.width, zone.height);
	}
	public void pickDrawMyZone( Zone zone){
		rect( 0, 0, zone.width, zone.height);
	}
	public void touchMyZone( Zone zone){
		System.out.println( zone.getName());
		System.out.printf( "\tzone.*: %d, %d\n",
			zone.x, zone.y);
		System.out.printf( "\tzone.get*(): %d, %d\n",
			zone.getX(), zone.getY());
		System.out.printf( "\tzone.getLocal*(): %f, %f\n",
			zone.getLocalX(), zone.getLocalY());
		zone.drag();
	}
	public void drawMySubZone( Zone zone){
		fill( 0x88, 0xdd, 0x88);
		rect( 0, 0, zone.width, zone.height);
	}
	public void pickDrawMySubZone( Zone zone){
		rect( 0, 0, zone.width, zone.height);
	}
	public void touchMySubZone( Zone zone){
		System.out.println( zone.getName());
		System.out.printf( "\tzone.*: %d, %d\n",
			zone.x, zone.y);
		System.out.printf( "\tzone.get*(): %d, %d\n",
			zone.getX(), zone.getY());
		System.out.printf( "\tzone.getLocal*(): %f, %f\n",
			zone.getLocalX(), zone.getLocalY());
		zone.drag();
	}

	// program entry point
	static public void main( String[] args){
		String[] appletArgs = new String[]{ "vialab.SMT.test.TestZoneAccessors"};
		if ( args != null)
			PApplet.main( concat( appletArgs, args));
		else
			PApplet.main( appletArgs);
	}
}
