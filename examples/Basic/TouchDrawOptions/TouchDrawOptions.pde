/**
 *  This sketch shows the different options for TouchDraw.
 *  Which has a DEBUG, NONE, and SMOOTH mode, and also a max
 *  path length to draw.
 */

import vialab.SMT.*;

void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.setTouchDraw(TouchDraw.DEBUG,100);
  //TouchClient.setTouchDraw(TouchDraw.NONE,100);
  //TouchClient.setTouchDraw(TouchDraw.SMOOTH,100);
  //TouchClient.setTouchDraw(TouchDraw.DEBUG);
  //TouchClient.setTouchDraw(TouchDraw.NONE);
  //TouchClient.setTouchDraw(TouchDraw.SMOOTH);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

