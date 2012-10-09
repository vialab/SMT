/**
 *   Created by Erik Paluka
 *   University of Ontario Institute of Technology
 *   August 2011
 *   A photo album sketch!
 *   Ported to simpler multitouch by Zach Cook
 */
import vialab.SMT.*;

TouchClient client;
final static int IMAGE_COPIES=1;
final static int NUM_IMAGES=11*IMAGE_COPIES;
PImage[] img = new PImage[11];
Zone[] zone = new Zone[NUM_IMAGES];

void setup() {
  size(displayWidth, displayHeight, P3D);
  
  client = new TouchClient(this, TouchSource.MOUSE);
  for(int i=0; i<11; i++){  
    img[i] = loadImage(i + ".jpg");
  }
  for(int i=0; i<NUM_IMAGES; i++){  
    zone[i] = new ImageZone("ImageZone",img[i%11], 
    (int)random(0, displayWidth-400), (int)random(0, displayHeight-400), 
    (int)random(200, 400), (int)random(200, 400));
    client.add(zone[i]);
    //zone[i].setDirect(true);
  }
}

void touchImageZone(Zone z){
  client.putZoneOnTop(z);
  z.rst();
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+" fps",width/2,10);
}
