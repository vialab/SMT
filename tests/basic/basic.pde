//SMT library imports
import vialab.SMT.*;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other
Zone indirect, frame, a, b, c, d, e, f;

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//other shit
	frame = new Zone( "Frame", 100, 100, 300, 300);
	indirect = new Zone( "IndirectZone", 00, 00, 300, 300);
	a = new Zone( 100, 50, 100, 100);
	b = new Zone( 50, 00, 100, 100);
	c = new Zone( 10, 10, 100, 100);
	d = new Zone( 10, 10, 100, 100);
	e = new Zone( 10, 10, 100, 100);
	f = new Zone( 10, 10, 100, 100);

	SMT.add( frame);
	frame.add( indirect);
	indirect.setDirect( false);
	indirect.add( a);
	indirect.add( b);
}

void draw(){
	//a.translate( 10, 0);
	background( 50, 50, 50);
	pushStyle();
	fill( 25, 25, 25, 130);
	stroke( 220, 220, 220, 130);
	rect( 700, 10, 480, 320);
	image(
		SMT.picker.picking_context,
		700, 10, 480, 320);
	popStyle();
}

void drawZone( Zone zone){
	pushStyle();
	fill( 100, 170, 100);
	stroke( 5, 5, 5, 255);
	strokeWeight( 2);
	rect( 0, 0, zone.width, zone.height, 5);
	popStyle();
}

void pickDrawZone( Zone zone){
	rect( 0, 0, zone.width, zone.height, 5);
}

void touchZone( Zone zone){
	zone.rst();
}

//void pickDrawIndirectZone( Zone zone){}
void drawIndirectZone( Zone zone){
	//background( 150);
	pushStyle();
	fill( 180, 100, 100);
	stroke( 5, 5, 5, 255);
	strokeWeight( 5);
	rect( - 10, - 10, zone.width + 20, zone.height + 20, 15);
	popStyle();
}

void touchIndirectZone( Zone zone){
	zone.rst();
}

void drawFrame( Zone zone){
	pushStyle();
	fill( 5, 5, 5, 180);
	stroke( 240, 240, 240, 220);
	strokeWeight( 5);
	rect( - 5, - 5, zone.width + 00, zone.height + 00, 10);
	popStyle();
}

void touchFrame( Zone zone){
	zone.rst();
}
