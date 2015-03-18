/**
 * This shows the TabZone in use to switch between tab screens
 * similar to a web-browser
 */

import vialab.SMT.*;

void setup() {
  size(800, 800, SMT.RENDERER);
  SMT.init(this, TouchSource.AUTOMATIC);
  SMT.add(new TabZone("Tab"));
  SMT.get("Tab").add(new Zone("BlackSquare",100,100,100,100));
  SMT.get("Tab").add(new Zone("WhiteSquare",500,100,100,100));
}
void draw() {
  background(79, 129, 189);
}
void drawBlackSquare(){
  fill(0);
  rect(0,0,100,100);
}
void drawWhiteSquare(){
  fill(255);
  rect(0,0,100,100);
}

void touchBlackSquare(){}
void touchWhiteSquare(){}
