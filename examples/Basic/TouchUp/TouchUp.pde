import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new Zone("Custom",0,0,200,200));
}
void draw() {
  background(79, 129, 189);
}
void drawCustom(Zone z){
  fill(255);
  rect(0,0,200,200);
}
void touchUpCustom(Zone z, Touch t){
  println("Touch #"+t.sessionID+" went up");
}
