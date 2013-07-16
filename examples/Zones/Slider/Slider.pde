/**
 *  This shows the SliderZone in use, it can be used to select a
 *  value, but here is just printing it out upon release
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new SliderZone("Slider",100,100,200,50,0,100));
}
void draw() {
  background(79, 129, 189);
}
void touchUpSlider(SliderZone s, Touch t){
  println(s.getCurrentValue());
}
