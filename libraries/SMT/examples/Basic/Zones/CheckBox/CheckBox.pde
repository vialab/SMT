import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new CheckBoxZone("TestButton",100,100,200,200));
}
void draw() {
  background(79, 129, 189);
}


