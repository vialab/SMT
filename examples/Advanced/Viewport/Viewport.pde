/**
 * Sketch for ViewportZone Tutorial
 */

import vialab.SMT.*;

//Setup function for the applet
void setup(){
	//SMT and Processing setup
	size( 1200, 800, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create and add viewport zone
	ViewportZone viewport = new ViewportZone(
		"Viewport", 100, 50, 1000, 700);
	Zone frame = new Zone( "Frame",
		viewport.x, viewport.y, viewport.width, viewport.height);
	frame.setPickable( false);
	SMT.add( viewport, frame);

	//create and add reset button
	Zone reset = new Zone( "ResetButton", 10, 10, 300, 80);
	viewport.add( reset);

	//create and add map
	//map credit to ohara-356011.deviantart.com
	//http://ohara-356011.deviantart.com/art/Map-of-Planet-New-Earth-354460381
	PImage map = loadImage( "map.png");
	ImageZone map_zone = new ImageZone( "Map", map, - 1000, - 600);
	map_zone.setPickable( false);
	viewport.add( map_zone);
}

//Draw function for the sketch
void draw(){
	background( 30);
}

void drawViewport( Zone zone){
	fill( 10, 140);
	noStroke();
	rect( 0, 0, zone.width, zone.height);
}
void touchViewport( Zone zone){
	zone.pinch(); //aka. rst( false, true, true)
}

void drawFrame( Zone zone){
	noFill();
	stroke( 240, 200);
	strokeWeight( 5);
	rect( 0, 0, zone.width, zone.height);
}
//disable picking for the frame
//void pickDrawFrame( Zone zone){}

//disable picking for the map image
//void pickDrawMap( Zone zone){}