/**
 *  This sketch shows how to make Zones that emulate a multiseat
 *  environment with a user at each side of the screen.
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  
  //create a "UserArea" Zone for each user
  Zone z1=new Zone("UserArea",0, 0, 800, 200);
  Zone z2=new Zone("UserArea",0, 0, 800, 200);
  Zone z3=new Zone("UserArea",0, 0, 800, 200);
  Zone z4=new Zone("UserArea",0, 0, 800, 200);
  
  //place each of the "UserArea" Zones at their own side of the screen
  z1.translate(displayWidth/2-z1.width/2,0);
  z1.rotateAbout(PI,CENTER);
  z2.translate(displayWidth/2-z2.width/2,displayHeight-z2.height);
  z3.translate(z3.height,displayHeight/2-z3.width/2);
  z3.rotateAbout(PI/2,CORNER);
  z4.translate(displayWidth-z4.height,displayHeight/2+z4.width/2);
  z4.rotateAbout(3*PI/2,CORNER);
  
  //Add a "Item" Zone to each of the "UserArea" Zones
  z1.add(new Zone("Item",0,0,200,100));
  z2.add(new Zone("Item",0,0,200,100));
  z3.add(new Zone("Item",0,0,200,100));
  z4.add(new Zone("Item",0,0,200,100));
  
  //add the "UserArea" Zones to the SMT
  //so that they are shown on screen
  SMT.add(z1);
  SMT.add(z2);
  SMT.add(z3);
  SMT.add(z4);
}

void draw() {
  //background with fps
  background(79, 129, 189);
  text(round(frameRate)+"fps, # of zones: "+SMT.getZones().length, width/2, height/2);
  fill(255);
}

void drawUserArea(Zone zone){
  //draw into the "UserArea" Zone
  fill(0,100,200);
  rect(0, 0, zone.width, zone.height);
}

void drawItem(Zone zone){
  //draw into the "Item" Zone
  fill(255);
  rect(0,0,zone.width,zone.height); 
  fill(0);
  text("This zone is relative to each parent zone, without any special setup",10,10,100,100);
}

void touchItem(Zone zone){
  //configure the "Item" Zone to Rotate Scale and Translate (rst)
  zone.rst(); 
}

void touchUserArea(){}
