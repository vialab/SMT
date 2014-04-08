/**
 *  A photo album sketch!
 *  This sketch loads a few images, makes a few copies, and
 *  puts them in randomly sized ImageZones. The ImageZones are
 *  configured to be tossed using the physics engine, and can
 *  collide, bounce, etc.
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);

  final int IMAGE_FILES=5;
  final int IMAGE_COPIES=4;
  PImage[] img = new PImage[IMAGE_FILES];

  for (int i=0; i < IMAGE_FILES; i++) {  
    img[i] = loadImage(i + ".jpg");
  }

  for (int i=0; i < IMAGE_FILES*IMAGE_COPIES; i++) {  
    SMT.add(new ImageZone("ImageZone", img[i%IMAGE_FILES], 
    (int)random(0, displayWidth-400), (int)random(0, displayHeight-400), 
    (int)random(100, 200), (int)random(100, 200)));
  }
  
  for(Zone z : SMT.getZones()){
    z.physics=true; 
  }
}

void touchImageZone(Zone z) {
  SMT.putZoneOnTop(z);
  z.toss();
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+" fps", width/2, 10);
}

