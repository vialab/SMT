import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
  TouchClient.add(new LeftPopUpMenuZone(300,100,50,50,200,100,"Button1","Button2"));
}
void draw() {
  background(79, 129, 189);
}
void pressButton1(){
  println("First Button Pressed");
}
void pressButton2(){
  println("Second Button Pressed"); 
}
