/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

void setup() {
  size(800, 800, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  for(int i=0; i<12; i++){
    TouchClient.add(new Zone("Zone",200,400, 50, 50));
  }
  TouchClient.add(new Zone("Grid",400, 400, 100, 100));
}

void draw() {
  background(79, 129, 189);
}

void touchZone(Zone zone){
  zone.rst();
}

void touchGrid(Zone zone){
  TouchClient.grid(0,0,width,100,100,TouchClient.getZones());
}

void drawGrid(Zone zone){
  fill(100);
  rect(0,0,zone.width,zone.height);
  text("Allign to grid",10,10);
}

void drawZone(Zone zone){
  fill(0);
  ellipse(zone.width/2,zone.height/2,zone.width,zone.height); 
}
