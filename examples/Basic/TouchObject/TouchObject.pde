/**
 *  This shows use of a Touch, which can be stored indefinitely
 *  and will keep its data. This example stores the first Touch
 *  and displays the rest when they are active.
 */

import vialab.SMT.*;

Touch first=null;

void setup() {
  size(800, 800, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
}

void draw(){ 
  background(123);
  //save and track the first touch we find
  if(first==null && SMT.getTouches().length>0){
    first=SMT.getTouches()[0];
  }
  //display info on first touch
  if(first==null){
    text("First Touch: Null",100,100);
  }
  else{
    text("First Touch: x:"+first.x+"\ty:"+first.y+" Touch is down:"+(first.isDown?"yes":"no"),100,100);
  }
  //show all current touches too
  text("All Touches: ",100,150);
  int c=0;
  for(Touch t : SMT.getTouches()){
    text("Touch ID#"+t.sessionID+"x:"+t.x+"\ty:"+t.y+"Source: "+t.getTouchSource(),100,170+c*20);
    c++;
  }
}

