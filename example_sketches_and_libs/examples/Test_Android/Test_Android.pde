/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;
import TUIO.*;

TouchClient client;

void setup() {
  //frameRate(1000);
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.ANDROID);
  client.setDrawTouchPoints(true,100);
}

void draw() {
  background(79, 129, 189);
  text(round(frameRate)+"fps", width/2, 10);
}

