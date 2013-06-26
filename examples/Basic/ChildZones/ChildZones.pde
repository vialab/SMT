/**
 * This shows Zones as children of other Zones by using add().
 * This makes the Zones have a child/parent relationship that
 * gives positional/rotational/translation inheritance.
 */

import vialab.SMT.*;

void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  Zone z = new Zone("Parent", 100, 100, 200, 200);
  z.add(new Zone("Child", 0, 0, 100, 100));
  TouchClient.add(z);
}

void draw() {
  background(79, 129, 189);
}

void touchParent(Zone z) {
  z.rst();
}

void drawParent(Zone z) {
  background(0, 255, 0);
  fill(0);
  text("Parent",z.width/2, z.height/2);
}

void touchChild(Zone z) {
  z.rst();
}

void drawChild(Zone z) {
  background(255, 0, 0);
  fill(0);
  text("Child",z.width/2, z.height/2);
}
