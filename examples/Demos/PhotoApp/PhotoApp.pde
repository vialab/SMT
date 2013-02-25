/**
 *   Created by Erik Paluka
 *   University of Ontario Institute of Technology
 *   August 2011
 *   A photo album sketch!
 *   Ported to SMTv3 by Zach Cook
 */
import vialab.SMT.*;
final static int IMAGE_COPIES=2;
PImage[] img = new PImage[11];

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.SMART);

  for (int i=0; i<11; i++) {  
    img[i] = loadImage(i + ".jpg");
  }

  for (int i=0; i<11*IMAGE_COPIES; i++) {  
    TouchClient.add(new ImageZone("ImageZone", img[i%11], 
    (int)random(0, displayWidth-400), (int)random(0, displayHeight-400), 
    (int)random(200, 400), (int)random(200, 400)));
  }
}

void touchImageZone(Zone z) {
  TouchClient.putZoneOnTop(z);
  z.toss();
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+" fps", width/2, 10);
}

