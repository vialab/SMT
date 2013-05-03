/**
 *  This example is similar to ChildZones, except instead of using
 *  Zone.rst() the child uses Zone.drawWithinParent(), which means
 *  the child will not move outside of the bounds of the parent.
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
  fill(0, 255, 0);
  rect(0, 0, 200, 200);
}

void touchChild(Zone z) {
  z.dragWithinParent();
}

void drawChild(Zone z) {
  fill(255, 0, 0);
  rect(0, 0, 100, 100);
}
