/**
 *   Created by Zach Cook
 *   University of Ontario Institute of Technology
 *   May 2012
 *   A test sketch using simpleMultiTouch toolkit
 */
import vialab.SMT.*;

TouchClient client;
Touch first=null;

void setup() {
  size(displayWidth, displayHeight, P3D);
  client = new TouchClient(this, TouchSource.MOUSE);
}

void draw(){ 
  //this tests that a touch object gets updated with the data from tuio
  if(first==null&&client.getTouches().size()>0){
    print("found a cursor");
    first=client.getTouch(0);
  }
  background(123);
  if(first!=null){
    text("x:"+first.x+"\ty:"+first.y,100,100);
  }
}

