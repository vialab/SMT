/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;
import TUIO.*;
import android.*;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.MOUSE);
  client.setDrawTouchPoints(true, 10);
  Zone z = new Zone("Pick1", 400, 400, 200, 200);
  Zone z2 = new Zone("Pick2", 1200, 400, 600, 600);
  z2.setDirect(true);
  //z.setDirect(true);
  client.add(z2);
  client.add(z);
  frameRate(60);
}

void draw() {
  background(79, 129, 189);
  fill(0);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length, width/2, 10);
  client.drawPickBuffer(0, 0, 200, 200);
}

void touchPick1(Zone zone) {
  zone.rst();
}

void touchPick2(Zone zone) {
  zone.rst();
}

void drawPick1(Zone zone) {
  fill(155);
  rect(150, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

void drawPick2(Zone zone) {
  fill(125);
  rect(150, 100, 100, 100);
}

void pickDrawPick1(Zone zone) {

  rect(150, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

