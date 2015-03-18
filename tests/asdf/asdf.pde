/**
 * Sketch for Java Tutorial.
 */

import vialab.SMT.*;

// vars
int window_width = 1200;
int window_height = 800;
boolean draw_fps = true;

//zones
Zone asdf;

void setup(){
	//basic setup
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create zones
	Zone textzone = new TextZone( 10, 10, 200, 50,
		"test test", false, false, false);
	SwipeKeyboard keyboard = new SwipeKeyboard();

	//add our zones to the sketch
	SMT.add( textzone, keyboard);
}

void draw(){
	background( 30);
	if( draw_fps) drawFrameRate();
}

//drawing methods
public void drawFrameRate(){
	float fps = this.frameRate;
	String fps_text = String.format( "fps: %.0f", fps);
	pushStyle();
	fill( 240, 240, 240, 180);
	textAlign( LEFT, TOP);
	textSize( 32);
	text( fps_text, 10, 10);
	popStyle();
}
