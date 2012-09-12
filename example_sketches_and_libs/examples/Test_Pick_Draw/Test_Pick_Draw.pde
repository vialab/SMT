/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;
import TUIO.*;


//set some configuration constants
final boolean USE_MOUSE_TO_TUIO=true;
final boolean DRAW_TOUCH_POINTS=true;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchClient.TOUCH_SOURCE_MOUSE);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS,10);
  Zone z = new Zone("Pick1",400, 400, 200, 200);
  Zone z2 = new Zone("Pick2",1200,400, 200, 200);
  z2.setDirect(true);
  client.add(z2);
  client.add(z);
  frameRate(1000);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
  client.drawPickBuffer(0,0,200,200);
}

void touchPick1(Zone zone){
  zone.rst();
}

void touchPick2(Zone zone){
  zone.drag();
}

void drawPick1(Zone zone){
  rect(100,100,100,100);
  ellipse(100,100,100,100); 
}

void drawPick2(Zone zone){
 
  rect(100,100,100,100);
}

void pickDrawPick1(Zone zone){
  rect(100,100,100,100);
  ellipse(100,100,100,100); 
}
