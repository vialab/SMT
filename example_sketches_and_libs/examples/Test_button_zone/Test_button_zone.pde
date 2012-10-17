/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

//set some configuration constants
final boolean DRAW_TOUCH_POINTS=true;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.MOUSE);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS,10);
  Zone z = new CustomButtonZone();
  client.add(z);
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+client.getZones().length,width/2,10);
}

class CustomButtonZone extends ButtonZone{
  CustomButtonZone(){
    super("CustomButton",400, 400, 100, 100); 
  }
  void pressImpl(){
    print("ButtonPressed\n");
  }
}
