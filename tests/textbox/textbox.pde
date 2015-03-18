/**
 * Sketch for Java Tutorial.
 */

import vialab.SMT.*;

// vars
int window_width = 1200;
int window_height = 800;
boolean draw_fps = true;

//zones
TextBox textbox;
SwipeKeyboard keyboard;

void setup(){
	//basic setup
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create zones
	textbox = new TextBox( "test test");
	textbox.setAutoWidthEnabled( true);
	textbox.translate( 400, 20);
	keyboard = new SwipeKeyboard();
	keyboard.setAnchorsEnabled( false);
	keyboard.translate(
		( window_width - keyboard.getWidth()) / 2,
		window_height - keyboard.getHeight() - 40);
	keyboard.addKeyListener( textbox);

	//add our zones to the sketch
	SMT.add( textbox, keyboard);
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

//keyboard handle
void keyPressed(){
	//println( key);
	switch( key){
		case 'r':{
			textbox.setContent( "");
			break;}
		default: break;
	}
}
