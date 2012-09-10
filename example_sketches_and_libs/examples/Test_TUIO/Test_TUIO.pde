/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import TUIO.*;


//set some configuration constants
final boolean USE_MOUSE_TO_TUIO=true;
final boolean DRAW_TOUCH_POINTS=true;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  //frameRate(1000);
  client = new TouchClient(this, USE_MOUSE_TO_TUIO, true);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
}
