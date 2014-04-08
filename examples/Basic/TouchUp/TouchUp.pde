/**
 *  This sketch shows use of the touchUp method, which is called
 *  when a touch that was assigned to the Zone is released. Unlike
 *  the press method, touchUp is called as long as the Touch is
 *  assigned to the Zone, and doesn't need to be currently over it.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new Zone("Custom",0,0,200,200));
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
