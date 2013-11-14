/**
 *  This example makes a simple view port, of which the image
 *  below can be dragged, it uses an indirect zone which will
 *  only render its contents inside its boundries as a texture
 */

import vialab.SMT.*;

void setup(){
	size(displayWidth, displayHeight, P3D);
	SMT.init(this, TouchSource.MULTIPLE);
	Zone asdf = new ContainerZone("Asdf", displayWidth/6, displayHeight/6, 2 * displayWidth/3, 2 * displayHeight/3);
	Zone viewport = new ContainerZone("ViewPort", displayWidth/4, displayHeight/4, displayWidth/2, displayHeight/2);
	Zone moving = new Zone("MovingZone", 0, 0, displayWidth/2, displayHeight/2);
	SMT.add( asdf);
	asdf.add( viewport);
	viewport.setDirect(false);
	viewport.add( moving);
	moving.add( new ImageZone(loadImage("0.jpg"), 50, 50, 50, 50));
	moving.add( new ImageZone(loadImage("0.jpg"), 150, 150, 50, 50));
}

void drawAsdf(Zone z){
	fill( 200, 150, 150);
	rect(0, 0, z.width, z.height);
}
void drawViewPort(Zone z){
	fill(0);
	rect(0, 0, z.width, z.height);
}

void drawMovingZone(Zone z){
	fill(0);
	rect(0, 0, z.width, z.height);
}

void touchMovingZone(Zone z){
	z.hSwipe();
}

void draw(){
	background(255); 
}
