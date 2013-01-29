import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new SliderZone("Slider",100,100,200,50,0,100));
}
void draw() {
  background(79, 129, 189);
}
void touchUpSlider(SliderZone s, Touch t){
  println(s.getCurrentValue());
}
