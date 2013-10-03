/**
 * This shows using Zone.assign() to allow a custom implementation
 * of the mapping of Touches to Zones, in this case making all
 * Touches be assigned to the Zone, instead of the default of
 * those that are in the area of the Zone.
 */
import vialab.SMT.*;

Zone z;

void setup() {
  size(displayWidth, displayHeight, JAVA2D);
  SMT.init(this, TouchSource.MULTIPLE);
  z = new Zone("Test",0,0,50,50);
  SMT.add(z);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

void drawTest(Zone z){
  background(255);
}

void touchTest(Zone z){
  z.rst(); 
}

void touchDown(){
  z.assign(SMT.getTouches()); 
}

