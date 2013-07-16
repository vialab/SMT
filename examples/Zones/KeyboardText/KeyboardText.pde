/**
 *  This example shows how to use a KeyboardZone with a TextZone.
 *  Since Zone implements KeyListener, any Zone can be added to
 *  A KeyboardZone using addKeyListener(). The TextZone will
 *  display any keys it recieves.
 */

import vialab.SMT.*;
void setup() {
  size(800, 800, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  KeyboardZone kb = new KeyboardZone("Keyboard");
  kb.add(new TextZone("Text", 0, 400, 200, 200));
  SMT.add(kb);
}
void draw() {
  background(79, 129, 189);
}

