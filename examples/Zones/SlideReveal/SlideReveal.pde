/**
 * This shows how the SlideRevealZone is used. The red arrow can
 * be dragged to reveal the hidden text.
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new SlideRevealZone(100,100,200,50,"Some hidden text."));
}
void draw() {
  background(79, 129, 189);
}
