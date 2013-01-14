/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  frameRate(600);
  TouchClient.init(this, TouchSource.MOUSE);
  Zone z = new Zone("Parent1", 400, 400, 200, 200);
  Zone zc = new Zone("Child1", 0, 0, 100, 100);
  zc.add(new Zone("Child1", 100, 0, 100, 100));
  Zone zr = new Zone("Remove", 200, 0, 100, 100);
  Zone clone = new Zone("Clone", 200, 200, 50, 50);
  z.add(zc);
  z.add(zr);
  z.add(clone);
  TouchClient.add(z);
  //TouchClient.add(z.clone());
}

void draw() {
  background(79, 129, 189);
  fill(0);
  text(round(frameRate)+"fps, # of zones: "+TouchClient.getZones().length, width/2, 10);
}

void touchParent1(Zone z) {
  z.rst();
}

void drawParent1(Zone z) {
  fill(0, 255, 0);
  rect(0, 0, z.width, z.height);
  fill(0);
  text("w:"+z.width+" h:"+z.height, 10, 10);
}

void touchChild1(Zone z) {
  z.rst();
}

void drawChild1(Zone z) {
  fill(255, 0, 0);
  rect(0, 0, z.width, z.height);
  fill(0);
  text("w:"+z.width+" h:"+z.height, 10, 10);
}

void touchRemove(Zone z) {
  TouchClient.remove(z.getParent());
}

void drawRemove(Zone z) {
  fill(255, 0, 0);
  rect(0, 0, z.width, z.height);
  fill(0);
  stroke(0);
  line(0, 0, z.width, z.height);
  line(0, z.height, z.width, 0);
  text("w:"+z.width+" h:"+z.height, 10, 10);
}

void touchClone(Zone z) {
  //Zone clone=z.getParent().clone();
  //client.add(clone);
  Zone clone =new Zone("Child1", 10, 600, 100, 100);
  //adding new indirect zones seems to cause massive slowdowns
  clone.setDirect(true);
  TouchClient.add(clone);
}

void drawClone(Zone z) {
  fill(123, 0, 234);
  rect(0, 0, z.width, z.height);
  fill(0);
  text("clone", 10, 10);
}

