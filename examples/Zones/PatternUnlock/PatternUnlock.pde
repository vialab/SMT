/**
 *  This shows the use of the PatternUnlockZone, designed similar
 *  to android's unlock screen. It doesn't currently work well,
 *  but will change the background color when the S-gesture is entered
 *  Layout:
 *  0 1 2
 *  3 4 5
 *  6 7 8
 *  Default passcode: 67412 or 21476
 */ 

import vialab.SMT.*;
void setup() {
  size(400, 400, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new PatternUnlockZone(100,100,200,200));
}
void draw() {
  if(SMT.get("PatternUnlockZone",PatternUnlockZone.class).accepted){
    background(0, 255, 0);
  }
  else{
    background(79, 129, 189);
  }
}
