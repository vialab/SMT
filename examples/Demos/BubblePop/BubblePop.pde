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
    translate(random(displayWidth-100), random(displayHeight-100));
  }
}
void setup() {
  size(displayWidth, displayHeight, JAVA2D);
  SMT.init(this, TouchSource.MULTIPLE);
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
  ellipse(zone.width/2, zone.height/2, zone.width, zone.height);
}

void pickDrawBubble(BubbleZone zone) {
  ellipse(zone.width/2, zone.height/2, zone.width, zone.height);
}

void touchBubble(BubbleZone zone) {
  SMT.remove(zone);
}
