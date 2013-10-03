/**
 *  This example makes a simple view port, of which the image
 *  below can be dragged, it uses an indirect zone which will
 *  only render its contents inside its boundries as a texture
 */

import vialab.SMT.*;

void setup(){
  size(displayWidth, displayHeight, JAVA2D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new ContainerZone("ViewPort", displayWidth/4, displayHeight/4, displayWidth/2, displayHeight/2));
  SMT.get("ViewPort").setDirect(false);
  SMT.addChild("ViewPort", new Zone("MovingZone", 0, 0, displayWidth/2, displayHeight/2));
  SMT.addChild("MovingZone",new ImageZone(loadImage("0.jpg"), 50, 50, 50, 50));
  SMT.addChild("MovingZone",new ImageZone(loadImage("0.jpg"), 150, 50, 50, 50));
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
