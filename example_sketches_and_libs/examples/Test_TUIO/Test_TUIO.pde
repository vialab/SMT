/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import TUIO.*;

TouchClient client;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchClient.TOUCH_SOURCE_MOUSE);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps",width/2,10);
}
