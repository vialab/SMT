/**
 *  This shows the use of the PatternUnlockZone, designed similar
 *  to android's unlock screen. It doesn't currently work well,
 *  but will print passcode accepted when the S-gesture is entered 
 */ 

import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new PatternUnlockZone(100,100,200,200));
}
void draw() {
  background(79, 129, 189);
}
