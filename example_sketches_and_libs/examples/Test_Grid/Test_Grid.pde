/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  Zone z = new Zone("Grid",400, 400, 100, 100);
  Zone z2 = new Zone("Zone",1200,400, 50, 50);
  TouchClient.add(z2);
  for(int i=0; i<25; i++){
    TouchClient.add(z2.clone());
  }
  TouchClient.add(z);
  print(TouchClient.getZones().length);
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+TouchClient.getZones().length,width/2,10);
  lights();
}

void touchZone(Zone zone){
  zone.rst();
}

void touchGrid(Zone zone){
  TouchClient.grid(100,100,1600,100,100,TouchClient.getZones());
}

void touchPick(Zone zone){
  zone.rst();
}

void drawGrid(Zone zone){
  pushStyle();
  fill(100);
  rect(0,0,zone.width,zone.height);
  popStyle();
  text("Allign to grid",10,10);
}

void drawZone(Zone zone){
  /*
  background(0);
  fill(random(255),random(255),random(255));
  ellipse(random(zone.width),random(zone.height),random(zone.width),random(zone.height)); 
  */
  //fill(0);
  //rect(0,0,zone.width,zone.height);
  translate(zone.width / 2, zone.height / 2);
  rotateY(map(mouseX, 0, zone.width, 0, PI));
  rotateZ(map(mouseY, 0, zone.height, 0, -PI));
  //noStroke();
  translate(0, -40, 0);
 // drawCylinder(10, 180, 200, 16); // Draw a mix between a cylinder and a cone
  //drawCylinder(70, 70, 120, 64); // Draw a cylinder
  //drawCylinder(0, 180, 200, 4); // Draw a pyramid
  pushStyle();
  drawCube();
  popStyle();
}

void drawCube(){
  scale(50);
  colorMode(RGB,1);
  beginShape(QUADS);

  fill(0, 1, 1); vertex(-1,  1,  1);
  fill(1, 1, 1); vertex( 1,  1,  1);
  fill(1, 0, 1); vertex( 1, -1,  1);
  fill(0, 0, 1); vertex(-1, -1,  1);

  fill(1, 1, 1); vertex( 1,  1,  1);
  fill(1, 1, 0); vertex( 1,  1, -1);
  fill(1, 0, 0); vertex( 1, -1, -1);
  fill(1, 0, 1); vertex( 1, -1,  1);

  fill(1, 1, 0); vertex( 1,  1, -1);
  fill(0, 1, 0); vertex(-1,  1, -1);
  fill(0, 0, 0); vertex(-1, -1, -1);
  fill(1, 0, 0); vertex( 1, -1, -1);

  fill(0, 1, 0); vertex(-1,  1, -1);
  fill(0, 1, 1); vertex(-1,  1,  1);
  fill(0, 0, 1); vertex(-1, -1,  1);
  fill(0, 0, 0); vertex(-1, -1, -1);

  fill(0, 1, 0); vertex(-1,  1, -1);
  fill(1, 1, 0); vertex( 1,  1, -1);
  fill(1, 1, 1); vertex( 1,  1,  1);
  fill(0, 1, 1); vertex(-1,  1,  1);

  fill(0, 0, 0); vertex(-1, -1, -1);
  fill(1, 0, 0); vertex( 1, -1, -1);
  fill(1, 0, 1); vertex( 1, -1,  1);
  fill(0, 0, 1); vertex(-1, -1,  1);

  endShape();
  }
