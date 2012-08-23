/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import vialab.mouseToTUIO.*;
import TUIO.*;
import processing.opengl.PGraphicsOpenGL;
import codeanticode.glgraphics.*;

//set some configuration constants
final boolean USE_MOUSE_TO_TUIO=true;
final boolean DRAW_TOUCH_POINTS=true;
final static int IMAGE_COPIES=1;
final static int NUM_IMAGES=11*IMAGE_COPIES;
PImage[] img = new PImage[NUM_IMAGES];

void touchImageZone(Zone z){
  client.putZoneOnTop(z);
  z.rst();
}

TouchClient client;

void setup() {
  size(screenWidth, screenHeight, GLConstants.GLGRAPHICS);
  frameRate(1000);
  for(int i=0; i<NUM_IMAGES; i++){  
    img[i] = loadImage(i%11 + ".jpg"); 
  }
  client = new TouchClient(this, USE_MOUSE_TO_TUIO, true);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS);
  client.add(new Zone("UserArea1",0, 0, 800, 400));
  client.add(new Zone("UserArea2",0, 0, 800, 400));
  client.add(new Zone("UserArea3",0, 0, 800, 400));
  client.add(new Zone("UserArea4",0, 0, 800, 400));
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,height/2);
  fill(255);
}

void drawUserArea1(Zone zone){
  background(0,100,200);
  //rect(0,0,100,100);
}

void drawUserArea2(Zone zone){
  background(200,100,0);
  //rect(0,0,100,100);
}

void drawUserArea3(Zone zone){
  background(0,200,100);
  //rect(0,0,100,100);
}

void drawUserArea4(Zone zone){
  background(200,0,100);
  //rect(0,0,100,100);
}

/*
void touchUserArea1(Zone zone){
  zone.unassignAll();
  zone.setName("UserArea2");
}

void touchUserArea2(Zone zone){
  zone.unassignAll();
  zone.setName("UserArea3");
}

void touchUserArea3(Zone zone){
  zone.unassignAll();
  zone.setName("UserArea4");
}

void touchUserArea4(Zone zone){
  zone.unassignAll();
  zone.setName("UserArea1");
}
*/

void setupUserArea1(Zone zone){
  zone.translate(screenWidth/2-zone.width/2,0);
  zone.rotateAbout(PI,CENTER);
  zone.add(new Zone("Item",0,0,200,100));
  for(int i=0; i<NUM_IMAGES; i++){  
     zone.add(new ImageZone("ImageZone",img[i], (int)random(0, zone.width-400), (int)random(0, zone.height-400), (int)random(200, 400), (int)random(200, 400)));
  }
}

void setupUserArea2(Zone zone){
  zone.translate(screenWidth/2-zone.width/2,screenHeight-zone.height);
  zone.add(new Zone("Item",0,0,200,100));
  for(int i=0; i<NUM_IMAGES; i++){  
     zone.add(new ImageZone("ImageZone",img[i], (int)random(0, zone.width-400), (int)random(0, zone.height-400), (int)random(200, 400), (int)random(200, 400)));
  }
}

void setupUserArea3(Zone zone){
  zone.translate(zone.height,screenHeight/2-zone.width/2);
  zone.rotateAbout(PI/2,CORNER);
  zone.add(new Zone("Item",0,0,200,100));
  for(int i=0; i<NUM_IMAGES; i++){  
     zone.add(new ImageZone("ImageZone",img[i],(int)random(0, zone.width-400), (int)random(0, zone.height-400),(int)random(200, 400), (int)random(200, 400)));
  }
}

void setupUserArea4(Zone zone){
  zone.translate(screenWidth-zone.height,screenHeight/2+zone.width/2);
  zone.rotateAbout(3*PI/2,CORNER);
  zone.add(new Zone("Item",0,0,200,100));
  for(int i=0; i<NUM_IMAGES; i++){  
    zone.add(new ImageZone("ImageZone",img[i],(int)random(0, zone.width-400), (int)random(0, zone.height-400),(int)random(200, 400), (int)random(200, 400)));
  }
}

void drawItem(Zone zone){
  fill(255);
  rect(0,0,zone.width,zone.height); 
  fill(0);
  text("This zone is relative to each user, without any special setup",10,10,100,100);
}

void touchItem(Zone zone){
  zone.rst(); 
}
