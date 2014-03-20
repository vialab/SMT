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

	//other shit
	Zone a = new Zone( 220, 220, 100, 100);
	Zone b = new Zone( 220, 220, 100, 100);
	SMT.add( a);	
	a.add( b);
	System.out.printf(
		"a: %s, %s\nb: %s, %s\n",
		a, a.getPickColor(),
		b, b.getPickColor());
}

void draw(){
	background( 50, 50, 50);
	pushStyle();
	fill( 25, 25, 25, 130);
	stroke( 220, 220, 220, 130);
	rect( 700, 10, 480, 320);
	image(
		SMT.picker.picking_context,
		690, 10, 500, 333);
	popStyle();
}

void drawZone( Zone zone){
	pushStyle();
	fill( 100, 170, 100);
	stroke( 5, 5, 5, 255);
	rect( 0, 0, 100, 100, 3);
	popStyle();
}

void pickDrawZone( Zone zone){
	rect( 0, 0, 100, 100, 5);
}