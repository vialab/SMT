/**
 *  This sketch emulates coloured bubbles being popped by touch.
 *  It shows dynamic add and remove of Zones.
 */
import vialab.SMT.*;

class BubbleZone extends Zone {
  color c;
  BubbleZone(String name, int x, int y, int w, int h, color c) {
    super(name, x, y, w, h);
    this.c=c;
    beginTouch(); 
    translate(random(displayWidth-100), random(displayHeight-100));
    endTouch();
    //direct is much faster, as it renders directly to the screen or parent/no image cop
    //but loses the control over the drawing and seperation that non-direct has
    //this.setDirect(true);
  }
}
void setup() {
  frameRate(1000);
  size(displayWidth, displayHeight, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new BubbleZone("Bubble", 0, 0, 100, 100, color(random(255), random(255), random(255))));
}

void draw() { 
  if (SMT.getZones().length<100) {
    SMT.add(new BubbleZone("Bubble", 0, 0, 100, 100, color(random(255), random(255), random(255))));
  }
  background(79, 129, 189);
  fill(255);
  text(round(frameRate)+"fps, # of zones: "+SMT.getZones().length, width/2, 10);
}

void drawBubble(BubbleZone zone) {
  fill(zone.c);
  ellipse(50, 50, 100, 100);
  //fill(0);
  //text(""+zone.getPickColor(),50,50);
}

void pickDrawBubble(BubbleZone zone) {
  ellipse(50, 50, 100, 100);
}

void touchBubble(BubbleZone zone) {
  zone.unassignAll();
  SMT.remove(zone);
}

