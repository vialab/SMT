/**
 *  This example is similar to ChildZones, except instead of using
 *  Zone.rst() the child uses Zone.drawWithinParent(), which means
 *  the child will not move outside of the bounds of the parent.
 */

import vialab.SMT.*;

void setup() {
  size(400, 400, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  Zone z = new Zone("Parent", 100, 100, 200, 200);
  z.add(new Zone("Child", 0, 0, 100, 100));
  SMT.add(z);
}

void draw() {
  background(79, 129, 189);
}

void touchParent(Zone z) {
  z.rst();
}

void drawParent(Zone z) {
  background(144, 202, 119);
}

void touchChild(Zone z) {
  z.dragWithinParent();
}

void drawChild(Zone z) {
  background(228, 135, 67);
}
