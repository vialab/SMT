/**
 *  This sketch shows the minimum amount of SMT needed to run,
 *  and so is a good starting point for a new sketch.
 */

import vialab.SMT.*;
void setup(){
  size(400,400,SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
}
void draw(){
  background(125);
}
