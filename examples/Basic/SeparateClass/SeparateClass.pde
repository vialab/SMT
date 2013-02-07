//methods in CustomZone class can be used as if they were in this class by adding the class to the init()
import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE,new CustomZone());
  TouchClient.add(new Zone("MyZone1",0,0,200,200));
  TouchClient.add(new Zone("MyZone2",200,0,200,200));
}
void draw() {
  background(79, 129, 189);
}

