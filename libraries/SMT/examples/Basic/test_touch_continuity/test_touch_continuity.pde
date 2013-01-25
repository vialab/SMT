/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;

Touch first=null;

void setup() {
  size(displayWidth, displayHeight, P3D);
  TouchClient.init(this, TouchSource.MOUSE);
}

void draw(){ 
  //this tests that a touch object gets updated with the data from tuio
  if(first==null&&TouchClient.getTouches().length>0){
    print("found a cursor");
    first=TouchClient.getTouch(0);
  }
  background(123);
  if(first!=null){
    text("x:"+first.x+"\ty:"+first.y+"down:"+(first.isDown?"yes":"no"),100,100);
  }
}

