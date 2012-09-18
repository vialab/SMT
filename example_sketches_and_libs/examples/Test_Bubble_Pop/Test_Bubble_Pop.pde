/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import TUIO.*;

final boolean DRAW_TOUCH_POINTS=true;

TouchClient client;

class BubbleZone extends Zone{
   color c;
   BubbleZone(String name, int x, int y, int w, int h, color c){
     super(name, x, y, w, h);
     this.c=c; 
     translate(random(displayWidth-100),random(displayHeight-100));
     
     //direct is much faster, as it renders directly to the screen or parent/no image cop
     //but loses the control over the drawing and seperation that non-direct has
     //this.setDirect(true);
   }
}
void setup() {
  frameRate(1000);
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchClient.TouchSource.MOUSE);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS);
  client.add(new BubbleZone("Bubble",0, 0, 100, 100, color(random(255),random(255),random(255))));
}

void draw() { 
  if(client.getZones().length<100){
      client.add(new BubbleZone("Bubble",0, 0, 100, 100, color(random(255),random(255),random(255))));
  }
  background(79, 129, 189);
  fill(255);
  text(round(frameRate)+"fps, # of zones: "+client.getZones().length,width/2,10);
  client.drawPickBuffer(800,800,200,200);
}

void drawBubble(BubbleZone zone){
  fill(zone.c);
  ellipse(50,50,100,100);
  //fill(0);
  //text(""+zone.getPickColor(),50,50);
}

void pickDrawBubble(BubbleZone zone){
  ellipse(50,50,100,100);
}

void touchBubble(BubbleZone zone){
  zone.unassignAll();
  client.remove(zone);
}
