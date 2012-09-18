/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import TUIO.*;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  frameRate(1000);
  client = new TouchClient(this, TouchClient.TouchSource.MOUSE);
  client.setDrawTouchPoints(true);
  Zone z = new Zone("Parent1",400, 400, 200, 200);
  Zone zc = new Zone("Child1", 0, 0, 100, 100);
  zc.add(new Zone("Child1", 100, 0, 100, 100));
  Zone zr = new Zone("Remove",200,0,100,100);
  Zone clone = new Zone("Clone",200,200,50,50);
  z.add(zc);
  z.add(zr);
  z.add(clone);
  client.add(z);
  //client.add(z.clone());
}

void draw() {
  //background(79, 129, 189);
  fill(0);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
}
  
void touchParent1(Zone z){
  z.rst();
}

void drawParent1(Zone z){
  background(0,255,0);
  fill(0);
  text("w:"+z.width+" h:"+z.height,10,10);
}

void touchChild1(Zone z){
  z.rst();
}

void drawChild1(Zone z){
  background(255,0,0);
  fill(0);
  text("w:"+z.width+" h:"+z.height,10,10);
}

void touchRemove(Zone z){
  client.remove(z.getParent());
}

void drawRemove(Zone z){
  background(255,0,0);
  fill(0);
  text("w:"+z.width+" h:"+z.height,10,10);
  line(0,0,z.width,z.height);
  line(0,z.height,z.width,0);
}

void touchClone(Zone z){
   client.add(z.getParent().clone());
   //client.add(new Zone("Child1",10,600,100,100));
}

void drawClone(Zone z){
  background(123,0,234);
  text("clone",10,10);
}
