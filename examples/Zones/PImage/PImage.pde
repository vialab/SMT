/**
 * This examples shows use of the ImageZone to store a PImage
 */

import vialab.SMT.*;
void setup() {
  size(800, 800, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new ImageZone(loadImage("moonwalk.jpg")));
}
void draw() {
  background(79, 129, 189);
}
