/**
 *  This example makes a simple view port, of which the layer
 *  below can be dragged 
 */

import vialab.SMT.*;

PImage img;

void setup(){
  size(displayWidth, displayHeight, P3D);
  SMT.init(this, TouchSource.MOUSE);
  SMT.add(new Zone("Under", 0, 0, displayWidth, displayHeight));
  SMT.add(new ShapeZone("Over", 0, 0, displayWidth, displayHeight));
  img = loadImage("0.jpg");
}

void drawOver(){
  fill(255);
  noStroke();
  rect(0,0,displayWidth, displayHeight*4/10);
  rect(0,displayHeight*6/10,displayWidth, displayHeight*4/10);
  rect(0,0,displayWidth*4/10, displayHeight);
  rect(displayWidth*6/10,0,displayWidth*4/10, displayHeight);
}

void touchOver(){}

void drawUnder(){
  image(img,0,0);
}

void touchUnder(Zone z){
  z.drag(); 
}

void draw(){
  background(0); 
}
