/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using SMTv3 toolkit
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new CustomButtonZone());
  TouchClient.add(new ButtonZone("Test",0,0,200,200,"test"));
  TouchClient.getZoneByName("Test",ButtonZone.class).deactivated=true;
  TouchClient.add(new ButtonZone("test",600,0,200,200,"test"));
}

void pressTest(){
  
}

void draw() {
  background(79, 129, 189);
  text(frameRate+"fps, # of zones: "+TouchClient.getZones().length,width/2,10);
  text(""+TouchClient.getZoneByName("CustomButton",ButtonZone.class).isButtonDown(),200,200);
}

class CustomButtonZone extends ButtonZone{
  CustomButtonZone(){
    super("CustomButton",400, 400, 100, 100); 
  }
  void pressImpl(){
    print("ButtonPressed\n");
  }
}
