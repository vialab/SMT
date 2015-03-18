/**
 *  This examples shows use of the LeftPopUpMenuZone, which is a
 *  red arrow that when touched will toggle the display of a menu
 *  with buttons as named in the constructor. Press methods for 
 *  these buttons can then be used.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new LeftPopUpMenuZone(300, 100, 50, 50, 200, 100, "Button1", "Button2"));
}
void draw() {
  background(79, 129, 189);
}
void pressButton1() {
  println("First Button Pressed");
}
void pressButton2() {
  println("Second Button Pressed");
}

