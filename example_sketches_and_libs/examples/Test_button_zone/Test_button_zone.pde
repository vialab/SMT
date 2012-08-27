/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;
import vialab.mouseToTUIO.*;
import TUIO.*;
import processing.opengl.PGraphicsOpenGL;
import codeanticode.glgraphics.*;

//set some configuration constants
final boolean USE_MOUSE_TO_TUIO=true;
final boolean DRAW_TOUCH_POINTS=true;

TouchClient client;

void setup() {
  size(screenWidth, screenHeight, GLConstants.GLGRAPHICS);
  client = new TouchClient(this, USE_MOUSE_TO_TUIO, true);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS,10);
  Zone z = new ButtonZone("Button",400, 400, 100, 100);
  client.add(z);
  frameRate(1000);
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+client.getZones().length,width/2,10);
}

void pressButton(Zone zone){
  print("ButtonPressed\n");
}