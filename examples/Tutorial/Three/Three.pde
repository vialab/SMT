/**
 * Sketch for Basics Tutorial 3
 */

import vialab.SMT.*;

boolean window_fullscreen = false;
int window_width = 1400;
int window_height = 800;

//Setup function for the applet
void setup(){
  if( window_fullscreen){
    window_width = displayWidth;
    window_height = displayHeight;
  }
	//SMT and Processing setup
	size( window_width, window_height, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//Make a new Zone
	Zone zone = new Zone( "MyZone");
	zone.setSize( 400, 400);
	zone.translate( 100, 100);
	SMT.add( zone);

	//Make a child Zone
	Zone child = new Zone( "ChildZone");
	child.setSize( 200, 200);
	child.translate( 100, 100);
	zone.add( child);

	//Make a grandchild Zone
	Zone grandchild = new Zone( "GrandChildZone");
	grandchild.setSize( 100, 100);
	grandchild.translate( 50, 50);
	child.add( grandchild);
}

//Draw function for the sketch
void draw(){
	background( 30);
}


// "MyZone" functions

//Functions for "MyZone"
void drawMyZone( Zone zone){
	noStroke();
	fill( #00bbbb);
	rect(0, 0, 400, 400);
}
void touchMyZone( Zone zone){
	zone.rst();
}

// "ChildZone" functions

//Functions for "ChildZone"
void drawChildZone( Zone zone){
	noStroke();
	fill( #88dd88);
	rect(0, 0, 200, 200);
}
void touchChildZone( Zone zone){
	zone.rst();
}

// "GrandChildZone" functions

//Functions for "GrandChildZone"
void drawGrandChildZone( Zone zone){
	noStroke();
	fill( #aa66aa);
	rect(0, 0, 100, 100);
}
void touchGrandChildZone( Zone zone){
	zone.rst();
}
