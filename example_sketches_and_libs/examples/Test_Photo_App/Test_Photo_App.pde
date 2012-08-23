/**
 *   Created by Erik Paluka
 *   University of Ontario Institute of Technology
 *   August 2011
 *   A photo album sketch!
 */
import processing.opengl.PGraphicsOpenGL;

PImage[] img = new PImage[11];

void setup() {
  size(screenWidth, screenHeight, OPENGL);
  frameRate(1000);
  for (int i = 0; i < 11; i++) {
    img[i] = loadImage(i + ".jpg");
  }
}

void draw() { 
  background(79, 129, 189);
  for (int i = 0; i < 11; i++) {
    translate((int)random(0, 800), (int)random(0, 800));
    image(img[i%11],0,0,400,400);
  }
  text(frameRate+" fps",10,10);
}

