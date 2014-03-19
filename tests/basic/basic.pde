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
	Zone asdf = new Zone( 220, 220, 100, 100);
	asdf.add( new Zone( 220, 220, 100, 100));
	SMT.add( asdf);	
}

void draw(){
	//draw background
	pushStyle();
	background( 80, 80, 80);
	fill( 240, 80, 80);
	stroke( 220, 220, 220, 130);
	strokeWeight( 5);
	rect( 110, 110, 100, 100, 5);
	popStyle();
}

void drawZone( Zone zone){
	pushStyle();
	fill( 120, 200, 120);
	stroke( 5, 5, 5, 130);
	strokeWeight( 5);
	rect( 0, 0, 100, 100, 5);
	popStyle();
}