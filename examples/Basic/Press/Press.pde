/**
 *  This example shows how to use the press method, which is
 *  called when a Touch is released from a Zone, like a button.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new Zone("Test",100,100,200,200));
}
void draw() {
  background(79, 129, 189);
}
void pressTest(){
  println("Zone Pressed");
}
void drawTest(){
 fill(0);
 rect(0,0,200,200); 
}
