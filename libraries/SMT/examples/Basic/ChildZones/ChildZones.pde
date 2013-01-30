import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  smooth(8);
  frameRate(600);
  TouchClient.init(this, TouchSource.MOUSE);
  Zone z = new Zone("Parent", 400, 400, 200, 200);
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
  rect(0, 0, z.width, z.height);
}

void touchChild(Zone z) {
  z.rst();
}

void drawChild(Zone z) {
  fill(255, 0, 0);
  rect(0, 0, z.width, z.height);
}
