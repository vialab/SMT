/**
 *   Created by Erik Paluka
 *   University of Ontario Institute of Technology
 *   August 2011
 *   A photo album sketch!
 *   Ported to simpler multitouch by Zach Cook
 */
import vialab.SMT.*;
import vialab.mouseToTUIO.*;
import TUIO.*;
import processing.opengl.PGraphicsOpenGL;
import codeanticode.glgraphics.*;

TouchClient client;
final static int IMAGE_COPIES=3;
final static int NUM_IMAGES=11*IMAGE_COPIES;
PImage[] img = new PImage[11];
Zone[] zone = new Zone[NUM_IMAGES];

void setup() {frameRate(1000);
  size(screenWidth, screenHeight, GLGraphics.GLGRAPHICS);
  
  client = new TouchClient(this,true,true);
  for(int i=0; i<11; i++){  
    img[i] = loadImage(i + ".jpg");
  }
  for(int i=0; i<NUM_IMAGES; i++){  
    zone[i] = new ImageZone("ImageZone",img[i%11], 
    (int)random(0, screenWidth-400), (int)random(0, screenHeight-400), 
    (int)random(200, 400), (int)random(200, 400));
    client.add(zone[i]);
    //zone[i].setDirect(true);
  }
  
  client.runWinTouchTuioServer("C:/Users/Zach/Downloads/Touch2Tuio_0.2/Touch2Tuio/Release/Touch2Tuio.exe");
}

void touchImageZone(Zone z){
  client.putZoneOnTop(z);
  z.rst();
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+" fps",width/2,10);
  client.drawPickBuffer(800,800,200,200);
}

void pickDraw(Zone zone){
   background(zone.getPickColor()); 
}
