import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new ContainerZone("RotatedContainer",0,0,400,400));
  TouchClient.getZone("RotatedContainer").rotateAbout(PI/4,CENTER);
  TouchClient.getZone("RotatedContainer").add(new ButtonZone("TestButton",100,100,200,200,"Button Text"));
}
void draw() {
  background(79, 129, 189);
}
void pressTestButton(){
  println("Button Pressed");
}
