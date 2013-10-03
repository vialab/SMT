import vialab.SMT.*;
void setup() {
  size(400, 400, JAVA2D);
  SMT.init(this, TouchSource.MULTIPLE);
  SMT.add(new ButtonZone("TestButton",100,100,200,200,"Button Text"));
}
void draw() {
  background(79, 129, 189);
}
void pressTestButton(){
  println("Button Pressed");
}
