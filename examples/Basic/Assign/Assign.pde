/**
 * This shows using Zone.assign() to allow a custom implementation
 * of the mapping of Touches to Zones, in this case making all
 * Touches be assigned to the Zone, instead of the default of
 * those that are in the area of the Zone.
 */
import vialab.SMT.*;

Zone z;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  z = new Zone("Test",0,0,50,50);
  TouchClient.add(z);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

void drawTest(Zone z){
  rect(0,0,50,50);
}

void touchTest(Zone z){
  z.rst(); 
}

void touchDown(){
  z.assign(TouchClient.getTouches()); 
}

