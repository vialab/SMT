/**
 * Test program for the experimental SwipeKeyboard class
 * by Kalev Kalda Sikes
 */

//SMT library imports
import vialab.SMT.*;
import vialab.SMT.zone.*;

//constants
final int display_width = 1200;
final int display_height = 800;
final int fps_limit = 60;

//objects
SwipeKeyboard keyboard;
SwipeKeyZone key;

//other
int display_halfWidth;
int display_halfHeight;

//main functions
void setup(){
	display_halfWidth = display_width / 2;
	display_halfHeight = display_height / 2;
	//processing library setup
	frameRate( fps_limit);
	size( display_width, display_height, P3D);
	//smt library setup
	SMT.init( this, TouchSource.MULTIPLE);

	//add key test
	key = new SwipeKeyZone();
	SMT.add( key);

	//add keyboard test
	keyboard = new SwipeKeyboard();
	//SMT.add( keyboard);
}

void draw(){
	fill( 255, 255, 255);
	//draw background
	rect( 0, 0, display_width, display_height);
}

void stop(){
	super.stop();
}