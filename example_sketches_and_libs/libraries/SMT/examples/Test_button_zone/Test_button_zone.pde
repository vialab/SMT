/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

ButtonZone z,z2,z3;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  z = new CustomButtonZone();
  TouchClient.add(z);
  z2 = new ButtonZone("Test",0,0,200,200,"test");
  TouchClient.add(z2);
  z2.deactivated=true;
  z3 = new ButtonZone("test",600,0,200,200,"test");
  TouchClient.add(z3);
}

void pressTest(){
  
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+TouchClient.getZones().length,width/2,10);
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
