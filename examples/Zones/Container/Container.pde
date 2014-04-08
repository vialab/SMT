/**
 *  This example show the use of ContainerZone, which by default
 *  has no visual representation, and is perfect to use to hold
 *  other Zones. In this example it is used to apply a rotation
 *  to its child Zones, in this case a ButtonZone
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new ContainerZone("RotatedContainer",0,0,400,400));
  SMT.get("RotatedContainer").rotateAbout(PI/4,CENTER);
  SMT.get("RotatedContainer").add(new ButtonZone("TestButton",100,100,200,200,"Button Text"));
}
void draw() {
  background(79, 129, 189);
}
void pressTestButton(){
  println("Button Pressed");
}
