import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.setDrawTouchPoints(TouchDraw.NONE,100);
  //TouchClient.setDrawTouchPoints(TouchDraw.NONE,100);
  //TouchClient.setDrawTouchPoints(TouchDraw.SMOOTH,100);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

