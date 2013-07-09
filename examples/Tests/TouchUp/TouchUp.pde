/**
 * This shows the partial parameter matching for the touchUp method
 */

import vialab.SMT.*;
void setup() {
  size(400, 400, P3D);
  SMT.init(this, TouchSource.MOUSE);
  SMT.add(new Zone("Custom1",0,0,200,200));
  SMT.add(new Zone("Custom2",200,0,200,200));
  SMT.add(new Zone("Custom3",0,200,200,200));
  SMT.add(new Zone("Custom4",200,200,200,200));
}
void draw() {
  background(79, 129, 189);
}
void drawCustom1(){
  fill(0);
  rect(0,0,200,200);
  fill(255);
  ellipse(100,100,100,100); 
}
void touchUpCustom1(){
  println("touchup1");
}

void drawCustom2(){
  fill(255);
  rect(0,0,200,200);
  fill(0);
  ellipse(100,100,100,100); 
}
void touchUpCustom2(Zone z){
  println("touchup2");
}

void drawCustom3(){
  fill(255);
  rect(0,0,200,200);
  fill(0);
  ellipse(100,100,100,100); 
}
void touchUpCustom3(Touch t){
  println("touchup3");
}

void drawCustom4(){
  fill(0);
  rect(0,0,200,200);
  fill(255);
  ellipse(100,100,100,100); 
}
void touchUpCustom4(Zone z, Touch t){
  println("touchup4");
}
