import vialab.SMT.*;

Touch first=null;

void setup() {
  size(800, 800, P3D);
  TouchClient.init(this, TouchSource.MULTIPLE);
}

void draw(){ 
  background(123);
  //save and track the first touch we find
  if(first==null && TouchClient.getTouches().length>0){
    first=TouchClient.getTouches()[0];
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
  for(Touch t : TouchClient.getTouches()){
    text("Touch ID#"+t.sessionID+"x:"+t.x+"\ty:"+t.y,100,170+c*20);
    c++;
  }
}

