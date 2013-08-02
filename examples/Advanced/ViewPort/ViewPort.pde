/**
 *  This example makes a simple view port, of which the image
 *  below can be dragged, it uses an indirect zone which will
 *  only render its contents inside its boundries as a texture
 */

import vialab.SMT.*;

void setup(){
  size(displayWidth, displayHeight, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  Zone z =new ContainerZone("ViewPort", displayWidth/4, displayHeight/4, displayWidth/2, displayHeight/2);
  z.setDirect(false);
  z.add(new ImageZone("0.jpg"));
  SMT.add(z);
}

void touchImageZone(Zone z){
  z.drag();
}

void draw(){
  background(255); 
}
