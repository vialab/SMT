/**
 *  This sketch shows the different options for TouchDraw.
 *  Which has a DEBUG, NONE, and SMOOTH mode, and also a max
 *  path length to draw.
 */

import vialab.SMT.*;

void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.setTouchDraw(TouchDraw.DEBUG,100);
  //SMT.setTouchDraw(TouchDraw.NONE,100);
  //SMT.setTouchDraw(TouchDraw.SMOOTH,100);
  //SMT.setTouchDraw(TouchDraw.DEBUG);
  //SMT.setTouchDraw(TouchDraw.NONE);
  //SMT.setTouchDraw(TouchDraw.SMOOTH);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

