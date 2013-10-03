/**
 * This tests the android support of SMT. Currently not working.
 */
import vialab.SMT.*;

void setup() {
  size(displayWidth, displayHeight, JAVA2D);
  //SMT.init(this, TouchSource.ANDROID);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

