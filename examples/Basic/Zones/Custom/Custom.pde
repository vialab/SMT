import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new Zone("Custom",0,0,200,200));
}
void draw() {
  background(79, 129, 189);
}
void drawCustom(){
  fill(0);
  rect(0,0,200,200);
  fill(255);
  ellipse(100,100,100,100); 
}
void touchCustom(Zone z){
  z.rst();
}
