package vialab.SMT.test;

//processing imports
import processing.core.*;

//smt imports
import vialab.SMT.*;

/**
 * A test program to validate that the zone get methods are accurate
 */
public class TestGets extends PApplet {

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
		frame.setTitle("Zone Get Accessor Test");

		//smt library setup
		SMT.init( this, TouchSource.MULTIPLE);

		//create zones
		Zone one = new Zone( "MyZone", 100, 100, 400, 400);
		Zone two = new Zone( "MyZone", 100, 100, 200, 200);
		Zone three = new Zone( "MyZone", 100, 100, 200, 200);
		Zone four = new Zone( "MyZone", 100, 100, 200, 200);
		Zone five = new Zone( "MyZone", 100, 100, 200, 200);
		Zone six = new Zone( "MyZone", 100, 100, 200, 200);

		//add zones
		SMT.add( one);
		one.add( two);
		two.add( three);
		three.add(four); 
		four.add( five);
		five.add( six);
	}

	public void drawMyZone( Zone zone){
		rect( 0, 0, zone.width, zone.height);
	}

	// program entry point
	static public void main( String[] args){
		String[] appletArgs = new String[]{ "vialab.SMT.test.TestGets"};
		if ( args != null) PApplet.main( concat( appletArgs, args));
		else PApplet.main( appletArgs);
	}
}
