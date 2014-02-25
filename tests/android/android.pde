import vialab.SMT.*;

void setup(){
	size( 500, 500, P3D);
	SMT.setTouchDraw( TouchDraw.NONE);
	//SMT.init( this, TouchSource.ANDROID);
	//draw
	background( 35);
	strokeWeight( 5);
	stroke( 250, 250, 250, 180);
	fill( 240, 140, 140, 200);
	rect( 100, 100, 200, 200);
	//print?
	println("asdf");
	new Zone();
}

void draw(){
	background( 35);
	strokeWeight( 5);
	stroke( 250, 250, 250, 180);
	fill( 140, 240, 140, 200);
	rect( 100, 100, 200, 200);
}
