import vialab.SMT.*;

void setup() {
  size(600, 600, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  Zone z = new Zone("Pick1", 100, 200, 200, 200);
  //This zone automatically is only touchable where it draws
  Zone z2 = new ShapeZone("Pick2", 300, 200, 100, 100);
  //z2.setDirect(true);
  //z.setDirect(true);
  TouchClient.add(z);
  TouchClient.add(z2);
}

void draw() {
  background(79, 129, 189);
}

void touchPick1(Zone zone) {
  zone.rst();
}

void touchPick2(Zone zone) {
  zone.rst();
}

void drawPick1(Zone zone) {
  fill(255);
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

void drawPick2(Zone zone) {
  fill(255);
  ellipse(100, 100, 100, 100);
}

void pickDrawPick1(Zone zone) {
  rect(100, 100, 100, 100);
  ellipse(100, 100, 100, 100);
}

