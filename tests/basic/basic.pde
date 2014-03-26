//SMT library imports
import vialab.SMT.*;

//constants
int window_width = 1200;
int window_height = 800;
int window_halfWidth;
int window_halfHeight;
int fps_limit = 60;
//other
Zone a, b, c, d, e, f;

//main functions
void setup(){
	window_halfWidth = window_width / 2;
	window_halfHeight = window_height / 2;
	//processing window setup
	frameRate( fps_limit);
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//other shit
	a = new Zone( 50, 50, 100, 100);
	b = new Zone( 50, 00, 100, 100);
	c = new Zone( 00, 50, 300, 300);
	d = new Zone( 10, 10, 100, 100);
	e = new Zone( 10, 10, 100, 100);
	f = new Zone( 10, 10, 100, 100);

	a.add( c);
	a.add( b);

	b.add( e);

	c.add( d);
	c.add( f);

	c.setDirect( false);

	SMT.add( a);
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
	rect( 0, 0, zone.width, zone.height, 3);
	popStyle();
}

void pickDrawZone( Zone zone){
	rect( 0, 0, zone.width, zone.height, 5);
}

void touchZone( Zone zone){
	zone.rst();
}