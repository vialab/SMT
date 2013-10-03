//SMT library imports
import vialab.SMT.*;
import vialab.SMT.zone.*;

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
	size( window_width, window_height, JAVA2D);
	textMode( SHAPE);
	frame.setTitle("Swipe Keyboard Test");
	//smt library setup
	SMT.init( this, TouchSource.MULTIPLE);

	//add keyboard test
	//keyboard = new SwipeKeyboard();
	keyboard = new SwipeKeyboard( SwipeKeyboard.condensedLayout);
	keyboard.translate( 40, 300);
	SMT.add( keyboard);
}

void draw(){
	fill( 255, 255, 255);
	//draw background
	rect( 0, 0, window_width, window_height);
}

void stop(){
	super.stop();
}