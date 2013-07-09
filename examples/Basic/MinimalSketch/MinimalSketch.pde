/**
 *  This sketch shows the minimum amount of SMT needed to run,
 *  and so is a good starting point for a new sketch.
 */

import vialab.SMT.*;
void setup(){
  size(400,400,P3D);
  SMT.init(this, TouchSource.MOUSE);
}
void draw(){
  background(125);
}
