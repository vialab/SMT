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
ButtonZone z,z2,z3;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.MOUSE);
  client.setDrawTouchPoints(DRAW_TOUCH_POINTS,10);
  z = new CustomButtonZone();
  client.add(z);
  z2 = new ButtonZone("Test",0,0,200,200,"test");
  client.add(z2);
  z2.deactivated=true;
  z3 = new ButtonZone("test",600,0,200,200,"test");
  client.add(z3);
}

void pressTest(){
  
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+client.getZones().length,width/2,10);
  text(""+z.isButtonDown(),200,200);
}

class CustomButtonZone extends ButtonZone{
  CustomButtonZone(){
    super("CustomButton",400, 400, 100, 100); 
  }
  void pressImpl(){
    print("ButtonPressed\n");
  }
}
