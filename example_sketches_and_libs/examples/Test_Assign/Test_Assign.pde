/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;

TouchClient client;
Zone z;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.MOUSE);
  client.setDrawTouchPoints(true,100);
  z = new Zone("Test",0,0,50,50);
  client.add(z);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

void drawTest(Zone z){
  rect(0,0,50,50);
}

void touchTest(Zone z){
  z.rst(); 
}

void touchDown(){
  z.assign(client.getTouches()); 
}

