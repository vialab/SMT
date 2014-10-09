/**
 * Sketch for Basics Tutorial 2
 */

import vialab.SMT.*;

//variables for this applet
color myZone_color = #00dddd;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( 1400, 800, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//Make a new Zone
	Zone zone = new Zone( "MyZone");
	zone.translate( 200, 200);
	SMT.add( zone);

	//Make another Zone
	Zone other = new Zone( "MyOtherZone");
	other.translate( 400, 200);
	SMT.add( other);
}

//Draw function for the sketch
void draw(){
	background( 30);
}


// "MyZone" functions
//Draw functions for "MyZone"
void drawMyZone( Zone zone){
	noStroke();
	fill( myZone_color);
	rect(0, 0, 100, 100);
}

//Touch function for "MyZone"
void touchMyZone( Zone zone){
	zone.drag();
}

//Touch Down function for "MyZone"
void touchDownMyZone( Zone zone){
	myZone_color = #dd8888;
}

//Touch Up function for "MyZone"
void touchUpMyZone( Zone zone){
	myZone_color = #00dddd;
}


// "MyOtherZone" functions
//Draw functions for "MyOtherZone"
void drawMyOtherZone( Zone zone){
	noStroke();
	fill( #88dd88);
	rect(0, 0, 100, 100);
}

//Touch function for "MyOtherZone"
void touchMyOtherZone( Zone zone){}

//Touch Pressed function for "MyOtherZone"
void pressMyOtherZone( Zone zone){
	myZone_color = #88dd88;
}