import vialab.SMT.*;
void setup() {
  size(800, 800, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new TabZone("Tab"));
  TouchClient.getZone("Tab").add(new Zone("Test1",100,100,100,100));
  TouchClient.getZone("Tab").add(new Zone("Test2",500,100,100,100));
}
void draw() {
  background(79, 129, 189);
}
void drawTest1(){
  fill(0);
  rect(0,0,100,100);
}
void drawTest2(){
  fill(255);
  rect(0,0,100,100);
}
