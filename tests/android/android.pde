import vialab.SMT.*;

void setup(){
	size( 500, 500, SMT.RENDERER);
	//SMT.setTouchDraw( TouchDraw.NONE);
	//SMT.init( this, TouchSource.ANDROID);
}

void draw(){
	background( 35);
	strokeWeight( 5);
	stroke( 250, 250, 250, 180);
	fill( 140, 240, 140, 200);
	rect( 100, 100, 200, 200);
}
