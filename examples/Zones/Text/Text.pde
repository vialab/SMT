/**
 * This shows a TextZone, any keyboard input will show up on
 * the Zone since the 5th parameter turns on listening to the
 * applet keyboard input when true
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, JAVA2D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new TextZone(0,0,200,200,true));
}
void draw() {
  background(79, 129, 189);
}
