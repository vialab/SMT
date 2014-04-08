/**
 * This examples shows use of the ImageZone to store a PImage
 */

import vialab.SMT.*;
void setup() {
  size(800, 800, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new ImageZone(loadImage("moonwalk.jpg")));
}
void draw() {
  background(79, 129, 189);
}
