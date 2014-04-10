/**
 *  This example makes a simple view port, of which the image
 *  below can be dragged, it uses an indirect zone which will
 *  only render its contents inside its boundries as a texture
 */

import vialab.SMT.*;

void setup(){
	//initial setup
	size( displayWidth, displayHeight, SMT.RENDERER);
	SMT.init( this, TouchSource.AUTOMATIC);

	//create zones
	Zone viewport = new ContainerZone( "ViewPort",
		displayWidth / 4, displayHeight / 4, displayWidth / 2, displayHeight / 2);
	Zone moving = new Zone(
		"MovingZone", 0, 0, displayWidth / 2, displayHeight / 2);

	//add zones and stuff
	SMT.add( viewport);
	viewport.setDirect(false);
	viewport.add( moving);

	//add little flower pics
	moving.add( new ImageZone( loadImage("0.jpg"), 50, 50, 50, 50));
	moving.add( new ImageZone( loadImage("0.jpg"), 150, 150, 50, 50));
}

void draw(){
	background(30); 
}

//function definitions
void drawViewPort( Zone zone){
	fill(0);
	rect(0, 0, zone.width, zone.height);
}
void drawMovingZone( Zone zone){
	fill(0);
	rect(0, 0, zone.width, zone.height);
}
void touchMovingZone( Zone zone){
	zone.hSwipe();
}
