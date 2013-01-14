/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.setDrawTouchPoints(true, 10);
  Zone z = new Zone("Pick1", 400, 400, 200, 200);
  Zone z2 = new Zone("Pick2", 1200, 400, 600, 600);
  z2.setDirect(true);
  //z.setDirect(true);
  TouchClient.add(z2);
  TouchClient.add(z);
  frameRate(60);
}

void draw() {
  background(79, 129, 189);
  fill(0);
  text(round(frameRate)+"fps, # of zones: "+TouchClient.getZones().length, width/2, 10);
  TouchClient.drawPickBuffer(0, 0, 200, 200);
}

void touchPick1(Zone zone) {
  zone.rst();
}

void touchPick2(Zone zone) {
  zone.rst();
}

void drawPick1(Zone zone) {
  fill(255);
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

void drawPick2(Zone zone) {
  fill(255);
  rect(100, 100, 100, 100);
}

void pickDrawPick1(Zone zone) {
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

