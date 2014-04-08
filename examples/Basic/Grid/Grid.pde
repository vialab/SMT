/**
 * This example shows how to use the SMT.grid() function
 * to align Zones into a grid.
 * See http://vialab.github.io/SMT/vialab/SMT/SMT.html#grid(int, int, int, int, int, vialab.SMT.Zone...) 
 */
import vialab.SMT.*;

void setup() {
  size(800, 800, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  for(int i=0; i<12; i++){
    SMT.add(new Zone("Zone",200,400, 50, 50));
  }
  SMT.add(new Zone("Grid",400, 400, 100, 100));
}

void draw() {
  background(79, 129, 189);
}

void touchZone(Zone zone){
  zone.rst();
}

void touchGrid(Zone zone){
  SMT.grid(0,0,width,100,100,TouchClient.getZones());
}

void drawGrid(Zone zone){
  fill(100);
  rect(0,0,zone.width,zone.height);
  fill(0);
  text("Align to grid",10,10);
}

void drawZone(Zone zone){
  fill(0);
  ellipse(zone.width/2,zone.height/2,zone.width,zone.height); 
}
