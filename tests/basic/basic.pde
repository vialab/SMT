//SMT library imports
import vialab.SMT.*;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
	SMT.add( new Zone( 0, 0, 100, 100));	
}

void draw(){
	//draw background
	pushStyle();
	background( 80, 80, 80);
	strokeWeight( 5);
	rect( 10, 10, 100, 100, 5);
	popStyle();
}
