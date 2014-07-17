/**
 * Sketch for ViewportZone Tutorial
 */

import vialab.SMT.*;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( displayWidth, displayHeight, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);
}

//Draw function for the sketch
void draw(){
	background( 30);
}